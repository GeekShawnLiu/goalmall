package www.tonghao.common.pay.wx;

public class WXpayConfig {
	public static String APPID = "wx9f6ae5f650b43bf1";//微信公众号APPID
    public static String WXPAYMENTACCOUNT = "1546735561";//微信公众号的商户号
    public static String APIKEY = "zhongyouhuifudianshangpingtai123";//微信公众号的商户支付密钥
    public static String SECRET = "867d6cf6918236dc6d26b81d7634f92f";//
	
    public static String basePath = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static String refundApiPath = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //public static String WXSSL_STRING_PAHT = "D:\\1546735561_20190824_cert\\apiclient_cert.p12";
    public static String WXSSL_STRING_PAHT = "/data/weixin_pay_cert/apiclient_cert.p12";
    
}
