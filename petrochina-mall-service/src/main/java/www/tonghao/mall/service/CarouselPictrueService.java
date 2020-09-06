package www.tonghao.mall.service;

import java.util.List;

import www.tonghao.mall.entity.CarouselPictrue;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Users;

import com.github.pagehelper.PageInfo;

/**
 * @Description:轮播图
 * @date 2019年4月24日
 */
public interface CarouselPictrueService extends BaseService<CarouselPictrue>{
	
	public PageInfo<CarouselPictrue> selectList(CarouselPictrue carouselPictrue);

	/**
	 * 
	 * Description: 查询首页轮播图
	 * 
	 * @data 2019年5月1日
	 * @param 
	 * @return
	 */
	public List<CarouselPictrue> selectIndexPictrue(Integer type, Users user);
	
	/**
	 * @Description:查询最大的排序
	 * @date 2019年5月15日
	 */
	public String selectMaxRank(Long id, Long rank);
}
