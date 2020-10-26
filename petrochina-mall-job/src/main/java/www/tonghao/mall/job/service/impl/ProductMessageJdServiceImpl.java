package www.tonghao.mall.job.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.jd.attwrap.Image;
import www.tonghao.mall.api.jd.attwrap.ImageResultAttr;
import www.tonghao.mall.api.jd.attwrap.MessageAttr;
import www.tonghao.mall.api.jd.attwrap.MessagePrice;
import www.tonghao.mall.api.jd.attwrap.MessageProduct;
import www.tonghao.mall.api.jd.attwrap.MessageSku;
import www.tonghao.mall.api.jd.attwrap.MessageState;
import www.tonghao.mall.api.jd.attwrap.PriceAttr;
import www.tonghao.mall.api.jd.attwrap.ProductAttr;
import www.tonghao.mall.api.jd.attwrap.ProductOther;
import www.tonghao.mall.api.jd.attwrap.StateResultAttr;
import www.tonghao.mall.api.jd.resultwrap.MessageDelRes;
import www.tonghao.mall.api.jd.resultwrap.MessageRes;
import www.tonghao.mall.api.jd.resultwrap.ProductImageRes;
import www.tonghao.mall.api.jd.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.jd.resultwrap.ProductRes;
import www.tonghao.mall.api.jd.resultwrap.ProductStateRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.ProductMessageJdService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.mall.job.util.StbUtilApi;
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

@Service("productMessageJdService")
public class ProductMessageJdServiceImpl implements ProductMessageJdService {

