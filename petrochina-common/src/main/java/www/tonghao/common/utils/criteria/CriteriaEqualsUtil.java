package www.tonghao.common.utils.criteria;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 
 * criteria equlas查询工具类
 */
public class CriteriaEqualsUtil {
	
	/**
	 * 
	 * @param criteria criteria对象
	 * @param map  key 实体名称  value 值
	 */
	public static void  criteriaEquals(Criteria criteria,Map<String, Object> map){
		if(!CollectionUtils.isEmpty(map)){
			Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, Object> next = iterator.next();
				String key = next.getKey();
				Object value = next.getValue();
				if(value != null && !"".equals(value)){
					criteria.andEqualTo(key.toString(), value);
				}
			}
		}
	}

}
