package www.tonghao.mall.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.constant.EmallConstant;
import www.tonghao.common.entity.ProductContacts;
import www.tonghao.common.utils.AssistUtil;
import www.tonghao.common.utils.BigDecimalUtil;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PriceUtil;
import www.tonghao.common.utils.ProductUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.ValidatorUtil;
import www.tonghao.common.warpper.DeliveryVo;
import www.tonghao.common.warpper.PayWayVo;
import www.tonghao.common.warpper.PriceRangeModel;
import www.tonghao.common.warpper.ProductAttributeModel;
import www.tonghao.common.warpper.ProductStock;
import www.tonghao.mall.api.jd.attwrap.NewStockAttr;
import www.tonghao.mall.api.jd.attwrap.PriceAttr;
import www.tonghao.mall.api.jd.call.CheckAreaApi;
import www.tonghao.mall.api.jd.call.ProductCheckApi;
import www.tonghao.mall.api.jd.call.ProductNewStockApi;
import www.tonghao.mall.api.jd.call.ProductPricesApi;
import www.tonghao.mall.api.jd.entity.Sku;
import www.tonghao.mall.api.jd.resultwrap.CheckAreaRes;
import www.tonghao.mall.api.jd.resultwrap.NewStockRes;
import www.tonghao.mall.api.jd.resultwrap.ProductCheckRes;
import www.tonghao.mall.api.jd.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.standard.attwrap.ProductStockAttr;
import www.tonghao.mall.api.standard.attwrap.StateResultAttr;
import www.tonghao.mall.api.standard.call.ProductStocksApi;
import www.tonghao.mall.api.standard.resultwrap.ProductStocksRes;
import www.tonghao.mall.api.stb.attwrap.ProductPriceAttr;
import www.tonghao.mall.api.stb.attwrap.ProductStateAttr;
import www.tonghao.mall.api.stb.attwrap.ProductStocksAttr;
import www.tonghao.mall.api.stb.call.ProductStateApi;
import www.tonghao.mall.api.stb.resultwrap.ProductStateRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.ApiCommon;
import www.tonghao.mall.entity.DeliveryWay;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.mall.entity.PayWay;
import www.tonghao.mall.entity.ProductCompareCell;
import www.tonghao.mall.entity.ProductCompareRow;
import www.tonghao.mall.mapper.DeliveryWayMapper;
import www.tonghao.mall.mapper.MallProductsMapper;
import www.tonghao.mall.service.MallProductService;
import www.tonghao.mall.service.PayWayService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.Catalogs;
import www.tonghao.service.common.entity.MappingArea;
import www.tonghao.service.common.entity.Parameter;
import www.tonghao.service.common.entity.ParameterItem;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductParameter;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.mapper.CatalogsMapper;
import www.tonghao.service.common.mapper.ParameterItemMapper;
import www.tonghao.service.common.mapper.ParameterMapper;
import www.tonghao.service.common.mapper.ProductParameterMapper;
import www.tonghao.service.common.mapper.UpperLowerHistoryMapper;
import www.tonghao.service.common.service.MappingAreaService;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.SuppliersService;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Sets;

@Service("mallProductService")
public class MalProductServiceImpl extends BaseServiceImpl<MallProducts> implements MallProductService {

	@Autowired
	private MallProductsMapper productsMapper;
	
	@Autowired
	private ProductParameterMapper productParameterMapper;
	
	@Autowired
	private ParameterMapper parameterMapper;
	
	@Autowired
	private ParameterItemMapper parameterItemMapper;
	
	@Autowired
	private CatalogsMapper catalogsMapper;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	
	@Autowired
	private ProductsService productService;
	
	@Autowired
	private PayWayService payWayService;
	
	@Autowired
	private DeliveryWayMapper deliveryWayMapper;
	
	@Autowired
	private UpperLowerHistoryMapper upperLowerHistoryMapper;
	
	@Autowired
	private SuppliersService suppliersService;
	
	@Autowired
	private MappingAreaService mappingAreaService;
	
	@Override
	public MallProducts selectByKey(Long id) {
		return productsMapper.getById(id);
	}
	
	@Override
	public MallProducts findById(Long id) {
		MallProducts product = productsMapper.getById(id);
		if(product!=null){
			
			if(product.getSupplier()==null&&product.getSupplierId()!=null){
				product.setSupplier(suppliersService.selectByKey(product.getSupplierId()));
			}
			
			List<ProductParameter> productParameters = productParameterMapper.getByProductId(id);
			productParameters.forEach(pp -> {
				pp.setParameter(getParameters(pp.getStandParamId()));
				pp.setParameterItems(getParameterItems(pp));
			});
			product.setProductParameters(productParameters.stream().filter(pp -> pp.getParameter()!=null).collect(Collectors.toList()));
			
			product.setAttributeModel(getProductAttributeModel(product));
		}
		return product;
	}
	
