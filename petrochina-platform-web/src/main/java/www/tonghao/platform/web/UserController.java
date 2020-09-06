package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.SysLog;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.FileUtilEx;
import www.tonghao.common.utils.IpAddressUtil;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.mall.core.ApiCommon;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UserRoles;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.RolesService;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.service.common.service.UserRolesService;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: UserController</p>  

* <p>Description: 用户管理后台控制类</p>  

* @author Yml  

* @date 2018年10月26日  

*/  
@Api(value="userController",description="用户管理api")
@RestController
@RequestMapping("/userController")
public class UserController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserRolesService userRolesService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private SuppliersService suppliersService;
	
	@Autowired
	private RedisDao redisDao;
	
	/**  
	 * <p>Title: list</p>  
	 * <p>Description: 分页查询用户列表</p>  
	 * @author Yml  
	 * @param user
	 * @param page
	 * @return  
	 */
	@SysLog(opt = "分页查询用户列表")
	@ApiOperation(value="分页查询列表",notes="获取用户列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="loginName",value="登录名",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="realName",value="真实姓名",required=false,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<Users> list(@ModelAttribute PageBean page, String loginName, String realName){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example = new Example(Users.class);
		Criteria criteria = example.createCriteria();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", loginName);
		map.put("realName", realName);
		CriteriaLikeUtil.criteriaLike(criteria, map);
		example.setOrderByClause("created_at desc");
		criteria.andEqualTo("isDelete", 0);
		criteria.andEqualTo("type", 6);
		List<Users> list = usersService.selectByExample(example);
		return new PageInfo<Users>(list);
	}
	
	/**  
	 * <p>Title: save</p>  
	 * <p>Description: 保存用户信息</p>  
	 * @author Yml  
	 * @param user
	 * @return  
	 */  
	@SysLog(opt = "添加用户")
	@ApiOperation(value="添加用户",notes="添加用户api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String,Object> save(@RequestBody Users user){
		Map<String, Object> vaUserInfo = usersService.vaUserInfo(user);
		if("success".equals(vaUserInfo.get("status"))){
			int result = usersService.saveUser(user);
			Roles roles =null;
			if(user.getType()==4){
				Suppliers supplier = suppliersService.selectByKey(user.getTypeId());
				Roles entity=new Roles();
				if(!ApiCommon.checkCode(supplier.getCode())){
					entity.setCode("BD_GYS");
				}else{
					entity.setCode("TH_GYS");
				}
				roles = rolesService.selectEntityOne(entity);
				
			}else if(user.getType()==1){
				Roles entity=new Roles();
				entity.setCode("CGR");
				roles = rolesService.selectEntityOne(entity);
			}
			if(roles!=null){
				UserRoles ur=new UserRoles();
				ur.setCreatedAt(DateUtilEx.timeFormat(new Date()));
				ur.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				ur.setRoleId(roles.getId());
				ur.setUserId(user.getId());
				userRolesService.save(ur);
			}
			return ResultUtil.resultMessage(result, "添加成功");
		}else{
			return vaUserInfo;
		}
	}
	
	/**  
	 * <p>Title: update</p>  
	 * <p>Description: 更新用户</p>  
	 * @author Yml  
	 * @param user
	 * @return  
	 */  
	@SysLog(opt = "更新用户")
	@ApiOperation(value="更新用户",notes="更新用户api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String,Object> update(@RequestBody Users user){
		Map<String, Object> vaUserInfo = usersService.vaUserInfo(user);
		if("success".equals(vaUserInfo.get("status"))){
			user.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			int result = usersService.updateNotNull(user);
			return ResultUtil.resultMessage(result, "修改成功");
		}else{
			return vaUserInfo;
		}
	}
	
	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: 删除用户</p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@SysLog(opt = "删除用户")
	@ApiOperation(value="删除用户",notes="删除用户api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int result = usersService.delete(id);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: updateIsDelte</p>  
	 * <p>Description: 更新用户删除状态</p>  
	 * @author Yml  
	 * @param user
	 * @return  
	 */  
	@ApiOperation(value="更新用户删除状态",notes="更新用户删除状态api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="用户id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="isDelete",value="是否删除，0:未删除，1:已删除",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/updateIsDelte",method=RequestMethod.POST)
	public Map<String,Object> updateIsDelte(@RequestParam(name="id") Long id, @RequestParam(name="isDelete") Integer isDelete){
		int result = usersService.updateIsDelte(id, isDelete);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**
	 * 
	 * Description: 批量删除用户（逻辑删除）
	 * 
	 * @data 2019年5月20日
	 * @param 
	 * @return
	 */
	@ApiOperation(value="批量删除用户（逻辑删除）",notes="批量删除用户（逻辑删除）api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="ids",value="用户ids",required=true,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/batchUpdateIsDelte",method=RequestMethod.POST)
	public Map<String,Object> batchUpdateIsDelte(String ids){
		if(!StringUtils.isEmpty(ids)){
			String[] split = ids.split(",");
			for (String string : split) {
				usersService.updateIsDelte(Long.parseLong(string), 1);
			}
		}
		return ResultUtil.resultMessage(1, "");
	}
	
	/**  
	 * <p>Title: checkLoginName</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @param loginName
	 * @return  
	 */  
	@ApiOperation(value="校验用户名是否重复",notes="校验用户名是否重复api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="用户id",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="loginName",value="用户名",required=true,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/checkLoginName",method=RequestMethod.POST)
	public Map<String,Object> checkLoginName(Long id, String loginName){
		long result = usersService.checkLoginName(id, loginName);
		if (result > 0) {
			return ResultUtil.resultMessage(ResultUtil.ERROR, "用户名已存在");
		}else {
			return ResultUtil.resultMessage(ResultUtil.MESSAGE, "用户名可用");
		}
	}
	
	/**  
	 * <p>Title: getById</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Users getById(Long id){
		Users users = usersService.selectByKey(id);
		if(users != null && users.getType() == 4 && users.getTypeId() != null){
			Suppliers supplier = suppliersService.selectByKey(users.getTypeId());
			if(supplier != null){
				users.setSupplier(supplier);
			}
		}
		return users;
	}
	
	/**  
	 * <p>Title: getAssignRoles</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param userId
	 * @return  
	 */  
	@ApiOperation(value="查询分配用户角色",notes="查询分配用户角色api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="int",paramType="query")
	})
	@RequestMapping(value="/getAssignRoles",method=RequestMethod.GET)
	public List<Roles> getAssignRoles(Long userId){
		List<Roles> list = userRolesService.getAssignRoles(userId);
		return list;
	}
	
	/**  
	 * <p>Title: saveAssignRoles</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param userRoles
	 * @return  
	 */  
	@ApiOperation(value="保存分配用户角色",notes="保存分配用户角色api")
	@RequestMapping(value="/saveAssignRoles",method=RequestMethod.POST)
	public Map<String, Object> saveAssignRoles(@RequestBody UserRoles userRoles){
		int result = userRolesService.saveAssignRoles(userRoles.getUserId(), userRoles.getRoleIds());
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: deleteAssignRoles</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param userRoles
	 * @return  
	 */  
	@ApiOperation(value="删除分配用户角色",notes="删除分配用户角色api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="roleIds",value="角色id",required=true,dataType="int",paramType="query")
	})
	@RequestMapping(value="/deleteAssignPermissions",method=RequestMethod.DELETE)
	public Map<String, Object> deleteAssignRoles(UserRoles userRoles){
		int result = userRolesService.deleteAssignRoles(userRoles);
		return ResultUtil.resultMessage(result, "");
	}
	
	/*@ApiOperation(value="重置密码",notes="用户重置密码api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="用户id",required=true,dataType="long",paramType="query"),
		@ApiImplicitParam(name="encryptedPassword",value="新密码",required=true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="oldPassword",value="旧密码",required=true,dataType="String",paramType="query")
	})
	@RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	public Map<String, Object> resetPassword(@RequestParam(value="id") Long id, @RequestParam(value="encryptedPassword") String encryptedPassword, @RequestParam(value="oldPassword") String oldPassword){
		Boolean status = usersService.checkPassword(id, oldPassword);
		if (!status) {
			return ResultUtil.error("旧密码输入错误");
		} else {
			int result = usersService.resetPassword(id, encryptedPassword);
			return ResultUtil.resultMessage(result, "");
		}
	}
	
	@ApiOperation(value="管理员重置用户密码",notes="管理员重置用户密码api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="用户id",required=true,dataType="long",paramType="query"),
		@ApiImplicitParam(name="encryptedPassword",value="新密码",required=true,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/resetUserPassword",method=RequestMethod.POST)
	public Map<String, Object> resetUserPassword(@RequestParam(value="id") Long id, @RequestParam(value="encryptedPassword") String encryptedPassword){
		int result = usersService.resetPassword(id, encryptedPassword);
		return ResultUtil.resultMessage(result, "");
	}*/
	
	/**  
	 * <p>Title: getUserSystems</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param userId
	 * @return  
	 */  
	/*@ApiOperation(value="查询系统授权",notes="查询系统授权api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="int",paramType="query")
	})
	@RequestMapping(value="/getUserSystems",method=RequestMethod.GET)
	public List<Permissions> getUserSystems(Long userId){
		List<Permissions> list = usersService.getUserSystems(userId);
		return list;
	}*/
	
	@ApiOperation(value="查询系统授权",notes="查询系统授权api")
	@RequestMapping(value="/getUserSystems",method=RequestMethod.GET)
	public List<TreeNode> getUserSystems(HttpServletRequest request){
		String ipAddress = IpAddressUtil.getIpAddress(request);
		Users user = (Users) redisDao.getValue(request.getHeader("token")+"@"+ipAddress);
		return user.getList();
	}
	
	/**  
	 * <p>Title: saveUserSystems</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param userId
	 * @param systemIds
	 * @return  
	 */  
	@ApiOperation(value="保存系统授权",notes="保存系统授权api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="systemIds",value="系统id数组字符串",required=true,dataType="String",paramType="query")
	})
	@RequestMapping(value="/saveUserSystems",method=RequestMethod.POST)
	public Map<String, Object> saveUserSystems(@RequestParam(name="userId") Long userId, @RequestParam(name="systemIds") String systemIds){
		int result = usersService.saveUserSystems(userId, systemIds);
		return ResultUtil.resultMessage(result, "");
	}
	
	
	@ApiOperation(value="根据机构id查询",notes="根据机构id查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="机构id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getOrgByDep",method=RequestMethod.GET)
	public List<Users> getOrgByDep(Long id){
		Example example=new Example(Users.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("depId", id);
		createCriteria.andEqualTo("isDelete", 0);
		createCriteria.andEqualTo("type", 1);
		List<Users> list = usersService.selectByExample(example);
		return list;
	}
	
	/**  
	 * <p>Title: updateSuningAccount</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param id
	 * @param suningAccount
	 * @return 
	 */
	@ApiOperation(value="更新苏宁账号",notes="更新苏宁账号api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="用户id",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="suningAccount",value="苏宁账号",required=true,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/updateSuningAccount",method=RequestMethod.POST)
	public Map<String, Object> updateSuningAccount(Long id, String suningAccount){
		Users user = new Users();
		user.setId(id);
		user.setSuningAccount(suningAccount);
		user.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = usersService.updateNotNull(user);
		return ResultUtil.resultMessage(result, "");
	}
	
	@SuppressWarnings("resource")
	@ApiOperation(value="Excel批量导入用户",notes="Excel批量导入用户api")
	@RequestMapping(value="/uploadUserExcel")
	public Map<String, Object> uploadUserExcel(HttpServletRequest request, HttpServletResponse response){
		String orgName = request.getParameter("orgName");
		Long orgId = Long.parseLong(request.getParameter("orgId"));
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFileMap().entrySet()
				.iterator().next().getValue();
		XSSFWorkbook workbook = null;
		//查询库里面所有的用户
		Example example = new Example(Users.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("isDelete", 0);
		List<Users> userList = usersService.selectByExample(example);
		Set<String> loginNameSet = userList.stream().map(Users :: getLoginName).collect(Collectors.toSet());
		Set<String> mobileSet = userList.stream().map(Users :: getMobile).collect(Collectors.toSet());
		Set<String> emailSet = userList.stream().map(Users :: getEmail).collect(Collectors.toSet());
		/*Set<String> idCardSet = userList.stream().map(Users :: getIdCard).collect(Collectors.toSet());
		Set<String> codeSet = userList.stream().map(Users :: getCode).collect(Collectors.toSet());*/
		try {
			workbook = new XSSFWorkbook(FileUtilEx.multipartFileToTmpFile(multipartFile));
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			return ResultUtil.error("导入失败");
		} catch (IOException e) {
			e.printStackTrace();
			return ResultUtil.error("导入失败");
		}
		XSSFSheet sheet = workbook.getSheetAt(0);
		int i=1;
		List<Users> list=new ArrayList<Users>();
		boolean flg=true;
		String mesage="";
		Set<String> loginName_set=new HashSet<>();
		Set<String> mobile_set=new HashSet<>();
		Set<String> email_set=new HashSet<>();
		/*Set<String> idCard_set=new HashSet<>();
		Set<String> code_set=new HashSet<>();*/
		String date = DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN);
		while ( i <= sheet.getLastRowNum() ) {
			XSSFRow row = sheet.getRow(i);
			if (row == null) {
				break;
			}
			if(row.getCell(0) != null){
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			}
			if(row.getCell(1) != null){
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			}
			if(row.getCell(2) != null){
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			}
			if(row.getCell(3) != null){
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			}
			if(row.getCell(4) != null){
				row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			}
			if(row.getCell(5) != null){
				row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			}
			if(row.getCell(6) != null){
				row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			}
			String loginName = row.getCell(0)==null?null:row.getCell(0).getStringCellValue();
			String realName = row.getCell(1)==null?null:row.getCell(1).getStringCellValue();
			String mobile = row.getCell(2)==null?null:row.getCell(2).getStringCellValue();
			String email = row.getCell(3)==null?null:row.getCell(3).getStringCellValue();
			String idCard = row.getCell(4)==null?null:row.getCell(4).getStringCellValue();
			String code = row.getCell(5)==null?null:row.getCell(5).getStringCellValue();
			String department = row.getCell(6)==null?null:row.getCell(6).getStringCellValue();
            if(loginName==null||"".equals(loginName.trim())) {
            	flg=false;
            	mesage="登录名为空";
            	break;
			}else {
				if(loginNameSet.contains(loginName)){
					flg=false;
	            	mesage="登录名重复:"+loginName;
	            	break;
				}else{
					loginName_set.add(loginName);
				}
			}
            if(realName==null||"".equals(realName.trim())) {
            	flg=false;
            	mesage="真实姓名为空";
            	break;
			}
            if(mobile==null||"".equals(mobile.trim())) {
            	flg=false;
            	mesage="手机号为空";
            	break;
			}else {
				if(mobileSet.contains(mobile)){
					flg=false;
	            	mesage="手机号重复:"+mobile;
	            	break;
				}else{
					mobile_set.add(mobile);
				}
				
			}
            if(email==null||"".equals(email.trim())) {
            	flg=false;
            	mesage="邮箱为空";
            	break;
			}else {
				if(emailSet.contains(email)){
					flg=false;
	            	mesage="邮箱重复:"+email;
	            	break;
				}else{
					email_set.add(email);
				}
			}
            /*if(idCard==null||"".equals(idCard.trim())) {
            	flg=false;
            	mesage="身份证号码为空";
            	break;
			}else {
				if(idCardSet.contains(idCard)){
					flg=false;
	            	mesage="身份证号码重复";
	            	break;
				}else{
					idCard_set.add(idCard);
				}
			}*/
            /*if(code==null||"".equals(code.trim())) {
            	flg=false;
            	mesage="员工编号为空";
            	break;
			}else {
				if(codeSet.contains(code)){
					flg=false;
	            	mesage="员工编号重复";
	            	break;
				}else{
					code_set.add(code);
				}
			}*/
            
            if(loginName_set.size()!=i) {
            	flg=false;
            	mesage="登录名重复";
            	break;
            }
            if(mobile_set.size()!=i) {
            	flg=false;
            	mesage="手机号重复";
            	break;
            }
            if(email_set.size()!=i) {
            	flg=false;
            	mesage="邮箱重复";
            	break;
            }
            /*if(idCard_set.size()!=i) {
            	flg=false;
            	mesage="身份证重复";
            	break;
            }
            if(code_set.size()!=i) {
            	flg=false;
            	mesage="员工编号重复";
            	break;
            }*/
            
			Users user = new Users();
			user.setLoginName(loginName.trim());
			//密码加密
			user.setRealName(realName.trim());
			user.setMobile(mobile.trim());
			user.setEmail(email.trim());
			user.setIdCard(idCard);
			user.setCode(code);
			user.setCreatedAt(date);
			user.setUpdatedAt(date);
			user.setType(1);
			user.setTypeId(orgId);
			user.setDepId(orgId);
			user.setDepName(orgName);
			user.setIsDelete(0);
			user.setDepartment(department);
			list.add(user);
            i++;
		}
		System.out.println(flg+"-=-=-=-=-=-=");
		if(flg) {
			ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
			newCachedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					if(!CollectionUtils.isEmpty(list)) {
						Roles entity=new Roles();
						entity.setCode("CGR");
						Roles roles = rolesService.selectEntityOne(entity);
						for (Users users:list) {
							Users us=new Users();
							us.setEmail(users.getEmail());
							Users usEntity = usersService.selectEntityOne(us);
							if(usEntity!=null){
								usEntity.setLoginName(users.getLoginName());
								//密码加密
								usEntity.setRealName(users.getRealName());
								usEntity.setMobile(users.getMobile());
								usEntity.setEmail(users.getEmail());
								usEntity.setIdCard(users.getIdCard());
								usEntity.setCode(users.getCode());
								usEntity.setUpdatedAt(date);
								usEntity.setType(1);
								usEntity.setTypeId(orgId);
								usEntity.setDepId(orgId);
								usEntity.setDepName(orgName);
								usEntity.setIsDelete(0);
								usEntity.setDepartment(users.getDepartment());
								usersService.updateNotNull(usEntity);
							}else{
								usersService.save(users);	
								if(roles!=null) {
									UserRoles ur=new UserRoles();
									ur.setCreatedAt(date);
									ur.setUpdatedAt(date);
									ur.setRoleId(roles.getId());
									ur.setUserId(users.getId());
									userRolesService.save(ur);
								}
							}
						}
					}
				}
			});
			return ResultUtil.success("导入成功");
		}else {
			return ResultUtil.error(mesage+",上传失败");
		}
	}
	
	
	@ApiOperation(value="下载模板")
	@RequestMapping(value="/downloadTemplate", method=RequestMethod.GET)
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ClassPathResource classPathResource = new ClassPathResource("templateExample/userTemplate.xlsx");
		InputStream inputStream = classPathResource.getInputStream();
		FileUtilEx.downloadFile(inputStream, response, "导入用户模板.xlsx");
	}
	
	@ApiOperation(value="个人中心用户信息")
	@RequestMapping(value="/userInfo", method=RequestMethod.GET)
	public Users userInfo(HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null){
			return usersService.selectByKey(user.getId());
		}
		return null;
	}
	
	@ApiOperation(value="修改密码")
	@RequestMapping(value="/updatePassword", method=RequestMethod.PUT)
	public Map<String, Object> updatePassword(HttpServletRequest request, @RequestBody Users user){
		Users loginUser = UserUtil.getUser(request);
		if(loginUser != null && user != null){
			user.setId(loginUser.getId());
			return usersService.updatePassword(user);
		}else{
			return ResultUtil.error("用户未登录"); 
		}
	}

	@ApiOperation(value="修改手机号")
	@RequestMapping(value="/updateMobile", method=RequestMethod.PUT)
	public Map<String, Object> updateMobile(HttpServletRequest request, @RequestBody Users user){
		Users loginUser = UserUtil.getUser(request);
		if(loginUser != null && user != null){
			user.setId(loginUser.getId());
			Users selectByKey = usersService.selectByKey(user.getId());
			return usersService.updateMobile(user, selectByKey.getMobile());
		}else{
			return ResultUtil.error("用户未登录"); 
		}
	}
	
	@ApiOperation(value="修改用户信息")
	@RequestMapping(value="/updateUserInfo", method=RequestMethod.PUT)
	public Map<String, Object> updateUserInfo(HttpServletRequest request, @RequestBody Users user){
		Users loginUser = UserUtil.getUser(request);
		if(loginUser != null && user != null){
			user.setId(loginUser.getId());
			int updateNotNull = usersService.updateNotNull(user);
			if(updateNotNull > 0){
				return ResultUtil.success("修改成功");
			}else{
				return ResultUtil.success("修改失败");
			}
		}else{
			return ResultUtil.error("用户未登录"); 
		}
	}
}
