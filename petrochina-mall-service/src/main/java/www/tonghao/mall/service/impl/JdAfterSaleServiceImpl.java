package www.tonghao.mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.constant.EmallConstant;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.api.jd.attwrap.ComponentExportAttr;
import www.tonghao.mall.api.jd.call.AuditCancelApi;
import www.tonghao.mall.api.jd.call.CreateAfsApplyApi;
import www.tonghao.mall.api.jd.call.GetAvailableNumberCompApi;
import www.tonghao.mall.api.jd.call.GetCustomerExpectCompApi;
import www.tonghao.mall.api.jd.call.GetWareReturnJdCompApi;
import www.tonghao.mall.api.jd.call.UpdateSendSkuApi;
import www.tonghao.mall.api.jd.entity.AfsApply;
import www.tonghao.mall.api.jd.entity.AfterSaleCustomerDto;
import www.tonghao.mall.api.jd.entity.AfterSaleDetailDto;
import www.tonghao.mall.api.jd.entity.AfterSalePickwareDto;
import www.tonghao.mall.api.jd.entity.AfterSaleReturnwareDto;
import www.tonghao.mall.api.jd.reqwrap.UpdateSendSkuReq;
import www.tonghao.mall.api.jd.resultwrap.AuditCancelRes;
import www.tonghao.mall.api.jd.resultwrap.CreateAfsApplyRes;
import www.tonghao.mall.api.jd.resultwrap.GetAvailableNumberCompRes;
import www.tonghao.mall.api.jd.resultwrap.GetCustomerExpectCompRes;
import www.tonghao.mall.api.jd.resultwrap.GetWareReturnJdCompRes;
import www.tonghao.mall.api.jd.resultwrap.UpdateSendSkuRes;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.mapper.OrderItemsMapper;
import www.tonghao.mall.mapper.OrdersMapper;
import www.tonghao.mall.service.JdAfterSaleService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.JdAfsWaybill;
import www.tonghao.service.common.entity.JdAfterSale;
import www.tonghao.service.common.entity.JdAfterSaleFile;
import www.tonghao.service.common.entity.MappingArea;
import www.tonghao.service.common.entity.OrderSplitItem;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.JdAfsWaybillMapper;
import www.tonghao.service.common.mapper.JdAfterSaleFileMapper;
import www.tonghao.service.common.mapper.JdAfterSaleMapper;
import www.tonghao.service.common.mapper.OrderSplitItemMapper;
import www.tonghao.service.common.mapper.ProductsMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.service.MappingAreaService;
import www.tonghao.service.common.service.SeqService;

@Service("jdAfterSaleService")
public class JdAfterSaleServiceImpl extends BaseServiceImpl<JdAfterSale> implements JdAfterSaleService{

	@Autowired
	private OrderItemsMapper orderItemsMapper;
	
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Autowired
	private ProductsMapper productsMapper;
	
	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Autowired
	private JdAfterSaleMapper jdAfterSaleMapper;
	
	@Autowired
	private JdAfterSaleFileMapper jdAfterSaleFileMapper;
	
	@Autowired
	private MappingAreaService mappingAreaService;
	
	@Autowired
	private SeqService seqService;
	
	@Autowired
	private JdAfsWaybillMapper jdAfsWaybillMapper;
	
	@Autowired
	private OrderSplitItemMapper orderSplitItemMapper;
	
	@Override
	public Map<String, Object> checkAfterSale(Long orderId, Long productId, Users user) {
		OrderSplitItem orderSplitItem = selectByOrderItemId(orderId, productId, user.getId());
		if(orderSplitItem != null){
			Suppliers supplier = suppliersMapper.selectByPrimaryKey(orderSplitItem.getSupplierId());
			if(supplier != null && supplier.getCode().equals(EmallConstant.MALL_JD_CODE)){
				GetAvailableNumberCompApi getAvailableNumberCompApi = new GetAvailableNumberCompApi(orderSplitItem.getChildEmallSn(), orderSplitItem.getSku());
				GetAvailableNumberCompRes call = getAvailableNumberCompApi.call();
				if(call.isSuccess()){
					//积分支付只能换货
					if(orderSplitItem.getPayId() != null && orderSplitItem.getPayId() == 1){
						//查询商品售后类型
						GetCustomerExpectCompApi getCustomerExpectCompApi = new GetCustomerExpectCompApi(orderSplitItem.getChildEmallSn(), orderSplitItem.getSku());
						GetCustomerExpectCompRes call2 = getCustomerExpectCompApi.call();
						if(call2.isSuccess()){
							List<ComponentExportAttr> result = call2.getResult();
							boolean checkFlag = false;
							for (ComponentExportAttr componentExportAttr : result) {
								String code = componentExportAttr.getCode();
								if("20".equals(code)){
									checkFlag |= true;
								}
							}
							if(checkFlag){
								return ResultUtil.success("");
							}else{
								return ResultUtil.error("该商品不支持售后");
							}
						}else{
							return ResultUtil.error(call2.getResultMessage());
						}
					}else{
						return ResultUtil.success("");
					}
				}else{
					return ResultUtil.error(call.getResultMessage());
				}
			}else{
				return ResultUtil.error("未查询到供应商信息");
			}
		}
		return ResultUtil.error("未查询到订单信息");
	}
	
