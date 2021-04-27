package www.tonghao.mall.job.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.stb.attwrap.Image;
import www.tonghao.mall.api.stb.attwrap.Message;
import www.tonghao.mall.api.stb.attwrap.MessageAttr;
import www.tonghao.mall.api.stb.attwrap.MessageSku;
import www.tonghao.mall.api.stb.attwrap.ProductDetailAttr;
import www.tonghao.mall.api.stb.attwrap.ProductImageAttr;
import www.tonghao.mall.api.stb.attwrap.ProductPriceAttr;
import www.tonghao.mall.api.stb.attwrap.ProductStateAttr;
import www.tonghao.mall.api.stb.resultwrap.MessageDelRes;
import www.tonghao.mall.api.stb.resultwrap.MessageRes;
import www.tonghao.mall.api.stb.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.stb.resultwrap.ProductImageRes;
import www.tonghao.mall.api.stb.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.stb.resultwrap.ProductStateRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.ProductMessageStbService;
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

@Service("productMessageStbService")
public class ProductMessageStbServiceImpl implements ProductMessageStbService {
	private static Log logger = LogFactory.getLog(ProductMessageStbServiceImpl.class);
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

	@Override
	public void executeProductMessageJob() {
		Suppliers sup = new Suppliers();
		sup.setCode(Constant.MALL_STB_CODE);
		Suppliers supplier = suppliersService.selectEntityOne(sup);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplier.getId());
		map.put("types", 1);
//		List<Protocol> pro = protocolService.getProtocolBySupplier(map);
//		Protocol protocol = null;
//		if (!CollectionUtil.collectionIsEmpty(pro)) {
//			protocol = pro.get(0);
//		}
		if (supplier != null) {
			// 添加消息
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

	public void messagePrice(Suppliers supplier, Protocol protocol) {
		MessageRes message = StbUtilApi.getMessage(2);
		if (message.isSuccess()) {
			List<MessageAttr> attr = message.getAttr();
			for (MessageAttr messageAttr : attr) {
				MessageSku mess_sku = (MessageSku) messageAttr.getMessage();
				Products product = isProduct(mess_sku.getSkuId(), supplier.getId());
				if (product != null) {// 存在才会去更新价格，不存在需要添加
					BigDecimal marketPrice = product.getMarketPrice();
					BigDecimal protocolPrice = product.getProtocolPrice();
					BigDecimal price = product.getPrice();
					addSkuLogs(supplier.getCode(), messageAttr.getId(), mess_sku.getSkuId(), messageAttr.getTime(), 6);
					try {
						ProductPriceRes productPriceApi = StbUtilApi.ProductPriceApi(mess_sku.getSkuId());
						if (productPriceApi.isSuccess()) {
							List<ProductPriceAttr> attrs = productPriceApi.getAttrs();
							if (!CollectionUtil.collectionIsEmpty(attrs)) {
								ProductPriceAttr productPriceAttr = attrs.get(0);
								product.setMarketPrice(productPriceAttr.getMall_price());
								product.setProtocolPrice(productPriceAttr.getPrice());
								if(supplier.getIsPrice()==0&&protocolPrice.compareTo(price)==0){
									product.setPrice(productPriceAttr.getPrice());
								}
								if(supplier.getIsPrice()==1||(supplier.getIsPrice()==0&&protocolPrice.compareTo(price)!=0)){
									if(marketPrice.compareTo(productPriceAttr.getMall_price())!=0||protocolPrice.compareTo(productPriceAttr.getPrice())!=0){
										product.setStatus(4);
										product.setIsChangePrice(0);
										saveUpperLowerHistory(product.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+productPriceAttr.getMall_price()+",协议价："+productPriceAttr.getPrice());
									}
								}
							}
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}else {
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					} catch (Exception e) {
						product.setStatus(4);
						saveUpperLowerHistory(product.getId(), 4, supplier.getId(), "价格接口调用异常");
						logger.info(mess_sku.getSkuId() + "价格接口失败");
					}
					if (product.getStatus() == 3) {
						if (product.getPrice() == null || product.getMarketPrice() == null
								|| product.getProtocolPrice().compareTo(new BigDecimal("0"))==0||product.getPrice().compareTo(new BigDecimal("0")) == 0) {// 判断价格
							product.setStatus(4);
							saveUpperLowerHistory(product.getId(), 4, supplier.getId(), Constant.PRODUCT_PRICE);
						}
						if (product.getPrice() != null && product.getMarketPrice() != null
								&& product.getProtocolPrice().compareTo(product.getMarketPrice()) >0) {// 判断价格
							product.setStatus(4);
							saveUpperLowerHistory(product.getId(), 4, supplier.getId(), Constant.PRICE_ERROR);
						}
					}
					productsService.updateNotNull(product);

				} else {
					ProductDetailRes productDetail = StbUtilApi.productDetail(mess_sku.getSkuId());// 获取商品详情
					if (productDetail.isSuccess()) {
						int saveProduct = saveProduct(productDetail.getAttr(), supplier, protocol);
						if (saveProduct > 0) {// 保存成功
							int result = updateProduct(mess_sku.getSkuId(), supplier,false);
							if (result > 0) {
								if(supplier.getIsPrice()==0){
									check(mess_sku.getSkuId(), supplier.getId());
								}
							}
						}
					}else {
						MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());//删除消息
					}
				}
			}
		}
	}

	public void messageState(Suppliers supplier, Protocol protocol) {
		MessageRes message = StbUtilApi.getMessage(4);
		if (message.isSuccess()) {
			List<MessageAttr> attr = message.getAttr();
			for (MessageAttr messageAttr : attr) {
				MessageSku mess_sku = (MessageSku) messageAttr.getMessage();
				int state = mess_sku.getState();
				Products product = isProduct(mess_sku.getSkuId(), supplier.getId());
				if (product != null) {// 存在才会去更新状态，不存在需要添加
					if (state == 0) {// 下架
						addSkuLogs(supplier.getCode(), messageAttr.getId(), mess_sku.getSkuId(), messageAttr.getTime(),
								5);
						product.setStatus(4);
						saveUpperLowerHistory(product.getId(), 4, supplier.getId(), Constant.EMALL_ERROR);
						int updateNotNull = productsService.updateNotNull(product);
						if (updateNotNull > 0) {
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}

					} else if (state == 1) {// 上架
						if(product.getIsShow()==3){
							if(supplier.getIsPrice()==0){
								addSkuLogs(supplier.getCode(), messageAttr.getId(), mess_sku.getSkuId(), messageAttr.getTime(),
										4);
								product.setStatus(3);
								saveUpperLowerHistory(product.getId(), 3, supplier.getId(), Constant.EMALL_SUCCESS);
								int updateNotNull = productsService.updateNotNull(product);
								if (updateNotNull > 0) {
									MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
									if (delMessage.isSuccess()) {
										delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
									}
								}
							}else{
								MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							}
						}else{
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
						}
					}
				} else {
					ProductDetailRes productDetail = StbUtilApi.productDetail(mess_sku.getSkuId());// 获取商品详情
					if (productDetail.isSuccess()) {
						int saveProduct = saveProduct(productDetail.getAttr(), supplier, protocol);
						if (saveProduct > 0) {// 保存成功
							int result = updateProduct(mess_sku.getSkuId(), supplier,false);
							if (result > 0) {
								if(supplier.getIsPrice()==0){
									check(mess_sku.getSkuId(), supplier.getId());
								}
							}
						}
						MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
					}else {
						MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
					}
				}
			}
		}
	}

	public void messageDelete(Suppliers supplier) {
		MessageRes message = StbUtilApi.getMessage(6);
		if (message.isSuccess()) {
			List<MessageAttr> attr = message.getAttr();
			for (MessageAttr messageAttr : attr) {
				MessageSku mess_sku = (MessageSku) messageAttr.getMessage();
				int state = mess_sku.getState();
				if (state == 2) {
					addSkuLogs(supplier.getCode(), messageAttr.getId(), mess_sku.getSkuId(), messageAttr.getTime(), 2);
					Products product = isProduct(mess_sku.getSkuId(), supplier.getId());
					if (product != null) {
						product.setIsDelete(1);
						product.setStatus(4);
						int updateNotNull = productsService.updateNotNull(product);
						if (updateNotNull > 0) {
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					} else {
						MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
						if (delMessage.isSuccess()) {
							delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
						}
					}

				}
			}
		}
	}

	public void messageUpdate(Suppliers supplier, Protocol protocol) {
		MessageRes message = StbUtilApi.getMessage(6);
		if (message.isSuccess()) {
			List<MessageAttr> attr = message.getAttr();
			for (MessageAttr messageAttr : attr) {
				MessageSku mess_sku = (MessageSku) messageAttr.getMessage();
				int state = mess_sku.getState();
				if (state == 3) {
					addSkuLogs(supplier.getCode(), messageAttr.getId(), mess_sku.getSkuId(), messageAttr.getTime(), 3);
					Products product = isProduct(mess_sku.getSkuId(), supplier.getId());
					if (product != null) {// 存在才修改，
						ProductDetailRes productDetail = StbUtilApi.productDetail(mess_sku.getSkuId());// 获取商品详情
						if (productDetail.isSuccess()) {
							int update = updateProduct(product, productDetail.getAttr());
							if (update > 0) {
								MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
								}
								if(supplier.getIsPrice()==0){
									check(mess_sku.getSkuId(), supplier.getId());
								}
							}
						}else {
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					} else { // 添加
						ProductDetailRes productDetail = StbUtilApi.productDetail(mess_sku.getSkuId());// 获取商品详情
						if (productDetail.isSuccess()) {
							int saveProduct = saveProduct(productDetail.getAttr(), supplier, protocol);
							if (saveProduct > 0) {// 保存成功
								int result = updateProduct(mess_sku.getSkuId(), supplier,false);
								if (result > 0) {
									MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
									if (delMessage.isSuccess()) {
										delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
									}
									if(supplier.getIsPrice()==0){//不需要改价
										check(mess_sku.getSkuId(), supplier.getId());
									}
								}
							}
						}else {
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					}
				}
			}
		}
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
			Brand brand = getBrandId(attr);
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

	public void messageSave(Suppliers supplier, Protocol protocol) {
		MessageRes message = StbUtilApi.getMessage(6);
		if (message.isSuccess()) {
			List<MessageAttr> attr = message.getAttr();
			for (MessageAttr messageAttr : attr) {
				MessageSku mess_sku = (MessageSku) messageAttr.getMessage();
				int state = mess_sku.getState();
				if (state == 1) {
					// 保存本地消息
					addSkuLogs(supplier.getCode(), messageAttr.getId(), mess_sku.getSkuId(), messageAttr.getTime(), 1);
					Products product = isProduct(mess_sku.getSkuId(), supplier.getId());
					if (product == null) { // 判断是否已存在
						ProductDetailRes productDetail = StbUtilApi.productDetail(mess_sku.getSkuId());// 获取商品详情
						if (productDetail.isSuccess()) {
							int saveProduct = saveProduct(productDetail.getAttr(), supplier, protocol);
							if (saveProduct > 0) {// 保存成功
								int result = updateProduct(mess_sku.getSkuId(), supplier,false);
								if (result > 0) {
									MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
									if (delMessage.isSuccess()) {
										delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
									}
									
								}
								if(supplier.getIsPrice()==0){//不需要改价  直接校验 上架
									check(mess_sku.getSkuId(), supplier.getId());
								}
							}
						}else {
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					} else {
						MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
						if (delMessage.isSuccess()) {
							delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
						}
					}
				}
			}
		}
	}

	public void check(String skuId, Long supplierId) {
		Products pro = isProduct(skuId, supplierId);
		if (pro != null) {
			if (pro.getStatus() == 3) {
				if (isNull(pro.getBrandName()) || isNull(pro.getModel())) {// 判断商品型号或者品牌
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.MODEL_ERROR);
				}
				if (pro.getProtocolPrice() == null || pro.getMarketPrice() == null
						|| pro.getProtocolPrice().compareTo(new BigDecimal("0")) == 0) {// 判断价格
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.PRODUCT_PRICE);
				}
				if (pro.getProtocolPrice() != null && pro.getMarketPrice() != null
						&& pro.getProtocolPrice().compareTo(pro.getMarketPrice()) > 0) {// 判断价格
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.PRICE_ERROR);
				}

				if (isNull(pro.getDetail())) {// 判断详情
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.PRODUCT_DETAIL);
				}
				if (isNull(pro.getCoverUrl())) {// 判断主图
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, Constant.PRODUCT_IMAGE);
				}
				productsService.updateNotNull(pro);
			}
		}
	}

