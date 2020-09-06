package www.tonghao.mall.job.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.jd.attwrap.Image;
import www.tonghao.mall.api.jd.attwrap.ImageResultAttr;
import www.tonghao.mall.api.jd.attwrap.PriceAttr;
import www.tonghao.mall.api.jd.attwrap.ProductOther;
import www.tonghao.mall.api.jd.attwrap.StateResultAttr;
import www.tonghao.mall.api.jd.resultwrap.ProductImageRes;
import www.tonghao.mall.api.jd.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.jd.resultwrap.ProductRes;
import www.tonghao.mall.api.jd.resultwrap.ProductStateRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.DeleteProductJdService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.EmallCatalogs;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductPics;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.entity.SkuLogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.mapper.EmallCatalogsMapper;
import www.tonghao.service.common.mapper.ProductPicsMapper;
import www.tonghao.service.common.mapper.SkuLogsMapper;
import www.tonghao.service.common.mapper.UpperLowerHistoryMapper;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.ProtocolService;
import www.tonghao.service.common.service.SuppliersService;

@Service("deleteProductJdService")
public class DeleteProductJdServiceImpl implements DeleteProductJdService {
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
	private UpperLowerHistoryMapper upperLowerHistoryMapper;
	@Autowired
	private SkuLogsMapper skuLogsMapper;
	@Autowired
	private EmallCatalogsMapper emallCatalogsMapper;
	
