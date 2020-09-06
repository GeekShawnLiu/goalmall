package www.tonghao.mall.service;

import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Users;

public interface UserRegisterService extends BaseService<Users>{

	public Map<String, Object> registerInfo(Users user);
}
