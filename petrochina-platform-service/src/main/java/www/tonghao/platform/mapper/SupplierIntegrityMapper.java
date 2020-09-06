package www.tonghao.platform.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.platform.entity.SupplierIntegrity;
import www.tonghao.platform.entity.SupplierIntegrityVo;
import www.tonghao.service.common.base.BaseMapper;


public interface SupplierIntegrityMapper extends BaseMapper<SupplierIntegrity> {
	
	List<SupplierIntegrityVo> selectSupplier(Map<String, Object> map);
	
}