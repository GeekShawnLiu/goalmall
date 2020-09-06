package www.tonghao.service.common.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.OrgSupplier;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.mapper.OrgSupplierMapper;
import www.tonghao.service.common.mapper.OrganizationMapper;
import www.tonghao.service.common.service.OrganizationService;

@Service("organizationService")
public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements OrganizationService {

	@Autowired
	private OrganizationMapper organizationMapper;
	
	@Autowired
	private OrgSupplierMapper orgSupplierMapper;
	@Override
	public int saveOrUpdate(Organization organization) {
		String parentName="";
		String parentId="";
		organization.setIsDelete(false);
		List<Organization> ot = organizationMapper.getParentById(organization.getParentId());
		if(!CollectionUtils.isEmpty(ot)){
			for (Organization og : ot) {
				if(og.getId()!=organization.getId()){
					parentName+=og.getName()+"_";
					parentId+=og.getId()+"_";
				}
			}
			if(parentName.length()>0){
				parentName=parentName.substring(0, parentName.length()-1);
				parentId=parentId.substring(0, parentId.length()-1);
			}
			organization.setTreeIds(parentId);
			organization.setTreeNames(parentName);
		}
		int result_default=0;
		if(organization.getId()!=null){
			Organization org = organizationMapper.selectByPrimaryKey(organization.getId());
			Example example=new Example(Organization.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("parentId", org.getParentId());
			List<Organization> list = organizationMapper.selectByExample(example);
			if(!CollectionUtils.isEmpty(list)&&list.size()==1){
				Organization orgs = organizationMapper.selectByPrimaryKey(org.getParentId());
				orgs.setIsParent("false");
				organizationMapper.updateByPrimaryKeySelective(orgs);
			}
			Organization orgs = organizationMapper.selectByPrimaryKey(organization.getParentId());
			if(orgs!=null&&"false".equals(orgs.getIsParent())){
				orgs.setIsParent("true");
				organizationMapper.updateByPrimaryKeySelective(orgs);
			}
			List<Organization> childrenById = organizationMapper.getChildrenById(organization.getId());
			if(!CollectionUtils.isEmpty(childrenById)&&childrenById.size()>1){
				getChildren(organization.getId(),orgs.getTreeDepth()+2,parentName+"_"+organization.getName(),parentId+"_"+organization.getId());
			}
			organization.setTreeDepth(orgs.getTreeDepth()+1);
			organization.setUpdatedAt(DateUtilEx.format(new Date(),DateUtilEx.TIME_PATTERN));
			result_default=organizationMapper.updateByPrimaryKeySelective(organization);
			//保存机构关联供应商信息
			Example osExample = new Example(OrgSupplier.class);
			Criteria osCriteria = osExample.createCriteria();
			osCriteria.andEqualTo("orgId", organization.getId());
			orgSupplierMapper.deleteByExample(osExample);
			
			if(StringUtils.isNotEmpty(organization.getSupplierIds())){
				String[] split = organization.getSupplierIds().split(",");
				for (String sId : split) {
					OrgSupplier orgSupplier = new OrgSupplier();
					orgSupplier.setOrgId(organization.getId());
					orgSupplier.setSupplierId(Long.parseLong(sId));
					orgSupplierMapper.insertSelective(orgSupplier);
				}
			}
		}else{
			Organization org = organizationMapper.selectByPrimaryKey(organization.getParentId());
			if(org!=null&&"false".equals(org.getIsParent())){
				org.setIsParent("true");
				organizationMapper.updateByPrimaryKeySelective(org);
			}
			if(org!=null){
				organization.setTreeDepth(org.getTreeDepth()+1);
			}else{
				organization.setTreeDepth(1);
			}
			organization.setIsParent("false");
			organization.setCreatedAt(DateUtilEx.format(new Date(),DateUtilEx.TIME_PATTERN));
			result_default=organizationMapper.insert(organization);
			//保存机构关联供应商信息
			if(StringUtils.isNotEmpty(organization.getSupplierIds())){
				String[] split = organization.getSupplierIds().split(",");
				for (String sId : split) {
					OrgSupplier orgSupplier = new OrgSupplier();
					orgSupplier.setOrgId(organization.getId());
					orgSupplier.setSupplierId(Long.parseLong(sId));
					orgSupplierMapper.insertSelective(orgSupplier);
				}
			}
		}
		return result_default;
	}
	private void getChildren(Long parentId,int level,String parentName,String parentIds){
		Example example=new Example(Organization.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("parentId", parentId);
		List<Organization> list = organizationMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)){
			for (Organization organization : list) {
				organization.setTreeDepth(level);
				organization.setTreeNames(parentName);
				organization.setTreeIds(parentIds);
				organizationMapper.updateByPrimaryKeySelective(organization);
				getChildren(organization.getId(), level+1,parentName+"_"+organization.getName(), parentIds+"_"+organization.getId());
			}
		}
	}
	@Override
	public List<Organization> getChildrenById(Long id) {
		return organizationMapper.getChildrenById(id);
	}

	@Override
	public int deleteOrganization(Long id) {
		Organization org = organizationMapper.selectByPrimaryKey(id);
		Example example=new Example(Organization.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("parentId", org.getParentId());
		List<Organization> list = organizationMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)&&list.size()==1){
			Organization oz = organizationMapper.selectByPrimaryKey(org.getParentId());
			oz.setIsParent("false");
			organizationMapper.updateByPrimaryKeySelective(oz);
		}
		int deleteByPrimaryKey = organizationMapper.deleteByPrimaryKey(id);
		return deleteByPrimaryKey;
	}

	@Override
	public Organization getOrganizationById(Long id) {
		return organizationMapper.getOrganizationById(id);
	}

	@Override
	public List<Organization> getOrgLevel(Map<String, Object> map) {
		return organizationMapper.getOrgLevel(map);
	}

	@Override
	public List<Organization> getParentById(Long id) {
		return organizationMapper.getParentById(id);
	}
	@Override
	public List<Organization> getNodeByOperaterId(Long id) {
		Set<Organization> resultSet = new HashSet<Organization>();
		List<Organization> parentList = new ArrayList<Organization>();
		parentList = getAllParent(id, parentList);
		resultSet.addAll(parentList);
		if(!CollectionUtils.isEmpty(parentList)){
			for (Organization organization : parentList) {
				Example example=new Example(Organization.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("parentId", organization.getId());
				List<Organization> selectByExample = organizationMapper.selectByExample(example);
				if(!CollectionUtils.isEmpty(selectByExample)){
					resultSet.addAll(selectByExample);
				}
			}
		}
		
		if(!CollectionUtils.isEmpty(resultSet)){
			resultSet.forEach(o -> {
				o.setOpen(true);
			});
		}
		List<Organization> arrayList = new ArrayList<Organization>(resultSet);
		
		List<Organization> arrayList2 = arrayList.stream().sorted(Comparator.comparing(Organization::getPriority)).collect(Collectors.toList());
		return arrayList2;
	}
	
	/**
	 * 
	 * Description: 获取所有的父级节点
	 * 
	 * @data 2019年5月28日
	 * @param 
	 * @return
	 */
	public List<Organization> getAllParent(Long id, List<Organization> list) {
		Organization organization = organizationMapper.selectByPrimaryKey(id);
		if (organization != null) {
			list.add(organization);
			getAllParent(organization.getParentId(), list);
		}
		return list;
	}

}
