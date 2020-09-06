package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.service.UpperLowerHistoryService;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductChangePrice;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductChangePriceService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: ProductsController</p>  

* <p>Description: 后台商品管理控制类</p>  

* @author Yml  

* @date 2018年11月16日  

*/  
@Api(value="productsController",description="后台商品管理api")
@RestController
@RequestMapping("/productsController")
public class ProductsController {
	
	@Autowired
	private ProductsService productsService;

	@Autowired
	private UpperLowerHistoryService upperLowerHistoryService;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	
	@Autowired
	private ProductChangePriceService productChangePriceService;
	
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
	public PageInfo<Products> list(@ModelAttribute PageBean page, String name, String sku,
			String catalogName, String brandName, Integer status, String model, String protocolName,
			String supplierName, Long catalogId, Long commoditiesId, Long supplierId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("sku", sku);
		map.put("catalogName", catalogName);
		map.put("brandName", brandName);
		map.put("status", status);
		map.put("model", model);
		map.put("protocolName", protocolName);
		map.put("supplierName", supplierName);
		map.put("supplierId", supplierId);
		if(catalogId != null){
			map.put("catalogId", catalogId);
			PlatformCatalogs selectByKey = platformCatalogsService.selectByKey(catalogId);
			if(selectByKey != null){
				map.put("tds", selectByKey.getTreeDepth()+"");
			}
		}
		map.put("commoditiesId", commoditiesId);
		PageHelper.startPage(page.getPage(), page.getRows());
		List<Products> list = productsService.find(map);
		return new PageInfo<Products>(list);
	}
	
	@ApiOperation(value="商品审核分页查询列表",notes="获取商品审核列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/listAudit",method=RequestMethod.GET)
	public PageInfo<Products> listAudit(@ModelAttribute PageBean page, String name, String sku,
			String catalogName, String brandName, Integer status, String model, String protocolName,
			String supplierName, Long catalogId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("sku", sku);
		map.put("catalogName", catalogName);
		map.put("brandName", brandName);
		map.put("status", status);
		map.put("model", model);
		map.put("protocolName", protocolName);
		map.put("notStaus", 0);
		map.put("supplierName", supplierName);
		if(catalogId != null){
			map.put("catalogId", catalogId);
			PlatformCatalogs selectByKey = platformCatalogsService.selectByKey(catalogId);
			if(selectByKey != null){
				map.put("tds", selectByKey.getTreeDepth()+"");
			}
		}
		PageHelper.startPage(page.getPage(), page.getRows());
		List<Products> list = productsService.find(map);
		return new PageInfo<Products>(list);
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
	public Products getById(Long id){
		Products product = productsService.getById(id);
		return product;
	}
	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = productsService.delete(id);
		return ResultUtil.resultMessage(delete, "");
	}
	
	/**  
	 * <p>Title: updateIsDelete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @param isDelete
	 * @return  
	 */  
	@ApiOperation(value="更新删除状态",notes="更新删除状态api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="isDelete",value="删除状态0：不删除 1：删除",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/updateIsDelete",method=RequestMethod.POST)
	public Map<String, Object> updateIsDelete(Long id, Integer isDelete){
		int result = productsService.updateIsDelete(id, isDelete);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: applyDown</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param id
	 * @return 
	 */
	@ApiOperation(value="下架",notes="下架api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/applyDown",method=RequestMethod.POST)
	public Map<String, Object> applyDown(Long id, HttpServletRequest request){
		int result = 0;
		Users user = UserUtil.getUser(request);
		if (user != null) {
			result = productsService.updateStatus(id, 4, user.getId());
		}
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: productAudit</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param upperLowerHistory
	 * @return 
	 */
	@ApiOperation(value="商品上架审核",notes="商品上架审核api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="productId",value="商品id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="status",value="审核状态 1：审核通过  2：审核不通过",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/productAudit",method=RequestMethod.POST)
	public Map<String, Object> productAudit(@RequestBody UpperLowerHistory upperLowerHistory, HttpServletRequest request){
		int result = 0;
		Users user = UserUtil.getUser(request);
		if (user != null) {
			//审核不通过时理由必填
			if (upperLowerHistory.getStatus() == 2 && StringUtils.isBlank(upperLowerHistory.getReason())) {
				return ResultUtil.resultMessage(0, "请填写审核不通过理由");
			}
			result = productsService.productAudit(upperLowerHistory, user.getId());
		}
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: upperLowerRecord</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param productId
	 * @param page
	 * @return 
	 */
	@ApiOperation(value="上下架记录分页查询列表",notes="获取上下架记录列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="productId",value="商品id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/upperLowerRecord",method=RequestMethod.GET)
	public PageInfo<UpperLowerHistory> upperLowerRecord(Long productId, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example = new Example(UpperLowerHistory.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("productId", productId);
		example.setOrderByClause("id desc");
		List<UpperLowerHistory> list = upperLowerHistoryService.selectByExample(example);
		return new PageInfo<UpperLowerHistory>(list);
	}

	/**  
	 * <p>Title: polymerization</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param id
	 * @return 
	 */
	@ApiOperation(value="聚合商品",notes="聚合商品api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="商品id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/polymerization",method=RequestMethod.POST)
	public Map<String, Object> polymerization(Long id){
		return productsService.polymerization(id);
	}
	
	
	/**  
	 * <p>Title: upperLowerRecord</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param productId
	 * @param page
	 * @return 
	 */
	@ApiOperation(value="调价历史表",notes="调价历史表api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="productId",value="商品id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/changePrice",method=RequestMethod.GET)
	public PageInfo<ProductChangePrice> changePrice(Long productId, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example = new Example(ProductChangePrice.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("productId", productId);
		example.setOrderByClause("id desc");
		 List<ProductChangePrice> list = productChangePriceService.selectByExample(example);
		return new PageInfo<ProductChangePrice>(list);
	}
}
