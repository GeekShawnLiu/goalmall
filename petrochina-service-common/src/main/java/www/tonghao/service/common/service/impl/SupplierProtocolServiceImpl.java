package www.tonghao.service.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.SupplierProtocol;
import www.tonghao.service.common.mapper.SupplierProtocolMapper;
import www.tonghao.service.common.service.SupplierProtocolService;
@Service("supplierProtocolService")
public class SupplierProtocolServiceImpl extends BaseServiceImpl<SupplierProtocol> implements SupplierProtocolService {
	
	@Autowired
	private SupplierProtocolMapper supplierProtocolMapper;
	
	/**
	 * 条件查询
	 */
	public SupplierProtocol findEntityBySupplierProtocol(Map<String, Object> map) {
		return supplierProtocolMapper.findEntityBySupplierProtocol(map);
	}

	public List selectDistinctSupplier() {
		return supplierProtocolMapper.selectDistinctSupplier();
	}
	
	

}
