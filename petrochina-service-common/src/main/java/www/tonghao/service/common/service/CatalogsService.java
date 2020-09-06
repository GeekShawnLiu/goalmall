package www.tonghao.service.common.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Catalogs;

public interface CatalogsService extends BaseService<Catalogs> {

	/**
	 * 添加或者修改品目
	 * @param catalogs
	 * @return
	 */
   	public int saveOrUpdate(Catalogs catalogs);
   	
   	
   	/**
	 * 根据id 获取所有的子节点，传入0 则获取所有
	 * @param id
	 * @return
	 */
	public List<Catalogs> getChildrenById(@Param("id") long id);
	
	
	/**
	 * 根据id 删除当前节点
	 * @param id
	 * @return
	 */
	
	public int deleteCatalogs(long id);
	
	
	/**
	 * 获取所有的父节点
	 * @param id
	 * @return
	 */
	public List<Catalogs> getParentById(Long id);
	
}
