package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Organization;

public interface OrganizationService extends BaseService<Organization> {

	/**
	 * 添加或者修改品目
	 * @param catalogs
	 * @return
	 */
   	public int saveOrUpdate(Organization organization);
   	
   	
   	/**
	 * 根据id 获取所有的子节点，传入0 则获取所有
	 * @param id
	 * @return
	 */
	public List<Organization> getChildrenById(@Param("id") Long id);
	
	
	/**
	 * 根据id 删除当前节点
	 * @param id
	 * @return
	 */
	
	public int deleteOrganization(Long id);
	
	/**
	 * 关联查询单条
	 * @param id
	 * @return
	 */
	public Organization getOrganizationById(Long id);
	
	/**
	 * 集采机构库
	 * @param map
	 * @return
	 */
	public List<Organization> getOrgLevel(Map<String, Object> map);
	
	/**
	 * 获取所有的父节点
	 * @param id
	 * @return
	 */
	public List<Organization> getParentById(Long id);
	
	/**
	 * 
	 * Description: 根据当前操作id展开相关联节点
	 * 
	 * @data 2019年5月28日
	 * @param 
	 * @return
	 */
	public List<Organization> getNodeByOperaterId(Long id);
}
