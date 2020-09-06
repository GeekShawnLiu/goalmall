package www.tonghao.service.common.mapper;

import java.util.List;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.PayWayEntity;


public interface PayWayMapper extends BaseMapper<PayWayEntity> {

	/**
	 * 查询供应商可用的支付方式
	 * @param id
	 * @return
	 */
	List<PayWayEntity> findSupplierUsableList(Long id);
}