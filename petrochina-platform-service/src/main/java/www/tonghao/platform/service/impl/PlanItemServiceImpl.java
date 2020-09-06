package www.tonghao.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.platform.entity.PlanItem;
import www.tonghao.platform.mapper.PlanItemMapper;
import www.tonghao.platform.service.PlanItemService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
@Service("planItemService")
public class PlanItemServiceImpl extends BaseServiceImpl<PlanItem> implements PlanItemService {
	
	@Autowired
	PlanItemMapper planItemMapper;
	@Override
	public List<PlanItem> getItemById(Long planId) {
		return planItemMapper.getItemById(planId);
	}
	@Override
	public void deductUseAmount(PlanItem item) {
		synchronized (this) {
			updateNotNull(item);
		}
	}

}
