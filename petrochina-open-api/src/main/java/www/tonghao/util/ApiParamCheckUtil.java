package www.tonghao.util;

import org.apache.commons.lang3.StringUtils;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.SpringUtil;

import javax.servlet.http.HttpServletRequest;

public class ApiParamCheckUtil {

    /**
     * api 请求参数校验
     *
     * @param request
     * @param checkSku   是否校验sku
     * @param checkOrder 是否校验订单
     * @return
     */
    public static String check(HttpServletRequest request, boolean checkSku, boolean checkOrder) {
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            return "token不能为空";
        }
        RedisDao redisDao = SpringUtil.getBean(RedisDao.class);
        if (!redisDao.isNotKey(token)) {
            return "token_expired";
        }
        String platformCode = request.getParameter("platformCode");
        if (StringUtils.isBlank(platformCode)) {
            return "platformCode不能为空";
        }
        String sku = request.getParameter("sku");
        if (checkSku && StringUtils.isBlank(sku)) {
            return "sku不能为空";
        }
        String order_id = request.getParameter("order_id");
        if(checkOrder && StringUtils.isBlank(order_id)){
            return "order_id不能为空";
        }
        return null;
    }
}
