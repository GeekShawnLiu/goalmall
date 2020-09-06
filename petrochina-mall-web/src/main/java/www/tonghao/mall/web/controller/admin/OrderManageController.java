package www.tonghao.mall.web.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.mall.entity.Invoices;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.InvoicesService;
import www.tonghao.mall.service.OrdersService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Maps;
/**
 * 商城管理员-订单管理
 * @author lxh
 *
 */
@Api(value="orderManageController",description="商城管理员-订单管理")
@RestController
@RequestMapping(value="/orderManage")
public class OrderManageController {
    
    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private InvoicesService invoicesService;
    
    /**
     * 商城管理员-订单分页查询
     * @author lxh
     * @return
     */
    @ApiOperation(value="分页查询", notes="分页查询api")
    @ApiImplicitParams({
        @ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
        @ApiImplicitParam(name="rows",value="行数",required=true,dataType="int",paramType="query"),
        @ApiImplicitParam(name="dateRange",value="时间范围",required=false,dataType="string",paramType="query",example="all|month_3|month_6|year_1"),
        @ApiImplicitParam(name="sn",value="订单号",required=false,dataType="string",paramType="query"),
        @ApiImplicitParam(name="emallSn",value="电商订单号",required=false,dataType="string",paramType="query"),
        @ApiImplicitParam(name="status",value="订单状态",required=false,dataType="string",paramType="query"),
        @ApiImplicitParam(name="queryDateStart",value="下单时间查询开始时间",required=false,dataType="string",paramType="query"),
        @ApiImplicitParam(name="queryDateEnd",value="下单时间查询结束时间",required=false,dataType="string",paramType="query"),
    })
    @RequestMapping(value="/getPage",method=RequestMethod.GET)
    public PageInfo<Orders> list(@ModelAttribute PageBean page,String dateRange
            ,String sn,String emallSn,String status,String queryDateStart,String queryDateEnd) {
        PageHelper.startPage(page.getPage(), page.getRows());
        Map<String, Object> queryFilter = Maps.newHashMap();
        Orders orders = new Orders();
        String dateRangeStart = null;
        String dateRangeEnd = null;
        //判断订单期限
        if (StringUtil.isNotEmpty(dateRange)) {
            Date date = new Date();
            if ("month_3".equals(dateRange)) {
                dateRangeStart = DateUtilEx.timeFormat(DateUtils.addMonths(date, -3));
            }else if ("month_6".equals(dateRange)) {
                dateRangeStart = DateUtilEx.timeFormat(DateUtils.addMonths(date, -6));
            }else if ("year_1".equals(dateRange)) {
                dateRangeStart = DateUtilEx.timeFormat(DateUtils.addYears(date, -1));
            }
            dateRangeEnd = DateUtilEx.timeFormat(date);
            queryFilter.put("dateRangeStart", dateRangeStart);
            queryFilter.put("dateRangeEnd", dateRangeEnd);
        }
        //判断下单时间
        if (StringUtil.isNotEmpty(queryDateStart)&& StringUtil.isNotEmpty(queryDateEnd)) {
            queryFilter.put("queryDateStart", queryDateStart);
            queryFilter.put("queryDateEnd", queryDateEnd);
        }
        if (StringUtil.isNotEmpty(sn)) {
            orders.setSn(sn);
        }
        if (StringUtil.isNotEmpty(emallSn)) {
            orders.setEmallSn(emallSn);
        }
        if (StringUtil.isNotEmpty(status)) {
            orders.setOrdersState(OrderStatus.valueOf(status));
        }
        orders.setQueryfilter(queryFilter);
//        Principal proncipal = UserUtil.currentLoginPrincipal();
//        orders.setUserId(proncipal.getId());
//        orders.setIsDelete(false);
        List<Orders> list = ordersService.findListByEntity(orders);
        return new PageInfo<Orders>(list);
    }
    
    /**
     * 根据id查询订单详情
     * @param id
     * @author lxh
     * @return
     */
    @ApiOperation(value="根据id查询订单详情", notes="查询单条api")
    @ApiImplicitParams({
        @ApiImplicitParam(name="id",value="id",required=true,dataType="long",paramType="query")
    })
    @RequestMapping(value="/getOrderDetailById",method=RequestMethod.GET)
    public Map<String, Object> getOrderDetailById(Long id){
        Map<String, Object> map = Maps.newHashMap();
        Orders orders = ordersService.findById(id);
        Long invoiceId = orders.getInvoiceId();
        if (invoiceId != null) {
            Invoices invoices = invoicesService.findById(invoiceId);
            if (invoices != null) {
                map.put("invoice", invoices);
            }
        }
        map.put("order", orders);
        //供应商信息-客服（临时字段）
        map.put("customService", "400-010-8878");
        //付款信息-电话（临时字段）
        map.put("payTel", "19037265863");
        return map;
    }
}
