package www.tonghao.platform.service;

import www.tonghao.platform.entity.PurchaseWay;
import www.tonghao.service.common.base.BaseService;

public interface PurchaseWayService extends BaseService<PurchaseWay> {

	/**
	 * 添加或者修改采购方式
	 * @param purchaseWay
	 * @return 
	 */
	public int saveOrUpdate(PurchaseWay purchaseWay);
}