	@Override
	public MallProducts findCarById(Long id) {
		MallProducts product = productsMapper.getCarById(id);
		if(product!=null){
			
			if(product.getSupplier()==null&&product.getSupplierId()!=null){
				product.setSupplier(suppliersService.selectByKey(product.getSupplierId()));
			}
			
			List<ProductParameter> productParameters = productParameterMapper.getByProductId(id);
			productParameters.forEach(pp -> {
				pp.setParameter(getParameters(pp.getStandParamId()));
				pp.setParameterItems(getParameterItems(pp));
			});
			product.setProductParameters(productParameters.stream().filter(pp -> pp.getParameter()!=null).collect(Collectors.toList()));
			
			product.setAttributeModel(getProductAttributeModel(product));
		}
		return product;
	}
	
	@Override
	public List<MallProducts> findListByEntity(Products entity) {
		return productsMapper.findSupplierProtocolListByEntity(entity);
	}
	
	@Override
	public List<MallProducts> findBenefitProductsByEntity(Products entity) {
		return productsMapper.findSupplierProtocolBenefitListByEntity(entity);
	}
	@Override
	public List<Brand> getQueryResultBrands(Products entity) {
		return productsMapper.getSupplierProtocolBrands(entity);
	}
	@Override
	public List<Suppliers> getQueryResultSuppliers(Products entity) {
		return productsMapper.getSupplierProtocolSuppliers(entity);
	}
	@Override
	public List<PriceRangeModel> getPriceRanges(Products entity) {
		if(entity!=null&&entity.getCatalogId()!=null){
			PlatformCatalogs catalog = platformCatalogsService.selectByKey(entity.getCatalogId());
			if(catalog!=null&&StringUtils.isNotBlank(catalog.getPriceRange())){
				String priceRange = catalog.getPriceRange();
				List<Map<String,Object>> list = JsonUtil.toObject(priceRange, new TypeReference<List<Map<String,Object>>>() {});
				List<PriceRangeModel> list2 = Lists.newArrayList();
				PriceRangeModel e = null;
				for(Map<String,Object> item:list){
					Object startPrice = item.get("startPrice");
					Object endPrice = item.get("endPrice");
					if(startPrice!=null&&ValidatorUtil.isNumber(startPrice.toString())){
						e = new PriceRangeModel();
						e.setBegin(Integer.parseInt(startPrice.toString()));
						if(endPrice!=null&&ValidatorUtil.isNumber(endPrice.toString())){
							e.setEnd(Integer.parseInt(endPrice.toString()));
						}
						list2.add(e);
					}
				}
				
				//根据开始值升序
				Collections.sort(list2, (item1,item2)->{
					if(item1.getBegin()<item2.getBegin()){
						return -1;
					}else if(item1.getBegin()>item2.getBegin()){
						return 1;
					}
					return 0;
				});
				return list2;
			}
		}
		BigDecimal maxPrice = productsMapper.getSupplierProtocolMaxPriceByEntity(entity);
		return PriceUtil.getRanges(maxPrice);
	}
	
	@Override
	public List<ProductParameter> getQueryResultParameters(Products entity) {
		List<ProductParameter> list = productsMapper.getSupplierProtocolProductParameters(entity);
		list.forEach(pp -> {
			//pp.setParameter(getParameters(pp.getStandParamId()));
			
			//pp.setParameterItems(getParameterItems(pp.getStandValueId()));
			pp.setParameterItems(getParameterItems(pp));
		});
		
		Map<String,ProductParameter> groupKeys = Maps.newHashMap();
		
		list.forEach(pp -> {
			String groupKey = pp.getStandParamId();
			if(!groupKeys.containsKey(groupKey)){
				groupKeys.put(groupKey, pp);
			}else{
				ProductParameter productParameter = groupKeys.get(groupKey);
				List<ParameterItem> productParameterItems = productParameter.getParameterItems();
				List<ParameterItem> items = pp.getParameterItems();
				if(items!=null){
					if(productParameterItems==null){
						productParameterItems = Lists.newArrayList();
					}
					Set<String> standValueIdSet = Sets.newHashSet(); //去重
					productParameterItems.forEach(ppi -> {
						standValueIdSet.add(ppi.getStandValueId());
					});
					
					for(ParameterItem item:items){
						if(!standValueIdSet.contains(item.getStandValueId())){
							standValueIdSet.add(item.getStandValueId());
							productParameterItems.add(item);
						}
					}
				}
			}
		});
		
		List<ProductParameter> result = Lists.newArrayList();
		
		final Pattern p = Pattern.compile("^(\\d+(\\.\\d+)?)(.*)");//匹配数字开头
		groupKeys.forEach((key,value) -> {
			if(value.getParameter()!=null&&value.getParameterItems()!=null&&value.getParameterItems().size()>1){
				List<ParameterItem> items = value.getParameterItems();
				//根据参数名称排序
				Collections.sort(items, (item1,item2)->{
					String s1 = item1.getName();
					String s2 = item2.getName();
					
					if(s1.equals("无")){
						return -1;
					}
					
					if(s2.equals("无")){
						return 1;
					}
					//如果数字开头
					Matcher matcher1 = p.matcher(s1);
					Matcher matcher2 = p.matcher(s2);
					Float n1 = null;
					Float n2 = null;
					if(matcher1.matches()&&matcher2.matches()){
						 n1 = Float.parseFloat(matcher1.group(1));
						 n2 = Float.parseFloat(matcher2.group(1));
						 if(n1-n2<0){
							 return -1;
						 }
						 if(n1-n2>0){
							 return 1;
						 }
						 return 0;
					}
					
					if(matcher1.matches()){
						 return -1;
					}
					
					if(matcher2.matches()){
						 return 1;
					}
					
					//如果不是数字开头
					return s1.compareTo(s2);
				});
				value.setParameterItems(items);
				result.add(value);
			}
		});
		return result;
	}
	
