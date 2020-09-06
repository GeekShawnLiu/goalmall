package www.tonghao.mall.job.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.entity.OrderTrack;
import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.mall.api.stb.attwrap.Message;
import www.tonghao.mall.api.stb.attwrap.MessageAttr;
import www.tonghao.mall.api.stb.attwrap.MessageOrder;
import www.tonghao.mall.api.stb.attwrap.Order;
import www.tonghao.mall.api.stb.attwrap.SelectOrderAttr;
import www.tonghao.mall.api.stb.attwrap.Track;
import www.tonghao.mall.api.stb.resultwrap.MessageDelRes;
import www.tonghao.mall.api.stb.resultwrap.MessageRes;
import www.tonghao.mall.api.stb.resultwrap.OrderTrackRes;
import www.tonghao.mall.api.stb.resultwrap.SelectOrderRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.JobCommonService;
import www.tonghao.mall.job.service.OrderStbService;
import www.tonghao.mall.job.util.StbUtilApi;
import www.tonghao.service.common.entity.JobOrderItems;
import www.tonghao.service.common.entity.JobOrders;
import www.tonghao.service.common.entity.SkuLogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.JobOrderItemsMapper;
import www.tonghao.service.common.mapper.JobOrdersMapper;
import www.tonghao.service.common.mapper.SkuLogsMapper;
import www.tonghao.service.common.service.IntegralUserService;
import www.tonghao.service.common.service.SuppliersService;

@Service("orderStbService")
public class OrderStbServiceImpl implements OrderStbService {
	@Autowired
	private SuppliersService suppliersService;
	@Autowired
	private JobOrdersMapper jobOrdersMapper;
	@Autowired
	private SkuLogsMapper skuLogsMapper;
	@Autowired
	private IntegralUserService integralUserService;
	@Autowired
	private JobOrderItemsMapper jobOrderItemsMapper;
	@Autowired
	private JobCommonService jobCommonService;
	
