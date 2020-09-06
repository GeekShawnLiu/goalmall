package www.tonghao.platform.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.AccessToken;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.FileUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.IntegralGrant;
import www.tonghao.service.common.entity.IntegralOrg;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.IntegralGrantService;
import www.tonghao.service.common.service.OrganizationService;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.utils.UserUtil;

/**
 * @Description:积分发放
 * @date 2019年4月26日
 */
@Api(value="integralGrantController", description="积分发放")
@RestController
@RequestMapping("/integralGrant")
public class IntegralGrantController {
	
	@Autowired
	private IntegralGrantService integralGrantService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * @Description:积分发放列表
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="积分发放列表",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="userRealname",value="姓名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orgName",value="用户单位",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="state",value="状态",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="integralName",value="积分名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="batch",value="导入批次",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="email",value="邮箱",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="mobile",value="手机号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="loginName",value="用户名",required=false,dataType="string",paramType="query")
	})
	@RequestMapping(value="/getPageList",method=RequestMethod.GET)
	public PageInfo<IntegralGrant> getPageList(@ModelAttribute PageBean page, String userRealname, String orgName, Integer state, String integralName, 
			String activityName, String batch, String email, String mobile, String loginName){
		Map<String, Object> map=new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userRealname)){
			map.put("userRealname", userRealname);
		}
		if(StringUtils.isNotBlank(orgName)){
			map.put("orgName", orgName);
		}
		if(StringUtils.isNotBlank(integralName)){
			map.put("integralName", integralName);
		}
		if(StringUtils.isNotBlank(activityName)){
			map.put("activityName", activityName);
		}
		if(StringUtils.isNotBlank(batch)){
			map.put("batch", batch);
		}
		if(StringUtils.isNotBlank(email)){
			map.put("email", email);
		}
		if(StringUtils.isNotBlank(mobile)){
			map.put("mobile", mobile);
		}
		if(StringUtils.isNotBlank(loginName)){
			map.put("loginName", loginName);
		}
		map.put("state", state);
		PageInfo<IntegralGrant> selectList = integralGrantService.selectList(page, map);
		return selectList;
	}
	
	/**
	 * @Description:积分发放
	 * @date 2019年4月26日
	 */
	@ApiOperation(value="积分发放")
	@AccessToken(rolesCode ={"admin"})
	@RequestMapping(value="/grant", method=RequestMethod.POST)
	public Map<String, Object> grant(@RequestBody IntegralGrant integralGrant, HttpServletRequest request){
		Map<String, Object> result = integralGrantService.saveInfo(integralGrant);
		System.out.println(result.get("message"));
		System.out.println(result.get("status"));
		return result;
	}
	
	/**
	 * @Description:修改积分发放
	 * @date 2019年4月27日
	 */
	@ApiOperation(value="修改积分发放")
	@AccessToken(rolesCode ={"admin"})
	@RequestMapping(value="/updateGrantIntegral", method=RequestMethod.POST)
	public Map<String, Object> updateGrantIntegral(@RequestBody IntegralGrant integralGrant){
		IntegralGrant integralGrantInfo = integralGrantService.selectByKey(integralGrant.getId());
		if(integralGrantInfo.getState() == 0 || integralGrantInfo.getState() ==3){
			Map<String, Object> result = integralGrantService.updateInfo(integralGrant);
			return result;
		}
		return ResultUtil.error("对不起，该状态不能修改");
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
		List<Organization> organizationList = organizationService.selectByExample(example);
		return organizationList;
	}
	
	/**
	 * @Description:获取机构下的用户
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="获取机构下的用户")
	@RequestMapping(value="/getUserList", method=RequestMethod.GET)
	public List<Users> getUserList(Long orgId, String realName){
		List<Users> userList = integralGrantService.getUserList(orgId, realName);
		return userList;
	}
	
	/**
	 * @Description:提交审核
	 * @date 2019年4月30日
	 */
	@ApiOperation(value="提交审核")
	@AccessToken(rolesCode ={"admin"})
	@RequestMapping(value="/submitAudit", method=RequestMethod.POST)
	public Map<String, Object> submitAudit(@RequestBody Long[] ids){
		int result = 0;
		if(ids !=null && ids.length > 0){
			for(Long id : ids){
				IntegralGrant integralGrant = new IntegralGrant();
				integralGrant.setId(id);
				integralGrant.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				integralGrant.setState(1);
				integralGrant.setSubmitAudit(DateUtilEx.timeFormat(new Date()));
				int update = integralGrantService.updateNotNull(integralGrant);
				if(update > 0){
					result++;
				}
			}
		}
		return ResultUtil.success("成功提交审核：" + result + "个");
	}
	
	/**
	 * @Description:删除积分发放
	 * @date 2019年4月30日
	 */
	@ApiOperation(value="删除积分发放")
	@AccessToken(rolesCode ={"admin"})
	@RequestMapping(value="/delIntegralGrant", method=RequestMethod.GET)
	public Map<String, Object> delIntegralGrant(Long id){
		IntegralGrant integralGrant = integralGrantService.selectByKey(id);
		if(integralGrant.getState() == 0 || integralGrant.getState() ==3){
			return integralGrantService.deleteIntegralGrant(id);
		}
		return ResultUtil.error("对不起，该状态不能删除");
	}
	
	/**
	 * @Description:id获取积分发放信息
	 * @date 2019年5月1日
	 */
	@ApiOperation(value="id获取积分发放信息")
	@RequestMapping(value="/getIntegralGrantInfo", method=RequestMethod.GET)
	public IntegralGrant getIntegralGrantInfo(Long id){
		 IntegralGrant integralGrant = integralGrantService.selectByKey(id);
		return integralGrant;
	}
	
	/**
	 * @Description:积分id获取机构
	 * @date 2019年5月1日
	 */
	@ApiOperation(value="积分id获取机构")
	@RequestMapping(value="/getIntegralOrg", method=RequestMethod.GET)
	public List<IntegralOrg> getIntegralOrg(Long integralId){
		 List<IntegralOrg> selectIntegralOrg = integralGrantService.selectIntegralOrg(integralId);
		return selectIntegralOrg;
	}
	
	/**
	 * @Description:积分id获取活动
	 * @date 2019年5月1日
	 */
	@ApiOperation(value="积分id获取活动")
	@RequestMapping(value="/getActivity", method=RequestMethod.GET)
	public Activity getActivity(Long integralId){
		Activity activity = integralGrantService.selectActivityByIntegralId(integralId);
		return activity;
	}
	
	
	/**
	 * @Description:积分发放审核列表
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="积分发放审核列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="userRealname",value="姓名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orgName",value="用户单位",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="state",value="状态",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="integralName",value="积分名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="email",value="邮箱",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="mobile",value="手机号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="loginName",value="用户名",required=false,dataType="string",paramType="query")
	})
	@RequestMapping(value="/getIntegralGrantAuditList",method=RequestMethod.GET)
	public PageInfo<IntegralGrant> getIntegralGrantList(@ModelAttribute PageBean page, String userRealname, String orgName, Integer state, 
			String integralName, String activityName, String email, String mobile, String loginName){
		Map<String, Object> map=new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userRealname)){
			map.put("userRealname", userRealname);
		}
		if(StringUtils.isNotBlank(orgName)){
			map.put("orgName", orgName);
		}
		if(StringUtils.isNotBlank(integralName)){
			map.put("integralName", integralName);
		}
		if(StringUtils.isNotBlank(activityName)){
			map.put("activityName", activityName);
		}
		if(StringUtils.isNotBlank(email)){
			map.put("email", email);
		}
		if(StringUtils.isNotBlank(mobile)){
			map.put("mobile", mobile);
		}
		if(StringUtils.isNotBlank(loginName)){
			map.put("loginName", loginName);
		}
		map.put("state", state);
		map.put("orderBySubmitAudit", "1");
		map.put("notState", 0);
		PageInfo<IntegralGrant> selectList = integralGrantService.selectList(page, map);
		return selectList;
	}
	
	/**
	 * @Description:积分发放审核通过
	 * @date 2019年5月2日
	 */
	@ApiOperation(value="积分发放审核通过")
	@AccessToken(rolesCode ={"admin"})
	@RequestMapping(value="/auditPass", method=RequestMethod.POST)
	public Map<String, Object> auditPass(@RequestBody Long[] integralGrantIds, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		Map<String, Object> map = integralGrantService.audit(integralGrantIds, user);
		return map;
	}
	
	/**
	 * @Description:积分发放审核不通过
	 * @date 2019年5月2日
	 */
	@ApiOperation(value="积分发放审核不通过")
	@AccessToken(rolesCode ={"admin"})
	@RequestMapping(value="/auditNoPass", method=RequestMethod.POST)
	public Map<String, Object> auditNoPass(@RequestBody Long[] integralGrantIds, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		Map<String, Object> map = integralGrantService.auditNoPass(integralGrantIds, user);
		return map;
	}
	
	
	/**
	 * @Description:按搜索条件审核分发放
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="按搜索条件审核分发放")
	@AccessToken(rolesCode ={"admin"})
	@ApiImplicitParams({
		@ApiImplicitParam(name="auditType",value="审核类型（1：通过，2：不通过）",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="userRealname",value="用户名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orgName",value="用户单位",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="state",value="状态",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="integralName",value="积分名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query")
	})
	@RequestMapping(value="/conditionAudit",method=RequestMethod.GET)
	public Map<String, Object> conditionAudit(HttpServletRequest request, Integer auditType, String userRealname, String orgName, Integer state, String integralName, String activityName){
		if(auditType ==null){
			return ResultUtil.error("操作失败");
		}
		if(StringUtils.isBlank(userRealname) && StringUtils.isBlank(orgName) && state ==null && StringUtils.isBlank(integralName) && StringUtils.isBlank(activityName)){
			return ResultUtil.error("请选择条件");
		}
		
		Example example=new Example(IntegralGrant.class);
		Map<String, Object> maplike=new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userRealname)){
			maplike.put("userRealname", userRealname);
		}
		if(StringUtils.isNotBlank(orgName)){
			maplike.put("orgName", orgName);
		}
		if(StringUtils.isNotBlank(integralName)){
			maplike.put("integralName", integralName);
		}
		if(StringUtils.isNotBlank(activityName)){
			maplike.put("activityName", activityName);
		}
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		createCriteria.andEqualTo("state", 1); //审核中
		CriteriaLikeUtil.criteriaLike(createCriteria, maplike);
		List<IntegralGrant> list = integralGrantService.selectByExample(example);
		
		if(CollectionUtils.isNotEmpty(list)){
			List<Long> ids = new ArrayList<>();
			for(IntegralGrant ig : list){
				ids.add(ig.getId());
			}
			Long[] integralGrantIds =  ids.toArray(new Long[list.size()]);
			
			Users user = UserUtil.getUser(request);
			//审核通过
			if(auditType == 1){
				Map<String, Object> map = integralGrantService.audit(integralGrantIds, user);
				return map;
			//审核不通过
			}else if(auditType == 2){
				Map<String, Object> map = integralGrantService.auditNoPass(integralGrantIds, user);
				return map;
			}
		}
		return ResultUtil.error("");
	}
	
	/**
	 * @Description:Excel批量导入积分发放
	 * @date 2019年5月8日
	 */
	@ApiOperation(value="Excel批量导入积分发放")
	@RequestMapping(value="/uploadIntegralGrantExcle", method=RequestMethod.POST)
	public Map<String, Object> uploadIntegralGrantExcle(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = integralGrantService.importIntegralGrantExcle(request, response);
		return result;
	}
	
	@ApiOperation(value="下载模板")
	@RequestMapping(value="/downloadTemplate", method=RequestMethod.GET)
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ClassPathResource classPathResource = new ClassPathResource("templateExample/integralGrantTemplate.xlsx");
		InputStream inputStream = classPathResource.getInputStream();
		FileUtilEx.downloadFile(inputStream, response, "积分发放模板.xlsx");
	}
	
	/**
	 * @Description:id获取机构信息
	 * @date 2019年5月14日
	 */
	@ApiOperation(value="id获取机构信息")
	@RequestMapping(value="/getOrg", method=RequestMethod.GET)
	public Organization getOrg(Long orgId){
		Organization organization = new Organization();
		organization.setId(orgId);
		organization.setIsDelete(false);
		Organization selectEntityOne = organizationService.selectEntityOne(organization);
		return selectEntityOne;
	}
	
	/**
	 * @Description:搜索条件提交审核
	 * @date 2019年8月14日
	 */
	@ApiOperation(value="搜索条件提交审核",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userRealname",value="姓名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orgName",value="用户单位",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="integralName",value="积分名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="batch",value="导入批次",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="email",value="邮箱",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="mobile",value="手机号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="loginName",value="用户名",required=false,dataType="string",paramType="query")
	})
	@RequestMapping(value="/conditionSubmitAudit",method=RequestMethod.GET)
	public Map<String, Object> conditionSubmitAudit(String userRealname, String orgName, String integralName, 
			String activityName, String batch, String email, String mobile, String loginName){
		Map<String, Object> map=new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userRealname)){
			map.put("userRealname", userRealname);
		}
		if(StringUtils.isNotBlank(orgName)){
			map.put("orgName", orgName);
		}
		if(StringUtils.isNotBlank(integralName)){
			map.put("integralName", integralName);
		}
		if(StringUtils.isNotBlank(activityName)){
			map.put("activityName", activityName);
		}
		if(StringUtils.isNotBlank(batch)){
			map.put("batch", batch);
		}
		if(StringUtils.isNotBlank(email)){
			map.put("email", email);
		}
		if(StringUtils.isNotBlank(mobile)){
			map.put("mobile", mobile);
		}
		if(StringUtils.isNotBlank(loginName)){
			map.put("loginName", loginName);
		}
		map.put("state", 0);
		Map<String, Object> result = integralGrantService.conditionSubmitAudit(map);
		return result;
	}
}
