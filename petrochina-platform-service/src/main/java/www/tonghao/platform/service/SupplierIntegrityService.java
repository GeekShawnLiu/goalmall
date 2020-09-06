package www.tonghao.platform.service;

import java.util.List;
import java.util.Map;

import www.tonghao.platform.entity.SupplierIntegrity;
import www.tonghao.platform.entity.SupplierIntegrityVo;
import www.tonghao.service.common.base.BaseService;

public interface SupplierIntegrityService extends BaseService<SupplierIntegrity> {
	List<SupplierIntegrityVo> selectSupplier(Map<String, Object> map);
}
