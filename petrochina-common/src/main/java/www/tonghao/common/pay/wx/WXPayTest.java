package www.tonghao.common.pay.wx;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import sun.misc.BASE64Decoder;

public class WXPayTest {
   public static void main(String[] args) throws Exception {
	  /* Map<String, String> map = new HashMap<>();
       map.put("appid", WXpayConfig.APPID);//公众账号ID
       map.put("mch_id", WXpayConfig.WXPAYMENTACCOUNT);//商户号
       map.put("body", "商品-订单支付");//商品描述
       map.put("nonce_str", WXPayUtil.generateNonceStr());
       map.put("notify_url", "http://lyl.sinopr.org/mall/orderPayPrice/payCallBack");//通知地址
       map.put("out_trade_no", "2019282918411");//订单号
       map.put("spbill_create_ip", "192.168.1.1");//终端ip
       map.put("trade_type", "JSAPI");//交易类型
       map.put("total_fee", "1");//总金额
       map.put("openid", "osvrA5xhZSohJE6ikEILuvdw3Sgg");//总金额
       String createSign = WXPayUtil.createSign(map, WXpayConfig.APIKEY, "MD5");
       map.put("sign", createSign);//签名
       System.out.println(map);
     //将订单对象转为xml格式
       String s = null;
       try {
            String mapToXml = WXPayUtil.mapToXml(map);
            Map<String, String> httpOrder = WXPayUtil.httpOrder(mapToXml);
            System.out.println(httpOrder);
            System.out.println(WXPayUtil.isSignatureValid(httpOrder, WXpayConfig.APIKEY, "MD5"));
            
       } catch (Exception e) {
           e.printStackTrace();
       }*/
	   String strs="DYlaf7qf+FN52VgVPSVKyawrCfVygafI8hdri+r7bmdjf94aIUzww9E4VYD3z0TRjoVV02iToq8zv97AL/RSLIZbI9PSNdl4s2PVC9fyECwkYU/2uzKfnqp4uy7wetLfywZiQgUq8eXv3bOj75IJC9bxTM/WqpmTrrVivdc31YhZtJGwv/HCjqK8CPn4byJgRKc2qVIAHywsAeURwpAIxHXYFFXDzZPGastuEOC8074SvlwAKZnbhmaZ7g49XX9YKS0bz79nVivL3P3Js8/R4Qh8c27z2UqrthwMnmQM02JVwEFfkwVOAbw9HFPnikESvLIuynnuNUL/g993iN8VuMagJ5fWild8ZXhE1gx+1/2gcSj+InFDfutbGALu/4vyI/FSGHGTBL0/wamNNAa0XhdHNWTfJb4TNnDvqzQQNWVsODMP4XG0LmeCuj+KFaeapCaw5fEmqheD0Ld1rJer+I6pINTowJtWDn89HoPcgSmK5H3+TT5ip1586NFSHquuvKp3epDOaPgAsxjhGHpOHqIEl7v+tvuCimrLASWLRvGqE+rF6tazUjzW7LMlxZu4LNJUDJefzgkY8L3S8ioA3vIS1sOXjg174IGbsNiL7astm/NOQ2TANRu7ooKXt2rULRmgdAFFkvaD1S2bZpz59tQ56lFdCfzN2NE7ClRlln0b2I1OoKGVooEQCbbfAS0HXI8ZbqWqVzURLu60M1iPfrsOLsVPP/B7+j5Y6Whakpexd+vIl/mV5nauxkyVWrnhtCfSB/LXNFHg6F3Zi9dyqtmJA7c5gJgH/Z86774m9YTcCM8TIbFsmdj3DDqjhT5e3id8ZWw105IXslP+3zU9cq7lG9Q8WWWXkq56zYxZNO2Gmw4xIAhHu8BMMNZva9rRSs8VmEHPKmH9akXMnAXDs5bpnCWGSe+qOQhicmEdcQ+9zlWagYOVezOugfCiKk0jxjk+j2SQsGrAf6gFtIwYu0sxH1HwXAt1s6uQqD9+XB41dSC6Cf+3Wh3CHIX+gpx19d6leyipFEEl5Utpk+Px0Q==";
	   
	   String decryptData = AESUtil.decryptData(strs, WXpayConfig.APIKEY);
	   System.out.println(decryptData);
   }
  
}
