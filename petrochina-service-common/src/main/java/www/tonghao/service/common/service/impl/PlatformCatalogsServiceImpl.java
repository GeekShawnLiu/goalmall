package www.tonghao.service.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.CatalogParameter;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.service.CatalogParameterService;
import www.tonghao.service.common.service.PlatformCatalogsService;

@Service("platformCatalogsService")
@Transactional
public class PlatformCatalogsServiceImpl extends BaseServiceImpl<PlatformCatalogs> implements PlatformCatalogsService {

	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;

	@Autowired
	private CatalogParameterService catalogParameterService;

	@Autowired
	private RedisDao redisDao;
	
	@Override
	public int saveOrUpdate(PlatformCatalogs platformCatalogs) {
		//层级名称拼接
		String parentName="";
		//层级id拼接
		String parentId="";
		//获取当前节点选中的所有父节点
		platformCatalogs.setTreeIds(parentId);
		platformCatalogs.setTreeNames(parentName);
		List<PlatformCatalogs> cg = platformCatalogsMapper.getParentById(platformCatalogs.getParentId());
		if(!CollectionUtils.isEmpty(cg)){
			for (PlatformCatalogs cl : cg) {
				if(cl.getId() != platformCatalogs.getId()){
					parentName += cl.getName()+"_";
					parentId += cl.getId()+"_";
				}
			}
			if(parentName.length()>0){
				parentName=parentName.substring(0, parentName.length()-1);
				parentId=parentId.substring(0, parentId.length()-1);
			}
			platformCatalogs.setTreeIds(parentId);
			platformCatalogs.setTreeNames(parentName);
		}
		int reault_default=0;
		//更新品目
		if(platformCatalogs.getId() != null){
			PlatformCatalogs cls = platformCatalogsMapper.selectByPrimaryKey(platformCatalogs.getId());
			//更新前父节点
			PlatformCatalogs oldParentCata = platformCatalogsMapper.selectByPrimaryKey(cls.getParentId());
			//更新后父节点
			PlatformCatalogs cl = platformCatalogsMapper.selectByPrimaryKey(platformCatalogs.getParentId());
			Integer c1Level = 1;
			if (cl != null) {
				c1Level = cl.getTreeDepth() + 1;
				//更新父节点isParent
				cl.setIsParent("true");
				updateNotNull(cl);
			}
			//保存当前节点更新
			platformCatalogs.setTreeDepth(c1Level);
			platformCatalogs.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			reault_default = updateNotNull(platformCatalogs);
			//更新当前节点的子节点
			List<PlatformCatalogs> childrenById = platformCatalogsMapper.getChildrenById(platformCatalogs.getId());
			if(!CollectionUtils.isEmpty(childrenById) && childrenById.size() > 1){
				String parenttreeName = "";
				String name = cls.getName();
				if (StringUtils.isNotBlank(platformCatalogs.getName())) {
					name = platformCatalogs.getName();
				}
				if (StringUtils.isNotBlank(parentName)) {
					parenttreeName = parentName + "_" + name;
				}else{
					parenttreeName = name;
				}
				String parenttreeId = "";
				if (StringUtils.isNotBlank(parentId)) {
					parenttreeId =  parentId + "_" + platformCatalogs.getId();
				}else {
					parenttreeId = "" + platformCatalogs.getId();
				}
				getChildren(platformCatalogs.getId(), c1Level+1, parenttreeName, parenttreeId);
			}
			
			//更新前父节点isParent
			if (oldParentCata != null) {
				Example example=new Example(PlatformCatalogs.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("parentId", oldParentCata.getId());
				createCriteria.andEqualTo("isDelete", 0);
				List<PlatformCatalogs> list = platformCatalogsMapper.selectByExample(example);
				if(!CollectionUtils.isEmpty(list) && list.size() > 0){
					oldParentCata.setIsParent("true");
					updateNotNull(oldParentCata);
				}else{
					oldParentCata.setIsParent("false");
					updateNotNull(oldParentCata);
				}
			}

			//更新品目参数，先删除后添加
			catalogParameterService.deleteByCatalogId(platformCatalogs.getId());

			List<CatalogParameter> parameterList = platformCatalogs.getParametersList();
			if (!CollectionUtils.isEmpty(parameterList)){
				for (CatalogParameter catalogParameter : parameterList) {
					catalogParameterService.save(catalogParameter);
				}
			}
		}else{
			//新增品目
			PlatformCatalogs newParentCata = platformCatalogsMapper.selectByPrimaryKey(platformCatalogs.getParentId());
			//更新所选父节点isParent
			if(newParentCata !=null && "false".equals(newParentCata.getIsParent())){
				newParentCata.setIsParent("true");
				updateNotNull(newParentCata);
			}
			if(newParentCata != null){
				platformCatalogs.setTreeDepth(newParentCata.getTreeDepth() + 1);
			}else{
				platformCatalogs.setTreeDepth(1);
			}
			platformCatalogs.setIsParent("false");
			platformCatalogs.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			platformCatalogs.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			reault_default = saveSelective(platformCatalogs);

			//添加品目参数
			List<CatalogParameter> parameterList = platformCatalogs.getParametersList();
			if (!CollectionUtils.isEmpty(parameterList)){
				for (CatalogParameter catalogParameter : parameterList) {
					catalogParameterService.save(catalogParameter);
				}
			}
		}
		return reault_default;
	}
	
	private void getChildren(Long parentId,int level,String parentName,String parentIds){
		Example example=new Example(PlatformCatalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("parentId", parentId);
		createCriteria.andEqualTo("isDelete", 0);
		List<PlatformCatalogs> list = platformCatalogsMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)){
			for (PlatformCatalogs catalogs : list) {
				catalogs.setTreeDepth(level);
				catalogs.setTreeNames(parentName);
				catalogs.setTreeIds(parentIds);
				updateNotNull(catalogs);
				getChildren(catalogs.getId(), level+1, parentName+"_"+catalogs.getName(), parentIds+"_"+catalogs.getId());
			}
		}
	}
	
	
	@Override
	public List<PlatformCatalogs> getChildrenById(long id) {
		return platformCatalogsMapper.getChildrenById(id);
	}
	
