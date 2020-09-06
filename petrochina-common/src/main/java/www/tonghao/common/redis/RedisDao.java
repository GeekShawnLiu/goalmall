package www.tonghao.common.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisDao {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	public void setKey(String key,Object object){
		ValueOperations opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, object);
	}

	public void setKey(String key,Object object, Long time){
		ValueOperations opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, object, time, TimeUnit.MILLISECONDS);
	}
	
	public Object getValue(String key){
		ValueOperations opsForValue = redisTemplate.opsForValue();
		return opsForValue.get(key);
	}
	
	public boolean isNotKey(String key){
		return redisTemplate.hasKey(key);
	}
	public Set getKeys(String pattern){
		return redisTemplate.keys(pattern+"*");
	}
	public boolean setNx(String key,Object object){
		return  redisTemplate.getConnectionFactory().getConnection().setNX(redisTemplate.getKeySerializer().serialize(key), redisTemplate.getKeySerializer().serialize(object));
	}
	
	public void deleteKey(String key){
		boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);
        }
	}
}
