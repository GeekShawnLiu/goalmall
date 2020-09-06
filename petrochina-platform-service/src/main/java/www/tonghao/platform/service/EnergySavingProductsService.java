package www.tonghao.platform.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.EnergySavingProducts;


public interface EnergySavingProductsService extends BaseService<EnergySavingProducts> {

	List<EnergySavingProducts> find(Map<String, Object> maplike);

}
