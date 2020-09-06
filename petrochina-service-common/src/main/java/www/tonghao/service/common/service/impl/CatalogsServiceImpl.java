package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Catalogs;
import www.tonghao.service.common.mapper.CatalogsMapper;
import www.tonghao.service.common.service.CatalogsService;

@Service("catalogsService")
public class CatalogsServiceImpl extends BaseServiceImpl<Catalogs> implements CatalogsService {

	@Autowired
	private CatalogsMapper catalogsMapper;
	@Override
	public int saveOrUpdate(Catalogs catalogs) {
		String parentName="";
		String parentId="";
		List<Catalogs> cg = catalogsMapper.getParentById(catalogs.getParentId());
		if(!CollectionUtils.isEmpty(cg)){
			for (Catalogs cl : cg) {
				if(cl.getId()!=catalogs.getId()){
					parentName+=cl.getName()+"_";
					parentId+=cl.getId()+"_";
				}
			}
			if(parentName.length()>0){
				parentName=parentName.substring(0, parentName.length()-1);
				parentId=parentId.substring(0, parentId.length()-1);
			}
			catalogs.setTreeIds(parentId);
			catalogs.setTreeNames(parentName);
		}
		int reault_default=0;
		if(catalogs.getId()!=null){
			Catalogs cls = catalogsMapper.selectByPrimaryKey(catalogs.getId());
			Example example=new Example(Catalogs.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("parentId", cls.getParentId());
			List<Catalogs> list = catalogsMapper.selectByExample(example);
			if(!CollectionUtils.isEmpty(list)&&list.size()==1){
				Catalogs cl = catalogsMapper.selectByPrimaryKey(cls.getParentId());
				cl.setIsParent("false");
				catalogsMapper.updateByPrimaryKeySelective(cl);
			}
			Catalogs cl = catalogsMapper.selectByPrimaryKey(catalogs.getParentId());
			if(cl!=null&&"false".equals(cl.getIsParent())){
				cl.setIsParent("true");
				catalogsMapper.updateByPrimaryKeySelective(cl);
			}
			List<Catalogs> childrenById = catalogsMapper.getChildrenById(catalogs.getId());
			if(!CollectionUtils.isEmpty(childrenById)&&childrenById.size()>1){
				getChildren(catalogs.getId(),cl.getTreeDepth()+2,parentName+"_"+catalogs.getName(),parentId+"_"+catalogs.getId());
			}
			catalogs.setTreeDepth(cl.getTreeDepth()+1);
			catalogs.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			reault_default=catalogsMapper.updateByPrimaryKeySelective(catalogs);
		}else{
			Catalogs cl = catalogsMapper.selectByPrimaryKey(catalogs.getParentId());
			if(cl!=null&&"false".equals(cl.getIsParent())){
				cl.setIsParent("true");
				catalogsMapper.updateByPrimaryKeySelective(cl);
			}
			if(cl!=null){
				catalogs.setTreeDepth(cl.getTreeDepth()+1);
			}else{
				catalogs.setTreeDepth(1);
			}
			catalogs.setIsParent("false");
			catalogs.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			reault_default=catalogsMapper.insert(catalogs);
		}
		return reault_default;
	}
	
	private void getChildren(Long parentId,int level,String parentName,String parentIds){
		Example example=new Example(Catalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("parentId", parentId);
		List<Catalogs> list = catalogsMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)){
			for (Catalogs catalogs : list) {
				catalogs.setTreeDepth(level);
				catalogs.setTreeNames(parentName);
				catalogs.setTreeIds(parentIds);
				catalogsMapper.updateByPrimaryKeySelective(catalogs);
				getChildren(catalogs.getId(), level+1, parentName+"_"+catalogs.getName(), parentIds+"_"+catalogs.getId());
			}
		}
	}
	
	
	@Override
	public List<Catalogs> getChildrenById(long id) {
		return catalogsMapper.getChildrenById(id);
	}
	@Override
	public int deleteCatalogs(long id) {
		Catalogs cata = catalogsMapper.selectByPrimaryKey(id);
		Example example=new Example(Catalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("parentId", cata.getParentId());
		List<Catalogs> list = catalogsMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)&&list.size()==1){
			Catalogs ca = catalogsMapper.selectByPrimaryKey(cata.getParentId());
			ca.setIsParent("false");
			catalogsMapper.updateByPrimaryKeySelective(ca);
		}
		int deleteByPrimaryKey = catalogsMapper.deleteByPrimaryKey(id);
		return deleteByPrimaryKey;
	}
	@Override
	public List<Catalogs> getParentById(Long id) {
		return catalogsMapper.getParentById(id);
	}

}
