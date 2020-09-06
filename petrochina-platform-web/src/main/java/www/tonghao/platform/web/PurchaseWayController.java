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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.platform.entity.PurchaseWay;
import www.tonghao.platform.service.PurchaseWayService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="purchaseWayController",description="采购方式Api")
@RestController
@RequestMapping("/purchaseWay")
public class PurchaseWayController {

	@Autowired
	private PurchaseWayService purchaseWayService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="code",value="代码",required=false,dataType="string",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<PurchaseWay> getPage(@ModelAttribute PageBean bean,String name,String code){
		PageHelper.startPage(bean.getPage(), bean.getRows());
		Example example=new Example(PurchaseWay.class);
		Criteria createCriteria = example.createCriteria();
		Map<String, Object> mapLike=new HashMap<String, Object>();
		mapLike.put("name", name);
		mapLike.put("code", code);
		CriteriaLikeUtil.criteriaLike(createCriteria, mapLike);
		List<PurchaseWay> listPurchaseWays = purchaseWayService.selectByExample(example);
		return new PageInfo<PurchaseWay>(listPurchaseWays);
	}
	
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public PurchaseWay getById(Long id){
		PurchaseWay purchaseWay = purchaseWayService.selectByKey(id);
		return purchaseWay;
	}
	
	
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = purchaseWayService.delete(id);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody PurchaseWay purchaseWay){
		int saveOrUpdate = purchaseWayService.saveOrUpdate(purchaseWay);
		return ResultUtil.result(saveOrUpdate, 0);
	}
}
