package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.ArticleType;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.service.ArticleTypeService;
import www.tonghao.service.common.service.CatalogsService;
import www.tonghao.service.common.service.PlatformCatalogsService;


@Api(value="articleTypeController",description="公告栏目Api")
@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

	@Autowired
	private ArticleTypeService articleTypeService;
	@Autowired
	private CatalogsService catalogsService;
	@Autowired
	private PlatformCatalogsService platformCatalogsService;

	
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
	/*@RequestMapping(value="/excel",method=RequestMethod.GET)
	public void excel() throws IOException{
		InputStream in = ArticleTypeController.class.getResourceAsStream("/www/tonghao/tuishu.xlsx");
		Workbook wb =new XSSFWorkbook(in);
		 Sheet sheet = wb.getSheetAt(0);;  //页数
	        Row row = null;  //行数
	        Cell cell = null;  //列数
	        int i=0;
	        System.out.println(sheet.getLastRowNum());
	        Set<String> set=new HashSet<>();
	        Map<String, Set<String>> map=new HashMap<String, Set<String>>();
	        int num=1;
	        while ( i <= sheet.getLastRowNum() ) {
	        	row = sheet.getRow(i);
	        	Cell ce0=row.getCell(0);//一级
	        	Cell ce1=row.getCell(1);//二级
	        	Cell ce2=row.getCell(2);//三级
	        	PlatformCatalogs pcs=new PlatformCatalogs();
	        	pcs.setName(ce1.getStringCellValue());
	        	pcs.setTreeNames(ce0.getStringCellValue());
	        	PlatformCatalogs pfa = platformCatalogsService.selectEntityOne(pcs);
	        	if(pfa!=null){
	        	 PlatformCatalogs catalogs=new PlatformCatalogs();
	   	    	 catalogs.setName(ce2.getStringCellValue());
	   	    	 catalogs.setParentId(pfa.getId());
	   	    	 catalogs.setTreeDepth(3);
	   	    	 catalogs.setUsable(0);
	   	    	 catalogs.setTreeIds(pfa.getTreeIds()+"_"+pfa.getId());
	   	    	 catalogs.setTreeNames(pfa.getTreeNames()+"_"+pfa.getName());
	   	    	 catalogs.setPriority((float)num);
	   	    	 catalogs.setIsParent("false");
	   	    	 catalogs.setIsDelete(0);	 
	   	    	 platformCatalogsService.saveSelective(catalogs);
	        	}
	        	num++;
	        	 i++;
	        }
		 System.out.println(i);
	}*/
	
	
}
