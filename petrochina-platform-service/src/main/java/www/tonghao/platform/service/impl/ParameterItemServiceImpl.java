package www.tonghao.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.platform.service.ParameterItemService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.ParameterItem;
import www.tonghao.service.common.mapper.ParameterItemMapper;
@Service("parameterItemService")
public class ParameterItemServiceImpl extends BaseServiceImpl<ParameterItem> implements ParameterItemService {

	@Autowired
	private ParameterItemMapper parameterItemMapper;
	@Override
	public int deleteParameterItemByParameterId(Long parameterId) {
		return parameterItemMapper.deleteParameterItemByParameterId(parameterId);
	}

}