	private Parameter getParameters(String standParamId){
		if(StringUtils.isNotEmpty(standParamId)){
			Parameter parameter = parameterMapper.getOneByStandParamId(standParamId);
			return parameter;
		}
		return null;
	}
	
	@Deprecated
	private List<ParameterItem> getParameterItems(String standValueIds){
		if(StringUtils.isNotEmpty(standValueIds)){
			String[] valueIds = standValueIds.split(",");
			if(valueIds.length>0){
				return parameterItemMapper.getByStandValueIds(valueIds);
			}
		}
		return null;
	}
	
	private List<ParameterItem> getParameterItems(ProductParameter productParameter){
		if(StringUtils.isNotEmpty(productParameter.getStandValueId())&&StringUtils.isNotEmpty(productParameter.getParamValue())){
			String[] valueIds = productParameter.getStandValueId().split(",");
			String[] paramValues = productParameter.getParamValue().split(",");
			if(valueIds.length>0&&paramValues.length>0&&valueIds.length==paramValues.length){
				List<ParameterItem> piList = Lists.newArrayList();
				ParameterItem pItem = null;
				//Set<String> standValueSet = Sets.newHashSet();//去重
				for(int i=0;i<valueIds.length;i++){
					//if(!standValueSet.contains(valueIds[i])){
						pItem = new ParameterItem();
						pItem.setStandValueId(valueIds[i]);
						pItem.setName(paramValues[i]);
						piList.add(pItem);
					//}
				}
				return piList;
			}
		}
		return null;
	}
	@Override
	public Set<Long> getKeyProductParameterItemIds(List<ProductParameter> productParameters) {
		if(productParameters!=null){
			Set<Long> parameterIds = Sets.newHashSet();
			if(productParameters!=null){
				for(ProductParameter pp:productParameters){
					Parameter parameter = pp.getParameter();
					if(parameter!=null&&parameter.getId()!=null&&pp.isKey()){
						parameterIds.add(parameter.getId());
					}
				}
			}
			if(!parameterIds.isEmpty()){
				return parameterItemMapper.getByParameterIds(parameterIds);
			}
		}
		return null;
	}
	@Override
	public List<MallProducts> findSupplierProtocolAggregationListByEntity(Products entity) {
		return findSupplierProtocolAggregationListByEntity(entity,true);
	}
	
	private List<MallProducts> findSupplierProtocolAggregationListByEntity(Products entity,boolean isCallApi) {
		List<MallProducts> list = productsMapper.findSupplierProtocolAggregationListByEntity(entity);
		if(isCallApi){
			list.forEach(mp -> {
				fillEmallUrlAndPriceAndRefreshState(mp);
			});
		}
		return list;
	}
	
	private void insertHistory(Products products,String message) {
		UpperLowerHistory record=new UpperLowerHistory();
		record.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		record.setProductId(products.getId());
		record.setReason(message);
		record.setStatus(1);
		record.setType(4);
		record.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		upperLowerHistoryMapper.insert(record);
	}
	
