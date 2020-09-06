package www.tonghao.mall.web.controller.purchaser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.pay.PayUtils;
import www.tonghao.common.pay.utils.RSA;
import www.tonghao.common.pay.wx.AESUtil;
import www.tonghao.common.pay.wx.AccessToken;
import www.tonghao.common.pay.wx.AccrssTokenApi;
import www.tonghao.common.pay.wx.WXPayUtil;
import www.tonghao.common.pay.wx.WXpayConfig;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.IpAddressUtil;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.OrderItemsService;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.JobOrderItems;
import www.tonghao.service.common.entity.JobOrders;
import www.tonghao.service.common.entity.OrderRefundItem;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.IntegralUserService;
import www.tonghao.service.common.service.OrderRefundItemService;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.utils.UserUtil;

@Api(value="orderPayPriceController", description="pc支付")
@RestController
@RequestMapping("/orderPayPrice")
public class OrderPayPriceController {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private OrderItemsService orderItemsService;
	@Autowired
	private RedisDao redisDao;
	@Value("${webSiteAddress}")
	private String webSiteAddress;
	@Autowired
	private IntegralUserService integralUserService;
	
	@Autowired
	private OrderRefundItemService orderRefundItemService;
	
	@Autowired
	private UsersService usersService;
	
	
	@ApiOperation(value = "一码付支付二维码", notes = "一码付支付二维码api")
	@RequestMapping(value = "/pay_price", method = RequestMethod.GET)
	public  void pay_price(Long orderMasterId,String token,HttpServletResponse response) throws IOException, WriterException {
		if(orderMasterId!=null&&!StringUtils.isEmpty(token)){
		    Users user = (Users) redisDao.getValue(token);
		 	String payQRCode=null;
			try {
				payQRCode = ordersService.payQRCode(orderMasterId,user.getId(),webSiteAddress+"/mall/orderPayPrice/payCallBack");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(payQRCode!=null){
			    Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			    // 指定编码格式
			    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			    // 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
			    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			    // 编码内容,编码类型(这里指定为二维码),生成图片宽度,生成图片高度,设置参数
			    BitMatrix bitMatrix = new MultiFormatWriter().encode(payQRCode, BarcodeFormat.QR_CODE, 200, 200, hints);
			    //设置请求头
			    response.setHeader("Content-Type","application/octet-stream");
			    response.setHeader("Content-Disposition", "attachment;filename=" + "支付.png");
			    OutputStream outputStream = response.getOutputStream();
			    MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
			    outputStream.flush();
			    outputStream.close();
			}
		}
	}
	@ApiOperation(value = "一码付后台现金支付二维码", notes = "一码付后台现金支付二维码api")
	@RequestMapping(value = "/payMoney", method = RequestMethod.GET)
	public void payMoney(Long orderId,String token,HttpServletRequest request,HttpServletResponse response) throws WriterException, IOException {
		if(orderId!=null){
			Users user = (Users) redisDao.getValue(token);
			String url=null;
			try {
				url = ordersService.payQRCodePlatform(orderId,user.getId(), webSiteAddress+"/mall/orderPayPrice/payPlatformCallBack");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(url!=null){
			    Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			    // 指定编码格式
			    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			    // 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
			    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			    // 编码内容,编码类型(这里指定为二维码),生成图片宽度,生成图片高度,设置参数
			    BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);
			    //设置请求头
			    response.setHeader("Content-Type","application/octet-stream");
			    response.setHeader("Content-Disposition", "attachment;filename=" + "支付.png");
			    OutputStream outputStream = response.getOutputStream();
			    MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
			    outputStream.flush();
			    outputStream.close();
			}
		}
	}
	
	/**
	 * payCode 1 微信，2支付宝
	 * payType 移动端还是pc端 1 电脑支付，2移动端支付
	 */
	@ApiOperation(value = "微信或支付宝支付二维码", notes = "微信或支付宝二维码api")
	@RequestMapping(value = "/pay_wx_or_ali_qrcode", method = RequestMethod.GET)
	public  void pay_wx_or_ali_qrcode(Long orderMasterId,String token,Integer payType,Integer payCode,HttpServletResponse response,HttpServletRequest request) throws IOException, WriterException {
		if(orderMasterId!=null&&!StringUtils.isEmpty(token)){
		    Users user = (Users) redisDao.getValue(token+"@"+IpAddressUtil.getIpAddress(request));
		    if(user!=null){
		    	String payQRCode=null;
				try {
					payQRCode=ordersService.payWxOrAliQrcode(orderMasterId,user.getId(),webSiteAddress+"/mall/orderPayPrice/payCallBack",payCode,payType,getIpAddr(request));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(payQRCode!=null){
				    Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
				    // 指定编码格式
				    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				    // 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
				    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
				    // 编码内容,编码类型(这里指定为二维码),生成图片宽度,生成图片高度,设置参数
				    BitMatrix bitMatrix = new MultiFormatWriter().encode(payQRCode, BarcodeFormat.QR_CODE, 200, 200, hints);
				    //设置请求头
				    response.setHeader("Content-Type","application/octet-stream");
				    response.setHeader("Content-Disposition", "attachment;filename=" + "支付.png");
				    OutputStream outputStream = response.getOutputStream();
				    MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
				    outputStream.flush();
				    outputStream.close();
				}
		    }
		}
	}
	/**
	 * payCode 1 微信，2支付宝
	 * payType 移动端还是pc端 1 电脑支付，2移动端支付
	 */
	@ApiOperation(value = "微信或支付宝支付二维码后台现金支付", notes = "微信或支付宝支付二维码后台现金支付api")
	@RequestMapping(value = "/plat_pay_wx_or_ali_qrcode", method = RequestMethod.GET)
	public void platPayWxOrAliQrcode(Long orderId,String token,Integer payType,Integer payCode,HttpServletRequest request,HttpServletResponse response) throws WriterException, IOException {
		if(orderId!=null){
			Users user = (Users) redisDao.getValue(token+"@"+IpAddressUtil.getIpAddress(request));
			String url=null;
			try {
				url = ordersService.platPayWxOrAliQrcode(orderId,user.getId(), webSiteAddress+"/mall/orderPayPrice/payCallBack",payCode,payType,getIpAddr(request));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(url!=null){
			    Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			    // 指定编码格式
			    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			    // 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
			    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			    // 编码内容,编码类型(这里指定为二维码),生成图片宽度,生成图片高度,设置参数
			    BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);
			    //设置请求头
			    response.setHeader("Content-Type","application/octet-stream");
			    response.setHeader("Content-Disposition", "attachment;filename=" + "支付.png");
			    OutputStream outputStream = response.getOutputStream();
			    MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
			    outputStream.flush();
			    outputStream.close();
			}
		}
	}
	
	@ApiOperation(value = "支付成功回调", notes = "支付成功回调api")
	@RequestMapping(value = "/payCallBack")
	public void payResult(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
        Map<String, String> map = WXPayUtil.xmlToMap(result);
        System.out.println(map);
        if(WXPayUtil.isSignatureValid(map, WXpayConfig.APIKEY, "MD5")){
        	String result_code = map.get("result_code");
        	String return_code = map.get("return_code");
        	if (null != map && "SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
        		int updatePayStatus=ordersService.updatePayStatus(map.get("out_trade_no"),JsonUtil.toJson(map),map.get("transaction_id"));
        		if(updatePayStatus>0){
        			PrintWriter writer = response.getWriter();
					writer.write(returnXML("SUCCESS"));
					writer.close();
    			}else{
    				PrintWriter writer = response.getWriter();
					writer.write(returnXML("FAIL"));
					writer.close();
    			}
        	}
        }
	}
	
	@ApiOperation(value = "后台支付成功回调", notes = "后台支付成功回调api")
	@RequestMapping(value = "/payPlatformCallBack")
	public void  payPlatformCallBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
        Map<String, String> map = WXPayUtil.xmlToMap(result);
        System.out.println(map);
        if(WXPayUtil.isSignatureValid(map, WXpayConfig.APIKEY, "MD5")){
        	String result_code = map.get("result_code");
        	String return_code = map.get("return_code");
        	if (null != map && "SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
        		int updatePayStatus = ordersService.updatePayStatusPlatform(map.get("transaction_id"),JsonUtil.toJson(map));
    			if(updatePayStatus>0){
    				PrintWriter writer = response.getWriter();
					writer.write(returnXML("SUCCESS"));
					writer.close();
    			}else{
    				PrintWriter writer = response.getWriter();
					writer.write(returnXML("FAIL"));
					writer.close();
    			}
        	}
        }
		
		/*Enumeration<String> enums = request.getParameterNames();
		Map<String, String> params=new HashMap<>();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			params.put(name.toLowerCase(), request.getParameter(name).toString());
		}
		String outer_trade_no = params.get("outer_trade_no");
		Map<String, String> sPara = PayUtils.paraFilter(params);
		System.out.println("预下订单原始值："+params);
		System.out.println("去掉关键参数后的值："+sPara);
		String prestr = PayUtils.createLinkString(sPara, false);
		System.out.println("拼接后的值："+prestr);
		boolean verify = RSA.verify(prestr, params.get("sign").toString(), PayUtils.MERCHANT_PUBLIC_KEY, PayUtils.charset);
		if(verify){
			System.out.println("验签结果："+verify);
			int updatePayStatus = ordersService.updatePayStatusPlatform(outer_trade_no,JsonUtil.toJson(params));
			if(updatePayStatus>0){
				return returnXML("SUCCESS");
			}else{
				return returnXML("FAIL");
			}
		}
		return "";*/
		
	}
	@ApiOperation(value = "退款成功回调", notes = "退款成功回调api")
	@RequestMapping(value = "/refundCallBack")
	public  void refundCallBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
        Map<String, String> map = WXPayUtil.xmlToMap(result);
        System.out.println(map);
        if(null != map){
        	String return_code = map.get("return_code");
        	if ("SUCCESS".equals(return_code)) {
        		String req_info = map.get("req_info");
        		String decryptData = AESUtil.decryptData(req_info, WXpayConfig.APIKEY);
        		Map<String, String> mapReq = WXPayUtil.xmlToMap(decryptData);
        		String refund_status = mapReq.get("refund_status");
        		OrderRefundItem entity=new OrderRefundItem();
    			entity.setRefundCode(mapReq.get("refund_id"));
    			OrderRefundItem ori = orderRefundItemService.selectEntityOne(entity);
    			if(ori!=null){
    				if("SUCCESS".equals(refund_status)){
    					ori.setRefundStatus(2);	
    					ori.setCjJson(JsonUtil.toJson(map)+"-=-="+JsonUtil.toJson(mapReq));
    					int updateNotNull = orderRefundItemService.updateNotNull(ori);
    					if(updateNotNull>0){
    						if(ori.getPayId()==3){
    					    	Example example=new Example(JobOrders.class);
    							Criteria createCriteria = example.createCriteria();
    							createCriteria.andEqualTo("sn", ori.getOrderSn());
    							List<Orders> order_list = ordersService.selectByExample(example);
    							if(!CollectionUtil.collectionIsEmpty(order_list)){
    								Orders jobOrders = order_list.get(0);
    								Example item_example=new Example(JobOrderItems.class);
    								Criteria item_createCriteria = item_example.createCriteria();
    								item_createCriteria.andEqualTo("orderId", jobOrders.getId());
    								List<OrderItems> item_order = orderItemsService.selectByExample(item_example);
    								if(!CollectionUtil.collectionIsEmpty(item_order)){
    									OrderItems jobOrderItems = item_order.get(0);
    									integralUserService.addBalance(ori.getRefundUserId(), jobOrderItems.getActivityId(), jobOrders.getId(), ori.getRefundIntegralPrice(), "手动退还积分");
    								}
    							}
    					    }
    						System.out.println("退款结果：退款成功");
    						PrintWriter writer = response.getWriter();
    						writer.write(returnXML("SUCCESS"));
    						writer.close();
    					}else{
						    System.out.println("退款结果：退款失败");
					        PrintWriter writer = response.getWriter();
							writer.write(returnXML("FAIL"));
							writer.close();
    					}
    				}
    			}
        	}
        }
       
		
		/*Enumeration<String> enums = request.getParameterNames();
		Map<String, String> params=new HashMap<>();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			params.put(name.toLowerCase(), request.getParameter(name).toString());
		}
		String outer_trade_no = params.get("inner_trade_no");
		Map<String, String> sPara = PayUtils.paraFilter(params);
		System.out.println("预下订单原始值："+params);
		System.out.println("去掉关键参数后的值："+sPara);
		String prestr = PayUtils.createLinkString(sPara, false);
		System.out.println("拼接后的值："+prestr);
		boolean verify = RSA.verify(prestr, params.get("sign").toString(), PayUtils.MERCHANT_PUBLIC_KEY, PayUtils.charset);
		if(verify){
			System.out.println("验签结果："+verify);
			OrderRefundItem entity=new OrderRefundItem();
			entity.setRefundCode(outer_trade_no);
			OrderRefundItem ori = orderRefundItemService.selectEntityOne(entity);
			if(ori!=null){
				ori.setRefundStatus(2);	
				ori.setCjJson(JsonUtil.toJson(params));
				int updateNotNull = orderRefundItemService.updateNotNull(ori);
				if(updateNotNull>0){
					if(ori.getPayId()==3){
				    	Example example=new Example(JobOrders.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("sn", ori.getOrderSn());
						List<Orders> order_list = ordersService.selectByExample(example);
						if(!CollectionUtil.collectionIsEmpty(order_list)){
							Orders jobOrders = order_list.get(0);
							Example item_example=new Example(JobOrderItems.class);
							Criteria item_createCriteria = item_example.createCriteria();
							item_createCriteria.andEqualTo("orderId", jobOrders.getId());
							List<OrderItems> item_order = orderItemsService.selectByExample(item_example);
							if(!CollectionUtil.collectionIsEmpty(item_order)){
								OrderItems jobOrderItems = item_order.get(0);
								integralUserService.addBalance(ori.getRefundUserId(), jobOrderItems.getActivityId(), jobOrders.getId(), ori.getRefundIntegralPrice(), "手动退还积分");
							}
						}
				    }
					PrintWriter writer = response.getWriter();
					writer.write("success");
					writer.close();
				}
			}
			
			
		}*/
		
	}
	@ApiOperation(value = "获取openId", notes = "获取openIdapi")
	@RequestMapping(value = "/openId")
	public void openId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String code = request.getParameter("code");
		/*String state = request.getParameter("state");*/
		if(!StringUtils.isEmpty(code)){
			AccrssTokenApi accrssTokenApi= new AccrssTokenApi(code);
			AccessToken accrssToken = accrssTokenApi.call();
			if(accrssToken!=null){
				String openid = accrssToken.getOpenid();
				if(!StringUtils.isEmpty(openid)){
					Users user = UserUtil.getUser(request);
					Users users = usersService.selectByKey(user.getId());
					users.setOpenid(openid);
					usersService.updateNotNull(users);
				}
			}
		}
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
    private String returnXML(String return_code) {
		return "<xml><return_code><![CDATA["

				+ return_code

				+ "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}
}
