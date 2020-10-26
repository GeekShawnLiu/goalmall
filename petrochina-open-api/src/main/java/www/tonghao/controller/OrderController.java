package www.tonghao.controller;

import com.zaxxer.hikari.util.FastList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.tonghao.dto.OrderDto;
import www.tonghao.service.OrderApiService;
import www.tonghao.util.ApiParamCheckUtil;
import www.tonghao.util.ApiResultUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderApiService orderApiService;

    /**
     * 预提交订单
     *
     * @param orderDto
     * @param request
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        return orderApiService.submit(orderDto, platformCode);
    }

    /**
     * 确认订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirm(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, true);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String orderSn = request.getParameter("order_id");
        String platformCode = request.getParameter("platformCode");
        return orderApiService.confirm(orderSn, platformCode);
    }

    /**
     * 取消订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, true);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String orderSn = request.getParameter("order_id");
        String platformCode = request.getParameter("platformCode");
        return orderApiService.cancel(orderSn, platformCode);
    }

    /**
     * 订单查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String select(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, true);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String orderSn = request.getParameter("order_id");
        String platformCode = request.getParameter("platformCode");
        return orderApiService.select(orderSn, platformCode);
    }

    /**
     * 物流信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/track", method = RequestMethod.POST)
    public String track(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, true);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String orderSn = request.getParameter("order_id");
        String platformCode = request.getParameter("platformCode");
        return orderApiService.track(orderSn, platformCode);
    }
}
