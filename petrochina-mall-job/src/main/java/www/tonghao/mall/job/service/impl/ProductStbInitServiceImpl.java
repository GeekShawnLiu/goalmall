package www.tonghao.mall.job.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.stb.attwrap.Image;
import www.tonghao.mall.api.stb.attwrap.ProductDetailAttr;
import www.tonghao.mall.api.stb.attwrap.ProductImageAttr;
import www.tonghao.mall.api.stb.attwrap.ProductPoolAttr;
import www.tonghao.mall.api.stb.attwrap.ProductPriceAttr;
import www.tonghao.mall.api.stb.attwrap.ProductStateAttr;
import www.tonghao.mall.api.stb.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.stb.resultwrap.ProductImageRes;
import www.tonghao.mall.api.stb.resultwrap.ProductPoolRes;
import www.tonghao.mall.api.stb.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.stb.resultwrap.ProductSkusRes;
import www.tonghao.mall.api.stb.resultwrap.ProductStateRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.ProductStbInitService;
import www.tonghao.mall.job.util.StbUtilApi;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductPics;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.entity.SupplierProtocol;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.mapper.ProductPicsMapper;
import www.tonghao.service.common.mapper.UpperLowerHistoryMapper;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.ProtocolService;
import www.tonghao.service.common.service.SupplierProtocolService;
import www.tonghao.service.common.service.SuppliersService;


/**
 * 史泰博商品初始化
 * @author 李万林
 *
 */
