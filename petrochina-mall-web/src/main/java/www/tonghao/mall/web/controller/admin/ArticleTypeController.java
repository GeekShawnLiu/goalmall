package www.tonghao.mall.web.controller.admin;

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
import www.tonghao.service.common.entity.ArticleType;
import www.tonghao.service.common.service.ArticleTypeService;
import www.tonghao.service.common.service.CatalogsService;


@Api(value="articleTypeController",description="公告栏目Api")
@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private CatalogsService catalogsService;

	@ApiOperation(value="根据id获取所有的子节点",notes="根据id获取所有的子节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenById",method=RequestMethod.GET)
	public List<ArticleType> getChildrenById(long id){
		List<ArticleType> childrenById = articleTypeService.getChildrenById(id);
		return childrenById;
	}
	
	@ApiOperation(value="根据id获取下一级所有节点",notes="根据id获取下一级所有节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenByLevel",method=RequestMethod.GET)
	public List<ArticleType> getChildrenByLevel(Long id){
		Example example=new Example(ArticleType.class);
		Criteria createCriteria = example.createCriteria();
		if(id!=null){
			createCriteria.andEqualTo("parentId", id);
		}else{
			createCriteria.andEqualTo("treeDepth", 1);
		}
		List<ArticleType> selectByExample = articleTypeService.selectByExample(example);
		return selectByExample;
	}
	
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public ArticleType getById(Long id){
		ArticleType articleType = articleTypeService.selectByKey(id);
		return articleType;
	}
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody ArticleType articleType){
		int saveOrUpdate = articleTypeService.saveOrUpdate(articleType);
		return ResultUtil.result(saveOrUpdate, 0);
	}
	
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = articleTypeService.deleteArticleType(id);
		return ResultUtil.result(delete, 0);
	}
	
	/*@RequestMapping(value="/cata",method=RequestMethod.POST)
	public void cata(){
		Example example=new Example(Catalogs.class);
        List<PlatformCatalogs> ct = platformCatalogsService.selectByExample(example);
        for (PlatformCatalogs catalogs : ct) {
        	Example example1=new Example(Catalogs.class);
        	Criteria createCriteria = example1.createCriteria();
        	createCriteria.andEqualTo("parentId", catalogs.getId());
        	List<PlatformCatalogs> selectByExample = platformCatalogsService.selectByExample(example1);
        	if(!CollectionUtils.isEmpty(selectByExample)){
        		catalogs.setIsParent("true");
        	}else{
        		catalogs.setIsParent("false");
        	}
        	platformCatalogsService.updateNotNull(catalogs);
		}
		
		
		
	}*/
	/*@RequestMapping(value="/excel",method=RequestMethod.POST)
	public void excel() throws IOException{
		InputStream in = ArticleTypeController.class.getResourceAsStream("/org/spe/catalogs.xlsx");
		Workbook wb =new XSSFWorkbook(in);
		 Sheet sheet = wb.getSheetAt(3);;  //页数
	        Row row = null;  //行数
	        Cell cell = null;  //列数
	        System.out.println(sheet.getLastRowNum());
	      //遍历当前sheet中的所有行 
	        Example example=new Example(PlatformCatalogs.class);
	        Criteria createCriteria = example.createCriteria();
	        createCriteria.andEqualTo("treeDepth", 3);
	        List<PlatformCatalogs> ct = platformCatalogsService.selectByExample(example);
	        float i=0;
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {  
                row = sheet.getRow(j);  
                cell = row.getCell(0);
                if(cell.getStringCellValue().toString().length()==7){
                	Example example1=new Example(PlatformCatalogs.class);
        	        Criteria createCriteria1 = example1.createCriteria();
        	        createCriteria1.andEqualTo("code", cell.getStringCellValue().toString());
        	        List<PlatformCatalogs> ct1 = platformCatalogsService.selectByExample(example1);
                	if(!CollectionUtils.isEmpty(ct1)){
                		continue;
                	}
                	
                	String code_one=cell.getStringCellValue().toString().substring(0, 5);
                	Long parent_id=null;
                	String name="";
                	String ids="";
                	ay:
                	for (PlatformCatalogs catalogs : ct) {
                		if(catalogs.getCode().equals(code_one)){
                			parent_id=catalogs.getId();
                			name=catalogs.getTreeNames()+"_"+catalogs.getName();
                			ids=catalogs.getTreeIds()+"_"+catalogs.getId();
                			break ay;
                		}
					}
                	i++;
                	PlatformCatalogs catalogs=new PlatformCatalogs();
                	catalogs.setCode(cell.getStringCellValue());
                	cell = row.getCell(1);
                	catalogs.setName(cell.getStringCellValue());
                	catalogs.setTreeDepth(4);
                	cell = row.getCell(2);
                	if(cell!=null){
                		catalogs.setDescs(cell.getStringCellValue());
                	}
                	catalogs.setTreeIds(ids);
                	catalogs.setTreeNames(name);
                	catalogs.setPriority(i);
                	catalogs.setIsDelete(0);
                	catalogs.setUsable(0);
                	catalogs.setParentId(parent_id);
                	platformCatalogsService.save(catalogs);
                	Catalogs catalogs=new Catalogs();
                	catalogs.setCode(cell.getStringCellValue());
                	cell = row.getCell(1);
                	catalogs.setName(cell.getStringCellValue());
                	catalogs.setParentId(parent_id);
                	catalogs.setTreeIds(ids);
                	catalogs.setTreeNames(name);
                	catalogs.setPriority(i);
                	catalogs.setTreeDepth(6);
                	catalogsService.save(catalogs);
                }
               
            }  
	        
		
	}
	*/
	
}
