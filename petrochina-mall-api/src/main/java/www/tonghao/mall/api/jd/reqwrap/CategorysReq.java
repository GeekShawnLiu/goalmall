package www.tonghao.mall.api.jd.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

public class CategorysReq implements ReqWrap {

	private String pageNo;//页号，从1开始；
	private String pageSize;//页大小，最大值5000；
	private String parentId;//父ID
	private String catClass;//分类等级（0:一级； 1:二级；2：三级）
	public CategorysReq(String pageNo,String pageSize){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	public CategorysReq(String pageNo,String pageSize,String parentId,String catClass){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.parentId = parentId;
		this.catClass = catClass;
	}
	public CategorysReq(String pageNo,String pageSize,String catClass){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.catClass = catClass;
	}
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getCategorys", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("pageNo", pageNo);
		param.put("pageSize", pageSize);
		param.put("parentId", parentId);
		param.put("catClass", catClass);
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		return param;
	}

}
