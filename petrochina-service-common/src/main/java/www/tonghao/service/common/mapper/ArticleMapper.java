package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Article;

public interface ArticleMapper extends BaseMapper<Article> {

	Article getById(@Param("id") Long id);

	List<Article> find(Map<String, Object> map);
	
	List<Article> findNotify(Map<String, Object> map);
	
	List<Article> findArticle(Map<String, Object> map);
	
	List<Article> findContent(Map<String, Object> map);
	
	Article getArticle(Map<String, Object> map);
}