package www.tonghao.platform.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.platform.entity.PurchaseWay;
import www.tonghao.platform.mapper.PurchaseWayMapper;
import www.tonghao.platform.service.PurchaseWayService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("purchaseWayService")
public class PurchaseWayServiceImpl extends BaseServiceImpl<PurchaseWay> implements PurchaseWayService {

	@Autowired
	private PurchaseWayMapper purchaseWayMapper;
	@Override
	public int saveOrUpdate(PurchaseWay purchaseWay) {
		int result_default=0;
        if(purchaseWay.getId()!=null){
        	purchaseWay.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
        	result_default=purchaseWayMapper.updateByPrimaryKeySelective(purchaseWay);
        }else{
        	purchaseWay.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
        	result_default=purchaseWayMapper.insert(purchaseWay);
        }
		return result_default;
	}

}
