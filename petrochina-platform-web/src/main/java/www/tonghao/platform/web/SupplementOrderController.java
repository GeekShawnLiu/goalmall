package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.StringUtil;
import www.tonghao.platform.entity.SupplementOrder;
import www.tonghao.platform.service.SupplementOrderService;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * Description: 补单控制类
 * 
 * @version 2019年6月24日
 * @since JDK1.8
 */
@Api(value="supplementOrderController", description="补单api")
@RestController
@RequestMapping("/supplementOrder")
public class SupplementOrderController {

	@Autowired
	private SupplementOrderService supplementOrderService;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	
	@Autowired
	private ProductsService productsService;
	
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public PageInfo<SupplementOrder> list(@ModelAttribute PageBean page, String sn, String productName, String createdAt, String userName, String consigneeName, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Users user = UserUtil.getUser(request);
		if(user != null){
			if(1 == user.getType()){
				return null;
			}else if(4 == user.getType()){
				map.put("supplierId", user.getTypeId());
			}
		}
		PageHelper.startPage(page.getPage(), page.getRows());
		map.put("sn", sn);
		map.put("productName", productName);
		map.put("userName", userName);
		map.put("consigneeName", consigneeName);
		map.put("createdAt", createdAt);
		List<SupplementOrder> list = supplementOrderService.findByMap(map);
		return new PageInfo<SupplementOrder>(list);
	}
	
	@ApiOperation(value="保存",notes="保存补单信息api")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public Map<String, Object> save(@RequestBody SupplementOrder supplementOrder, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user == null){
			return ResultUtil.error("用户未登录");
		}
		return supplementOrderService.save(supplementOrder, user);
	}
	
	@ApiOperation(value="查看补单信息详情",notes="查看补单信息详情api")
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public SupplementOrder view(Long id, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null && (6 == user.getType() || 4 == user.getType())){
			return supplementOrderService.view(id);
		}
		return null;
	}
	
	@ApiOperation(value="查询商品数据",notes="查询商品数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/productList", method=RequestMethod.GET)
	public PageInfo<Products> productList(@ModelAttribute PageBean page, HttpServletRequest request, Long catalogId, String supplierName, String sku, String productName, BigDecimal leftPrice, BigDecimal rightPrice, String checkedIds){
		Users user = UserUtil.getUser(request);
		if(user != null){
			Map<String, Object> map = new HashMap<String, Object>();
			if(1 == user.getType()){
				return null;
			}else if(4 == user.getType()){
				map.put("supplierId", user.getTypeId());
			}
			map.put("name", productName);
			map.put("sku", sku);
			map.put("status", 3);
			if(catalogId != null){
				map.put("catalogId", catalogId);
				PlatformCatalogs selectByKey = platformCatalogsService.selectByKey(catalogId);
				if(selectByKey != null){
					map.put("tds", selectByKey.getTreeDepth()+"");
				}
			}
			map.put("supplierName", supplierName);
			map.put("leftPrice", leftPrice);
			map.put("rightPrice", rightPrice);
			if(StringUtil.strIsNotEmpty(checkedIds)){
				Long cIds[] = (Long[])ConvertUtils.convert(checkedIds.split(","), Long.class);
				map.put("checkedIds", Arrays.asList(cIds));
			}
			PageHelper.startPage(page.getPage(), page.getRows());
			List<Products> list = productsService.find(map);
			return new PageInfo<Products>(list);
		}
		return null;
	}
}
