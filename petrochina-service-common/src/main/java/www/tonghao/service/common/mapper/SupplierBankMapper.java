package www.tonghao.service.common.mapper;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.SupplierBank;

public interface SupplierBankMapper extends BaseMapper<SupplierBank>{

	SupplierBank selectBySupplierId(@Param("supplierId") Long supplierId);
}
