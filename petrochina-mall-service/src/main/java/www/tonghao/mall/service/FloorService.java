package www.tonghao.mall.service;

import java.util.List;

import www.tonghao.mall.entity.Floor;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Users;

public interface FloorService  extends BaseService<Floor> {
    
    /**
     * 通过id查询
     * @param id
     * @return
     */
	Floor findById(Long id);
	
	/**
	 * 处理list，填充关联表数据
	 * @param list
	 * @return
	 */
	List<Floor> processList(List<Floor> list);
	
	/**
	 * 查询楼层品目商品列表
	 * @param catalogId
	 * @return
	 */
	List<MallProducts> findCatalogProducts(Long catalogId, Users user);
	
	/**
	 * 查询楼层所有品目的特惠商品列表
	 * @param catalogId
	 * @return
	 */
	List<MallProducts> findCatalogBenefitProducts(Long[] catalogIds, Users user);
	
	/**
	 * 
	 * Description: 获取楼层商品数据
	 * 
	 * @data 2019年5月15日
	 * @param 
	 * @return
	 */
	List<MallProducts> getFloorProducts(Long floorId, Integer num, Users user);
}
