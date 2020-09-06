package www.tonghao.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.platform.entity.SupplierIntegrity;
import www.tonghao.platform.entity.SupplierIntegrityVo;
import www.tonghao.platform.mapper.SupplierIntegrityMapper;
import www.tonghao.platform.service.SupplierIntegrityService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("supplierIntegrityService")
public class SupplierIntegrityServiceImpl extends BaseServiceImpl<SupplierIntegrity> implements SupplierIntegrityService{

	@Autowired
	private SupplierIntegrityMapper supplierIntegrityMapper;
	
	@Override
	public List<SupplierIntegrityVo> selectSupplier(Map<String, Object> map) {
		return supplierIntegrityMapper.selectSupplier(map);
	}

	
}
