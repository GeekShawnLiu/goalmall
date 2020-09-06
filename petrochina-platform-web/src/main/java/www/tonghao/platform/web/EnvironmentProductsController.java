package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.platform.entity.EnvironmentProducts;
import www.tonghao.platform.service.EnvironmentProductsService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: EnvironmentProductsController</p>  

* <p>Description: 环保清单控制类</p>  

* @author Yml  

* @date 2018年11月9日  

*/  
@Api(value="environmentProductsController",description="环保清单api")
@RestController
@RequestMapping("/environmentProductsController")
public class EnvironmentProductsController {

	@Autowired
	private EnvironmentProductsService environmentProductsService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="purItemName",value="品目",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="brandZhName",value="品牌",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="certCode",value="证书号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="goodsSpec",value="型号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="manufName",value="企业名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="period ",value="清单期数",required=false,dataType="string",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<EnvironmentProducts> getPage(@ModelAttribute PageBean page,String purItemName, String brandZhName,String certCode,String goodsSpec,String manufName,String period){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(EnvironmentProducts.class);
		Criteria createCriteria = example.createCriteria();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purItemName", purItemName);
		map.put("brandZhName", brandZhName);
		map.put("certCode", certCode);
		map.put("goodsSpec", goodsSpec);
		map.put("manufName", manufName);
		map.put("period", period);
		CriteriaLikeUtil.criteriaLike(createCriteria, map);
		List<EnvironmentProducts> list = environmentProductsService.selectByExample(example);
		return new PageInfo<EnvironmentProducts>(list);
	}
	
}
