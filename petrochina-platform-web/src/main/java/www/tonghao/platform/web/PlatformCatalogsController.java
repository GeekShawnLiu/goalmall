package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.FileUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.service.common.entity.CatalogParameter;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.service.CatalogParameterService;
import www.tonghao.service.common.service.PlatformCatalogsService;

/**  

* <p>Title: PlatformCatalogsController</p>  

* <p>Description: 平台品目管理控制类</p>  

* @author Yml  

* @date 2018年11月14日  

*/  
@Api(value="platformCatalogsController",description="平台品目管理api")
@RestController
@RequestMapping("/platformCatalogsController")
public class PlatformCatalogsController {

	@Autowired
	private PlatformCatalogsService platformCatalogsService;

	@Autowired
	private CatalogParameterService catalogParameterService;
	
	@ApiOperation(value="根据id获取所有的子节点",notes="根据id获取所有的子节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenById",method=RequestMethod.GET)
	public List<PlatformCatalogs> getChildrenById(long id){
		List<PlatformCatalogs> childrenById = platformCatalogsService.getChildrenById(id);
		return childrenById;
	}
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public PlatformCatalogs getById(Long id){
		PlatformCatalogs catalogs = platformCatalogsService.findRelationById(id);
		List<CatalogParameter> parameterList = catalogParameterService.getByPlatformCatalogId(id);
		catalogs.setParametersList(parameterList);
		return catalogs;
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
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody PlatformCatalogs catalogs){
		if (catalogs.getId() != null && catalogs.getParentId() != null && catalogs.getId() == catalogs.getParentId()) {
			return ResultUtil.resultMessage(0, "数据异常");
		}
		//同级节点名称重复不可第二次添加
		if (catalogs.getParentId() != null) {
			int count = platformCatalogsService.validateName(catalogs.getParentId(), catalogs.getId(), catalogs.getName());
			if (count > 0) {
				return ResultUtil.resultMessage(0, "同级节点名称不可重复");
			}
		}
		int saveOrUpdate = platformCatalogsService.saveOrUpdate(catalogs);
		return ResultUtil.resultMessage(saveOrUpdate, "");
	}
	
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = platformCatalogsService.deleteCatalogs(id);
		return ResultUtil.resultMessage(delete, "");
	}
	
	@ApiOperation(value="更新删除状态",notes="更新删除状态api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="isDelete",value="删除状态",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/updateDelete",method=RequestMethod.POST)
	public Map<String, Object> updateDelete(Long id, Integer isDelete){
		int result = platformCatalogsService.updateDelete(id, isDelete);
		return ResultUtil.resultMessage(result, "");
	}
	
	@ApiOperation(value="获取数据排除自身及子节点",notes="获取数据排除自身及子节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getSelectData",method=RequestMethod.GET)
	public List<PlatformCatalogs> getSelectData(Long id){
		List<PlatformCatalogs> permissions = platformCatalogsService.getSelectData(id);
		return permissions;
	}
	
	@ApiOperation(value="是否节能品目",notes="是否节能品目api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="平台品目id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/isEnergySavingCatalog",method=RequestMethod.GET)
	public Map<String, Object> isEnergySavingCatalog(Long id){
		PlatformCatalogs platformCatalog = platformCatalogsService.findRelationById(id);
		//财政品目标识 1 节能 2环保
		if (platformCatalog != null && platformCatalog.getCatalog() != null 
				&& StringUtils.isNotBlank(platformCatalog.getCatalog().getTypes())
				&& platformCatalog.getCatalog().getTypes().contains("1")) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("catalog", platformCatalog.getCatalog());
			return ResultUtil.resultMessage(HttpResponseCode.OK, "true", data);
		}
		return ResultUtil.resultMessage(HttpResponseCode.OK, "false", null);
	}
	
	/**  
	 * <p>Title: getPriceMonitorCatalog</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param id
	 * @return 
	 */
	@ApiOperation(value="获取价格监测品目",notes="获取价格监测品目api")
	@RequestMapping(value="/getPriceMonitorCatalog",method=RequestMethod.GET)
	public List<PlatformCatalogs> getPriceMonitorCatalog(){
		Example example = new Example(PlatformCatalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isPriceMonitor", 1);
		createCriteria.andEqualTo("isParent", "false");
		createCriteria.andEqualTo("isDelete", 0);
		List<PlatformCatalogs> catalogs = platformCatalogsService.selectByExample(example);
		return catalogs;
	}
	@ApiOperation(value="根据id获取下一级所有节点",notes="根据id获取下一级所有节点api")
	@RequestMapping(value="/getPlatformCatalogsAll",method={RequestMethod.GET,RequestMethod.POST})
	public List<PlatformCatalogs> getPlatformCatalogsAll(Long id){
		Example example=new Example(PlatformCatalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		example.setOrderByClause("priority");
		List<PlatformCatalogs> selectByExample = platformCatalogsService.selectByExample(example);
		return selectByExample;
	}
	
	/**
	 * @Description:获取货物所有品目
	 * @date 2019年4月29日
	 */
	@ApiOperation(value="获取货物所有品目")
	@RequestMapping(value="/getGoodsPlatformCatalogsAll",method={RequestMethod.GET})
	public List<PlatformCatalogs> getGoodsPlatformCatalogsAll(){
		List<PlatformCatalogs> selectByTreeNames = platformCatalogsService.selectByTreeNamesOrCode(null,"A");
		return selectByTreeNames;
	}
	
	@RequestMapping(value="/exec",method={RequestMethod.GET})
	public void exec() {
		long time1=System.currentTimeMillis();
		File excelFile = new File("F:\\品目.xlsx");
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(FileUtilEx.openInputStream(excelFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = workbook.getSheetAt(0);
		int i=1;
		Set<String> set=new HashSet<>();
		while ( i <= sheet.getLastRowNum() ) {
			XSSFRow row = sheet.getRow(i);
			if (row == null) {
				break;
			}
			 String stringCellValue1 = row.getCell(1).getStringCellValue();
			 String stringCellValue2 = row.getCell(2).getStringCellValue();
			 set.add(stringCellValue1+","+stringCellValue2);
			 i++;
			
		}
		float n=1;
		for (String string : set) {
			String[] split = string.split(",");
			String parentName=split[0];
			String name=split[1];
			PlatformCatalogs pp=new PlatformCatalogs();
			pp.setTreeDepth(2);
			pp.setName(parentName);
			PlatformCatalogs parent = platformCatalogsService.selectEntityOne(pp);
			
			PlatformCatalogs catalogs=new PlatformCatalogs();
			catalogs.setName(name);
			catalogs.setParentId(parent.getId());
			catalogs.setTreeDepth(3);
			catalogs.setUsable(0);
			catalogs.setPriority(n++);
			catalogs.setIsParent("false");
			catalogs.setIsDelete(0);
			catalogs.setTreeIds(parent.getTreeIds()+"_"+parent.getId());
			catalogs.setTreeNames(parent.getTreeNames()+"_"+parent.getName());
			platformCatalogsService.save(catalogs);
		}
		long time2=System.currentTimeMillis();
		System.out.println(time2-time1);
	}


}
