package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Catalogs;

public interface CatalogsMapper extends BaseMapper<Catalogs> {
	/**
	 * 根据id 获取所有的子节点，传入0 则获取所有
	 * @param id
	 * @return
	 */
	public List<Catalogs> getChildrenById(@Param("id") long id);
	
	/**
	 * 获取所有的父节点
	 * @param id
	 * @return
	 */
	public List<Catalogs> getParentById(@Param("id") Long id);
}