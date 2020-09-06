package www.tonghao.mall.api.jd.call;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import www.tonghao.mall.api.jd.attwrap.AccessTokenResultAttr;
import www.tonghao.mall.api.jd.reqwrap.AccessTokenReq;
import www.tonghao.mall.api.jd.resultwrap.AccessTokenRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.api.utils.RedisUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
/**
 * 获取token
 * @author 李万林
 *
 */
public class AccessTokenApi extends AbstractBusinesApi<AccessTokenRes> {
	private static Log logger = LogFactory.getLog(AccessTokenApi.class);
	
	private static Map<String,AccessTokenResultAttr> emallToken = Maps.newConcurrentMap();
	public AccessTokenApi(){
		super(new AccessTokenReq());
	}
	
	/**
	 * 获取缓存token
	 * @param emallcode
	 * @return
	 */
	public static String getCacheToken(String emallcode){
		AccessTokenResultAttr tokenObj = emallToken.get(emallcode);
		if(tokenObj!=null){
			Date date = new Date();
			if(tokenObj.getTime()+(1000*60*60*23)<=date.getTime()){
				refresh(emallcode);
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
		AccessTokenApi instance = new AccessTokenApi();
		AccessTokenRes res = instance.call();
		if(res.isSuccess()){
			/*Jedis jedisClient = RedisUtil.getJedisClientOnline();
			if(jedisClient!=null){
				jedisClient.set(emallcode,res.getResult().getAccess_token());
				jedisClient.expire(emallcode,80000);
				RedisUtil.close(jedisClient);
			}*/
			emallToken.put(emallcode, res.getResult());
		}
	}
	
	
	@Override
	protected AccessTokenRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		AccessTokenRes res = new AccessTokenRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			JsonNode uidNode = resultNode.path("uid");
			JsonNode refresh_token_expires_node = resultNode.path("refresh_token_expires");
			JsonNode expires_in_node = resultNode.path("expires_in");
			JsonNode refresh_token_node = resultNode.path("refresh_token");
			JsonNode access_token_node = resultNode.path("access_token");
			JsonNode time_node = resultNode.path("time");
			AccessTokenResultAttr attr = new AccessTokenResultAttr();
			attr.setTime(time_node.asLong());
			attr.setUid(uidNode.asText());
			attr.setRefresh_token_expires(refresh_token_expires_node.asLong());
			attr.setExpires_in(expires_in_node.asLong());
			attr.setRefresh_token(refresh_token_node.asText());
			attr.setAccess_token(access_token_node.asText());
			res.setResult(attr);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
}