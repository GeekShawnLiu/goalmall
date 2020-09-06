package www.tonghao.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.platform.entity.AcceptOrder;
import www.tonghao.platform.mapper.AcceptOrderMapper;
import www.tonghao.platform.service.AcceptOrderService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
@Service("acceptOrderService")
public class AcceptOrderServiceImpl extends BaseServiceImpl<AcceptOrder> implements AcceptOrderService {
	@Autowired
	private AcceptOrderMapper acceptOrderMapper;
}
