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
import www.tonghao.mall.entity.DeliveryWay;
import www.tonghao.mall.service.DeliveryWayService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: DeliveryWayController</p>  

* <p>Description: "配送管理控制类</p>  

* @author Yml  

* @date 2018年11月16日  

*/  
@Api(value="deliveryWayController",description="配送管理api")
@RestController
@RequestMapping("/deliveryWay")
public class DeliveryWayController {
	
	@Autowired
	private DeliveryWayService deliveryWayService;
	
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<DeliveryWay> list(@ModelAttribute DeliveryWay deliveryWay, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<String, Object>();
		List<DeliveryWay> list = deliveryWayService.find(map);
		return new PageInfo<DeliveryWay>(list);
	}
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public DeliveryWay getById(Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<DeliveryWay> list = deliveryWayService.find(map);
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
		int delete = deliveryWayService.delete(id);
		return ResultUtil.resultMessage(delete, "");
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody DeliveryWay deliveryWay){
		int saveOrUpdate = deliveryWayService.saveOrUpdate(deliveryWay);
		return ResultUtil.resultMessage(saveOrUpdate, "");
	}
	
	
}
