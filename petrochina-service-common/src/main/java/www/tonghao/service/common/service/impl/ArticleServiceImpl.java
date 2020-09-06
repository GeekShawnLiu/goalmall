package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Article;
import www.tonghao.service.common.entity.ArticleFile;
import www.tonghao.service.common.mapper.ArticleFileMapper;
import www.tonghao.service.common.mapper.ArticleMapper;
import www.tonghao.service.common.service.ArticleService;

@Service("articleService")
@Transactional
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private ArticleFileMapper articleFileMapper;
	
	@Override
	public int saveArticle(Article article) {
		article.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		if (article.getStatus() != null && "2".equals(article.getStatus())) {
			article.setPublishAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		}
		int result = saveSelective(article);
		if (result > 0 && !CollectionUtils.isEmpty(article.getArticleFile())) {
			//保存附件
			List<ArticleFile> articleFiles = article.getArticleFile();
			for (ArticleFile articleFile : articleFiles) {
				articleFile.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				articleFile.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				articleFile.setArticleId(article.getId());
				articleFile.setIsDelete(0);
				articleFileMapper.insertSelective(articleFile);
			}
		}
		return result;
	}

	@Override
	public Article getById(Long id) {
		return articleMapper.getById(id);
	}

	@Override
	public List<Article> find(Map<String, Object> map) {
		return articleMapper.find(map);
	}

	@Override
	public List<Article> findNotify(Map<String, Object> map) {
		return articleMapper.findNotify(map);
	}

	@Override
	public List<Article> findArticle(Map<String, Object> map) {
		return articleMapper.findArticle(map);
	}

	@Override
	public int updateArticle(Article article) {
		article.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		if (article.getStatus() != null && "2".equals(article.getStatus())) {
			article.setPublishAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		}
		int result = updateNotNull(article);
		if (result > 0 && !CollectionUtils.isEmpty(article.getArticleFile())) {
			List<ArticleFile> articleFiles = article.getArticleFile();
			for (ArticleFile articleFile : articleFiles) {
				if (articleFile.getId() != null) {
					if (articleFile.getIsDelete() != null && articleFile.getIsDelete() == 1) {
						articleFile.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
						articleFileMapper.updateByPrimaryKeySelective(articleFile);
					}
				}else {
					articleFile.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					articleFile.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					articleFile.setArticleId(article.getId());
					articleFile.setIsDelete(0);
					articleFileMapper.insertSelective(articleFile);
				}
			}
		}
		return result;
	}

	@Override
	public List<Article> findContent(Map<String, Object> map) {
		return articleMapper.findContent(map);
	}

	@Override
	public Article getArticle(Map<String, Object> map) {
		return articleMapper.getArticle(map);
	}

	
}
