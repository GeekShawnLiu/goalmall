package www.tonghao.mall.job.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.mall.api.jd.attwrap.CompatibleServiceDetailDTO;
import www.tonghao.mall.api.jd.attwrap.MessageAfterService;
import www.tonghao.mall.api.jd.attwrap.MessageAttr;
import www.tonghao.mall.api.jd.attwrap.ServiceAftersalesAddressInfoDTO;
import www.tonghao.mall.api.jd.resultwrap.GetServiceDetailInfoRes;
import www.tonghao.mall.api.jd.resultwrap.MessageDelRes;
import www.tonghao.mall.api.jd.resultwrap.MessageRes;
import www.tonghao.mall.job.service.AfterSaleMessageJdService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.service.common.entity.JdAfterSale;
import www.tonghao.service.common.entity.SkuLogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.JdAfterSaleMapper;
import www.tonghao.service.common.mapper.SkuLogsMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.service.OrderRefundItemService;

@Service("afterSaleMessageJdService")
public class AfterSaleMessageJdServiceImpl implements AfterSaleMessageJdService{

	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Autowired
	private JdAfterSaleMapper jdAfterSaleMapper;
	
	@Autowired
	private SkuLogsMapper skuLogsMapper;
	
	@Autowired
	private OrderRefundItemService orderRefundItemService;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void executeAfterSaleMessageJdJob() {
		Suppliers s = new Suppliers();
		s.setCode(EmallConstant.MALL_JD_CODE);
		Suppliers supplier = suppliersMapper.selectOne(s);
		if(supplier != null){
			MessageRes messageRes = JdUtilApi.MessageApis(28);
			if(messageRes.isSuccess()){
				List<MessageAttr> result = messageRes.getResult();
				if(!CollectionUtil.collectionIsEmpty(result)){
					//按时间增序排列
					result.sort(new Comparator<MessageAttr>() {

						@Override
						public int compare(MessageAttr o1,MessageAttr o2) {
							// TODO Auto-generated method stub
							return o1.getTime().compareTo(o2.getTime());
						}
					});
					
					for (MessageAttr messageAttr : result) {
						MessageAfterService messageAfterService = (MessageAfterService)messageAttr.getMessage();
						String afsServiceId = messageAfterService.getAfsServiceId();
						String orderId = messageAfterService.getOrderId();
						String skuId = messageAfterService.getSkuId();
						addSkuLogs(supplier.getCode(), messageAttr.getId(), orderId, messageAttr.getTime(), 11);
						int state = messageAfterService.getState();
						Map<String, Object> map = new HashMap<>();
						map.put("orderEmallSn", orderId);
						map.put("productSku", skuId);
						map.put("afsServiceId", afsServiceId);
						List<JdAfterSale> jdAfterSaleList = jdAfterSaleMapper.selectByAfsServiceId(map);
						if(CollectionUtils.isNotEmpty(jdAfterSaleList)){
							//该服务单存在   更新服务单状态
							JdAfterSale jdAfterSale = jdAfterSaleList.get(0);
							jdAfterSale.setStatus(state);
							jdAfterSale.setAfsServiceId(afsServiceId);
							int update = jdAfterSaleMapper.updateByPrimaryKeySelective(jdAfterSale);
							if(update > 0){
								MessageDelRes messageDelApis = JdUtilApi.MessageDelApis(messageAttr.getId());
								if(messageDelApis.isSuccess()){
									delSkuLogs(messageAttr.getId(), supplier.getCode());
								}
							}
							//调用明细查询接口 判断是否允许取消  或者 是否需要填写发运地址
							findServiceDetailInfo(jdAfterSale.getId(), afsServiceId);
							if(state == 4 && jdAfterSale.getType() != null && jdAfterSale.getType() == 10){
								//如果售后完成售后类型为退货   需要退款
								orderRefundItemService.afterSaleRefund(jdAfterSale.getId(), "1");
							}
						}else{
							//该服务单不存在   保存服务单id
							Map<String, Object> newMap = new HashMap<>();
							newMap.put("orderEmallSn", orderId);
							newMap.put("productSku", skuId);
							newMap.put("isNull", "isNull");
							List<JdAfterSale> newJdAfterSaleList = jdAfterSaleMapper.selectByAfsServiceId(newMap);
							if(CollectionUtils.isNotEmpty(newJdAfterSaleList)){
								JdAfterSale jdAfterSale = newJdAfterSaleList.get(0);
								jdAfterSale.setStatus(state);
								jdAfterSale.setAfsServiceId(afsServiceId);
								int update = jdAfterSaleMapper.updateByPrimaryKeySelective(jdAfterSale);
								if(update > 0){
									MessageDelRes messageDelApis = JdUtilApi.MessageDelApis(messageAttr.getId());
									if(messageDelApis.isSuccess()){
										delSkuLogs(messageAttr.getId(), supplier.getCode());
									}
								}
								//调用明细查询接口 判断是否允许取消  或者 是否需要填写发运地址
								findServiceDetailInfo(jdAfterSale.getId(), afsServiceId);
								if(state == 4 && jdAfterSale.getType() != null && jdAfterSale.getType() == 10){
									//如果售后完成售后类型为退货   需要退款
									orderRefundItemService.afterSaleRefund(jdAfterSale.getId(), "1");
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * Description: 调用明细查询接口 更新服务单操作内容
	 * 
	 * @data 2019年7月25日
	 * @param 
	 * @return
	 */
	public void findServiceDetailInfo(Long afsId, String afsServiceId){
		JdAfterSale jdAfterSale = jdAfterSaleMapper.selectByPrimaryKey(afsId);
		if(jdAfterSale != null){
			List<Integer> list = new ArrayList<Integer>();
			list.add(1);
			list.add(2);
			list.add(5);
			GetServiceDetailInfoRes selectServiceDetailInfo = JdUtilApi.selectServiceDetailInfo(afsServiceId, list);
			if(selectServiceDetailInfo.isSuccess()){
				CompatibleServiceDetailDTO compatibleServiceDetailDTO = selectServiceDetailInfo.getResult();
				List<Integer> allowOperations = compatibleServiceDetailDTO.getAllowOperations();
				if(CollectionUtil.collectionIsEmpty(allowOperations)){
					//不允许操作
					jdAfterSale.setAllowOperations(0);
				}else{
					if(allowOperations.contains(1)){
						jdAfterSale.setAllowOperations(1);
					}
					if(allowOperations.contains(2)){
						jdAfterSale.setAllowOperations(2);
					}
					if(allowOperations.contains(1) && allowOperations.contains(2)){
						jdAfterSale.setAllowOperations(3);
					}
				}
				ServiceAftersalesAddressInfoDTO serviceAftersalesAddressInfoDTO = compatibleServiceDetailDTO.getServiceAftersalesAddressInfoDTO();
				if(serviceAftersalesAddressInfoDTO != null){
					//需要填写发运信息
					if(jdAfterSale.getWaybillStatus() != null && jdAfterSale.getWaybillStatus() != 2){
						jdAfterSale.setWaybillStatus(1);
					}
					jdAfterSale.setAddress(serviceAftersalesAddressInfoDTO.getAddress());
					jdAfterSale.setTel(serviceAftersalesAddressInfoDTO.getTel());
					jdAfterSale.setLinkMan(serviceAftersalesAddressInfoDTO.getLinkMan());
					jdAfterSale.setPostCode(serviceAftersalesAddressInfoDTO.getPostCode());
				}
				jdAfterSaleMapper.updateByPrimaryKeySelective(jdAfterSale);
			}
		}
	}
	
	// 1 添加，2删除，3修改，4上架，5下架,6价格,7主图,8 订单拆分消息，9妥投订单消息，10取消订单消息， 11售后
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
