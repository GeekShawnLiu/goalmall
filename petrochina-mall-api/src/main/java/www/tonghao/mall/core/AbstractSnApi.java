package www.tonghao.mall.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.utils.JsonUtil;

import com.suning.api.DefaultSuningClient;


/**
 * 抽象商家接口
 *
 */
public abstract class AbstractSnApi<T extends ResultWrap> implements ApiCall<T>{
	
	private static Log log = LogFactory.getLog(AbstractSnApi.class);
	
	
	private ReqWrap reqWrap = null;
	
	public AbstractSnApi(){}
	
	public  AbstractSnApi(ReqWrap reqWrap){
		this.reqWrap = reqWrap;
	}
	@Override
	public T call() {
		if(reqWrap==null){
			throw new RuntimeException("请求对象不能为NULL");
		}
		DefaultSuningClient client = null;
		try {
			if(log.isInfoEnabled()){
				log.info(getClass().getName()+" 电商接口调用["+reqWrap.getApiUrl()+"], 参数："+JsonUtil.toJson(reqWrap.getParams()));
			}
			//允许调用失败次数
			for(int i = 1;i<=3;i++){
				if(reqWrap.getHttpMethod()!=null&&reqWrap.getHttpMethod()==HttpMethod.post){
					client = new DefaultSuningClient(reqWrap.getApiUrl(), reqWrap.getParams().get("appKey"),
							reqWrap.getParams().get("appSecret"), "json");
					if (client!=null) {
						break;
					}
				}
				log.error(getClass().getName()+" 调用["+reqWrap.getApiUrl()+"]失败"+i+"次");
			}
		} catch (Exception e){
			log.error(getClass().getName()+" 调用["+reqWrap.getApiUrl()+"]异常："+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resolver(client);
	}
	
	public void setReqWrap(ReqWrap reqWrap){
		this.reqWrap = reqWrap;
	}
	
	protected abstract T resolver(DefaultSuningClient client);
	
	protected abstract String result(DefaultSuningClient client);
}
