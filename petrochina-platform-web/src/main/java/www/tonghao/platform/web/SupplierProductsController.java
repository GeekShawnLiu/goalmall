package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.service.UpperLowerHistoryService;
import www.tonghao.service.common.entity.EmallCatalogs;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.EmallCatalogsService;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.utils.UserUtil;

import com.beust.jcommander.internal.Maps;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: SupplierProductsController</p>  

* <p>Description: 供应商商品管理控制类</p>  

* @author Yml  

* @date 2018年11月16日  

*/  
@PropertySource({"classpath:application.properties"})
@Api(value="supplierProductsController",description="供应商商品管理api")
@RestController
@RequestMapping("/supplierProductsController")
public class SupplierProductsController {
	
	private static Log log  = LogFactory.getLog(SupplierProductsController.class);
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService; 
	
	/*@Value("${bigdata.mySiteCode}")*/
	private String mySiteCode;
	
	@Autowired
	private SuppliersService suppliersService;
	
	@Autowired
	private UpperLowerHistoryService upperLowerHistoryService;
	
	@Autowired
	private EmallCatalogsService emallCatalogsService;


	@ApiOperation(value="分页查询普通商品列表",notes="获取普通商品列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<Products> list(@ModelAttribute PageBean page, String name, String sku,
			String catalogName, String brandName, Integer status, String model, String protocolName,
			Long catalogId, String supplierName, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if (user != null && user.getType() != null && user.getTypeId() != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("supplierId", user.getTypeId());
			map.put("is_car", "false");
			map.put("name", name);
			map.put("sku", sku);
			map.put("catalogName", catalogName);
			map.put("brandName", brandName);
			map.put("status", status);
			map.put("model", model);
			map.put("protocolName", protocolName);
			if(catalogId != null){
				map.put("catalogId", catalogId);
				PlatformCatalogs selectByKey = platformCatalogsService.selectByKey(catalogId);
				if(selectByKey != null){
					map.put("tds", selectByKey.getTreeDepth()+"");
				}
			}
			map.put("supplierName", supplierName);
			PageHelper.startPage(page.getPage(), page.getRows());
			List<Products> list = productsService.find(map);
			return new PageInfo<Products>(list);
		}
		return null;
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
	 * <p>Title: saveOrUpdate</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param product
	 * @return  
	 */  
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Products product, HttpServletRequest request){
		//部分校验
		if(!StringUtils.isNotBlank(product.getDetail())){
			return ResultUtil.resultMessage(0, "图文描述不能为空");
		}
		
		Users user = UserUtil.getUser(request);
		if (user.getType() != null  && user.getTypeId() != null) {
			product.setUserId(user.getId());//设置当前操作用户id
			Suppliers supplier = suppliersService.selectByKey(user.getTypeId());
			if (supplier != null) {
				log.info(supplier.getName()+":保存商品数据--start--------------------");
				product.setSupplierId(supplier.getId());//设置供应商id
				product.setSupplierName(supplier.getName());//设置供应商名称
				//product.setProtocolPrice(product.getPrice());
//				product.setPrice(product.getProtocolPrice());
				//价格对比
				BigDecimal price = product.getPrice();
				//第三方供应商新增商品  协议价=售价
//				product.setProtocolPrice(price);
				log.info("price:"+product.getPrice());
				//2020-09-07 注释掉商品保存价格限制
//				if (price != null) {
//					//对比平台品目通用资源配置价格
//					PlatformCatalogs platformCatalog = platformCatalogsService.selectByKey(product.getCatalogId());
//					if (platformCatalog != null && platformCatalog.getNormalPrice() != null) {
//						if (price.compareTo(platformCatalog.getNormalPrice()) == 1) {
//							return ResultUtil.resultMessage(0, "协议价不能高于平台品目通用资源配置价格");
//						}
//					}
//				}
				HashMap<String, Object> map = productsService.saveOrUpdate(product);
				int status = 0;
				status = (int) map.get("status");
				String msg = (String) map.get("message");
				log.info("商品保存结束-"+ map.toString() +"-end------------------------------------");
				//注释掉商品聚合
//				if (status > 0) {
//					//异步执行商品聚合
//					productsService.asynPolymerization(product.getId());
//				}
				return ResultUtil.resultMessage(status, msg);
			}
		}
		return ResultUtil.resultMessage(0, "操作限制！");
	}
	
	
	/**  
	 * <p>Title: updateParameter</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param product
	 * @return 
	 */
	@ApiOperation(value="商品参数维护",notes="商品参数维护api")
	@RequestMapping(value="/updateParameter",method=RequestMethod.POST)
	public Map<String, Object> updateParameter(@RequestBody Products product, HttpServletRequest request){
		int result = 0;
		Users user = UserUtil.getUser(request);
		if (user.getType() != null && user.getType() == 4 && user.getTypeId() != null) {
			product.setUserId(user.getId());//设置当前操作用户id
			Suppliers supplier = suppliersService.selectByKey(user.getTypeId());
			if (supplier != null) {
				Map<String, Object> map = productsService.updateParameter(product);
				return map;
			}else {
				return ResultUtil.resultMessage(result, "供应商异常");
			}
		}else {
			return ResultUtil.resultMessage(result, "操作限制！");
		}
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
	 * <p>Title: applyUpper</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */ 
	@ApiOperation(value="申请上架",notes="申请上架api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="商品id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/applyUpper",method=RequestMethod.POST)
	public Map<String, Object> applyUpper(Long id, HttpServletRequest request){
		int result = 0;
		Users user = UserUtil.getUser(request);
		if (user != null) {
			Map<String, Object> map = Maps.newHashMap();
			map = productsService.applyUpper(id, mySiteCode, user.getId());
			return map;
		}else {
			return ResultUtil.resultMessage(result, "未登录");
		}
	}

	/**
	 * 商品上架
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/applyUp",method=RequestMethod.POST)
	public Map<String, Object> applyUp(Long id, HttpServletRequest request){
		int result = 0;
		Users user = UserUtil.getUser(request);
		if (user != null) {
			result = productsService.updateStatus(id, 3, user.getId());
		}else {
			return ResultUtil.resultMessage(result, "未登录");
		}
		return ResultUtil.resultMessage(result, "");
	}

	/**  
	 * <p>Title: applyDown</p>  
	 * <p>Description: </p>  
	 * @author Yml  
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
		}else {
			return ResultUtil.resultMessage(result, "未登录");
		}
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: upperLowerRecord</p>  
	 * <p>Description: </p>  
	 * @author Yml  
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
	 * <p>Title: myAgentProducts</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param page
	 * @param name
	 * @param sku
	 * @param catalogName
	 * @param brandName
	 * @param status
	 * @param model
	 * @param protocolName
	 * @return 
	 */
	@ApiOperation(value="我代理的商品查询列表",notes="获取我代理的商品数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/myAgentProducts",method=RequestMethod.GET)
	public PageInfo<Products> myAgentProducts(@ModelAttribute PageBean page, String name, String sku,
			String catalogName, String brandName, Integer status, String model, String protocolName,
			Long catalogId, String supplierName, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if (user.getType() != null && user.getType() == 4 && user.getTypeId() != null) {
			PageHelper.startPage(page.getPage(), page.getRows());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("supplierId", user.getTypeId());
			map.put("name", name);
			map.put("sku", sku);
			map.put("catalogName", catalogName);
			map.put("brandName", brandName);
			map.put("status", status);
			map.put("model", model);
			map.put("protocolName", protocolName);
			map.put("catalogId", catalogId);
			map.put("supplierName", supplierName);
			List<Products> list = productsService.findMyAgentProducts(map);
			return new PageInfo<Products>(list);
		}
		return null;
	}
	
	/**  
	 * <p>Title: theDaylist</p>
	 * <p>Description: 查询当天更新的商品列表</p>  
	 * @author YML 
	 * @param page
	 * @param name
	 * @param sku
	 * @param catalogName
	 * @param brandName
	 * @param status
	 * @param model
	 * @param protocolName
	 * @param catalogId
	 * @return 
	 */
	@ApiOperation(value="查询当天更新的商品列表",notes="查询当天更新的商品列表api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierId",value="供应商id",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/theDaylist",method=RequestMethod.GET)
	public PageInfo<Products> theDaylist(@ModelAttribute PageBean page, String name, String sku,
			String catalogName, String brandName, Integer status, String model, String protocolName, Long supplierId,
			Long catalogId, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		Suppliers supplier = null;
		//供应商
		if(user != null && user.getType() != null && user.getType() == 4 && user.getTypeId() != null){
			supplier = suppliersService.selectByKey(user.getTypeId());
		}
		//运营单位
		if(user != null && user.getType() != null && user.getType() == 6 && supplierId != null){
			supplier = suppliersService.selectByKey(supplierId);
		}
		if(supplier != null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("supplierId", supplier.getId());
			map.put("compareStartDate", DateUtilEx.dateFormat(new Date()));
			map.put("compareEndDate", DateUtilEx.timeFormat(new Date()));
			map.put("name", name);
			map.put("sku", sku);
			map.put("catalogName", catalogName);
			map.put("brandName", brandName);
			map.put("status", status);
			map.put("model", model);
			map.put("protocolName", protocolName);
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
		return null;
	}
	
	/**  
	 * <p>Title: updateEmallProductsBySku</p>
	 * <p>Description: 根据商品sku更新或保存商品</p>  
	 * @author YML 
	 * @param sku
	 * @return 
	 */
	@ApiOperation(value="根据商品sku更新或保存商品",notes="根据商品sku更新或保存商品api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="sku",value="sku",required=true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="supplierId",value="供应商id",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/updateEmallProductsBySku",method=RequestMethod.POST)
	public Map<String, Object> updateEmallProductsBySku(String sku, HttpServletRequest request, Long supplierId){
		Users user = UserUtil.getUser(request);
		int result = 0;
		Suppliers supplier = null;
		//供应商
		if(user != null && user.getType() != null && user.getType() == 4){
			supplier = suppliersService.selectByKey(user.getTypeId());
		}
		//运营单位
		if(user != null && user.getType() != null && user.getType() == 6){
			if(supplierId != null){
				supplier = suppliersService.selectByKey(supplierId);
			}else{
				return ResultUtil.resultMessage(result, "参数错误");
			}
		}
		if(supplier != null){
			if("jd".equals(supplier.getCode())){
				Example example=new Example(EmallCatalogs.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("emallCatalogsId", sku.split("\\|")[0].trim());
				createCriteria.andEqualTo("emallCode", "jd");
				List<EmallCatalogs> selectByExample = emallCatalogsService.selectByExample(example);
				if(!CollectionUtils.isEmpty(selectByExample)){
					EmallCatalogs emallCatalogs = selectByExample.get(0);
					return productsService.updateEmallProducts(supplier, sku.split("\\|")[1].trim(),emallCatalogs.getCatalogsId(),true);
				}else{
					return ResultUtil.resultMessage(result, "未找到该品目映射");
				}
			}else{
				return productsService.updateEmallProducts(supplier, sku,0L,false);
			}
		}else{
			return ResultUtil.resultMessage(result, "未登录或当前账号信息异常");
		}
	}
	
	@ApiOperation(value="根据品目更新或保存商品",notes="根据品目更新或保存商品api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="catalogId",value="平台品目id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/updateEmallProductsByCatalog",method=RequestMethod.POST)
	public Map<String, Object> updateEmallProductsByCatalog(Long catalogId, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		int result = 0;
		if (user != null && user.getType() != null && user.getType() == 4 && user.getTypeId() != null) {
			Suppliers supplier = suppliersService.selectByKey(user.getTypeId());
			return productsService.updateEmallProductsByCatalog(supplier, catalogId);
		}else {
			log.warn("未登录或当前账号信息异常");
			return ResultUtil.resultMessage(result, "未登录或当前账号信息异常");
		}
	}
	
	@GetMapping("/getUser")
	public Map<String, Object> getUser(HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null){
			Long supplierId = user.getTypeId();
			if(supplierId != null){
				Suppliers supplier = suppliersService.selectByKey(supplierId);
				if(supplier != null && "jd".equals(supplier.getCode())){
					return ResultUtil.resultMessage(1, "true");
				}
			}
		}
		return ResultUtil.resultMessage(0, "false");
	}
	
	/**
	 * 
	 * Description: 获取可用供应商
	 * 
	 * @data 2019年5月31日
	 * @param 
	 * @return
	 */
	@GetMapping("/getAvailableSuppliers")
	public List<Suppliers> getAvailableSuppliers(){
		Suppliers entity = new Suppliers();
		entity.setStatus(1);
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("isDelete", 0);
		queryfilter.put("protocolAllow", true);
		queryfilter.put("protocolType", 1);
		queryfilter.put("orderByCondition", "s.priority asc");
		entity.setQueryfilter(queryfilter);
		List<Suppliers> list = suppliersService.findListByEntity(entity);
		return list;
	}


}