	@Override
	public Map<String, Object> findAfsType(Long orderId, Long productId, Users user) {
		Map<String, Object> resultMap = new HashMap<>();
		OrderSplitItem orderSplitItem = selectByOrderItemId(orderId, productId, user.getId());
		if(orderSplitItem != null){
			Suppliers supplier = suppliersMapper.selectByPrimaryKey(orderSplitItem.getSupplierId());
			if(supplier != null && supplier.getCode().equals(EmallConstant.MALL_JD_CODE)){
				//查询商品售后类型
				GetCustomerExpectCompApi getCustomerExpectCompApi = new GetCustomerExpectCompApi(orderSplitItem.getChildEmallSn(), orderSplitItem.getSku());
				GetCustomerExpectCompRes call = getCustomerExpectCompApi.call();
				if(call.isSuccess()){
					List<ComponentExportAttr> result = call.getResult();
					//积分支付只能换货
					if(orderSplitItem.getPayId() != null && orderSplitItem.getPayId() == 1){
						List<ComponentExportAttr> resultList = new ArrayList<>();
						for (ComponentExportAttr componentExportAttr : result) {
							String code = componentExportAttr.getCode();
							if("20".equals(code)){
								resultList.add(componentExportAttr);
							}
						}
						resultMap.put("afsTypeList", resultList);
					}else{
						resultMap.put("afsTypeList", result);
					}
				}else{
					return ResultUtil.error(call.getResultMessage());
				}
				//查询商品逆向配送
				GetWareReturnJdCompApi getWareReturnJdCompApi = new GetWareReturnJdCompApi(orderSplitItem.getChildEmallSn(), orderSplitItem.getSku());
				GetWareReturnJdCompRes call2 = getWareReturnJdCompApi.call();
				if(call2.isSuccess()){
					List<ComponentExportAttr> result = call2.getResult();
					resultMap.put("pickwareTypeList", result);
				}else{
					return ResultUtil.error(call2.getResultMessage());
				}
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> createAfsApply(JdAfterSale jdAfterSale, Users user) {
		jdAfterSale.setUserId(user.getId());
		jdAfterSale.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		jdAfterSale.setIsDelete(0);
		jdAfterSale.setStatus(1);
		jdAfterSale.setWaybillStatus(0);
		jdAfterSale.setAllowOperations(0);
		if(jdAfterSale.getOrderId() != null && jdAfterSale.getProductId() != null){
			Long orderId = jdAfterSale.getOrderId();
			Long productId = jdAfterSale.getProductId();
			OrderItems orderItems = orderItemsMapper.selectByOrderAndProduct(orderId, productId);
			OrderSplitItem orderSplitItem = selectByOrderItemId(orderId, productId, user.getId());
			if(orderItems != null && orderSplitItem != null){
				Suppliers supplier = suppliersMapper.selectByPrimaryKey(orderSplitItem.getSupplierId());
				if(supplier != null && supplier.getCode().equals(EmallConstant.MALL_JD_CODE)){
					jdAfterSale.setProductId(orderSplitItem.getProductId());
					jdAfterSale.setProductSku(orderSplitItem.getSku());
					jdAfterSale.setOrderId(orderSplitItem.getChildOrderId());
					jdAfterSale.setOrderSn(orderSplitItem.getOrderSn());
					jdAfterSale.setOrderEmallSn(orderSplitItem.getChildEmallSn());
					jdAfterSale.setOrderItemId(orderItems.getId());
					jdAfterSale.setPayId(orderSplitItem.getPayId());
					jdAfterSale.setProductName(orderItems.getName());
					jdAfterSale.setReturnPrice(orderItems.getPrice());
					jdAfterSale.setSupplierId(supplier.getId());
					jdAfterSale.setSupplierName(supplier.getName());
					jdAfterSale.setSn(seqService.getSeqSn(DateUtilEx.timeToDate(new Date(),"yyyyMMdd")));
					//调用jd售后接口
					Map<String, Object> createjdAfterSale = createjdAfterSale(jdAfterSale);
					if(ResultUtil.isSuccess(createjdAfterSale)){
						int insertSelective = jdAfterSaleMapper.insertSelective(jdAfterSale);
						if(insertSelective > 0){
							List<JdAfterSaleFile> jdAfterSaleFileList = jdAfterSale.getJdAfterSaleFileList();
							if(CollectionUtils.isNotEmpty(jdAfterSaleFileList)){
								jdAfterSaleFileList.forEach(file -> {
									file.setCreatedAt(DateUtilEx.timeFormat(new Date()));
									file.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
									file.setIsDelete(0);
									file.setJdAfterSaleId(jdAfterSale.getId());
									jdAfterSaleFileMapper.insertSelective(file);
								});
							}
						}
					}
					return createjdAfterSale;
				}else{
					return ResultUtil.error("供应商信息错误");
				}
			}
		}
		return ResultUtil.error("提交失败");
		
	}

	/**
	 * 
	 * Description: 提交jd售后
	 * 
	 * @data 2019年7月17日
	 * @param 
	 * @return
	 */
	private Map<String, Object> createjdAfterSale(JdAfterSale jdAfterSale) {
		AfsApply afsApply = new AfsApply();
		afsApply.setJdOrderId(jdAfterSale.getOrderEmallSn());
		afsApply.setCustomerExpect(jdAfterSale.getType()+"");
		if(jdAfterSale.getDescription() != null){
			afsApply.setQuestionDesc(jdAfterSale.getDescription());
		}
		afsApply.setIsNeedDetectionReport(jdAfterSale.getApplicationCredentials() == null ? false : jdAfterSale.getApplicationCredentials()==1);
		List<JdAfterSaleFile> jdAfterSaleFileList = jdAfterSale.getJdAfterSaleFileList();
		if(CollectionUtils.isNotEmpty(jdAfterSaleFileList)){
			StringBuffer questionPic = new StringBuffer();
			jdAfterSaleFileList.forEach(file -> {
				questionPic.append(file.getPath() + ",");
			});
			if(questionPic.length() > 0){
				questionPic.deleteCharAt(questionPic.length() -1);
			}
			afsApply.setQuestionPic(questionPic.toString() == null ? "" : questionPic.toString());
		}else{
			afsApply.setQuestionPic("");
		}
		
		afsApply.setIsHasPackage(jdAfterSale.getIsHasPackage() == null ? false : jdAfterSale.getIsHasPackage()!=0);
		afsApply.setPackageDesc(jdAfterSale.getIsHasPackage()+"");
		//客户信息
		AfterSaleCustomerDto afterSaleCustomerDto = new AfterSaleCustomerDto();
		afterSaleCustomerDto.setCustomerContactName(jdAfterSale.getUserName());
		afterSaleCustomerDto.setCustomerMobilePhone(jdAfterSale.getUserMobile());
		afterSaleCustomerDto.setCustomerTel(jdAfterSale.getUserMobile());
		afsApply.setAsCustomerDto(afterSaleCustomerDto);
		
		//取件信息
		AfterSalePickwareDto afterSalePickwareDto = new AfterSalePickwareDto();
		Integer pickwareType = jdAfterSale.getPickwareType();
		afterSalePickwareDto.setPickwareType(pickwareType+"");
		if(pickwareType != null && pickwareType == 4){
			//上门取件
			MappingArea pickwareMappingArea = mappingAreaService.getEmallMappingArea(jdAfterSale.getPickwareAddressId(), EmallConstant.MALL_JD_CODE);
			if(pickwareMappingArea != null){
				String[] mappingAreas = pickwareMappingArea.getMappingCode().split("_");
				String province = mappingAreas[0];
				String city = mappingAreas[1];
				String county = mappingAreas[2];
				String town = null;
				if(mappingAreas.length==3){
					town = "0";
				}else{
					town = mappingAreas[3];
				}
				afterSalePickwareDto.setPickwareProvince(province);
				afterSalePickwareDto.setPickwareCity(city);
				afterSalePickwareDto.setPickwareCounty(county);
				afterSalePickwareDto.setPickwareVillage(town);
			}else{
				return ResultUtil.error("地址映射错误");
			}
			afterSalePickwareDto.setPickwareAddress(jdAfterSale.getPickwareDetailedAddress());
		}
		afsApply.setAsPickwareDto(afterSalePickwareDto);
		//返件信息
		AfterSaleReturnwareDto afterSaleReturnwareDto = new AfterSaleReturnwareDto();
		afterSaleReturnwareDto.setReturnwareType(jdAfterSale.getReturnwareType()+"");
		MappingArea returnwareMappingArea = mappingAreaService.getEmallMappingArea(jdAfterSale.getReturnwareAddressId(), EmallConstant.MALL_JD_CODE);
		if(returnwareMappingArea != null){
			String[] mappingAreas = returnwareMappingArea.getMappingCode().split("_");
			String province = mappingAreas[0];
			String city = mappingAreas[1];
			String county = mappingAreas[2];
			String town = null;
			if(mappingAreas.length==3){
				town = "0";
			}else{
				town = mappingAreas[3];
			}
			afterSaleReturnwareDto.setReturnwareProvince(province);
			afterSaleReturnwareDto.setReturnwareCity(city);
			afterSaleReturnwareDto.setReturnwareCounty(county);
			afterSaleReturnwareDto.setReturnwareVillage(town);
		}else{
			return ResultUtil.error("地址映射错误");
		}
		afterSaleReturnwareDto.setReturnwareAddress(jdAfterSale.getReturnwareDetailedAddress());
		afsApply.setAsReturnwareDto(afterSaleReturnwareDto);
		//申请单明细
		AfterSaleDetailDto afterSaleDetailDto = new AfterSaleDetailDto();
		afterSaleDetailDto.setSkuId(jdAfterSale.getProductSku());
		afterSaleDetailDto.setSkuNum(jdAfterSale.getProductNum());
		afsApply.setAsDetailDto(afterSaleDetailDto);
		
		//调用接口
		CreateAfsApplyApi createAfsApplyApi = new CreateAfsApplyApi(afsApply);
		CreateAfsApplyRes call = createAfsApplyApi.call();
		if(call.isSuccess()){
			return ResultUtil.success("");
		}else{
			return ResultUtil.error(call.getResultMessage());
		}
	}

	@Override
	public Map<String, Object> cancelAfsApply(Long id) {
		JdAfterSale jdAfterSale = jdAfterSaleMapper.selectByPrimaryKey(id);
		if(jdAfterSale != null && jdAfterSale.getAfsServiceId() != null){
			List<Integer> list = new ArrayList<>();
			list.add(Integer.parseInt(jdAfterSale.getAfsServiceId()));
			AuditCancelApi auditCancelApi = new AuditCancelApi(list, "取消售后");
			AuditCancelRes call = auditCancelApi.call();
			if(call.isSuccess()){
				if(call.isResult()){
					//取消售后申请成功
					JdAfterSale jdAfterSale2 = new JdAfterSale();
					jdAfterSale2.setId(id);
					jdAfterSale2.setStatus(3);
					jdAfterSaleMapper.updateByPrimaryKeySelective(jdAfterSale2);
					return ResultUtil.success("");
				}else{
					return ResultUtil.error(call.getResultMessage());
				}
			}else{
				return ResultUtil.error(call.getResultMessage());
			}
		}
		return ResultUtil.error("未查询到该售后信息");
	}

	@Override
	public JdAfterSale view(Long id) {
		JdAfterSale jdAfterSale = jdAfterSaleMapper.selectByPrimaryKey(id);
		if(jdAfterSale != null){
			Example example = new Example(JdAfterSaleFile.class);
			Criteria critria = example.createCriteria();
			critria.andEqualTo("isDelete", 0);
			critria.andEqualTo("jdAfterSaleId", jdAfterSale.getId());
			List<JdAfterSaleFile> jdAfterSaleFileList = jdAfterSaleFileMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(jdAfterSaleFileList)){
				jdAfterSale.setJdAfterSaleFileList(jdAfterSaleFileList);
			}
		}
		return jdAfterSale;
	}

	@Override
	public List<JdAfterSale> findListByMap(Map<String, Object> map) {
		return jdAfterSaleMapper.findListByMap(map);
	}

	@Override
	public Map<String, Object> addWaybillInfo(JdAfsWaybill jdAfsWaybill, Users user) {
		JdAfterSale jdAfterSale = jdAfterSaleMapper.selectByPrimaryKey(jdAfsWaybill.getJdAfsId());
		if(jdAfterSale != null){
			JdAfsWaybill waybill = new JdAfsWaybill();
			waybill.setJdAfsId(jdAfsWaybill.getJdAfsId());
			List<JdAfsWaybill> list = jdAfsWaybillMapper.select(jdAfsWaybill);
			if(CollectionUtils.isNotEmpty(list)){
				return ResultUtil.error("已经填写过发运单信息");
			}
			jdAfsWaybill.setIsDelete(0);
			jdAfsWaybill.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			jdAfsWaybill.setUserId(user.getId());
			
			//调用发运单信息接口
			UpdateSendSkuReq updateSendSkuReq = new UpdateSendSkuReq();
			updateSendSkuReq.setAfsServiceId(jdAfterSale.getAfsServiceId()+"");
			updateSendSkuReq.setFreightMoney(jdAfsWaybill.getFreightMoney());
			updateSendSkuReq.setExpressCompany(jdAfsWaybill.getExpressCompany());
			updateSendSkuReq.setDeliverDate(jdAfsWaybill.getDeliverDate());
			updateSendSkuReq.setExpressCode(jdAfsWaybill.getExpressCode());
			UpdateSendSkuApi updateSendSkuApi = new UpdateSendSkuApi(updateSendSkuReq);
			UpdateSendSkuRes call = updateSendSkuApi.call();
			if(call.isSuccess()){
				jdAfsWaybill.setStatus(1);
				int insertSelective = jdAfsWaybillMapper.insertSelective(jdAfsWaybill);
				if(insertSelective > 0){
					//更新发运单填写状态
					jdAfterSale.setWaybillStatus(2);
					jdAfterSaleMapper.updateByPrimaryKeySelective(jdAfterSale);
					return ResultUtil.success("提交成功");
				}else{
					return ResultUtil.error("提交失败");
				}
			}else{
				return ResultUtil.error(call.getResultMessage());
			}
		}
		return ResultUtil.error("未查询到售后信息");
	}
	
	@Override
	public JdAfsWaybill waybillView(Long jdAfsId){
		JdAfsWaybill jdAfsWaybill = new JdAfsWaybill();
		jdAfsWaybill.setJdAfsId(jdAfsId);
		List<JdAfsWaybill> list = jdAfsWaybillMapper.select(jdAfsWaybill);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0); 
		}
		return null;
	}