	@Override
	public ProductAttributeModel getProductAttributeModel(MallProducts mallProduct) {
		ProductAttributeModel attr = new ProductAttributeModel();
		attr.setProductId(mallProduct.getId());
		if (mallProduct.getCatalogId() == null || mallProduct.getBrandId() == null || mallProduct.getModel() == null) {
			attr.setAggregationNum(1);
		}else{
			attr.setAggregationNum(getAggregationNum(mallProduct));
		}
		PlatformCatalogs platformCatalogs = platformCatalogsService.selectByKey(mallProduct.getCatalogId());
		attr.setCollect(isCollect(platformCatalogs));
		
		if(platformCatalogs!=null){
			Catalogs catalogs = catalogsMapper.selectByPrimaryKey(platformCatalogs.getCatalogId());
			if(catalogs!=null&&catalogs.getTypes()!=null){
				String[] ts = catalogs.getTypes().split(",");
				attr.setEnergyConservation(ValidatorUtil.isContainStringFormArray(ts, "1"));
				attr.setEnvironmentalProtection(ValidatorUtil.isContainStringFormArray(ts, "2"));
			}
		}
		
		/*if(mallProduct.getSupplierId()!=null){
			attr.setPayWayArray(getPayWayArray(mallProduct.getSupplierId()));
			attr.setDeliveryArray(getDeliveryArray(mallProduct.getSupplierId()));
		}*/
		return attr;
	}
	/**
	 * 支付方式
	 * @param supplier
	 * @return
	 */
	private PayWayVo[] getPayWayArray(Long supplierId){
		List<PayWay> payWays = payWayService.findSupplierUsableList(supplierId);
		if(payWays!=null&&payWays.size()>0){
			Set<PayWayVo> payWayvos = Sets.newHashSet();
			
			payWays.forEach(payway -> {
				PayWayVo payWayVo = new PayWayVo(payway.getId(), payway.getName());
				payWayVo.setIcon(AssistUtil.getPayWayType(payway.getName()));
				payWayvos.add(payWayVo);
			});
			
			return payWayvos.toArray(new PayWayVo[payWayvos.size()]);
		}
		return null;
	}
	
	/**
	 * 配送方式
	 * @param supplier
	 * @return
	 */
	private DeliveryVo[] getDeliveryArray(Long supplierId){
		List<DeliveryWay> deliveryWays = deliveryWayMapper.findSupplierUsableList(supplierId);
		if(deliveryWays!=null&&deliveryWays.size()>0){
			Set<DeliveryVo> deliveryVos = Sets.newHashSet();
			deliveryWays.forEach(deliveryWay -> {
				DeliveryVo deliveryVo = new DeliveryVo(deliveryWay.getId(), deliveryWay.getName());
				deliveryVo.setIcon(AssistUtil.getDeliveryType(deliveryWay.getName()));
				deliveryVos.add(deliveryVo);
			});
			return deliveryVos.toArray(new DeliveryVo[deliveryVos.size()]);
		}
		return null;
	}
	public int getAggregationNum(MallProducts mallProduct){
		Map<String, Object> queryFilter = mallProduct.getQueryfilter();
		if(queryFilter==null){
			queryFilter = Maps.newHashMap();
		}
		queryFilter.put("mcid", mallProduct.getCatalogId());
		queryFilter.put("andCidEq", 1);
		
		if(mallProduct.getProductParameters()!=null){
			queryFilter.put("inParameterItems", getKeyProductParameterItemIds(mallProduct.getProductParameters()));
		}
		mallProduct.setQueryfilter(queryFilter);
		return productsMapper.countSupplierProtocolAggregationListByEntity(mallProduct);
	}
	
	public boolean isCollect(PlatformCatalogs platformCatalogs){
		if(platformCatalogs==null||platformCatalogs.getTypes()==null){
			return false;
		}
		return ValidatorUtil.isContainStringFormArray(platformCatalogs.getTypes().split(","), "collection");
	}
	
