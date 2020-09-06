package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.PayWay;
import www.tonghao.mall.service.PayWayService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: PayWayController</p>  

* <p>Description: 支付方式管理控制类</p>  

* @author Yml  

* @date 2018年11月16日  

*/  
@Api(value="payWayController",description="支付方式管理api")
@RestController
@RequestMapping("/payWay")
public class PayWayController {

	@Autowired
	private PayWayService payWayService;
	
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<PayWay> list(@ModelAttribute PayWay payWay, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<String, Object>();
		List<PayWay> list = payWayService.find(map);
		return new PageInfo<PayWay>(list);
	}
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public PayWay getById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<PayWay> list = payWayService.find(map);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = payWayService.delete(id);
		return ResultUtil.resultMessage(delete, "");
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody PayWay payWay){
		int saveOrUpdate = payWayService.saveOrUpdate(payWay);
		return ResultUtil.resultMessage(saveOrUpdate, "");
	}
	
	@ApiOperation(value="获取所有支付方式",notes="获取所有支付方式api")
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public List<PayWay> getAll(){
		PageHelper.startPage(1, 1000);
		Map<String, Object> map = new HashMap<String, Object>();
		List<PayWay> list = payWayService.find(map);
		return list;
	}
}
