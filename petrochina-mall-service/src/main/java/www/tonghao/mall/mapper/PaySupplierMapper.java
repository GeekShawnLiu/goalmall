package www.tonghao.mall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.mall.entity.PaySupplier;
import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Suppliers;

public interface PaySupplierMapper extends BaseMapper<PaySupplier> {

	List<Suppliers> findSuppliersByPayWayId(@Param("payWayId") Long payWayId);
}