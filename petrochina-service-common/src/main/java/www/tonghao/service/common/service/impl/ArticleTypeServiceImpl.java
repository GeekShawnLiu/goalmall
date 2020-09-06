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
import www.tonghao.service.common.entity.ArticleType;
import www.tonghao.service.common.mapper.ArticleTypeMapper;
import www.tonghao.service.common.service.ArticleTypeService;

@Service("articleTypeService")
public class ArticleTypeServiceImpl extends BaseServiceImpl<ArticleType> implements ArticleTypeService {

	@Autowired
	private ArticleTypeMapper articleTypeMapper;
	@Override
	public int saveOrUpdate(ArticleType articleType) {
		String parentName="";
		String parentId="";
		List<ArticleType> parentById = articleTypeMapper.getParentById(articleType.getParentId());
		if(!CollectionUtils.isEmpty(parentById)){
			for ( ArticleType at : parentById) {
				if(at.getId()!=articleType.getId()){
					parentName+=at.getName()+"_";
					parentId+=at.getId()+"_";
				}
			}
			if(parentName.length()>0){
				parentName=parentName.substring(0, parentName.length()-1);
				parentId=parentId.substring(0, parentId.length()-1);
			}
			articleType.setTreeNames(parentName);
			articleType.setTreeIds(parentId);
		}
		int result_default=0;
		if(articleType.getId()!=null){
			ArticleType art = articleTypeMapper.selectByPrimaryKey(articleType.getId());//获取老数据的父节点
			Example example=new Example(ArticleType.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("parentId", art.getParentId());
			List<ArticleType> list = articleTypeMapper.selectByExample(example);
			if(!CollectionUtils.isEmpty(list)&&list.size()==1){//判断老的新节点的父节点
				ArticleType at = articleTypeMapper.selectByPrimaryKey(art.getParentId());
				at.setIsParent("false");
				articleTypeMapper.updateByPrimaryKeySelective(at);
			}
			ArticleType at = articleTypeMapper.selectByPrimaryKey(articleType.getParentId());
			if(at!=null&&"false".equals(at.getIsParent())){//判断修改的新节点的父节点
				at.setIsParent("true");
				articleTypeMapper.updateByPrimaryKeySelective(at);
			}
			List<ArticleType> childrenById = articleTypeMapper.getChildrenById(articleType.getId());
			if(!CollectionUtils.isEmpty(childrenById)&&childrenById.size()>1){
				getChildren(articleType.getId(),at.getTreeDepth()+2,parentName+"_"+articleType.getName(),parentId+"_"+articleType.getId());
			}
			if(at != null){
				articleType.setTreeDepth(at.getTreeDepth()+1);
			}
			articleType.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=articleTypeMapper.updateByPrimaryKeySelective(articleType);
		}else{
			ArticleType at = articleTypeMapper.selectByPrimaryKey(articleType.getParentId());
			if(at!=null&&"false".equals(at.getIsParent())){
				at.setIsParent("true");
				articleTypeMapper.updateByPrimaryKeySelective(at);
			}
			if(at!=null){
				articleType.setTreeDepth(at.getTreeDepth()+1);	
			}else{
				articleType.setTreeDepth(1);
			}
			articleType.setIsParent("false");
			articleType.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=articleTypeMapper.insert(articleType);
		}
		return result_default;
	}
	
	private void getChildren(Long parentId,int level,String parentName,String parentIds){
		Example example=new Example(ArticleType.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("parentId", parentId);
		List<ArticleType> list = articleTypeMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)){
			for (ArticleType articleType : list) {
				articleType.setTreeDepth(level);
				articleType.setTreeNames(parentName);
				articleType.setTreeIds(parentIds);
				articleTypeMapper.updateByPrimaryKeySelective(articleType);
				getChildren(articleType.getId(), level+1, parentName+"_"+articleType.getName(), parentIds+"_"+articleType.getId());
			}
		}
	}
	@Override
	public List<ArticleType> getChildrenById(long id) {
		return articleTypeMapper.getChildrenById(id);
	}
	@Override
	public int deleteArticleType(long id) {
		ArticleType at = articleTypeMapper.selectByPrimaryKey(id);
		Example example=new Example(ArticleType.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("parentId", at.getParentId());
		List<ArticleType> list = articleTypeMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)&&list.size()==1){
			ArticleType artType = articleTypeMapper.selectByPrimaryKey(at.getParentId());
			artType.setIsParent("false");
			articleTypeMapper.updateByPrimaryKeySelective(artType);
		}
		int deleteByPrimaryKey = articleTypeMapper.deleteByPrimaryKey(id);
		return deleteByPrimaryKey;
	}
	@Override
	public List<ArticleType> getParentById(Long id) {
		return articleTypeMapper.getParentById(id);
	}

	
	
	
}
