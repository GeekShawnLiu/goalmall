package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ArticleFile;

public interface ArticleFileMapper extends BaseMapper<ArticleFile> {
	
	List<ArticleFile> getByArticleId(@Param("articleId") Long articleId);
}