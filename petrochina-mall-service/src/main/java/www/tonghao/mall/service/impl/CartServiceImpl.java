package www.tonghao.mall.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.Constant;
import www.tonghao.common.constant.EmallConstant;
import www.tonghao.common.utils.BigDecimalUtil;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.api.jd.attwrap.CheckAreaAttr;
import www.tonghao.mall.api.jd.attwrap.NewStockAttr;
import www.tonghao.mall.api.jd.attwrap.PriceAttr;
import www.tonghao.mall.api.jd.attwrap.ProductCheckAttr;
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
import www.tonghao.mall.core.ApiCommon;
import www.tonghao.mall.entity.Cart;
import www.tonghao.mall.entity.CartItems;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.mall.mapper.CartItemsMapper;
import www.tonghao.mall.mapper.CartMapper;
import www.tonghao.mall.mapper.MallProductsMapper;
import www.tonghao.mall.service.CartItemsService;
import www.tonghao.mall.service.CartService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.IntegralUser;
import www.tonghao.service.common.entity.MappingArea;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.SystemSetting;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.ActivityMapper;
import www.tonghao.service.common.mapper.ActivityProductMapper;
import www.tonghao.service.common.mapper.IntegralUserMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.mapper.SystemSettingMapper;
import www.tonghao.service.common.mapper.UpperLowerHistoryMapper;
import www.tonghao.service.common.service.MappingAreaService;
import www.tonghao.service.common.service.ProductsService;
@Service(value="mallCartService")
public class CartServiceImpl extends BaseServiceImpl<Cart> implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private CartItemsService cartItemsService;

	@Autowired
	private CartItemsMapper cartItemsMapper;
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private MappingAreaService mappingAreaService;
	
	@Autowired
	private UpperLowerHistoryMapper upperLowerHistoryMapper;
	
	@Autowired
	private IntegralUserMapper integralUserMapper;
	
    @Autowired
    private MallProductsMapper mallProductsMapper;
	
	@Autowired
	private ActivityProductMapper activityProductMapper;
	
	@Autowired
	private SystemSettingMapper systemSettingMapper;
	
	@Override
	public Cart add(Cart cart) {
		save(cart);
		return cart;
	}
	
	@Override
	public Cart findById(Long id) {
		return cartMapper.findById(id);
	}

	@Override
	public Cart getUserCart(Long userId) {
		return cartMapper.getUserCart(userId);
	}

	@Override
	public void deleteOrderChecked(Cart cart) {
		List<CartItems> items = cartItemsService.findListByCartId(cart.getId());
		int itemSize = items.size();
		int count=0;
		for (CartItems item : items) {
			//只删除选中的购物车项
			if(item.getIsChecked()){
				cartItemsService.delete(item.getId());
				count++;
			}
		}
		//如果删除的item数量等于items大小，说明购物车已清空
		if(itemSize==count){
			delete(cart.getId());
		}
	}

	@Override
	public void truncate(Cart cart) {
		if(cart==null||cart.getId()==null) return ;
		
		List<CartItems> items = cart.getCartItems();
		if(items!=null){
			for (CartItems item : items) {
				cartItemsService.delete(item.getId());
			}
		}
	}

	@Override
	public LinkedHashMap<String, Map<String, List<CartItems>>> getUserCartItems(Long userId, Integer isChecked) {
		Map<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("userId", userId);
		if(isChecked != null){
			selectMap.put("isChecked", isChecked);
		}
		List<CartItems> cartItems = findListByUserId(selectMap);
		if(!CollectionUtil.collectionIsEmpty(cartItems)){
			
			//遍历根据活动分组
			Map<String, List<CartItems>> avtivityMap = cartItems.stream().collect(Collectors.groupingBy(ci -> ci.getActivityId() == null ? 0 +"_暂无活动" : ci.getActivityId()+"_"+ci.getActivity().getName()));
					
			//遍历集合根据供应商分组
			LinkedHashMap<String, Map<String, List<CartItems>>> resultMap = new LinkedHashMap<String, Map<String,List<CartItems>>>();
			avtivityMap.entrySet().stream().sorted(((m1, m2) -> m2.getKey().compareTo(m1.getKey()))).forEach((entry) -> {
				String key = entry.getKey();
				List<CartItems> value = entry.getValue();
				Map<String, List<CartItems>> supplierMap = value.stream().collect(Collectors.groupingBy(v -> v.getSupplierId()+"_"+v.getSupplier().getName()));
				
				resultMap.put(key, supplierMap);
			});
			return resultMap;
		}
		return null;
	}

	@Override
	public Map<String, Object> checkSkip(Users user, Long areaId, List<CartItems> cartItemsList) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Set<Long> activityIdSet = new HashSet<>();
		resultMap = ResultUtil.success("");
		//获取是否开启混合支付配置
		SystemSetting systemSetting = systemSettingMapper.selectByCode("open_mixture_pay");
		boolean openMixturePay = systemSetting != null && "1".equals(systemSetting.getSetValue());
		if(!CollectionUtil.collectionIsEmpty(cartItemsList)){
			//商品是否都关联活动标识
			Boolean activityflag = null;
			Map<String, List<CartItems>> supplier_items_map = new HashMap<>();
			for (CartItems cartItems : cartItemsList) {
				//未开启混合支付 非活动商品不能下单
				if(!openMixturePay && cartItems.getActivityId() == null){
					activityflag = false;
					resultMap.put("status", "error");
					resultMap.put("message", "非活动商品不能下单");
					break;
				}
				Products product = cartItems.getProduct();
				Suppliers sup = cartItems.getSupplier();
				//判断商品是否 全都关联活动   或者   全都不关联活动
				if(cartItems.getActivityId() != null){
					activityIdSet.add(cartItems.getActivityId());
				}
				if(activityflag == null){
					activityflag = cartItems.getActivityId() != null;
				}else if(activityflag != (cartItems.getActivityId() != null)){
					resultMap.put("status", "error");
					resultMap.put("message", "非活动商品与活动商品不能一起下单");
					break;
				}
				//判断供应商
				if(sup == null){
					resultMap.put("status", "error");
					resultMap.put("message", "供应商不可用！");
					break;
				}
				//判断商品
				if(product == null || product.getStatus() != 3){
					resultMap.put("status", "error");
					resultMap.put("message", "商品" + product.getName() + "未上架");
					break;
				}
				//按照供应商分组
				if(supplier_items_map.get(sup.getCode()) == null){
					List<CartItems> suppliersCartItemList = new ArrayList<CartItems>();
					suppliersCartItemList.add(cartItems);
					supplier_items_map.put(sup.getCode(), suppliersCartItemList);
				}else{
					List<CartItems> suppliersCartItemList = supplier_items_map.get(sup.getCode());
					suppliersCartItemList.add(cartItems);
					supplier_items_map.put(sup.getCode(), suppliersCartItemList);
				}
			}
			
			//判断是否分配积分
			if(activityflag && "success".equals(resultMap.get("status"))){
				for (Long aid : activityIdSet) {
					//查询活动状态
					Integer vaIsStart = activityMapper.vaIsStart(aid, user.getId());
					if(vaIsStart == null || vaIsStart == 0){
						resultMap.put("status", "error");
						resultMap.put("message", "请在活动进行期间购买");
						break;
					}
					IntegralUser integralUser = new IntegralUser();
					integralUser.setActivityId(aid);
					integralUser.setUserId(user.getId());
					List<IntegralUser> select = integralUserMapper.select(integralUser);
					if(CollectionUtils.isEmpty(select)){
						resultMap.put("status", "error");
						resultMap.put("message", "未查询到活动积分 请联系管理员分配");
						break;
					}
				}
			}
			//批量校验商品状态
			if(ResultUtil.isSuccess(resultMap) && supplier_items_map != null){
				for (Map.Entry<String, List<CartItems>> entry : supplier_items_map.entrySet()) {
		            if(EmallConstant.MALL_JD_CODE.equals(entry.getKey())) {
						try {
							resultMap = checkJdProduct(areaId, entry.getValue());
						} catch (Exception e) {
							resultMap.put("status", "error");
							resultMap.put("message", "接口请求异常");
							e.printStackTrace();
							break;
						}
					}else{
						if(!ApiCommon.checkCode(entry.getKey())){
							//本地供应商
							resultMap = ResultUtil.success("");
						}
					}
		        }
			}
		}else{
			resultMap.put("status", "error");
			resultMap.put("message", "请先选择商品后下单");
		}
		return resultMap;
	}

	/**
	 * 
	 * Description: 京东商品校验
	 * 
	 * @data 2019年7月9日
	 * @param 
	 * @return
	 */
	private Map<String, Object> checkJdProduct(Long areaId, List<CartItems> cartItemList) {
		Suppliers supplier = cartItemList.get(0).getSupplier();
		StringBuffer sb = new StringBuffer();
		String skus = "";
		Map<String, Products> sku_product = new HashMap<>();
		List<Sku> skuList = new ArrayList<>();
		if(!CollectionUtil.collectionIsEmpty(cartItemList)){
			for (CartItems cartItems2 : cartItemList) {
				Products product = cartItems2.getProduct();
				sb.append(product.getSku() + ",");
				sku_product.put(product.getSku(), product);
				Sku jdSku = new Sku();
				jdSku.setNum(cartItems2.getNum());
				jdSku.setSkuId(product.getSku());
				skuList.add(jdSku);
			}
		}
		if(sb.length() > 0){
			skus = sb.toString().substring(0, sb.length() - 1);
		}
		Map<String, Object> resultMap = new HashMap<>();
		//商品是否可售
		ProductCheckApi pcApi = new ProductCheckApi(skus);
		ProductCheckRes pcres = pcApi.call();
		if(pcres.isSuccess() && !CollectionUtil.collectionIsEmpty(pcres.getResult())){
			for (ProductCheckAttr productCheckAttr : pcres.getResult()) {
				if(productCheckAttr.getSaleState() == 0){
					Products product = sku_product.get(productCheckAttr.getSkuId());
					product.setStatus(4);
					resultMap.put("status", "error");
					resultMap.put("message", "商品" + product.getName() + "不可售，已下架");
					insertHistory(product.getId(), "商品不可售，自动下架");
					productsService.updateNotNull(product);
					return resultMap;
				}
			}
		}else{
			resultMap.put("status", "error");
			resultMap.put("message", pcres.getResultMessage());
			return resultMap;
		}
		//上下架状态
		www.tonghao.mall.api.jd.call.ProductStateApi productStateApi = new www.tonghao.mall.api.jd.call.ProductStateApi(skus);
		www.tonghao.mall.api.jd.resultwrap.ProductStateRes call = productStateApi.call();
		if(call.isSuccess() && !CollectionUtil.collectionIsEmpty(call.getResult())){
			for (www.tonghao.mall.api.jd.attwrap.StateResultAttr stateResultAttr : call.getResult()) {
				if(stateResultAttr.getState() == 0){
					Products product = sku_product.get(stateResultAttr.getSku());
					product.setStatus(4);
					resultMap.put("status", "error");
					resultMap.put("message", "商品" + product.getName() + "已下架");
					insertHistory(product.getId(), "电商自动下架");
					productsService.updateNotNull(product);
					return resultMap;
				}
			}
		}else{
			resultMap.put("status", "error");
			resultMap.put("message", call.getResultMessage());
			return resultMap;
		}
		//库存
		if(areaId != null){
			MappingArea mappingArea = mappingAreaService.getEmallMappingArea(areaId, EmallConstant.MALL_JD_CODE);
			if(mappingArea != null){
				ProductNewStockApi stockApi=new ProductNewStockApi(mappingArea.getMappingCode(), skuList);
				NewStockRes stockCall = stockApi.call();
				if(stockCall.isSuccess() && !CollectionUtil.collectionIsEmpty(stockCall.getResult())){
					for (NewStockAttr attr : stockCall.getResult()) {
						if (attr.getStockStateId() == 33 || attr.getStockStateId() == 39 || attr.getStockStateId() == 40) {
							// 有货
						} else{
							Products product = sku_product.get(attr.getSkuId());
							resultMap.put("status", "error");
							resultMap.put("message", "商品" + product.getName() + "库存不足");
							return resultMap;
						}
					}
				}else{
					resultMap.put("status", "error");
					resultMap.put("message", stockCall.getResultMessage());
					return resultMap;
				}
			}else{
				resultMap.put("status", "error");
				resultMap.put("message", "地区未映射");
				return resultMap;
			}
		}
		//查询商品区域购买限制
		if(areaId != null){
			MappingArea mappingArea = mappingAreaService.getEmallMappingArea(areaId, EmallConstant.MALL_JD_CODE);
			if (mappingArea != null && mappingArea.getMappingCode() != null) {
				String mappingCode = mappingArea.getMappingCode();
				String[] split = mappingCode.split("_");
				if(split.length >= 3){
					CheckAreaApi checkAreaApi = new CheckAreaApi(skus, split[0], split[1], split[2]);
					CheckAreaRes checkAreaCall = checkAreaApi.call();
					if(checkAreaCall.isSuccess() && !CollectionUtil.collectionIsEmpty(checkAreaCall.getAttr())){
						for (CheckAreaAttr checkAreaAttr : checkAreaCall.getAttr()) {
							if(checkAreaAttr.isAreaRestrict()){
								Products product = sku_product.get(checkAreaAttr.getSkuId());
								resultMap.put("status", "error");
								resultMap.put("message", "商品" + product.getName() + "在该地区不支持配送");
								return resultMap;
							}
						}
					}else{
						resultMap.put("status", "error");
						resultMap.put("message", checkAreaCall.getResultMessage());
						return resultMap;
					}
				}else{
					resultMap.put("status", "error");
					resultMap.put("message", "地区未映射");
					return resultMap;
				}
			}else{
				resultMap.put("status", "error");
				resultMap.put("message", "地区未映射");
				return resultMap;
			}
		}
		//价格
		ProductPricesApi api=new ProductPricesApi(skus);
	    ProductPriceRes res = api.call();
	    if(res.isSuccess() && !CollectionUtil.collectionIsEmpty(res.getResult())){
	    	for (PriceAttr priceAttr : res.getResult()) {
	    		Products product = sku_product.get(priceAttr.getSkuId());
	    		BigDecimal marketPrice = product.getMarketPrice();
	    		BigDecimal protocolPrice = product.getProtocolPrice();
	    		BigDecimal price = product.getPrice();
	    		if((supplier.getIsPrice() != null && supplier.getIsPrice() == 1) || BigDecimalUtil.compareTo(protocolPrice, price) != 0){
					//供应商需要改价  或者 运营手动改价
		    		if(BigDecimalUtil.compareTo(priceAttr.getJdPrice(), marketPrice) == 0 && BigDecimalUtil.compareTo(priceAttr.getPrice(), protocolPrice) == 0){
						product.setProtocolPrice(priceAttr.getPrice());
						product.setMarketPrice(priceAttr.getJdPrice());
					}else{
						//如果价格变动直接下架  并记录
						insertHistory(product.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+product.getMarketPrice()+",原协议价："+product.getProtocolPrice()+",现市场价："+priceAttr.getJdPrice()+",现协议价："+priceAttr.getPrice());
						product.setMarketPrice(priceAttr.getJdPrice());
						product.setProtocolPrice(priceAttr.getPrice());
						product.setStatus(4);
						product.setIsChangePrice(0);
						resultMap.put("status", "error");
						resultMap.put("message", "商品" + product.getName() + "已下架");
						productsService.updateNotNull(product);
						return resultMap;
					}
		    	}else{
		    		//供应商不需要改价
		    		if(BigDecimalUtil.compareTo(priceAttr.getJdPrice(), marketPrice) == 0 && BigDecimalUtil.compareTo(priceAttr.getPrice(), protocolPrice) == 0 && BigDecimalUtil.compareTo(priceAttr.getPrice(), price) == 0){
						product.setProtocolPrice(priceAttr.getPrice());
						product.setMarketPrice(priceAttr.getJdPrice());
						product.setPrice(priceAttr.getPrice());
					}else{
						product.setProtocolPrice(priceAttr.getPrice());
						product.setMarketPrice(priceAttr.getJdPrice());
						product.setPrice(priceAttr.getPrice());
						productsService.updateNotNull(product);
					}
		    	}
	    		if(product.getStatus() == 3){
	    	    	//价格为0  下架
	    			if(BigDecimalUtil.compareTo(product.getProtocolPrice(), BigDecimalUtil.ZERO) == 0 || BigDecimalUtil.compareTo(product.getMarketPrice(), BigDecimalUtil.ZERO) == 0 ){
	    				product.setStatus(4);
	    				resultMap.put("status", "error");
	    				resultMap.put("message", "商品" + product.getName() + "已下架");
	    				insertHistory(product.getId(), "协议价或者售价为0，自动下架");
	    				productsService.updateNotNull(product);
	    				return resultMap;
	    			}
	    	    	if(BigDecimalUtil.compareTo(product.getProtocolPrice(), product.getMarketPrice()) > 0){
	    				//判断价格
	    				product.setStatus(4);
	    				resultMap.put("status", "error");
	    				resultMap.put("message", "商品" + product.getName() + "已下架");
	    				insertHistory(product.getId(), "商品协议价大于市场价，该商品下架");
	    				productsService.updateNotNull(product);
	    				return resultMap;
	    			}
	    	    }
	    		
			}
	    }else{
			resultMap.put("status", "error");
			resultMap.put("message", res.getResultMessage());
			return resultMap;
	    }
		return ResultUtil.success("");
	}
	
	/**
	 * 
	 * Description: 史泰博商品校验
	 * 
	 * @data 2019年7月9日
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String, Object> checkStbProduct(Long areaId, CartItems cartItems, Products product, Suppliers sup) {
		Map<String, Object> resultMap = new HashMap<>();
		//上下架状态
		ProductStateApi api_state=new ProductStateApi(product.getSku());
		ProductStateRes call_state = api_state.call();
		if(call_state.isSuccess()) {
			ProductStateAttr productStateAttr = call_state.getList().get(0);
			if(productStateAttr.getState()==0) {
				product.setStatus(4);
				resultMap.put("status", "error");
				resultMap.put("message", "商品" + product.getName() + "已下架");
				insertHistory(product.getId(), "电商自动下架");
				productsService.updateNotNull(product);
				return resultMap;
			}
		}else{
			product.setStatus(4);
			resultMap.put("status", "error");
			resultMap.put("message", "商品" + product.getName() + "已下架");
			insertHistory(product.getId(), call_state.getDesc());
			productsService.updateNotNull(product);
			return resultMap;
		}
		//库存
		if(areaId != null){
			List<Map<String, String>> mapList = new ArrayList<>();
		    Map<String, String> area1 = new HashMap<String, String>();
			area1.put("sku", product.getSku());
			area1.put("num", cartItems.getNum()+"");
			mapList.add(area1);
			MappingArea mappingArea = mappingAreaService.getEmallMappingArea(areaId, sup.getCode());
			if(mappingArea != null){
				www.tonghao.mall.api.stb.call.ProductStocksApi api=new www.tonghao.mall.api.stb.call.ProductStocksApi(mappingArea.getMappingCode(),mapList);
				www.tonghao.mall.api.stb.resultwrap.ProductStocksRes call = api.call();
				if(call.isSuccess()) {
					ProductStocksAttr productStocksAttr = call.getProductStocksAttrs().get(0);
				    if(productStocksAttr.getDesc() != null && "无货".equals(productStocksAttr.getDesc())) {
						resultMap.put("status", "error");
						resultMap.put("message", "商品" + product.getName() + "库存不足");
						return resultMap;
				    }
				}else{
					product.setStatus(4);
					resultMap.put("status", "error");
					resultMap.put("message", "商品" + product.getName() + "已下架");
					insertHistory(product.getId(), call.getDesc());
					productsService.updateNotNull(product);
					return resultMap;
				}
			}else{
				resultMap.put("status", "error");
				resultMap.put("message", "地区未映射");
				return resultMap;
			}
		}
		//价格
		BigDecimal marketPrice = product.getMarketPrice();
		BigDecimal protocolPrice = product.getProtocolPrice();
		BigDecimal price = product.getPrice();
		www.tonghao.mall.api.stb.call.ProductPriceApi api=new www.tonghao.mall.api.stb.call.ProductPriceApi(product.getSku());
		www.tonghao.mall.api.stb.resultwrap.ProductPriceRes call = api.call();
		if(call.isSuccess()) {
			ProductPriceAttr pp = call.getAttrs().get(0);
			if((sup.getIsPrice() != null && sup.getIsPrice() == 1) || BigDecimalUtil.compareTo(protocolPrice, price) != 0){
				//供应商需要改价  或者 运营手动改价
				if(BigDecimalUtil.compareTo(pp.getMall_price(), marketPrice) == 0 && BigDecimalUtil.compareTo(pp.getPrice(), protocolPrice) == 0){
					product.setMarketPrice(pp.getMall_price());
					product.setProtocolPrice(pp.getPrice());
				}else{
					//如果价格变动直接下架  并记录
					insertHistory(product.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+product.getMarketPrice()+",原协议价："+product.getProtocolPrice()+",现市场价："+pp.getMall_price()+",现协议价："+pp.getPrice());
					product.setMarketPrice(pp.getMall_price());
					product.setProtocolPrice(pp.getPrice());
					product.setStatus(4);
					product.setIsChangePrice(0);
					resultMap.put("status", "error");
					resultMap.put("message", "商品" + product.getName() + "已下架");
					productsService.updateNotNull(product);
					return resultMap;
				}
			}else{
				//供应商不需要改价 
				product.setMarketPrice(pp.getMall_price());
				product.setProtocolPrice(pp.getPrice());
				product.setPrice(pp.getPrice());
				productsService.updateNotNull(product);
			}
		}else{
			product.setStatus(4);
			resultMap.put("status", "error");
			resultMap.put("message", "商品" + product.getName() + "已下架");
			insertHistory(product.getId(), call.getDesc());
			productsService.updateNotNull(product);
			return resultMap;
		}
		if(product.getStatus() == 3){
	    	//价格为0  下架
			if(BigDecimalUtil.compareTo(product.getProtocolPrice(), BigDecimalUtil.ZERO) == 0 || BigDecimalUtil.compareTo(product.getMarketPrice(), BigDecimalUtil.ZERO) == 0 ){
				product.setStatus(4);
				resultMap.put("status", "error");
				resultMap.put("message", "商品" + product.getName() + "已下架");
				insertHistory(product.getId(), "协议价或者售价为0，自动下架");
				productsService.updateNotNull(product);
				return resultMap;
			}
	    	if(BigDecimalUtil.compareTo(product.getProtocolPrice(), product.getMarketPrice()) > 0){
				//判断价格
				product.setStatus(4);
				resultMap.put("status", "error");
				resultMap.put("message", "商品" + product.getName() + "已下架");
				insertHistory(product.getId(), "商品协议价大于市场价，该商品下架");
				productsService.updateNotNull(product);
				return resultMap;
			}
	    }
		return ResultUtil.success("");
	}
	
	/**
	 * 
	 * Description: 得力商品校验
	 * 
	 * @data 2019年7月9日
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String, Object> checkDlProduct(Long areaId, CartItems cartItems, Products product, Suppliers sup) {
		Map<String, Object> resultMap = new HashMap<>();
		//上下架状态
		www.tonghao.mall.api.standard.call.ProductStateApi api_state=new www.tonghao.mall.api.standard.call.ProductStateApi(sup.getCode(), product.getSku());
		www.tonghao.mall.api.standard.resultwrap.ProductStateRes call_state = api_state.call();
		if(call_state.isSuccess()) {
			if(!CollectionUtil.collectionIsEmpty(call_state.getResult())){
				StateResultAttr stateResultAttr = call_state.getResult().get(0);
				if(stateResultAttr.getState() == 0){
					product.setStatus(4);
					resultMap.put("status", "error");
					resultMap.put("message", "商品" + product.getName() + "已下架");
					insertHistory(product.getId(), "电商自动下架");
					productsService.updateNotNull(product);
					return resultMap;
				}
			}else{
				product.setStatus(4);
				resultMap.put("status", "error");
				resultMap.put("message", "商品" + product.getName() + "已下架");
				insertHistory(product.getId(), "电商自动下架");
				productsService.updateNotNull(product);
				return resultMap;
			}
		}else{
			product.setStatus(4);
			resultMap.put("status", "error");
			resultMap.put("message", "商品" + product.getName() + "已下架");
			insertHistory(product.getId(), call_state.getDesc());
			productsService.updateNotNull(product);
			return resultMap;
		}
		//库存
		if (areaId != null) {
			MappingArea mappingArea = mappingAreaService.getEmallMappingArea(areaId, sup.getCode());
			if (mappingArea != null) {
				ProductStocksApi api = new ProductStocksApi(sup.getCode(), mappingArea.getMappingCode(), product.getSku());
				ProductStocksRes call = api.call();
				if (call.isSuccess() && call.getResult() != null && call.getResult().size() > 0) {
					ProductStockAttr attr = call.getResult().get(0);
					if (attr.getNum() < cartItems.getNum()) {
						resultMap.put("status", "error");
						resultMap.put("message", "商品" + product.getName() + "库存不足");
						return resultMap;
					}
				} else {
					product.setStatus(4);
					resultMap.put("status", "error");
					resultMap.put("message", "商品" + product.getName() + "已下架");
					insertHistory(product.getId(), call.getDesc());
					productsService.updateNotNull(product);
					return resultMap;
				}
			}else{
				resultMap.put("status", "error");
				resultMap.put("message", "地区未映射");
				return resultMap;
			}
		}
		//价格
		BigDecimal marketPrice = product.getMarketPrice();
		BigDecimal protocolPrice = product.getProtocolPrice();
		BigDecimal price = product.getPrice();
		www.tonghao.mall.api.standard.call.ProductPricesApi api=new www.tonghao.mall.api.standard.call.ProductPricesApi(sup.getCode(), product.getSku());
		www.tonghao.mall.api.standard.resultwrap.ProductPriceRes priceRes = api.call();
		if(priceRes.isSuccess() && !CollectionUtil.collectionIsEmpty(priceRes.getResult())){
			www.tonghao.mall.api.standard.attwrap.PriceAttr priceAttr = priceRes.getResult().get(0);
			if((sup.getIsPrice() != null && sup.getIsPrice() == 1) || BigDecimalUtil.compareTo(protocolPrice, price) != 0){
				//供应商需要改价  或者 运营手动改价
				if(BigDecimalUtil.compareTo(priceAttr.getMall_price(), marketPrice) == 0 && BigDecimalUtil.compareTo(priceAttr.getPrice(), protocolPrice) == 0){
					product.setProtocolPrice(priceAttr.getPrice());
					product.setMarketPrice(priceAttr.getMall_price());
				}else{
					//如果价格变动直接下架  并记录
					insertHistory(product.getId(), www.tonghao.mall.api.Constant.PROTOL_PRICE_ERROR+",原商品市场价："+product.getMarketPrice()+",原协议价："+product.getProtocolPrice()+",现市场价："+priceAttr.getMall_price()+",现协议价："+priceAttr.getPrice());
					product.setProtocolPrice(priceAttr.getPrice());
					product.setMarketPrice(priceAttr.getMall_price());
					product.setStatus(4);
					product.setIsChangePrice(0);
					resultMap.put("status", "error");
					resultMap.put("message", "商品" + product.getName() + "已下架");
					productsService.updateNotNull(product);
					return resultMap;
				}
			}else{
				//供应商不需要改价   电商价格同步并保存
				product.setProtocolPrice(priceAttr.getPrice());
				product.setMarketPrice(priceAttr.getMall_price());
				product.setPrice(priceAttr.getPrice());
				productsService.updateNotNull(product);
			}
		}else{
			product.setStatus(4);
			resultMap.put("status", "error");
			resultMap.put("message", "商品" + product.getName() + "已下架");
			insertHistory(product.getId(), priceRes.getDesc());
			productsService.updateNotNull(product);
			return resultMap;
		}
		if(product.getStatus() == 3){
	    	//价格为0  下架
			if(BigDecimalUtil.compareTo(product.getProtocolPrice(), BigDecimalUtil.ZERO) == 0 || BigDecimalUtil.compareTo(product.getMarketPrice(), BigDecimalUtil.ZERO) == 0 ){
				product.setStatus(4);
				resultMap.put("status", "error");
				resultMap.put("message", "商品" + product.getName() + "已下架");
				insertHistory(product.getId(), "协议价或者售价为0，自动下架");
				productsService.updateNotNull(product);
				return resultMap;
			}
	    	if(BigDecimalUtil.compareTo(product.getProtocolPrice(), product.getMarketPrice()) > 0){
				//判断价格
				product.setStatus(4);
				resultMap.put("status", "error");
				resultMap.put("message", "商品" + product.getName() + "已下架");
				insertHistory(product.getId(), "商品协议价大于市场价，该商品下架");
				productsService.updateNotNull(product);
				return resultMap;
			}
	    }
		return ResultUtil.success("");
	}
	
	@Override
	public List<CartItems> findListByUserId(Map<String, Object> map) {
		return cartItemsMapper.findListByUserId(map);
	}
	
	/**
	 * 
	 * Description: 记录商品上下架记录
	 * 
	 * @data 2019年6月25日
	 * @param 
	 * @return
	 */
	private void insertHistory(Long productId, String desc){
		UpperLowerHistory record = new UpperLowerHistory();
		record.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		record.setProductId(productId);
		record.setReason(desc);
		record.setStatus(1);
		record.setType(4);
		record.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		upperLowerHistoryMapper.insert(record);
	}

	@Override
	public Map<String, Object> checkAddCart(Users user, String ids, int num, Long activityId) {
		if (user == null) {
			return ResultUtil.error("登录失效,请重新登录");
		}
		if (user.getType() == null || user.getType() != 1) {
			return ResultUtil.error("非采购人用户不允许采购");
		}
		Map<String, Object> resultMap = ResultUtil.success("");
		if(ids != null && !"".equals(ids)){
			f1: for (String id : ids.split(",")) {
				Long pid = Long.parseLong(id);
				MallProducts product = mallProductsMapper.getById(pid);
				if (product == null) {
					resultMap = ResultUtil.error("商品不存在");
					break f1;
				}
				if (num < 1) {
					resultMap = ResultUtil.error(product.getName() + "购买数量不能小于1");
					break f1;
				}
				if (num > Constant.MAX_PRODUCT_CART_COUNT) {
					resultMap = ResultUtil.error(product.getName() + "购买数量限制");
					break f1;
				}
				if (!product.isAllowPurchase()) {
					resultMap = ResultUtil.error(product.getName() + "非电商商品不允许采购");
					break f1;
				}
				Cart cart = getUserCart(user.getId());
				//判断是否为福瑞商城活动
				Activity activity = null;
				if(activityId != null){
					activity = activityMapper.selectByPrimaryKey(activityId);
				}
				if(activity == null){
					/*Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", user.getId());
					map.put("productId", pid);
					List<ActivityProduct> ap_list = activityProductMapper.getActivityProductByUser(map);
					if(CollectionUtil.collectionIsEmpty(ap_list)){
						List<Activity> selectActivityByUser = activityMapper.selectActivityByUser(user.getId());
						if(CollectionUtils.isNotEmpty(selectActivityByUser)){
							resultMap = ResultUtil.error("个人消费即将上线，敬请期待！");
							break f1;
						}
					}else{
						for (ActivityProduct activityProduct : ap_list) {
							
						}
					}*/
					if (cart != null && cart.contains(product, null)) {
						CartItems cartItem = cart.getCartItem(product, null);
						if (cartItem != null) {
							cartItem.setNum(cartItem.getNum() + num);
							if (cartItem.getNum() < 1 || cartItem.getNum() > Constant.MAX_PRODUCT_CART_COUNT) {
								resultMap = ResultUtil.error("商品" + product.getName() + "购买数量限制");
								break f1;
							}
						}
					}
				}else{
					Long acId = null;
					if(activity.getType() != null  && activity.getType() == 2){
						acId = null;
					}else{
						acId = activityId;
					}
					if(cart != null && cart.contains(product, acId)){
						CartItems cartItem = cart.getCartItem(product, acId);
						if (cartItem != null) {
							cartItem.setNum(cartItem.getNum() + num);
							if (cartItem.getNum() < 1 || cartItem.getNum() > Constant.MAX_PRODUCT_CART_COUNT) {
								resultMap = ResultUtil.error("商品" + product.getName() + "购买数量限制");
								break f1;
							}
						}
					}
				}
			}
		}else{
			resultMap = ResultUtil.error("请先选择商品");
		}
		return resultMap;
	}

	@Override
	public void addToCart(Users user, String ids, int num, Long activityId) {
		Cart cart = getUserCart(user.getId());
		if(cart == null){
			cart = new Cart();
			cart.setUserId(user.getId());
			cart.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			add(cart);
		}
		//判断是否为积分活动
		Activity activity = null;
		if(activityId != null){
			activity = activityMapper.selectByPrimaryKey(activityId);
		}
		if(ids != null && !"".equals(ids)){
			for (String id : ids.split(",")) {
				Long pid = Long.parseLong(id);
				MallProducts product = mallProductsMapper.getById(pid);
				if(activity == null){
					/*Map<String, Object> map=new HashMap<String, Object>();
					map.put("userId", user.getId());
					map.put("productId", pid);
					List<ActivityProduct> ap_list = activityProductMapper.getActivityProductByUser(map);
					if(!CollectionUtil.collectionIsEmpty(ap_list)){
						for (ActivityProduct activityProduct : ap_list) {
							addProductToCart(num, activityProduct.getActivityId(), cart, product);
						}
					}else{
						List<Activity> selectActivityByUser = activityMapper.selectActivityByUser(user.getId());
						if(CollectionUtils.isEmpty(selectActivityByUser)){
							
						}
					}*/
					addProductToCart(num, null, cart, product);
				}else{
					 if(activity.getType() != null  && activity.getType() == 2){
						 addProductToCart(num, null, cart, product);
					 }else{
						 addProductToCart(num, activityId, cart, product);
					 }
				}
			}
		}
	}

	/**
	 * 
	 * Description: 商品添加入购物车
	 * 
	 * @data 2019年8月5日
	 * @param 
	 * @return
	 */
	private void addProductToCart(int num, Long activityId, Cart cart, MallProducts product) {
		if (cart.contains(product, activityId)) {
			CartItems cartItem = cart.getCartItem(product, activityId);
			if (cartItem != null) {
				cartItem.setNum(cartItem.getNum() + num);
				cartItem.setIsChecked(true);
				cartItem.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				cartItemsMapper.updateByPrimaryKeySelective(cartItem);
			}
		} else {
			CartItems cartItem = new CartItems();
			cartItem.setCartId(cart.getId());
			cartItem.setProductId(product.getId());
			cartItem.setNum(num);
			cartItem.setSupplierId(product.getSupplierId());
			cartItem.setIsChecked(true);
			cartItem.setActivityId(activityId);
			cartItem.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			cartItemsMapper.insertSelective(cartItem);
		}
	}

	@Override
	public Integer getCartCount(Long userId) {
		return cartMapper.getCartCount(userId);
	}
}
