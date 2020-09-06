package www.tonghao.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.PlanItem;
import www.tonghao.service.common.base.BaseMapper;

public interface PlanItemMapper extends BaseMapper<PlanItem> {
	List<PlanItem>  getItemById(@Param("planId")Long planId);
	PlanItem findCategoryByPlanID(@Param("planId")Long planId);
}