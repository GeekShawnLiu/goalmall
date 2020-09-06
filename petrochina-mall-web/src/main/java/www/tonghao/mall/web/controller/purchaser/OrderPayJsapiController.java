package www.tonghao.mall.web.controller.purchaser;

import com.google.zxing.WriterException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.pay.wx.WXPayUtil;
import www.tonghao.common.pay.wx.WXpayConfig;
import www.tonghao.common.utils.IpAddressUtil;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.JsApiResult;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.utils.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: petrochina
 * @description: 公众号支付jsapi
 * @author:
 * @create: 2019-08-08 19:09
 */
@Api(value="orderPayPriceController", description="jsapi支付")
@RestController
@RequestMapping("/orderJsPayPrice")
public class OrderPayJsapiController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UsersService usersService;

    @Value("${webSiteAddress}")
    private String webSiteAddress;


    @ApiOperation(value = "不拆单Jsapi支付公众号支付", notes = "Jsapi支付公众号支付")
    @RequestMapping(value = "/payJsapi", method = RequestMethod.GET)
    public Map<String, Object> pay_wx_or_ali_payJsapi(Long orderMasterId, Integer payType, Integer payCode, HttpServletResponse response, HttpServletRequest request) throws IOException, WriterException {
        Users user = UserUtil.getUser(request);
        if(user != null){
        	Users users = usersService.selectByKey(user.getId());
        	if(StringUtils.isEmpty(users.getOpenid())){
        		return ResultUtil.error("未授权");
        	}
            try {
                JsApiResult result = new JsApiResult();
                Map<String, String> mobileWxOrAliQrcode = ordersService.mobileWxOrAliQrcode(orderMasterId,user.getId(),webSiteAddress+"/mall/orderPayPrice/payCallBack",payCode,payType,getIpAddr(request),users.getOpenid());
                if(mobileWxOrAliQrcode!=null){
                	long nowTime= System.currentTimeMillis() /1000;
                	result.setPrepayId("prepay_id=" + mobileWxOrAliQrcode.get("prepay_id"));
                    result.setSignType("MD5");
                    result.setNonceStr(WXPayUtil.generateNonceStr());
                    result.setTimeStamp(String.valueOf(nowTime));
                    Map<String,String> signMap = new HashMap<>();
                    signMap.put("appId", WXpayConfig.APPID);
                    signMap.put("timeStamp",result.getTimeStamp());
                    signMap.put("nonceStr",result.getNonceStr());
                    signMap.put("package",result.getPrepayId());
                    signMap.put("signType",result.getSignType());
                    result.setPaySign(WXPayUtil.createSign(signMap,WXpayConfig.APIKEY, "MD5"));
                    signMap.put("paySign",result.getPaySign());
                    System.out.println(result);
                    return ResultUtil.success(JsonUtil.toJson(signMap));
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
    @ApiOperation(value = "单个Jsapi支付公众号支付", notes = "Jsapi支付公众号支付")
    @RequestMapping(value = "/payJsOneapi", method = RequestMethod.GET)
    public Map<String, Object> pay_wx_or_ali_payJsOneapi(Long orderId, Integer payType, Integer payCode, HttpServletResponse response, HttpServletRequest request) throws IOException, WriterException {
        Users user = UserUtil.getUser(request);
        if(user != null){
        	Users users = usersService.selectByKey(user.getId());
        	if(StringUtils.isEmpty(users.getOpenid())){
        		return ResultUtil.error("未授权");
        	}
            try {
                JsApiResult result = new JsApiResult();
                Map<String, String> mobileWxOrAliQrcode = ordersService.mobileWxOrAliQrcodeOne(orderId,user.getId(),webSiteAddress+"/mall/orderPayPrice/payCallBack",payCode,payType,getIpAddr(request),users.getOpenid());
                if(mobileWxOrAliQrcode!=null){
                	long nowTime= System.currentTimeMillis() /1000;
                	result.setPrepayId("prepay_id=" + mobileWxOrAliQrcode.get("prepay_id"));
                    result.setSignType("MD5");
                    result.setNonceStr(WXPayUtil.generateNonceStr());
                    result.setTimeStamp(String.valueOf(nowTime));
                    Map<String,String> signMap = new HashMap<>();
                    signMap.put("appId", WXpayConfig.APPID);
                    signMap.put("timeStamp",result.getTimeStamp());
                    signMap.put("nonceStr",result.getNonceStr());
                    signMap.put("package",result.getPrepayId());
                    signMap.put("signType",result.getSignType());
                    result.setPaySign(WXPayUtil.createSign(signMap,WXpayConfig.APIKEY, "MD5"));
                    signMap.put("paySign",result.getPaySign());
                    System.out.println(result);
                    return ResultUtil.success(JsonUtil.toJson(signMap));
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    @ApiOperation(value = "不拆单h5浏览器支付", notes = "h5浏览器支付")
    @RequestMapping(value = "/payMwebapi", method = RequestMethod.GET)
    public Map<String, Object> pay_wx_or_ali_payMwebapi(Long orderMasterId, Integer payType, Integer payCode,String ip, HttpServletResponse response, HttpServletRequest request) throws IOException, WriterException {
        Users user = UserUtil.getUser(request);
        if(user != null){
            try {
                String url = ordersService.mobileWxMwebOrAliQrcode(orderMasterId,user.getId(),webSiteAddress+"/mall/orderPayPrice/payCallBack",payCode,payType,ip);
                return ResultUtil.success(url);
                } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    @ApiOperation(value = "单个h5浏览器支付", notes = "h5浏览器支付")
    @RequestMapping(value = "/payMwebOneapi", method = RequestMethod.GET)
    public Map<String, Object> pay_wx_or_ali_payMwebOneapi(Long orderId, Integer payType, Integer payCode,String ip, HttpServletResponse response, HttpServletRequest request) throws IOException, WriterException {
        Users user = UserUtil.getUser(request);
        if(user != null){
            try {
                String url = ordersService.mobileWxMwebOrAliQrcodeOne(orderId,user.getId(),webSiteAddress+"/mall/orderPayPrice/payCallBack",payCode,payType,ip);
                return ResultUtil.success(url);
                } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }

}
