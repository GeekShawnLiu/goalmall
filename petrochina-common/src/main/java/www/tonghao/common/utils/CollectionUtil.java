package www.tonghao.common.utils;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

public class CollectionUtil {
	public static boolean collectionIsEmpty(Collection coll) {
		if(coll==null||CollectionUtils.isEmpty(coll)||"null".equals(coll.toString())||"[null]".equals(coll.toString())||"[]".equals(coll.toString())) {
			return true;
		}
		return false;
	}
}
