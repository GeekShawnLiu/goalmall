package www.tonghao.service.common.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.UserSystem;
import www.tonghao.service.common.service.UserSystemService;

@Service("userSystemService")
@Transactional
public class UserSystemServiceImpl extends BaseServiceImpl<UserSystem> implements UserSystemService {

}
