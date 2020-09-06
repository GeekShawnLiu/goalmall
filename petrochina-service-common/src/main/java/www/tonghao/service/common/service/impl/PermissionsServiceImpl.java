package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.TreeBuilder;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.mapper.PermissionsMapper;
import www.tonghao.service.common.service.PermissionsService;

import com.google.common.collect.Lists;

@Service("permissionsService")
@Transactional
public class PermissionsServiceImpl extends BaseServiceImpl<Permissions> implements PermissionsService {

	@Autowired
	private PermissionsMapper permissionsMapper;

	@Override
	public int savePermissions(Permissions permissions) {
		String nowDate = DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN);
		permissions.setCreatedAt(nowDate);
		permissions.setUpdatedAt(nowDate);
		permissions.setIsFrozen(0);
		permissions.setIsDelete(0);
		//设置tree开始
		HashMap<String, Object> map = setTree(permissions.getParentId(), permissions.getName());
		String treeIds = (String) map.get("treeIds");
		permissions.setTreeIds(treeIds);
		String treeNames = (String) map.get("treeNames");
		permissions.setTreeNames(treeNames);
		Integer treeDepth = (Integer) map.get("treeDepth");
		permissions.setTreeDepth(treeDepth);
		Long parentId = (Long) map.get("parentId");
		permissions.setParentId(parentId);
		//设置tree结束
		int result = permissionsMapper.insertSelective(permissions);
		return result;
	}

	@Override
	public List<Permissions> getChildrenById(long id) {
		return permissionsMapper.getChildrenById(id);
	}
	
	public HashMap<String, Object> setTree(Long parentId, String name){
		String treeIds = "";
		String treeNames = name;
		Integer treeDepth = 1;
		if (parentId != null) {
			Permissions parent = permissionsMapper.selectByPrimaryKey(parentId);
			if (parent != null) {
				if (StringUtils.isNotBlank(parent.getTreeIds())) {
					treeIds = parent.getTreeIds() + "-" + parent.getId();
				} else {
					treeIds = parent.getId()+"";
				}
				treeDepth = parent.getTreeDepth() + 1;
				treeNames = parent.getTreeNames() + "-" + name;
			}
		} else {
			parentId = 0L;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("treeIds", treeIds);
		map.put("treeNames", treeNames);
		map.put("treeDepth", treeDepth);
		map.put("parentId", parentId);
		return map;
	}

	@Override
	public int updatePermissions(Permissions permissions) {
		permissions.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		
		//设置tree开始
		HashMap<String, Object> map = setTree(permissions.getParentId(), permissions.getName());
		String treeIds = (String) map.get("treeIds");
		permissions.setTreeIds(treeIds);
		String treeNames = (String) map.get("treeNames");
		permissions.setTreeNames(treeNames);
		Integer treeDepth = (Integer) map.get("treeDepth");
		permissions.setTreeDepth(treeDepth);
		Long parentId = (Long) map.get("parentId");
		permissions.setParentId(parentId);
		//设置tree结束
		
		int result = permissionsMapper.updateByPrimaryKeySelective(permissions);
		
		updateChildsTree(permissions.getId());
		return result;
	}
	
	/**  
	 * <p>Title: updateChildsTree</p>  
	 * <p>Description: 更新子集tree</p>  
	 * @author Yml  
	 * @param parentId  
	 */  
	public void updateChildsTree(Long parentId){
		Example example = new Example(Permissions.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", parentId);
		List<Permissions> childs = permissionsMapper.selectByExample(example);
		if (childs != null && childs.size() > 0) {
			for (Permissions child : childs) {
				child.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				child.setParentId(parentId);
				//设置tree开始
				HashMap<String, Object> map = setTree(child.getParentId(), child.getName());
				String treeIds = (String) map.get("treeIds");
				child.setTreeIds(treeIds);
				String treeNames = (String) map.get("treeNames");
				child.setTreeNames(treeNames);
				Integer treeDepth = (Integer) map.get("treeDepth");
				child.setTreeDepth(treeDepth);
				Long childParentId = (Long) map.get("parentId");
				child.setParentId(childParentId);
				//设置tree结束
				permissionsMapper.updateByPrimaryKeySelective(child);
				updateChildsTree(child.getId());
			}
		}
		
	}

	@Override
	public List<Permissions> getSelectData(Long id) {
		List<Permissions> list = permissionsMapper.getSelectData(id);
		return list;
	}

	@Override
	public List<Permissions> getOtherChildrenById(long id) {
		return permissionsMapper.getOtherChildrenById(id);
	}

	@Override
	public Permissions findByUrl(String url) {
		return permissionsMapper.findByUrl(url);
	}

	@Override
	public List<Permissions> findUsableRolesPerms(List<Roles> roles) {
		return permissionsMapper.findUsableRolesPerms(roles);
	}

	@Override
	public List<TreeNode> buildTree(List<Permissions> list) {
		if(list==null){
			return null;
		}
		TreeBuilder treeBuilder = new TreeBuilder();
		List<TreeNode> nodeList = Lists.newArrayList();
		for(Permissions perm:list){
			TreeNode node = new TreeNode(perm.getId(),perm.getParentId(),perm.getName(),perm.getPriority());
			node.setIcon(perm.getIconName());
			node.setLinkUrl(perm.getUrl());
			node.setExtObj(perm.getType());
			node.setExtStr(perm.getPerms());
			nodeList.add(node);
		}
		return treeBuilder.listToTree(nodeList);
	}

}
