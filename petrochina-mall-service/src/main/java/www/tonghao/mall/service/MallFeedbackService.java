package www.tonghao.mall.service;

import java.util.List;

import www.tonghao.mall.entity.MallFeedback;
import www.tonghao.service.common.base.BaseService;

public interface MallFeedbackService extends BaseService<MallFeedback> {

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
