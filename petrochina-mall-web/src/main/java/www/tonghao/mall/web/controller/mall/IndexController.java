package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.utils.PageBean;
import www.tonghao.mall.entity.CarouselPictrue;
import www.tonghao.mall.entity.Floor;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.mall.service.CarouselPictrueService;
import www.tonghao.mall.service.FloorService;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;


@Api(value="mallIndexController",description="商城首页api")
@RestController(value="mallIndexController")
@RequestMapping(value="/index")
@Scope("prototype")
public class IndexController {
	
	@Autowired
	private SuppliersService suppliersService;
	
	@Autowired
	private FloorService floorService;
	
	@Autowired
	private CarouselPictrueService carouselPictrueService;
	
	@ApiOperation(value="首页供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/suppliers")
    public PageInfo<Suppliers> suppliers(HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		PageHelper.startPage(1, 10000);
		Suppliers entity = new Suppliers();
		entity.setStatus(1);
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("isDelete", 0);
		queryfilter.put("protocolAllow", true);
		queryfilter.put("protocolType", 1);
		queryfilter.put("orderByCondition", "s.priority asc");
		//根据机构判断商品展示
		if(user != null && user.getType() != null){
			if(user.getType() == 1){
				queryfilter.put("orgId", user.getDepId());
			}else if(user.getType() == 4){
				queryfilter.put("supplierId", user.getTypeId());
			}
		}
		entity.setQueryfilter(queryfilter);
		List<Suppliers> list = suppliersService.findListByEntity(entity);
		return new PageInfo<Suppliers>(list);
	}
	
	@ApiOperation(value="楼层数据")	
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "floor")
    public List<Floor> floorData(@ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example = new Example(Floor.class);
		example.setOrderByClause(" sort asc");
		List<Floor> list = floorService.selectByExample(example);
		return floorService.processList(list);
	}
	
	@ApiOperation(value="楼层品目商品数据")	
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="cid",value="品目id",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "floor_catalog_products")
    public List<MallProducts> floorCatalogProduct(@ModelAttribute PageBean page ,@RequestParam(required=true,name="cid")Long cid, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		PageHelper.startPage(page.getPage(), page.getRows());
		return floorService.findCatalogProducts(cid, user);
	}
	
	@ApiOperation(value="楼层品目特惠商品数据")	
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="cids",value="品目ids",required=false,dataType="int[]",paramType="query"),
	})
	@GetMapping(value = "floor_catalog_benefitproducts")
    public List<MallProducts> floorCatalogBenefitProducts(@ModelAttribute PageBean page
    		,@RequestParam(value="cids[]",required=false) Long[] cids, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		PageHelper.startPage(page.getPage(), page.getRows());
		return floorService.findCatalogBenefitProducts(cids, user);
	}
	
	@ApiOperation(value="首页轮播图")
	@ApiImplicitParams({
		@ApiImplicitParam(name="type",value="类型：1:pc,2:手机,3:小程序      活动页轮播图 4:pc,5:手机,6:小程序",required=true,dataType="int",paramType="query"),
	})
//	@GetMapping(value = "/carouselPictrue")
//    public List<CarouselPictrue> carouselPictrue(HttpServletRequest request, Integer type){
//		Users user = UserUtil.getUser(request);
//		PageHelper.startPage(1, 100);
//		List<CarouselPictrue> list = carouselPictrueService.selectIndexPictrue(type, user);
//		return list;
//	}

	@GetMapping(value = "/carouselPictrue")
	public List<CarouselPictrue> carouselPictrue(HttpServletRequest request, Integer type){
		PageHelper.startPage(1, 100);
		Example example = new Example(CarouselPictrue.class);
		example.setOrderByClause(" rank asc");
		List<CarouselPictrue> list = carouselPictrueService.selectByExample(example);
		return list;
	}
	
	@ApiOperation(value="获取楼层商品数据")	
	@ApiImplicitParams({
		@ApiImplicitParam(name="fid",value="楼层id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="num",value="商品数",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "get_floor_products")
    public List<MallProducts> getFloorProduct(@RequestParam(required=true,name="fid")Long fid, Integer num, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		PageHelper.startPage(1, 10000);
		return floorService.getFloorProducts(fid, num, user);
	}
}
