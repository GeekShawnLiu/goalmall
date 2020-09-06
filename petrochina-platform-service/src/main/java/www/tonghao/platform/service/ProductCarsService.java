package www.tonghao.platform.service;

import java.util.List;

import www.tonghao.platform.entity.ProductCars;
import www.tonghao.service.common.base.BaseService;

public interface ProductCarsService extends BaseService<ProductCars>  {
	
	/**
	 * 根据商品ID查询
	 * @param productId
	 * @return
	 */
	List<ProductCars> findListByProduct(Long productId);
}
