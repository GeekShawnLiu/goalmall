package www.tonghao.common.utils.criteria;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 
 * criteria Like查询工具类
 */
public class CriteriaLikeUtil {

	/**
	 * 
	 * @param criteria criteria对象
	 * @param map  key 实体名称  value 值
	 */
	public static void  criteriaLike(Criteria criteria,Map<String, Object> map){
		if(!CollectionUtils.isEmpty(map)){
			Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, Object> next = iterator.next();
				String key = next.getKey();
				Object value = next.getValue();
				if(value != null && !"".equals(value)){
					criteria.andLike(key.toString(), "%"+value+"%");
				}
			}
		}
	}
	
	public static void criteriaBetwwen(Criteria criteria, String properties, Object param1, Object param2){
		if(param1 != null && !"".equals(param1) && param2 != null && !"".equals(param2)){
			criteria.andBetween(properties, param1, param2);
		}
	}
}
