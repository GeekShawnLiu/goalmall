package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.DeliveryWayEntity;

public interface DeliveryWayMapper extends BaseMapper<DeliveryWayEntity> {

	/**
	 * 查询供应商可用的配送方式
	 * @param supplierId
	 * @return
	 */
	List<DeliveryWayEntity> findSupplierUsableList(@Param("supplierId")Long supplierId);
}