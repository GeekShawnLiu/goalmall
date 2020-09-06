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
import www.tonghao.platform.service.EnergySavingProductsService;
import www.tonghao.service.common.entity.EnergySavingProducts;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**  

* <p>Title: EnergySavingProductsController</p>  

* <p>Description: </p>  

* @author Yml  

* @date 2018年11月9日  

*/  
@Api(value="energySavingProductsController",description="节能清单api")
@RestController
@RequestMapping("/energySavingProducts")
public class EnergySavingProductsController {

	@Autowired
	private EnergySavingProductsService energySavingProductsService;
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="item",value="品目",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="brand",value="品牌",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="certificateNo",value="证书号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="model",value="型号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="manufacturer",value="制造商",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="period ",value="期数",required=false,dataType="string",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<EnergySavingProducts> getPage(@ModelAttribute PageBean page,String itemName,String brand,String certNo,String model,String manufacturer,String period,String categoryId){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> maplike=new HashMap<String, Object>();
		maplike.put("itemName", itemName);
		maplike.put("brand", brand);
		maplike.put("certNo", certNo);
		maplike.put("model", model);
		maplike.put("manufacturer", manufacturer);
		maplike.put("period", period);
		maplike.put("categoryId", categoryId);
		List<EnergySavingProducts> list = energySavingProductsService.find(maplike);
		return new PageInfo<EnergySavingProducts>(list);
	}
	
	
}
