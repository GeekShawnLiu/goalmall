package www.tonghao.platform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.platform.entity.EnvironmentProducts;
import www.tonghao.platform.service.EnvironmentProductsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("environmentProductsService")
@Transactional
public class EnvironmentProductsServiceImpl extends BaseServiceImpl<EnvironmentProducts> implements EnvironmentProductsService {

}
