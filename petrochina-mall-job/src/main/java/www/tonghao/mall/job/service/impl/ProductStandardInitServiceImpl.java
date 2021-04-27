package www.tonghao.mall.job.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.standard.attwrap.Image;
import www.tonghao.mall.api.standard.attwrap.ImageResultAttr;
import www.tonghao.mall.api.standard.attwrap.PriceAttr;
import www.tonghao.mall.api.standard.attwrap.ProductDetailAttr;
import www.tonghao.mall.api.standard.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.standard.resultwrap.ProductImageRes;
import www.tonghao.mall.api.standard.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.standard.resultwrap.ProductSkusRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.ProductStandardInitService;
import www.tonghao.mall.job.util.StandardUtilApi;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.EmallCatalogs;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductPics;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.mapper.EmallCatalogsMapper;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.mapper.ProductPicsMapper;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.ProtocolService;
import www.tonghao.service.common.service.SuppliersService;

@Service("productStandardInitService")
public class ProductStandardInitServiceImpl implements ProductStandardInitService {

	private static Log logger = LogFactory.getLog(ProductStandardInitServiceImpl.class);
	@Autowired
	private SuppliersService suppliersService;
	@Autowired
	private EmallCatalogsMapper emallCatalogsMapper;
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private ProtocolService protocolService;
	
	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private ProductPicsMapper productPicsMapper;
	
	@Override
	public void productStandardInitJob() {
		Suppliers sup = new Suppliers();
		sup.setCode(Constant.MALL_DL_CODE);
		Suppliers supplier = suppliersService.selectEntityOne(sup);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("supplierId", supplier.getId());
		map.put("types", 1);
//		List<Protocol> pro = protocolService.getProtocolBySupplier(map);
//		Protocol protocol=null;
//		if(!CollectionUtil.collectionIsEmpty(pro)) {
//			protocol=pro.get(0);
//		}
//		if(supplier!=null){
//			Example example=new Example(EmallCatalogs.class);
//			Criteria createCriteria = example.createCriteria();
//			createCriteria.andEqualTo("emallCode", supplier.getCode());
//			createCriteria.andEqualTo("emallId", supplier.getId());
//			createCriteria.andIsNotNull("catalogsId");
//			List<EmallCatalogs> ec = emallCatalogsMapper.selectByExample(example);
//			if(!CollectionUtil.collectionIsEmpty(ec)){
//				for (EmallCatalogs emallCatalogs : ec) {
//					getSku(supplier,emallCatalogs,protocol);
//				}
//			}
//		}
	}
	public void getSku(Suppliers supplier,EmallCatalogs emallCatalogs,Protocol protocol){
		if(emallCatalogs.getCatalogsId()!=null){
			ProductSkusRes productSku = StandardUtilApi.productSkuApi(supplier.getCode(), emallCatalogs.getEmallCatalogsId());
			if(productSku.isSuccess()){
				List<String> result = productSku.getResult();
				if(!CollectionUtil.collectionIsEmpty(result)){
					for (String sku : result) {
						Products product = isProduct(sku, supplier.getId());
						if(product==null){
							int saveProduct = saveProduct(supplier,sku,emallCatalogs,protocol);
							if(saveProduct>0){
								updateProduct(supplier,sku,emallCatalogs,protocol);
							}
						}
					}
				}
			}
		}
	}
	public void updateProduct(Suppliers supplier,String sku,EmallCatalogs emallCatalogs,Protocol protocol){
		Products product = isProduct(sku, supplier.getId());
		if(product!=null){
			try {
				ProductPriceRes productPrices = StandardUtilApi.productPricesApi(supplier.getCode(), sku);
				if(productPrices.isSuccess()){
					List<PriceAttr> result = productPrices.getResult();
					if(!CollectionUtil.collectionIsEmpty(result)){
						PriceAttr pa = result.get(0);
						BigDecimal price = pa.getPrice();
						BigDecimal mall_price = pa.getMall_price();
						product.setMarketPrice(mall_price);
						product.setProtocolPrice(price);
						product.setPrice(price);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(sku+"获取价格接口异常");
			}
			
			try {
				ProductImageRes productImage = StandardUtilApi.productImageApi(supplier.getCode(), sku);
				if(productImage.isSuccess()){
					List<ImageResultAttr> result = productImage.getResult();
					if(!CollectionUtil.collectionIsEmpty(result)){
						ImageResultAttr imageResultAttr = result.get(0);
						if(imageResultAttr!=null){
							List<Image> images = imageResultAttr.getImages();
							ProductPics productPics=null;
							if(!CollectionUtil.collectionIsEmpty(images)){
								for (Image image : images) {
									productPics = new ProductPics();
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
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(sku+"获取图片接口异常");
			}
			productsService.updateNotNull(product);
		}
	}
	
	
	public int saveProduct(Suppliers supplier,String sku,EmallCatalogs emallCatalogs,Protocol protocol){
		try {
			ProductDetailRes productDetail = StandardUtilApi.productDetailApi(supplier.getCode(), sku);
			if(productDetail.isSuccess()){
				ProductDetailAttr result = productDetail.getResult();
				if(result!=null){
					Products products=new Products();
					products.setSupplierId(supplier.getId());
					products.setSupplierName(supplier.getName());
					products.setName(result.getName());
					products.setSku(result.getSku());
					products.setModel(result.getModel());
					products.setWeight(result.getWeight());
					products.setStatus(4);
					products.setIsChangePrice(0);
					products.setParam(result.getParam());
					products.setDetail(result.getIntroduction());
					products.setWare(result.getWare());
					products.setProductArea(result.getProductArea());
					products.setUpc(result.getUpc());
					products.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					products.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					products.setIsDelete(0);
					products.setCoverUrl(result.getImage_path());
					products.setFactoryURL(result.getUrl());
					PlatformCatalogs pf = platformCatalogsMapper.selectByPrimaryKey(emallCatalogs.getCatalogsId());
					if(pf!=null) {
						products.setCatalogId(pf.getId());
						products.setCatalogName(pf.getName());
						products.setGovCatalogId(pf.getCatalogId());
					}
					
					if(protocol!=null) {
						products.setProtocolId(protocol.getId());
						products.setProtocolName(protocol.getName());
					}
					
					products.setMallBrandName(result.getBrand_name());
					Brand brand = getBrandId(result.getBrand_name());
					if(brand!=null) {
						products.setBrandId(brand.getId());
						products.setBrandName(brand.getName());
					}
				
					return productsService.saveSelective(products);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(sku+"保存失败");
		}
		return 0;
	}
	
	/**
	 * 获取对应品牌
	 * */
	public Brand getBrandId(String mallBrandName) {
		Brand return_brand=new Brand();
		List<Brand> list = brandMapper.findByMappingName(mallBrandName);
		if(!CollectionUtil.collectionIsEmpty(list)){
			oth:
				for (Brand brand : list) {
					String mappingName = brand.getMappingName();
					String[] split = mappingName.split("\\|");
					for (String string : split) {
						if(string.equals(mallBrandName)){
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
}
