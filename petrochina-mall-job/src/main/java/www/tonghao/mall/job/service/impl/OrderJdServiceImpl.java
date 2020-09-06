package www.tonghao.mall.job.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.constant.EmallConstant;
import www.tonghao.common.entity.OrderTrack;
import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.common.utils.StringUtil;
import www.tonghao.mall.api.jd.attwrap.ChildrenOrderRepVO;
import www.tonghao.mall.api.jd.attwrap.MessageAttr;
import www.tonghao.mall.api.jd.attwrap.MessageOrder;
import www.tonghao.mall.api.jd.attwrap.MessageOrderFinished;
import www.tonghao.mall.api.jd.attwrap.MessagePOrder;
import www.tonghao.mall.api.jd.attwrap.OrderSelectAttr;
import www.tonghao.mall.api.jd.attwrap.OrderSkuVO;
import www.tonghao.mall.api.jd.attwrap.Track;
import www.tonghao.mall.api.jd.attwrap.TrackAttr;
import www.tonghao.mall.api.jd.resultwrap.MessageDelRes;
import www.tonghao.mall.api.jd.resultwrap.MessageRes;
import www.tonghao.mall.api.jd.resultwrap.OrderSelectRes;
import www.tonghao.mall.api.jd.resultwrap.TrackRes;
import www.tonghao.mall.job.service.JobCommonService;
import www.tonghao.mall.job.service.OrderJdService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.service.common.entity.JobOrders;
import www.tonghao.service.common.entity.OrderSplitItem;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.SkuLogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.JobOrderItemsMapper;
import www.tonghao.service.common.mapper.JobOrdersMapper;
import www.tonghao.service.common.mapper.SkuLogsMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.service.IntegralUserService;
import www.tonghao.service.common.service.OrderSplitItemService;
import www.tonghao.service.common.service.ProductsService;

@Service("orderJdService")
public class OrderJdServiceImpl implements OrderJdService {

	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Autowired
	private SkuLogsMapper skuLogsMapper;
	
	@Autowired
	private JobOrdersMapper jobOrdersMapper;
	
	@Autowired
	private JobOrderItemsMapper jobOrderItemsMapper;
	
	@Autowired
	private IntegralUserService integralUserService;
	
	@Autowired
	private JobCommonService jobCommonService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private OrderSplitItemService orderSplitItemService;

	@Override
	public void executeOrderJdJob() {
		Suppliers s = new Suppliers();
		s.setCode(EmallConstant.MALL_JD_CODE);
		Suppliers supplier = suppliersMapper.selectOne(s);
		if(supplier != null){
			splitOrder(supplier); //拆单消息
			completedOrder(supplier);//妥投消息
			cancelledOrder(supplier);//取消消息
		}
	}

	/**
	 * 
	 * Description: 取消消息
	 * 
	 * @data 2019年7月19日
	 * @param 
	 * @return
	 */
	private void cancelledOrder(Suppliers supplier) {
		MessageRes messageRes = JdUtilApi.MessageApis(10);
		if(messageRes.isSuccess()){
			List<MessageAttr> result = messageRes.getResult();
			if(!CollectionUtil.collectionIsEmpty(result)){
				for (MessageAttr messageAttr : result) {
					MessageOrder messageOrder = (MessageOrder)messageAttr.getMessage();
					String orderId = messageOrder.getOrderId();
					addSkuLogs(supplier.getCode(), messageAttr.getId(), orderId, messageAttr.getTime(), 10);
					JobOrders jobOrder = getOrder(orderId, supplier);
					if(jobOrder != null){
						jobOrder.setOrdersState(OrderStatus.cancelled);
						int update = jobOrdersMapper.updateByPrimaryKeySelective(jobOrder);
						if(update > 0){
							if(jobOrder.getParentId() != null){
								//子订单
								Example example=new Example(JobOrders.class);
								Criteria createCriteria = example.createCriteria();
								createCriteria.andEqualTo("supplierId", supplier.getId());
								createCriteria.andEqualTo("isDelete", 0);
								createCriteria.andEqualTo("parentId", jobOrder.getParentId());
								List<JobOrders> jobOrdersList = jobOrdersMapper.selectByExample(example);
								//查询所有子订单
								if(!CollectionUtil.collectionIsEmpty(jobOrdersList)){
									boolean cancellFlag = true;
									for (JobOrders jobOrders : jobOrdersList) {
										if(!jobOrders.getOrdersState().equals(OrderStatus.cancelled)){
											cancellFlag &= false;
										}
									}
									if(cancellFlag){
										//子订单全部取消
										JobOrders parentJobOrders = jobOrdersMapper.selectByPrimaryKey(jobOrder.getParentId());
										if(parentJobOrders != null && !parentJobOrders.getOrdersState().equals(OrderStatus.cancelled)) {
											//jobCommonService.refund(parentJobOrders);
											parentJobOrders.setOrdersState(OrderStatus.cancelled);
											jobOrdersMapper.updateByPrimaryKeySelective(parentJobOrders);
										}
									}
								}
							}
							MessageDelRes messageDelApis = JdUtilApi.MessageDelApis(messageAttr.getId());
							if(messageDelApis.isSuccess()){
								delSkuLogs(messageAttr.getId(), supplier.getCode());
							}
						}
					}else{
						JobOrders jobOrders = new JobOrders();
						jobOrders.setSupplierId(supplier.getId());
						jobOrders.setIsDelete(1);
						jobOrders.setEmallSn(orderId);
						JobOrders jo = jobOrdersMapper.selectOne(jobOrders);
						if(jo!=null){
							MessageDelRes messageDelApis = JdUtilApi.MessageDelApis(messageAttr.getId());
							if(messageDelApis.isSuccess()){
								delSkuLogs(messageAttr.getId(), supplier.getCode());
							}
						}
					}
				}
			}
		}
		
		
	}