	private static Log logger = LogFactory.getLog(ProductMessageJdServiceImpl.class);
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
	public void executeProductJdMessageJob() {
		Suppliers sup = new Suppliers();
		sup.setCode(Constant.MALL_JD_CODE);
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
	
	public void messagePrice(Suppliers suppliers,Protocol protocol){
		MessageRes messageApis = JdUtilApi.MessageApis(2);
		if(messageApis.isSuccess()){
			List<MessageAttr> result = messageApis.getResult();
			if(!CollectionUtil.collectionIsEmpty(result)){
				for (MessageAttr messageAttr : result) {
					MessagePrice message = (MessagePrice)messageAttr.getMessage();
					Products product = isProduct(message.getSkuId(), suppliers.getId());
					if (product != null) {// 存在才会去更新价格，不存在需要添加
						BigDecimal marketPrice = product.getMarketPrice();
						BigDecimal protocolPrice = product.getProtocolPrice();
						BigDecimal price = product.getPrice();
						addSkuLogs(suppliers.getCode(), messageAttr.getId(), message.getSkuId(), messageAttr.getTime(), 6);
						try {
							ProductPriceRes productPricesApi = JdUtilApi.ProductPricesApi(message.getSkuId());
							if(productPricesApi.isSuccess()){
								List<PriceAttr> paAttr = productPricesApi.getResult();
								if(!CollectionUtil.collectionIsEmpty(paAttr)){
									PriceAttr priceAttr = paAttr.get(0);
									product.setMarketPrice(priceAttr.getJdPrice());
									product.setProtocolPrice(priceAttr.getPrice());
									if(suppliers.getIsPrice()==0&&protocolPrice.compareTo(price)==0){
										product.setPrice(priceAttr.getPrice());
									}
									if(suppliers.getIsPrice()==1||(suppliers.getIsPrice()==0&&protocolPrice.compareTo(price)!=0)){
										if(marketPrice.compareTo(priceAttr.getJdPrice())!=0||protocolPrice.compareTo(priceAttr.getPrice())!=0){
											product.setStatus(4);
											product.setIsChangePrice(0);
											saveUpperLowerHistory(product.getId(), 4, suppliers.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+priceAttr.getJdPrice()+",协议价："+priceAttr.getPrice());
										}
									}
								}
								MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
								}
							}else{
								MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
								}
							}
						} catch (Exception e) {
							product.setStatus(4);
							saveUpperLowerHistory(product.getId(), 4, suppliers.getId(), "价格接口调用异常");
							e.printStackTrace();
						}
						if (product.getStatus() == 3) {
							if (product.getPrice() == null || product.getMarketPrice() == null
									||product.getProtocolPrice().compareTo(new BigDecimal("0"))==0 || product.getPrice().compareTo(new BigDecimal("0")) == 0) {// 判断价格
								product.setStatus(4);
								saveUpperLowerHistory(product.getId(), 4, suppliers.getId(), Constant.PRODUCT_PRICE);
							}
							if (product.getPrice() != null && product.getMarketPrice() != null
									&& product.getProtocolPrice().compareTo(product.getMarketPrice()) >0) {// 判断价格
								product.setStatus(4);
								saveUpperLowerHistory(product.getId(), 4, suppliers.getId(), Constant.PRICE_ERROR);
							}
						}
						productsService.updateNotNull(product);
					}else{
						ProductRes pda = JdUtilApi.ProductDetailApi(message.getSkuId());
						if(pda.isSuccess()){
							int saveProduct = saveProduct(suppliers,protocol,pda.getResult());
							if(saveProduct>0){
								int updateProductAll = updateProductAll(message.getSkuId(), suppliers);
							    if(updateProductAll>0){
								   delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
							    }
							    if(suppliers.getIsPrice()==0){//不需要改价  直接校验 上架
									check(message.getSkuId(), suppliers.getId());
								}
							}
						}else{
							delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
						}
					}
				}
			}
		}
		
		
	}

	public void messageState(Suppliers suppliers,Protocol protocol){
		MessageRes messageApis = JdUtilApi.MessageApis(4);
		if(messageApis.isSuccess()){
			List<MessageAttr> result = messageApis.getResult();
			if(!CollectionUtil.collectionIsEmpty(result)){
				for (MessageAttr messageAttr : result) {
					MessageState message = (MessageState)messageAttr.getMessage();
					int state = message.getState();
					Products product = isProduct(message.getSkuId(), suppliers.getId());
					if(product!=null){
						if(state==0){//下架
							addSkuLogs(suppliers.getCode(), messageAttr.getId(), message.getSkuId(), messageAttr.getTime(),
									5);
							product.setStatus(4);
							saveUpperLowerHistory(product.getId(), 4, suppliers.getId(), Constant.EMALL_ERROR);
							int updateNotNull = productsService.updateNotNull(product);
							if (updateNotNull > 0) {
								MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
								}
							}
						}
						if(state==1){
							if(product.getIsShow()==3){
								if(suppliers.getIsPrice()==0){
									addSkuLogs(suppliers.getCode(), messageAttr.getId(), message.getSkuId(), messageAttr.getTime(),
											4);
									product.setStatus(3);
									saveUpperLowerHistory(product.getId(), 3, suppliers.getId(), Constant.EMALL_SUCCESS);
									int updateNotNull = productsService.updateNotNull(product);
									if (updateNotNull > 0) {
										MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
										if (delMessage.isSuccess()) {
											delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
										}
									}
								}else{
									MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
								}
							}else{
								MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
							}
						}
					}else{
						ProductRes pda = JdUtilApi.ProductDetailApi(message.getSkuId());
						if(pda.isSuccess()){
							int saveProduct = saveProduct(suppliers,protocol,pda.getResult());
							if(saveProduct>0){
								int updateProductAll = updateProductAll(message.getSkuId(), suppliers);
							    if(updateProductAll>0){
								   delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
							    }
							    if(suppliers.getIsPrice()==0){//不需要改价  直接校验 上架
									check(message.getSkuId(), suppliers.getId());
								}
							}
						}else{
							delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
						}
					}
					
				}
			}
		}
	}
	public void messageDelete(Suppliers suppliers){
		MessageRes messageApis = JdUtilApi.MessageApis(6);
		if(messageApis.isSuccess()){
			List<MessageAttr> result = messageApis.getResult();
			if(!CollectionUtil.collectionIsEmpty(result)){
				for (MessageAttr messageAttr : result) {
					MessageSku message =(MessageSku) messageAttr.getMessage();
					int state = message.getState();
					if(state==2){//删除
						addSkuLogs(suppliers.getCode(), messageAttr.getId(), message.getSkuId(), messageAttr.getTime(), 2);
						Products product = isProduct(message.getSkuId(), suppliers.getId());
						if(product!=null){
							product.setIsDelete(1);
							product.setStatus(4);
							int updateNotNull = productsService.updateNotNull(product);
							if(updateNotNull>0){
								MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
								}
							}
						}else{
							MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
							}
						}
					}
				}
			}
		}
	}
	
	public void messageUpdate(Suppliers suppliers,Protocol protocol){
		MessageRes messageApis = JdUtilApi.MessageApis(16);
		if(messageApis.isSuccess()){
			List<MessageAttr> result = messageApis.getResult();
			if(!CollectionUtil.collectionIsEmpty(result)){
				for (MessageAttr messageAttr : result) {
					MessageProduct message = (MessageProduct)messageAttr.getMessage();
					addSkuLogs(suppliers.getCode(), messageAttr.getId(), message.getSkuId(), messageAttr.getTime(), 3);
					Products product = isProduct(message.getSkuId(), suppliers.getId());
					if(product!=null){//修改
						ProductRes pda = JdUtilApi.ProductDetailApi(message.getSkuId());
						if(pda.isSuccess()){
							int updateProduct = updateProduct(suppliers,protocol,pda.getResult());
							if(updateProduct>0){
								MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
								}
								if(suppliers.getIsPrice()==0){
									check(message.getSkuId(), suppliers.getId());
								}
							}
						}else{
							MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
							}
						}
					}else{
						ProductRes pda = JdUtilApi.ProductDetailApi(message.getSkuId());
						if(pda.isSuccess()){
							int saveProduct = saveProduct(suppliers,protocol,pda.getResult());
							if(saveProduct>0){
								int updateProductAll = updateProductAll(message.getSkuId(), suppliers);
							    if(updateProductAll>0){
							    	MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
									if (delMessage.isSuccess()) {
										delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
									}
									
							    }
							    if(suppliers.getIsPrice()==0){//不需要改价  直接校验 上架
									check(message.getSkuId(), suppliers.getId());
								}
							}
						}else{
							MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
							if (delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
							}
						}
					}
				}
			}
		}
	}
	
	public int updateProduct(Suppliers suppliers,Protocol protocol,ProductAttr attr){
		ProductOther po = (ProductOther) attr.getProduct();
		Products pt = isProduct(po.getSku(), suppliers.getId());
		if(pt!=null){
			Brand brand = getBrandId(po.getBrandName());
			if(protocol!=null){
				pt.setProtocolId(protocol.getId());
				pt.setProtocolName(protocol.getName());
			}
			pt.setName(po.getName());
			pt.setModel(po.getModel());
			pt.setWeight(po.getWeight());
			pt.setParam(po.getParam());
			pt.setFactoryURL("https://item.jd.com/"+po.getSku()+".html"); //官网地址
			pt.setMallBrandName(po.getBrandName());
			if( brand != null){
				pt.setBrandId(brand.getId());
				pt.setBrandName(brand.getName());
			}
			String category = po.getCategory();
			if(!StringUtils.isEmpty(category)){
				String[] split = category.split(";");
				pt.setEmallCatalogeId(split[split.length-1]);
				Example example=new Example(EmallCatalogs.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("emallCode",suppliers.getCode());
				createCriteria.andEqualTo("emallId",suppliers.getId());
				createCriteria.andEqualTo("emallCatalogsId",split[split.length-1]);
				List<EmallCatalogs> ec = emallCatalogsMapper.selectByExample(example);
				if(!CollectionUtil.collectionIsEmpty(ec)){
					if(ec.size()==1){
						EmallCatalogs emallCatalogs = ec.get(0);
						if(emallCatalogs.getCatalogsId()!=null){
							PlatformCatalogs pc = platformCatalogsService.selectByKey(emallCatalogs.getCatalogsId());
							pt.setCatalogId(emallCatalogs.getCatalogsId());
							pt.setCatalogName(pc.getName());
						}
					}
				}
			}
			if(!StringUtils.isBlank(po.getImagePath())){
				pt.setCoverUrl("http://img13.360buyimg.com/n0/"+po.getImagePath());
			}
			pt.setDetail(po.getIntroduction());
			pt.setWare(po.getWareQD());
			pt.setProductArea(po.getProductArea());
			pt.setUpc(po.getUpc());
			pt.setAfterSaleService(po.getShouhou());
			pt.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			return productsService.updateNotNull(pt);
		}
		return 0;
		
	}
	public void messageSave(Suppliers suppliers,Protocol protocol){
			MessageRes messageApis = JdUtilApi.MessageApis(6);
			if(messageApis.isSuccess()){
				List<MessageAttr> result = messageApis.getResult();
				if(!CollectionUtil.collectionIsEmpty(result)){
					for (MessageAttr messageAttr : result) {
						MessageSku message =(MessageSku) messageAttr.getMessage();
						int state = message.getState();
						if(state==1){//添加
							// 保存本地消息
							addSkuLogs(suppliers.getCode(), messageAttr.getId(), message.getSkuId(), messageAttr.getTime(), 1);
							Products product = isProduct(message.getSkuId(), suppliers.getId());
							if(product==null){
								ProductRes pda = JdUtilApi.ProductDetailApi(message.getSkuId());
								if(pda.isSuccess()){
									int saveProduct = saveProduct(suppliers,protocol,pda.getResult());
									if(saveProduct>0){
										int updateProductAll = updateProductAll(message.getSkuId(), suppliers);
									    if(updateProductAll>0){
									    	MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
											if (delMessage.isSuccess()) {
												delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
											}
											
									    }
									    if(suppliers.getIsPrice()==0){//不需要改价  直接校验 上架
											check(message.getSkuId(), suppliers.getId());
										}
									}
								}else{
									MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
									if (delMessage.isSuccess()) {
										delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
									}
								}
							}else{
								MessageDelRes delMessage = JdUtilApi.MessageDelApis(messageAttr.getId());// 刪除消息
								if (delMessage.isSuccess()) {
									delSkuLogs(messageAttr.getId(), suppliers.getCode());// 删除本地消息
								}
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
				if (isNull(pro.getBrandName())) {// 判断商品型号或者品牌
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
				if(isNull(pro.getCatalogName())) {
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplierId, "品目没映射自动下架");
				}
				productsService.updateNotNull(pro);
			}
		}
	}
	public int updateProductAll(String sku,Suppliers suppliers){
		Products pt = isProduct(sku, suppliers.getId());
		if(pt==null){
			return 0;
		}
		if(suppliers.getIsPrice()==0){
			try {
				ProductStateRes productStateApi = JdUtilApi.ProductStateApi(sku);
				if(productStateApi.isSuccess()){
					List<StateResultAttr> result = productStateApi.getResult();
					if(!CollectionUtil.collectionIsEmpty(result)){
						StateResultAttr stateResultAttr = result.get(0);
						if(stateResultAttr.getState()==1){
							pt.setStatus(3);
						}else if(stateResultAttr.getState()==0){
							pt.setStatus(4);
						}
					}
				}else{
					pt.setStatus(4);
					saveUpperLowerHistory(pt.getId(), 4, suppliers.getId(),productStateApi.getResultMessage() );
				}
			} catch (Exception e) {
				pt.setStatus(4);
				saveUpperLowerHistory(pt.getId(), 4, suppliers.getId(), "上下架接口调用异常，自动下架");
				e.printStackTrace();
			}
		}
		
		try {
			ProductPriceRes productPricesApi = JdUtilApi.ProductPricesApi(sku);
			if(productPricesApi.isSuccess()){
				List<PriceAttr> result = productPricesApi.getResult();
				if(!CollectionUtil.collectionIsEmpty(result)){
					PriceAttr priceAttr = result.get(0);
					BigDecimal jdPrice = priceAttr.getJdPrice();//京东官网价
					BigDecimal price = priceAttr.getPrice();//京东协议价
					pt.setMarketPrice(jdPrice);
					pt.setProtocolPrice(price);
					pt.setPrice(price);
				}
			}else{
				pt.setStatus(4); 
				saveUpperLowerHistory(pt.getId(), 4, suppliers.getId(), productPricesApi.getResultMessage());
			}
		} catch (Exception e) {
			pt.setStatus(4); 
			saveUpperLowerHistory(pt.getId(), 4, suppliers.getId(), "价格接口调用异常");
			e.printStackTrace();
		}
		try {
			ProductImageRes productImageApi = JdUtilApi.ProductImageApi(sku);
			if(productImageApi.isSuccess()){
				List<ImageResultAttr> result = productImageApi.getResult();
				if(!CollectionUtil.collectionIsEmpty(result)){
					ImageResultAttr imageResultAttr = result.get(0);
					List<Image> images = imageResultAttr.getImages();
                    for (Image image : images) {
                    	ProductPics productPics = new ProductPics();
						productPics.setProductId(pt.getId());
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
			}else{
				pt.setStatus(4);
				saveUpperLowerHistory(pt.getId(), 4, suppliers.getId(), productImageApi.getResultMessage());
			}
		} catch (Exception e) {
			pt.setStatus(4);
			saveUpperLowerHistory(pt.getId(), 4, suppliers.getId(), "图片详情接口调用异常");
		}
		
		return productsService.updateNotNull(pt);
	}
	public int saveProduct(Suppliers suppliers,Protocol protocol,ProductAttr attr){
		try {
			ProductOther po = (ProductOther) attr.getProduct();
			Products pt = isProduct(po.getSku(), suppliers.getId());
			if(pt==null){
				Products products=new Products();
				Brand brand = getBrandId(po.getBrandName());
				if(protocol!=null){
					products.setProtocolId(protocol.getId());
					products.setProtocolName(protocol.getName());
				}
				products.setSupplierId(suppliers.getId());
				products.setSupplierName(suppliers.getName());
				products.setName(po.getName());
				products.setSku(po.getSku());
				products.setModel(po.getModel());
				products.setWeight(po.getWeight());
				products.setStatus(4);
				if(suppliers.getIsPrice()==0){
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
					createCriteria.andEqualTo("emallCode",suppliers.getCode());
					createCriteria.andEqualTo("emallId",suppliers.getId());
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
				products.setAppintroduce(po.getAppintroduce());
				products.setWxintroduction(po.getWxintroduction());
				products.setIsFactoryShip(po.getIs_factory_ship());
				return productsService.save(products);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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
	public Brand getBrandId(String brandName) {
		Brand return_brand = new Brand();
		List<Brand> list = brandMapper.findByMappingName(brandName);
		if (!CollectionUtil.collectionIsEmpty(list)) {
			oth: for (Brand brand : list) {
				String mappingName = brand.getMappingName();
				String[] split = mappingName.split("\\|");
				for (String string : split) {
					if (string.equals(brandName)) {
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
