package www.tonghao.platform.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.ParameterItem;

import java.util.Map;

public interface ParameterItemService extends BaseService<ParameterItem> {
	int deleteParameterItemByParameterId(Long parameterId);

	Map<String, Object> saveOrUpdate(ParameterItem item);
}
