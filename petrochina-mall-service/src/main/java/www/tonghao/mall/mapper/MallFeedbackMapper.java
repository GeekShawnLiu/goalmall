package www.tonghao.mall.mapper;

import java.util.List;

import www.tonghao.mall.entity.MallFeedback;
import www.tonghao.service.common.base.BaseMapper;

public interface MallFeedbackMapper extends BaseMapper<MallFeedback> {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	MallFeedback findById(Long id);
	
	/**
	 * 条件查询列表
	 * @param entity
	 * @return
	 */
	List<MallFeedback> findListByEntity(MallFeedback entity);
}