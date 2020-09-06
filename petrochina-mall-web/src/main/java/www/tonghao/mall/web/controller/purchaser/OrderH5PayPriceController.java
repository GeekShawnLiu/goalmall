package www.tonghao.mall.web.controller.purchaser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.redis.RedisDao;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;


@RestController
@RequestMapping("/orderH5PayPrice")
@Api(value="OrderH5PayPriceController", description="h5支付")
public class OrderH5PayPriceController {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private RedisDao redisDao;
	@Value("${webSiteAddress}")
	private String webSiteAddress;
	
	@ApiOperation(value = "支付", notes = "支付api")
	@RequestMapping(value = "/pay_price", method = RequestMethod.GET)
	public Map<String, Object> pay_price(HttpServletRequest request,String info,Long orderMasterId){
		Users user = UserUtil.getUser(request);
		if(user!=null&&!StringUtils.isEmpty(info)){
			ordersService.payH5Price(user, info,orderMasterId, webSiteAddress);
		}
		return null;
	}
	
	@ApiOperation(value = "支付", notes = "支付api")
	@RequestMapping(value = "/pay_wx_price", method = RequestMethod.GET)
	public Map<String, Object> pay_wx_price(HttpServletRequest request,Long orderMasterId){
		Users user = UserUtil.getUser(request);
		Map<String, Object> payWxPrice=new HashMap<>();
		if(user!=null){
			 try {
				payWxPrice = ordersService.payWxPrice(user,orderMasterId, webSiteAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return payWxPrice;
	}
}
