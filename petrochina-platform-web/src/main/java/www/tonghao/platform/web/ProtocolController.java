package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
import www.tonghao.common.utils.criteria.CriteriaEqualsUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.entity.SupplierProtocol;
import www.tonghao.service.common.entity.SupplierProtocolBrand;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProtocolService;
import www.tonghao.service.common.service.SupplierProtocolBrandService;
import www.tonghao.service.common.service.SupplierProtocolService;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/protocol")
@Api(value="protocolController",description="协议Api")
public class ProtocolController {

	@Autowired
	private ProtocolService protocolService;
	@Autowired
	private SupplierProtocolService supplierProtocolService;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	@Autowired
	private SupplierProtocolBrandService supplierProtocolBrandService;
	@Autowired
	private SuppliersService suppliersService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="协议名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="code",value="协议编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="types",value="协议类型",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="startTime",value="失效日期开始",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="endTime",value="失效日期结束",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="状态",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Protocol> test(@ModelAttribute PageBean page,String name,String code,Integer types,Integer status,String startTime,String endTime){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(Protocol.class);
		Criteria createCriteria = example.createCriteria();
		Map<String, Object> maplike=new HashMap<String, Object>();
		maplike.put("name", name);
		maplike.put("code", code);
		CriteriaLikeUtil.criteriaLike(createCriteria, maplike);
		Map<String, Object> mapEq=new HashMap<String, Object>();
		mapEq.put("types", types);
		mapEq.put("status", status);
		mapEq.put("isDelete", 0);
		CriteriaEqualsUtil.criteriaEquals(createCriteria, mapEq);
		CriteriaLikeUtil.criteriaBetwwen(createCriteria, "endTime", startTime, endTime);
		List<Protocol> list = protocolService.selectByExample(example);
		return new PageInfo<Protocol>(list);
	}
	
	
	@ApiOperation(value="删除协议",notes="删除协议api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		Protocol protocol=new Protocol();
		protocol.setId(id);
		protocol.setIsDelete(1);
		int delete = protocolService.updateNotNull(protocol);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="添加",notes="添加api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String, Object> save(@RequestBody Protocol protocol){
		
		int save = protocolService.saveProtocol(protocol);
		return ResultUtil.result(save, 0);
	}
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="Long",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Protocol getById(Long id){
		Protocol protocol = protocolService.getProtocolById(id);
		List<SupplierProtocol> supplierProtocols = protocol.getSupplierProtocols();
		if(!CollectionUtils.isEmpty(supplierProtocols)){
			for (SupplierProtocol supplierProtocol : supplierProtocols) {
				Example example=new Example(SupplierProtocolBrand.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("supplierProtocolId", supplierProtocol.getId());
				List<SupplierProtocolBrand> selectByExample = supplierProtocolBrandService.selectByExample(example);
				if(!CollectionUtils.isEmpty(selectByExample)){
					List<String> collect = selectByExample.stream().map(SupplierProtocolBrand::getBrandName).collect(Collectors.toList());
					List<Long> ids = selectByExample.stream().map(SupplierProtocolBrand::getBrandId).collect(Collectors.toList());
					List<String>  list=new ArrayList<String>(); 
					if(!CollectionUtils.isEmpty(ids)){
						for (Long long1 : ids) {
							list.add(""+long1);
						}
					}
					supplierProtocol.setBrandNmas(String.join(",", collect.toArray(new String[collect.size()])));
					supplierProtocol.setBrandIds(String.join(",", list.toArray(new String[list.size()])));
				}
				
			}
		}
		return protocol;
	}
	
	@ApiOperation(value="修改",notes="修改api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String, Object> update(@RequestBody Protocol protocol){
		int save = protocolService.updateProtocol(protocol);
		return ResultUtil.result(save, 0);
	}
	
	@ApiOperation(value="删除协议供应商",notes="删除协议供应商api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/deleteSupper",method=RequestMethod.DELETE)
	public Map<String, Object> deleteSupper(Long id){
		int delete = supplierProtocolService.delete(id);
		return ResultUtil.result(delete, 0);
	}
	
	
	@ApiOperation(value="执行协议",notes="执行协议接口api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/execute",method=RequestMethod.GET)
	public Map<String, Object> execute(Long id){
		Example example=new Example(SupplierProtocol.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("protocolId", id);
		List<SupplierProtocol> selectByExample = supplierProtocolService.selectByExample(example);
		
		if(CollectionUtils.isEmpty(selectByExample)){
			return ResultUtil.error("请先分配供应商后进行操作！");
		}else{
			Protocol protocol=new Protocol();
			protocol.setId(id);
			protocol.setStatus(2);
			int upate = protocolService.updateNotNull(protocol);
			return ResultUtil.result(upate, 0);
		}
	}
	
	@ApiOperation(value="暂停销售",notes="暂停销售api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/stopSupplier",method=RequestMethod.GET)
	public Map<String, Object> stopSupplier(Long id){
		SupplierProtocol protocol=new SupplierProtocol();
		protocol.setId(id);
		protocol.setStatus(2);
		int upate = supplierProtocolService.updateNotNull(protocol);
		return ResultUtil.result(upate, 0);
	}
	
	
	@ApiOperation(value="代理商协议",notes="代理商协议api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="协议名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="code",value="协议编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="startTime",value="失效日期开始",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="endTime",value="失效日期结束",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="状态",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/protocolSupplier",method=RequestMethod.GET)
	public PageInfo<Protocol> protocolSupplier(@ModelAttribute PageBean page,String name,String code,Integer status,String startTime,String endTime,HttpServletRequest request){
		PageHelper.startPage(page.getPage(), page.getRows());
		List<Protocol> protocolBySupplier=null;
		Users user = UserUtil.getUser(request);
		if(user!=null&&user.getType()==4){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("name", name);
			map.put("code", code);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("status", status);
			map.put("supplierId", user.getTypeId());
			protocolBySupplier = protocolService.getProtocolBySupplier(map);
		}
		
		return new PageInfo<Protocol>(protocolBySupplier);
	}
	
	
	@ApiOperation(value="获取供应商中标品目",notes="获取供应商中标品目api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="proId",value="协议id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierId",value="协议供应商关联id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getCatalogs",method=RequestMethod.GET)
	public ArrayList<PlatformCatalogs> getCatalogs(Long proId,Long supplierId){
		Map<String, Object> protocol=new HashMap<String, Object>();
		protocol.put("proId", proId);
		protocol.put("supplierId", supplierId);
		protocol.put("type", 1);
		List<PlatformCatalogs> plft = platformCatalogsService.getCatalogsBysupplierIdAndProId(protocol);
		Set<PlatformCatalogs> catalogs=new HashSet<PlatformCatalogs>();
		if(!CollectionUtils.isEmpty(plft)){
			for (PlatformCatalogs platformCatalogs : plft) {
				List<PlatformCatalogs> parentById = platformCatalogsService.getParentById(platformCatalogs.getId());
				catalogs.addAll(parentById);
			}
		}
		return new ArrayList<PlatformCatalogs>(catalogs);
	}
	
	@ApiOperation(value="获取供应商中标品目",notes="获取供应商中标品目api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="proId",value="协议id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierId",value="协议供应商关联id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getProtocolCatalogs",method=RequestMethod.GET)
	public List<PlatformCatalogs> getProtocolCatalogs(Long proId,Long supplierId){
		Map<String, Object> protocol=new HashMap<String, Object>();
		protocol.put("proId", proId);
		protocol.put("supplierId", supplierId);
		protocol.put("type", 1);
		List<PlatformCatalogs> plft = platformCatalogsService.getCatalogsBysupplierIdAndProId(protocol);
		return plft;
	}
	
	/**  
	 * <p>Title: getProtocolBySupplierId</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param supplierId
	 * @return  
	 */  
	@ApiOperation(value="获取当前登录供应商生效协议",notes="获取供应商生效协议api")
	@RequestMapping(value="/getProtocolBySupplierId",method=RequestMethod.GET)
	public List<Protocol> getProtocolBySupplierId(HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if (user != null && user.getType() != null && user.getType() == 4 && user.getTypeId() != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("supplierId", user.getTypeId());
			map.put("endTime", DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			List<Protocol> protocols = protocolService.getProtocolBySupplier(map);
			return protocols;
		}
		return null;
	}
	
	@RequestMapping(value="/getSupplier",method=RequestMethod.GET)
	public Map<String, Object> getSupplier(String companyName){
		/*SelectSupplier selectSupplier=new SelectSupplier(companyName);
		SupplierRes result = selectSupplier.getResult();
		int result_num=0;
		if(result.getSuccess()){
			List<SupplierAttr> supplierList = result.getSupplierList();
			if(!CollectionUtils.isEmpty(supplierList)){
				SupplierAttr supplierAttr = supplierList.get(0);
				String companyCode = supplierAttr.getCompanyCode();
				Example example=new Example(Suppliers.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("creditCode", companyCode);
				List<Suppliers> selectByExample = suppliersService.selectByExample(example);
				if(CollectionUtils.isEmpty(selectByExample)){
					Suppliers suppliers=new Suppliers();
					suppliers.setName(supplierAttr.getCompanyName());
					suppliers.setCreditCode(supplierAttr.getCompanyCode());
					suppliers.setLegalName(supplierAttr.getCorporation());
					suppliers.setOpeningBank(supplierAttr.getBankName());
					suppliers.setBankAccount(supplierAttr.getBankNum());
					suppliers.setContactsName(supplierAttr.getLinkman());
					suppliers.setContactsPhone(supplierAttr.getTel());
					suppliers.setContactsMobile(supplierAttr.getMobile());
					suppliers.setContactsEmail(supplierAttr.getEmail());
					suppliers.setStatus(1);
					String usetnames=supplierAttr.getUsername();
					Users users=new Users();
					users.setLoginName(usetnames);
					users.setRealName(suppliers.getName());
					users.setType(4);
					suppliers.setUsers(users);
					result_num=suppliersService.saveSupplierApi(suppliers);
				}
				
			}else{
				return ResultUtil.resultMessage(result_num, "没有此供应商");
			}
		}else{
			return ResultUtil.resultMessage(result_num, result.getMsg());
		}*/
		return ResultUtil.resultMessage(0, "供应商抓取成功");
	}
	
	
}
