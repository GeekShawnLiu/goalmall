package www.tonghao.mall.web.controller.purchaser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.service.OrderItemsService;
import www.tonghao.service.common.entity.AfterSale;
import www.tonghao.service.common.entity.AfterSaleAudit;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.AfterSaleAuditService;
import www.tonghao.service.common.service.AfterSaleService;
import www.tonghao.utils.UserUtil;

/**
 * @Description:售后
 * @date 2019年7月11日
 */
@Api(value="afterSaleController", description="采购人售后")
@RestController
@RequestMapping("/purchaserAfterSale")
public class AfterSaleController {

	@Autowired
	private AfterSaleService afterSaleService;
	
	@Autowired
	private OrderItemsService orderItemsService;
	
	@Autowired
	private AfterSaleAuditService afterSaleAuditService;
	
	/**
	 * @Description:获取订单商品信息
	 * @date 2019年7月12日
	 */
	@ApiOperation(value="获取订单商品信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="orderItemId",value="订单明细ID",required=true,dataType="long"),
	})
	@GetMapping(value="/getOrderItem")
	public OrderItems getOrderItem(Long orderItemId, HttpServletRequest request){
		OrderItems orderItem = orderItemsService.findById(orderItemId);
		return orderItem;
	}
	
	/**
	 * @Description:提交申请
	 * @date 2019年7月11日
	 */
	@ApiOperation(value="提交申请")
	@PostMapping("/submitApply")
	@ApiImplicitParams({
		@ApiImplicitParam(name="isResubmit",value="卖家驳回退款（部分退款），买家修改时需传：1",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="resubmitId",value="卖家驳回退款（部分退款），买家修改时需要传：售后列表id",required=false,dataType="string",paramType="query"),
	})
	public Map<String, Object> submitApply(@RequestBody AfterSale afterSale, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user ==null){
			return ResultUtil.error("用户获取失败");
		}
		
		if(afterSale ==null || afterSale.getOrderItmesId() ==null){
			return ResultUtil.error("订单获取失败");
		}
		
		OrderItems orderItems = orderItemsService.findById(afterSale.getOrderItmesId());
		if(orderItems.getProduct() ==null || orderItems.getOrder() ==null || orderItems.getOrder().getSupplierId() ==null){
			return ResultUtil.error("订单获取失败");
		}
		
		Integer num = orderItems.getNum();
		if(afterSale.getProductNum() <=0 || afterSale.getProductNum() > num){
			return ResultUtil.error("数量填写错误");
		}
		Long payId = orderItems.getOrder().getPayId();
		if(payId !=null){
			afterSale.setPayType(payId.intValue());
		}
		afterSale.setSupplierId(orderItems.getOrder().getSupplierId());
		afterSale.setUserId(user.getId());
		afterSale.setSupplierName(orderItems.getProduct().getSupplierName() ==null? "" : orderItems.getProduct().getSupplierName());
		afterSale.setSku(orderItems.getProduct().getSku() ==null? "" : orderItems.getProduct().getSku());
		afterSale.setOrderItmesId(afterSale.getOrderItmesId());
		afterSale.setProductPrice(orderItems.getPrice());
		afterSale.setOrdersSn(orderItems.getOrderSn());
		afterSale.setProductName(orderItems.getName());
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(afterSale.getIsResubmit() !=null && afterSale.getIsResubmit() ==1){
			//卖家驳回退款，买家重新提交
			result = afterSaleService.resubmitSubmitApply(afterSale);
		}else{
			result = afterSaleService.submitApply(afterSale);
		}
		return result;
	}
	
	/**
	 * @Description:售后列表
	 * @date 2019年7月12日
	 */
	@ApiOperation(value="售后列表",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="ordersSn",value="订单编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="afterSaleSn",value="售后编号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="type",value="售后类型：1：换货，2：退货退款，3：退款",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="status",value="当前状态：0:待审核，1:不通过，2：通过，3：货物邮寄中，4：卖家货物邮寄中，5：售后完成， 6：卖家确认收货，待退款，"
				+ "7：卖家确认收货，驳回退款，8：已退款，售后完成，9：卖家拒绝退款，10退款中，11：已退款",required=false,dataType="int",paramType="query"),
	})
	@GetMapping("/getAfterSaleList")
	public PageInfo<AfterSale> getAfterSaleList(PageBean page, HttpServletRequest request, String ordersSn, String afterSaleSn, Integer type, Integer status){
		Users user = UserUtil.getUser(request);
		PageInfo<AfterSale> pageInfo = null;
		
		if(user != null){
			Example example = new Example(AfterSale.class);
			Criteria createCriteria = example.createCriteria();
			
			createCriteria.andEqualTo("userId", user.getId());
			if(StringUtils.isNotBlank(afterSaleSn)){
				createCriteria.andEqualTo("afterSaleSn", afterSaleSn);
			}
			createCriteria.andEqualTo("ordersSn", ordersSn);
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
			pageInfo = afterSaleService.getAfterSaleList(page, example);
		}
		
		if(pageInfo !=null && CollectionUtils.isNotEmpty(pageInfo.getList())){
			List<AfterSale> list = pageInfo.getList();
			for(AfterSale a : list){
				OrderItems orderItems = orderItemsService.selectByKey(a.getOrderItmesId());
				if(orderItems != null){
					a.setProductThumbnail(orderItems.getThumbnail());
				}
			}
		}
		return pageInfo;
	}
	
	/**
	 * @Description:采购人填补售后信息
	 * @date 2019年7月15日
	 */
	@ApiOperation(value="采购人填补售后信息",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="afterSaleId",value="申请售后id",required=true,dataType="long",paramType="query"),
		@ApiImplicitParam(name="purchaserExpressNum",value="采购人填写的快递号",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="purchaserExpressName",value="采购人填写的快递名称",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="afterSaleImg",value="售后凭证图片",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="afterSaleImgName",value="售后凭证图片名称",required=false,dataType="string",paramType="query"),
	})
	@GetMapping("/fillAfterSaleInfo")
	public Map<String, Object> fillAfterSaleInfo(Long afterSaleId, String purchaserExpressNum, String purchaserExpressName, String afterSaleImg, String afterSaleImgName){
		if(afterSaleId ==null || StringUtils.isBlank(purchaserExpressNum) || StringUtils.isBlank(purchaserExpressName)){
			return ResultUtil.error("校验失败");
		}
		
		AfterSale selectByKey = afterSaleService.selectByKey(afterSaleId);
		//2：通过
		if(selectByKey == null || selectByKey.getStatus() != 2){
			return ResultUtil.error("操作失败");
		}
		
		AfterSale afterSale = new AfterSale();
		afterSale.setId(afterSaleId);
		//3：买家货物邮寄中
		afterSale.setStatus(3);
		afterSale.setPurchaserExpressNum(purchaserExpressNum);
		afterSale.setPurchaserExpressName(purchaserExpressName);
		afterSale.setAfterSaleImg(afterSaleImg);
		afterSale.setAfterSaleImgName(afterSaleImgName);
		afterSale.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int update = afterSaleService.updateNotNull(afterSale);
		return ResultUtil.resultMessage(update, "");
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
	 * @Description:修改售后信息
	 * @date 2019年7月15日
	 */
	@ApiOperation(value="修改售后信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="afterSaleId",value="申请售后id",required=true,dataType="long",paramType="query"),
		@ApiImplicitParam(name="refund",value="退款金额",required=true,dataType="BigDecimal",paramType="query"),
		@ApiImplicitParam(name="reason",value="售后原因",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="description",value="问题描述",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="certificateImg",value="凭证图片路径",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="certificateImgName",value="凭证图片名称",required=false,dataType="string",paramType="query"),
	})
	@GetMapping("/updateAfterSaleInfo")
	public Map<String, Object> updateAfterSaleInfo(Long afterSaleId, BigDecimal refund, String reason, String description, String certificateImg, String certificateImgName){
		AfterSale afterSale = afterSaleService.selectByKey(afterSaleId);
		if(afterSale ==null){
			return ResultUtil.error("获取信息失败");
		}
		//1:不通过
		if(afterSale.getStatus() !=1){
			return ResultUtil.error("审核不通过才可修改信息");
		}
		
		AfterSale as = new AfterSale();
		//0:待审核
		as.setStatus(0);
		as.setId(afterSaleId);
		as.setRefund(refund);
		as.setReason(reason);
		as.setDescription(description);
		as.setCertificateImg(certificateImg);
		as.setCertificateImgName(certificateImgName);
		as.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = afterSaleService.updateNotNull(as);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**
	 * @Description:采购人确认收货
	 * @date 2019年7月16日
	 */
	@ApiOperation(value="采购人确认收货")
	@ApiImplicitParams({
		@ApiImplicitParam(name="afterSaleId",value="申请售后id",required=true,dataType="long",paramType="query")
	})
	@GetMapping("/confirmReceipt")
	public Map<String, Object> confirmReceipt(Long afterSaleId){
		AfterSale afterSale = afterSaleService.selectByKey(afterSaleId);
		//4：卖家货物邮寄中
		if(afterSale !=null && afterSale.getStatus() == 4){
			AfterSale as = new AfterSale();
			as.setId(afterSaleId);
			//5：售后完成
			as.setStatus(5);
			as.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			int result = afterSaleService.updateNotNull(as);
			return ResultUtil.resultMessage(result, "");
		}
		return ResultUtil.error("");
	}
	
	/**
	 * @Description:校验售后数量
	 * @date 2019年7月30日
	 */
	@ApiOperation(value="校验售后数量")
	@ApiImplicitParams({
		@ApiImplicitParam(name="orderItemsId",value="订单明细id",required=true,dataType="long",paramType="query")
	})
	@GetMapping("/checkAfterSaleNum")
	public Map<String, Object> checkAfterSaleNum(Long orderItemsId){
		if(orderItemsId ==null){
			ResultUtil.error("orderItemsId为空");
		}
		OrderItems orderItems = orderItemsService.selectByKey(orderItemsId);
		if(orderItems ==null){
			return ResultUtil.error("订单详细获取失败");
		}
		//购买时数量
		Integer totalNum = orderItems.getNum();
		
		Example example = new Example(AfterSale.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("orderItmesId", orderItemsId);
		List<AfterSale> afterSaleList = afterSaleService.selectByExample(example);
		//已售后的数量
		Integer afterSaleNum = 0;
		if(CollectionUtils.isNotEmpty(afterSaleList)){
			for(AfterSale as : afterSaleList){
				afterSaleNum += as.getProductNum();
			}
		}
		if(totalNum > afterSaleNum){
			Integer num  = totalNum-afterSaleNum;
			return ResultUtil.resultMessage(HttpResponseCode.OK, "可申请售后,剩余可申请数量：" + num, num);
		}
		return ResultUtil.error("不可申请售后");
	}
}
