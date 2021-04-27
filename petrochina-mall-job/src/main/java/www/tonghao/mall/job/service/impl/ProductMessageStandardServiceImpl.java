package www.tonghao.mall.job.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.standard.attwrap.Image;
import www.tonghao.mall.api.standard.attwrap.ImageResultAttr;
import www.tonghao.mall.api.standard.attwrap.Message;
import www.tonghao.mall.api.standard.attwrap.MessageAttr;
import www.tonghao.mall.api.standard.attwrap.MessagePrice;
import www.tonghao.mall.api.standard.attwrap.MessageProductParam;
import www.tonghao.mall.api.standard.attwrap.MessageProductSku;
import www.tonghao.mall.api.standard.attwrap.MessageState;
import www.tonghao.mall.api.standard.attwrap.PriceAttr;
import www.tonghao.mall.api.standard.attwrap.ProductDetailAttr;
import www.tonghao.mall.api.standard.resultwrap.MessageDelRes;
import www.tonghao.mall.api.standard.resultwrap.MessageRes;
import www.tonghao.mall.api.standard.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.standard.resultwrap.ProductImageRes;
import www.tonghao.mall.api.standard.resultwrap.ProductPriceRes;
import www.tonghao.mall.core.XmlConfig;
import www.tonghao.mall.job.service.ProductMessageStandardService;
import www.tonghao.mall.job.util.StandardUtilApi;
import www.tonghao.mall.job.util.StbUtilApi;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductPics;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.entity.SkuLogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.mapper.ProductPicsMapper;
import www.tonghao.service.common.mapper.SkuLogsMapper;
import www.tonghao.service.common.mapper.UpperLowerHistoryMapper;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.ProtocolService;
import www.tonghao.service.common.service.SuppliersService;

@Service("productMessageStandardService")
public class ProductMessageStandardServiceImpl implements ProductMessageStandardService {
	private static Log logger = LogFactory.getLog(ProductMessageStandardServiceImpl.class);
	@Autowired
	private SuppliersService suppliersService;
	@Autowired
	private ProtocolService protocolService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private SkuLogsMapper skuLogsMapper;
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private UpperLowerHistoryMapper upperLowerHistoryMapper;
	@Autowired
	private ProductPicsMapper productPicsMapper;
	
	@Override
	public void executeProductMessageStandardJob() {
		List<Node> node = XmlConfig.getNode("/api_config/common/small_mapping/mall");
		for (Node node2 : node) {
			Element element = (Element)node2;
			String emallcode = element.attributeValue("code");
			Suppliers sup = new Suppliers();
			sup.setCode(emallcode);
			Suppliers suppliers = suppliersService.selectEntityOne(sup);
			if(suppliers == null||suppliers.getCode().equals(Constant.JD_CODE)){
				continue;
			}
			
			Suppliers supplier = suppliersService.selectEntityOne(sup);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("supplierId", supplier.getId());
			map.put("types", 1);
//			List<Protocol> pro = protocolService.getProtocolBySupplier(map);
//			Protocol protocol = null;
//			if (!CollectionUtil.collectionIsEmpty(pro)) {
//				protocol = pro.get(0);
//			}
//
//			// 添加消息
//			messageSave(supplier, protocol);
//			// 修改消息
//			messageUpdate(supplier, protocol);
//			// 删除消息
//			messageDelete(supplier);
//			// 上下架状态消息
//			messageState(supplier, protocol);
//			// 价格消息
//			messagePrice(supplier, protocol);
			
			
		}
	}

