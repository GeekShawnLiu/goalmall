package www.tonghao.mall.mapper;

import java.util.List;

import www.tonghao.mall.entity.Bill;
import www.tonghao.service.common.base.BaseMapper;

public interface BillMapper extends BaseMapper<Bill> {
	
	/**
	 * 条件查询列表
	 * @param entitys
	 * @return
	 */
	List<Bill> findListByEntity(Bill entity);
}