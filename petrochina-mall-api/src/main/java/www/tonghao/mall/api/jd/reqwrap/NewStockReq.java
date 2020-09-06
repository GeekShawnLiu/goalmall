package www.tonghao.mall.api.jd.reqwrap;

import java.util.List;
import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.api.jd.entity.Sku;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class NewStockReq implements ReqWrap {

	private List<Sku> skus;
	
	//格式：2_2830_51805_0 (分别代表 1、2、3 级地址) 
	private String area;
	
	public NewStockReq(List<Sku> skus,String area){
		this.skus=skus;
		this.area=area;
	}
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getNewStockById", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("area", area);
		param.put("skuNums", getSku());
		return param;
	}
	private String getSku(){
		List<Map<String,Object>> skuList = Lists.newArrayList();
		Map<String,Object> sku = null;
		List<Sku> skus_s = skus;
		for(Sku item:skus_s){
			sku = Maps.newHashMap();
			sku.put("skuId", item.getSkuId());
			sku.put("num", item.getNum());
			skuList.add(sku);
		}
		return JsonUtil.toJson(skuList);
	}

}
