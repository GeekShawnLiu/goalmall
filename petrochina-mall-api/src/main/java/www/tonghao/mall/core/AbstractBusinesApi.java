package www.tonghao.mall.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.utils.HttpClient;
import www.tonghao.mall.api.utils.JsonUtil;



/**
 * 抽象商家接口
 * @author developer001
 *
 */
public abstract class AbstractBusinesApi<T extends ResultWrap> implements ApiCall<T>{
	
	private static Log log = LogFactory.getLog(AbstractBusinesApi.class);
	
	
	private ReqWrap reqWrap = null;
	
	public AbstractBusinesApi(){}
	
	public  AbstractBusinesApi(ReqWrap reqWrap){
		this.reqWrap = reqWrap;
	}
	@Override
	public T call() {
		if(reqWrap==null){
			throw new RuntimeException("请求对象不能为NULL");
		}
		String result = null;
		try {
			if(log.isInfoEnabled()){
				log.info(getClass().getName()+" 电商接口调用["+reqWrap.getApiUrl()+"], 参数："+JsonUtil.toJson(reqWrap.getParams()));
			}
			//允许调用失败次数
			for(int i = 1;i<=3;i++){
				if(reqWrap.getHttpMethod()!=null&&reqWrap.getHttpMethod()==HttpMethod.post){
					result = HttpClient.doPost(reqWrap.getApiUrl(), reqWrap.getParams());
				}else{
					result = HttpClient.doGet(reqWrap.getApiUrl(), reqWrap.getParams());
				}
				if(result!=null){
					break;
				}
				log.error(getClass().getName()+" 调用["+reqWrap.getApiUrl()+"]失败"+i+"次");
			}
		} catch (Exception e){
			e.printStackTrace();
			log.error(getClass().getName()+" 调用["+reqWrap.getApiUrl()+"]异常："+e.getLocalizedMessage());
		}
		if(result==null){
			log.error("result is null ");
			return emptyInstanceResultWrap();
		}
		
		if(log.isDebugEnabled()){
			log.debug("result = "+result);
		}
		return resolver(result);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private T emptyInstanceResultWrap(){
		Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            	Class<T> resultWrapClass = (Class<T>) parameterizedType[0];
            	try {
					return resultWrapClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
        }
        return null;
	}
	
	public void setReqWrap(ReqWrap reqWrap){
		this.reqWrap = reqWrap;
	}
	
	protected abstract T resolver(String result);
}
