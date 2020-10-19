package www.tonghao.service.common.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.Constant;
import www.tonghao.common.constant.EmallConstant;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ImageUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.api.jd.attwrap.ImageResultAttr;
import www.tonghao.mall.api.jd.attwrap.PriceAttr;
import www.tonghao.mall.api.jd.attwrap.ProductOther;
import www.tonghao.mall.api.jd.attwrap.StateResultAttr;
import www.tonghao.mall.api.jd.call.ProductDetailApi;
import www.tonghao.mall.api.jd.call.ProductImageApi;
import www.tonghao.mall.api.jd.call.ProductPricesApi;
import www.tonghao.mall.api.jd.call.ProductStateApi;
import www.tonghao.mall.api.jd.resultwrap.ProductImageRes;
import www.tonghao.mall.api.jd.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.jd.resultwrap.ProductRes;
import www.tonghao.mall.api.jd.resultwrap.ProductStateRes;
import www.tonghao.mall.api.sn.attwrap.PriceSkus;
import www.tonghao.mall.api.sn.call.ProductPriceApi;
import www.tonghao.mall.api.stb.attwrap.Image;
import www.tonghao.mall.api.stb.attwrap.ProductDetailAttr;
import www.tonghao.mall.api.stb.attwrap.ProductImageAttr;
import www.tonghao.mall.api.stb.attwrap.ProductPriceAttr;
import www.tonghao.mall.api.stb.attwrap.ProductStateAttr;
import www.tonghao.mall.api.stb.resultwrap.ProductDetailRes;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.Catalogs;
import www.tonghao.service.common.entity.Commodities;
import www.tonghao.service.common.entity.CommoditiesParameter;
import www.tonghao.service.common.entity.EmallCatalogsMapping;
import www.tonghao.service.common.entity.EnergySavingProducts;
import www.tonghao.service.common.entity.Parameter;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductChangePrice;
import www.tonghao.service.common.entity.ProductParameter;
import www.tonghao.service.common.entity.ProductPics;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.ProductsList;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.mapper.CatalogsMapper;
import www.tonghao.service.common.mapper.CommoditiesMapper;
import www.tonghao.service.common.mapper.CommoditiesParameterMapper;
import www.tonghao.service.common.mapper.EmallCatalogsMappingMapper;
import www.tonghao.service.common.mapper.EnergySavingProductsMapper;
import www.tonghao.service.common.mapper.ParameterMapper;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.mapper.ProductChangePriceMapper;
import www.tonghao.service.common.mapper.ProductParameterMapper;
import www.tonghao.service.common.mapper.ProductPicsMapper;
import www.tonghao.service.common.mapper.ProductsMapper;
import www.tonghao.service.common.mapper.ProtocolMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.mapper.UpperLowerHistoryMapper;
import www.tonghao.service.common.service.ProductLogsService;
import www.tonghao.service.common.service.ProductSearchService;
import www.tonghao.service.common.service.ProductsService;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.suning.api.entity.govbus.BatchProdSaleStatusGetRequest.SkuIds;
import com.suning.api.entity.govbus.BatchProdSaleStatusGetResponse.OnShelvesList;
import com.suning.api.entity.govbus.ProdImageQueryResponse.Urls;

@Service("productsService")
@Transactional
public class ProductsServiceImpl extends BaseServiceImpl<Products> implements ProductsService {

	private static Log log  = LogFactory.getLog(ProductsServiceImpl.class);
	
	@Autowired
	private ProductsMapper productsMapper;
	
	@Autowired
	private ProductPicsMapper productPicsMapper; 
	
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	
	@Autowired
	private ProductParameterMapper productParameterMapper;
	
	@Autowired
	private UpperLowerHistoryMapper upperLowerHistoryMapper;
	
	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private CatalogsMapper catalogsMapper;
	
	@Autowired
	private ParameterMapper parameterMapper;
	
	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Autowired
	private ProductLogsService productLogsService;

	@Autowired
	private CommoditiesParameterMapper commoditiesParameterMapper;
	
	@Autowired
	private ProtocolMapper protocolMapper;
	
	@Autowired
	private EnergySavingProductsMapper energySavingProductsMapper;
	
	@Autowired
	private CommoditiesMapper commoditiesMapper;
	
	@Autowired
	private EmallCatalogsMappingMapper emallCatalogsMappingMapper;
	
	@Autowired
	private ProductSearchService productSearchService;
	
	@Autowired
	private ProductChangePriceMapper productChangePriceMapper;

	@Override
	public int updateIsDelete(Long id, Integer isDelete) {
		Products entity = new Products();
		entity.setId(id);
		entity.setIsDelete(isDelete);
		return updateNotNull(entity);
	}

	@Override
	public HashMap<String, Object> saveOrUpdate(Products product) {
		//定义返回数据
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		product.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		
		/** ---------主图相关---start----------  */
		//设置主图
		product.setCoverName("");
		product.setCoverUrl("");
		if (!CollectionUtils.isEmpty(product.getPics())) {
			for (int i = 0; i < product.getPics().size(); i++) {
				if (product.getPics().get(i).getIsDelete() == null || product.getPics().get(i).getIsDelete() != 1) {
					product.setCoverName(product.getPics().get(i).getFileName());
					product.setCoverUrl(product.getPics().get(i).getSource());
					break;
				}
			}
		}
		/** ---------主图相关---end----------  */
		
		/** ---------品目相关---start----------  */
		//设置财政品目id
		Long govCatalogId = null;
		Catalogs catalog = null;
		//平台品目id
		Long platformCatalogId = product.getCatalogId();
		if (platformCatalogId != null) {
			PlatformCatalogs platformCatalog = platformCatalogsMapper.selectByPrimaryKey(platformCatalogId);
//			if (platformCatalog != null) {
//				govCatalogId = platformCatalog.getCatalogId();
//				catalog = catalogsMapper.selectByPrimaryKey(govCatalogId);
//				/**------------是否批量-start----------------------*/
//				if (StringUtils.isNotBlank(platformCatalog.getSystems()) && platformCatalog.getSystems().contains("central")) {
//					product.setIsBatch(1);
//				}else {
//					product.setIsBatch(0);
//				}
//				/**------------是否批量-end----------------------*/
//			}else {
//				resultMap.put("status", 0);
//				resultMap.put("message", "请选择归属品目");
//				return resultMap;
//			}
		}else {
			resultMap.put("status", 0);
			resultMap.put("message", "请选择归属品目");
			return resultMap;
		}
//		if (govCatalogId == null || catalog == null) {
//			resultMap.put("status", 0);
//			resultMap.put("message", "归属品目未映射标准品目参数");
//			return resultMap;
//		}
		//设置财政品目id
		product.setGovCatalogId(govCatalogId);
		/** ---------品目相关---end----------  */
		
		/**-----------产品关键参数校验不为空---start-------------------*/
		/*if (govCatalogId != null) {
			//获取财政品目对应参数
			List<Parameter> parameters = parameterMapper.getParameterJoinByCatalogId(govCatalogId);
			String parameterMsg = "";
			boolean isVali = true;
			for (Parameter parameter : parameters) {
				if ("true".equals(parameter.getIsKey()) && !"品牌".equals(parameter.getName()) && !"型号".equals(parameter.getName())) {
					boolean flag = false;
					//录入的商品参数数据
					List<ProductParameter> productParameters = product.getProductParameters();
					pp:
					for (ProductParameter productParameter : productParameters) {
						if (parameter.getStandParamId().equals(productParameter.getStandParamId()) && StringUtils.isNotBlank(productParameter.getParamValue())) {
							flag = true;
							break pp;
						}
					}
					if (!flag) {
						isVali = false;
						parameterMsg +=  "[" + parameter.getName() + "]";
					}
				}
			}
			if (StringUtils.isNotBlank(parameterMsg) && !isVali) {
				resultMap.put("status", 0);
				resultMap.put("message", "请录入" + parameterMsg + "参数信息");
				return resultMap;
			}
		}*/
		/**-----------产品参数校验---end-------------------*/
		
		if (product.getId() != null) {
			product.setStatus(4);
			result = updateNotNull(product);
			//保存商品图片
			if (result > 0 && !CollectionUtils.isEmpty(product.getPics())) {
				List<ProductPics> pics = product.getPics();
				float index = 1;
				for (int i = 0; i < pics.size(); i++) {
					ProductPics productPics = pics.get(i);
					if (productPics.getId() != null) {
						if (productPics.getIsDelete() != null && productPics.getIsDelete() == 1) {
							//删除表数据
							productPicsMapper.deleteByPrimaryKey(productPics.getId());
						}else{
							productPics.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
							productPics.setPriority(index);
							productPicsMapper.updateByPrimaryKeySelective(productPics);
							index ++;
						}
					}else {
						productPics.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
						productPics.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
						productPics.setPriority(index);
						productPics.setProductId(product.getId());
						//生成大图、中图、缩略图
						HashMap<String, String> map = createImageExt(productPics.getSource());
						productPics.setLarge(map.get("largePath"));
						productPics.setMedium(map.get("mediumPath"));
						productPics.setThumbnail(map.get("thumbnailPath"));
						productPicsMapper.insertSelective(productPics);
						index ++;
					}
				}
			}
			/*---保存图片 end----*/
			
			//保存商品参数值
			List<ProductParameter> parameters = product.getProductParameters();
			if (result > 0 && !CollectionUtils.isEmpty(parameters)) {
				//先删除之前保存的商品参数数据
				ProductParameter record = new ProductParameter();
				record.setProductId(product.getId());
				record.setPlatformCatalogsId(product.getCatalogId());
				productParameterMapper.delete(record);
				//保存最新的商品参数数据
				for (ProductParameter productParameter : parameters) {
					productParameter.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					productParameter.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					productParameter.setProductId(product.getId());
					productParameterMapper.insertSelective(productParameter);
				}
			}
			/*--- 保存商品参数值end  ---*/
			
		}else {
			//新增商品数据
			product.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			product.setIsDelete(0);
			product.setStatus(4);
			result = saveSelective(product);
			
			Suppliers supplier = suppliersMapper.selectByPrimaryKey(product.getSupplierId());
//			if(supplier != null && supplier.getCode() != null){
//				product.setSku(supplier.getCode() + product.getId());
//			}
			result = updateNotNull(product);
			/*---保存图片----*/
			if (result > 0 && !CollectionUtils.isEmpty(product.getPics())) {
				List<ProductPics> pics = product.getPics();
				for (int i = 0; i < pics.size(); i++) {
					ProductPics productPics = pics.get(i);
					productPics.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					productPics.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					productPics.setPriority((float) i + 1);
					productPics.setProductId(product.getId());
					//生成大图、中图、缩略图
					HashMap<String, String> map = createImageExt(productPics.getSource());
					productPics.setLarge(map.get("largePath"));
					productPics.setMedium(map.get("mediumPath"));
					productPics.setThumbnail(map.get("thumbnailPath"));
					productPicsMapper.insertSelective(productPics);
				}
			}
			/*---保存图片 end----*/
			
			/*--- 保存商品参数值  ---*/
			List<ProductParameter> parameters = product.getProductParameters();
			if (result > 0 && !CollectionUtils.isEmpty(parameters)) {
				for (ProductParameter productParameter : parameters) {
					productParameter.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					productParameter.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					productParameter.setProductId(product.getId());
					productParameterMapper.insertSelective(productParameter);
				}
			}
			/*--- 保存商品参数值end  ---*/
			
		}
		resultMap.put("status", result);
		resultMap.put("message", "");
		return resultMap;
	}

	@Override
	public List<Products> find(Map<String, Object> map) {
		return productsMapper.find(map);
	}

	@Override
	public Products getById(Long id) {
		return productsMapper.getById(id);
	}

	@Override
	public int updateStatus(Long id, Integer status, Long userId) {
		if (status != null) {
			Products entity = new Products();
			entity.setId(id);
			entity.setStatus(status);
			entity.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			//更新商品状态
			int result = updateNotNull(entity);
			if (result > 0) {
				//添加上下架记录
				UpperLowerHistory upperLowerHistory = new UpperLowerHistory();
				upperLowerHistory.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				upperLowerHistory.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				upperLowerHistory.setProductId(id);
				upperLowerHistory.setOperateId(userId);
				switch(status){
				case 1:
					//申请上架
					upperLowerHistory.setType(1);
					upperLowerHistory.setReason("供应商提交商品上架审核");
					upperLowerHistoryMapper.insertSelective(upperLowerHistory);
					break;
				case 2:
					//退回
					upperLowerHistory.setType(2);
					upperLowerHistory.setReason("管理员审核退回");
					upperLowerHistory.setStatus(2);
					upperLowerHistoryMapper.insertSelective(upperLowerHistory);
					break;
				case 3:
					//上架
					upperLowerHistory.setType(3);
					upperLowerHistory.setReason("管理员审核通过商品上架");
					upperLowerHistory.setStatus(1);
					upperLowerHistoryMapper.insertSelective(upperLowerHistory);
					break;
				case 333:
					//上架
					upperLowerHistory.setType(3);
					upperLowerHistory.setReason("自动审核：商品协议价不高于大数据合理价，自动上架");
					upperLowerHistory.setStatus(1);
					upperLowerHistoryMapper.insertSelective(upperLowerHistory);
					break;
				case 4:
					//下架
					upperLowerHistory.setType(4);
					upperLowerHistory.setReason("商品下架");
					upperLowerHistoryMapper.insertSelective(upperLowerHistory);
					break;
				}
			}
			return result;
		}else {
			return 0;
		}
	}

