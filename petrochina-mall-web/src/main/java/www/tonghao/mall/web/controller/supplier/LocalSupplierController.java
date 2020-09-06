package www.tonghao.mall.web.controller.supplier;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.utils.UserUtil;

@RestController
@RequestMapping("localSupplier")
public class LocalSupplierController {
  
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private SuppliersService suppliersService;
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="dateRange",value="时间范围(all|month_3|year_1)",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="订单状态",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="contractStatus",value="合同状态",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="sn",value="订单号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name = "emallSn", value = "电商订单号", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name="productName",value="商品名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="consigneeName",value="收货人",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="userName",value="用户名",required=false,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Orders> getPage(@ModelAttribute PageBean page
			,String dateRange,String status,String contractStatus, String emallSn
			,String sn,String productName,String consigneeName, HttpServletRequest request, String userName){
		Users user = UserUtil.getUser(request);
		List<Orders> list =null;
		if(user!=null){
			PageHelper.startPage(page.getPage(), page.getRows());
			Orders entity = new Orders();
			Map<String, Object> queryFilter = Maps.newHashMap();
			String dateRangeStart = null;
			String dateRangeEnd = null;
			if(StringUtils.isNotEmpty(dateRange)){
				Date date = new Date();
				if(dateRange.equals("month_3")){
					dateRangeStart = DateUtilEx.timeFormat(DateUtils.addMonths(date, -3));
				}else if(dateRange.equals("year_1")){
					dateRangeStart = DateUtilEx.timeFormat(DateUtils.addYears(date, -1));
				}
				dateRangeEnd = DateUtilEx.timeFormat(date);
				queryFilter.put("dateRangeStart", dateRangeStart);
				queryFilter.put("dateRangeEnd", dateRangeEnd);
			}
			queryFilter.put("productName", productName);
			entity.setQueryfilter(queryFilter);
			entity.setOrdersState(EnumUtils.getEnum(OrderStatus.class, status));
			entity.setSn(sn);
			entity.setEmallSn(emallSn);
			entity.setConsigneeName(consigneeName);
			entity.setUserName(userName);
			entity.setSupplierId(user.getTypeId());
			entity.setIsDelete(0);
			list= ordersService.findListByEntity(entity);
			if(CollectionUtils.isNotEmpty(list)){
				list.forEach(o -> {
					o.setIsHavaChild(0);
				});
			}
		}
		return new PageInfo<Orders>(list);
	}
	
	@RequestMapping(value="/isReceipt",method=RequestMethod.GET)
	public Map<String,Object> isReceipt(Long orderId){
		Orders orders = ordersService.selectByKey(orderId);
		orders.setIsReceipt(1);
		orders.setReceiptAt(DateUtilEx.timeToDate(new Date(), DateUtilEx.TIME_PATTERN));
		int updateNotNull = ordersService.updateNotNull(orders);
		if(updateNotNull>0){
			return ResultUtil.success("发货成功");
		}else{
			return ResultUtil.success("发货失败");
		}
	}
	
	
	@RequestMapping(value="/logistics",method=RequestMethod.GET)
	public Map<String,Object> logistics(Long orderId,String logisticsName,String logisticsSn){
		Orders orders = ordersService.selectByKey(orderId);
		orders.setLogisticsName(logisticsName);
		orders.setLogisticsSn(logisticsSn);
		orders.setTrack("[{\"content\":\"物流公司:"+logisticsName+"\"},{\"content\":\"物流单号:"+logisticsSn+"\"}]");
		orders.setIsReceipt(1);
		orders.setReceiptAt(DateUtilEx.timeToDate(new Date(), DateUtilEx.TIME_PATTERN));
		int updateNotNull=ordersService.updateNotNull(orders);
		if(updateNotNull>0){
			return ResultUtil.success("物流录入成功");
		}else{
			return ResultUtil.success("物流录入失败");
		}
	}
	
	@RequestMapping(value="/orderOver",method=RequestMethod.GET)
	public Map<String,Object> orderOver(Long orderId){
		Orders orders = ordersService.selectByKey(orderId);
		orders.setOrdersState(OrderStatus.completed);
		orders.setCompletedAt(DateUtilEx.timeToDate(new Date(), DateUtilEx.TIME_PATTERN));
		int updateNotNull=ordersService.updateNotNull(orders);
		if(updateNotNull>0){
			return ResultUtil.success("订单已完成");
		}else{
			return ResultUtil.success("操作失败");
		}
	}
	
	@RequestMapping(value="/orderCancel",method=RequestMethod.GET)
	public Map<String,Object> orderCancel(Long orderId){
		Orders orders = ordersService.selectByKey(orderId);
		orders.setOrdersState(OrderStatus.cancelled);
		orders.setCompletedAt(DateUtilEx.timeToDate(new Date(), DateUtilEx.TIME_PATTERN));
		int updateNotNull=ordersService.updateNotNull(orders);
		if(updateNotNull>0){
			return ResultUtil.success("订单取消成功");
		}else{
			return ResultUtil.success("订单取消失败");
		}
	}
	
}
