package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.entity.IndustryCategory;
import www.tonghao.platform.service.IndustryCategoryService;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.entity.EnterpriseNature;
import www.tonghao.service.common.entity.OrgSupplier;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.AreasService;
import www.tonghao.service.common.service.EnterpriseNatureService;
import www.tonghao.service.common.service.OrgSupplierService;
import www.tonghao.service.common.service.OrganizationService;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="organizationController",description="采购人库Api")
@RestController
@RequestMapping("/organization")
public class OrganizationController {
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private EnterpriseNatureService enterpriseNatureService;
	@Autowired
	private IndustryCategoryService industryCategoryService;
	@Autowired
	private AreasService areasService;
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private SuppliersService suppliersService;
	
	@Autowired
	private OrgSupplierService orgSupplierService;
	
	@ApiOperation(value="根据id获取所有的子节点",notes="根据id获取所有的子节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenById",method=RequestMethod.GET)
	public List<Organization> getChildrenById(long id){
		List<Organization> childrenById = organizationService.getChildrenById(id);
		return childrenById;
	}
	
	@ApiOperation(value="根据id获取下一级所有节点",notes="根据id获取下一级所有节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="type",value="机构类型 1：采购人 2：监管机构",required=false,dataType="int",paramType="query")
	})
	@RequestMapping(value="/getChildrenByLevel",method={RequestMethod.GET,RequestMethod.POST})
	public List<Organization> getChildrenByLevel(Long id, Integer type, Long operaterOrgId){
		Example example=new Example(Organization.class);
		Criteria createCriteria = example.createCriteria();
		if(id!=null){
			createCriteria.andEqualTo("parentId", id);
		}else{
			createCriteria.andEqualTo("treeDepth", 1);
		}
		if (type != null) {
			createCriteria.andEqualTo("type", type);
		}
		List<Organization> selectByExample = organizationService.selectByExample(example);
		if(id == null && operaterOrgId != null){
			List<Organization> nodeByOperaterId = organizationService.getNodeByOperaterId(operaterOrgId);
			if(!CollectionUtils.isEmpty(selectByExample)){
				selectByExample = nodeByOperaterId;
			}
		}
		return selectByExample;
	}
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Organization getById(Long id){
		Organization organization = organizationService.getOrganizationById(id);
		if(organization!=null){
			IndustryCategory industryCategorys = industryCategoryService.selectByKey(organization.getIndustryCategory());
			if(industryCategorys!=null){
				organization.setIndustryCategoryName(industryCategorys.getName());	
			}
			EnterpriseNature enterpriseNature = enterpriseNatureService.selectByKey(organization.getDepProperties());
			if(enterpriseNature!=null){
				organization.setDepPropertiesName(enterpriseNature.getName());
			}
			Areas entity=new Areas();
			if(organization.getAreaIds()!=null&&!"".equals(organization.getAreaIds())) {
				entity.setTreeIds(organization.getAreaIds().replaceAll("_", "-"));
				Areas areas = areasService.selectEntityOne(entity);
				if(areas!=null){
					organization.setAreaName(areas.getTreeNames());
				}
			}
			Example example = new Example(OrgSupplier.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("orgId", organization.getId());
			List<OrgSupplier> orgSupplierList = orgSupplierService.selectByExample(example);
			if(CollectionUtils.isNotEmpty(orgSupplierList)){
				StringBuffer sb = new StringBuffer();
				orgSupplierList.forEach(os -> {
					if(os != null){
						sb.append(os.getSupplierId() + ",");
					}
				});
				if(sb.length() > 0){
					organization.setSupplierIds(sb.toString().substring(0, sb.length() - 1));
				}
			}
		}
		return organization;
	}
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Organization organization){
		int saveOrUpdate = organizationService.saveOrUpdate(organization);
		return ResultUtil.result(saveOrUpdate, 0);
	}
	
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = organizationService.deleteOrganization(id);
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="集采机构库查询",notes="集采机构库查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="name",value="采购机构名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="type",value="机构类型 1：采购人/集采机构 2：监管机构",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getOrgLevel",method=RequestMethod.GET)
	public PageInfo<Organization> getOrgLevel(@ModelAttribute PageBean page,String name, Integer type){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		map.put("type", type);
		List<Organization> orgLevel = organizationService.getOrgLevel(map);
		return new PageInfo<Organization>(orgLevel);
	}
	
	@ApiOperation(value="获取当前机构下的用户",notes="获取当前机构下的用户api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="typeId",value="机构id",required=true,dataType="Long",paramType="query"),
		@ApiImplicitParam(name="realName",value="姓名",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="loginName",value="登录名",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="email",value="邮箱",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="mobile",value="手机号",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="code",value="员工编号",required=false,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/getUsers",method=RequestMethod.GET)
	public PageInfo<Users> getUsers(@ModelAttribute PageBean page, Long typeId, String realName, String loginName, String email, String mobile, String code){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(Users.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("type", 1);
		createCriteria.andEqualTo("typeId", typeId);
		createCriteria.andEqualTo("isDelete", 0);
		if(!StringUtils.isEmpty(realName)){
			createCriteria.andLike("realName", "%"+realName+"%");
		}
		if(!StringUtils.isEmpty(loginName)){
			createCriteria.andLike("loginName", "%"+loginName+"%");	
		}
		if(!StringUtils.isEmpty(email)){
			createCriteria.andLike("email", "%"+email+"%");
		}
		if(!StringUtils.isEmpty(mobile)){
			createCriteria.andLike("mobile", "%"+mobile+"%");
		}
		if(!StringUtils.isEmpty(code)){
			createCriteria.andEqualTo("code", code);
		}
		List<Users> users = usersService.selectByExample(example);
		return new PageInfo<Users>(users);
	}
	
	/**
	 * @Description:获取机构列表
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="获取机构列表")
	@RequestMapping(value="/getOrganizationList", method=RequestMethod.GET)
	public List<Organization> getOrganizationList(){
		Example example=new Example(Organization.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		createCriteria.andEqualTo("isParent", "false");
		List<Organization> organizationList = organizationService.selectByExample(example);
		return organizationList;
	}
	
	
	@ApiOperation(value="根据id获取下一级所有节点",notes="根据id获取下一级所有节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="type",value="机构类型 1：采购人 2：监管机构",required=false,dataType="int",paramType="query")
	})
	@RequestMapping(value="/getNodesByOperaterOrgId",method=RequestMethod.GET)
	public List<Organization> getNodesByOperaterOrgId(Long id, Integer type, Long operaterOrgId){
		Example example=new Example(Organization.class);
		Criteria createCriteria = example.createCriteria();
		if(id!=null){
			createCriteria.andEqualTo("parentId", id);
		}else{
			createCriteria.andEqualTo("treeDepth", 1);
		}
		if (type != null) {
			createCriteria.andEqualTo("type", type);
		}
		example.setOrderByClause("priority");
		List<Organization> selectByExample = organizationService.selectByExample(example);
		
		if(id == null && operaterOrgId != null){
			List<Organization> nodeByOperaterId = organizationService.getNodeByOperaterId(operaterOrgId);
			if(!CollectionUtils.isEmpty(selectByExample)){
				selectByExample = nodeByOperaterId;
			}
		}
		//首次默认展示两级节点
		if(id == null && operaterOrgId == null && CollectionUtils.isNotEmpty(selectByExample)){
			selectByExample.forEach(o -> {
				if(o.getTreeDepth() != null && o.getTreeDepth() == 1){
					o.setOpen(true);
				}
			});
			Example childExample=new Example(Organization.class);
			Criteria childCreateCriteria = childExample.createCriteria();
			childCreateCriteria.andEqualTo("parentId", selectByExample.get(0).getId());
			if (type != null) {
				childCreateCriteria.andEqualTo("type", type);
			}
			childExample.setOrderByClause("priority");
			List<Organization> childList = organizationService.selectByExample(childExample);
			if(CollectionUtils.isNotEmpty(childList)){
				selectByExample.addAll(childList);
			}
		}
		return selectByExample;
	}
	
	@ApiOperation(value="获取所有供应商",notes="获取所有供应商api")
	@RequestMapping(value="/getAllSupplier", method=RequestMethod.GET)
	public List<Suppliers> getAllSupplier(){
		Example example = new Example(Suppliers.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		List<Suppliers> supplierList = suppliersService.selectByExample(example);
		return supplierList;
	}
	
	@ApiOperation(value="重置采购人密码",notes="重置采购人密码api")
	@RequestMapping(value="/resetPassword", method=RequestMethod.PUT)
	public Map<String, Object> resetPassword(@RequestBody Users user, HttpServletRequest request){
		Users loginUser = UserUtil.getUser(request);
		if(loginUser != null && loginUser.getType() != null && loginUser.getType() == 6){
			String encryptedPassword = user.getEncryptedPassword();
			String confirmPassword = user.getConfirmPassword();
			if(encryptedPassword == null || confirmPassword == null){
				return ResultUtil.error("密码不能为空");
			}else{
				if(!encryptedPassword.equals(confirmPassword)){
					return ResultUtil.error("两次密码不一致");
				}else{
					int resetPassword = usersService.resetPassword(user.getId(), user.getEncryptedPassword());
					return ResultUtil.resultMessage(resetPassword, "");
				}
			}
		}else{
			return ResultUtil.error("登录信息异常");
		}
	}
}
