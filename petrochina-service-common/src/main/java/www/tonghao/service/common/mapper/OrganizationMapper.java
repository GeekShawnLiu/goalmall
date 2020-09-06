package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Organization;

public interface OrganizationMapper extends BaseMapper<Organization> {
	/**
	 * 根据id 获取所有的子节点，传入0 则获取所有
	 * @param id
	 * @return
	 */
	public List<Organization> getChildrenById(@Param("id") long id);
	
	/**
	 * 根据id查询采购人库
	 * @param id
	 * @return
	 */
	public Organization getOrganizationById(@Param("id") Long id);
	
	/**
	 * 查询集采机构库
	 */
	public List<Organization> getOrgLevel(Map<String, Object> map);
	
	/**
	 * 获取所有的父节点
	 * @param id
	 * @return
	 */
	public List<Organization> getParentById(@Param("id") Long id);
}