	@Override
	public List<Products> findMyAgentProducts(Map<String, Object> map) {
		return productsMapper.findMyAgentProducts(map);
	}

	@Override
	public Map<String, Object> applyUpper(Long id, String mySiteCode, Long userId) {
		int result = 0;
		Products product = productsMapper.selectByPrimaryKey(id);
		if (product != null) {
			Protocol protocol = protocolMapper.selectByPrimaryKey(product.getProtocolId());
			if (protocol != null && protocol.getStatus() == 2 && protocol.getIsDelete() == 0
					&& DateUtilEx.compareDate(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN), protocol.getEndTime(), DateUtilEx.DATE_PATTERN) == -1) {
					
					/**
					 * 1:人工审核的，不管大数据获取的到或获取不到，都到待审核
					 */
					result = updateStatus(id, 1, userId);
					return ResultUtil.resultMessage(result, "");
					
				}else {
					return ResultUtil.resultMessage(result, "商品品目异常！");
				}
			}else {
				return ResultUtil.resultMessage(result, "申请失败，商品所属协议未执行或已失效");
			}
	}

	@Override
	public void asynPolymerization(Long productId) {
		polymerization(productId);
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public Map<String, Object> polymerization(Long productId) {
		log.info(productId+"商品聚合开始---------------------------------");
		int result = 0;
		String msg = "";
		Products product = productsMapper.getById(productId);
		if (product != null) {
			if (product.getBrandId() != null && product.getCatalogId() != null
					&& StringUtils.isNotBlank(product.getModel())) {
				try {
					Commodities record = new Commodities();
					record.setBrandId(product.getBrandId());//聚合品牌
					record.setCatalogId(product.getCatalogId());//聚合品目
					record.setModel(product.getModel());//聚合型号
					List<Commodities> list = commoditiesMapper.select(record);
					if (CollectionUtils.isEmpty(list)) {
						//不存在相同品目、品牌、型号的聚合商品
						result = saveCommoditiesAndUpdateProduct(product);
					}else {
						//聚合商品的关键参数
						List<ProductParameter> productParameters = product.getProductParameters();
						if (CollectionUtils.isNotEmpty(productParameters)) {
							Boolean isExist = false;//是否存在该商品参数的聚合
							Long commoditieId = null;
							f1:
								for (Commodities commoditie : list) {
									Boolean flag = true;
									HashMap<String, Object> map = new HashMap<String, Object>();
									map.put("commoditiesId", commoditie.getId());
									f2:
										for (ProductParameter productParameter : productParameters) {
											Parameter parameterExm = new Parameter();
											parameterExm.setStandParamId(productParameter.getStandParamId());
											parameterExm.setCatalogsId(product.getGovCatalogId());
											List<Parameter> parameters = parameterMapper.select(parameterExm);
											if (CollectionUtils.isNotEmpty(parameters) && parameters.size() == 1 && "true".equals(parameters.get(0).getIsKey())) {
												map.put("standParamId", productParameter.getStandParamId());
												map.put("ttype", productParameter.getTtype());
												if (productParameter.getTtype() == 1) {
													//单选
													map.put("standValueId", productParameter.getStandValueId());
													List<CommoditiesParameter> findList = commoditiesParameterMapper.find(map);
													if (CollectionUtils.isEmpty(findList)) {
														//未找到该聚合参数
														flag = false;
														break f2;
													}
												}else if (productParameter.getTtype() == 2) {
													//复选
													List<CommoditiesParameter> findList = commoditiesParameterMapper.find(map);
													if (CollectionUtils.isEmpty(findList)) {
														//未找到该聚合参数
														flag = false;
														break f2;
													}else {
														String prodstandValueIds = productParameter.getStandValueId();//商品参数
														String[] prodstandValueIdArry = prodstandValueIds.split(",");
														Arrays.sort(prodstandValueIdArry);
														String commStandValueIds = findList.get(0).getStandValueId();//聚合商品参数
														String[]commStandValueIdArry = commStandValueIds.split(",");
														Arrays.sort(commStandValueIdArry);
														if (Arrays.equals(prodstandValueIdArry, commStandValueIdArry)) {
															
														}else {
															//未找到该聚合参数
															flag = false;
															break f2;
														}
													}
												}else {
													//手录
													map.put("paramValue", productParameter.getParamValue());
													List<CommoditiesParameter> findList = commoditiesParameterMapper.find(map);
													map.remove("paramValue");
													if (CollectionUtils.isEmpty(findList)) {
														//未找到该聚合参数
														flag = false;
														break f2;
													}
												}
											}else {
												msg = productParameter.getId()+"参数错误";
												log.info(productParameter.getId()+"参数错误");
											}
										}
									//存在该产品所有参数数据的聚合商品
									if (flag) {
										isExist = true;
										commoditieId = commoditie.getId();
										break f1;
									}
								}
							if (isExist && commoditieId != null) {
								//存在相同品目、品牌、型号、各关键参数值得聚合商品
								log.info("存在相同品目、品牌、型号、各关键参数值得聚合商品");
								//更新商品信息：商品关联聚合商品
								Products p = new Products();
								p.setId(productId);
								p.setCommoditiesId(list.get(0).getId());
								result = updateNotNull(p);
							}else {
								//不存在相同品目、品牌、型号、各关键参数值的聚合商品
								log.info("不存在相同品目、品牌、型号、各关键参数值的聚合商品");
								//新建聚合商品信息
								result = saveCommoditiesAndUpdateProduct(product);
							}
						}else {
							msg = "商品没有参数";
							log.info(product.getId()+"商品没有参数");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				log.info(productId+"商品聚合结束---------------------------------");
			}else {
				msg = "商品品目、品牌、型号错误";
			}
		}else {
			msg = productId + "：商品未找到";
			log.info(productId + "：商品未找到");
		}
		return ResultUtil.resultMessage(result, msg);
	}

	@Override
	public Integer selectProductCount(Map<String, Object> map) {
		return productsMapper.selectProductCount(map);
	}

	@Override
	public List<Products> selectProductPage(Map<String, Object> map) {
		return productsMapper.selectProductPage(map);
	}

	@Override
	public String emallAutoRefreshDownStatus(Suppliers supplier, Products product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveProductImages(
			Suppliers supplier, String sku) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> updateEmallProducts(Suppliers supplier, String sku, Long catelogId, boolean is_catelog) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String status = "error";
		sku = sku.trim();
		if (supplier != null && StringUtils.isNotBlank(supplier.getCode()) && StringUtils.isNotBlank(sku)) {
			String eMallCode = supplier.getCode();
			if (EmallConstant.MALL_JD_CODE.equals(eMallCode)) {
				//京东
				//1.更新商品信息
				resultMap = updateJDProductsBySku(supplier, sku, catelogId, is_catelog);
				status = (String) resultMap.get("status");
				if ("success".equals(status)) {
					//2.更新上下架变更
					setJDProductIsMarketable(supplier, sku);
					//3.更新价格
					setJDProductPrice(supplier, sku);
					//4.校验上下架
					validate(supplier, sku);
				}
			} else if (EmallConstant.MALL_SN_CODE.equals(eMallCode)) {
				//苏宁
				//1.更新商品信息
				resultMap = updateSNProductsBySku(supplier, sku);
				status = (String) resultMap.get("status");
				if ("success".equals(status)) {
					//2.更新上下架变更
					setSNProductIsMarketable(supplier, sku);
					//3.更新价格
					setSNProductPrice(supplier, sku);
					//4.校验上下架
					validate(supplier, sku);
				}
			} else if (EmallConstant.MALL_STB_CODE.equals(eMallCode)){
				//史泰博
				resultMap = updateSTBProductsBySku(supplier, sku);
				status = (String) resultMap.get("status");
				if ("success".equals(status)) {
					//2.更新上下架变更
					setSTBProductIsMarketable(supplier, sku);
					//3.更新价格
					setSTBProductPrice(supplier, sku);
					//4.校验上下架
					validate(supplier, sku);
				}
			} else if (eMallCode.equals(EmallConstant.MALL_DL_CODE)){
				//小电商
				//1.更新商品信息
				resultMap = updateEmallProductsBySku(supplier, sku);
				status = (String) resultMap.get("status");
				if ("success".equals(status)) {
					//2.设置商品上下架
					setProductIsMarketable(supplier, sku);
					
					//3.更新商品价格
					setProductPrice(supplier, sku);
					
					//4.校验上下架
					validate(supplier, sku);
				}
			}else {
				return ResultUtil.resultMessage(0, "当前供应商非电商");
			}
			return resultMap;
		}else {
			return ResultUtil.resultMessage(0, "参数错误");
		}
	}

	@Override
	public Map<String, Object> updateEmallProductsByCatalog(Suppliers supplier, Long catalogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateParameter(Products product) {
		int result = 0;
		/**-----------产品关键参数校验不为空---start-------------------*/
		if (product != null && product.getId() != null && product.getGovCatalogId() != null) {
			Long govCatalogId = product.getGovCatalogId();
			//获取财政品目对应参数
			List<Parameter> parameters = parameterMapper.getParameterJoinByCatalogId(govCatalogId);
			String parameterMsg = "";
			boolean isVali = true;
			for (Parameter parameter : parameters) {
				if ("true".equals(parameter.getIsKey()) && !"品牌".equals(parameter.getName()) && !"型号".equals(parameter.getName())) {
					boolean flag = false;
					//录入的商品参数数据
					List<ProductParameter> productParameters = product.getProductParameters();
					pp:
					for (ProductParameter productParameter : productParameters) {
						if (parameter.getStandParamId().equals(productParameter.getStandParamId()) && StringUtils.isNotBlank(productParameter.getParamValue())) {
							flag = true;
							break pp;
						}
					}
					if (!flag) {
						isVali = false;
						parameterMsg +=  "[" + parameter.getName() + "]";
					}
				}
			}
			if (StringUtils.isNotBlank(parameterMsg) && !isVali) {
				return ResultUtil.resultMessage(0, "请录入" + parameterMsg + "参数信息");
			}
		}else {
			return ResultUtil.resultMessage(0, "请求参数异常");
		}
		/**-----------产品参数校验---end-------------------*/
		//保存商品参数值
		List<ProductParameter> parameters = product.getProductParameters();
		if (!CollectionUtils.isEmpty(parameters)) {
			//先删除之前保存的商品参数数据
			ProductParameter record = new ProductParameter();
			record.setProductId(product.getId());
			record.setPlatformCatalogsId(product.getCatalogId());
			productParameterMapper.delete(record);
			//保存最新的商品参数数据
			for (ProductParameter productParameter : parameters) {
				productParameter.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				productParameter.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				productParameter.setProductId(product.getId());
				result += productParameterMapper.insertSelective(productParameter);
			}
			Products entity = new Products();
			entity.setId(product.getId());
			entity.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			updateNotNull(entity);
		}
		return ResultUtil.resultMessage(result, "");
	}

	@Override
	public List<Products> findByCommoditiesId(Map<String, Object> map) {
		return productsMapper.findByCommoditiesId(map);
	}

	@Override
	public Boolean getProductState(String sku, String emallCode) {
		log.info("获取电商商品最新上架状态==sku:"+sku+"==emallCode:"+emallCode+"===========start==========================");
		Boolean state = false;//是否上架，true：上架
		if (StringUtils.isNotBlank(sku) && StringUtils.isNotBlank(emallCode)) {
			try {
				if (emallCode.equals(EmallConstant.MALL_JD_CODE)) {
					//获取京东最新价格
					log.info("获取京东最新上架状态============start==============");
					ProductStateApi api = new ProductStateApi(sku);
					ProductStateRes productStateRes = api.call();
					if (productStateRes != null && productStateRes.isSuccess()) {
						List<StateResultAttr> result = productStateRes.getResult();
						if (CollectionUtils.isNotEmpty(result) && result.get(0).getState() == 1) {
							state = true;
						}
					}
					log.info("获取京东最新上架状态============end==============");
				}else if (emallCode.equals(EmallConstant.MALL_SN_CODE)) {
					//获取苏宁最新价格
					log.info("获取苏宁最新上架状态============start==============");	
					SkuIds skuEntity = new SkuIds();
					skuEntity.setSkuId(sku);
					List<SkuIds> skuIds = Arrays.asList(skuEntity);
					www.tonghao.mall.api.sn.call.ProductStateApi api = new www.tonghao.mall.api.sn.call.ProductStateApi(skuIds);
					www.tonghao.mall.api.sn.resultwrap.ProductStateRes productStateRes = api.call();
					if (productStateRes != null && productStateRes.isSuccess()) {
						List<OnShelvesList> onShelvesList = productStateRes.getOnShelvesList();
						if (CollectionUtils.isNotEmpty(onShelvesList) && "1".equals(onShelvesList.get(0).getState())) {
							state = true;
						}
					}
					log.info("获取苏宁最新上架状态============end==============");
				}else if (emallCode.equals(EmallConstant.MALL_STB_CODE)) {
					//获取史泰博最新价格
					log.info("获取史泰博最新上架状态============start==============");	
					www.tonghao.mall.api.stb.call.ProductStateApi api = new www.tonghao.mall.api.stb.call.ProductStateApi(sku);
					www.tonghao.mall.api.stb.resultwrap.ProductStateRes call = api.call();
					if (call != null && call.isSuccess()) {
						List<ProductStateAttr> list = call.getList();
						if (CollectionUtils.isNotEmpty(list) && list.get(0).getState() == 1) {
							state = true;
						}
					}
					log.info("获取史泰博最新上架状态============end==============");
				}else {
					//获取其他小电商最新价格
					log.info("获取其他小电商最新上架状态============start==============");
					www.tonghao.mall.api.standard.call.ProductStateApi api = new www.tonghao.mall.api.standard.call.ProductStateApi(emallCode, sku);
					www.tonghao.mall.api.standard.resultwrap.ProductStateRes productStateRes = api.call();
					if (productStateRes != null && productStateRes.isSuccess()) {
						List<www.tonghao.mall.api.standard.attwrap.StateResultAttr> result = productStateRes.getResult();
						if (CollectionUtils.isNotEmpty(result) && result.get(0).getState() == 1) {
							state = true;
						}
					}
					log.info("获取其他小电商最新上架状态============end==============");
				}	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			log.info("参数错误");
		}
		log.info("获取电商商品最新上架状态==sku:"+sku+"==emallCode:"+emallCode+"===========end==========================");
		return state;
	}

	@Override
	public List<Map<String, Object>> selectEmallProducts(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> findByEmallSupplier(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectEmallBrand(List<Long> supplierList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> findSupplierProducts(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Boolean isEnergyProduct(Long catalogId, String model, Long brandId){
		if (catalogId != null && StringUtils.isNotBlank(model) && brandId != null) {
			Brand brand = brandMapper.selectByPrimaryKey(brandId);
			if (brand != null) {
				String mappingName = brand.getMappingName();
				if (StringUtils.isNotBlank(mappingName)) {
					Example example = new Example(EnergySavingProducts.class);
					Criteria createCriteria = example.createCriteria();
					String[] mappings = mappingName.split("\\|");
					List<String> asList = new ArrayList<String>(Arrays.asList(mappings)) ;
					asList.add(brand.getName());
					createCriteria.andIn("brand", asList);
					createCriteria.andEqualTo("categoryId", catalogId+"");
					createCriteria.andEqualTo("model", model);
					int resultCount = energySavingProductsMapper.selectCountByExample(example);
					if(resultCount > 0){
						return true;
					}else {
						return false;
					}
				}
			}
		}
		return null;
	}
	
	/**  
	 * <p>Title: createImageExt</p>  
	 * <p>Description: 生成缩略图等</p>  
	 * @author Yml  
	 * @param sourcePath
	 * @return  
	 */  
	private static HashMap<String, String> createImageExt(String sourcePath) {
		HashMap<String, String> map = new HashMap<String, String>();
		//原图片绝对位置如：E:\spe\product\\upload\image\201811\21\0bb4aa43-40b3-48aa-b3b3-630fdb117426.png
		String absolutePath = sourcePath.replace(Constant.imgSiteUrl, Constant.UPLOAD_BASE_PATH);
		//原图片绝对路径,如：E:\spe\product\\upload\image\201811\21\0bb4aa43-40b3-48aa-b3b3-630fdb117426
		String realSourcePath = absolutePath.substring(0,absolutePath.lastIndexOf("."));
		
		String largePath = realSourcePath +"-large.jpg";
		String mediumPath = realSourcePath +"-medium.jpg";
		String thumbnailPath = realSourcePath +"-thumbnail.jpg";
		
		File sourceTempFile = new File(absolutePath);
		File largeTempFile = new File(largePath);
		File mediumTempFile = new File(mediumPath);
		File thumbnailTempFile = new File(thumbnailPath);
		//图片压缩尺寸暂时在这里定义
		ImageUtil.zoom(sourceTempFile, largeTempFile, 800, 800);
		ImageUtil.zoom(sourceTempFile, mediumTempFile, 300, 300);
		ImageUtil.zoom(sourceTempFile, thumbnailTempFile, 170, 170);
		map.put("largePath", largePath.replace(Constant.UPLOAD_BASE_PATH, Constant.imgSiteUrl));
		map.put("mediumPath", mediumPath.replace(Constant.UPLOAD_BASE_PATH, Constant.imgSiteUrl));
		map.put("thumbnailPath", thumbnailPath.replace(Constant.UPLOAD_BASE_PATH, Constant.imgSiteUrl));
		return map;
	}
	
	/**  
	 * <p>Title: getProductNewPrice</p>
	 * <p>Description: 获取电商商品最新协议价和市场价</p>  
	 * @author YML 
	 * @param sku
	 * @param emallCode
	 * @return 
	 */
	public HashMap<String, BigDecimal> getProductNewPrice(String sku, String emallCode){
		log.info("获取电商商品最新协议价和市场价==sku:"+sku+"==emallCode:"+emallCode+"===========start==========================");
		HashMap<String, BigDecimal> map = null;
		/*if (StringUtils.isNotBlank(sku) && StringUtils.isNotBlank(emallCode)) {
			try {
				if (emallCode.equals(EmallConstant.MALL_JD_CODE)) {
					//获取京东最新价格
					log.info("获取京东最新价格============start==============");
					ProductPricesApi api = new ProductPricesApi(sku);
					ProductPriceRes productPriceRes = api.call();
					if (productPriceRes != null && productPriceRes.isSuccess()) {
						List<PriceAttr> priceAttrs = productPriceRes.getResult();
						if (CollectionUtils.isNotEmpty(priceAttrs)) {
							map = Maps.newHashMap();
							map.put("price", priceAttrs.get(0).getPrice());//协议价
							map.put("mallPrice", priceAttrs.get(0).getJdPrice());//市场价
						}
					}
					log.info("获取京东最新价格============end==============");
				}else if (emallCode.equals(EmallConstant.MALL_SN_CODE)) {
					//获取苏宁最新价格
					log.info("获取苏宁最新价格============start==============");
					com.suning.api.entity.govbus.PriceQueryRequest.Skus skuEntity = new com.suning.api.entity.govbus.PriceQueryRequest.Skus();
					skuEntity.setSkuId(sku);
					List<Skus> skus = Arrays.asList(skuEntity);

					ProductPriceApi api = new ProductPriceApi(skus, EmallConstant.CITY_JINAN_ID);
					www.tonghao.mall.api.sn.resultwrap.ProductPriceRes productPriceRes = api.call();
					if (productPriceRes != null && productPriceRes.isSuccess()) {
						List<PriceSkus> resultInfo = productPriceRes.getResultInfo();
						if (CollectionUtils.isNotEmpty(resultInfo)) {
							map = Maps.newHashMap();
							if (StringUtils.isNotBlank(resultInfo.get(0).getPrice())) {
								map.put("price", new BigDecimal(resultInfo.get(0).getPrice()));//协议价
							}
							if (StringUtils.isNotBlank(resultInfo.get(0).getSnPrice())) {
								map.put("mallPrice",  new BigDecimal(resultInfo.get(0).getSnPrice()));//市场价
							}
						}
					}
					log.info("获取苏宁最新价格============end==============");
				}else {
					//获取其他小电商最新价格
					log.info("获取其他小电商最新价格============start==============");
					www.tonghao.mall.api.standard.call.ProductPricesApi api = new www.tonghao.mall.api.standard.call.ProductPricesApi(emallCode, sku);
					www.tonghao.mall.api.standard.resultwrap.ProductPriceRes productPriceRes = api.call();
					if (productPriceRes != null && productPriceRes.isSuccess()) {
						List<www.tonghao.mall.api.standard.attwrap.PriceAttr> priceAttrs = productPriceRes.getResult();
						if (CollectionUtils.isNotEmpty(priceAttrs)) {
							map = Maps.newHashMap();
							map.put("price", priceAttrs.get(0).getPrice());//协议价
							map.put("mallPrice", priceAttrs.get(0).getMall_price());//市场价
						}
					}
					log.info("获取其他小电商最新价格============end==============");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			log.info("参数错误");
		}*/
		log.info("获取电商商品最新协议价和市场价=======================end==========================");
		return map;
	}
	
	/**  
	 * <p>Title: saveCommodities</p>
	 * <p>Description: 保存聚合商品信息</p>  
	 * @author YML 
	 * @return 
	 */
	public int saveCommoditiesAndUpdateProduct(Products product){
		int result = 0;
		Commodities record = new Commodities();
		record.setBrandId(product.getBrandId());//聚合品牌
		record.setCatalogId(product.getCatalogId());//聚合品目
		record.setModel(product.getModel());//聚合型号
		record.setBrandName(product.getBrandName());
		record.setCatalogName(product.getCatalogName());
		record.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		record.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		result = commoditiesMapper.insertSelective(record);
		if (result > 0) {
			//保存聚合商品参数信息
			for (ProductParameter productParameter : product.getProductParameters()) {
				Parameter parameter = new Parameter();
				parameter.setStandParamId(productParameter.getStandParamId());
				parameter.setCatalogsId(product.getGovCatalogId());
				List<Parameter> list = parameterMapper.select(parameter);
				if (CollectionUtils.isNotEmpty(list) && list.size() == 1 && "true".equals(list.get(0).getIsKey())) {
					CommoditiesParameter commoditiesParameter = new CommoditiesParameter();
					commoditiesParameter.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					commoditiesParameter.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					commoditiesParameter.setCommoditiesId(record.getId());
					commoditiesParameter.setParamValue(productParameter.getParamValue());
					commoditiesParameter.setPosition(productParameter.getPosition());
					commoditiesParameter.setStandParamId(productParameter.getStandParamId());
					commoditiesParameter.setStandValueId(productParameter.getStandValueId());
					commoditiesParameter.setTtype(productParameter.getTtype());
					commoditiesParameterMapper.insertSelective(commoditiesParameter);
				}else {
					log.info("参数获取错误");
				}
			}
			//更新商品信息：商品关联聚合商品
			Products p = new Products();
			p.setId(product.getId());
			p.setCommoditiesId(record.getId());
			updateNotNull(p);
		}
		return result;
	}

	@Override
	public int productAudit(UpperLowerHistory upperLowerHistory, Long userId) {
		String reason = "";
		Products entity = new Products();
		entity.setId(upperLowerHistory.getProductId());
		entity.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		//审核通过
		if (upperLowerHistory.getStatus() == 1) {
			//设置商品为上架状态
			entity.setStatus(3);
			reason = "管理员审核商品通过";
		}
		//审核不通过
		if (upperLowerHistory.getStatus() == 2) {
			//设置商品为退回状态
			entity.setStatus(2);
			reason = "管理员审核商品退回";
		}
		//更新商品状态
		int result = updateNotNull(entity);
		if (result > 0) {
			//添加上下架记录
			upperLowerHistory.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			upperLowerHistory.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			upperLowerHistory.setOperateId(userId);
			upperLowerHistory.setType(1);
			if (StringUtils.isBlank(upperLowerHistory.getReason())) {
				upperLowerHistory.setReason(reason);
			}
			upperLowerHistoryMapper.insertSelective(upperLowerHistory);
		}
		return result;
	}
	
	/**====================================京东start============================================================*/
	private Map<String, Object> updateJDProductsBySku(Suppliers supplier,
			String sku,Long catelogId,boolean is_catelog) {
		int result = 0;
		String emallCode = supplier.getCode();
		String isNew="false";
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				ProductDetailApi productDetailApi = new ProductDetailApi(sku);
				ProductRes productDetail = productDetailApi.call();
				if(productDetail.isSuccess()){
					ProductOther pro = (ProductOther) productDetail.getResult().getProduct();
					//查询映射品目
					PlatformCatalogs platformCatalog = null;
					if(is_catelog){
						EmallCatalogsMapping entity = new EmallCatalogsMapping();
						entity.setCatalogsId(catelogId);
						entity.setEmallCode(emallCode);
						EmallCatalogsMapping ec = emallCatalogsMappingMapper.selectOne(entity);
						if(ec != null && ec.getCatalogsId()!= null){
							platformCatalog = platformCatalogsMapper.selectByPrimaryKey(ec.getCatalogsId());
							if(platformCatalog == null){
								return ResultUtil.resultMessage(result, "商品信息接口调用失败，sku:" + sku + ".信息：品目" + catelogId + "未映射");
							}
						} else {
							return ResultUtil.resultMessage(result, "商品信息接口调用失败，sku:" + sku + ".信息：品目" + catelogId + "未映射");
						}
					}
					
					/*String category = pro.getCategory();
					if(category.length() > 0){
						category = category.split(";")[category.split(";").length-1];
					}*/
					/*EmallCatalogsMapping entity = new EmallCatalogsMapping();
					entity.setEmallCatalogsId(category);
					entity.setEmallCode(emallCode);
					EmallCatalogsMapping ec = emallCatalogsMappingMapper.selectOne(entity);
					PlatformCatalogs platformCatalog = null;
					if(ec != null && ec.getCatalogsId()!= null){
						platformCatalog = platformCatalogsMapper.selectByPrimaryKey(ec.getCatalogsId());
						if(platformCatalog == null){
							return ResultUtil.resultMessage(result, "商品信息接口调用失败，sku:" + sku + ".信息：品目" + category + "未映射");
						}
					} else {
						return ResultUtil.resultMessage(result, "商品信息接口调用失败，sku:" + sku + ".信息：品目" + category + "未映射");
					}*/
					
					Products product = null;
					Products selProducts = new Products();
					selProducts.setSku(sku);
					selProducts.setSupplierId(supplier.getId());
					selProducts.setIsDelete(0);
					product = productsMapper.selectOne(selProducts);
					if (product == null) {
						isNew="true";
						product = new Products();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("supplierId", supplier.getId());
						map.put("endTime", DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
						map.put("types", 1);
						List<Protocol> protocols = protocolMapper.getProtocolBySupplier(map);
						Protocol protocol=null;
						if(!CollectionUtils.isEmpty(protocols)){
							protocol=protocols.get(0);
							if(protocol != null){
								product.setProtocolId(protocol.getId());
								product.setProtocolName(protocol.getName());
							}
						}
						product.setCreatedAt(DateUtilEx.timeFormat(new Date()));
						product.setIsDelete(0);
						product.setSupplierId(supplier.getId());
						product.setSupplierName(supplier.getName());
					}
					product.setCatalogId(platformCatalog.getId());
					product.setCatalogName(platformCatalog.getName());
					product.setGovCatalogId(platformCatalog.getCatalogId());
					
					Brand brand = mappingBrand(pro.getBrandName());
					if(brand != null){
						product.setBrandId(brand.getId());
						product.setBrandName(brand.getName());
					}
					
					product.setName(pro.getName());
					product.setSku(sku);
					product.setModel(pro.getModel());
					if (StringUtils.isNotBlank(pro.getWeight())) {
						product.setWeight(pro.getWeight());
					}
					product.setStatus(4);
					product.setParam(pro.getParam());
					product.setMallBrandName(pro.getBrandName());
					if(!StringUtils.isBlank(pro.getImagePath())){
						product.setCoverUrl("http://img13.360buyimg.com/n0/"+pro.getImagePath());
					}
					product.setDetail(pro.getIntroduction());
					product.setWare(pro.getWareQD());
					product.setProductArea(pro.getProductArea());
					product.setUpc(pro.getUpc());
					product.setAfterSaleService(pro.getShouhou());
					product.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					product.setFactoryURL("https://item.jd.com/"+ sku +".html");
					if (product != null && product.getId() != null) {
						synchronized (this) {
							result = updateNotNull(product);
						}
					}else {
						synchronized (this) {
							result = saveSelective(product);
						}
					}
					
					if (result > 0) {
						Example example=new Example(ProductPics.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("productId", product.getId());
						List<ProductPics> pic_list = productPicsMapper.selectByExample(example);
						if(!CollectionUtils.isEmpty(pic_list)){
							for (ProductPics productPics : pic_list) {
								productPicsMapper.deleteByPrimaryKey(productPics.getId());
							}
							synchronized (this) {
								saveJDproductPics(product);
							}
						}else{
							synchronized (this) {
								saveJDproductPics(product);
							}
						}
					}
				}else {
					return ResultUtil.resultMessage(result, "商品详情接口调用失败，sku = " + sku + ".信息：" + productDetail.getResultMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("商品详情接口调用异常，sku = " + sku);
				return ResultUtil.resultMessage(result, "商品详情接口调用异常，sku = " + sku);
			}
		} else {
			ResultUtil.resultMessage(result, "电商code或sku未获取到");
		}
		return ResultUtil.resultMessage(result, isNew);
	}
	
	/**  
	 * <p>Title: saveJDproductPics</p>
	 * <p>Description: 保存京东商品图片</p>  
	 * @author YML 
	 * @param product 
	 */
	private void saveJDproductPics(Products product) {
		try {
			ProductImageApi productImageApi = new ProductImageApi(product.getSku());
			ProductImageRes productImage = productImageApi.call();
		    if(productImage.isSuccess()){
		    	List<ImageResultAttr> result = productImage.getResult();
		    	if(!CollectionUtils.isEmpty(result)){
		    		ImageResultAttr imageResultAttr = result.get(0);
	    			List<www.tonghao.mall.api.jd.attwrap.Image> images = imageResultAttr.getImages();
	    			if(!CollectionUtils.isEmpty(images)){
			    		for (www.tonghao.mall.api.jd.attwrap.Image image : images) {
			    			ProductPics pics=new ProductPics();
			    			pics.setProductId(product.getId());
			    			pics.setLarge("http://img13.360buyimg.com/n0/"+image.getPath());
			    			pics.setMedium("http://img13.360buyimg.com/n0/"+image.getPath());
			    			pics.setSource("http://img13.360buyimg.com/n0/"+image.getPath());
			    			pics.setPriority(Float.valueOf(image.getPosition()));
			    			pics.setIsDelete(0);
			    			pics.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			    			pics.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
							productPicsMapper.insert(pics);
						}
	    			}else {
	    				log.info("未获取到商品图片，sku:" + product.getSku());
					}
		    	}
		    }else {
				log.info("商品图片接口调用失败，sku:" + product.getSku() + ",信息:" + productImage.getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("商品图片接口调用失败，sku:" + product.getSku());
		}
	}
	/**  
	 * <p>Title: setJDProductPrice</p>
	 * <p>Description: 更新京东商品价格</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	private void setJDProductPrice(Suppliers supplier, String sku) {
		String emallCode = supplier.getCode();
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				Products selProducts = new Products();
				selProducts.setSku(sku);
				selProducts.setSupplierId(supplier.getId());
				selProducts.setIsDelete(0);
				Products product = productsMapper.selectOne(selProducts);
				if (product != null) {
					ProductPricesApi productPricesApi = new ProductPricesApi(sku);
					ProductPriceRes productPrices = productPricesApi.call();
					if(productPrices.isSuccess()){
						List<PriceAttr> result_price = productPrices.getResult();
						if(!CollectionUtils.isEmpty(result_price)){
							PriceAttr priceAttr = result_price.get(0);
							product.setPrice(priceAttr.getPrice());
							product.setMarketPrice(priceAttr.getJdPrice());
							product.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
							updateNotNull(product);
						}else {
							log.warn("未获取到价格，sku = " + sku);
						}
					}else {
						log.warn("京东价格接口调用失败，sku = " + sku);
					}
				}else {
					log.warn("京东价格接口调用失败，sku = " + sku + "未查到对应商品");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("京东价格接口调用异常，sku = " + sku);
			}
		} else {
			log.warn("电商code或sku未获取到");
		}
	}
	/**  
	 * <p>Title: setJDProductIsMarketable</p>
	 * <p>Description: 更新京东商品上下架状态</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	private void setJDProductIsMarketable(Suppliers supplier, String sku) {
		String emallCode = supplier.getCode();
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				Products selProducts = new Products();
				selProducts.setSku(sku);
				selProducts.setSupplierId(supplier.getId());
				selProducts.setIsDelete(0);
				Products product = productsMapper.selectOne(selProducts);
				if(product != null){
					Boolean productState = getProductState(sku, supplier.getCode());
					if (productState) {
						product.setStatus(3);
						insertHistory(product,EmallConstant.EMALL_SUCCESS,true);
					}else {
						product.setStatus(4);
						insertHistory(product,EmallConstant.EMALL_ERROR,false);
					}
					updateNotNull(product);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("商品详情接口调用异常，sku = " + sku);
			}
		} else {
			log.warn("电商code或sku未获取到");
		}
	}
	
	/**====================================京东end============================================================*/
	/**====================================苏宁start============================================================*/
	/**  
	 * <p>Title: setSNProductPrice</p>
	 * <p>Description: 更新苏宁商品价格</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	private void setSNProductPrice(Suppliers supplier, String sku) {
		String emallCode = supplier.getCode();
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				List<com.suning.api.entity.govbus.PriceQueryRequest.Skus> skus_s = new ArrayList<com.suning.api.entity.govbus.PriceQueryRequest.Skus>();
				com.suning.api.entity.govbus.PriceQueryRequest.Skus skuEntity = new com.suning.api.entity.govbus.PriceQueryRequest.Skus();
				skuEntity.setSkuId(sku);
				skus_s.add(skuEntity);
				ProductPriceApi api=new ProductPriceApi(skus_s, EmallConstant.CITY_JINAN_ID);
				www.tonghao.mall.api.sn.resultwrap.ProductPriceRes price = api.call();
				if(price.isSuccess()){
					List<PriceSkus> resultInfo = price.getResultInfo();
					if(!CollectionUtils.isEmpty(resultInfo)){
						Products selProducts = new Products();
						selProducts.setSku(sku);
						selProducts.setSupplierId(supplier.getId());
						selProducts.setIsDelete(0);
						Products products = productsMapper.selectOne(selProducts);
						if(products != null){
							for (PriceSkus priceSkus : resultInfo) {
								if (priceSkus.getSkuId().equals(sku)) {
									products.setPrice(new BigDecimal(isBigDecimal(priceSkus.getPrice())));
									products.setMarketPrice(new BigDecimal(isBigDecimal(priceSkus.getSnPrice())));
									updateNotNull(products);
								}
							}
						}
					}
				} else {
					log.warn("商品价格接口调用异常，sku = " + sku + ".信息：" + price.getError_msg());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("商品价格接口调用异常，sku = " + sku);
			}
		}else {
			log.warn("电商code或sku未获取到");
		}
	}
	public String isBigDecimal(String str){
		if(str == null || "".equals(str)){
			return "0";
		}
		return str;
	}
	
	/**  
	 * <p>Title: setSNProductIsMarketable</p>
	 * <p>Description: 更新苏宁商品上下架状态</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	private void setSNProductIsMarketable(Suppliers supplier, String sku) {
		String emallCode = supplier.getCode();
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				List<com.suning.api.entity.govbus.BatchProdSaleStatusGetRequest.SkuIds> skuIds = new ArrayList<com.suning.api.entity.govbus.BatchProdSaleStatusGetRequest.SkuIds>();
				com.suning.api.entity.govbus.BatchProdSaleStatusGetRequest.SkuIds ids = new com.suning.api.entity.govbus.BatchProdSaleStatusGetRequest.SkuIds();
				ids.setSkuId(sku);
				skuIds.add(ids);
				www.tonghao.mall.api.sn.call.ProductStateApi api = new www.tonghao.mall.api.sn.call.ProductStateApi(skuIds);
				www.tonghao.mall.api.sn.resultwrap.ProductStateRes state = api.call();
				if(state.isSuccess()){
					List<OnShelvesList> onShelvesList = state.getOnShelvesList();
					if(!CollectionUtils.isEmpty(onShelvesList)){
						Products selProducts = new Products();
						selProducts.setSku(sku);
						selProducts.setSupplierId(supplier.getId());
						selProducts.setIsDelete(0);
						Products products = productsMapper.selectOne(selProducts);
						if(products!=null){
							for (OnShelvesList os : onShelvesList) {
								if (os.getSkuId().equals(sku)) {
									//下架状态1：在售0：下架
									if("1".equals(os.getState())){
										products.setStatus(3);
										insertHistory(products,EmallConstant.EMALL_SUCCESS,true);
									} else {
										products.setStatus(4);
										insertHistory(products,EmallConstant.EMALL_ERROR,false);
									}
									updateNotNull(products);
								}
							}
						}
					}
				}else {
					log.warn("商品上下架接口调用失败，sku = " + sku + ".信息：" + state.getError_msg());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("商品上下架接口调用异常，sku = " + sku);
			}
		}else {
			log.warn("电商code或sku未获取到");
		}
	}
	/**  
	 * <p>Title: updateSNProductsBySku</p>
	 * <p>Description: 苏宁根据sku商品更新</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku
	 * @return 
	 */
	private Map<String, Object> updateSNProductsBySku(Suppliers supplier,
			String sku) {
		int result = 0;
		String msg = "";
		String emallCode = supplier.getCode();
		String isNew="false";
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				www.tonghao.mall.api.sn.call.ProductDetailApi api = new www.tonghao.mall.api.sn.call.ProductDetailApi(sku);
				www.tonghao.mall.api.sn.resultwrap.ProductDetailRes productDetail = api.call();
				if(productDetail.isSuccess()){
					//品目校验
					EmallCatalogsMapping emall_catalog = new EmallCatalogsMapping();
					emall_catalog.setEmallCode(supplier.getCode());
					emall_catalog.setEmallId(supplier.getId());
					emall_catalog.setEmallCatalogsId(productDetail.getResult().getCategory());
					EmallCatalogsMapping emallCatalog = emallCatalogsMappingMapper.selectOne(emall_catalog);
					PlatformCatalogs platformCatalog = null;
					if(emallCatalog != null && emallCatalog.getCatalogsId() != null){
						platformCatalog = platformCatalogsMapper.selectByPrimaryKey(emallCatalog.getCatalogsId());
						if (platformCatalog == null) {
							msg = "商品信息接口调用失败，sku:" + sku + ".信息：品目" + productDetail.getResult().getCategory() + "未映射";
							return ResultUtil.resultMessage(result, msg);
						}
					} else {
						msg = "商品信息接口调用失败，sku:" + sku + ".信息：品目" + productDetail.getResult().getCategory() + "未映射";
						return ResultUtil.resultMessage(result, msg);
					}
					Products products = null;
					Products selProducts = new Products();
					selProducts.setSku(sku);
					selProducts.setSupplierId(supplier.getId());
					selProducts.setIsDelete(0);
					products = productsMapper.selectOne(selProducts);
					
					if(products == null){
						isNew="true";
						products = new Products();
						products.setCreatedAt(DateUtilEx.timeFormat(new Date()));
						products.setSupplierId(supplier.getId());
						products.setSupplierName(supplier.getName());
						products.setSku(sku);
						//设置协议
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("supplierId", supplier.getId());
						map.put("endTime", DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
						map.put("types", 1);
						List<Protocol> protocols = protocolMapper.getProtocolBySupplier(map);
						Protocol protocol = null;
						if(!CollectionUtils.isEmpty(protocols)){
							protocol = protocols.get(0);
							products.setProtocolId(protocol.getId());
							products.setProtocolName(protocol.getName());
						}
					}
					products.setCatalogId(platformCatalog.getId());
					products.setCatalogName(platformCatalog.getName());
					products.setGovCatalogId(platformCatalog.getCatalogId());
					
					products.setName(productDetail.getResult().getName());
					products.setModel(productDetail.getResult().getModel());
					if (StringUtils.isNotBlank(productDetail.getResult().getWeight())) {
						products.setWeight(productDetail.getResult().getWeight());
					}
					products.setIsMarketable(new Byte("1"));
					products.setUnit(productDetail.getResult().getSaleUnit());
					products.setParam(productDetail.getResult().getProdParams().getDesc());
					products.setFactoryURL("http://product.suning.com/0000000000/"+ sku +".html");
					products.setMallBrandName(productDetail.getResult().getBrand());
					
					Brand brand = mappingBrand(productDetail.getResult().getBrand());
					if(brand != null){
						products.setBrandId(brand.getId());
						products.setBrandName(brand.getName());
					}
					
					products.setCoverUrl(productDetail.getResult().getImage());
					products.setDetail(productDetail.getResult().getIntroduction());
					products.setWare(productDetail.getResult().getPacklisting());
					products.setProductArea(productDetail.getResult().getProductArea());
					products.setUpc(productDetail.getResult().getUpc());
					products.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					products.setIsDelete(0);
					
					if(products != null && products.getId() != null){
						synchronized (this) {
							result = updateNotNull(products);
						}
					}else {
						synchronized (this) {
							result = saveSelective(products);
						}
					}
					if (result > 0) {
						Example example=new Example(ProductPics.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("productId", products.getId());
						List<ProductPics> pic_list = productPicsMapper.selectByExample(example);
						if(!CollectionUtils.isEmpty(pic_list)){
							for (ProductPics productPics : pic_list) {
								productPicsMapper.deleteByPrimaryKey(productPics.getId());
							}
							synchronized (this) {
								saveSNproductPic(products);
							}
						}else{
							synchronized (this) {
								saveSNproductPic(products);
							}
						}
					}else {
						msg = "苏宁  当前sku商品保存失败 sku="+sku;
					}
				} else {
					msg = "商品详情接口调用返回失败，sku = " + sku + ".信息：" + productDetail.getError_msg();
					log.warn(msg);
					return ResultUtil.resultMessage(result, msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg = "商品详情接口调用异常，sku = " + sku;
				log.warn(msg);
				return ResultUtil.resultMessage(result, msg);
			}
			return ResultUtil.resultMessage(result, isNew);
		} else {
			return ResultUtil.resultMessage(result, "电商code或sku未获取到");
		}
	}
	
	/**  
	 * <p>Title: saveSNproductPic</p>
	 * <p>Description: 保存苏宁商品图片</p>  
	 * @author YML 
	 * @param products 
	 */
	private void saveSNproductPic(Products products) {
		String sku = products.getSku();
		if (StringUtils.isNotBlank(sku)) {
			List<com.suning.api.entity.govbus.ProdImageQueryRequest.SkuIds> skuIds = new ArrayList<com.suning.api.entity.govbus.ProdImageQueryRequest.SkuIds>();
			com.suning.api.entity.govbus.ProdImageQueryRequest.SkuIds ids = new com.suning.api.entity.govbus.ProdImageQueryRequest.SkuIds();
			ids.setSkuId(sku);
			skuIds.add(ids);
			try {
				www.tonghao.mall.api.sn.call.ProductImageApi api= new www.tonghao.mall.api.sn.call.ProductImageApi(skuIds);
				www.tonghao.mall.api.sn.resultwrap.ProductImageRes images = api.call();
				if(images.isSuccess()){
					List<com.suning.api.entity.govbus.ProdImageQueryResponse.ResultInfo> resultInfo = images.getResultInfo();
					if(!CollectionUtils.isEmpty(resultInfo)){
						for (com.suning.api.entity.govbus.ProdImageQueryResponse.ResultInfo infos : resultInfo) {
							List<Urls> urls = infos.getUrls();
							if(!CollectionUtils.isEmpty(urls)){
								int cou=0;
								for (Urls ul : urls) {
									ProductPics productPics = new ProductPics();
									productPics.setProductId(products.getId());
									productPics.setLarge(ul.getPath());
									productPics.setMedium(ul.getPath());
									productPics.setSource(ul.getPath());
									productPics.setPriority((float)cou++);
									productPics.setIsDelete(0);
									productPics.setCreatedAt(DateUtilEx.timeFormat(new Date()));
									productPics.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
									productPicsMapper.insert(productPics);
								}
							}else{//下架
								insertHistory(products, EmallConstant.IMAGE_ERROR, false);
								products.setStatus(4);
								updateNotNull(products);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("苏宁商品图片接口调用失败，sku:" + sku);
			}
		}
	}
	/**====================================苏宁end============================================================*/

	/**====================================史泰博start============================================================*/
	private Map<String, Object> updateSTBProductsBySku(Suppliers supplier, String sku) {
		int result = 0;
		String emallCode = supplier.getCode();
		String isNew="false";
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				www.tonghao.mall.api.stb.call.ProductDetailApi productDetailApi = new www.tonghao.mall.api.stb.call.ProductDetailApi(sku);
				ProductDetailRes call = productDetailApi.call();
				if(call.isSuccess()){
					ProductDetailAttr attr = call.getAttr();
					//查询映射品目
					PlatformCatalogs entity = new PlatformCatalogs();
					entity.setId(Long.parseLong(attr.getCategory()));
					entity.setTreeDepth(3);
					entity.setIsDelete(0);
					entity.setIsParent("false");
					PlatformCatalogs platformCatalog = platformCatalogsMapper.selectOne(entity);
					if(platformCatalog == null){
						return ResultUtil.resultMessage(result, "品目未找到！");
					}
					
					Products product = null;
					Products selProducts = new Products();
					selProducts.setSku(sku);
					selProducts.setSupplierId(supplier.getId());
					selProducts.setIsDelete(0);
					product = productsMapper.selectOne(selProducts);
					if (product == null) {
						isNew="true";
						product = new Products();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("supplierId", supplier.getId());
						map.put("endTime", DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
						map.put("types", 1);
						List<Protocol> protocols = protocolMapper.getProtocolBySupplier(map);
						Protocol protocol=null;
						if(!CollectionUtils.isEmpty(protocols)){
							protocol=protocols.get(0);
							if(protocol != null){
								product.setProtocolId(protocol.getId());
								product.setProtocolName(protocol.getName());
							}
						}
						product.setCreatedAt(DateUtilEx.timeFormat(new Date()));
						product.setIsDelete(0);
						product.setSupplierId(supplier.getId());
						product.setSupplierName(supplier.getName());
					}
					product.setCatalogId(platformCatalog.getId());
					product.setCatalogName(platformCatalog.getName());
					product.setGovCatalogId(platformCatalog.getCatalogId());
					
					Brand brand = mappingBrand(attr.getBrand_name());
					if(brand != null){
						product.setBrandId(brand.getId());
						product.setBrandName(brand.getName());
					}
					
					product.setName(attr.getName());
					product.setSku(sku);
					product.setModel(attr.getModel());
					if (StringUtils.isNotBlank(attr.getWeight())) {
						product.setWeight(attr.getWeight());
					}
					product.setStatus(4);
					product.setParam(attr.getParam());
					product.setMallBrandName(attr.getBrand_name());
					product.setCoverUrl(attr.getImage_path());
					product.setDetail(attr.getIntroduction());
					product.setWare(attr.getWare());
					product.setProductArea(attr.getProductArea());
					product.setUpc(attr.getUpc());
					product.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					product.setFactoryURL("http://www.staples.cn/product/"+attr.getSku());
					if (product != null && product.getId() != null) {
						result = updateNotNull(product);
					}else {
						result = saveSelective(product);
					}
					
					if (result > 0) {
						Example example=new Example(ProductPics.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("productId", product.getId());
						List<ProductPics> pic_list = productPicsMapper.selectByExample(example);
						if(!CollectionUtils.isEmpty(pic_list)){
							for (ProductPics productPics : pic_list) {
								productPicsMapper.deleteByPrimaryKey(productPics.getId());
							}
							saveSTBproductPics(product);
						}else{
							saveSTBproductPics(product);
						}
					}
				}else {
					return ResultUtil.resultMessage(result, "商品详情接口调用失败，sku = " + sku + ".信息：" + call.getDesc());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("商品详情接口调用异常，sku = " + sku);
				return ResultUtil.resultMessage(result, "商品详情接口调用异常，sku = " + sku);
			}
		} else {
			ResultUtil.resultMessage(result, "电商code或sku未获取到");
		}
		return ResultUtil.resultMessage(result, "商品详情更新成功");
	}
	
	/**  
	 * <p>Title: saveSTBproductPics</p>
	 * <p>Description: 保存史泰博商品图片</p>  
	 * @author YML 
	 * @param product 
	 */
	private void saveSTBproductPics(Products product) {
		try {
			www.tonghao.mall.api.stb.call.ProductImageApi productImageApi = new www.tonghao.mall.api.stb.call.ProductImageApi(product.getSku());
			www.tonghao.mall.api.stb.resultwrap.ProductImageRes call = productImageApi.call();
		    if(call.isSuccess()){
		    	List<ProductImageAttr> result = call.getImagesList();
		    	if(!CollectionUtils.isEmpty(result)){
		    		ProductImageAttr productImageAttr = result.get(0);
	    			List<Image> images = productImageAttr.getImages();
	    			if(!CollectionUtils.isEmpty(images)){
			    		for (Image image : images) {
			    			ProductPics pics=new ProductPics();
			    			pics.setProductId(product.getId());
			    			pics.setLarge(image.getPath());
			    			pics.setMedium(image.getPath());
			    			pics.setSource(image.getPath());
			    			pics.setPriority(Float.valueOf(image.getOrder()));
			    			pics.setIsDelete(0);
			    			pics.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			    			pics.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
							productPicsMapper.insert(pics);
						}
	    			}else {
	    				log.info("未获取到商品图片，sku:" + product.getSku());
					}
		    	}
		    }else {
				log.info("商品图片接口调用失败，sku:" + product.getSku() + ",信息:" + call.getDesc());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("商品图片接口调用失败，sku:" + product.getSku());
		}
	}
	/**  
	 * <p>Title: setJDProductPrice</p>
	 * <p>Description: 更新史泰博商品价格</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	private void setSTBProductPrice(Suppliers supplier, String sku) {
		String emallCode = supplier.getCode();
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				Products selProducts = new Products();
				selProducts.setSku(sku);
				selProducts.setSupplierId(supplier.getId());
				selProducts.setIsDelete(0);
				Products product = productsMapper.selectOne(selProducts);
				if (product != null) {
					www.tonghao.mall.api.stb.call.ProductPriceApi productPricesApi = new www.tonghao.mall.api.stb.call.ProductPriceApi(sku);
					www.tonghao.mall.api.stb.resultwrap.ProductPriceRes call = productPricesApi.call();
					if(call.isSuccess()){
						List<ProductPriceAttr> attrs = call.getAttrs();
						if(!CollectionUtils.isEmpty(attrs)){
							ProductPriceAttr productPriceAttr = attrs.get(0);
							product.setPrice(productPriceAttr.getPrice());
							product.setMarketPrice(productPriceAttr.getMall_price());
							product.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
							updateNotNull(product);
						}else {
							log.warn("未获取到价格，sku = " + sku);
						}
					}else {
						log.warn("史泰博价格接口调用失败，sku = " + sku);
					}
				}else {
					log.warn("史泰博价格接口调用失败，sku = " + sku + "未查到对应商品");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("史泰博价格接口调用异常，sku = " + sku);
			}
		} else {
			log.warn("电商code或sku未获取到");
		}
	}
	/**  
	 * <p>Title: setSTBProductIsMarketable</p>
	 * <p>Description: 更新史泰博商品上下架状态</p>  
	 * @author YML 
	 * @param supplier
	 * @param sku 
	 */
	private void setSTBProductIsMarketable(Suppliers supplier, String sku) {
		String emallCode = supplier.getCode();
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				Products selProducts = new Products();
				selProducts.setSku(sku);
				selProducts.setSupplierId(supplier.getId());
				selProducts.setIsDelete(0);
				Products product = productsMapper.selectOne(selProducts);
				if(product != null){
					Boolean productState = getProductState(sku, supplier.getCode());
					if (productState) {
						product.setStatus(3);
						insertHistory(product,EmallConstant.EMALL_SUCCESS,true);
					}else {
						product.setStatus(4);
						insertHistory(product,EmallConstant.EMALL_ERROR,false);
					}
					updateNotNull(product);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("商品详情接口调用异常，sku = " + sku);
			}
		} else {
			log.warn("电商code或sku未获取到");
		}
	}
	
	/**====================================史泰博end============================================================*/
	
	
	/**====================================小电商start============================================================*/
	@Override
	public Map<String, Object> updateEmallProductsBySku(Suppliers supplier, String sku) {
		int result = 0;
		String msg = "";
		String emallCode = supplier.getCode();
		String isNew="false";
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				www.tonghao.mall.api.standard.call.ProductDetailApi productDetailApi = new www.tonghao.mall.api.standard.call.ProductDetailApi(emallCode, sku);
				www.tonghao.mall.api.standard.resultwrap.ProductDetailRes productDetail = productDetailApi.call();
				if(productDetail.isSuccess()){
					PlatformCatalogs platformCatalog = getPlatformCatalogs(productDetail.getResult().getCategory(), emallCode);
					if (platformCatalog == null) {
						return ResultUtil.resultMessage(result, "商品信息接口调用失败，sku:" + sku + ".信息：品目" + productDetail.getResult().getCategory() + "未映射");
					}
					
					Products selProducts = new Products();
					selProducts.setSku(sku);
					selProducts.setSupplierId(supplier.getId());
					selProducts.setIsDelete(0);
					Products product = productsMapper.selectOne(selProducts);
					if(product != null){
						
						Brand brand = mappingBrand(productDetail.getResult().getBrand_name());
						if(brand != null){
							product.setBrandId(brand.getId());
							product.setBrandName(brand.getName());
						}
						
						product.setCatalogId(platformCatalog.getId());
						product.setCatalogName(platformCatalog.getName());
						product.setGovCatalogId(platformCatalog.getCatalogId());
						
						product.setSupplierId(supplier.getId());
						product.setSupplierName(supplier.getName());
						product.setName(productDetail.getResult().getName());
						product.setSku(sku);
						product.setModel(productDetail.getResult().getModel());
						String weight = productDetail.getResult().getWeight();
						if(weight != null && !"".equals(weight) && !"null".equals(weight)){
							product.setWeight(Float.parseFloat(weight)+"");
						}
						product.setIsMarketable(new Byte("1"));
						product.setUnit(productDetail.getResult().getUnit());
						product.setParam(productDetail.getResult().getParam());
						product.setFactoryURL(productDetail.getResult().getUrl());
						product.setMallBrandName(productDetail.getResult().getBrand_name());
						product.setCoverUrl(productDetail.getResult().getImage_path());
						product.setDetail(productDetail.getResult().getIntroduction());
						product.setWare(productDetail.getResult().getWare());
						product.setProductArea(productDetail.getResult().getProductArea());
						product.setUpc(productDetail.getResult().getUpc());
						product.setAfterSaleService(productDetail.getResult().getService());
						product.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
						product.setIsDelete(0);
						result = updateNotNull(product);
					}else{
						isNew="true";
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("supplierId", supplier.getId());
						map.put("endTime", DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
						map.put("types", 1);
						List<Protocol> protocols = protocolMapper.getProtocolBySupplier(map);
						Protocol protocol = null;
						if(!CollectionUtils.isEmpty(protocols)){
							protocol = protocols.get(0);
						}
						
						product = new Products();
						
						Brand brand = mappingBrand(productDetail.getResult().getBrand_name());
						if(brand != null){
							product.setBrandId(brand.getId());
							product.setBrandName(brand.getName());
						}
						
						product.setSupplierId(supplier.getId());
						product.setSupplierName(supplier.getName());
						product.setName(productDetail.getResult().getName());
						product.setSku(sku);
						product.setModel(productDetail.getResult().getModel());
						String weight = productDetail.getResult().getWeight();
						if(weight!=null&&!"".equals(weight)&&!"null".equals(weight)){
							product.setWeight(Float.parseFloat(weight)+"");
						}
						product.setIsMarketable(new Byte("1"));
						product.setUnit(productDetail.getResult().getUnit());
						product.setParam(productDetail.getResult().getParam());
						product.setFactoryURL(productDetail.getResult().getUrl());
						product.setMallBrandName(productDetail.getResult().getBrand_name());
						
						product.setCatalogId(platformCatalog.getId());
						product.setCatalogName(platformCatalog.getName());
						product.setGovCatalogId(platformCatalog.getCatalogId());
						if(protocol != null){
							product.setProtocolId(protocol.getId());
							product.setProtocolName(protocol.getName());
						}
						product.setCoverUrl(productDetail.getResult().getImage_path());
						product.setDetail(productDetail.getResult().getIntroduction());
						product.setWare(productDetail.getResult().getWare());
						product.setProductArea(productDetail.getResult().getProductArea());
						product.setUpc(productDetail.getResult().getUpc());
						product.setAfterSaleService(productDetail.getResult().getService());
						product.setCreatedAt(DateUtilEx.timeFormat(new Date()));
						product.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
						product.setIsDelete(0);
						product.setIsChangePrice(0);
						result = saveSelective(product);
					}
					
					if (result > 0) {
						//保存图片
						Example example=new Example(ProductPics.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("productId", product.getId());
						List<ProductPics> pic_list = productPicsMapper.selectByExample(example);
						if(!CollectionUtils.isEmpty(pic_list)){
							www.tonghao.mall.api.standard.call.ProductImageApi productImageApi = new www.tonghao.mall.api.standard.call.ProductImageApi(emallCode, sku);
							www.tonghao.mall.api.standard.resultwrap.ProductImageRes productImages = productImageApi.call();
							if(productImages.isSuccess()){
								for (ProductPics productPics : pic_list) {
									productPicsMapper.deleteByPrimaryKey(productPics.getId());
								}
								saveProductPic(product, productImages);
							}else {
								log.warn("商品图片接口调用失败，sku = " + sku);
							}
						}else{
							www.tonghao.mall.api.standard.call.ProductImageApi productImageApi = new www.tonghao.mall.api.standard.call.ProductImageApi(emallCode, sku);
							www.tonghao.mall.api.standard.resultwrap.ProductImageRes productImages = productImageApi.call();
							if(productImages.isSuccess()){
								saveProductPic(product, productImages);
							}else {
								log.warn("商品图片接口调用失败，sku = " + sku);
							}
						}
					}
				}else{
					msg = "商品详情接口调用失败，sku = " + sku + ".信息：" + productDetail.getDesc();
					log.warn("商品详情接口调用失败，sku = " + sku + ".信息：" + productDetail.getDesc());
					return ResultUtil.resultMessage(result, msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.warn("商品详情接口调用异常，sku = " + sku);
				return ResultUtil.resultMessage(result, "商品详情接口调用异常，sku = " + sku);
			}
		}
		return ResultUtil.resultMessage(result, "");
	}
	
	@Override
	public void setProductIsMarketable(Suppliers supplier, String sku) {
		int result = 0;
		String emallCode = supplier.getCode();
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				www.tonghao.mall.api.standard.call.ProductStateApi productStateApi = new www.tonghao.mall.api.standard.call.ProductStateApi(emallCode, sku);
				www.tonghao.mall.api.standard.resultwrap.ProductStateRes productStateRes = productStateApi.call();
				if(productStateRes.isSuccess() ){
					List<www.tonghao.mall.api.standard.attwrap.StateResultAttr> states = productStateRes.getResult();
					if(CollectionUtils.isNotEmpty(states)){
						for (www.tonghao.mall.api.standard.attwrap.StateResultAttr stateResultAttr2 : states) {
							Products selProducts = new Products();
							selProducts.setSku(stateResultAttr2.getSku());
							selProducts.setSupplierId(supplier.getId());
							selProducts.setIsDelete(0);
							Products products = productsMapper.selectOne(selProducts);
							if(products != null ){
								Products product = new Products();
								product.setId(products.getId());
								if(stateResultAttr2.getState() == 1 ){
									//电商上架  不做任何处理  
								}else{
									product.setStatus(4);
									insertHistory(products, EmallConstant.EMALL_ERROR, false);
								}
								product.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
								updateNotNull(product);
							}
						}
					}
				}else {
					log.warn("商品上下架接口调用失败，错误原因："+productStateRes.getDesc()+"，参数：" + sku);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setProductPrice(Suppliers supplier, String sku) {
		int result = 0;
		String emallCode = supplier.getCode();
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				Products selProducts = new Products();
				selProducts.setSku(sku);
				selProducts.setSupplierId(supplier.getId());
				selProducts.setIsDelete(0);
				Products product = productsMapper.selectOne(selProducts);
				if(product!=null){
					www.tonghao.mall.api.standard.call.ProductPricesApi productPricesApi = new www.tonghao.mall.api.standard.call.ProductPricesApi(emallCode, sku);
					www.tonghao.mall.api.standard.resultwrap.ProductPriceRes productPrices = productPricesApi.call();
					if(productPrices.isSuccess()){
						List<www.tonghao.mall.api.standard.attwrap.PriceAttr> messageAttr = productPrices.getResult();
						if(!CollectionUtil.collectionIsEmpty(messageAttr)){
							www.tonghao.mall.api.standard.attwrap.PriceAttr priceAttr = messageAttr.get(0);
							if(product.getProtocolPrice() != null && product.getProtocolPrice().compareTo(priceAttr.getPrice()) == -1){
				    			insertHistory(product,"价格更新高于原价格，该产品下架",false);
				    			product.setStatus(4);
								
				    		}else if(priceAttr.getMall_price().compareTo(product.getMarketPrice()) == 0 && priceAttr.getPrice().compareTo(product.getProtocolPrice()) == 0){
				    			product.setProtocolPrice(priceAttr.getPrice());
				    			product.setMarketPrice(priceAttr.getMall_price());
							}else{
								//如果价格变动直接下架  并记录
								insertHistory(product, www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+product.getMarketPrice()+",原协议价："+product.getProtocolPrice()+",现市场价："+priceAttr.getMall_price()+",现协议价："+priceAttr.getPrice(), false);
								product.setMarketPrice(priceAttr.getMall_price());
								product.setProtocolPrice(priceAttr.getPrice());
								product.setStatus(4);
								product.setIsChangePrice(0);
							}
						}else{
							product.setStatus(4);
							insertHistory(product, productPrices.getDesc(), false);
						}
					}else{
						product.setStatus(4);
						insertHistory(product, productPrices.getDesc(), false);
					}
					updateNotNull(product);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**====================================小电商end============================================================*/
	
	@Override
	public void validate(Suppliers supplier, String sku) {
		String emallCode = supplier.getCode();
		if (StringUtils.isNotBlank(emallCode) && StringUtils.isNotBlank(sku)) {
			try {
				Products selProducts = new Products();
				selProducts.setSku(sku);
				selProducts.setSupplierId(supplier.getId());
				selProducts.setIsDelete(0);
				Products products = productsMapper.selectOne(selProducts);
				try {
					if(products != null && products.getStatus() == 3){
						if(isNull(products)){//判断品牌型号，
							insertHistory(products, EmallConstant.MODEL_ERROR, false);
							products.setStatus(4);
							updateNotNull(products);
						}
					}
				} catch (Exception e) {
					insertHistory(products,"产品型号品牌校验发生异常，该产品下架",false);
					products.setStatus(4);
					updateNotNull(products);
				}
				
				try {
					if(products != null && products.getStatus() == 3){
						if(products.getProtocolPrice() != null && products.getMarketPrice()!= null && products.getProtocolPrice().compareTo(products.getMarketPrice())>0){//判断价格
							insertHistory(products, EmallConstant.PRICE_ERROR, false);
							products.setStatus(4);
							updateNotNull(products);
						}
						if(products.getProtocolPrice() == null|| products.getMarketPrice() == null || products.getProtocolPrice().compareTo(new BigDecimal("0")) == 0){
							insertHistory(products,EmallConstant.PRODUCT_PRICE,false);
							products.setStatus(4);
							updateNotNull(products);
						}
						if(vaStrIsBlack(products.getDetail())) {
							insertHistory(products, "无商品详情，自动下架", false);
							products.setStatus(4);
							updateNotNull(products);
						}
						if(vaStrIsBlack(products.getCoverUrl())) {
							insertHistory(products, "无商品主图，自动下架", false);
							products.setStatus(4);
							updateNotNull(products);
						}
					}
				} catch (Exception e) {
					insertHistory(products,"价格校验发生异常，该产品下架",false);
					products.setStatus(4);
					updateNotNull(products);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isNull(Products products){
		if(StringUtils.isBlank(products.getBrandName())||StringUtils.isBlank(products.getModel())||"null".equals(products.getBrandName())||"null".equals(products.getModel())){
			return true;
		}
		return false;
	}
	
	private void insertHistory(Products products, String message, boolean success) {
		UpperLowerHistory record=new UpperLowerHistory();
		record.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		record.setProductId(products.getId());
		record.setReason(message);
		record.setStatus(1);
		if(success){
			record.setType(3);
		}else{
			record.setType(4);
		}
		record.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		upperLowerHistoryMapper.insertSelective(record);
	}
	
	public Brand mappingBrand(String brandName){
		Brand return_brand = new Brand();
		List<Brand> list = brandMapper.findByMappingName(brandName);
		if(!CollectionUtils.isEmpty(list)){
		   oth:
			for (Brand brand : list) {
				String mappingName = brand.getMappingName();
				String[] split = mappingName.split("\\|");
				for (String string : split) {
					if(string.equals(brandName)){
						return_brand = brand;
						break oth;
					}
				}
			}
			return return_brand;
		}else{
			return null;
		}
	}
	
	public int updateNotNull(Products product) {
		int result = super.updateNotNull(product);
        //更新es
		productSearchService.addData(productsMapper.getById(product.getId()));
        return result;
    }

	@Override
	public void updateHits(Products product) {
		productsMapper.updateHits(product);
	}
	
	public boolean vaStrIsBlack(String str){
		return str == null || "null".equals(str) || "".equals(str);
	}

	@Override
	public List<Products> selectChangePrice(Map<String, Object> map) {
		return productsMapper.selectChangePrice(map);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updatePrice(Products products) {
		ProductChangePrice changePrice=new ProductChangePrice();
		Products pro = productsMapper.selectByPrimaryKey(products.getId());
		int updateNotNull=0;
		System.out.println(pro.getProtocolPrice()+"-=-="+products.getProtocolPrice());
		System.out.println(pro.getProtocolPrice().compareTo(products.getProtocolPrice()));
		if(pro.getProtocolPrice().compareTo(products.getProtocolPrice())==0) {
			if(products.getPrice().compareTo(pro.getProtocolPrice())>=0){
				saveChangePriceLog(changePrice, pro,pro.getMarketPrice(),pro.getProtocolPrice(),products.getPrice());
				pro.setPrice(products.getPrice());
				pro.setIsChangePrice(1);
				pro.setStatus(4);
				updateNotNull = updateNotNull(pro);
				if(updateNotNull>0) {
					productChangePriceMapper.insertSelective(changePrice);
				}
			}
		}
		return updateNotNull;
	}
		
	private void saveChangePriceLog(ProductChangePrice changePrice, Products pro,BigDecimal newMarketPrice,BigDecimal newProtocolPrice,BigDecimal newPrice) {
		changePrice.setProductId(pro.getId());
		changePrice.setCreateAt(DateUtilEx.timeToDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		changePrice.setMarketPrice(pro.getMarketPrice());
		changePrice.setProtocolPrice(pro.getProtocolPrice());
		changePrice.setPrice(pro.getPrice());
		changePrice.setNewMarketPrice(newMarketPrice);
		changePrice.setNewProtocolPrice(newProtocolPrice);
		changePrice.setNewPrice(newPrice);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> updateBatchPrice(ProductsList productList) {
		Map<String, Object> map=new HashMap<String, Object>();
		int result_num=0;
		String sku="";
		String name="";
		String result="";
		boolean flag=false;
		List<Products> product_List = productList.getProductList();
		if(!CollectionUtil.collectionIsEmpty(product_List)) {
			for (Products products : product_List) {
				Products pro = productsMapper.selectByPrimaryKey(products.getId());
				/*BigDecimal divide = pro.getProtocolPrice().divide(pro.getMarketPrice(),2,BigDecimal.ROUND_HALF_UP);*/
				if(pro.getProtocolPrice().compareTo(products.getProtocolPrice())!=0) {
					sku=pro.getSku();
					name=pro.getName();
					flag=true;
					result+="商品:"+sku+",";
					continue;
				}
				BigDecimal multiply = new BigDecimal(pro.getMarketPrice()+"").multiply(new BigDecimal(products.getNewDiscount()+"")).setScale(2, BigDecimal.ROUND_HALF_UP);;
				if(multiply.compareTo(pro.getProtocolPrice())>=0){
					ProductChangePrice changePrice=new ProductChangePrice();
					saveChangePriceLog(changePrice, pro,pro.getMarketPrice(),pro.getProtocolPrice(),multiply);
					pro.setPrice(multiply);
					pro.setStatus(4);
					pro.setIsChangePrice(1);
					int updateNotNull = updateNotNull(pro);
					if(updateNotNull>0) {
						result_num+=updateNotNull;
						productChangePriceMapper.insertSelective(changePrice);
					}
				}
			}
		}
		/*if(flag) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}*/
		if(!StringUtils.isEmpty(result)) {
			result+="调价失败，协议价在此期间有变动，请刷新页面重试！";
		}
		map.put("result", result_num);
		map.put("message", result);
		return map;
	}

	@Override
	public int updateBatchPriceResult(Map<String, Object> map) {
		Map<String, Object> maps=new HashMap<String, Object>();
		int result_num=0;
		/*String sku="";
		String name="";
		String result="";
		boolean flag=false;*/
		BigDecimal newDiscount = new BigDecimal(map.get("newDiscount")+"");
		BigDecimal beginOldDiscount = new BigDecimal(map.get("beginOldDiscount")+"");
		BigDecimal endOldDiscount = new BigDecimal(map.get("endOldDiscount")+"");
		List<Products> product_list = productsMapper.selectChangePrice(map);
		if(!CollectionUtil.collectionIsEmpty(product_list)) {
			for (Products products : product_list) {
				Products pro = productsMapper.selectByPrimaryKey(products.getId());
				BigDecimal divide = pro.getProtocolPrice().divide(pro.getMarketPrice(),2,BigDecimal.ROUND_HALF_UP);
				
				if(beginOldDiscount.compareTo(divide)<=0&&endOldDiscount.compareTo(divide)>=0) {
					BigDecimal multiply = new BigDecimal(pro.getMarketPrice()+"").multiply(new BigDecimal(newDiscount+"")).setScale(2, BigDecimal.ROUND_HALF_UP);;
					if(multiply.compareTo(pro.getProtocolPrice())>=0){
						ProductChangePrice changePrice=new ProductChangePrice();
						saveChangePriceLog(changePrice, pro,pro.getMarketPrice(),pro.getProtocolPrice(),multiply);
						pro.setPrice(multiply);
						if(pro.getStatus()==3) {
							pro.setStatus(4);
						}
						pro.setIsChangePrice(1);
						int updateNotNull = updateNotNull(pro);
						if(updateNotNull>0) {
							result_num+=updateNotNull;
							productChangePriceMapper.insertSelective(changePrice);
						}
					}
				}
			}
		}
		return result_num;
	}

	@Override
	public List<Products> selectproductPutaway(Map<String, Object> map) {
		return productsMapper.selectproductPutaway(map);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateToUp(Long id) {
		int updateNotNull=0;
		Products pro = productsMapper.selectByPrimaryKey(id);
		if(pro!=null){
			pro.setIsShow(3);
			Suppliers supplier = suppliersMapper.selectByPrimaryKey(pro.getSupplierId());
			if(supplier.getCode().equals(EmallConstant.MALL_STB_CODE)) {//史泰博
				updateNotNull = stb_product(updateNotNull, pro, supplier);
			}else if(supplier.getCode().equals(EmallConstant.MALL_JD_CODE)) {//京东
				updateNotNull = jd_product(updateNotNull, pro, supplier);
			}else if(supplier.getCode().equals(EmallConstant.MALL_DL_CODE)){//小电商
				updateNotNull = standard_product(updateNotNull, pro, supplier);
			}else {//本地供应商
				if(pro!=null&&!StringUtils.isEmpty(pro.getSku())&&pro.getIsChangePrice()==1&&pro.getStatus()==4) {//判断商品是否已经改价了
					pro.setStatus(3);
					updateNotNull=updateNotNull(pro);
					saveUpperLowerHistory(pro.getId(), 3, supplier.getId(), "手动上架");
				}
			}
		}
		return updateNotNull;
	}

	private int standard_product(int updateNotNull, Products pro, Suppliers supplier){
		BigDecimal marketPrice = pro.getMarketPrice();//原市场价
		BigDecimal protocolPrice = pro.getProtocolPrice();//原协议价
		if(pro!=null&&!StringUtils.isEmpty(pro.getSku())&&pro.getIsChangePrice()==1&&pro.getStatus()==4) {//判断商品是否已经改价了
			www.tonghao.mall.api.standard.call.ProductPricesApi priceApi=new www.tonghao.mall.api.standard.call.ProductPricesApi(supplier.getCode(), pro.getSku());
			www.tonghao.mall.api.standard.resultwrap.ProductPriceRes price_call = priceApi.call();
			boolean flg=false;
			if(price_call.isSuccess()){
				List<www.tonghao.mall.api.standard.attwrap.PriceAttr> price_result = price_call.getResult();
				if(!CollectionUtil.collectionIsEmpty(price_result)){
					www.tonghao.mall.api.standard.attwrap.PriceAttr priceAttr = price_result.get(0);
					pro.setMarketPrice(priceAttr.getMall_price());
					pro.setProtocolPrice(priceAttr.getPrice());
					if(pro.getProtocolPrice().compareTo(protocolPrice)!=0||pro.getMarketPrice().compareTo(marketPrice)!=0) {//判断协议价和市场价 在上一次的基础上有没有被修改
						pro.setStatus(4);
						//添加下架原因
						saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+pro.getMarketPrice()+",协议价："+pro.getProtocolPrice());
						//修改是否改价标室
						pro.setIsChangePrice(0);
						flg=true;
						updateNotNull(pro);
					}
				}
			}else{
				flg=true;
				pro.setStatus(4);
				saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), price_call.getDesc());
			}
			if(!flg) {
				www.tonghao.mall.api.standard.call.ProductStateApi state_api=new www.tonghao.mall.api.standard.call.ProductStateApi(supplier.getCode(), pro.getSku());
				www.tonghao.mall.api.standard.resultwrap.ProductStateRes state_call = state_api.call();
				if(state_call.isSuccess()){
					List<www.tonghao.mall.api.standard.attwrap.StateResultAttr> state_result = state_call.getResult();
					if(!CollectionUtil.collectionIsEmpty(state_result)){
						www.tonghao.mall.api.standard.attwrap.StateResultAttr stateResultAttr = state_result.get(0);
						int state = stateResultAttr.getState();
						if(state==0){//下架
							pro.setStatus(4);
							saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.EMALL_ERROR);
						}else if(state==1){
							pro.setStatus(3);
							saveUpperLowerHistory(pro.getId(), 3, supplier.getId(), "手动上架");
						}
					}
				}else{
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), state_call.getDesc());
				}
				if(pro.getStatus()==3) {
					check(pro, supplier);
				}
				if(pro.getStatus()==3) {
					updateNotNull = updateNotNull(pro);
				}else {
					updateNotNull(pro);
				}
			}
		}
		return updateNotNull;
	}
	
	private int jd_product(int updateNotNull, Products pro, Suppliers supplier){
		BigDecimal marketPrice = pro.getMarketPrice();//原市场价
		BigDecimal protocolPrice = pro.getProtocolPrice();//原协议价
		if(pro!=null&&!StringUtils.isEmpty(pro.getSku())&&pro.getIsChangePrice()==1&&pro.getStatus()==4) {//判断商品是否已经改价了
			//===========================start   价格接口===================
		    boolean flg=false;
		    ProductPricesApi productPricesApi=new ProductPricesApi(pro.getSku());
		    ProductPriceRes call = productPricesApi.call();
		    if(call.isSuccess()){
		    	List<PriceAttr> result = call.getResult();
		    	if(!CollectionUtil.collectionIsEmpty(result)){
		    		PriceAttr priceAttr = result.get(0);
		    		pro.setMarketPrice(priceAttr.getJdPrice());
					pro.setProtocolPrice(priceAttr.getPrice());
					if(pro.getProtocolPrice().compareTo(protocolPrice)!=0||pro.getMarketPrice().compareTo(marketPrice)!=0) {//判断协议价和市场价 在上一次的基础上有没有被修改
						pro.setStatus(4);
						//添加下架原因
						saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+pro.getMarketPrice()+",协议价："+pro.getProtocolPrice());
						//添加改价记录
						/*ProductChangePrice changePrice=new ProductChangePrice();
						saveChangePriceLog(changePrice, old_pro,pro.getMarketPrice(),pro.getProtocolPrice(),pro.getPrice());
						productChangePriceMapper.insertSelective(changePrice);*/
						//修改是否改价标室
						pro.setIsChangePrice(0);
						flg=true;
						updateNotNull(pro);
					}
		    	}
		    }else{
		    	flg=true;
				pro.setStatus(4);
				saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), call.getResultMessage());
		    }
		    if(!flg) {
		    	ProductStateApi productStateApi=new ProductStateApi(pro.getSku());
		    	ProductStateRes ps_call = productStateApi.call();
		    	if(ps_call.isSuccess()){
		    		List<StateResultAttr> result = ps_call.getResult();
		    		if(!CollectionUtil.collectionIsEmpty(result)){
		    			StateResultAttr stateResultAttr = result.get(0);
		    			if(stateResultAttr.getState()==1){//上架
		    				pro.setStatus(3);
							saveUpperLowerHistory(pro.getId(), 3, supplier.getId(), "手动上架");
		    			}else if(stateResultAttr.getState()==0){//下架
		    				pro.setStatus(4);
							saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.EMALL_ERROR);
		    			}
		    		}
		    	}else{
		    		pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), ps_call.getResultMessage());
		    	}
		    	if(pro.getStatus()==3) {
					check(pro, supplier);
				}
				if(pro.getStatus()==3) {
					updateNotNull = updateNotNull(pro);
				}else {
					updateNotNull(pro);
				}
		    }
		}
		 return updateNotNull;
	}
	
	private int stb_product(int updateNotNull, Products pro, Suppliers supplier) {
		BigDecimal marketPrice = pro.getMarketPrice();//原市场价
		BigDecimal protocolPrice = pro.getProtocolPrice();//原协议价
		if(pro!=null&&!StringUtils.isEmpty(pro.getSku())&&pro.getIsChangePrice()==1&&pro.getStatus()==4) {//判断商品是否已经改价了
				//===========================start   价格接口===================
			    boolean flg=false;
			    www.tonghao.mall.api.stb.call.ProductPriceApi stb_price=new www.tonghao.mall.api.stb.call.ProductPriceApi(pro.getSku());
				www.tonghao.mall.api.stb.resultwrap.ProductPriceRes call = stb_price.call();
				if(call.isSuccess()) {
					List<ProductPriceAttr> attrs = call.getAttrs();
					if(!CollectionUtil.collectionIsEmpty(attrs)) {
						ProductPriceAttr productPriceAttr = attrs.get(0);
						pro.setMarketPrice(productPriceAttr.getMall_price());
						pro.setProtocolPrice(productPriceAttr.getPrice());
						if(pro.getProtocolPrice().compareTo(protocolPrice)!=0||pro.getMarketPrice().compareTo(marketPrice)!=0) {//判断协议价和市场价 在上一次的基础上有没有被修改
							pro.setStatus(4);
							//添加下架原因
							saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+marketPrice+",原协议价："+protocolPrice+",现市场价："+pro.getMarketPrice()+",协议价："+pro.getProtocolPrice());
							//添加改价记录
							/*ProductChangePrice changePrice=new ProductChangePrice();
							saveChangePriceLog(changePrice, old_pro,pro.getMarketPrice(),pro.getProtocolPrice(),pro.getPrice());
							productChangePriceMapper.insertSelective(changePrice);*/
							//修改是否改价标室
							pro.setIsChangePrice(0);
							flg=true;
							updateNotNull(pro);
						}
						
					}
				}else{
					flg=true;
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), call.getDesc());
				}
				
				if(!flg) {
					www.tonghao.mall.api.stb.call.ProductStateApi stb_state=new www.tonghao.mall.api.stb.call.ProductStateApi(pro.getSku());
					www.tonghao.mall.api.stb.resultwrap.ProductStateRes call2 = stb_state.call();
					if(call2.isSuccess()) {
						List<ProductStateAttr> list = call2.getList();
						if(!CollectionUtil.collectionIsEmpty(list)) {
							ProductStateAttr productStateAttr = list.get(0);
							if(productStateAttr.getState()==0) {//下架
								pro.setStatus(4);
								saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.EMALL_ERROR);
							}else if(productStateAttr.getState()==1) {//上架
								pro.setStatus(3);
								saveUpperLowerHistory(pro.getId(), 3, supplier.getId(), "手动上架");
							}
						}
					}else{
						pro.setStatus(4);
						saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), call2.getDesc());
					}
					if(pro.getStatus()==3) {
						check(pro, supplier);
					}
					if(pro.getStatus()==3) {
						updateNotNull = updateNotNull(pro);
					}else {
						updateNotNull(pro);
					}
				}
		}
		return updateNotNull;
	}

	private void check(Products pro, Suppliers supplier) {
		if(isNull(pro.getBrandName())) {//判断商品型号或者品牌
			pro.setStatus(4);
			saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.MODEL_ERROR);
		}
		if(pro.getProtocolPrice()==null||pro.getMarketPrice()==null||pro.getProtocolPrice().compareTo(new BigDecimal("0"))==0){//判断价格
			pro.setStatus(4);
			saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PRODUCT_PRICE);
		}
		if(pro.getProtocolPrice()!=null&&pro.getMarketPrice()!=null&&pro.getProtocolPrice().compareTo(pro.getMarketPrice())>0){//判断价格
			pro.setStatus(4);
			saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PRICE_ERROR);
		}
		if(isNull(pro.getDetail())) {
			pro.setStatus(4);
			saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PRODUCT_DETAIL);
		}
		if(isNull(pro.getCoverUrl())) {
			pro.setStatus(4);
			saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), www.tonghao.mall.api.Constant.PRODUCT_IMAGE);
		}
		if(isNull(pro.getCatalogName())) {
			pro.setStatus(4);
			saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), "品目未映射，自动下架");
		}
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
	private boolean isNull(String str){
		if(str==null||"null".equals(str)||StringUtils.isEmpty(str)){
			return true;
		}
		return false;
	}

	@Override
	public int updateToUpBatch(String ids) {
		String[] split = ids.split(",");
		int updateNotNull=0;
		for (String id_str : split) {
			Products pro = productsMapper.selectByPrimaryKey(Long.parseLong(id_str));
			if(pro!=null&&pro.getStatus()==4){
				pro.setIsShow(3);
				Suppliers supplier = suppliersMapper.selectByPrimaryKey(pro.getSupplierId());
				if(supplier.getCode().equals(EmallConstant.MALL_STB_CODE)) {//史泰博
					updateNotNull += stb_product(updateNotNull, pro, supplier);
				}else if(supplier.getCode().equals(EmallConstant.MALL_JD_CODE)) {//京东
					updateNotNull += jd_product(updateNotNull, pro, supplier);
				}else if(supplier.getCode().equals(EmallConstant.MALL_DL_CODE)){
					updateNotNull += standard_product(updateNotNull, pro, supplier);
				} else{//本地供应商
					if(pro!=null&&!StringUtils.isEmpty(pro.getSku())&&pro.getIsChangePrice()==1&&pro.getStatus()==4) {//判断商品是否已经改价了
						pro.setStatus(3);
						updateNotNull+=updateNotNull(pro);
						saveUpperLowerHistory(pro.getId(), 3, supplier.getId(), "手动上架");
					}
				}
			}
		}
		return updateNotNull;
	}
	
	private PlatformCatalogs getPlatformCatalogs(String category, String emallCode) {
		if (StringUtils.isNotBlank(category)) {
			EmallCatalogsMapping emallCatalog = new EmallCatalogsMapping();
			emallCatalog.setEmallCatalogsId(category);
			emallCatalog.setEmallCode(emallCode);
			EmallCatalogsMapping emallCatalogsMapping = emallCatalogsMappingMapper.selectOne(emallCatalog);
			if (emallCatalogsMapping != null && emallCatalogsMapping.getCatalogsId() != null) {
				PlatformCatalogs platformCatalog = platformCatalogsMapper.selectByPrimaryKey(emallCatalogsMapping.getCatalogsId());
				return platformCatalog;
			}
		}
		return null;
	}
	
	private void saveProductPic(Products product, www.tonghao.mall.api.standard.resultwrap.ProductImageRes productImages) {
		List<www.tonghao.mall.api.standard.attwrap.ImageResultAttr> result = productImages.getResult();
		if(!CollectionUtils.isEmpty(result)){
			for (www.tonghao.mall.api.standard.attwrap.ImageResultAttr imageResultAttr : result) {
				List<www.tonghao.mall.api.standard.attwrap.Image> images = imageResultAttr.getImages();
				if( !CollectionUtils.isEmpty(images)){
					for (www.tonghao.mall.api.standard.attwrap.Image image : images) {
						ProductPics productPics = new ProductPics();
						productPics.setProductId(product.getId());
						productPics.setLarge(image.getPath());
						productPics.setMedium(image.getPath());
						productPics.setSource(image.getPath());
						productPics.setPriority(Float.valueOf(image.getOrder()));
						productPics.setIsDelete(0);
						productPics.setCreatedAt(DateUtilEx.timeFormat(new Date()));
						productPics.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
						productPicsMapper.insert(productPics);
					}
				}else {
					log.info("未获取到商品图片");
				}
			}
		}
	}

	@Override
	public List<Products> productJdSku() {
		return productsMapper.productJdSku();
	}

	@Override
	public Products selectBySku(String sku, Long supplierId) {
		Products products = new Products();
		products.setSku(sku);
		products.setSupplierId(supplierId);
		List<Products> select = productsMapper.select(products);
		if(CollectionUtils.isNotEmpty(select)){
			return select.get(0);
		}
		return null;
	}
}
