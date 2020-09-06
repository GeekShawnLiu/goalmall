package www.tonghao.mall.api.stb.call;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.stb.attwrap.AccessTokenResultAttr;
import www.tonghao.mall.api.stb.reqwarp.AccessTokenReq;
import www.tonghao.mall.api.stb.resultwrap.AccessTokenRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class AccessTokenApi extends AbstractBusinesApi<AccessTokenRes> {
	
	
	private static Log logger = LogFactory.getLog(AccessTokenApi.class);
	private static Map<String,AccessTokenResultAttr> emallToken = Maps.newConcurrentMap();
	public AccessTokenApi() {
		super(new AccessTokenReq());
	}
	
	/**
	 * 获取缓存token
	 * @param emallcode
	 * @return
	 */
	public static String getCacheToken(){
		
		AccessTokenResultAttr tokenObj = emallToken.get(Constant.STB_CODE);
		if(tokenObj!=null){
			String expires_at = tokenObj.getExpires_at();
			if(StringUtils.isNotEmpty(expires_at)){
				try {
					Date expires = DateUtils.parseDate(expires_at, "yyyy-MM-dd HH:mm:ss");
					Date current = new Date();
					expires = DateUtils.addHours(expires, -1);//提前一小时刷新
					if(current.after(expires)){
						refresh();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else{
			refresh();
		}
		return emallToken.get(Constant.STB_CODE).getAccess_token();
	}
	/**
	 * 刷新电商token
	 * @param emallcode
	 */
	public static void refresh(){
		AccessTokenApi instance = new AccessTokenApi();
		AccessTokenRes res = instance.call();
		if(res.isSuccess()){
			emallToken.put(Constant.STB_CODE, res.getAccessTokenResultAttr());
		}
	}
	@Override
	protected AccessTokenRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		AccessTokenRes accessTokenRes=new AccessTokenRes();
		boolean success = successNode.asBoolean();
		accessTokenRes.setSuccess(success);
		if(success) {
			JsonNode resultNode = rootNode.path("result");
			AccessTokenResultAttr accessTokenResultAttr=new AccessTokenResultAttr();
			for(JsonNode node:resultNode){
				String token = node.path("access_token").asText();
				String expires = node.path("expires_in").asText();
				accessTokenResultAttr.setAccess_token(token);
				accessTokenResultAttr.setExpires_at(expires);
			}
			accessTokenRes.setAccessTokenResultAttr(accessTokenResultAttr);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			accessTokenRes.setDesc(descNode.asText());
		}
		return accessTokenRes;
	}

}