	@Override
	public List<JdAfterSale> findH5ListByMap(Map<String, Object> map) {
		return jdAfterSaleMapper.findH5ListByMap(map);
	}
	
	@Override
	public OrderSplitItem selectByOrderItemId(Long orderId, Long productId, Long userId) {
		OrderSplitItem orderSplitItem = null;
		if (orderId != null &&productId != null) {
			Orders orders = ordersMapper.selectByPrimaryKey(orderId);
			Products products = productsMapper.selectByPrimaryKey(productId);
			if(orders != null && products != null && orders.getUserId().equals(userId)){
				//查询是否拆单
				Example orderExample = new Example(Orders.class);
				Criteria orderCriteria = orderExample.createCriteria();
				orderCriteria.andEqualTo("parentId", orders.getId());
				orderCriteria.andEqualTo("isDelete", 0);
				List<Orders> selectByExample = ordersMapper.selectByExample(orderExample);
				if(CollectionUtils.isNotEmpty(selectByExample)){
					//拆单
					Example orderSplitItemExample = new Example(OrderSplitItem.class);
					Criteria orderSplitItemCriteria = orderSplitItemExample.createCriteria();
					orderSplitItemCriteria.andEqualTo("orderSn", orders.getSn());
					orderSplitItemCriteria.andEqualTo("orderEmallSn", orders.getEmallSn());
					orderSplitItemCriteria.andEqualTo("sku", products.getSku());
					orderSplitItemCriteria.andEqualTo("supplierId", orders.getSupplierId());
					orderSplitItemCriteria.andEqualTo("productId", products.getId());
					orderSplitItemCriteria.andEqualTo("isDelete", 0);
					List<OrderSplitItem> selectByExample2 = orderSplitItemMapper.selectByExample(orderSplitItemExample);
					if(CollectionUtils.isNotEmpty(selectByExample2)){
						orderSplitItem = selectByExample2.get(0);
						orderSplitItem.setPayId(orders.getPayId());
					}
				}else{
					//未拆单
					orderSplitItem = new OrderSplitItem();
					orderSplitItem.setSku(products.getSku());
					orderSplitItem.setProductId(products.getId());
					orderSplitItem.setChildEmallSn(orders.getEmallSn());
					orderSplitItem.setSupplierId(orders.getSupplierId());
					orderSplitItem.setPayId(orders.getPayId());
					orderSplitItem.setChildOrderId(orders.getId());
					orderSplitItem.setOrderSn(orders.getSn());
				}
			} 
		}
		return orderSplitItem;
	}
}