	@Override
	public int deleteCatalogs(long id) {
		PlatformCatalogs cata = platformCatalogsMapper.selectByPrimaryKey(id);
		Example example=new Example(PlatformCatalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("parentId", cata.getParentId());
		createCriteria.andEqualTo("isDelete", 0);
		List<PlatformCatalogs> list = platformCatalogsMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list) && list.size() == 1){
			PlatformCatalogs parentCata = platformCatalogsMapper.selectByPrimaryKey(cata.getParentId());
			parentCata.setIsParent("false");
			updateNotNull(parentCata);
		}
		int deleteByPrimaryKey = delete(id);

		//删除品目参数
		catalogParameterService.deleteByCatalogId(id);
		return deleteByPrimaryKey;
	}
	@Override
	public List<PlatformCatalogs> getParentById(Long id) {
		return platformCatalogsMapper.getParentById(id);
	}

	@Override
	public List<PlatformCatalogs> getSelectData(Long id) {
		return platformCatalogsMapper.getSelectData(id);
	}

	@Override
	public int updateDelete(Long id, Integer isDelete) {
		PlatformCatalogs cata = platformCatalogsMapper.selectByPrimaryKey(id);
		Long parentId = cata.getParentId();
		cata.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		cata.setIsDelete(isDelete);
		int result = updateNotNull(cata);
		//更新父节点isParent
		Example example=new Example(PlatformCatalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("parentId", parentId);
		createCriteria.andEqualTo("isDelete", 0);
		List<PlatformCatalogs> list = platformCatalogsMapper.selectByExample(example);
		PlatformCatalogs parentCata = platformCatalogsMapper.selectByPrimaryKey(cata.getParentId());
		if (parentCata != null) {
			if (!CollectionUtils.isEmpty(list) && list.size() > 0) {
				parentCata.setIsParent("true");
				updateNotNull(parentCata);
			}else{
				parentCata.setIsParent("false");
				updateNotNull(parentCata);
			}
		}
		return result;
	}

	@Override
	public PlatformCatalogs findRelationById(Long id) {
		return platformCatalogsMapper.findRelationById(id);
	}

	@Override
	public List<PlatformCatalogs> getCatalogsBysupplierIdAndProId(
			Map<String, Object> map) {
		return platformCatalogsMapper.getCatalogsBysupplierIdAndProId(map);
	}

	@Override
	public PlatformCatalogs findByCode(String code) {
		return platformCatalogsMapper.findByCode(code);
	}

	@Override
	public int validateName(Long parentId, Long id, String name) {
		return platformCatalogsMapper.validateName(parentId, id, name);
	}

	@Override
	public List<PlatformCatalogs> selectByTreeNamesOrCode(String treeNames, String code) {
		return platformCatalogsMapper.selectByTreeNamesOrCode(treeNames, code);
	}

	@Override
	public List<PlatformCatalogs> selectShopCatalogs(Long shopId) {
		return platformCatalogsMapper.selectShopCatalogs(shopId);
	}


	public List<TreeNode> getPortalCatalogs() {
		List<TreeNode> resultList = new ArrayList();
		Object value = redisDao.getValue("portal_catalog_list");
		if(value != null) {
			resultList = (List<TreeNode>) value;
		}else {
			resultList = getPortalCatalogList();
			redisDao.setKey("portal_catalog_list",resultList);
		}
		return resultList;
	}

	public List<TreeNode> getPortalCatalogList() {
		List<PlatformCatalogs> firstLevel = platformCatalogsMapper.getByTreeDepth(1);
		List<TreeNode> catalogNodes = Lists.newArrayListWithExpectedSize(firstLevel.size());
		for (PlatformCatalogs first : firstLevel) {
			TreeNode node = new TreeNode(first.getId(),0L,first.getName(),first.getPriority());
			node.setIcon(first.getPic());
			node.setExistChildren(false);
			if ("true".equals(first.getIsParent())) {
				node.setExistChildren(true);
				List<PlatformCatalogs> secondLevel = platformCatalogsMapper.getOneLevelChildren(first.getId());
				List<TreeNode> secondNodeList = new ArrayList<>();
				if (!CollectionUtil.collectionIsEmpty(secondLevel)) {
					for (PlatformCatalogs second : secondLevel) {
						TreeNode secondNode = new TreeNode(second.getId(),second.getParentId(),second.getName(),second.getPriority());
						secondNode.setExistChildren(false);
						if ("true".equals(second.getIsParent())) {
							secondNode.setExistChildren(true);
							List<PlatformCatalogs> thirdLevel = platformCatalogsMapper.getOneLevelChildren(second.getId());
							if (!CollectionUtil.collectionIsEmpty(thirdLevel)) {
								List<TreeNode> thirdNodeList = new ArrayList<>();
								for (PlatformCatalogs third : thirdLevel) {
									TreeNode thirdNode = new TreeNode(third.getId(),third.getParentId(),third.getName(),third.getPriority());
									thirdNode.setExistChildren(false);
									thirdNodeList.add(thirdNode);
								}
								secondNode.setChildren(thirdNodeList);
							}
						}
						secondNodeList.add(secondNode);
					}
				}
				node.setChildren(secondNodeList);
			}
			catalogNodes.add(node);
		}
		return catalogNodes;
	}
}
