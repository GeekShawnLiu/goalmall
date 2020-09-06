package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.service.UpperLowerHistoryService;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.UpperLowerHistory;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="upperLowerHistoryController",description="上下架记录接口")
@RestController
@RequestMapping("/upperLowerHistory")
public class UpperLowerHistoryController {

	@Autowired
	private UpperLowerHistoryService upperLowerHistoryService;
	
	/**  
	 * <p>Title: list</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param products
	 * @param page
	 * @return  
	 */  
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<UpperLowerHistory> list(@ModelAttribute UpperLowerHistory upperLowerHistory, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example = new Example(UpperLowerHistory.class);
		example.setOrderByClause("created_at desc");
		List<UpperLowerHistory> list = upperLowerHistoryService.selectByExample(example);
		return new PageInfo<UpperLowerHistory>(list);
	}
	
	@ApiOperation(value="上下架",notes="上下架api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Products product){
		return ResultUtil.resultMessage(0, "");
	}
	
}
