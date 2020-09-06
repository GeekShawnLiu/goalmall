package www.tonghao.mall.api.standard.reqwrap;

import java.util.List;
import java.util.Map;

import www.tonghao.mall.api.standard.attwrap.CreOrdSku;
import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.api.standard.entity.Returns;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class OrderReturnReq implements ReqWrap{
	
	private Returns returns;
	private String apiUrlPrefix;
	private String mallCode;
	
	public OrderReturnReq(String mallCode,Returns returns){
		this.returns = returns;
		this.mallCode=mallCode;
		apiUrlPrefix = "/api_config/standard/"+mallCode;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix+"/order_return_url", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		
		//请求参数
		param.put("token", AccessTokenApi.getCacheToken(mallCode));
		param.put("order_id",returns.getOrderId());
		param.put("skus", getSku());
		param.put("type", returns.getType()+"");
		param.put("package",returns.getPackages());//是否有包装
		param.put("desc", returns.getDesc());//售后问题描述
		param.put("contact_detail", getcontactDetail(returns));//采购人信息
		param.put("return_area", getreturnArea(returns));//地址信息
		return param;
	}

	//退换的商品编号和数量
	private String getSku(){
		List<Map<String,Object>> skuList = Lists.newArrayList();
		Map<String,Object> sku = null;
		List<CreOrdSku> orderItems = returns.getSkus();
		for(CreOrdSku item:orderItems){
			sku = Maps.newHashMap();
			sku.put("sku", item.getSku());
			sku.put("num", item.getNum());
			sku.put("price", item.getPrice());
			skuList.add(sku);
		}
		return JsonUtil.toJson(skuList);
	}
	
	//采购人信息
	private String getcontactDetail(Returns returns){
		Map<String,Object> contact = Maps.newHashMap();
		contact.put("customer_contact_name", returns.getCustomerContactName());
		contact.put("customer_mobile_phone", returns.getCustomerMobilePhone());
		contact.put("customer_tel", returns.getCustomerTel());
		return JsonUtil.toJson(contact);
	}
	
	//地址信息
	private String getreturnArea(Returns returns){
		Map<String,Object> contact = Maps.newHashMap();
		contact.put("return_province", returns.getReturnProvince());
		contact.put("return_city", returns.getReturnCity());
		contact.put("return_county", returns.getReturnCounty());
		contact.put("return_place",returns.getReturnPlace());
		return JsonUtil.toJson(contact);
	}
}
