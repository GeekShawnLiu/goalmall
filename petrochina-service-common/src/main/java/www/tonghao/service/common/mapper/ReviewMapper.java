package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Review;

public interface ReviewMapper extends BaseMapper<Review> {
	/**
	 * 条件查询列表
	 * @param entity
	 * @return
	 */
	List<Review> findListByEntity(Review entity);
	
	Review selectByDetailId(@Param("detailId")Long detailId);
}