	@Override
	public void executeDeleteProductJdJob() {
		
		List<Products> productJdSku = productsService.productJdSku();
		if(!CollectionUtil.collectionIsEmpty(productJdSku)){
			Set<String> set=new HashSet<>();
			for (Products products : productJdSku) {
				Example example=new Example(Products.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("sku",products.getSku());
				createCriteria.andEqualTo("supplierId",products.getSupplierId());
				List<Products> pro_list = productsService.selectByExample(example);
				if(!CollectionUtil.collectionIsEmpty(pro_list)){
					for (Products pdts : pro_list) {
						Example old_example=new Example(ProductPics.class);
						Criteria old_createCriteria = old_example.createCriteria();
						old_createCriteria.andEqualTo("productId", pdts.getId());
						List<ProductPics> old_pic = productPicsMapper.selectByExample(old_example);
						if(!CollectionUtil.collectionIsEmpty(old_pic)) {
							for (ProductPics productPics : old_pic) {
								productPicsMapper.deleteByPrimaryKey(productPics.getId());
							}
						}
						productsService.delete(pdts.getId());
					}
				}
				Example skuLogs_example=new Example(SkuLogs.class);
				Criteria skuLogs_createCriteria = skuLogs_example.createCriteria();
				skuLogs_createCriteria.andEqualTo("messSku", products.getSku());
				skuLogs_createCriteria.andEqualTo("mallCode", "jd");
				skuLogs_example.setOrderByClause(" id desc");
				List<SkuLogs> sku_list = skuLogsMapper.selectByExample(skuLogs_example);
				boolean flg=true;
				if(!CollectionUtil.collectionIsEmpty(sku_list)){
					SkuLogs skuLogs = sku_list.get(0);
					if(skuLogs.getType()==2){
						flg=false;
					}
				}
				if(flg){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("supplierId", products.getSupplierId());
					map.put("types", 1);
					List<Protocol> pro = protocolService.getProtocolBySupplier(map);
					Protocol protocol=null;
					if(!CollectionUtil.collectionIsEmpty(pro)) {
						protocol=pro.get(0);
					}
					Suppliers supers = suppliersService.selectByKey(products.getSupplierId());
					int saveProduct = saveProduct(supers, protocol, products.getSku());
					if(saveProduct>0){
						//获取价格，获取图片
						int savePrice = savePrice(products.getSku(),supers);
						if(savePrice>0){
							saveImages(products.getSku(),supers);
							if(supers.getIsPrice()==0){
								productState(products.getSku(),supers);
								check(products.getSku(), supers.getId());
							}
						}
						
					}
				}
			}
		}
	}
	/**
	 * 
	 * @param type 3上架 4 下架
	 * @param supplierId
	 * @param content
	 */
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
	private boolean isNull(String str){
		if(str==null||"null".equals(str)){
			return true;
		}
		return false;
	}
	public void check(String skuId,Long supplierId) {
		Products pro = isProduct(skuId, supplierId);
		if(pro!=null) {
			if(pro.getStatus()==3) {
				if(isNull(pro.getBrandName())) {//判断商品型号或者品牌
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.MODEL_ERROR);
				}
				if(pro.getProtocolPrice()==null||pro.getMarketPrice()==null||pro.getProtocolPrice().compareTo(new BigDecimal("0"))==0){//判断价格
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.PRODUCT_PRICE);
				}
				if(pro.getProtocolPrice()!=null&&pro.getMarketPrice()!=null&&pro.getProtocolPrice().compareTo(pro.getMarketPrice())>0){//判断价格
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.PRICE_ERROR);
				}
				
				if(isNull(pro.getDetail())) {
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.PRODUCT_DETAIL);
				}
				if(isNull(pro.getCoverUrl())) {
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.PRODUCT_IMAGE);
				}
				if(isNull(pro.getCatalogName())) {
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, "品目没映射自动下架");
				}
				productsService.updateNotNull(pro);
			}
		}
	}
	public void productState(String sku,Suppliers sup){
		Products product = isProduct(sku, sup.getId());
		if(product!=null){
			ProductStateRes productStateApi = JdUtilApi.ProductStateApi(sku);
			if(productStateApi.isSuccess()){
				List<StateResultAttr> result = productStateApi.getResult();
				if(!CollectionUtil.collectionIsEmpty(result)){
					StateResultAttr stateResultAttr = result.get(0);
					if(stateResultAttr.getState()==1){//上架
						product.setStatus(3);
					}else if(stateResultAttr.getState()==0){
						product.setStatus(3);
					}
					productsService.updateNotNull(product);
				}
			}
		}
	}
	public void saveImages(String sku,Suppliers sup){
		Products product = isProduct(sku, sup.getId());
		if(product!=null){
			try {
				ProductImageRes productImageApi = JdUtilApi.ProductImageApi(sku);
				if(productImageApi.isSuccess()){
					List<ImageResultAttr> result = productImageApi.getResult();
					if(!CollectionUtil.collectionIsEmpty(result)){
						ImageResultAttr imageResultAttr = result.get(0);
						List<Image> images = imageResultAttr.getImages();
                        for (Image image : images) {
                        	ProductPics productPics = new ProductPics();
    						productPics.setProductId(product.getId());
    						productPics.setLarge("http://img13.360buyimg.com/n0/"+image.getPath());
    						productPics.setMedium("http://img13.360buyimg.com/n0/"+image.getPath());
    						productPics.setSource("http://img13.360buyimg.com/n0/"+image.getPath());
    						productPics.setPriority(Float.valueOf(image.getPosition()));
    						productPics.setIsDelete(0);
    						productPics.setCreatedAt(DateUtilEx.timeFormat(new Date()));
    						productPics.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
    						productPicsMapper.insert(productPics);
						}
					}
				}
			} catch (Exception e) {
				
			}
		}
	}
	public int  savePrice(String sku,Suppliers sup){
		Products product = isProduct(sku, sup.getId());
		if(product!=null){
			try {
				ProductPriceRes productPricesApi = JdUtilApi.ProductPricesApi(sku);
				if(productPricesApi.isSuccess()){
					List<PriceAttr> result = productPricesApi.getResult();
					if(!CollectionUtil.collectionIsEmpty(result)){
						PriceAttr priceAttr = result.get(0);
						product.setMarketPrice(priceAttr.getJdPrice());
						product.setProtocolPrice(priceAttr.getPrice());
						product.setPrice(priceAttr.getPrice());
						return productsService.updateNotNull(product);
					}
				}
			} catch (Exception e) {
				
			}
		
		}
		return 0;
		
	}
	public int saveProduct(Suppliers sup,Protocol protocol,String sku){
		int default_result=0;
		Products product = isProduct(sku, sup.getId());
		if(product==null){
			try {
				ProductRes productDetailApi = JdUtilApi.ProductDetailApi(sku);
				if(productDetailApi.isSuccess()){
					Products products=new Products();
					ProductOther po = (ProductOther) productDetailApi.getResult().getProduct();
					Brand brand = getBrandId(po);
					if(protocol!=null){
						products.setProtocolId(protocol.getId());
						products.setProtocolName(protocol.getName());
					}
					products.setSupplierId(sup.getId());
					products.setSupplierName(sup.getName());
					products.setName(po.getName());
					products.setSku(sku);
					products.setModel(po.getModel());
					products.setWeight(po.getWeight());
					products.setStatus(4);
					if(sup.getIsPrice()==0){
						products.setIsChangePrice(1);
					}else{
						products.setIsChangePrice(0);
					}
					products.setIsShow(3);
					products.setParam(po.getParam());
					products.setFactoryURL("https://item.jd.com/"+po.getSku()+".html"); //官网地址
					products.setMallBrandName(po.getBrandName());
					if( brand != null){
						products.setBrandId(brand.getId());
						products.setBrandName(brand.getName());
					}
					String category = po.getCategory();
					if(!StringUtils.isEmpty(category)){
						String[] split = category.split(";");
						products.setEmallCatalogeId(split[split.length-1]);
						Example example=new Example(EmallCatalogs.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("emallCode",sup.getCode());
						createCriteria.andEqualTo("emallId",sup.getId());
						createCriteria.andEqualTo("emallCatalogsId",split[split.length-1]);
						List<EmallCatalogs> ec = emallCatalogsMapper.selectByExample(example);
						if(!CollectionUtil.collectionIsEmpty(ec)){
							if(ec.size()==1){
								EmallCatalogs emallCatalogs = ec.get(0);
								if(emallCatalogs.getCatalogsId()!=null){
									PlatformCatalogs pc = platformCatalogsService.selectByKey(emallCatalogs.getCatalogsId());
									products.setCatalogId(emallCatalogs.getCatalogsId());
									products.setCatalogName(pc.getName());
								}
							}
						}
					}
					if(!StringUtils.isBlank(po.getImagePath())){
						products.setCoverUrl("http://img13.360buyimg.com/n0/"+po.getImagePath());
					}
					products.setDetail(po.getIntroduction());
					products.setWare(po.getWareQD());
					products.setProductArea(po.getProductArea());
					products.setUpc(po.getUpc());
					products.setAfterSaleService(po.getShouhou());
					products.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					products.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					products.setIsDelete(0);
					default_result=productsService.save(products);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return default_result;
	}
	/**
	 * 获取对应品牌
	 * */
	public Brand getBrandId(ProductOther result) {
		Brand return_brand=new Brand();
		List<Brand> list = brandMapper.findByMappingName(result.getBrandName());
		if(!CollectionUtils.isEmpty(list)){
			oth:
				for (Brand brand : list) {
					String mappingName = brand.getMappingName();
					String[] split = mappingName.split("\\|");
					for (String string : split) {
						if(string.equals(result.getBrandName())){
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
	public Products isProduct(String skuId, Long supplierId) {
		Example example=new Example(Products.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("supplierId", supplierId);
		createCriteria.andEqualTo("sku", skuId);
		createCriteria.andEqualTo("isDelete", 0);
		List<Products> pro_list = productsService.selectByExample(example);
		if(!CollectionUtil.collectionIsEmpty(pro_list)){
			return pro_list.get(0);
		}else{
			return null;
		}

	}
}