	@Override
	public void executeOrderStbJob() {
		Suppliers sup = new Suppliers();
		sup.setCode(Constant.MALL_STB_CODE);
		Suppliers supplier = suppliersService.selectEntityOne(sup);
		if(supplier!=null) {
			splitOrder(supplier); //拆单消息
			completedOrder(supplier);//妥投消息
			cancelledOrder(supplier);//取消消息
		}
	}
	private void cancelledOrder(Suppliers supplier) {
		MessageRes message = StbUtilApi.getMessage(13); //取消消息
		if(message.isSuccess()) {
			List<MessageAttr> attr = message.getAttr();
			if(!CollectionUtil.collectionIsEmpty(attr)) {
				for (MessageAttr messageAttr : attr) {
					MessageOrder message2 = (MessageOrder)messageAttr.getMessage();
					String orderId = message2.getOrderId();//订单id
					addSkuLogs(supplier.getCode(), messageAttr.getId(), orderId, messageAttr.getTime(), 10);
					JobOrders jo = getOrder(orderId, supplier);
					if(jo!=null) {
						jo.setOrdersState(OrderStatus.cancelled);
						int update = jobOrdersMapper.updateByPrimaryKeySelective(jo);
						if(update>0) {
							if(jo.getParentId()!=null) {//子订单
								Example example=new Example(JobOrders.class);
								Criteria createCriteria = example.createCriteria();
								createCriteria.andEqualTo("supplierId", supplier.getId());
								createCriteria.andEqualTo("isDelete", 0);
								createCriteria.andEqualTo("parentId", jo.getParentId());
								List<JobOrders> jorders = jobOrdersMapper.selectByExample(example);
								if(!CollectionUtil.collectionIsEmpty(jorders)) {//查询所有子订单
									boolean bool=false;
									oyt:
									for (JobOrders jobOrders2 : jorders) {//判断是否所有的订单都取消了
										if(!jobOrders2.getOrdersState().equals(OrderStatus.cancelled)) {
											bool=true;
											break oyt;
										}
									}
									if(!bool) {//如果都取消了  修改父订单状态
										JobOrders joborders = jobOrdersMapper.selectByPrimaryKey(jo.getParentId());
										if(joborders!=null&&!joborders.getOrdersState().equals(OrderStatus.cancelled)) {
											//jobCommonService.refund(joborders);
											joborders.setOrdersState(OrderStatus.cancelled);
											jobOrdersMapper.updateByPrimaryKeySelective(joborders);
										}
									}
									
								}
							}
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							if(delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
						}
					}
					
					
				}
			}
		}
	}
	private void completedOrder(Suppliers supplier) {
		MessageRes message = StbUtilApi.getMessage(5); //妥投消息
		if(message.isSuccess()) {
			List<MessageAttr> attr = message.getAttr();
			if(!CollectionUtil.collectionIsEmpty(attr)) {
				for (MessageAttr messageAttr : attr) {
					MessageOrder message2 = (MessageOrder)messageAttr.getMessage();
					String orderId = message2.getOrderId();//订单id
					addSkuLogs(supplier.getCode(), messageAttr.getId(), orderId, messageAttr.getTime(), 9);
					JobOrders jo = getOrder(orderId, supplier);
					if(jo!=null) {
						int update=0;
						String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						if(message2.getState()==1) {//1是妥投，2是拒收
							jo.setOrdersState(OrderStatus.completed);
							OrderTrackRes selectTrack = StbUtilApi.selectTrack(orderId);
							if(selectTrack.isSuccess()&&selectTrack.getOrderTrackAttr()!=null) {
								List<Track> trackList = selectTrack.getOrderTrackAttr().getTrackList();
								if(!CollectionUtil.collectionIsEmpty(trackList)) {
									List<OrderTrack> list=new ArrayList<>();
									for (Track track : trackList) {
										OrderTrack orderTrack=new OrderTrack();
										orderTrack.setContent(track.getContent());
										orderTrack.setOperator(track.getOperator());
										orderTrack.setOperateTime(track.getMsgTime());
										list.add(orderTrack);
									}
									jo.setTrack(JsonUtil.toJson(list));
								}
							}
							jo.setCompletedAt(date);
							update = jobOrdersMapper.updateByPrimaryKeySelective(jo);
						}else if(message2.getState()==2){
							jo.setCompletedAt(date);
							jo.setOrdersState(OrderStatus.cancelled);
							update = jobOrdersMapper.updateByPrimaryKeySelective(jo);
						}
						if(update>0) {
							if(jo.getParentId()!=null) {//子订单
								Example example=new Example(JobOrders.class);
								Criteria createCriteria = example.createCriteria();
								createCriteria.andEqualTo("supplierId", supplier.getId());
								createCriteria.andEqualTo("isDelete", 0);
								createCriteria.andEqualTo("parentId", jo.getParentId());
								List<JobOrders> jorders = jobOrdersMapper.selectByExample(example);
								if(!CollectionUtil.collectionIsEmpty(jorders)) {//查询所有的子订单
									boolean bool=false;
									oyt:
									for (JobOrders jobOrders2 : jorders) {//判断是否有没有完成的订单
										if(jobOrders2.getOrdersState().equals(OrderStatus.confirmed)||jobOrders2.getOrdersState().equals(OrderStatus.commit)) {
											bool=true;
											break oyt;
										}
									}
									if(!bool) {//如果都完成了  修改父订单状态
										JobOrders joborders = jobOrdersMapper.selectByPrimaryKey(jo.getParentId());
										if(joborders!=null) {
											joborders.setOrdersState(OrderStatus.completed);
											jobOrdersMapper.updateByPrimaryKeySelective(joborders);
										}
									}
									
								}
							}
							MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
							if(delMessage.isSuccess()) {
								delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
							}
							
						}
					}
					
				}
			}
		}
		
		
	}
	private void splitOrder(Suppliers supplier) {
		MessageRes message = StbUtilApi.getMessage(1); //拆单
		if(message.isSuccess()) {
			List<MessageAttr> attr = message.getAttr();
			if(!CollectionUtil.collectionIsEmpty(attr)) {
				for (MessageAttr messageAttr : attr) {
					MessageOrder message2 = (MessageOrder)messageAttr.getMessage();
					String porderId = message2.getPorderId();//父订单id
					addSkuLogs(supplier.getCode(), messageAttr.getId(), porderId, messageAttr.getTime(), 8);
					int c_order = c_order(porderId,supplier);
					if(c_order>0) {
						MessageDelRes delMessage = StbUtilApi.delMessage(messageAttr.getId());// 刪除消息
						if(delMessage.isSuccess()) {
							delSkuLogs(messageAttr.getId(), supplier.getCode());// 删除本地消息
						}
						
					}
				}
			}
		}
	}

	public int c_order(String porderId,Suppliers supplier) {
		JobOrders jo = getOrder(porderId, supplier);
		int result=0;
		if(jo!=null) {
			try {
				SelectOrderRes selectOrder = StbUtilApi.selectOrder(porderId, 1);
				if(selectOrder.isSuccess()) {
					SelectOrderAttr attr = selectOrder.getAttr();
					List<Order> getcOrder = attr.getcOrder();
					if(!CollectionUtil.collectionIsEmpty(getcOrder)) {
						for (Order order : getcOrder) {
							String supplierOrderId = order.getSupplierOrderId();
							if(!StringUtils.isEmpty(supplierOrderId)) {
								JobOrders newOrder = getOrder(supplierOrderId, supplier);
								if(newOrder==null) {
									JobOrders jobOrder=new JobOrders();
									jobOrder.setParentId(jo.getId());
									jobOrder.setEmallSn(supplierOrderId);
									jobOrder.setSupplierId(supplier.getId());
									jobOrder.setSupplierName(jo.getSupplierName());
									jobOrder.setCreatedAt(DateUtilEx.timeToDate(new Date(), DateUtilEx.TIME_PATTERN));
									jobOrder.setUpdatedAt(DateUtilEx.timeToDate(new Date(), DateUtilEx.TIME_PATTERN));
									jobOrder.setIsDelete(0);
									//物流状态（ 0：新建  1：妥投   2：拒收  3:退货 4：换货 5：未收到货）,
									if(order.getState().equals(0)||order.getState().equals(5)) {
										jobOrder.setOrdersState(OrderStatus.confirmed);
									}else if(order.getState().equals(1)) {
										jobOrder.setOrdersState(OrderStatus.completed);
									}else if(order.getState().equals(2)||order.getState().equals(3)||order.getState().equals(4)) {
										jobOrder.setOrdersState(OrderStatus.cancelled);
									}
									result+=jobOrdersMapper.insertSelective(jobOrder);
								}else {
									result+=1;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private JobOrders getOrder(String porderId, Suppliers supplier) {
		JobOrders jobOrders=new JobOrders();
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
