package www.tonghao.mall.job.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.mall.job.service.JobCommonService;
import www.tonghao.service.common.entity.IntegralConsume;
import www.tonghao.service.common.entity.JobOrders;
import www.tonghao.service.common.mapper.IntegralConsumeMapper;
import www.tonghao.service.common.service.IntegralUserService;

@Service("jobCommonService")
public class JobCommonServiceImpl implements JobCommonService{

	@Autowired
	private IntegralConsumeMapper integralConsumeMapper;
	
	@Autowired
	private IntegralUserService integralUserService;

	@Override
	public void refund(JobOrders jobOrders) {
		Long payId = jobOrders.getPayId();
		if(payId != null && (1 == payId || 3 == payId)){
			IntegralConsume integralConsume = new IntegralConsume();
			integralConsume.setOrderId(jobOrders.getId());
			integralConsume.setUserId(jobOrders.getUserId());
			integralConsume.setType(1);
			List<IntegralConsume> integralConsumeList = integralConsumeMapper.select(integralConsume);
			if (CollectionUtils.isNotEmpty(integralConsumeList)) {
				for (IntegralConsume ic : integralConsumeList) {
					integralUserService.addBalance(ic.getUserId(), ic.getActivityId(), ic.getOrderId(), ic.getIntegralNum(), "下单失败退还积分");
				}
			}
		}
	}
}