	public void messagePrice(Suppliers supplier,Protocol protocol){
		MessageRes message = StandardUtilApi.getMessage(supplier.getCode(), 2);
		if(message.isSuccess()){
			List<MessageAttr> attr = message.getAttr();
			if(!CollectionUtil.collectionIsEmpty(attr)){
				for (MessageAttr messageAttr : attr) {
					MessagePrice messagePrice=(MessagePrice) messageAttr.getMessage();
					Products product = isProduct(messagePrice.getSkuId(), supplier.getId());
					if(product!=null){
						BigDecimal marketPrice = product.getMarketPrice();
						BigDecimal protocolPrice = product.getProtocolPrice();
						addSkuLogs(supplier.getCode(), messageAttr.getId(), messagePrice.getSkuId(), messageAttr.getTime(), 6);
						ProductPriceRes productPricesApi = StandardUtilApi.productPricesApi(supplier.getCode(), messagePrice.getSkuId());
					    if(productPricesApi.isSuccess()){
					    	List<PriceAttr> result = productPricesApi.getResult();
					    	if(!CollectionUtil.collectionIsEmpty(result)){
					    		PriceAttr priceAttr = result.get(0);
					    		product.setMarketPrice(priceAttr.getMall_price());
								product.setProtocolPrice(priceAttr.getPrice());
								if(marketPrice.compareTo(priceAttr.getMall_price())!=0||protocolPrice.compareTo(priceAttr.getPrice())!=0){
									product.setStatus(4);
									product.setIsChangePrice(0);
									saveUpperLowerHistory(product.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+priceAttr.getMall_price()+",协议价："+priceAttr.getPrice());
								}
					    	}
					    	MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
					    	
					    }else{
					    	MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
					    }
					    if (product.getStatus() == 3) {
							if (product.getPrice() == null || product.getMarketPrice() == null
									|| product.getPrice().compareTo(new BigDecimal("0")) == 0) {// 判断价格
								product.setStatus(4);
								saveUpperLowerHistory(product.getId(), 4, supplier.getId(), Constant.PRODUCT_PRICE);
							}
							if (product.getPrice() != null && product.getMarketPrice() != null
									&& product.getProtocolPrice().compareTo(product.getMarketPrice()) > 0) {// 判断价格
								product.setStatus(4);
								saveUpperLowerHistory(product.getId(), 4, supplier.getId(), Constant.PRICE_ERROR);
							}
						}
						productsService.updateNotNull(product);
					}else{
						ProductDetailRes productDetail = StandardUtilApi.productDetailApi(supplier.getCode(),messagePrice.getSkuId());
						if(productDetail.isSuccess()){
							int saveProduct = saveProduct(productDetail.getResult(), supplier, protocol);
						    if(saveProduct>0){
						    	int updateProducts = updateProduct(messagePrice.getSkuId(), supplier,false);
						    }
						    MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
						}else{
							MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
						}
					}
				}
			}
		}
	}
	public void messageState(Suppliers supplier,Protocol protocol){
		MessageRes message = StandardUtilApi.getMessage(supplier.getCode(), 4);
		if(message.isSuccess()){
			List<MessageAttr> attr = message.getAttr();
			if(!CollectionUtil.collectionIsEmpty(attr)){
				for (MessageAttr messageAttr : attr) {
					MessageState messageState=(MessageState) messageAttr.getMessage();
					int state = messageState.getState();
					Products product = isProduct(messageState.getSkuId(), supplier.getId());
					if (product != null) {// 存在才会去更新状态，不存在需要添加
						if(state==2){//下架
							addSkuLogs(supplier.getCode(), messageAttr.getId(), messageState.getSkuId(), messageAttr.getTime(),
									5);
							product.setStatus(4);
							saveUpperLowerHistory(product.getId(), 4, supplier.getId(), Constant.EMALL_ERROR);
							int updateNotNull = productsService.updateNotNull(product);
							if(updateNotNull>0){
								MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
								}
							}
						}else if(state==1){
							addSkuLogs(supplier.getCode(), messageAttr.getId(), messageState.getSkuId(), messageAttr.getTime(),
									4);
							MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					}else{
						ProductDetailRes productDetail = StandardUtilApi.productDetailApi(supplier.getCode(),messageState.getSkuId());
						if(productDetail.isSuccess()){
							int saveProduct = saveProduct(productDetail.getResult(), supplier, protocol);
						    if(saveProduct>0){
						    	int updateProducts = updateProduct(messageState.getSkuId(), supplier,false);
						    }
						    MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
						}else{
							MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
						}
						
					}
					
				}
				
			}
		}
		
	}
	public void messageDelete(Suppliers supplier){
		MessageRes message = StandardUtilApi.getMessage(supplier.getCode(), 6);
		if(message.isSuccess()){
			List<MessageAttr> attr = message.getAttr();
			if(!CollectionUtil.collectionIsEmpty(attr)){
				for (MessageAttr messageAttr : attr) {
					MessageProductSku mp = (MessageProductSku)messageAttr.getMessage();
					int state = mp.getState();
					if(state==2){//删除
						addSkuLogs(supplier.getCode(), messageAttr.getId(), mp.getSkuId(), messageAttr.getTime(), 2);
						Products product = isProduct(mp.getSkuId(), supplier.getId());
						if (product != null) {
							product.setIsDelete(1);
							product.setStatus(4);
							int updateNotNull = productsService.updateNotNull(product);
							if (updateNotNull > 0) {
								MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
								}
							}
						}else{
							MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					}
				}
			}
		}
	}
	public void messageUpdate(Suppliers supplier,Protocol protocol){
		MessageRes message = StandardUtilApi.getMessage(supplier.getCode(), 16);
		if(message.isSuccess()){
			List<MessageAttr> attr = message.getAttr();
			if(!CollectionUtil.collectionIsEmpty(attr)){
				for (MessageAttr messageAttr : attr) {
					MessageProductParam mess = (MessageProductParam) messageAttr.getMessage();
					String skuId = mess.getSkuId();
					addSkuLogs(supplier.getCode(), messageAttr.getId(), skuId, messageAttr.getTime(), 3);
					Products product = isProduct(skuId, supplier.getId());
					if (product != null) {// 存在才修改，
						BigDecimal marketPrice = product.getMarketPrice();
						BigDecimal protocolPrice = product.getProtocolPrice();
						ProductDetailRes productDetailApi = StandardUtilApi.productDetailApi(supplier.getCode(), skuId);
						if(productDetailApi.isSuccess()){
							int updateProduct = updateProduct(product, productDetailApi.getResult());
							if(updateProduct>0){
								MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
								}
								int updateProducts = updateProduct(product.getSku(), supplier,true);
								if (updateProducts > 0) {
									Products new_pro = isProduct(product.getSku(), supplier.getId());
									if(new_pro.getStatus()==3){
										if(new_pro.getMarketPrice().compareTo(marketPrice)!=0||new_pro.getProtocolPrice().compareTo(protocolPrice)!=0){
											new_pro.setStatus(4);
											new_pro.setIsChangePrice(0);
											saveUpperLowerHistory(new_pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+new_pro.getMarketPrice()+",协议价："+new_pro.getProtocolPrice());
											productsService.updateNotNull(new_pro);
										}
									}
								}
							}
							
						}else{
							MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					}else{
						ProductDetailRes productDetail = StandardUtilApi.productDetailApi(supplier.getCode(),skuId);
						if(productDetail.isSuccess()){
							int saveProduct = saveProduct(productDetail.getResult(), supplier, protocol);
						    if(saveProduct>0){
						    	int updateProducts = updateProduct(skuId, supplier,false);
						    	if(updateProducts>0){
						    		MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
									if (delMessage.isSuccess()) {
										delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
									}
						    	}
						    }
						}else{
							MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					}
				}
			}
		}
		
		
	}
	
	
	
	public void messageSave(Suppliers supplier,Protocol protocol){
		MessageRes message = StandardUtilApi.getMessage(supplier.getCode(), 6);
		if(message.isSuccess()){
			List<MessageAttr> attr = message.getAttr();
			if(!CollectionUtil.collectionIsEmpty(attr)){
				for (MessageAttr messageAttr : attr) {
					MessageProductSku mp = (MessageProductSku)messageAttr.getMessage();
					int state = mp.getState();
					if(state==1){//添加
						// 保存本地消息
						addSkuLogs(supplier.getCode(), messageAttr.getId(), mp.getSkuId(), messageAttr.getTime(), 1);
						Products product = isProduct(mp.getSkuId(), supplier.getId());
						if(product==null){//添加商品
							ProductDetailRes productDetail = StandardUtilApi.productDetailApi(supplier.getCode(), mp.getSkuId());
							if(productDetail.isSuccess()){
								int saveProduct = saveProduct(productDetail.getResult(), supplier, protocol);
							    if(saveProduct>0){
							    	int result = updateProduct(mp.getSkuId(), supplier,false);
							        if(result>0){
							        	MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
										if (delMessage.isSuccess()) {
											delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
										}
							        }
							    }
							}else{
								MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
								}
							}
						}else{//修改商品
							BigDecimal marketPrice = product.getMarketPrice();//旧的的市场价
							BigDecimal protocolPrice = product.getProtocolPrice();//旧的协议价
							ProductDetailRes productDetail = StandardUtilApi.productDetailApi(supplier.getCode(), mp.getSkuId());
							if(productDetail.isSuccess()){
								int updateProduct = updateProduct(product, productDetail.getResult());
								if(updateProduct>0){
									int result = updateProduct(mp.getSkuId(), supplier,false);
									if(result>0){
										Products new_pro = isProduct(product.getSku(), supplier.getId());
										if(new_pro.getStatus()==3){
											if(new_pro.getMarketPrice().compareTo(marketPrice)!=0||new_pro.getProtocolPrice().compareTo(protocolPrice)!=0){
												new_pro.setStatus(4);
												new_pro.setIsChangePrice(0);
												saveUpperLowerHistory(new_pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+new_pro.getMarketPrice()+",协议价："+new_pro.getProtocolPrice());
												productsService.updateNotNull(new_pro);
											}
										}
										MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
										if (delMessage.isSuccess()) {
											delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
										}
									}
								}
							}else{
								MessageDelRes delMessage = StandardUtilApi.messageDel(supplier.getCode(),messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
								}
							}
						}
					}
				}
			}
		}
	}
	
