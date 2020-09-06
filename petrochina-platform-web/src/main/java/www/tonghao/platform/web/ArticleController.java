package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.Article;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.ArticleService;
import www.tonghao.service.common.service.FileService;
import www.tonghao.utils.UserUtil;

import com.beust.jcommander.internal.Maps;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: ArticleController</p>  

* <p>Description: </p>  

* @author Yml  

* @date 2018年11月2日  

*/  
@Api(value="articleController",description="公告管理api")
@RestController
@RequestMapping("/articleController")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private FileService fileService;
	
	/**  
	 * <p>Title: list</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param article
	 * @param page
	 * @return  
	 */  
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<Article> list(@ModelAttribute Article article, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", article.getTitle());
		map.put("status", article.getStatus());
		map.put("articleTypeId", article.getArticleTypeId());
		map.put("businessType", article.getBusinessType());	
		map.put("businessStatus", article.getBusinessStatus());
		List<Article> list = articleService.find(map);
		return new PageInfo<Article>(list);
	}
	
	/**  
	 * <p>Title: save</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param article
	 * @return  
	 */  
	@ApiOperation(value="添加数据",notes="添加数据api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String,Object> save(@RequestBody Article article, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if (user != null) {
			if (StringUtils.isBlank(article.getContent())) {
				return ResultUtil.resultMessage(0, "请填写正文信息");
			}
			article.setUserId(user.getId());
			article.setAuthor(user.getRealName());
			int result = articleService.saveArticle(article);
			return ResultUtil.resultMessage(result, "");
		}else {
			return ResultUtil.resultMessage(0, "未检测到登录");
		}
	}
	
	/**  
	 * <p>Title: update</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param article
	 * @return  
	 */  
	@ApiOperation(value="更新数据",notes="更新数据api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String,Object> update(@RequestBody Article article){
		article.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		int result = articleService.updateArticle(article);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="删除数据",notes="删除数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int result = articleService.delete(id);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: getById</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Article getById(Long id){
		Article article = articleService.getById(id);
		return article;
	}
	
	/**
	 * 根据业务类型及业务ID查看公告
	 */
	@ApiOperation(value="查看业务公告",notes="查看业务公告Api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="businessId",value="业务ID",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="businesStatus",value="业务状态",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="businessType",value="业务类型",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getArticle",method=RequestMethod.GET)
	public Article getArticle(@ModelAttribute Article article){
		Map<String, Object> map=Maps.newHashMap();
		if (article.getBusinessId() ==null || article.getBusinessStatus() == null || article.getBusinessType() ==null) {
			return null;
		}
		map.put("businessId", article.getBusinessId());
		map.put("businessStatus", article.getBusinessStatus());
		map.put("businessType", article.getBusinessType());
		return articleService.getArticle(map);
	}
	
	
	
}
