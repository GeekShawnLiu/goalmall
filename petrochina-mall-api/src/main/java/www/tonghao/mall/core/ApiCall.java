package www.tonghao.mall.core;



/**
 * 接口调用
 * @author developer001
 *
 */
public interface ApiCall<T> {
	
	/**
	 * 调用
	 * @return
	 */
	T call();
}
