package www.tonghao.mall.api.jd.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class ProductSkusReq implements ReqWrap {

	
	/**
	 * 页码
	 */
	private int pageNo;
	/**
	 * 商品池编号 
	 */
	private String pageNum;
	
	/**
	 * 
	 * @param pageNum 商品池编号 
	 * @param pageNo 页码
	 */
	public ProductSkusReq(String pageNum,int pageNo){
    	this.pageNo=pageNo;
    	this.pageNum=pageNum;
	}
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getSkuByPage", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("pageNum", pageNum);
		param.put("pageNo", pageNo+"");
		return param;
	}

	
	
	
}
