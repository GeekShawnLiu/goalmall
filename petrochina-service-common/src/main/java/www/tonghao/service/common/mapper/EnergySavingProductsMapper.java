package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.EnergySavingProducts;


public interface EnergySavingProductsMapper extends BaseMapper<EnergySavingProducts> {

	List<EnergySavingProducts> find(Map<String, Object> maplike);
}