package www.tonghao.platform.mapper;

import www.tonghao.platform.entity.InterfaceUser;
import www.tonghao.service.common.base.BaseMapper;

public interface InterfaceUserMapper extends BaseMapper<InterfaceUser> {
	
	public InterfaceUser getUserByCode(InterfaceUser interfaceUser);
}