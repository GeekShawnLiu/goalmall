package www.tonghao.platform.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.AfterSale;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.AfterSaleService;
import www.tonghao.service.common.service.OrderRefundItemService;
import www.tonghao.utils.UserUtil;

/**
 * @Description:售后退款
 * @date 2019年7月16日
 */
@Api(value="afterSaleRefundController", description="售后退款")
@RestController
@RequestMapping("/afterSaleRefund")
public class AfterSaleRefundController {
	
	@Autowired
	private AfterSaleService afterSaleService;
	
	@Autowired
	private OrderRefundItemService orderRefundItemService;
	
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
		@ApiImplicitParam(name="status",value="当前状态：0:待审核，1:不通过，2：通过，3：买家货物邮寄中，4：卖家货物邮寄中，5：售后完成， 6：卖家确认收货，待退款，"
				+ "7：卖家确认收货，驳回退款，8：已退款，售后完成，9：卖家拒绝退款，10：卖家同意退款，11：已退款",required=false,dataType="int",paramType="query"),
	})
	@GetMapping("/getAfterSaleAuditList")
	public PageInfo<AfterSale> getAfterSaleAuditList(PageBean page, HttpServletRequest request, String ordersSn, String afterSaleSn, Integer type, Integer status){
		Users user = UserUtil.getUser(request);
		PageInfo<AfterSale> afterSaleList = null;
		if(user != null && user.getType() == 6){
			Example example = new Example(AfterSale.class);
			Criteria createCriteria = example.createCriteria();
			
			createCriteria.andEqualTo("afterSaleSn", afterSaleSn);
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
			afterSaleList = afterSaleService.getAfterSaleList(page, example);
		}
		return afterSaleList;
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
		return afterSale;
	}
	
	/**
	 * @Description:退款
	 * @date 2019年7月23日
	 */
	@ApiOperation(value="退款")
	@ApiImplicitParams({
		@ApiImplicitParam(name="afterSaleId",value="申请售后id",required=true,dataType="long",paramType="query")
	})
	@GetMapping("/refund")
	public Map<String, Object> refund(HttpServletRequest request, Long afterSaleId){
		Users user = UserUtil.getUser(request);
		if(user ==null || afterSaleId ==null){
			return ResultUtil.error("操作失败");
		}
		
		if(user.getType() != 6){
			return ResultUtil.error("权限不足");
		}
		
		Map<String, Object> afterSaleRefundResult = orderRefundItemService.afterSaleRefund(afterSaleId, "2");
		if("success".equals(afterSaleRefundResult.get("status"))){
			Map<String, Object> result = afterSaleService.refund(user, afterSaleId);
			return result;
		}else{
			return afterSaleRefundResult;
		}
	}
}