	@Override
	public List<ProductCompareRow> getProductCompareRows(Long[] pids) {
		
		List<Parameter> productParameterList = Lists.newArrayList();
		List<MallProducts> productList = Lists.newArrayList();
		
		for(Long pid:pids){
			MallProducts product = productsMapper.getById(pid);
			if(product!=null){
				List<ProductParameter> productParameters = productParameterMapper.getByProductId(pid);
				productParameters.forEach(pp -> {
					Parameter parameter = getParameters(pp.getStandParamId());
					pp.setParameter(parameter);
					pp.setParameterItems(getParameterItems(pp.getStandValueId()));
					if(parameter!=null&&pp.getParamValue()!=null&&!productParameterList.contains(parameter)){
						productParameterList.add(parameter);
					}
				});
				product.setProductParameters(productParameters);
				//product.setAttributeModel(getProductAttributeModel(product));
				productList.add(product);
			}
		}
		
		List<ProductCompareRow> rows = Lists.newArrayList();
		
		ProductCompareRow row1 = new ProductCompareRow();
		row1.setName("参数");
		List<ProductCompareCell> row1cells = Lists.newArrayList();
		productList.forEach(product -> {
			ProductCompareCell cell = new ProductCompareCell(product.getName(),product.getCoverUrl(),1);
			cell.setUrl(ProductUtil.getMallProductUrl(product.getCatalogId(), product.getId()));
			row1cells.add(cell);
		});
		row1.setCells(row1cells);
		rows.add(row1);
		
		ProductCompareRow row2 = new ProductCompareRow();
		row2.setName("名称");
		List<ProductCompareCell> row2cells = Lists.newArrayList();
		productList.forEach(product -> {
			ProductCompareCell cell = new ProductCompareCell(product.getName(),product.getName(),0);
			cell.setUrl(ProductUtil.getMallProductUrl(product.getCatalogId(), product.getId()));
			row2cells.add(cell);
		});
		row2.setCells(row2cells);
		rows.add(row2);
		
		ProductCompareRow row3 = new ProductCompareRow();
		row3.setName("电商");
		List<ProductCompareCell> row3cells = Lists.newArrayList();
		productList.forEach(product -> {
			ProductCompareCell cell = new ProductCompareCell(product.getName(),product.getSupplierName(),0);
			row3cells.add(cell);
		});
		row3.setCells(row3cells);
		rows.add(row3);
		
		ProductCompareRow row4 = new ProductCompareRow();
		row4.setName("价格");
		List<ProductCompareCell> row4cells = Lists.newArrayList();
		productList.forEach(product -> {
			ProductCompareCell cell = new ProductCompareCell(product.getName(),"￥"+product.getPrice().setScale(2, BigDecimal.ROUND_UP),0);
			row4cells.add(cell);
		});
		row4.setCells(row4cells);
		rows.add(row4);
		
		//动态参数
		productParameterList.forEach(param -> {
			ProductCompareRow row = new ProductCompareRow();
			row.setName(param.getName());
			List<ProductCompareCell> cells = Lists.newArrayList();
			productList.forEach(product -> {
				List<ProductParameter> pps = product.getProductParameters();
				if(pps!=null&&pps.size()>0){
					boolean isHit = false;
					for(ProductParameter pp:pps){
						Parameter p = pp.getParameter();
						if(p!=null&&p.equals(param)){
							cells.add(new ProductCompareCell(p.getName(),pp.getParamValue().replaceAll(",", "、")));
							isHit = true;
							break;
						}
					}
					if(!isHit){
						cells.add(new ProductCompareCell(param.getName(),"无"));
					}
				}else{
					cells.add(new ProductCompareCell(param.getName(),"无"));
				}
				
			});
			row.setCells(cells);
			rows.add(row);
		});
		
		return rows;
	}
	@Override
	public MallProducts fillEmallUrlAndPriceAndRefreshState(MallProducts mallProduct) {
		Suppliers s = mallProduct.getSupplier();
		if(s!=null&&ApiCommon.checkCode(s.getCode())&&StringUtils.isNotEmpty(mallProduct.getSku())){
			String code = s.getCode();
			String sku = mallProduct.getSku();
			BigDecimal marketPrice = mallProduct.getMarketPrice();
			BigDecimal protocolPrice = mallProduct.getProtocolPrice();
			BigDecimal price = mallProduct.getPrice();
			if(code.equals(EmallConstant.MALL_JD_CODE)){
				mallProduct.setEmallUrl("https://item.jd.com/"+sku+".html");
				ProductPricesApi api=new ProductPricesApi(sku);
			    ProductPriceRes res = api.call();
				if(res.isSuccess() && CollectionUtils.isNotEmpty(res.getResult())){
					PriceAttr priceAttr = res.getResult().get(0);
					if((s.getIsPrice() != null && s.getIsPrice() == 1) || BigDecimalUtil.compareTo(protocolPrice, price) != 0){
						//供应商需要改价  或者 运营手动改价
						if(BigDecimalUtil.compareTo(priceAttr.getJdPrice(), marketPrice) == 0 && BigDecimalUtil.compareTo(priceAttr.getPrice(), protocolPrice) == 0){
							mallProduct.setProtocolPrice(priceAttr.getPrice());
							mallProduct.setMarketPrice(priceAttr.getJdPrice());
						}else{
							//如果价格变动直接下架  并记录
							insertHistory(mallProduct, www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+mallProduct.getMarketPrice()+",原协议价："+mallProduct.getProtocolPrice()+",现市场价："+priceAttr.getJdPrice()+",现协议价："+priceAttr.getPrice());
							mallProduct.setMarketPrice(priceAttr.getJdPrice());
							mallProduct.setProtocolPrice(priceAttr.getPrice());
							mallProduct.setStatus(4);
							mallProduct.setIsChangePrice(0);
						}
					}else{
						//供应商不需要改价  电商价格同步并保存
						mallProduct.setProtocolPrice(priceAttr.getPrice());
						mallProduct.setMarketPrice(priceAttr.getJdPrice());
						mallProduct.setPrice(priceAttr.getPrice());
					}
				}else{
					mallProduct.setStatus(4);
					insertHistory(mallProduct, res.getResultMessage());
				}
				ProductCheckApi pcApi = new ProductCheckApi(sku);
				ProductCheckRes pcres = pcApi.call();
				if(pcres.isSuccess()){
					if(pcres.getResult()!=null&&pcres.getResult().size()>0){
						if(pcres.getResult().get(0).getSaleState()==0){
							insertHistory(mallProduct, "商品不可售，自动下架");
							mallProduct.setStatus(4);
							productService.updateNotNull(mallProduct);
						}
					}
				}else{
					mallProduct.setStatus(4);
					insertHistory(mallProduct, res.getResultMessage());
				}
			}else if(code.equals(EmallConstant.MALL_STB_CODE)) {
				www.tonghao.mall.api.stb.call.ProductPriceApi api=new www.tonghao.mall.api.stb.call.ProductPriceApi(sku);
				www.tonghao.mall.api.stb.resultwrap.ProductPriceRes call = api.call();
				if(call.isSuccess()) {
					ProductPriceAttr pp = call.getAttrs().get(0);
					if((s.getIsPrice() != null && s.getIsPrice() == 1) || BigDecimalUtil.compareTo(protocolPrice, price) != 0){
						//供应商需要改价  或者 运营手动改价
						if(BigDecimalUtil.compareTo(pp.getMall_price(), marketPrice) == 0 && BigDecimalUtil.compareTo(pp.getPrice(), protocolPrice) == 0){
							mallProduct.setMarketPrice(pp.getMall_price());
							mallProduct.setProtocolPrice(pp.getPrice());
						}else{
							//如果价格变动直接下架  并记录
							insertHistory(mallProduct, www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+mallProduct.getMarketPrice()+",原协议价："+mallProduct.getProtocolPrice()+",现市场价："+pp.getMall_price()+",现协议价："+pp.getPrice());
							mallProduct.setMarketPrice(pp.getMall_price());
							mallProduct.setProtocolPrice(pp.getPrice());
							mallProduct.setStatus(4);
							mallProduct.setIsChangePrice(0);
						}
					}else{
						//供应商不需要改价  电商价格同步并保存
						mallProduct.setProtocolPrice(pp.getPrice());
						mallProduct.setMarketPrice(pp.getMall_price());
						mallProduct.setPrice(pp.getPrice());
					}
				}else{
					mallProduct.setStatus(4);
					insertHistory(mallProduct, call.getDesc());
				}
				
			}else if(code.equals(EmallConstant.MALL_DL_CODE)){
				www.tonghao.mall.api.standard.call.ProductPricesApi api=new www.tonghao.mall.api.standard.call.ProductPricesApi(code, sku);
				www.tonghao.mall.api.standard.resultwrap.ProductPriceRes priceRes = api.call();
				if(priceRes.isSuccess()){
					if(!CollectionUtil.collectionIsEmpty(priceRes.getResult())){
						www.tonghao.mall.api.standard.attwrap.PriceAttr priceAttr = priceRes.getResult().get(0);
						if((s.getIsPrice() != null && s.getIsPrice() == 1) || BigDecimalUtil.compareTo(protocolPrice, price) != 0){
							//供应商需要改价  或者 运营手动改价
							if(BigDecimalUtil.compareTo(priceAttr.getMall_price(), mallProduct.getMarketPrice()) == 0 && BigDecimalUtil.compareTo(priceAttr.getPrice(), mallProduct.getProtocolPrice()) == 0){
								mallProduct.setProtocolPrice(priceAttr.getPrice());
								mallProduct.setMarketPrice(priceAttr.getMall_price());
							}else{
								//如果价格变动直接下架  并记录
								insertHistory(mallProduct, www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+mallProduct.getMarketPrice()+",原协议价："+mallProduct.getProtocolPrice()+",现市场价："+priceAttr.getMall_price()+",现协议价："+priceAttr.getPrice());
								mallProduct.setMarketPrice(priceAttr.getMall_price());
								mallProduct.setProtocolPrice(priceAttr.getPrice());
								mallProduct.setStatus(4);
								mallProduct.setIsChangePrice(0);
							}
						}else{
							//供应商不需要改价  电商价格同步并保存
							mallProduct.setProtocolPrice(priceAttr.getPrice());
							mallProduct.setMarketPrice(priceAttr.getMall_price());
							mallProduct.setPrice(priceAttr.getPrice());
						}
					}else{
						mallProduct.setStatus(4);
						insertHistory(mallProduct, priceRes.getDesc());
					}
			    }else{
			    	mallProduct.setStatus(4);
					insertHistory(mallProduct, priceRes.getDesc());
			    }
			}
			
			//根据价格判断自动下架
			if(mallProduct.getStatus() == 3 && ApiCommon.checkCode(s.getCode())){
				//价格为0  下架
				if(BigDecimalUtil.compareTo(mallProduct.getProtocolPrice(), BigDecimalUtil.ZERO) == 0 || BigDecimalUtil.compareTo(mallProduct.getMarketPrice(), BigDecimalUtil.ZERO) == 0 ){
					insertHistory(mallProduct, "协议价或者售价为0，自动下架");
					mallProduct.setStatus(4);
				}
				//聚合查询协议价大于官网售价，自动下架
				if(BigDecimalUtil.compareTo(mallProduct.getProtocolPrice(), mallProduct.getMarketPrice()) > 0){
					insertHistory(mallProduct, "聚合查询协议价大于官网售价，自动下架");
					mallProduct.setStatus(4);
				}
			}
		}
	    productService.updateNotNull(mallProduct);
		return mallProduct;
	}
	@Override
	public ProductStock getProductStock(Products product, Long areaId, int num) {
		Suppliers s = suppliersService.selectByKey(product.getSupplierId());
		int state = 0;
		String msg = null;
		if(s!=null&&ApiCommon.checkCode(s.getCode())&&StringUtils.isNotEmpty(product.getSku())){
			String code = s.getCode();
			String sku = product.getSku();
			if(code.equals(EmallConstant.MALL_JD_CODE)){
				if(areaId!=null&&num>0){
					MappingArea mappingArea = mappingAreaService.getEmallMappingArea(areaId, code);
					if(mappingArea!=null){
						List<Sku> skuList=Lists.newArrayList();
						Sku jdSku=new Sku();
						jdSku.setNum(num);
						jdSku.setSkuId(sku);
						skuList.add(jdSku);
						ProductNewStockApi stockApi=new ProductNewStockApi(mappingArea.getMappingCode(), skuList);
						NewStockRes call = stockApi.call();
						if(call.isSuccess()&&CollectionUtils.isNotEmpty(call.getResult())){
							NewStockAttr attr = call.getResult().get(0);
							if(attr.getStockStateId()==33||attr.getStockStateId()==39||attr.getStockStateId()==40){
								state = 1;
							}
							msg = attr.getStockStateDesc();
						}else{
							msg = "商品已下架";
							insertHistory(product, call.getResultMessage());
							product.setStatus(4);
							productService.updateNotNull(product);
						}
					}
				}
			}else if(code.equals(EmallConstant.MALL_STB_CODE)) {
				if(areaId!=null){
					List<Map<String, String>> list=new ArrayList<>();
				    Map<String, String> area1=new HashMap<String, String>();
					area1.put("sku", sku);
					area1.put("num", num+"");
					list.add(area1);
					MappingArea mappingArea = mappingAreaService.getEmallMappingArea(areaId, code);
					if(mappingArea != null){
						www.tonghao.mall.api.stb.call.ProductStocksApi api=new www.tonghao.mall.api.stb.call.ProductStocksApi(mappingArea.getMappingCode(),list);
						www.tonghao.mall.api.stb.resultwrap.ProductStocksRes call = api.call();
						if(call.isSuccess()) {
							ProductStocksAttr productStocksAttr = call.getProductStocksAttrs().get(0);
						    if(productStocksAttr.getDesc()!=null&&"无货".equals(productStocksAttr.getDesc())) {
						    	msg = "库存不足";
						    }else {
						    	state=1;
						    	msg = "有货";
						    }
						}else{
							msg = "商品已下架";
							insertHistory(product, call.getDesc());
							product.setStatus(4);
							productService.updateNotNull(product);
						}
					}else{
						msg = "库存不足";
					}
				}
			} else if (code.equals(EmallConstant.MALL_DL_CODE)) {
				if (areaId != null) {
					MappingArea mappingArea = mappingAreaService.getEmallMappingArea(areaId, code);
					if (mappingArea != null) {
						ProductStocksApi api = new ProductStocksApi(code, mappingArea.getMappingCode(), sku);
						ProductStocksRes call = api.call();
						if (call.isSuccess() && call.getResult() != null && call.getResult().size() > 0) {
							ProductStockAttr attr = call.getResult().get(0);
							if (attr.getNum() >= num) {
								msg = attr.getDesc();
								state = 1;
								if (StringUtils.isEmpty(msg)) {
									msg = "库存" + attr.getNum()
											+ product.getUnit() != null ? product
											.getUnit() : "";
								}
							} else {
								msg = "库存不足";
							}
						} else {
							msg = "库存不足";
						}
					}
				} else {
					msg = "库存不足";
				}
			}
		}else{
			if(!ApiCommon.checkCode(s.getCode())){
				//本地供应商
				state = 1;
				msg = "有货";
			}else{
				if(product.getStock()!=null&&product.getStock()>0){
					state=1;
					msg = "库存"+product.getStock()+product.getUnit()!=null?product.getUnit():"";
				}
			}
		}
		
		if(msg==null&&state==0){
			msg = "库存不足";
		}
		ProductStock productStock = new ProductStock();
		productStock.setState(state);
		productStock.setMsg(msg);
		return productStock;
	}
	@Override
	public List<ProductContacts> getContacts(Long catalogId, Long supplierId, Long protocolId) {
		return productsMapper.getContacts(catalogId, supplierId, protocolId);
	}
	@Override
	public List<MallProducts> findCurrConfigureList(Map<String, Object> map) {
		List<MallProducts> list = productsMapper.findCurrConfigureList(map);
		list.forEach(mp -> {
			mp.setAttributeModel(getProductAttributeModel(mp));
		});
		return list;
	}
	@Override
	public List<MallProducts> findCurrPriceList(Map<String, Object> map) {
		List<MallProducts> list = productsMapper.findCurrPriceList(map);
		list.forEach(mp -> {
			mp.setAttributeModel(getProductAttributeModel(mp));
		});
		return list;
	}
	@Override
	public List<ProductParameter> getRelationByProductId(Long productId) {
		return productParameterMapper.getRelationByProductId(productId);
	}

