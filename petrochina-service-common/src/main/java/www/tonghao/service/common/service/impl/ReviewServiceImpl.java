package www.tonghao.service.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Review;
import www.tonghao.service.common.mapper.ReviewMapper;
import www.tonghao.service.common.service.ReviewService;

/**
 * 评价
 * @author developer001
 *
 */
@Service("mallReviewService")
public class ReviewServiceImpl extends BaseServiceImpl<Review> implements ReviewService{
	@Autowired
	private ReviewMapper reviewMapper;
	@Override
	public List<Review> findListByEntity(Review entity) {
		return reviewMapper.findListByEntity(entity);
	}
	
}
