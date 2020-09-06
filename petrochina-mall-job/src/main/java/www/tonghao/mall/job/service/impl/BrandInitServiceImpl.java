package www.tonghao.mall.job.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.jd.attwrap.PriceAttr;
import www.tonghao.mall.api.jd.attwrap.StateResultAttr;
import www.tonghao.mall.api.stb.attwrap.ProductPriceAttr;
import www.tonghao.mall.api.stb.attwrap.ProductStateAttr;
import www.tonghao.mall.api.stb.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.stb.resultwrap.ProductStateRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.BrandInitService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.mall.job.util.StbUtilApi;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.mapper.ProductPicsMapper;
import www.tonghao.service.common.mapper.UpperLowerHistoryMapper;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.ProtocolService;
import www.tonghao.service.common.service.SuppliersService;

@Service("brandInitService")
public class BrandInitServiceImpl implements BrandInitService {
	private static Log logger = LogFactory.getLog(BrandInitServiceImpl.class);
	@Autowired
	private SuppliersService suppliersService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private ProductPicsMapper productPicsMapper;
	@Autowired
	private ProtocolService protocolService;
	@Autowired
	private UpperLowerHistoryMapper  upperLowerHistoryMapper;
	
	@Override
	public void brandInitServiceJob() {
		
		Example example=new Example(PlatformCatalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("treeDepth", 3);
		createCriteria.andEqualTo("isDelete", 0);
		createCriteria.andEqualTo("isParent", "false");
		List<PlatformCatalogs> pc = platformCatalogsService.selectByExample(example);
		if(!CollectionUtil.collectionIsEmpty(pc)) {
			for (PlatformCatalogs platformCatalogs : pc) {
				product(platformCatalogs);
			}
		}
		
		
	}


	private void product(PlatformCatalogs platformCatalogs) {
		Example example_product=new Example(Products.class);
		Criteria createCriteria_product = example_product.createCriteria();
		createCriteria_product.andEqualTo("catalogId", platformCatalogs.getId());
		createCriteria_product.andEqualTo("isDelete", 0);
		createCriteria_product.andIsNull("brandId");
		createCriteria_product.andIsNotNull("mallBrandName");
		List<Products> pro_list = productsService.selectByExample(example_product);
		if(!CollectionUtil.collectionIsEmpty(pro_list)) {
			for (Products products : pro_list) {
				Brand brand = getBrandId(products);
				if(brand!=null) {
					products.setBrandId(brand.getId());
					products.setBrandName(brand.getName());
					Suppliers suppliers = suppliersService.selectByKey(products.getSupplierId());
					if(suppliers!=null){
						if(Constant.MALL_JD_CODE.equals(suppliers.getCode())){
							updateJdProduct(suppliers,products);
						}else if(Constant.MALL_DL_CODE.equals(suppliers.getCode())){
							
						}else if(Constant.MALL_STB_CODE.equals(suppliers.getCode())){
							//updateStbProduct(suppliers,products);
						}
					}
					if(suppliers.getIsPrice()==0){
						check(products.getSku(),suppliers);
					}
					
				}
			}
		}
	}
	public void updateJdProduct(Suppliers suppliers,Products products){
		if(suppliers.getIsPrice()==0){//是上架，并且自动上架的时候才会去
			try {
				www.tonghao.mall.api.jd.resultwrap.ProductStateRes productStateApi = JdUtilApi.ProductStateApi(products.getSku());
			    if(productStateApi.isSuccess()){
			    	List<StateResultAttr> result = productStateApi.getResult();
			    	if(!CollectionUtil.collectionIsEmpty(result)){
			    		StateResultAttr stateResultAttr = result.get(0);
			    		if(stateResultAttr.getState()==0){//下架
			    			products.setStatus(4);
			    			saveUpperLowerHistory(products.getId(), 4, suppliers.getId(), Constant.EMALL_ERROR);
			    		}else if(stateResultAttr.getState()==1){//上架，判断是否手动下架
			    			if(products.getIsShow()==3){
			    				products.setStatus(3);
			    				saveUpperLowerHistory(products.getId(), 3, suppliers.getId(), Constant.EMALL_SUCCESS);
			    			}
			    		}
			    	}
			    }
			} catch (Exception e) {
				products.setStatus(4);
				saveUpperLowerHistory(products.getId(), 4, suppliers.getId(), "上下架状态接口异常，自动下架");
				e.printStackTrace();
			}
		}
		
		try {
			BigDecimal marketPrice = products.getMarketPrice();
			BigDecimal protocolPrice = products.getProtocolPrice();
			BigDecimal price = products.getPrice();
			www.tonghao.mall.api.jd.resultwrap.ProductPriceRes productPricesApi = JdUtilApi.ProductPricesApi(products.getSku());
		    if(productPricesApi.isSuccess()){
		    	List<PriceAttr> result = productPricesApi.getResult();
		    	if(!CollectionUtil.collectionIsEmpty(result)){
		    		PriceAttr priceAttr = result.get(0);
		    		products.setMarketPrice(priceAttr.getJdPrice());
		    		products.setProtocolPrice(priceAttr.getPrice());
					if(suppliers.getIsPrice()==0&&protocolPrice.compareTo(price)==0){
						products.setPrice(priceAttr.getPrice());
					}
					if(suppliers.getIsPrice()==1||(suppliers.getIsPrice()==0&&protocolPrice.compareTo(price)!=0)){
						if(marketPrice.compareTo(priceAttr.getJdPrice())!=0||protocolPrice.compareTo(priceAttr.getPrice())!=0){
							products.setStatus(4);
							products.setIsChangePrice(0);
							saveUpperLowerHistory(products.getId(), 4, suppliers.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+priceAttr.getJdPrice()+",协议价："+priceAttr.getPrice());
						}
					}
		    	}
		    }
		} catch (Exception e) {
			products.setStatus(4);
			saveUpperLowerHistory(products.getId(), 4, suppliers.getId(), "价格接口异常，商品自动下架");
		}
		productsService.updateNotNull(products);
		
	}
	public Products isProduct(String skuId,Long supplierId) {
		Products products=null;
		try {
			Products entity=new Products();
			entity.setSupplierId(supplierId);
			entity.setSku(skuId);
			entity.setIsDelete(0);
			products = productsService.selectEntityOne(entity);
		} catch (Exception e) {
			logger.info(skuId+"出现多个商品,入库失败");
		}
		return products;
		
	}
	private boolean isNull(String str){
		if(str==null||"null".equals(str)){
			return true;
		}
		return false;
	}
	public void check(String skuId,Suppliers suppliers ) {
		Products pro = isProduct(skuId, suppliers.getId());
		if(pro!=null) {
			if(pro.getStatus()==3) {
				if(Constant.MALL_JD_CODE.equals(suppliers.getCode())){
					if(isNull(pro.getBrandName())) {//判断商品型号或者品牌
						pro.setStatus(4);
						saveUpperLowerHistory(pro.getId(), 4, suppliers.getId(), Constant.MODEL_ERROR);
					}
				}else{
					if(isNull(pro.getBrandName())||isNull(pro.getModel())) {//判断商品型号或者品牌
						pro.setStatus(4);
						saveUpperLowerHistory(pro.getId(), 4, suppliers.getId(), Constant.MODEL_ERROR);
					}
				}
				
				if(pro.getProtocolPrice()==null||pro.getMarketPrice()==null||pro.getProtocolPrice().compareTo(new BigDecimal("0"))==0){//判断价格
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, suppliers.getId(), Constant.PRODUCT_PRICE);
				}
				if(pro.getProtocolPrice()!=null&&pro.getMarketPrice()!=null&&pro.getProtocolPrice().compareTo(pro.getMarketPrice())>0){//判断价格
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, suppliers.getId(), Constant.PRICE_ERROR);
				}
				
				if(isNull(pro.getDetail())) {
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, suppliers.getId(), Constant.PRODUCT_DETAIL);
				}
				if(isNull(pro.getCoverUrl())) {
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, suppliers.getId(), Constant.PRODUCT_IMAGE);
				}
				if(isNull(pro.getCatalogName())) {
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, suppliers.getId(), "品目没映射自动下架");
				}
				productsService.updateNotNull(pro);
			}
		}
	}
	public void updateStbProduct(Suppliers suppliers,Products product) {
		if(suppliers.getIsPrice()==0){//是上架，并且自动上架的时候才会去
			try {
				ProductStateRes productStateApi = StbUtilApi.ProductStateApi(product.getSku());//上下架状态
				if(productStateApi.isSuccess()) {
					List<ProductStateAttr> list = productStateApi.getList();
					if(!CollectionUtil.collectionIsEmpty(list)) {
						ProductStateAttr productStateAttr = list.get(0);
						int state = productStateAttr.getState();
						if(state==0) {
							product.setStatus(4);
							saveUpperLowerHistory(product.getId(), 4, product.getSupplierId(), Constant.EMALL_ERROR);
						}else if(state==1){
							if(product.getIsShow()==3){
								product.setStatus(3);
								saveUpperLowerHistory(product.getId(), 3, product.getSupplierId(), Constant.EMALL_SUCCESS);
			    			}
						}
					}
				}
			} catch (Exception e) {
				product.setStatus(4);
				saveUpperLowerHistory(product.getId(), 4, product.getSupplierId(), "上下架接口调用异常，自动下架");
				logger.info(product.getSku()+"上下架状态接口失败");
			}
		}
			try {
				BigDecimal marketPrice = product.getMarketPrice();
				BigDecimal protocolPrice = product.getProtocolPrice();
				BigDecimal price = product.getPrice();
				ProductPriceRes productPriceApi = StbUtilApi.ProductPriceApi(product.getSku());
				if(productPriceApi.isSuccess()) {
					List<ProductPriceAttr> attrs = productPriceApi.getAttrs();
					if(!CollectionUtil.collectionIsEmpty(attrs)) {
						ProductPriceAttr productPriceAttr = attrs.get(0);
						product.setMarketPrice(productPriceAttr.getMall_price());
						product.setProtocolPrice(productPriceAttr.getPrice());
						if(suppliers.getIsPrice()==0&&protocolPrice.compareTo(price)==0){
							product.setPrice(productPriceAttr.getPrice());
						}
						if(suppliers.getIsPrice()==1||(suppliers.getIsPrice()==0&&protocolPrice.compareTo(price)!=0)){
							if(marketPrice.compareTo(productPriceAttr.getMall_price())!=0||protocolPrice.compareTo(productPriceAttr.getPrice())!=0){
								product.setStatus(4);
								product.setIsChangePrice(0);
								saveUpperLowerHistory(product.getId(), 4, suppliers.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+productPriceAttr.getMall_price()+",协议价："+productPriceAttr.getPrice());
							}
						}
					}
				}
			} catch (Exception e) {
				product.setStatus(4);
				saveUpperLowerHistory(product.getId(), 4, product.getSupplierId(), "价格接口调用异常，自动下架");
				logger.info(product.getSupplierId()+"价格接口失败");
			}
		productsService.updateNotNull(product);
	}
	
	
	public void saveUpperLowerHistory(Long productId,int type,Long supplierId,String content) {
		UpperLowerHistory history=new UpperLowerHistory();
		history.setCreatedAt(DateUtilEx.timeToDate(new Date(), "YYYY-MM-dd HH:mm:ss"));
		history.setUpdatedAt(DateUtilEx.timeToDate(new Date(), "YYYY-MM-dd HH:mm:ss"));
		history.setType(type);
		history.setReason(content);
		history.setProductId(productId);
		history.setOperateId(supplierId);
		upperLowerHistoryMapper.insert(history);
	}
	/**
	 * 获取对应品牌
	 * */
	public Brand getBrandId(Products products) {
		Brand return_brand=new Brand();
		List<Brand> list = brandMapper.findByMappingName(products.getMallBrandName());
		if(!CollectionUtil.collectionIsEmpty(list)){
			oth:
				for (Brand brand : list) {
					String mappingName = brand.getMappingName();
					String[] split = mappingName.split("\\|");
					for (String string : split) {
						if(string.equals(products.getMallBrandName())){
							return_brand=brand;
							break oth;
						}
					}
				}
				return return_brand;
		}else{
			return null;
		}
	}
	
}