	public int updateProduct(String skuId, Suppliers supplier,boolean isUpdate) {
		Products product = isProduct(skuId, supplier.getId());
		if (product != null) {
			if(!isUpdate&&supplier.getIsPrice()==0){//添加  
				try {
					ProductStateRes productStateApi = StbUtilApi.ProductStateApi(skuId);// 上下架状态
					if (productStateApi.isSuccess()) {
						List<ProductStateAttr> list = productStateApi.getList();
						if (!CollectionUtil.collectionIsEmpty(list)) {
							ProductStateAttr productStateAttr = list.get(0);
							int state = productStateAttr.getState();
							if (state == 0) {
								product.setStatus(4);
								saveUpperLowerHistory(product.getId(), 4, supplier.getId(), Constant.EMALL_ERROR);
							} else if (state == 1) {
								product.setStatus(3);
								saveUpperLowerHistory(product.getId(), 3, supplier.getId(), Constant.EMALL_SUCCESS);
							}

						}
					}
				} catch (Exception e) {
					product.setStatus(4);
					saveUpperLowerHistory(product.getId(), 4, supplier.getId(), "上下架接口调用异常，自动下架");
					logger.info(skuId + "上下架状态接口失败");
				}
			}

			try {
				ProductPriceRes productPriceApi = StbUtilApi.ProductPriceApi(skuId);
				if (productPriceApi.isSuccess()) {
					List<ProductPriceAttr> attrs = productPriceApi.getAttrs();
					if (!CollectionUtil.collectionIsEmpty(attrs)) {
						ProductPriceAttr productPriceAttr = attrs.get(0);
						product.setMarketPrice(productPriceAttr.getMall_price());
						product.setProtocolPrice(productPriceAttr.getPrice());
						if(supplier.getIsPrice()==1){//需要改价
							if(!isUpdate){//添加  是否修改  修改的时候不去修改售价
								product.setPrice(productPriceAttr.getPrice());
							}
						}else{//不改价
						    product.setPrice(productPriceAttr.getPrice());
						}
						
						
					}
				}else{
					product.setStatus(4); 
					saveUpperLowerHistory(product.getId(), 4, supplier.getId(), productPriceApi.getDesc());
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
				ProductImageRes productImageApi = StbUtilApi.ProductImageApi(skuId);
				if (productImageApi.isSuccess()) {
					List<ProductImageAttr> imagesList = productImageApi.getImagesList();
					if (!CollectionUtil.collectionIsEmpty(imagesList)) {
						ProductImageAttr productImageAttr = imagesList.get(0);
						List<Image> images = productImageAttr.getImages();
						if (!CollectionUtil.collectionIsEmpty(images)) {
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
							Example example_pic=new Example(ProductPics.class);
							Criteria createCriteria = example_pic.createCriteria();
							createCriteria.andEqualTo("productId", product.getId());
							List<ProductPics> pic = productPicsMapper.selectByExample(example_pic);
							if(!CollectionUtil.collectionIsEmpty(pic)&&pic.size()==1) {
								saveProductPic(pic);
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

	private void saveProductPic(List<ProductPics> pic) {
		ProductPics productPics = pic.get(0);
		String large = productPics.getLarge();
		String large1=""; 
		String large2="";
		String large3="";
		if(large.indexOf("_1_")!=-1) {
			 large1 = large.replace("_1_", "_2_");
			 large2 = large.replace("_1_", "_3_");
			 large3 = large.replace("_1_", "_4_");
		}
		if(large.indexOf("_2_")!=-1) {
			 large1 = large.replace("_2_", "_1_");
			 large2 = large.replace("_2_", "_3_");
			 large3 = large.replace("_2_", "_4_");
		}
		if(large.indexOf("_3_")!=-1) {
			large1 = large.replace("_3_", "_1_");
			large2 = large.replace("_3_", "_2_");
			large3 = large.replace("_3_", "_4_");	
		}
		if(large.indexOf("_4_")!=-1) {
			large1 = large.replace("_4_", "_1_");
			large2 = large.replace("_4_", "_2_");
			large3 = large.replace("_4_", "_3_");
		}
		if(large.indexOf("_5_")!=-1) {
			large1 = large.replace("_5_", "_1_");
			large2 = large.replace("_5_", "_2_");
			large3 = large.replace("_5_", "_3_");
		}
		List<ProductPics> list=new ArrayList<ProductPics>();
		if(StringUtils.isNotEmpty(large1)) {
			ProductPics pc=new ProductPics();
			pc.setLarge(large1);
			pc.setMedium(large1);
			pc.setSource(large1);
			pc.setProductId(productPics.getProductId());
			pc.setCreatedAt("2019-05-31 00:00:00");
			pc.setUpdatedAt("2019-05-31 00:00:00");
			list.add(pc);
		}
		if(StringUtils.isNotEmpty(large2)) {
			ProductPics pc=new ProductPics();
			pc.setLarge(large2);
			pc.setMedium(large2);
			pc.setSource(large2);
			pc.setProductId(productPics.getProductId());
			pc.setCreatedAt("2019-05-31 00:00:00");
			pc.setUpdatedAt("2019-05-31 00:00:00");
			list.add(pc);
		}
		if(StringUtils.isNotEmpty(large3)) {
			ProductPics pc=new ProductPics();
			pc.setLarge(large3);
			pc.setMedium(large3);
			pc.setSource(large3);
			pc.setProductId(productPics.getProductId());
			pc.setCreatedAt("2019-05-31 00:00:00");
			pc.setUpdatedAt("2019-05-31 00:00:00");
			list.add(pc);
		}
		if(!CollectionUtil.collectionIsEmpty(list)) {
			for (ProductPics pp : list) {
				productPicsMapper.insert(pp);
			}
		}
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
				selProducts.setIsShow(3);
				if(supplier.getIsPrice()==0){
					selProducts.setIsChangePrice(1);
				}else{
					selProducts.setIsChangePrice(0);
				}
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
				Brand brand = getBrandId(attr);
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

	/**
	 * 获取对应品牌
	 */
	public Brand getBrandId(ProductDetailAttr attr) {
		Brand return_brand = new Brand();
		List<Brand> list = brandMapper.findByMappingName(attr.getBrand_name());
		if (!CollectionUtil.collectionIsEmpty(list)) {
			oth: for (Brand brand : list) {
				String mappingName = brand.getMappingName();
				String[] split = mappingName.split("\\|");
				for (String string : split) {
					if (string.equals(attr.getBrand_name())) {
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

	private boolean isNull(String str) {
		if (str == null || "null".equals(str)) {
			return true;
		}
		return false;
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
