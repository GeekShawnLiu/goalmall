package www.tonghao.mall.api.utils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class RedisUtil {
	
	private static String redis_host="10.200.37.19";
	private static String redis_auth="IgIiKtC4(a1OhOTvVE=";
	/*private static String redis_host="192.168.1.103";
	private static String redis_auth="flower";*/
	
	public static Jedis getJedisClient(){
		JedisShardInfo info=new JedisShardInfo(PropertiesUtil.getString("redis.host"),PropertiesUtil.getInt("redis.port"));
		info.setPassword(PropertiesUtil.getString("redis.auth"));
		Jedis jedis=new Jedis(info);
		return jedis;
	}
	
	public synchronized static Jedis getJedisClientOnline(){
		JedisShardInfo info=new JedisShardInfo(redis_host,6379);
		info.setPassword(redis_auth);
		Jedis jedis=new Jedis(info);
		return jedis;
	}
	
	public synchronized static void close(Jedis jedis){
		if(jedis!=null){
			jedis.close();
		}
	}

	
	
}
