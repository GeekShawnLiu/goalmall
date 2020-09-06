package www.tonghao.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.platform.entity.InterfaceUser;
import www.tonghao.platform.mapper.InterfaceUserMapper;
import www.tonghao.platform.service.InterfaceUserService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("interfaceUserService")
public class InterfaceUserServiceImpl extends BaseServiceImpl<InterfaceUser> implements InterfaceUserService {

	@Autowired
	private InterfaceUserMapper interfaceUserMapper;
	
	@Override
	public InterfaceUser getUserByCode(InterfaceUser  interfaceUser) {
		return interfaceUserMapper.getUserByCode(interfaceUser);
	}

}
