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


import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import www.tonghao.common.utils.StringUtil;
import www.tonghao.platform.entity.IndustryCategory;
import www.tonghao.platform.entity.SupplierScale;
import www.tonghao.platform.service.IndustryCategoryService;
import www.tonghao.platform.service.SupplierScaleService;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UserRoles;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.service.common.service.UserRolesService;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.utils.UserUtil;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Api(value="suppliersController",description="供应商Api")
@RestController
@RequestMapping("/suppliers")
public class SuppliersController {
	
	@Autowired
	public SuppliersService suppliersService;
	@Autowired
	private IndustryCategoryService industryCategoryService;
	@Autowired
	private SupplierScaleService supplierScaleService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private UserRolesService userRolesService;
	
	@ApiOperation(value="管理员和审核人员分页查询所有",notes="分页查询所有api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="供应商名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="nickName",value="供应商简称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="contactsMobile",value="联系人电话",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="labelType",value="供应商标签",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Suppliers> test(@ModelAttribute PageBean page, String name, String nickName, String contactsMobile, Integer labelType){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", name == null ? null : name.trim());
		map.put("nickName", nickName == null ? null : nickName.trim());
		map.put("contactsMobile", contactsMobile == null ? null : contactsMobile.trim());
		map.put("labelType", labelType);
		List<Suppliers> list = suppliersService.getSuppliersAll(map);
		return new PageInfo<Suppliers>(list);
	}
	
	
	@ApiOperation(value="供应商自己分页查询",notes="分页查询所有api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="供应商名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="状态",required=false,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/getSupplier",method=RequestMethod.GET)
	public PageInfo<Suppliers> getSupplier(@ModelAttribute PageBean page,String name,String status,HttpServletRequest request){
		PageHelper.startPage(page.getPage(), page.getRows());
		List<Suppliers> list=null;
		Users user = UserUtil.getUser(request);
		if(user.getType()==4){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("name", name);
			map.put("id", user.getTypeId());
			map.put("status", status);
			list = suppliersService.getSuppliersAll(map);
		}
		return new PageInfo<Suppliers>(list);
	}
	
	
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Suppliers getById(Long id){
		Suppliers suppliers = suppliersService.getSuppliersById(id);
		if(suppliers!=null){
			Example example=new Example(Users.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("isDelete", 0);
			createCriteria.andEqualTo("type", 4);
			createCriteria.andEqualTo("typeId", suppliers.getId());
			List<Users> userList = usersService.selectByExample(example);
			suppliers.setUserList(userList);
			IndustryCategory ic = industryCategoryService.selectByKey(suppliers.getIndustryCategory());
			if(ic!=null){
				suppliers.setIndustryCategoryName(ic.getName());
			}
			SupplierScale supplierscale = supplierScaleService.selectByKey(suppliers.getSupplierScale());
		    if(supplierscale!=null){
		    	suppliers.setSupplierScaleName(supplierscale.getName());
		    }
		}
		return suppliers;
	}
	@ApiOperation(value="根据id查询所有用户",notes="据id查询所有用户api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public List<Users> getUser(Long id){
		Example example=new Example(Users.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		createCriteria.andEqualTo("type", 4);
		createCriteria.andEqualTo("typeId", id);
		return usersService.selectByExample(example);
	}
	
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = 0;
		Suppliers selectByKey = suppliersService.selectByKey(id);
		if(selectByKey != null){
			selectByKey.setIsDelete((byte)1);
			delete = suppliersService.updateNotNull(selectByKey);
		}
		return ResultUtil.result(delete, 0);
	}
	
	@ApiOperation(value="修改或添加",notes="修改或添加api")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Suppliers suppliers){
		suppliers.setStatus(1);
		suppliers.setIsFillBank(1);
		suppliers.setIsDelete((byte)0);
		if(suppliers == null || StringUtil.strIsEmpty(suppliers.getCode())){
			return ResultUtil.error("供应商code不能为空");
		}
		int saveOrUpdate = suppliersService.saveOrUpdate(suppliers);
		return ResultUtil.result(saveOrUpdate, 0);
	}
	
	@ApiOperation(value="根据ids逗号隔开查询",notes="查询多条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="ids",value="ids",required=true,dataType="string",paramType="query"),
		
	})
	@RequestMapping(value="/getByIds",method=RequestMethod.GET)
	public List<Suppliers> getByIds(String ids){
		Long[] idL=null;
		List<Suppliers> supplierIds=null;
		if(ids!=null&&!"".equals(ids)){
			String[] split = ids.split(",");
			idL=new Long[split.length];
			for (int i = 0; i < split.length; i++) {
				idL[i]=Long.parseLong(split[i]);
			}
		supplierIds = suppliersService.getSupplierIds(idL);
		}
		return supplierIds;
	}
	
	@ApiOperation(value="获取电商列表",notes="获取电商列表api")
	@RequestMapping(value="/getMallSuppliers",method=RequestMethod.GET)
	public List<Suppliers> getMallSuppliers(){
		List<Suppliers> list = suppliersService.getMallSuppliers();
		return list;
	}
	
	@ApiOperation(value="获取电商列表",notes="获取电商列表api")
	@RequestMapping(value="/getSupplierAll",method=RequestMethod.GET)
	public List<Suppliers> getSupplierAll(){
		Example example=new Example(Suppliers.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		createCriteria.andEqualTo("status", 1);
		createCriteria.andEqualTo("isFreeze", 0);
		List<Suppliers> selectByExample = suppliersService.selectByExample(example);
		return selectByExample;
	}
	
	/**  
	 * <p>Title: updateSupplierNickName</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param supplierId
	 * @param nickName
	 * @return 
	 */
	@ApiOperation(value="更新供应商简称",notes="更新供应商简称api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="nickName",value="供应商简称",required=true,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/updateSupplierNickName",method=RequestMethod.POST)
	public Map<String, Object> updateSupplierNickName(String nickName, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if (user != null && user.getType() != null && user.getType() == 4 && user.getTypeId() != null) {
			Suppliers supplier = new Suppliers();
			supplier.setId(user.getTypeId());
			supplier.setNickName(nickName);
			supplier.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			int result = suppliersService.updateNotNull(supplier);
			return ResultUtil.resultMessage(result, "");
		}else {
			return ResultUtil.resultMessage(0, "登录账号异常");
		}
	}
	
	/**
	 * 
	 * Description: 获取供应商
	 * 
	 * @data 2019年5月9日
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/getAllSuppliers",method=RequestMethod.GET)
	public List<Suppliers> getAllSuppliers(Long userId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status", 1);
		List<Suppliers> list = suppliersService.findNoUserSuppliers(map);
		return list;
	}
	
	@ApiOperation(value="供应商新增子账号",notes="供应商新增子账号api")
	@RequestMapping(value="/addSupplierUser", method=RequestMethod.POST)
	public Map<String, Object> addSupplierUser(@RequestBody Users user, HttpServletRequest request){
		Map<String, Object> vaUserInfo = usersService.vaUserInfo(user);
		Users loginUser = UserUtil.getUser(request);
		if("success".equals(vaUserInfo.get("status")) && loginUser != null){
			if(user.getEncryptedPassword() == null || user.getConfirmPassword() == null){
				return ResultUtil.error("密码不能为空");
			}else{
				if(!user.getEncryptedPassword().equals(user.getConfirmPassword())){
					return ResultUtil.error("密码不一致，请重新输入");
				}
			}
			user.setIsKey(0);
			user.setType(loginUser.getType());
			user.setTypeId(loginUser.getTypeId());
			int result = usersService.saveUser(user);
			return ResultUtil.resultMessage(result, "添加成功");
		}else{
			return vaUserInfo;
		}
	}
	
	@ApiOperation(value="供应商修改子账号",notes="供应商修改子账号api")
	@RequestMapping(value="/updateSupplierUser", method=RequestMethod.PUT)
	public Map<String, Object> updateSupplierUser(@RequestBody Users user){
		Map<String, Object> vaUserInfo = usersService.vaUserInfo(user);
		if("success".equals(vaUserInfo.get("status"))){
			user.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			int result = usersService.updateNotNull(user);
			return ResultUtil.resultMessage(result, "修改成功");
		}else{
			return vaUserInfo;
		}
	}
	
	@ApiOperation(value="供应商删除子账号",notes="供应商删除子账号api")
	@RequestMapping(value="/deleteSupplierUser", method=RequestMethod.DELETE)
	public Map<String, Object> deleteSupplierUser(Long id){
		int result = usersService.updateIsDelte(id, 1);
		return ResultUtil.resultMessage(result, "");
	}
	
	@ApiOperation(value="供应商子账号列表",notes="供应商子账号列表api")
	@RequestMapping(value="/supplierUserList", method=RequestMethod.GET)
	public PageInfo<Users> supplierUserList(HttpServletRequest request, @ModelAttribute PageBean page){
		Users user = UserUtil.getUser(request);
		if(user != null && user.getType() != null && user.getType() == 4 && user.getTypeId() != null){
			PageHelper.startPage(page.getPage(), page.getRows());
			Example example=new Example(Users.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("isDelete", 0);
			createCriteria.andEqualTo("type", 4);
			createCriteria.andEqualTo("isKey", 0);
			createCriteria.andEqualTo("typeId", user.getTypeId());
			List<Users> selectByExample = usersService.selectByExample(example);
			if(CollectionUtils.isNotEmpty(selectByExample)){
				selectByExample.forEach(u -> {
					List<Roles> userRoles = userRolesService.getUserRoles(u.getId());
					if(CollectionUtils.isNotEmpty(userRoles)){
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < userRoles.size(); i++) {
							if(i != userRoles.size() - 1){
								sb.append(userRoles.get(i).getName() + "，");
							}else{
								sb.append(userRoles.get(i).getName());
							}
						}
						u.setRoleNames(sb.toString());
					}else{
						u.setRoleNames("");
					}
				});
			}
			return new PageInfo<Users>(selectByExample);
		}
		return null;
	}
	
	@ApiOperation(value="根据id查询子账号信息",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getSupplierUserById",method=RequestMethod.GET)
	public Users getSupplierUserById(Long id){
		Users selectByKey = usersService.selectByKey(id);
		selectByKey.setEncryptedPassword(null);
		return selectByKey;
	}
	
	@ApiOperation(value="供应商子账号角色查询",notes="供应商子账号角色查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="userId",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getSupplierUserRoles",method=RequestMethod.GET)
	public List<Roles> getSupplierUserRoles(Long userId){
		return userRolesService.getSupplierUserRoles(userId);
	}
	
	@ApiOperation(value="保存供应商子账号角色信息",notes="保存供应商子账号角色信息api")
	@RequestMapping(value="/saveSupplierUserRoles",method=RequestMethod.POST)
	public Map<String, Object> saveSupplierUserRoles(@RequestBody UserRoles userRoles){
		int result = userRolesService.saveAssignRoles(userRoles.getUserId(), userRoles.getRoleIds());
		return ResultUtil.resultMessage(result, "");
	}
}
