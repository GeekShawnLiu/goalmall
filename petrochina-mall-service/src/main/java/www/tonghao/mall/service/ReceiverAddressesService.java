package www.tonghao.mall.service;

import www.tonghao.mall.entity.ReceiverAddresses;
import www.tonghao.service.common.base.BaseService;

public interface ReceiverAddressesService  extends BaseService<ReceiverAddresses> {

	/**
	 * 得到用户默认收货地址
	 * @param userId
	 * @return
	 */
	ReceiverAddresses findUserDefault(Long userId);
}
