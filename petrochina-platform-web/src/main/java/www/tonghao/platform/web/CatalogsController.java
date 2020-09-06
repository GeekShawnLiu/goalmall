package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.Catalogs;
import www.tonghao.service.common.service.CatalogsService;

@Api(value="catalogsController",description="品目Api")
@RestController
@RequestMapping("/catalogs")
public class CatalogsController {

	@Autowired
	private CatalogsService catalogsService;
	
	@ApiOperation(value="根据id获取所有的子节点",notes="根据id获取所有的子节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenById",method=RequestMethod.GET)
	public List<Catalogs> getChildrenById(long id){
		List<Catalogs> childrenById = catalogsService.getChildrenById(id);
		return childrenById;
	}
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Catalogs getById(Long id){
		Catalogs catalogs = catalogsService.selectByKey(id);
		return catalogs;
	}
	
	@ApiOperation(value="根据id获取下一级所有节点",notes="根据id获取下一级所有节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenByLevel",method={RequestMethod.GET,RequestMethod.POST})
	public List<Catalogs> getChildrenByLevel(Long id){
		Example example=new Example(Catalogs.class);
		Criteria createCriteria = example.createCriteria();
		if(id!=null){
			createCriteria.andEqualTo("parentId", id);
		}else{
			createCriteria.andEqualTo("treeDepth", 1);
		}
		List<Catalogs> selectByExample = catalogsService.selectByExample(example);
		return selectByExample;
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Catalogs catalogs){
		int saveOrUpdate = catalogsService.saveOrUpdate(catalogs);
		return ResultUtil.result(saveOrUpdate, 0);
	}
	
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = catalogsService.deleteCatalogs(id);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="推送产品目录到大数据id",notes="推送产品目录到大数据")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/sendBIgData",method=RequestMethod.DELETE)
	public Map<String, Object> sendBIgData(Long id){
		/*Catalogs selectByKey = catalogsService.selectByKey(id);
		int i=0;
		if(selectByKey!=null&&"false".equals(selectByKey.getIsParent())){
			Map<String, String> map=new HashMap<String, String>();
			map.put(""+selectByKey.getId(), selectByKey.getName());
			SyncCatalogsApi api=new SyncCatalogsApi("spe",map);
			SyncCatalogsRes result = api.getResult();
			if(result.getSuccess()){
				i=1;
			}
		}
		return ResultUtil.result(i, 0);*/
		return ResultUtil.result(0, 0);
	}
	
}
