package www.tonghao.mall.web.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.Constant;
import www.tonghao.common.SysLog;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.TreeBuilder;
import www.tonghao.common.warpper.FileInfo.FileType;
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.mall.entity.Floor;
import www.tonghao.mall.entity.FloorBrand;
import www.tonghao.mall.entity.FloorPlatformCatalog;
import www.tonghao.mall.entity.MallCatalogs;
import www.tonghao.mall.service.FloorBrandService;
import www.tonghao.mall.service.FloorPlatformCatalogService;
import www.tonghao.mall.service.FloorService;
import www.tonghao.mall.service.MallCatalogsService;
import www.tonghao.mall.service.MallPlatformCatalogsService;
import www.tonghao.service.common.entity.Catalogs;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.service.BrandService;
import www.tonghao.service.common.service.CatalogsService;
import www.tonghao.service.common.service.FileService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;


@Api(value="adminFloorController",description="楼层管理")
@RestController
@RequestMapping("/floor")
public class FloorController {
	
	@Autowired
	private FloorService floorService;
	
	@Autowired
	private FloorBrandService floorBrandService;
	
	@Autowired
	private FloorPlatformCatalogService floorPlatformCatalogService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private CatalogsService catalogsService;
	
	@Autowired
    private MallPlatformCatalogsService mallPlatformCatalogsService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private MallCatalogsService mallCatalogsService;
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	@SysLog(opt = "分页查询楼层列表")
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@GetMapping("/getPage")
	public PageInfo<Floor> getPage(@ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		List<Floor> list = floorService.selectByExample(null);
		return new PageInfo<Floor>(list);
	}
	
