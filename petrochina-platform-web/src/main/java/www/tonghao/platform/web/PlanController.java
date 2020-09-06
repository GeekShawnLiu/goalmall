package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.Principal;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.entity.Plan;
import www.tonghao.platform.entity.PlanItem;
import www.tonghao.platform.service.PlanItemService;
import www.tonghao.platform.service.PlanService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  
* <p>Title: PlanController</p>  
* <p>Description: </p>  
* @author YML  
* @date 2018年12月11日  
*/ 
@Api(value="planController",description="计划单管理api")
@RestController
@RequestMapping("/planController")
public class PlanController {

	@Autowired
	private PlanService planService;
	
	@Autowired
	private PlanItemService planItemService;
	
	
	/**  
	 * <p>Title: list</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param page
	 * @param planCode
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @param department
	 * @param categoryName
	 * @return 
	 */
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="planCode",value="计划单编号",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="startDate",value="开始时间",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="endDate",value="结束时间",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="status",value="状态",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="department",value="采购单位",required=false,dataType="String",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<Plan> list(@ModelAttribute PageBean page, String planCode, String startDate,
			String endDate, Integer status, String department, String categoryName){
		Principal principal = null;
		if (principal != null) {
			PageHelper.startPage(page.getPage(), page.getRows());
			Map<String, Object> map = new HashMap<String, Object>();
			if (!principal.isSuperAdmin() && !principal.isPlatformAdmin()) {
				map.put("deptCode", principal.getDepartmentCode());
			}
			map.put("planCode", planCode);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("status", status);
			map.put("department", department);
			map.put("categoryName", categoryName);
			List<Plan> list = planService.find(map);
			return new PageInfo<Plan>(list);
		}
		return null;
	}
	
	/**  
	 * <p>Title: getById</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param id
	 * @return 
	 */
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Plan getById(Long id){
		Plan plan = planService.findById(id);
		return plan;
	}
	
	/**  
	 * <p>Title: planReturn</p>
	 * <p>Description: 计划单退回</p>  
	 * @author YML 
	 * @param planId
	 * @param planCode
	 * @return 
	 */
	@ApiOperation(value="计划单退回",notes="计划单退回api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="planId",value="计划单id",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="planCode",value="计划单编号",required=true,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/planReturn",method=RequestMethod.POST)
	public Map<String, Object> planReturn(Long id, String planId, String planCode){
		/*Principal principal = null;
		if (principal != null) {
			PushPlanApi pushPlanApi = new PushPlanApi(planCode, planId, "2");
			AccessRes res = pushPlanApi.getResult();
			if (res.getSuccess()) {
				Plan entity = new Plan();
				entity.setId(id);
				entity.setStatus(5);
				int result = planService.updateNotNull(entity);
				return ResultUtil.resultMessage(1, res.getMsg());
			}else {
				return ResultUtil.resultMessage(0, res.getMsg());
			}
		}else {*/
			return ResultUtil.resultMessage(0, "未登录");
		/*}*/
		
	}
	
	/**  
	 * <p>Title: planComplete</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param id
	 * @param planId
	 * @param planCode
	 * @return 
	 */
	@ApiOperation(value="计划单完成",notes="计划单完成api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="planId",value="计划单id",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="planCode",value="计划单编号",required=true,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/planComplete",method=RequestMethod.POST)
	public Map<String, Object> planComplete(Long id, String planId, String planCode){
		/*Principal principal = null;
		if (principal != null) {
			PushPlanApi pushPlanApi = new PushPlanApi(planCode, planId, "1");
			AccessRes res = pushPlanApi.getResult();
			if (res.getSuccess()) {
				Plan entity = new Plan();
				entity.setId(id);
				entity.setStatus(6);
				int result = planService.updateNotNull(entity);
				return ResultUtil.resultMessage(1, res.getMsg());
			}else {
				return ResultUtil.resultMessage(0, res.getMsg());
			}
		}else {*/
			return ResultUtil.resultMessage(0, "未登录");
		/*}*/
		
	}
	
	/**  
	 * <p>Title: save</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param plan
	 * @return 
	 */
	@ApiOperation(value="添加计划单",notes="添加计划单api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String,Object> save(@RequestBody Plan plan){
		int result = 0;
		Principal principal =null;
		if (principal != null) {
			plan.setDepartment(principal.getDepName());
			plan.setDepartmentCode(principal.getDepartmentCode());
			plan.setRegion(principal.getAreaId()+"");
			plan.setStatus(0);
			plan.setIsAdvance("0");
			plan.setProjectCode(plan.getPlanCode());
			plan.setIntegratedCost("0");
			plan.setAgency("山东省政府采购中心");
			plan.setAgencyCode("001");
			plan.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			result = planService.saveSelective(plan);
			if (result > 0) {
				List<PlanItem> items = plan.getItems();
				if (CollectionUtils.isNotEmpty(items)) {
					for (PlanItem planItem : items) {
						planItem.setPlanId(plan.getId());
						planItemService.saveSelective(planItem);
					}
				}
			}
		}
		return ResultUtil.resultMessage(result, "");
	}
	
	@ApiOperation(value="删除计划单",notes="删除计划单api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int result = 0;
		Principal principal = null;
		if (principal != null) {
			Plan plan = planService.findById(id);
			if (plan != null && StringUtils.isNotBlank(plan.getDepartmentCode()) 
					&& plan.getDepartmentCode().equals(principal.getDepartmentCode())) {
				result = planService.delete(id);
			}
		}
		return ResultUtil.resultMessage(result, "");
	}
}
