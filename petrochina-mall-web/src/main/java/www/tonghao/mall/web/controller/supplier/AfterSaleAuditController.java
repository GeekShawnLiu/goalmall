package www.tonghao.mall.web.controller.supplier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.ReceiverAddresses;
import www.tonghao.mall.service.OrderItemsService;
import www.tonghao.mall.service.ReceiverAddressesService;
import www.tonghao.service.common.entity.AfterSale;
import www.tonghao.service.common.entity.AfterSaleAudit;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.AfterSaleAuditService;
import www.tonghao.service.common.service.AfterSaleService;
import www.tonghao.utils.UserUtil;

/**
 * @Description:供应商售后审核
 * @date 2019年7月12日
 */
@Api(value="afterSaleAuditController", description="供应商售后审核")
@RestController
@RequestMapping("/supplierAfterSale")
public class AfterSaleAuditController {
	
	@Autowired
	private AfterSaleAuditService afterSaleAuditService;
	
	@Autowired
	private AfterSaleService afterSaleService;
	
	@Autowired
	private ReceiverAddressesService receiverAddressesService;
	
	@Autowired
	private OrderItemsService orderItemsService;
	
	/**
	 * @Description:售后列表
	 * @date 2019年7月12日
	 */
	@ApiOperation(value="售后审核列表",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="ordersSn",value="订单编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="afterSaleSn",value="售后编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="type",value="售后类型：1：换货，2：退货退款，3：退款",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="status",value="当前状态：0:待审核，1:不通过，2：通过，3：买家货物邮寄中，4：货物邮寄中，5：售后完成， 6：确认收货，待退款，"
				+ "7：确认收货，驳回退款，8：已退款，售后完成，9：拒绝退款，10同意退款，11：已退款",required=false,dataType="int",paramType="query"),
	})
	@GetMapping("/getAfterSaleAuditList")
	public PageInfo<AfterSale> getAfterSaleAuditList(PageBean page, HttpServletRequest request, String ordersSn, String afterSaleSn, Integer type, Integer status){
		Users user = UserUtil.getUser(request);
		PageInfo<AfterSale> afterSaleList = null;
		if(user != null && user.getType() == 4){
			Example example = new Example(AfterSale.class);
			Criteria createCriteria = example.createCriteria();
			
			createCriteria.andEqualTo("supplierId", user.getTypeId());
			createCriteria.andEqualTo("afterSaleSn", afterSaleSn == ""? null : afterSaleSn);
			createCriteria.andEqualTo("ordersSn", ordersSn == ""? null : ordersSn);
			if(status !=null){
				if(status == 1){
					List<Integer> types = new ArrayList<Integer>();
					types.add(1);
					types.add(2);
					createCriteria.andIn("type", types);
					createCriteria.andEqualTo("status", 1);
				}else if(status == 2){
					List<Integer> types = new ArrayList<Integer>();
					types.add(1);
					types.add(2);
					createCriteria.andIn("type", types);
					createCriteria.andEqualTo("status", 2);
				}else if(status == 5){
					createCriteria.andEqualTo("type", 1);
					createCriteria.andEqualTo("status", 5);
				}else if(status == 8){
					createCriteria.andEqualTo("type", 2);
					createCriteria.andEqualTo("status", 5);
				}else if(status == 9){
					createCriteria.andEqualTo("type", 3);
					createCriteria.andEqualTo("status", 1);
				}else if(status == 10){
					createCriteria.andEqualTo("type", 3);
					createCriteria.andEqualTo("status", 2);
				}else if(status == 11){
					createCriteria.andEqualTo("type", 3);
					createCriteria.andEqualTo("status", 5);
				}else{
					createCriteria.andEqualTo("status", status);
				}
			}
			createCriteria.andEqualTo("type", type);
			afterSaleList = afterSaleService.getAfterSaleList(page, example);
		}
		return afterSaleList;
	}
	
	/**
	 * @Description:审核
	 * @date 2019年7月15日
	 */
	@ApiOperation(value="审核")
	@ApiImplicitParams({
		@ApiImplicitParam(name="afterSaleId",value="申请售后id",required=true,dataType="long",paramType="query"),
		@ApiImplicitParam(name="auditType",value="类型：1不通过，2通过",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierAddress",value="供应商收货地址",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="reason",value="原因",required=false,dataType="string",paramType="query")
	})
	@GetMapping("/audit")
	public Map<String, Object> audit(HttpServletRequest request, Long afterSaleId, String reason, Integer auditType, String supplierAddress){
		Users user = UserUtil.getUser(request);
		if(user == null || afterSaleId == null || (auditType !=1 && auditType !=2)){
			return ResultUtil.error("操作失败");
		}
		
		if(auditType == 2 && supplierAddress ==null){
			return ResultUtil.error("请选择收货地址");
		}
		
		AfterSale selectByKey = afterSaleService.selectByKey(afterSaleId);
		if(selectByKey == null || selectByKey.getStatus() != 0){
			return ResultUtil.error("操作失败"); 
		}
		
		AfterSaleAudit afterSaleAudit = new AfterSaleAudit();
		afterSaleAudit.setAfterSaleId(afterSaleId);
		afterSaleAudit.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		afterSaleAudit.setReason(reason);
		afterSaleAudit.setUserId(user.getId());
		afterSaleAudit.setUserName(user.getRealName());
		Map<String, Object> result = afterSaleAuditService.audit(afterSaleAudit, auditType, supplierAddress);
		return result;
	}
	
	/**
	 * @Description:获取收货地址
	 * @date 2019年7月15日
	 */
	@ApiOperation(value="获取收货地址")
	@GetMapping("/getReceiverAddresses")
	public List<ReceiverAddresses> getReceiverAddresses(HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		List<ReceiverAddresses> receiverAddressesList = null;
		if(user !=null && user.getType() == 4){
			Example example = new Example(ReceiverAddresses.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("userId", user.getId());
			receiverAddressesList = receiverAddressesService.selectByExample(example);
		}
		return receiverAddressesList;
	}
	
	
	/**
	 * @Description:采购人换货，供应商填补售后信息
	 * @date 2019年7月15日
	 */
	@ApiOperation(value="采购人换货，供应商填补售后信息",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="afterSaleId",value="申请售后id",required=true,dataType="long",paramType="query"),
		@ApiImplicitParam(name="supplierExpressNum",value="供应商填写的快递号",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="supplierExpressName",value="供应商填写的快递名称",required=true,dataType="string",paramType="query")
	})
	@GetMapping("/fillAfterSaleInfo")
	public Map<String, Object> fillAfterSaleInfo(Long afterSaleId, String supplierExpressNum, String supplierExpressName){
		if(afterSaleId ==null || StringUtils.isBlank(supplierExpressNum) || StringUtils.isBlank(supplierExpressName)){
			return ResultUtil.error("校验失败");
		}
		
		AfterSale selectByKey = afterSaleService.selectByKey(afterSaleId);
		//3：买家货物邮寄中
		if(selectByKey == null || selectByKey.getStatus() != 3){
			return ResultUtil.error("操作失败");
		}
		
		AfterSale afterSale = new AfterSale();
		afterSale.setId(afterSaleId);
		//4：卖家货物邮寄中
		afterSale.setStatus(4);
		afterSale.setSupplierExpressNum(supplierExpressNum);
		afterSale.setSupplierExpressName(supplierExpressName);
		afterSale.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int saveSelective = afterSaleService.updateNotNull(afterSale);
		return ResultUtil.resultMessage(saveSelective, "");
	}
	
	/**
	 * @Description:查看售后信息
	 * @date 2019年7月15日
	 */
	@ApiOperation(value="查看售后信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="afterSaleId",value="申请售后id",required=true,dataType="long",paramType="query")
	})
	@GetMapping("/getAfterSaleInfo")
	public AfterSale getAfterSaleInfo(Long afterSaleId){
		AfterSale afterSale = afterSaleService.selectByKey(afterSaleId);
		if(afterSale !=null){
			AfterSaleAudit afterSaleAudit = afterSaleAuditService.selectByCreatedAtMax(afterSaleId);
			afterSale.setAfterSaleAudit(afterSaleAudit);
		}
		return afterSale;
	}
	
	/**
	 * @Description:采购人退货退款，供应商确认收货
	 * @date 2019年7月15日
	 */
	@ApiOperation(value="采购人退货退款，供应商确认收货",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="afterSaleId",value="申请售后id",required=true,dataType="long",paramType="query"),
		@ApiImplicitParam(name="type",value="1:卖家确认收货，待退款，2:卖家确认收货，驳回退款",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="noRefundType",value="1:不退款，2：可退部分金额",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="noRefundReason",value="不退款理由",required=false,dataType="string",paramType="query")
	})
	@GetMapping("/confirmReceipt")
	public Map<String, Object> confirmReceipt(HttpServletRequest request, Long afterSaleId, Integer type, Integer noRefundType, String noRefundReason){
		Users user = UserUtil.getUser(request);
		if(user == null || afterSaleId ==null || (type !=1 && type !=2)){
			return ResultUtil.error("操作失败");
		}
		AfterSale selectByKey = afterSaleService.selectByKey(afterSaleId);
		//3：买家货物邮寄中
		if(selectByKey == null || selectByKey.getStatus() != 3){
			return ResultUtil.error("操作失败");
		}
		AfterSale afterSale = new AfterSale();
		afterSale.setId(afterSaleId);
		if(type == 1){
			//6：卖家确认收货，待退款
			afterSale.setStatus(6);
		}else if(type == 2){
			//7：卖家确认收货，驳回退款
			afterSale.setStatus(7);
		}
		afterSale.setNoRefundType(noRefundType);
		afterSale.setNoRefundReason(noRefundReason);
		afterSale.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = afterSaleService.updateNotNull(afterSale);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**
	 * @Description:获取订单商品信息
	 * @date 2019年7月12日
	 */
	@ApiOperation(value="获取订单商品信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="orderItemId",value="订单明细ID",required=true,dataType="long")
	})
	@GetMapping(value="/getOrderItem")
	public OrderItems getOrderItem(Long orderItemId, HttpServletRequest request){
		OrderItems orderItem = orderItemsService.findById(orderItemId);
		return orderItem;
	}
}
