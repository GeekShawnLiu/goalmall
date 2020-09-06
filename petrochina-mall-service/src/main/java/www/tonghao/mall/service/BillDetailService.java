package www.tonghao.mall.service;

import www.tonghao.mall.entity.BillDetail;
import www.tonghao.service.common.base.BaseService;

public interface BillDetailService extends BaseService<BillDetail> {

	int saveDetail(BillDetail billDetail);
	
	
}
