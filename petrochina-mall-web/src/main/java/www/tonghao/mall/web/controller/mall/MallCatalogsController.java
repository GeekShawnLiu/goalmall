package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.mall.entity.MallCatalogs;
import www.tonghao.mall.service.MallCatalogsService;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.service.PlatformCatalogsService;

import com.github.pagehelper.PageHelper;


@Api(value="mallCatalogsController",description="商城品目api")
@RestController(value="mallCatalogsController")
@RequestMapping("/mall_catalogs")
@Scope("singleton")
public class MallCatalogsController {
	
	@Autowired
	private MallCatalogsService mallCatalogsService;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	
	@ApiOperation(value="查询全部商品分类",notes="查询全部商品分类api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="count",value="一级品目数量",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="displayPlatform",value="展示平台pc h5",required=false,dataType="String",paramType="query"),
	})
	@GetMapping("/getall")
	public List<TreeNode> getAll(Integer count, String displayPlatform){
		PageHelper.startPage(1, 10000);
		return mallCatalogsService.getTreeList(count, displayPlatform);
	}
	
	@ApiOperation(value="查询全部竞价商品分类",notes="查询全部竞价商品分类api")
	@GetMapping("/getall_bidding")
	public List<TreeNode> getAllBidding(){
		return mallCatalogsService.getBiddingTreeList();
	}
	
	@ApiOperation(value="根据id获取下一级所有节点",notes="根据id获取下一级所有节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenByLevel",method={RequestMethod.GET,RequestMethod.POST})
	public List<PlatformCatalogs> getChildrenByLevel(Long id){
		Example example=new Example(PlatformCatalogs.class);
		Criteria createCriteria = example.createCriteria();
		if(id != null){
			createCriteria.andEqualTo("parentId", id);
		}else{
			createCriteria.andEqualTo("treeDepth", 1);
		}
		createCriteria.andEqualTo("isDelete", 0);
		example.setOrderByClause("priority");
		List<PlatformCatalogs> selectByExample = platformCatalogsService.selectByExample(example);
		return selectByExample;
	}
	
	@ApiOperation(value="查询所有有上架商品的一级品目",notes="查询所有有上架商品的一级品目api")
	@GetMapping("/getCatalogsByProducts")
	public List<MallCatalogs> getCatalogsByProducts(){
		PageHelper.startPage(1, 10000);
		return mallCatalogsService.selectMallCatalogsByProducts();
	}
	
	@ApiOperation(value="查询商城一级品目",notes="查询商城一级品目api")
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public List getPage(){
		Example example=new Example(MallCatalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("treeDepth", 0);
		example.setOrderByClause(" priority asc");
		List<MallCatalogs> mallCatalogsList = mallCatalogsService.selectByExample(example);
		return mallCatalogsList;
	}
	@ApiOperation(value="查询单个商城一级品目",notes="查询单个商城一级品目api")
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public MallCatalogs getById(Long id){
		MallCatalogs selectByKey = mallCatalogsService.selectByKey(id);
		return selectByKey;
	}
	
	@ApiOperation(value="修改商城品目",notes="修改商城品目api")
	@RequestMapping(value="/updateMallCatalog",method=RequestMethod.POST)
	public Map<String, Object> updateMallCatalog(@RequestBody MallCatalogs mallCatalogs){
		int updateNotNull = mallCatalogsService.updateNotNull(mallCatalogs);
		return ResultUtil.resultMessage(updateNotNull, "");
	}
	
}
