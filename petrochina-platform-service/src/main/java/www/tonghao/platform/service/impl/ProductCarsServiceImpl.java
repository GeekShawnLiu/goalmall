package www.tonghao.platform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.platform.entity.ProductCars;
import www.tonghao.platform.service.ProductCarsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
@Service("productCarsService")
public class ProductCarsServiceImpl extends BaseServiceImpl<ProductCars> implements ProductCarsService {

	@Override
	public List<ProductCars> findListByProduct(Long productId) {
		Example example = new Example(ProductCars.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("productId", productId);
		example.setOrderByClause(" id asc ");
		return selectByExample(example);
	}
}
