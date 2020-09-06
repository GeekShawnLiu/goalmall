package www.tonghao.service.common.service;

import java.util.List;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Areas;


public interface AreasService extends BaseService<Areas> {

	/**  
	 * <p>Title: saveAreas</p>  
	 * <p>Description: 保存地区</p>  
	 * @author Yml  
	 * @param areas
	 * @return  
	 */  
	int saveAreas(Areas areas);

	/**  
	 * <p>Title: getChildrenById</p>  
	 * <p>Description: 根据id 获取所有的子节点，传入0 则获取所有</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	List<Areas> getChildrenById(long id);

	/**
	 * 条件查询列表
	 * @param areas
	 * @return
	 */
	List<Areas> findListByEntity(Areas areas);
}
