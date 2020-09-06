package www.tonghao.config;

import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import www.tonghao.common.constant.RedisKeyConstant;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.CollectionUtil;

@Configuration
public class InItApplication implements InitializingBean {

	@Autowired
	private RedisDao redisDao;
	@Value("${server.port}")
	private Integer port;
	@Override
	public void afterPropertiesSet() throws Exception {
		if(port!=null&&port==8088){
			redisDao.deleteKey(RedisKeyConstant.UPDATEBATCHPRICE_KEY);
			redisDao.deleteKey(RedisKeyConstant.TOPUTAWAY_KEY);
			redisDao.deleteKey(RedisKeyConstant.TODOWN_KEY);
		}
		/*Set<String> keys = redisDao.getKeys("aaa");
		if(!CollectionUtil.collectionIsEmpty(keys)){
			for (String object : keys) {
				redisDao.deleteKey(object);
			}
		}*/
	}

}
