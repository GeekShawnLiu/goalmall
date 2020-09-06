package www.tonghao.common.pay.wx;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.common.utils.HttpClient;
import www.tonghao.common.utils.JsonUtil;

public class AccrssTokenApi {
	private String code;
	public AccrssTokenApi(String code){
		this.code=code;
		
	}
	public AccessToken call(){
		String doGet = HttpClient.doGet(WXpayConfig.ACCESS_TOKEN_URL, params(code));
		JsonNode rootNode = JsonUtil.readTree(doGet);
		AccessToken accessToken=new AccessToken();
        accessToken.setOpenid(rootNode.path("openid").asText());
        accessToken.setAccess_token(rootNode.path("access_token").asText());
        return accessToken;
	}
	public Map<String, String> params(String code){
		Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("appid", WXpayConfig.APPID);
        params.put("grant_type", "authorization_code");
        params.put("secret", WXpayConfig.SECRET);
        return params;
	}
}
