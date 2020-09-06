package www.tonghao.mall.api.jd.reqwrap;

import java.util.List;
import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.api.jd.entity.Freight;
import www.tonghao.mall.api.jd.entity.Sku;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FreightReq  implements ReqWrap {

	private Freight freight;
	public FreightReq(Freight freight){
		this.freight=freight;
	}
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getFreight", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("sku", getSku());
		param.put("province", freight.getProvince());
		param.put("city", freight.getCity());
		param.put("county", freight.getCounty()); 
		param.put("town", freight.getTown());
		param.put("paymentType", freight.getPaymentType()+"");
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		return param;
	}
	private String getSku(){
		List<Map<String,Object>> skuList = Lists.newArrayList();
		Map<String,Object> sku = null;
		List<Sku> skus = freight.getSkus();
		for(Sku item:skus){
			sku = Maps.newHashMap();
			sku.put("skuId", item.getSkuId());
			sku.put("num", item.getNum());
			skuList.add(sku);
		}
		return JsonUtil.toJson(skuList);
	}
}
