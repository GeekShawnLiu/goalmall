package www.tonghao.platform.web;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.warpper.SelectOption;
import www.tonghao.config.OrderStatusConfig;
import www.tonghao.service.common.entity.IntegralConsume;
import www.tonghao.service.common.entity.IntegralUser;
import www.tonghao.service.common.service.IntegralUserService;

import com.github.pagehelper.PageInfo;

/**
 * @Description:用户积分(消费明细)
 * @date 2019年5月6日
 */
@Api(value="integralUserController", description="用户积分")
@RestController
@RequestMapping("/integralUser")
public class IntegralUserController {
	
	@Autowired
	private IntegralUserService integralUserService;
	
	@Autowired
	private OrderStatusConfig orderStatusConfig;
	
	@ApiOperation(value="用户积分",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="realName",value="姓名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="integralName",value="积分名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="mobile",value="手机号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="email",value="邮箱",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="startTotal",value="开始的积分总额",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="endTotal",value="结束的积分总额",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="startBalance",value="开始的积分余额",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="endBalance",value="结束的积分余额",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="startConsumed",value="开始的已消费积分",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="endConsumed",value="结束的已消费积分",required=false,dataType="BigDecimal",paramType="query"),
	})
	@RequestMapping(value="/getIntegralUser",method=RequestMethod.GET)
	public PageInfo<IntegralUser> getAllIntegralUser(@ModelAttribute PageBean page, String realName, String integralName, String activityName, 
			String mobile, String email, HttpServletRequest request, BigDecimal startTotal, BigDecimal endTotal, BigDecimal startBalance, BigDecimal endBalance,
			BigDecimal startConsumed, BigDecimal endConsumed){
		IntegralUser integralUser = new IntegralUser();
		if(StringUtils.isNotBlank(realName)){
			integralUser.setRealName(realName);
		}
		if(StringUtils.isNotBlank(integralName)){
			integralUser.setIntegralName(integralName);
		}
		if(StringUtils.isNotBlank(activityName)){
			integralUser.setActivityName(activityName);
		}
		if(StringUtils.isNotBlank(mobile)){
			integralUser.setMobile(mobile);
		}
		if(StringUtils.isNotBlank(email)){
			integralUser.setEmail(email);
		}
		integralUser.setStartTotal(startTotal);
		integralUser.setEndTotal(endTotal);
		integralUser.setStartBalance(startBalance);
		integralUser.setEndBalance(endBalance);
		integralUser.setStartConsumed(startConsumed);
		integralUser.setEndConsumed(endConsumed);
		//integralUser.setUserId(user.getId());
		PageInfo<IntegralUser> selectAllIntegralUser = integralUserService.selectAllIntegralUser(page, integralUser);
		return selectAllIntegralUser;
	}
	

	/**
	 * @Description:导出excel
	 * @date 2019年5月7日
	 */
	@ApiOperation(value="导出excel",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="realName",value="姓名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="integralName",value="积分名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="mobile",value="手机号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="email",value="邮箱",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="startTotal",value="开始的积分总额",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="endTotal",value="结束的积分总额",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="startBalance",value="开始的积分余额",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="endBalance",value="结束的积分余额",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="startConsumed",value="开始的已消费积分",required=false,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="endConsumed",value="结束的已消费积分",required=false,dataType="BigDecimal",paramType="query"),
	})
	@RequestMapping(value="/exportExcel",method=RequestMethod.GET)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String realName, String integralName, String activityName, 
			String mobile, String email, BigDecimal startTotal, BigDecimal endTotal, BigDecimal startBalance, BigDecimal endBalance,
			BigDecimal startConsumed, BigDecimal endConsumed){
		IntegralUser integralUser = new IntegralUser();
		if(StringUtils.isNotBlank(realName) && !"undefined".equals(realName)){
			integralUser.setRealName(realName);
		}
		if(StringUtils.isNotBlank(integralName) && !"undefined".equals(integralName)){
			integralUser.setIntegralName(integralName);
		}
		if(StringUtils.isNotBlank(activityName) && !"undefined".equals(activityName)){
			integralUser.setActivityName(activityName);
		}
		if(StringUtils.isNotBlank(mobile)){
			integralUser.setMobile(mobile);
		}
		if(StringUtils.isNotBlank(email)){
			integralUser.setEmail(email);
		}
		integralUser.setStartTotal(startTotal);
		integralUser.setEndTotal(endTotal);
		integralUser.setStartBalance(startBalance);
		integralUser.setEndBalance(endBalance);
		integralUser.setStartConsumed(startConsumed);
		integralUser.setEndConsumed(endConsumed);
		integralUserService.exportExcel(request, response, integralUser);
	}
	
	/**
	 * @Description:查询积分消费
	 * @date 2019年5月29日
	 */
	@ApiOperation(value="查询积分消费明细",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="integralUserId",value="积分用户id",required=false,dataType="long",paramType="query"),
		@ApiImplicitParam(name="depName",value="采购单位",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="integralName",value="积分名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="realName",value="用户名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="ordersState",value="订单状态",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderCreatedAtStart",value="下单开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderCreatedAtEnd",value="下单结束时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderCompletedAtStart",value="订单完成开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderCompletedAtEnd",value="订单完成结束时间",required=false,dataType="string",paramType="query")
	})
	@RequestMapping(value="/getIntegralConsumeInfo", method=RequestMethod.GET)
	public PageInfo<IntegralConsume> getIntegralConsumeInfo(@ModelAttribute PageBean page, Long integralUserId, String depName, String supplierName, 
			String integralName, String realName, String ordersState, String orderCreatedAtStart, String orderCreatedAtEnd, String orderCompletedAtStart, String orderCompletedAtEnd){
		PageInfo<IntegralConsume> selectIntegralConsumeList = new PageInfo<>();
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(depName)){
			map.put("depName", depName);
		}
		if(StringUtils.isNotBlank(supplierName)){
			map.put("supplierName", supplierName);
		}
		if(StringUtils.isNotBlank(integralName)){
			map.put("integralName", integralName);
		}
		if(StringUtils.isNotBlank(realName)){
			map.put("realName", realName);
		}
		if(StringUtils.isNotBlank(orderCreatedAtStart)){
			map.put("orderCreatedAtStart", orderCreatedAtStart);
		}
		if(StringUtils.isNotBlank(orderCreatedAtEnd)){
			map.put("orderCreatedAtEnd", orderCreatedAtEnd);
		}
		if(StringUtils.isNotBlank(ordersState)){
			map.put("ordersState", ordersState);
		}
		if(StringUtils.isNotBlank(orderCompletedAtStart)){
			map.put("orderCompletedAtStart", orderCompletedAtStart);
		}
		if(StringUtils.isNotBlank(orderCompletedAtEnd)){
			map.put("orderCompletedAtEnd", orderCompletedAtEnd);
		}
		selectIntegralConsumeList = integralUserService.selectIntegralConsume(page, map);
		return selectIntegralConsumeList;
	}
	
	/**
	 * @Description:导出明细
	 * @date 2019年6月3日
	 */
	@ApiOperation(value="导出excel",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="ids",value="ids",required=false,dataType="long",paramType="query"),
		@ApiImplicitParam(name="exportType",value="1：勾选下载，2全部下载",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="integralUserId",value="积分用户id",required=false,dataType="long",paramType="query"),
		@ApiImplicitParam(name="depName",value="采购单位",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="integralName",value="积分名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="realName",value="用户名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="ordersState",value="订单状态",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderCreatedAtStart",value="下单开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderCreatedAtEnd",value="下单结束时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderCompletedAtStart",value="订单完成开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderCompletedAtEnd",value="订单完成结束时间",required=false,dataType="string",paramType="query")
	})
	@RequestMapping(value = "/exportExcelConsumeInfo", method = RequestMethod.GET)
	public void exportExcelConsumeInfo(HttpServletRequest request, HttpServletResponse response, Long[] ids, Integer exportType, Long integralUserId, 
			String depName, String supplierName, String integralName, String realName, String ordersState, String orderCreatedAtStart, String orderCreatedAtEnd, 
			String orderCompletedAtStart, String orderCompletedAtEnd
			){
		Map<String, Object> map = new HashMap<>();
		if(1 == exportType && ids !=null){
			String str = StringUtils.join(ids, ",");
			map.put("ids", str);
			integralUserService.exportConsumeInfoExcel(request, response, map);
		}else if(2 == exportType){
			if(StringUtils.isNotBlank(depName) && !"undefined".equals(depName)){
				map.put("depName", depName);
			}
			if(StringUtils.isNotBlank(supplierName) && !"undefined".equals(supplierName)){
				map.put("supplierName", supplierName);
			}
			if(StringUtils.isNotBlank(integralName) && !"undefined".equals(integralName)){
				map.put("integralName", integralName);
			}
			if(StringUtils.isNotBlank(realName) && !"undefined".equals(realName)){
				map.put("realName", realName);
			}
			if(StringUtils.isNotBlank(ordersState) && !"undefined".equals(ordersState)){
				map.put("ordersState", ordersState);
			}
			if(StringUtils.isNotBlank(orderCreatedAtStart) && !"undefined".equals(orderCreatedAtStart)){
				map.put("orderCreatedAtStart", orderCreatedAtStart);
			}
			if(StringUtils.isNotBlank(orderCreatedAtEnd) && !"undefined".equals(orderCreatedAtEnd)){
				map.put("orderCreatedAtEnd", orderCreatedAtEnd);
			}
			if(StringUtils.isNotBlank(orderCompletedAtStart)){
				map.put("orderCompletedAtStart", orderCompletedAtStart);
			}
			if(StringUtils.isNotBlank(orderCompletedAtEnd)){
				map.put("orderCompletedAtEnd", orderCompletedAtEnd);
			}
			integralUserService.exportConsumeInfoExcel(request, response, map);
		}
	}
	
	@RequestMapping(value="/getOrderStatusList", method=RequestMethod.GET)
	public List<SelectOption> getOrderStatusList (){
		List<SelectOption> optionList = new ArrayList<SelectOption>();
			OrderStatus[] enumConstants = OrderStatus.class.getEnumConstants();
			SelectOption selectOption = null;
			for(int i=0; i<enumConstants.length; i++){
				String label = orderStatusConfig.getOrderstatus().get(enumConstants[i].name());
				selectOption = new SelectOption(enumConstants[i].name(),label);
				optionList.add(selectOption);
			}
			return optionList;
	}
}
