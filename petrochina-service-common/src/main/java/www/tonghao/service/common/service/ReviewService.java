package www.tonghao.service.common.service;

import java.util.List;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Review;

public interface ReviewService  extends BaseService<Review> {
	
	/**
	 * 条件查询列表
	 * @param entity
	 * @return
	 */
	List<Review> findListByEntity(Review entity);
}
