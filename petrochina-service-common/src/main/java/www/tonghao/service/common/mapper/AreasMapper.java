package www.tonghao.service.common.mapper;

import java.util.List;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Areas;


public interface AreasMapper extends BaseMapper<Areas> {

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
	
	/**
	 * 按name及treeIds模糊匹配
	 * @param areas
	 * @return
	 */
	Areas likeNameOrTreeIds(Areas areas);
}