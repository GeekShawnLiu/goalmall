package www.tonghao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.tonghao.service.OrderApiService;
import www.tonghao.util.ApiParamCheckUtil;
import www.tonghao.util.ApiResultUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private OrderApiService orderApiService;

    /**
     * 获取电子发票
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getInvoiceList", method = RequestMethod.POST)
    public String getInvoiceList(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        String order_ids = request.getParameter("order_ids");
        return orderApiService.getInvoiceList(order_ids, platformCode);
    }
}
