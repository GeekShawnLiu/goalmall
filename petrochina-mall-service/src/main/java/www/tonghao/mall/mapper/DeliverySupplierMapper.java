package www.tonghao.mall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.mall.entity.DeliverySupplier;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Suppliers;

public interface DeliverySupplierMapper extends BaseMapper<DeliverySupplier> {
	
	List<Suppliers> findSuppliersByDeliveryId(@Param("deliveryId") Long deliveryId);
}