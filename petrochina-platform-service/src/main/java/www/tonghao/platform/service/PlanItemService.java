package www.tonghao.platform.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.PlanItem;
import www.tonghao.service.common.base.BaseService;

public interface PlanItemService extends BaseService<PlanItem> {
	List<PlanItem>  getItemById(@Param("planId")Long planId);
	
	/**
	 * 扣除使用金额
	 * @param item
	 */
	void deductUseAmount(PlanItem item);
}
