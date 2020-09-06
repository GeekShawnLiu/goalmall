package www.tonghao.mall.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.mall.entity.CarouselPictrue;
import www.tonghao.service.common.base.BaseMapper;

/**
 * @Description:轮播图
 * @date 2019年4月24日
 */
public interface CarouselPictrueMapper extends BaseMapper<CarouselPictrue> {
	
	public List<CarouselPictrue> selectList(CarouselPictrue carouselPictrue);
	
	/**
	 * 
	 * Description: 查询首页轮播图
	 * 
	 * @data 2019年5月1日
	 * @param 
	 * @return
	 */
	List<CarouselPictrue> selectIndexPictrueByMap(Map<String, Object> map);
	
	/**
	 * @Description:查询最大的排序
	 * @date 2019年5月15日
	 */
	public Long selectMaxRank();
	
	/**
	 * 
	 * Description: 查询活动页
	 * 
	 * @data 2019年9月6日
	 * @param 
	 * @return
	 */
	List<CarouselPictrue> selectActivityPictrue(Map<String, Object> map);
}