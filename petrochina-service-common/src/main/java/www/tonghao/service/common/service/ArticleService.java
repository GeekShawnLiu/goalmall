package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Article;


public interface ArticleService extends BaseService<Article> {

	/**  
	 * <p>Title: saveUser</p>  
	 * <p>Description: 保存公告</p>  
	 * @author Yml  
	 * @param article
	 * @return  
	 */  
	int saveArticle(Article article);

	/**  
	 * <p>Title: getById</p>  
	 * <p>Description: 根据id查询</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	Article getById(Long id);

	/**  
	 * <p>Title: find</p>  
	 * <p>Description: 根据条件查询</p>  
	 * @author Yml  
	 * @param map
	 * @return  
	 */  
	List<Article> find(Map<String, Object> map);
	List<Article> findNotify(Map<String, Object> map);
	List<Article> findArticle(Map<String, Object> map);
	List<Article> findContent(Map<String, Object> map);

	int updateArticle(Article article);
	
	Article getArticle(Map<String, Object> map);
	
	
}
