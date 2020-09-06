package www.tonghao.platform.service;

import www.tonghao.platform.entity.InterfaceUser;
import www.tonghao.service.common.base.BaseService;

public interface InterfaceUserService extends BaseService<InterfaceUser> {

	public InterfaceUser getUserByCode(InterfaceUser interfaceUser);
	
}
