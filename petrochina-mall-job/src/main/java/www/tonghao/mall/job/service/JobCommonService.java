package www.tonghao.mall.job.service;

import www.tonghao.service.common.entity.JobOrders;

public interface JobCommonService {

	/**
	 * 
	 * Description: 取消订单退还积分
	 * 
	 * @data 2019年7月19日
	 * @param
	 * @return
	 */
	public void refund(JobOrders jobOrders);
}