	/**
	 * 
	 * Description: 妥头消息
	 * 
	 * @data 2019年7月19日
	 * @param 
	 * @return
	 */
	private void completedOrder(Suppliers supplier) {
		MessageRes messageRes = JdUtilApi.MessageApis(5);//妥头消息
		if(messageRes.isSuccess()){
			List<MessageAttr> result = messageRes.getResult();
			if(!CollectionUtil.collectionIsEmpty(result)){
				for (MessageAttr messageAttr : result) {
					MessageOrderFinished messageOrder = (MessageOrderFinished)messageAttr.getMessage();
					String orderId = messageOrder.getOrderId();
					addSkuLogs(supplier.getCode(), messageAttr.getId(), orderId, messageAttr.getTime(), 10);
					JobOrders jobOrder = getOrder(orderId, supplier);
					if(jobOrder != null){
						int update = 0;
						String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						if(messageOrder.getState() == 1){
							//妥头
							jobOrder.setOrdersState(OrderStatus.completed);
							TrackRes selectTrack = JdUtilApi.selectTrack(orderId);
							if(selectTrack.isSuccess()){
								TrackAttr result2 = selectTrack.getResult();
								List<Track> trackList = result2.getOrderTrack();
								if (!CollectionUtils.isEmpty(trackList)) {
									List<OrderTrack> OrderTrackList = new ArrayList<OrderTrack>();
									for (Track track : trackList) {
										OrderTrack e = new OrderTrack();
										e.setContent(track.getContent());
										e.setOperateTime(track.getMsgTime());
										e.setOperator(StringUtil.strIsEmpty(track.getOperator()) ? "" : track.getOperator());
										OrderTrackList.add(e);
									}
									jobOrder.setTrack(JsonUtil.toJson(OrderTrackList));
								}
							}
							jobOrder.setCompletedAt(date);
							update = jobOrdersMapper.updateByPrimaryKeySelective(jobOrder);
						}else if(messageOrder.getState() == 2){
							//拒收
							jobOrder.setCompletedAt(date);
							jobOrder.setOrdersState(OrderStatus.cancelled);
							update = jobOrdersMapper.updateByPrimaryKeySelective(jobOrder);
						}
						if(update > 0){
							if(jobOrder.getParentId() != null){
								//子订单
								Example example=new Example(JobOrders.class);
								Criteria createCriteria = example.createCriteria();
								createCriteria.andEqualTo("supplierId", supplier.getId());
								createCriteria.andEqualTo("isDelete", 0);
								createCriteria.andEqualTo("parentId", jobOrder.getParentId());
								List<JobOrders> jobOrdersList = jobOrdersMapper.selectByExample(example);
								//查询所有子订单
								if(!CollectionUtil.collectionIsEmpty(jobOrdersList)){
									boolean orderFlag = true;
									for (JobOrders jobOrders : jobOrdersList) {
										if(jobOrders.getOrdersState().equals(OrderStatus.confirmed) || jobOrders.getOrdersState().equals(OrderStatus.commit)){
											orderFlag &= false;
										}
									}
									if(orderFlag){
										//子订单全部完成
										JobOrders j = jobOrdersMapper.selectByPrimaryKey(jobOrder.getParentId());
										if(j!=null) {
											j.setCompletedAt(date);
											j.setOrdersState(OrderStatus.completed);
											jobOrdersMapper.updateByPrimaryKeySelective(j);
										}
									}
								}
							}
							MessageDelRes messageDelApis = JdUtilApi.MessageDelApis(messageAttr.getId());
							if(messageDelApis.isSuccess()){
								delSkuLogs(messageAttr.getId(), supplier.getCode());
							}
						}
					}
				}
			}
		}
		
	}