	@Override
	public MallProducts vaProductPutawayStatus(MallProducts mallProduct) {
		Suppliers s = mallProduct.getSupplier();
		if(s!=null && ApiCommon.checkCode(s.getCode()) && StringUtils.isNotEmpty(mallProduct.getSku())){
			String code = s.getCode();
			String sku = mallProduct.getSku();
			if(code.equals(EmallConstant.MALL_JD_CODE)){
				www.tonghao.mall.api.jd.call.ProductStateApi productStateApi = new www.tonghao.mall.api.jd.call.ProductStateApi(sku);
				www.tonghao.mall.api.jd.resultwrap.ProductStateRes call = productStateApi.call();
				if(call.isSuccess()){
					List<www.tonghao.mall.api.jd.attwrap.StateResultAttr> result = call.getResult();
					if(CollectionUtils.isEmpty(result) || result.get(0).getState() == 0){
						mallProduct.setStatus(4);
						insertHistory(mallProduct, "电商自动下架");
					}
				}else{
					mallProduct.setStatus(4);
					insertHistory(mallProduct, call.getResultMessage());
				}
			}else if(code.equals(EmallConstant.MALL_STB_CODE)) {
				//上下架状态
				ProductStateApi api_state=new ProductStateApi(sku);
				ProductStateRes call_state = api_state.call();
				if(call_state.isSuccess()) {
					ProductStateAttr productStateAttr = call_state.getList().get(0);
					if(productStateAttr.getState()==0) {
						mallProduct.setStatus(4);
						insertHistory(mallProduct, "电商自动下架");
					}
				}else{
					mallProduct.setStatus(4);
					insertHistory(mallProduct, call_state.getDesc());
				}
			}else if(code.equals(EmallConstant.MALL_DL_CODE)){
				//小电商
				www.tonghao.mall.api.standard.call.ProductStateApi api_state=new www.tonghao.mall.api.standard.call.ProductStateApi(code, sku);
				www.tonghao.mall.api.standard.resultwrap.ProductStateRes call_state = api_state.call();
				if(call_state.isSuccess()) {
					if(!CollectionUtil.collectionIsEmpty(call_state.getResult())){
						StateResultAttr stateResultAttr = call_state.getResult().get(0);
						if(stateResultAttr.getState() == 0){
							mallProduct.setStatus(4);
							insertHistory(mallProduct, "电商自动下架");
						}
					}else{
						mallProduct.setStatus(4);
						insertHistory(mallProduct, "电商自动下架");
					}
				}else{
					mallProduct.setStatus(4);
					insertHistory(mallProduct, call_state.getDesc());
				}
			}
		}
	    productService.updateNotNull(mallProduct);
		return mallProduct;
	}

