package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.BigDecimalUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.CartItems;
import www.tonghao.mall.service.CartItemsService;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;

@Api(value="placeOrderController",description="商城下单api")
@RestController(value="placeOrderController")
@RequestMapping(value="/place_order")
public class PlaceOrderController {

	@Autowired
	private CartItemsService cartItemsService;
	
	/**
	 * 
	 * Description: 订单金额
	 * 
	 * @data 2019年7月11日
	 * @param 
	 * @return
	 */
	@ApiOperation(value = "订单页面金额")
	@RequestMapping(value="/getOrderTotal", method=RequestMethod.GET)
	public Map<String, Object> getOrderTotal(HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user != null){
			//根据订单商品信息判断支付方式
			Map<String, Object> selectMap = new HashMap<String, Object>();
			selectMap.put("userId", user.getId());
			selectMap.put("isChecked", 1);
			List<CartItems> cartItems = cartItemsService.findListByUserId(selectMap);
			// 积分活动商品标识
			boolean activityProductFlag = false;
			for (CartItems cartItems2 : cartItems) {
				if (cartItems2.getActivityId() != null) {
					activityProductFlag |= true;
				}
			}
			Map<String, Object> resultMap = ResultUtil.success("");
			resultMap.put("isActivity", activityProductFlag);
			if(activityProductFlag){
				//有积分活动商品 存在积分
				List<Activity> findCartIntegral = cartItemsService.findCartIntegral(user.getId());
				resultMap.put("activityIntegral", findCartIntegral);
				BigDecimal orderTotal = BigDecimalUtil.ZERO;
				BigDecimal integralTotal = BigDecimalUtil.ZERO;
				BigDecimal moneyTotal = BigDecimalUtil.ZERO;
				for (Activity activity : findCartIntegral) {
					orderTotal = BigDecimalUtil.add(orderTotal, activity.getActivityTotal());
					//消费积分
					BigDecimal consumptionIntegral = activity.getConsumptionIntegral();
					//积分余额
					BigDecimal integralBalance = activity.getIntegralBalance();
					if(integralBalance == null){
						return ResultUtil.error("未查询到活动积分 请联系管理员分配");
					}
					if(BigDecimalUtil.compareTo(integralBalance, BigDecimalUtil.ZERO) < 0){
						integralTotal = BigDecimalUtil.add(integralTotal, BigDecimalUtil.add(consumptionIntegral, integralBalance));
						moneyTotal = BigDecimalUtil.add(moneyTotal, BigDecimalUtil.multiply(integralBalance, new BigDecimal("-1")));
						activity.setMoney(BigDecimalUtil.multiply(integralBalance, new BigDecimal("-1")));
					}else{
						integralTotal = BigDecimalUtil.add(integralTotal, consumptionIntegral);
						activity.setMoney(new BigDecimal("0"));
					}
				}
				resultMap.put("orderTotal", orderTotal);
				resultMap.put("integralTotal", integralTotal);
				resultMap.put("moneyTotal", moneyTotal);
			}else{
				//没有积分活动
				List<Activity> findCartIntegral = cartItemsService.findCartIntegral(user.getId());
				BigDecimal orderTotal = BigDecimalUtil.ZERO;
				for (Activity activity : findCartIntegral) {
					orderTotal = BigDecimalUtil.add(orderTotal, activity.getActivityTotal());
				}
				resultMap.put("orderTotal", orderTotal);
				resultMap.put("moneyTotal", orderTotal);
			}
			return resultMap;
		}
		return ResultUtil.error("登录异常");
	}
}