	/**
	 * 
	 * Description: 拆单消息
	 * 
	 * @data 2019年7月19日
	 * @param 
	 * @return
	 */
	private void splitOrder(Suppliers supplier) {
		MessageRes messageRes = JdUtilApi.MessageApis(1);//拆单消息
		if(messageRes.isSuccess()){
			List<MessageAttr> result = messageRes.getResult();
			if(!CollectionUtil.collectionIsEmpty(result)){
				for (MessageAttr messageAttr : result) {
					MessagePOrder messagePOrder = (MessagePOrder)messageAttr.getMessage();
					String porderId = messagePOrder.getpOrder();
					addSkuLogs(supplier.getCode(), messageAttr.getId(), porderId, messageAttr.getTime(), 8);
					int c_order = c_order(porderId, supplier);
					if(c_order>0) {
						MessageDelRes messageDelApis = JdUtilApi.MessageDelApis(messageAttr.getId());
						if(messageDelApis.isSuccess()){
							delSkuLogs(messageAttr.getId(), supplier.getCode());
						}
					}
				}
			}
		}
	}
	
	public int c_order(String porderId, Suppliers supplier) {
		JobOrders jo = getOrder(porderId, supplier);
		int result=0;
		if(jo!=null) {
			try {
				OrderSelectRes selectOrder = JdUtilApi.selectOrder(porderId);
				if(selectOrder.isSuccess()){
					OrderSelectAttr attr = selectOrder.getResult();
					List<ChildrenOrderRepVO> getcOrder = attr.getcOrder();
					if(!CollectionUtil.collectionIsEmpty(getcOrder)){
						for (ChildrenOrderRepVO order : getcOrder) {
							String jdOrderId = order.getJdOrderId();
							if(StringUtils.isNotEmpty(jdOrderId)){
								JobOrders newOrder = getOrder(jdOrderId, supplier);
								if(newOrder == null){
									JobOrders jobOrder=new JobOrders();
									jobOrder.setParentId(jo.getId());
									jobOrder.setEmallSn(jdOrderId);
									jobOrder.setSupplierId(supplier.getId());
									jobOrder.setSupplierName(jo.getSupplierName());
									jobOrder.setCreatedAt(DateUtilEx.timeToDate(new Date(), DateUtilEx.TIME_PATTERN));
									jobOrder.setUpdatedAt(DateUtilEx.timeToDate(new Date(), DateUtilEx.TIME_PATTERN));
									jobOrder.setIsDelete(0);
									if(order.getOrderState() == 0){
										//取消
										jobOrder.setOrdersState(OrderStatus.cancelled);
									}else{
										//有效订单
										//物流状态 0 是新建  1 是妥投   2 是拒收 
										if(order.getState() == 0){
											jobOrder.setOrdersState(OrderStatus.confirmed);
										}else if(order.getState() == 1){
											jobOrder.setOrdersState(OrderStatus.completed);
										}else if(order.getState() == 2){
											jobOrder.setOrdersState(OrderStatus.cancelled);
										}
									}
									result += jobOrdersMapper.insertSelective(jobOrder);
									
									List<OrderSkuVO> sku = order.getSku();
									if(!CollectionUtil.collectionIsEmpty(sku)){
										for (OrderSkuVO orderSkuVO : sku) {
											Example example=new Example(Products.class);
											Criteria createCriteria = example.createCriteria();
											createCriteria.andEqualTo("sku", orderSkuVO.getSkuId());
											createCriteria.andEqualTo("supplierId", supplier.getId());
											createCriteria.andEqualTo("isDelete", 0);
											List<Products> ps_list = productsService.selectByExample(example);
										    if(!CollectionUtil.collectionIsEmpty(ps_list)){
										    	Products products = ps_list.get(0);
										    	OrderSplitItem item=new OrderSplitItem();
										    	item.setChildOrderId(jobOrder.getId());
										    	item.setOrderSn(jo.getSn());
										    	item.setOrderEmallSn(jo.getEmallSn());
										    	item.setChildEmallSn(jobOrder.getEmallSn());
										    	item.setSku(orderSkuVO.getSkuId());
										    	item.setSupplierId(supplier.getId());
										    	item.setSupplierName(supplier.getName());
										    	item.setProductId(products.getId());
										    	item.setNum(orderSkuVO.getNum());
										    	item.setIsDelete(0);
										    	orderSplitItemService.saveSelective(item);
										    }
										}
									}
								}else{
									result += 1;
								}
							}
						}
					}else{
						return 1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private JobOrders getOrder(String porderId, Suppliers supplier) {
		JobOrders jobOrders = new JobOrders();
		jobOrders.setSupplierId(supplier.getId());
		jobOrders.setIsDelete(0);
		jobOrders.setEmallSn(porderId);
		JobOrders jo = jobOrdersMapper.selectOne(jobOrders);
		return jo;
	}
	
	// 1 添加，2删除，3修改，4上架，5下架,6价格,7主图,8 订单拆分消息，9妥投订单消息，10取消订单消息
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
