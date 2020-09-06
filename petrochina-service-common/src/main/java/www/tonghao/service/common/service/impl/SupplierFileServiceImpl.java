package www.tonghao.service.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.SupplierFile;
import www.tonghao.service.common.mapper.SupplierFileMapper;
import www.tonghao.service.common.service.SupplierFileService;
@Service("supplierFileService")
public class SupplierFileServiceImpl extends BaseServiceImpl<SupplierFile> implements SupplierFileService {

	@Autowired
	private SupplierFileMapper supplierFileMapper;
}
