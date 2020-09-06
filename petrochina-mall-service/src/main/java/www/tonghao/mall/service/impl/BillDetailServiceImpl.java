package www.tonghao.mall.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.entity.Bill;
import www.tonghao.mall.entity.BillDetail;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.mapper.BillDetailMapper;
import www.tonghao.mall.mapper.BillMapper;
import www.tonghao.mall.mapper.OrdersMapper;
import www.tonghao.mall.service.BillDetailService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
@Service("billDetailService")
public class BillDetailServiceImpl extends BaseServiceImpl<BillDetail> implements BillDetailService {

	@Autowired
	private BillDetailMapper billDetailMapper;
	@Autowired
	private BillMapper billMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Override
	public int saveDetail(BillDetail billDetail) {
		Bill bill = billMapper.selectByPrimaryKey(billDetail.getBillId());
		int save_count=0;
		if(!StringUtils.isBlank(billDetail.getOrderIds())){
			BigDecimal tatal=bill.getTotalAmount();
			String[] split = billDetail.getOrderIds().split(",");
			for (String string : split) {
				Orders orders = ordersMapper.selectByPrimaryKey(Long.parseLong(string));
				BillDetail bd=new BillDetail();
				bd.setOrderId(Long.parseLong(string));
				bd.setBillId(bill.getId());
				bd.setCreatedAt(DateUtilEx.timeFormat(new Date()));
				bd.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				orders.setBillStatus(1);
				ordersMapper.updateByPrimaryKeySelective(orders);
				bd.setAmount(orders.getTotal());
				tatal=tatal.add(orders.getTotal());
				save_count+=billDetailMapper.insert(bd);
			}
		}
		return save_count;
	}
	
	
}
