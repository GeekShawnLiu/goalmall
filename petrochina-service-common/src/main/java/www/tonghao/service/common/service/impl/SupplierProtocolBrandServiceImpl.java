package www.tonghao.service.common.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.SupplierProtocolBrand;
import www.tonghao.service.common.mapper.SupplierProtocolBrandMapper;
import www.tonghao.service.common.service.SupplierProtocolBrandService;

@Service("supplierProtocolBrandService")
public class SupplierProtocolBrandServiceImpl extends BaseServiceImpl<SupplierProtocolBrand> implements SupplierProtocolBrandService{

	@Autowired
	private SupplierProtocolBrandMapper supplierProtocolBrandMapper;

	@Override
	public List<Brand> getSupplierProtocolBrands(HashMap<String, Object> map) {
		return supplierProtocolBrandMapper.getSupplierProtocolBrands(map);
	}
}