@Service("productStbInitService")
public class ProductStbInitServiceImpl implements ProductStbInitService {
	private static Log logger = LogFactory.getLog(ProductStbInitServiceImpl.class);
	
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
	public void executeStbInitJob() {
		Suppliers sup = new Suppliers();
		sup.setCode(Constant.MALL_STB_CODE);
		Suppliers supplier = suppliersService.selectEntityOne(sup);
		if(supplier!=null) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("supplierId", supplier.getId());
			map.put("types", 1);
//			List<Protocol> pro = protocolService.getProtocolBySupplier(map);
//			Protocol protocol=null;
//			if(!CollectionUtil.collectionIsEmpty(pro)) {
//				protocol=pro.get(0);
//			}
//			ProductPoolRes productPoolApi = StbUtilApi.ProductPoolApi();//获取商品池
//			if(productPoolApi.isSuccess()) {
//				List<ProductPoolAttr> productPoolAttrs = productPoolApi.getProductPoolAttrs();
//				if(!CollectionUtil.collectionIsEmpty(productPoolAttrs)) {
//					for (ProductPoolAttr productPoolAttr : productPoolAttrs) {
//						pool(productPoolAttr.getId(),supplier,protocol);
//					}
//				}
//			}
		}
	}

	public void pool(String poolId,Suppliers supplier,Protocol protocol) {
		ProductSkusRes productSku = StbUtilApi.productSku(poolId);//获取sku
		if(productSku.isSuccess()) {
			List<String> skus = productSku.getSkus();
			if(!CollectionUtil.collectionIsEmpty(skus)) {
				for (String skuId : skus) {
					Products pro = isProduct(skuId, supplier.getId());
					if(pro==null) { //添加商品
						ProductDetailRes productDetail = StbUtilApi.productDetail(skuId);//获取商品详情
						if(productDetail.isSuccess()) {
							int saveProduct = saveProduct(productDetail.getAttr(),supplier,protocol);
							if(saveProduct>0) {//保存成功
								int result = updateProduct(skuId, supplier.getId());
								/*if(result>0) {
									check(skuId, supplier.getId());
								}*/
							}
						}
					}else {//修改商品
						
					}
				}
			}
		}
	}
	
	public void check(String skuId,Long supplierId) {
		Products pro = isProduct(skuId, supplierId);
		if(pro!=null) {
			if(pro.getStatus()==3) {
				if(isNull(pro.getBrandName())||isNull(pro.getModel())) {//判断商品型号或者品牌
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
				productsService.updateNotNull(pro);
			}
		}
	}
	public int updateProduct(String skuId,Long supplierId) {
		Products product = isProduct(skuId, supplierId);
		if(product!=null) {
			/*try {
				ProductStateRes productStateApi = StbUtilApi.ProductStateApi(skuId);//上下架状态
				if(productStateApi.isSuccess()) {
					List<ProductStateAttr> list = productStateApi.getList();
					if(!CollectionUtil.collectionIsEmpty(list)) {
						ProductStateAttr productStateAttr = list.get(0);
						int state = productStateAttr.getState();
						if(state==0) {
							product.setStatus(4);
							saveUpperLowerHistory(product.getId(), 4, supplierId, Constant.EMALL_ERROR);
						}else if(state==1){
							product.setStatus(3);
							saveUpperLowerHistory(product.getId(), 3, supplierId, Constant.EMALL_SUCCESS);
						}
						
					}
				}
			} catch (Exception e) {
				product.setStatus(4);
				saveUpperLowerHistory(product.getId(), 4, supplierId, "上下架接口调用异常，自动下架");
				logger.info(skuId+"上下架状态接口失败");
			}*/
			
			try {
				ProductPriceRes productPriceApi = StbUtilApi.ProductPriceApi(skuId);
				if(productPriceApi.isSuccess()) {
					List<ProductPriceAttr> attrs = productPriceApi.getAttrs();
					if(!CollectionUtil.collectionIsEmpty(attrs)) {
						ProductPriceAttr productPriceAttr = attrs.get(0);
						product.setPrice(productPriceAttr.getPrice());
						product.setMarketPrice(productPriceAttr.getMall_price());
						product.setProtocolPrice(productPriceAttr.getPrice());
					}
				}
			} catch (Exception e) {
				/*product.setStatus(4);*/
				saveUpperLowerHistory(product.getId(), 4, supplierId, "价格接口调用异常");
				logger.info(skuId+"价格接口失败");
			}
			
            try {
            	ProductImageRes productImageApi = StbUtilApi.ProductImageApi(skuId);
            	if(productImageApi.isSuccess()) {
            		List<ProductImageAttr> imagesList = productImageApi.getImagesList();
            		if(!CollectionUtil.collectionIsEmpty(imagesList)) {
            			ProductImageAttr productImageAttr = imagesList.get(0);
            			List<Image> images = productImageAttr.getImages();
            			if(!CollectionUtil.collectionIsEmpty(images)) {
            				for (Image image : images) {
            					ProductPics productPics = new ProductPics();
								productPics.setProductId(product.getId());
								productPics.setLarge(image.getPath());
								productPics.setMedium(image.getPath());
								productPics.setSource(image.getPath());
								productPics.setPriority(Float.parseFloat(image.getOrder()+""));
								productPics.setIsDelete(0);
								productPics.setCreatedAt(DateUtilEx.timeFormat(new Date()));
								productPics.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
								productPicsMapper.insert(productPics);
							}
            			}
            		}
            	}
			} catch (Exception e) {
				/*product.setStatus(4);*/
				saveUpperLowerHistory(product.getId(), 4, supplierId, "图片详情接口调用异常");
				logger.info(skuId+"详情图片接口失败");
			} 
		}
		return productsService.updateNotNull(product);
	}
	
	public int saveProduct(ProductDetailAttr attr,Suppliers supplier,Protocol protocol) {
		try {
			Products selProducts = new Products();
			selProducts.setSupplierId(supplier.getId());
			selProducts.setSupplierName(supplier.getName());
			selProducts.setName(attr.getName());
			selProducts.setSku(attr.getSku());
			selProducts.setModel(attr.getModel());
			selProducts.setWeight(attr.getWeight());
			selProducts.setStatus(4);
			selProducts.setIsChangePrice(0);
			selProducts.setParam(attr.getParam());
			selProducts.setDetail(attr.getIntroduction());
			selProducts.setWare(attr.getWare());
			selProducts.setProductArea(attr.getProductArea());
			selProducts.setUpc(attr.getUpc());
			selProducts.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			selProducts.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			selProducts.setIsDelete(0);
			selProducts.setCoverUrl(attr.getImage_path());
			selProducts.setFactoryURL("http://www.staples.cn/product/"+attr.getSku());
			PlatformCatalogs pf = platformCatalogsService.selectByKey(Long.parseLong(attr.getCategory()));
			if(pf!=null) {
				selProducts.setCatalogId(pf.getId());
				selProducts.setCatalogName(pf.getName());
				selProducts.setGovCatalogId(pf.getCatalogId());
			}
			
			if(protocol!=null) {
				selProducts.setProtocolId(protocol.getId());
				selProducts.setProtocolName(protocol.getName());
			}
			
			selProducts.setMallBrandName(attr.getBrand_name());
			Brand brand = getBrandId(attr);
			if(brand!=null) {
				selProducts.setBrandId(brand.getId());
				selProducts.setBrandName(brand.getName());
			}
			return productsService.save(selProducts);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(attr.getSku()+"保存失败");
		}
		return 0;
	}
	
	
	public Products isProduct(String skuId,Long supplierId) {
		Products products=null;
		try {
			Products entity=new Products();
			entity.setSupplierId(supplierId);
			entity.setSku(skuId);
			entity.setIsDelete(0);;
			products = productsService.selectEntityOne(entity);
		} catch (Exception e) {
			logger.info(skuId+"出现多个商品,入库失败");
		}
		return products;
		
	}
	/**
	 * 获取对应品牌
	 * */
	public Brand getBrandId(ProductDetailAttr attr) {
		Brand return_brand=new Brand();
		List<Brand> list = brandMapper.findByMappingName(attr.getBrand_name());
		if(!CollectionUtil.collectionIsEmpty(list)){
			oth:
				for (Brand brand : list) {
					String mappingName = brand.getMappingName();
					String[] split = mappingName.split("\\|");
					for (String string : split) {
						if(string.equals(attr.getBrand_name())){
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
}
