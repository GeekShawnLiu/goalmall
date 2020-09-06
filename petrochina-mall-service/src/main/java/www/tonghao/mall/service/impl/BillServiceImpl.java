package www.tonghao.mall.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.entity.Bill;
import www.tonghao.mall.entity.BillDetail;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.mapper.BillDetailMapper;
import www.tonghao.mall.mapper.BillMapper;
import www.tonghao.mall.mapper.OrdersMapper;
import www.tonghao.mall.service.BillService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

/**
 * 采购对账
 * @author developer001
 *
 */
@Service("mallBillService")
public class BillServiceImpl extends BaseServiceImpl<Bill> implements BillService{

	@Autowired
	private BillMapper billMapper;
	@Autowired
	private BillDetailMapper billDetailMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Override
	public List<Bill> findListByEntity(Bill entity) {
		return billMapper.findListByEntity(entity);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveBill(Bill entity) {
		entity.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		entity.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		entity.setStatus(1);
		entity.setIsDelete(0);
		int save = billMapper.insert(entity);
		BigDecimal tatal=new BigDecimal("0");
		if(!StringUtils.isBlank(entity.getOrderIds())){
			String[] split = entity.getOrderIds().split(",");
			for (String orderid : split) {
				BillDetail bd=new BillDetail();
				bd.setOrderId(Long.parseLong(orderid));
				bd.setBillId(entity.getId());
				bd.setCreatedAt(DateUtilEx.timeFormat(new Date()));
				bd.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				Orders order = ordersMapper.selectByPrimaryKey(bd.getOrderId());
				order.setBillStatus(1);
				ordersMapper.updateByPrimaryKeySelective(order);
				bd.setAmount(order.getTotal());
				tatal=tatal.add(order.getTotal());
				save+=billDetailMapper.insert(bd);
			}
		}
		entity.setTotalAmount(tatal);
		billMapper.updateByPrimaryKeySelective(entity);
		return save;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteBill(Long id) {
		Bill bill = billMapper.selectByPrimaryKey(id);
		bill.setIsDelete(1);
		int delete = billMapper.updateByPrimaryKeySelective(bill);
		Example example=new Example(BillDetail.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("billId", id);
		List<BillDetail> billDetails = billDetailMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(billDetails)){
			for (BillDetail billDetail : billDetails) {
				Orders order = ordersMapper.selectByPrimaryKey(billDetail.getOrderId());
				order.setBillStatus(0);
				ordersMapper.updateByPrimaryKeySelective(order);
			}
		}
		return delete;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteBillDetail(Long id) {
		BillDetail billDetail = billDetailMapper.selectByPrimaryKey(id);
		int delete = billDetailMapper.deleteByPrimaryKey(id);
		Orders order = ordersMapper.selectByPrimaryKey(billDetail.getOrderId());
		order.setBillStatus(0);
		Example example=new Example(BillDetail.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("billId", billDetail.getBillId());
		List<BillDetail> billDetails = billDetailMapper.selectByExample(example);
		BigDecimal tatal=new BigDecimal("0");
		if(!CollectionUtils.isEmpty(billDetails)){
			for (BillDetail billDetailList : billDetails) {
				tatal=tatal.add(billDetailList.getAmount());
			}
		}
		Bill bill = billMapper.selectByPrimaryKey(billDetail.getBillId());
		bill.setTotalAmount(tatal);
		billMapper.updateByPrimaryKeySelective(bill);
		ordersMapper.updateByPrimaryKeySelective(order);
		return delete;
	}

	@Override
	public int updateBill(Bill entity) {
		Example example=new Example(BillDetail.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("billId", entity.getId());
		List<BillDetail> billDetails = billDetailMapper.selectByExample(example);
		BigDecimal tatal=new BigDecimal("0");
		if(!CollectionUtils.isEmpty(billDetails)){
			for (BillDetail billDetail : billDetails) {
				tatal=tatal.add(billDetail.getAmount());
			}
		}
		entity.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		entity.setTotalAmount(tatal);
		int update = billMapper.updateByPrimaryKeySelective(entity);
		return update;
	}
	
}
