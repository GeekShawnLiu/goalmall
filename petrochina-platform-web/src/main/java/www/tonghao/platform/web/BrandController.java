package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
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
import www.tonghao.common.utils.ChineseCharToEn;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.BrandService;
import www.tonghao.service.common.service.SupplierProtocolBrandService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="brandController",description="品牌api")
@RestController
@RequestMapping("/brand")
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	@Autowired
	private SupplierProtocolBrandService supplierProtocolBrandService;
	
	
	/*@SysOptLog(opt="查看品牌所有数据")*/
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="shortName",value="简称",required=false,dataType="string",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Brand> test(@ModelAttribute PageBean page,String name,String shortName){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(Brand.class);
		Map<String, Object> maplike=new HashMap<String, Object>();
		Criteria createCriteria = example.createCriteria();
		maplike.put("name", name);
		maplike.put("shortName", shortName);
		createCriteria.andEqualTo("isDelete", 0);
		CriteriaLikeUtil.criteriaLike(createCriteria, maplike);
		List<Brand> list = brandService.selectByExample(example);
		return new PageInfo<Brand>(list);
	}
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Brand getById(Long id){
		Brand brand = brandService.selectByKey(id);
		return brand;
	}
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		Brand brand = brandService.selectByKey(id);
		brand.setIsDelete(1);
		int delete = brandService.updateNotNull(brand);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Brand brand){
		int saveOrUpdate = brandService.saveOrUpdate(brand);
		return ResultUtil.result(saveOrUpdate, 0);
	}
	
	
	/**  
	 * <p>Title: find</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param brand
	 * @return  
	 */  
	@ApiOperation(value="根据协议查询供应商品牌",notes="根据协议查询供应商品牌api")
	@RequestMapping(value="/find",method=RequestMethod.GET)
	public List<Brand> find(@ModelAttribute Brand brand){
		Example example=new Example(Brand.class);
		Map<String, Object> maplike=new HashMap<String, Object>();
		Criteria createCriteria = example.createCriteria();
		maplike.put("name", brand.getName());
		maplike.put("shortName", brand.getShortName());
		maplike.put("S", brand.getMappingName());
		createCriteria.andEqualTo("isFrozen", 0);
		createCriteria.andEqualTo("isDelete", 0);
		CriteriaLikeUtil.criteriaLike(createCriteria, maplike);
		List<Brand> list = brandService.selectByExample(example);
		/*for (Brand brand2 : list) {
			 String regex = "^[a-zA-Z]+$";
			String name = brand2.getName().substring(0, 1);
			if(!name.matches(regex)){
				
				brand2.setPinyin(ChineseCharToEn.getAllFirstLetter(name).toUpperCase());
			}else{
				brand2.setPinyin(name.toUpperCase());
			}
			
			brandService.updateNotNull(brand2);
		}*/
		return list;
	}
	
	/**  
	 * <p>Title: updatePinyin</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @return 
	 */
	@ApiOperation(value="更新品牌首字母",notes="更新品牌首字母api")
	@RequestMapping(value="/updatePinyin",method=RequestMethod.GET)
	public Map<String, Object> updatePinyin(){
		Example example=new Example(Brand.class);
		List<Brand> list = brandService.selectByExample(example);
		int result = 0;
		for (Brand brand2 : list) {
			if (StringUtils.isBlank(brand2.getPinyin())) {
				Brand brand = new Brand();
				brand.setId(brand2.getId());
				String regex = "^[a-zA-Z]+$";
				String name = brand2.getName().substring(0, 1);
				if(!name.matches(regex)){
					brand.setPinyin(ChineseCharToEn.getAllFirstLetter(name).toUpperCase());
				}else{
					brand.setPinyin(name.toUpperCase());
				}
				brand.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				brand.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
				result = brandService.updateNotNull(brand);
			}
		}
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: find</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param brand
	 * @return  
	 */  
	@ApiOperation(value="根据协议查询供应商品牌",notes="根据协议查询供应商品牌api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="protocolId",value="协议id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="mappingName",value="节能清单品牌名称",required=false,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/getSupplierProtocolBrands",method=RequestMethod.GET)
	public List<Brand> getSupplierProtocolBrands(Long protocolId, String mappingName, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if (protocolId != null && user.getType() != null && user.getType() == 4 && user.getTypeId() != null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("supplierId", user.getTypeId());
			map.put("protocolId", protocolId);
			if (StringUtils.isNotBlank(mappingName)) {
				map.put("mappingName", mappingName);
			}
			List<Brand> list = supplierProtocolBrandService.getSupplierProtocolBrands(map);
			return list;
		}
		return null;
	}
	
	
	
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	public List<Brand> getAll(Long protocolId, String mappingName, HttpServletRequest request){
		Example example=new Example(Brand.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isFrozen", 0);
		createCriteria.andEqualTo("isDelete", 0);
		List<Brand> list = brandService.selectByExample(example);
		return list;
	}
	
}
