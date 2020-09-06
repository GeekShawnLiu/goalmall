package www.tonghao.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.platform.service.EnergySavingProductsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.EnergySavingProducts;
import www.tonghao.service.common.mapper.EnergySavingProductsMapper;

@Service("energySavingProductsService")
public class EnergySavingProductsServiceImpl extends BaseServiceImpl<EnergySavingProducts>  implements EnergySavingProductsService {

	@Autowired
	private EnergySavingProductsMapper energySavingProductsMapper;
	
	@Override
	public List<EnergySavingProducts> find(Map<String, Object> maplike) {
		return energySavingProductsMapper.find(maplike);
	}

}
