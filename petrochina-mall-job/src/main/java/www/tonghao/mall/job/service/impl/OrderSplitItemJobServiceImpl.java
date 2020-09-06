package www.tonghao.mall.job.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.mall.api.jd.attwrap.OrderSelectAttr;
import www.tonghao.mall.api.jd.attwrap.OrderSkuVO;
import www.tonghao.mall.api.jd.attwrap.ParentOrderRepVO;
import www.tonghao.mall.api.jd.resultwrap.OrderSelectRes;
import www.tonghao.mall.job.service.OrderSplitItemJobService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.service.common.entity.JobOrderItems;
import www.tonghao.service.common.entity.JobOrders;
import www.tonghao.service.common.entity.OrderSplitItem;
import www.tonghao.service.common.mapper.JobOrderItemsMapper;
import www.tonghao.service.common.mapper.JobOrdersMapper;
import www.tonghao.service.common.mapper.OrderSplitItemMapper;
@Service("orderSplitItemJobService")
public class OrderSplitItemJobServiceImpl implements OrderSplitItemJobService {

	
	@Autowired
	private JobOrdersMapper jobOrdersMapper;
	
	@Autowired
	private JobOrderItemsMapper jobOrderItemsMapper;
	
	@Autowired
	private OrderSplitItemMapper orderSplitItemMapper;
	
	@Override
	public void orderSplitItemJob() {
		Example example=new Example(JobOrders.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andIsNotNull("parentId");
		List<JobOrders> jobOrdersList = jobOrdersMapper.selectByExample(example);
		for (JobOrders jobOrders : jobOrdersList) {
			JobOrders jobOrders_parent = jobOrdersMapper.selectByPrimaryKey(jobOrders.getParentId());
			OrderSelectRes selectOrder = JdUtilApi.selectOrderChildren(jobOrders.getEmallSn());
			if(selectOrder.isSuccess()){
				OrderSelectAttr result = selectOrder.getResult();
				ParentOrderRepVO getpOrder = result.getpOrder();
				List<OrderSkuVO> sku = getpOrder.getSku();
				if(!CollectionUtil.collectionIsEmpty(sku)){
					for (OrderSkuVO orderSkuVO : sku) {
						Example jobOrderItems_example=new Example(JobOrderItems.class);
						Criteria jobOrderItems_createCriteria = jobOrderItems_example.createCriteria();
						jobOrderItems_createCriteria.andEqualTo("orderId", jobOrders_parent.getId());
						jobOrderItems_createCriteria.andEqualTo("sn", orderSkuVO.getSkuId());
						List<JobOrderItems> jobOrderItemsList = jobOrderItemsMapper.selectByExample(jobOrderItems_example);
						if(!CollectionUtil.collectionIsEmpty(jobOrderItemsList)){
							JobOrderItems jobOrderItems = jobOrderItemsList.get(0);
							OrderSplitItem item=new OrderSplitItem();
							item.setChildOrderId(jobOrders.getId());
							item.setOrderSn(jobOrders_parent.getSn());
							item.setOrderEmallSn(jobOrders_parent.getEmallSn());
							item.setChildEmallSn(jobOrders.getEmallSn());
							item.setSku(orderSkuVO.getSkuId());
							item.setSupplierId(jobOrders_parent.getSupplierId());
							item.setSupplierName(jobOrders_parent.getSupplierName());
							item.setProductId(jobOrderItems.getProductId());
							item.setNum(orderSkuVO.getNum());
							item.setIsDelete(0);
							orderSplitItemMapper.insert(item);
						}
					}
				}
			}
		}
	}

}