	public int updateProduct(String skuId, Suppliers supplier,boolean isUpdate) {
		Products product = isProduct(skuId, supplier.getId());
		if (product != null) {
			try {
				ProductPriceRes productPricesApi = StandardUtilApi.productPricesApi(supplier.getCode(), skuId);
				if(productPricesApi.isSuccess()){
					List<PriceAttr> result = productPricesApi.getResult();
					if (!CollectionUtil.collectionIsEmpty(result)) {
						PriceAttr priceAttr = result.get(0);
						product.setMarketPrice(priceAttr.getMall_price());
						product.setProtocolPrice(priceAttr.getPrice());
						if(!isUpdate){//是否修改  修改的时候不去修改售价
							product.setPrice(priceAttr.getPrice());
							
						}
					}
				}else{
					product.setStatus(4); 
					saveUpperLowerHistory(product.getId(), 4, supplier.getId(), productPricesApi.getDesc());
				}
			} catch (Exception e) {
				product.setStatus(4); 
				saveUpperLowerHistory(product.getId(), 4, supplier.getId(), "价格接口调用异常");
				logger.info(skuId + "价格接口失败");
			}
			try {
				Example old_example=new Example(ProductPics.class);
				Criteria old_createCriteria = old_example.createCriteria();
				old_createCriteria.andEqualTo("productId", product.getId());
				List<ProductPics> old_pic = productPicsMapper.selectByExample(old_example);
				if(!CollectionUtil.collectionIsEmpty(old_pic)) {
					for (ProductPics productPics : old_pic) {
						productPicsMapper.deleteByPrimaryKey(productPics.getId());
					}
				}
				ProductImageRes productImageApi = StandardUtilApi.productImageApi(supplier.getCode(), product.getSku());
				if(productImageApi.isSuccess()){
					List<ImageResultAttr> result = productImageApi.getResult();
					if(!CollectionUtil.collectionIsEmpty(result)){
						ImageResultAttr imageResultAttr = result.get(0);
						List<Image> images = imageResultAttr.getImages();
						if(!CollectionUtil.collectionIsEmpty(images)){
							for (Image image : images) {
								ProductPics productPics = new ProductPics();
								productPics.setProductId(product.getId());
								productPics.setLarge(image.getPath());
								productPics.setMedium(image.getPath());
								productPics.setSource(image.getPath());
								productPics.setPriority(Float.parseFloat(image.getOrder() + ""));
								productPics.setIsDelete(0);
								productPics.setCreatedAt(DateUtilEx.timeFormat(new Date()));
								productPics.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
								productPicsMapper.insert(productPics);
							}
						}
					}
				}else{
					product.setStatus(4);
					saveUpperLowerHistory(product.getId(), 4, supplier.getId(), productImageApi.getDesc());
				}
			} catch (Exception e) {
				product.setStatus(4);
				saveUpperLowerHistory(product.getId(), 4, supplier.getId(), "图片详情接口调用异常");
				logger.info(skuId + "详情图片接口失败");
			}
		}
		return productsService.updateNotNull(product);
	}
	public int saveProduct(ProductDetailAttr attr, Suppliers supplier, Protocol protocol) {
		try {
			Products pt = isProduct(attr.getSku(), supplier.getId());
			if(pt==null) {
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
				selProducts.setFactoryURL(attr.getUrl());
				PlatformCatalogs pf = platformCatalogsService.selectByKey(Long.parseLong(attr.getCategory()));
				if (pf != null) {
					selProducts.setCatalogId(pf.getId());
					selProducts.setCatalogName(pf.getName());
					selProducts.setGovCatalogId(pf.getCatalogId());
				}

				if (protocol != null) {
					selProducts.setProtocolId(protocol.getId());
					selProducts.setProtocolName(protocol.getName());
				}

				selProducts.setMallBrandName(attr.getBrand_name());
				Brand brand = getBrandId(attr.getBrand_name());
				if (brand != null) {
					selProducts.setBrandId(brand.getId());
					selProducts.setBrandName(brand.getName());
				}
				return productsService.save(selProducts);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(attr.getSku() + "保存失败");
		}
		return 0;
	}
       public int updateProduct(Products product, ProductDetailAttr attr) {
		try {
			product.setName(attr.getName());
			product.setModel(attr.getModel());
			product.setWeight(attr.getWeight());
			product.setParam(attr.getParam());
			product.setDetail(attr.getIntroduction());
			product.setWare(attr.getWare());
			product.setProductArea(attr.getProductArea());
			product.setUpc(attr.getUpc());
			product.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			product.setCoverUrl(attr.getImage_path());
			PlatformCatalogs pf = platformCatalogsService.selectByKey(Long.parseLong(attr.getCategory()));
			if (pf != null) {
				product.setCatalogId(pf.getId());
				product.setCatalogName(pf.getName());
				product.setGovCatalogId(pf.getCatalogId());
			}
			product.setMallBrandName(attr.getBrand_name());
			Brand brand = getBrandId(attr.getBrand_name());
			if (brand != null) {
				product.setBrandId(brand.getId());
				product.setBrandName(brand.getName());
			}
			return productsService.updateNotNull(product);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(attr.getSku() + "保存失败");
		}
		return 0;
	}
   	/**
   	 * 
   	 * @param type
   	 *            3上架 4 下架
   	 * @param supplierId
   	 * @param content
   	 */
   	public void saveUpperLowerHistory(Long productId, int type, Long supplierId, String content) {
   		UpperLowerHistory history = new UpperLowerHistory();
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
	 */
	public Brand getBrandId(String  brand_name) {
		Brand return_brand = new Brand();
		List<Brand> list = brandMapper.findByMappingName(brand_name);
		if (!CollectionUtil.collectionIsEmpty(list)) {
			oth: for (Brand brand : list) {
				String mappingName = brand.getMappingName();
				String[] split = mappingName.split("\\|");
				for (String string : split) {
					if (string.equals(brand_name)) {
						return_brand = brand;
						break oth;
					}
				}
			}
			return return_brand;
		} else {
			return null;
		}
	}
	public Products isProduct(String skuId, Long supplierId) {
		Products products = null;
		try {
			Products entity = new Products();
			entity.setSupplierId(supplierId);
			entity.setSku(skuId);
			entity.setIsDelete(0);
			;
			products = productsService.selectEntityOne(entity);
		} catch (Exception e) {
			logger.info(skuId + "出现多个商品,入库失败");
		}
		return products;

	}
	// 1 添加，2删除，3修改，4上架，5下架,6价格,7主图
		public void addSkuLogs(String code, String messId, String messSku, String messTime, Integer type) {
			SkuLogs logs = new SkuLogs();
			logs.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			logs.setIsDelete(0);
			logs.setMallCode(code);
			logs.setMessId(messId);
			logs.setMessSku(messSku);
			logs.setMessTime(messTime);
			logs.setType(type);
			skuLogsMapper.insert(logs);
		}
		
		public void delSkuLogs(String messId, String code) {
			Example example = new Example(SkuLogs.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("messId", messId);
			createCriteria.andEqualTo("mallCode", code);
			List<SkuLogs> selectByExample = skuLogsMapper.selectByExample(example);
			if (!CollectionUtil.collectionIsEmpty(selectByExample)) {
				for (SkuLogs skuLogs : selectByExample) {
					skuLogs.setIsDelete(1);
					skuLogsMapper.updateByPrimaryKeySelective(skuLogs);
				}
			}
		}
}