	/**
	 * 查询单个楼层
	 * @param id
	 * @return
	 */
	@ApiOperation(value="查询单个",notes="查询单个api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="ID",required=true,dataType="long"),
	})
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public Floor findById(Long id){
//	    List<PlatformCatalogs> cataList = mallPlatformCatalogsService.getPlatformCatalogs();
	    List<TreeNode> mallCataList = mallCatalogsService.getTreeList(null, null);
//	    System.out.println("转换前++++++++++"+JsonUtil.toJson(mallCataList));
	    TreeBuilder tb = new TreeBuilder();
		List<TreeNode> mallCataLogList = tb.treeToList(mallCataList);
//		System.out.println("转换后=========="+JsonUtil.toJson(mallCataLogList));
	    Floor floor = floorService.findById(id);
	    List<FloorPlatformCatalog> floorCataList = floor.getFloorPlatformCatalogs();
	    for (TreeNode obj : mallCataLogList) {
	    	for (FloorPlatformCatalog floorPlatformCatalog : floorCataList) {
	    		String platFormCatalogid = String.valueOf(floorPlatformCatalog.getPlatformCatalogId());
	    		String catalogType = floorPlatformCatalog.getCatalogType();
	    		String judge = catalogType+"-"+platFormCatalogid;
	    		//平台品目和楼层品目对比，选中则设置sort为999
	    		if (obj.getExtStr().equals(judge) || obj.getExtStr() == judge) {
					obj.setSort((float) 999);
				}
	    	}
	    	obj.setChildren(null);
		}
	    
	    System.out.println(JsonUtil.toJson(mallCataLogList));
	    floor.setPlatformCatalogs(mallCataLogList);
	    return floor;
	}
	
	/**
	 * 添加楼层
	 * @return
	 */
	@ApiOperation(value="添加",notes="添加api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="name",value="楼层名",required=true,dataType="string"),
		@ApiImplicitParam(name="sort",value="排序",required=true,dataType="int"),
		@ApiImplicitParam(name="fontColor",value="颜色",required=false,dataType="string"),
		@ApiImplicitParam(name="iconUrl",value="图标url",required=false,dataType="string"),
		@ApiImplicitParam(name="cids",value="品目id数组",required=false,allowMultiple=true,dataType="string"),
		@ApiImplicitParam(name="bids",value="新增的楼层品牌id数组",required=false,allowMultiple=true,dataType="long")
	})
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Map<String, Object> add(String name,Integer sort,String fontColor,
	        String iconUrl,String[] cids,Long[] bids){
	    Map<String, Object> result = Maps.newHashMap();
		if(StringUtils.isEmpty(name)){
			return ResultUtil.error("楼层名不能为空");
		}
		if(sort==null){
			return ResultUtil.error("排序不能为空");
		}
		Floor entity = new Floor();
		entity.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		Example example = new Example(Floor.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("name", name);
		List<Floor> floors = floorService.selectByExample(example);
		if (floors.size() >= 1) {
			return ResultUtil.error("楼层名称已存在，请重新输入");
		}
		entity.setName(name);
		entity.setSort(sort);
		entity.setFontColor(fontColor);
		entity.setIconUrl(iconUrl);
		int floorFlag = floorService.save(entity);
		if (floorFlag > 0) {
		    StringBuffer floorCatalogName = new StringBuffer("");
		    result = ResultUtil.success("");
		    Long parentId = entity.getId();
		    if (bids != null &&bids.length>0) {
		        for (Long bid : bids) {
		            FloorBrand floorBrand = floorBrandService.selectByKey(bid);
		            floorBrand.setFloorId(parentId);
		            floorBrand.setSort(0);
		            floorBrandService.updateNotNull(floorBrand);
		        }
            }
		    if (cids != null &&cids.length>0) {
		        for (String cid : cids) {
		        	String[] str = cid.split("-");
		        	String type = str[0];
		        	Long catalogId = Long.valueOf(str[1]);
		        	FloorPlatformCatalog floorCatalog = new FloorPlatformCatalog();
		        	String catalogName = "";
		        	//m为商城品目
		        	if ("m".equals(type)) {
		        		MallCatalogs mallCatalogs = mallCatalogsService.selectByKey(catalogId);
		        		if (mallCatalogs != null) {
		        			floorCatalog.setPlatformCatalogId(mallCatalogs.getId());
		        			catalogName = mallCatalogs.getName();
		        			floorCatalog.setPlatformCatalogName(catalogName);
		        			floorCatalog.setCatalogType("m");
						}
		        	//p为平台品目	
					}else if ("p".equals(type)) {
						PlatformCatalogs catalogs = mallPlatformCatalogsService.selectByKey(catalogId);
						if (catalogs != null) {
							floorCatalog.setPlatformCatalogId(catalogs.getId());
							catalogName = catalogs.getName();
							floorCatalog.setPlatformCatalogName(catalogName);
							floorCatalog.setCatalogType("p");
						}
					}
		            if (catalogName != null) {
		                floorCatalog.setPlatformCatalogName(catalogName);
		                floorCatalogName.append(catalogName+"-");
		            }
		            floorCatalog.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		            floorCatalog.setFloorId(parentId);
		            floorCatalog.setSort(0);
		            floorPlatformCatalogService.save(floorCatalog);
		        }
		        if (!"".equals(floorCatalogName)) {
		            String fcatalog = floorCatalogName.toString();
		            fcatalog = fcatalog.substring(0, fcatalog.length()-1);
		            entity.setFloorCatalog(fcatalog);
		            floorService.updateAll(entity);
		        }
		    }
        }
		return result;
	}
	
	/**
	 * 删除楼层（物理删除）
	 * @param id
	 * @return
	 */
	   @ApiOperation(value="删除楼层",notes="删除api")
	    @ApiImplicitParams({
	        @ApiImplicitParam(name="id",value="楼层iid",required=true,dataType="long"),
	    })
	@Transactional
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
	    Map<String, Object> result = Maps.newHashMap();
	    if (id != null) {
	        int delFlag = floorService.delete(id);
	        if (delFlag>0) {
	            result = ResultUtil.success("");
	            floorBrandService.deleteByFloorId(id);
	            floorPlatformCatalogService.deleteByFloorId(id);
            }else {
                result = ResultUtil.error("");
            }
        }
	    return result;
	}
	
	/**
	 * 修改楼层信息
	 * @return
	 */
	@ApiOperation(value="修改",notes="修改api")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="id",value="楼层id",required=true,dataType="long"),
	    @ApiImplicitParam(name="name",value="楼层名",required=true,dataType="string"),
	    @ApiImplicitParam(name="sort",value="排序",required=true,dataType="int"),
	    @ApiImplicitParam(name="fontColor",value="颜色",required=false,dataType="string"),
	    @ApiImplicitParam(name="iconUrl",value="图标url",required=false,dataType="string"),
	    @ApiImplicitParam(name="cids",value="品目id数组",required=false,allowMultiple=true,dataType="string"),
	})
	@Transactional
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String, Object> update(Long id,String name,Integer sort,String fontColor,
            String iconUrl,String[] cids){
	    Map<String, Object> result = Maps.newHashMap();
	    if (id == null) {
            return ResultUtil.error("楼层id不能为空");
        }
	    if(StringUtils.isEmpty(name)){
            return ResultUtil.error("楼层名不能为空");
        }
        if(sort==null){
            return ResultUtil.error("排序不能为空");
        }
		Example example = new Example(Floor.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("name", name);
		List<Floor> floors = floorService.selectByExample(example);
		if (floors.size() >= 1) {
			Floor fl = floorService.selectByKey(id);
			String flName = fl.getName(); 
			if (!flName.equals(name)) {
				return ResultUtil.error("楼层名称已存在，请重新输入");
			}
		}
        //先删除所有品目再重新添加
	    floorPlatformCatalogService.deleteByFloorId(id);
	    Floor entity = floorService.selectByKey(id);
	    entity.setName(name);
	    entity.setSort(sort);
	    entity.setFontColor(fontColor);
	    entity.setIconUrl(iconUrl);
	    entity.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
	    int edit = floorService.updateNotNull(entity);
	    if (edit > 0) {
            result = ResultUtil.success("");
            Long parentId = entity.getId();
            StringBuffer floorCatalogName = new StringBuffer("");
            if (cids != null &&cids.length>0) {
		        for (String cid : cids) {
		        	String[] str = cid.split("-");
		        	String type = str[0];
		        	Long catalogId = Long.valueOf(str[1]);
		        	FloorPlatformCatalog floorCatalog = new FloorPlatformCatalog();
		        	String catalogName = "";
		        	if ("m".equals(type)) {
		        		MallCatalogs mallCatalogs = mallCatalogsService.selectByKey(catalogId);
		        		if (mallCatalogs != null) {
		        			floorCatalog.setPlatformCatalogId(mallCatalogs.getId());
		        			catalogName = mallCatalogs.getName();
		        			floorCatalog.setPlatformCatalogName(catalogName);
		        			floorCatalog.setCatalogType("m");
						}
					}else if ("p".equals(type)) {
						PlatformCatalogs catalogs = mallPlatformCatalogsService.selectByKey(catalogId);
						if (catalogs != null) {
							floorCatalog.setPlatformCatalogId(catalogs.getId());
							catalogName = catalogs.getName();
							floorCatalog.setPlatformCatalogName(catalogName);
							floorCatalog.setCatalogType("p");
						}
					}
		            if (catalogName != null) {
		                floorCatalog.setPlatformCatalogName(catalogName);
		                floorCatalogName.append(catalogName+"-");
		            }
		            floorCatalog.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		            floorCatalog.setFloorId(parentId);
		            floorCatalog.setSort(0);
		            floorPlatformCatalogService.save(floorCatalog);
		        }
		        if (!"".equals(floorCatalogName)) {
		            String fcatalog = floorCatalogName.toString();
		            fcatalog = fcatalog.substring(0, fcatalog.length()-1);
		            entity.setFloorCatalog(fcatalog);
		            floorService.updateNotNull(entity);
		        }
		    }
        }
	    return result;
	}
	    /**
	     * 删除楼层品牌
	     * @param id
	     * @return
	     */
	   @ApiOperation(value="删除楼层品牌",notes="删除楼层品牌api")
	    @ApiImplicitParams({
	        @ApiImplicitParam(name="id",value="id",required=true,dataType="long",paramType="query"),
	    })
	    @RequestMapping(value="/deleteFloorBrand",method=RequestMethod.DELETE)
	    public Map<String, Object> deleteFloorBrand(Long id){
	        int delete = floorBrandService.delete(id);
	        return ResultUtil.resultMessage(delete, "");
	    }
	   
	   /**
	    * 修改楼层品牌
	    * @param id
	    * @return
	    */
       @ApiOperation(value="修改楼层品牌",notes="修改楼层品牌api")
       @ApiImplicitParams({
           @ApiImplicitParam(name="id",value="id",required=true,dataType="long",paramType="query"),
           @ApiImplicitParam(name="brandName",value="品牌名称",required=true,dataType="string",paramType="query"),
           @ApiImplicitParam(name="brandLogo",value="品牌logo",required=false,dataType="string",paramType="query"),
       })
       @RequestMapping(value="/updateFloorBrand",method=RequestMethod.POST)
       public Map<String, Object> updateFloorBrand(Long id,String brandName,String brandLogo){
           FloorBrand floorBrand = new FloorBrand();
           floorBrand.setId(id);
           floorBrand.setBrandName(brandName);
           if (brandLogo != null) {
               floorBrand.setBrandLogo(brandLogo);
           }
           floorBrand.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
           int update = floorBrandService.updateNotNull(floorBrand);
           return ResultUtil.resultMessage(update, "");
       }
       
       /**
        * 新增楼层品牌
        * @return
        */
       @ApiOperation(value="新增楼层品牌",notes="新增楼层品牌api")
       @ApiImplicitParams({
           @ApiImplicitParam(name="brandName",value="品牌名称",required=true,dataType="string",paramType="query"),
           @ApiImplicitParam(name="brandLogo",value="品牌logo",required=false,dataType="string",paramType="query"),
           @ApiImplicitParam(name="floorId",value="楼层id",required=false,dataType="long",paramType="query"),
       })
       @RequestMapping(value="/addFloorBrand",method=RequestMethod.POST)
       public Map<String, Object> addFloorBrand(String brandName,String brandLogo,Long floorId){
           Map<String, Object> result = Maps.newHashMap();
           FloorBrand floorBrand = new FloorBrand();
           floorBrand.setBrandName(brandName);
           floorBrand.setBrandLogo(brandLogo);
           if (floorId == null) {
               floorId = (long) 000;
           }
           floorBrand.setFloorId(floorId);
           floorBrand.setCreatedAt(DateUtilEx.timeFormat(new Date()));
           int save = floorBrandService.save(floorBrand);
           if (save>0) {
               result = ResultUtil.resultMessage(save, "");
               result.put("dataId", floorBrand.getId());
           }else {
               result = ResultUtil.resultMessage(0, "");
           }
           return result;
       }
	   
       /**
        * 通过楼层id查询楼层品牌
        * @param floorId
        * @return
        */
       @ApiOperation(value="通过楼层id查询楼层品牌列表",notes="通过楼层id查询楼层品牌列表api")
       @ApiImplicitParams({
           @ApiImplicitParam(name="floorId",value="楼层id",required=true,dataType="long",paramType="query"),
       })
       @RequestMapping(value="/findBrandByFloorId",method=RequestMethod.GET)
       public List<FloorBrand> findBrandByFloorId(Long floorId){
           List<FloorBrand> list = floorBrandService.selectByFloorId(floorId);
           return list;
       }
       
       /**
        * 通过id查询楼层品牌列表
        * @param ids
        * @return
        */
       @ApiOperation(value="通过id查询楼层品牌列表",notes="查询楼层品牌列表api")
       @ApiImplicitParams({
           @ApiImplicitParam(name="ids",value="id数组",required=true,allowMultiple=true,dataType="long",paramType="query"),
       })
       @RequestMapping(value="/findBrandByIds",method=RequestMethod.POST)
       public List<FloorBrand> findBrandByIds(Long[] ids){
           List<FloorBrand> list = new ArrayList<FloorBrand>();
           if (ids != null) {
               for (Long id : ids) {
                   FloorBrand floorBrand = floorBrandService.selectByKey(id);
                   list.add(floorBrand);
               }
           }
           return list;
       }
       /**
        * 查询品目
        * @param id
        * @return
        */
       @RequestMapping(value="/findCata",method=RequestMethod.GET)
       public Catalogs findCata(Long id){
           Catalogs catalogs  = catalogsService.selectByKey(id);
           return catalogs;
       }
       
       /**
        * 文件上传
        * @return
        * @throws Exception
        */
	   @ApiOperation(value="楼层管理文件上传",notes="文件上传api")
	    @ApiImplicitParams({
	        @ApiImplicitParam(name="pathPrefix",value="文件存储前缀,如：/article, /product",required=false,dataType="String",paramType="query"),
	    })
	    @RequestMapping(value = "/upload", method=RequestMethod.POST)
	    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response, @Param("pathPrefix") String pathPrefix)
	            throws Exception {
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	        MultipartFile multipartFile = multipartRequest.getFileMap().entrySet()
	                .iterator().next().getValue();
	        
	        String filename = FilenameUtils.getName(multipartFile
	                .getOriginalFilename());
	        String uploadPath = Constant.IMAGE_UPLOAD_PATH;
	        //根据图片内容判断是否为图片文件
	        InputStream inputStream = multipartFile.getInputStream();
	        BufferedImage bi = ImageIO.read(inputStream);
	        if(bi == null){
	            //不是图片
	            uploadPath = Constant.FILE_UPLOAD_PATH;
	        }
	        String destPath = null;
	        if (StringUtils.isNotBlank(pathPrefix)) {
	            destPath = pathPrefix + uploadPath;
	        }else {
	            destPath = uploadPath;
	        }
	        
	        String ctxFileUrl = fileService.upload(FileType.file, multipartFile, destPath, false);
	        if (StringUtils.isNotEmpty(ctxFileUrl)) {
	            Map<String,Object> data = new HashMap<String, Object>();
	            data.put("fileName", filename);
	            data.put("path", ctxFileUrl);
	            data.put("fileSize", multipartFile.getSize());
	            return ResultUtil.resultMessage(HttpResponseCode.OK, " ", data);
	        }else {
	            return ResultUtil.resultMessage(HttpResponseCode.BAD_REQUEST, filename + "：上传失败！", null);
	        }
	    }
}
