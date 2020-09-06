package www.tonghao.platform.web;

import java.math.BigDecimal;
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

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.AccessToken;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.Integral;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.service.ActivityService;
import www.tonghao.service.common.service.IntegralGrantService;
import www.tonghao.service.common.service.IntegralOrgService;
import www.tonghao.service.common.service.IntegralService;

/**
 * @Description:积分
 * @date 2019年4月26日
 */
@Api(value="integralController", description="积分")
@RestController
@RequestMapping("/integral")
public class IntegralController {
	
	@Autowired
	private IntegralService integralService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private IntegralGrantService integralGrantService;
	
	@Autowired
	private IntegralOrgService integralOrgService;
	
	/**
	 * @Description:积分列表
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="name",value="积分名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityStartAt",value="活动开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityEndAt",value="活动结束时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="isIntegralUser",value="是否分配用户（1是，2否）",required=false,dataType="int",paramType="query")
	})
	@RequestMapping(value="/getIntegralList",method=RequestMethod.GET)
	public PageInfo<Integral> getIntegralList(@ModelAttribute PageBean page, String name, String activityName, String activityStartAt, String activityEndAt, Integer isIntegralUser){
		Integral integral = new Integral();
		if(StringUtils.isNotBlank(name)){
			integral.setName(name);
		}
		if(StringUtils.isNotBlank(activityName)){
			integral.setActivityName(activityName);
		}
		if(StringUtils.isNotBlank(activityStartAt)){
			integral.setActivityStartAt(activityStartAt);
		}
		if(StringUtils.isNotBlank(activityEndAt)){
			integral.setActivityEndAt(activityEndAt);
		}
		if(isIntegralUser !=null){
			integral.setIsIntegralUser(isIntegralUser);
		}
		PageInfo<Integral> selectList = integralService.selectIntegralList(page, integral);
		return selectList;
	}
	
	/**
	 * @Description:登记积分
	 * @date 2019年4月26日
	 */
	@ApiOperation(value="登记积分")
	/*@ApiImplicitParams({
		@ApiImplicitParam(name="total",value="积分",required=true,dataType="BigDecimal",paramType="query"),
	})*/
	@AccessToken(rolesCode ={"admin"})
	@RequestMapping(value="/registrationIntegral", method=RequestMethod.POST)
	public Map<String, Object> registrationIntegral(@RequestBody Integral integral, HttpServletRequest request){
		Map<String, Object> result = integralService.registrationIntegral(integral);
		return result;
	}
	
	/**
	 * @Description:积分登记修改
	 * @date 2019年4月27日
	 */
	@ApiOperation(value="修改积分")
	@AccessToken(rolesCode ={"admin"})
	@RequestMapping(value="/updateIntegral", method=RequestMethod.POST)
	public Map<String, Object> updateIntegral(@RequestBody Integral integral){
		Integral selectByKey = integralService.selectByKey(integral.getId());
		if(selectByKey !=null && selectByKey.getGrantQuota().compareTo(BigDecimal.ZERO) == 0){
			Map<String, Object> checkIsGrant = checkIsGrant(integral.getId());
			if("success".equals(checkIsGrant.get("status"))){
				Map<String, Object> result = integralService.updateIntegral(integral);
				return result;
			}
			return checkIsGrant;
		}
		return ResultUtil.error("该积分已被分配,无法修改");
	}
	
	/**
	 * @Description:假删除积分
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="删除积分")
	@AccessToken(rolesCode ={"admin"})
	@RequestMapping(value="/removeIntegral", method=RequestMethod.DELETE)
	public Map<String, Object> updateIntegral(Long id){
		Integral selectByKey = integralService.selectByKey(id);
		if(selectByKey !=null && selectByKey.getGrantQuota().compareTo(BigDecimal.ZERO) == 0){
			Integral integral = new Integral();
			integral.setId(id);
			integral.setIsDelete(1);
			int result = integralService.updateNotNull(integral);
			return ResultUtil.resultMessage(result, "");
		}
		return ResultUtil.error("删除失败，该积分已被分配");
	}
	
	/**
	 * @Description:获取所有登记积分
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="获取所有登记积分")
	@RequestMapping(value="/getIntegralAll", method=RequestMethod.GET)
	public List<Integral> getIntegralAll(){
		List<Integral> list = integralService.selectByUsableActivity();
		return list;
	}
	
	/**
	 * @Description:获取机构下的积分活动
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="获取机构下的积分活动")
	@RequestMapping(value="/getActivityByOrg", method=RequestMethod.POST)
	public List<Activity> getActivityByOrg(@RequestBody Long[] orgIds){
		List<Activity> activityList = null;
		if(orgIds.length > 0){
			activityList = activityService.selectActivityByOrg(orgIds);
		}
		return activityList;
	}
	
	/**
	 * @Description:id获取积分信息
	 * @date 2019年5月1日
	 */
	@ApiOperation(value="id获取积分信息")
	@RequestMapping(value="/getIntegralInfo", method=RequestMethod.GET)
	public Map<String, Object> getIntegralInfo(Long integralId){
		Integral integral = integralService.selectByKey(integralId);
		if(integral !=null && integral.getGrantQuota().compareTo(BigDecimal.ZERO) == 0){
			/*List<IntegralOrg> integralOrgList = integralGrantService.selectIntegralOrg(integral.getId());
			integral.setIntegralOrgList(integralOrgList);
			
			StringBuilder sb = new StringBuilder();
			if(CollectionUtils.isNotEmpty(integralOrgList)){
				integralOrgList.forEach(org ->{
					sb.append(org.getOrgName() + "，");
				});
			}
			String orgName = sb.substring(0, sb.length()-1);
			integral.setOrgName(orgName);*/
			return ResultUtil.resultMessage(HttpResponseCode.OK, "", integral);
		}
		return ResultUtil.error("对不起，该积分已被分配");
	}
	
	/**
	 * @Description:检查是否发放过
	 * @date 2019年5月1日
	 */
	@ApiOperation(value="检查是否发放过")
	@RequestMapping(value="/checkIsGrant", method=RequestMethod.GET)
	public Map<String, Object> checkIsGrant(Long integralId){
		Integral integral = integralService.selectByKey(integralId);
		if(integral !=null && integral.getGrantQuota().compareTo(BigDecimal.ZERO) == 0){
			return ResultUtil.success("");
		}
		return ResultUtil.error("对不起，该积分已被分配");
	}
	
	/**
	 * @Description:获取机构树
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="获取机构树")
	@RequestMapping(value="/getOrgTree", method=RequestMethod.GET)
	public List<Organization> getOrgTree(Long integralId){
		List<Organization> organizationList = integralService.getOrgTree(integralId);
		return organizationList;
	}
}
