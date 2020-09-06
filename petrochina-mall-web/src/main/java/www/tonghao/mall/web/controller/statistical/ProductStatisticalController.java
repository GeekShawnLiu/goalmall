package www.tonghao.mall.web.controller.statistical;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.SysLog;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.StringUtil;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.service.OrderItemsService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/productStatistical")
@Api(value="productStatisticalController", description="商品统计")
public class ProductStatisticalController {

	@Autowired
	private OrderItemsService orderItemsService;
	
	@SysLog(opt = "商品统计")
	@ApiOperation(value="商品统计列表",notes="商品统计列表api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="createStartAt",value="下单开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="createEndAt",value="下单结束时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="purchaserName",value="组织机构名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="userName",value="用户名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="productName",value="商品名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="completedStartAt",value="完成开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="completedEndAt",value="完成结束时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="sn",value="平台订单编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="emallSn",value="电商订单编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="payId",value="支付方式",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="sku",value="商品编码",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public PageInfo<OrderItems> list(@ModelAttribute PageBean page,
			String createStartAt, String createEndAt, String purchaserName,
			String userName, String productName, String supplierName,
			String completedStartAt, String completedEndAt, String sn,
			String emallSn, String activityName, Long payId, String sku) {
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map = new HashMap<>();
		map.put("createStartAt", StringUtils.isNotEmpty(StringUtil.strTrim(createStartAt)) ? createStartAt.trim() + " 00:00:00" : null);
		map.put("createEndAt", StringUtils.isNotEmpty(StringUtil.strTrim(createEndAt)) ? createEndAt.trim() + " 23:59:59" : null);
		map.put("purchaserName", StringUtil.strTrim(purchaserName));
		map.put("userName", StringUtil.strTrim(userName));
		map.put("productName", StringUtil.strTrim(productName));
		map.put("supplierName", StringUtil.strTrim(supplierName));
		map.put("completedStartAt", StringUtils.isNotEmpty(StringUtil.strTrim(completedStartAt)) ? completedStartAt.trim() + " 00:00:00" : null);
		map.put("completedEndAt", StringUtils.isNotEmpty(StringUtil.strTrim(completedEndAt)) ? completedEndAt.trim() + " 23:59:59" : null);
		map.put("sn", StringUtil.strTrim(sn));
		map.put("emallSn", StringUtil.strTrim(emallSn));
		map.put("activityName", StringUtil.strTrim(activityName));
		map.put("payId", payId);
		map.put("sku", StringUtil.strTrim(sku));
		List<OrderItems> list = orderItemsService.productStatisticalList(map);
		return new PageInfo<OrderItems>(list);
	}
	
	@SysLog(opt = "商品统计导出excel")
	@ApiOperation(value="商品统计导出excel",notes="商品统计导出api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="createStartAt",value="下单开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="createEndAt",value="下单结束时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="purchaserName",value="组织机构名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="userName",value="用户名",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="productName",value="商品名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="completedStartAt",value="完成开始时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="completedEndAt",value="完成结束时间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="sn",value="平台订单编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="emallSn",value="电商订单编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="payId",value="支付方式",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="sku",value="商品编码",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/downloadExcel", method=RequestMethod.GET)
	public void downloadExcel(String createStartAt, String createEndAt,
			String purchaserName, String userName, String productName,
			String supplierName, String completedStartAt,
			String completedEndAt, String sn, String emallSn,
			String activityName, Long payId, String sku,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("createStartAt", StringUtils.isNotEmpty(StringUtil.strTrim(createStartAt)) ? createStartAt.trim() + " 00:00:00" : null);
		map.put("createEndAt", StringUtils.isNotEmpty(StringUtil.strTrim(createEndAt)) ? createEndAt.trim() + " 23:59:59" : null);
		map.put("purchaserName", StringUtil.strTrim(purchaserName));
		map.put("userName", StringUtil.strTrim(userName));
		map.put("productName", StringUtil.strTrim(productName));
		map.put("supplierName", StringUtil.strTrim(supplierName));
		map.put("completedStartAt", StringUtils.isNotEmpty(StringUtil.strTrim(completedStartAt)) ? completedStartAt.trim() + " 00:00:00" : null);
		map.put("completedEndAt", StringUtils.isNotEmpty(StringUtil.strTrim(completedEndAt)) ? completedEndAt.trim() + " 23:59:59" : null);
		map.put("sn", StringUtil.strTrim(sn));
		map.put("emallSn", StringUtil.strTrim(emallSn));
		map.put("activityName", StringUtil.strTrim(activityName));
		map.put("payId", payId);
		map.put("sku", StringUtil.strTrim(sku));
		orderItemsService.downloadProductStatistical(map, response);
	}
	
}
