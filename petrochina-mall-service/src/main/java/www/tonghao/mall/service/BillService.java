package www.tonghao.mall.service;

import java.util.List;

import www.tonghao.mall.entity.Bill;
import www.tonghao.service.common.base.BaseService;

public interface BillService  extends BaseService<Bill> {
	
	/**
	 * 条件查询列表
	 * @param entity
	 * @return
	 */
	List<Bill> findListByEntity(Bill entity);
	
	int saveBill(Bill entity);
	
	int deleteBill(Long id);
	
	int deleteBillDetail(Long id);
	
	
	int updateBill(Bill entity);
	

	
}
