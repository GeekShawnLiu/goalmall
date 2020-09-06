package www.tonghao.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;


public class TokenApi {
   public Map<String, String> getToken(){
	   Map<String, String> map=new HashMap<String, String>();
	   JSONObject tokenParam = new JSONObject();
       tokenParam.put("sn", DJConfig.sn);
       tokenParam.put("appCode", DJConfig.appCode);
       Map<String, String> head = new HashMap<>();
       head.put("Authorization", DJConfig.authorization);
       String res = HttpUtils.doPost(DJConfig.token_url, tokenParam, head);
       JSONObject result = JSONObject.fromObject(res);
       String token = "";
       String validateKey = "";
       if("SUCCESS".equals(result.getString("code").trim().toUpperCase())) {
    	   map.put("token", result.getJSONObject("data").getString("token"));
    	   map.put("validateKey", result.getJSONObject("data").getString("validateKey"));
       }
       return map;
   }
}
