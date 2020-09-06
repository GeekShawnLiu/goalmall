package www.tonghao.mall.api.standard.call;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import www.tonghao.mall.api.standard.attwrap.AccessTokenResultAttr;
import www.tonghao.mall.api.standard.reqwrap.AccessTokenReq;
import www.tonghao.mall.api.standard.resultwrap.AccessTokenRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 获取token接口
 *
 */
public class AccessTokenApi extends AbstractBusinesApi<AccessTokenRes>{
	private static Map<String,AccessTokenResultAttr> emallToken = Maps.newConcurrentMap();

	public AccessTokenApi(String emallcode){
		super(new AccessTokenReq(emallcode));
	}
	
	/**
	 * 获取缓存token
	 * @param emallcode
	 * @return
	 */
	public static String getCacheToken(String emallcode){
		
		AccessTokenResultAttr tokenObj = emallToken.get(emallcode);
		if(tokenObj!=null){
			String expires_at = tokenObj.getExpires_at();
			if(StringUtils.isNotEmpty(expires_at)){
				try {
					Date expires = DateUtils.parseDate(expires_at, "yyyy-MM-dd HH:mm:ss");
					Date current = new Date();
					expires = DateUtils.addHours(expires, -1);//提前一小时刷新
					if(current.after(expires)){
						refresh(emallcode);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else{
			refresh(emallcode);
		}
		return emallToken.get(emallcode).getAccess_token();
	}
	
	/**
	 * 刷新电商token
	 * @param emallcode
	 */
	public static void refresh(String emallcode){
		AccessTokenApi instance = new AccessTokenApi(emallcode);
		AccessTokenRes res = instance.call();
		if(res.isSuccess()){
			emallToken.put(emallcode, res.getResult());
		}
	}
	
	/**
	 * 刷新所有
	 */
	public static void refreshAll(){
		Set<Entry<String, AccessTokenResultAttr>> entrySet = emallToken.entrySet();
		for(Entry<String, AccessTokenResultAttr> entry:entrySet){
			refresh(entry.getKey());
		}
	}
	
	@Override
	protected AccessTokenRes resolver(String result) {
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		AccessTokenRes res = new AccessTokenRes();
		res.setSuccess(success);
		if(success){
			JsonNode accessTokenNode = rootNode.path("access_token");
			JsonNode expiresAtNode = rootNode.path("expires_at");
			AccessTokenResultAttr resultAttr = new AccessTokenResultAttr();
			resultAttr.setAccess_token(accessTokenNode.asText());
			resultAttr.setExpires_at(expiresAtNode.asText());
			res.setResult(resultAttr);
		}else{
			JsonNode descNode = rootNode.path("desc");
			res.setDesc(descNode.asText());
		}
		return res;
	}
	
	public static Map<String,AccessTokenResultAttr> getStandardEmallToken(){
		return emallToken;
	}
}