	@Override
	public Map<String, Object> checkAreaLimit(MallProducts product, Long areaId) {
		Suppliers s = suppliersService.selectByKey(product.getSupplierId());
		if (s != null && ApiCommon.checkCode(s.getCode()) && StringUtils.isNotEmpty(product.getSku())) {
			String code = s.getCode();
			String sku = product.getSku();
			if (code.equals(EmallConstant.MALL_JD_CODE) && areaId != null) {
				MappingArea mappingArea = mappingAreaService.getEmallMappingArea(areaId, code);
				if (mappingArea != null && mappingArea.getMappingCode() != null) {
					String mappingCode = mappingArea.getMappingCode();
					String[] split = mappingCode.split("_");
					if(split.length >= 3){
						CheckAreaApi checkAreaApi = new CheckAreaApi(sku, split[0], split[1], split[2]);
						CheckAreaRes call = checkAreaApi.call();
						if(call.isSuccess() && CollectionUtils.isNotEmpty(call.getAttr())){
							if(call.getAttr().get(0).isAreaRestrict()){
								return ResultUtil.error("该地区不支持配送");
							}else{
								return ResultUtil.success("");
							}
						}else{
							return ResultUtil.error("该地区不支持配送");
						}
					}else{
						return ResultUtil.error("该地区未映射");
					}
				}else{
					return ResultUtil.error("该地区未映射");
				}
			} else {
				return ResultUtil.success("");
			}
		}else if(s != null && !ApiCommon.checkCode(s.getCode())){
			return ResultUtil.success("");
		}else{
			return ResultUtil.error("未找到该供应商信息");
		}
	}
}
