package www.tonghao.mall.web.controller.supplier;

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
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.core.ApiCommon;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;



@RestController
@RequestMapping("/supplier/order")
@Api(value="供应商订单",description="供应商订单Api")
public class SupplierOrderController {

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
	
	@ApiOperation(value="根据编号查询",notes="查询单条api")
	@GetMapping(value="/getBySn")
	public Orders getBySn(String sn, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		Orders order = ordersService.findBySn(sn);
		if(order!=null&&order.getSupplierId().equals(user.getTypeId())){
			order.setTrackList(ordersService.getOrderTrackById(order.getId()));
			return order;
		}
		return null;
	}
	
	@ApiOperation(value="查询供应商已完成订单",notes="查询供应商已完成订单api")
	@GetMapping(value="/getSupplierOrder")
	public Map<String, Integer> getSupplierOrder(HttpServletRequest request){
		Map<String, Integer> map=new HashMap<String, Integer>();
		Users user = UserUtil.getUser(request);
		Example example=new Example(Orders.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("supplierId", user.getTypeId());
		createCriteria.andEqualTo("ordersState", "completed");
		createCriteria.andIsNull("parentId");
		List<Orders> orderList = ordersService.selectByExample(example);
		if(!CollectionUtils.isEmpty(orderList)){
			map.put("completed", orderList.size());
		}else{
			map.put("completed", 0);
		}
		return map;
	}
	
	@ApiOperation(value="供应商确认订单", notes="供应商确认订单api")
	@RequestMapping(value="/supplierConfirmedOrder", method=RequestMethod.PUT)
	public Map<String, Object> supplierConfirmedOrder(HttpServletRequest request, Long orderId){
		Users user = UserUtil.getUser(request);
		if(user != null && user.getType() != null && user.getType() == 4 && user.getTypeId() != null){
			return ordersService.supplierConfirmedOrder(user.getTypeId(), orderId);
		}
		return ResultUtil.error("登录信息异常");
	}
	
	@ApiOperation(value="判断供应商是否为电商", notes="供应商确认订单api")
	@RequestMapping(value="/isEmallSupplier", method=RequestMethod.GET)
	public boolean isEmallSupplier(HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null && user.getType() != null && user.getType() == 4 && user.getTypeId() != null){
			Suppliers supplier = suppliersService.selectByKey(user.getTypeId());
			if(supplier != null){
				return ApiCommon.checkCode(supplier.getCode());
			}
		}
		return false;
	}
}
