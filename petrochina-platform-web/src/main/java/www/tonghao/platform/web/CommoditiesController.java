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

import www.tonghao.common.utils.PageBean;
import www.tonghao.platform.service.CommoditiesService;
import www.tonghao.service.common.entity.Commodities;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.service.ProductsService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  
* <p>Title: CommoditiesController</p>  
* <p>Description: 聚合商品管理</p>  
* @author YML  
* @date 2019年1月24日  
*/ 
@Api(value="CommoditiesController",description="聚合商品管理api")
@RestController
@RequestMapping("/CommoditiesController")
public class CommoditiesController {

	@Autowired
	private CommoditiesService commoditiesService;
	
	@Autowired
	private ProductsService productsService;
	
	/**  
	 * <p>Title: list</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param page
	 * @param catalogId
	 * @param model
	 * @return 
	 */
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="catalogId",value="平台品目id",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="model",value="型号",required=false,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<Commodities> list(@ModelAttribute PageBean page, Long catalogId, String model){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("catalogId", catalogId);
		map.put("model", model);
		List<Commodities> list = commoditiesService.findCond(map);
		return new PageInfo<Commodities>(list);
	}
	
	/**  
	 * <p>Title: findByCommoditiesId</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param page
	 * @param ptype
	 * @param name
	 * @param sku
	 * @param catalogName
	 * @param brandName
	 * @param status
	 * @param model
	 * @param protocolName
	 * @param supplierName
	 * @param catalogId
	 * @param commoditiesId
	 * @return 
	 */
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="ptype",value="商品类型：mall(电商),others(协议)",required=false,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/findByCommoditiesId",method=RequestMethod.GET)
	public PageInfo<Products> findByCommoditiesId(@ModelAttribute PageBean page, String ptype,  String name, String sku,
			String catalogName, String brandName, Integer status, String model, String protocolName,
			String supplierName, Long catalogId, Long commoditiesId){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commoditiesId", commoditiesId);
		map.put("ptype", ptype);
		map.put("name", name);
		map.put("sku", sku);
		map.put("catalogName", catalogName);
		map.put("brandName", brandName);
		map.put("status", status);
		map.put("model", model);
		map.put("protocolName", protocolName);
		map.put("supplierName", supplierName);
		map.put("catalogId", catalogId);
		List<Products> list = productsService.findByCommoditiesId(map);
		return new PageInfo<Products>(list);
	}
	
	
}
