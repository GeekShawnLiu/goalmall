package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.enums.ArticleTypePosition;
import www.tonghao.common.utils.FileUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.Article;
import www.tonghao.service.common.entity.ArticleFile;
import www.tonghao.service.common.entity.ArticleType;
import www.tonghao.service.common.service.ArticleFileService;
import www.tonghao.service.common.service.ArticleService;
import www.tonghao.service.common.service.ArticleTypeService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Api(value="mallArticleController",description="商城文章api")
@RestController(value="mallArticleController")
@RequestMapping(value="/m_article")
public class MallArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private ArticleFileService articleFileService;
	
	@ApiOperation(value="文章分页列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="tid",value="栏目ID",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/page")
    public PageInfo<Article> page(@ModelAttribute PageBean page,@RequestParam(required=true)Long tid){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example = new Example(Article.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("articleTypeId",tid);
		criteria.andEqualTo("status","2");
		example.setOrderByClause(" created_at desc");
		List<Article> list = articleService.selectByExample(example);
		return new PageInfo<Article>(list);
	}
	
	@ApiOperation(value="文章详情")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="文章ID",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/detail/{id}")
    public Article detail(@PathVariable Long id){
		//return articleService.selectByKey(id);
		return articleService.getById(id);
	}
	
	@ApiOperation(value="商城栏目导航")
	@GetMapping(value = "/type_nav")
	public List<ArticleType> articleNav(){
		Example example = new Example(ArticleType.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", "1");
		criteria.andEqualTo("position", ArticleTypePosition.mall_nav);
		example.setOrderByClause(" priority asc ");
		return articleTypeService.selectByExample(example);
	}
	
	/**
	 * 栏目面包屑
	 * @param articleTypeId
	 * @return
	 */
	@ApiOperation(value="栏目面包屑")
	@ApiImplicitParams({
		@ApiImplicitParam(name="articleTypeId",value="栏目ID",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/type_nodes")
	public List<ArticleType> articleTypeNodeList(Long articleTypeId){
		return articleTypeService.getParentById(articleTypeId);
	}
	
	@ApiOperation(value="文章刷新点击量")
	@PostMapping(value = "/{aid}/refreshHits")
	public Map<String, Object> refreshHits(@PathVariable Long aid){
		Article article = articleService.selectByKey(aid);
		if(article!=null){
			int hits = article.getHits()+1;
			article.setHits(hits);
			articleService.updateNotNull(article);
			return ResultUtil.success("");
		}
		return ResultUtil.error("");
	}
	
	
	@ApiOperation(value="下载公告附件")
	@RequestMapping(value="/downloadFile", method=RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, Long articleFileId) throws IOException{
		ArticleFile articleFile = articleFileService.selectByKey(articleFileId);
		if(articleFile != null && articleFile.getPath() != null){
			String path = articleFile.getPath();
			URL url = new URL(path);
			InputStream in = url.openStream();
			FileUtilEx.downloadFile(in, response, articleFile.getFileName());
		}
	}
}
