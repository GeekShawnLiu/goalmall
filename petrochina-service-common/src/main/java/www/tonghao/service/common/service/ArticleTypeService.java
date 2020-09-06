package www.tonghao.service.common.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.ArticleType;

public interface ArticleTypeService extends BaseService<ArticleType>{
	/**
	 * 添加或者修改栏目
	 * @param articleType
	 * @return
	 */
    public int saveOrUpdate(ArticleType articleType);
    
    /**
	 * 根据id 获取所有的子节点，传入0 则获取所有
	 * @param id
	 * @return
	 */
	public List<ArticleType> getChildrenById(@Param("id") long id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public int deleteArticleType(long id);
	/**
	 * 根据id获取所有父节点 
	 * @param id
	 * @return
	 */
	public List<ArticleType> getParentById(Long id);
}
