package www.tonghao.mall.api.standard.reqwrap;

import java.util.List;
import java.util.Map;

import www.tonghao.mall.api.standard.attwrap.CreOrdSku;
import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.api.standard.entity.Order;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class OrderCreateReq implements ReqWrap{
	private String emallcode;
	private String apiUrlPrefix;
	private Order order;
	public OrderCreateReq(String emallcode,Order order){
		apiUrlPrefix = "/api_config/standard/"+emallcode;
		this.emallcode=emallcode;
		this.order=order;
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue(apiUrlPrefix +"/order_submit_url", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		
		//收货人信息
		param.put("token", AccessTokenApi.getCacheToken(emallcode));
		param.put("yggc_order", order.getYggcOrder());
		param.put("sku", getSku());
		param.put("name", order.getName());
		param.put("address", order.getAddress());
		param.put("zip", order.getZip());
		param.put("phone", order.getPhone());
		param.put("mobile", order.getMobile());
		
		//用户信息
		param.put("email", order.getEmail());
		param.put("remark", order.getRemark());
		param.put("dep_name", order.getDepName());
		
		//地址信息
		param.put("province", order.getProvince());
		param.put("city", order.getCity());
		param.put("county", order.getCounty());
		
		param.put("invoice_title", order.getInvoiceTitle());
		param.put("invoice_type", order.getInvoiceType()+"");
		param.put("invoice_org_code", order.getInvoiceOrgCode());
		//当invoice_type=2 时   增值票收票人、注册电话、开户银行、开户行帐号、注册地址必填
		if (order.getInvoiceType()==2) {
			param.put("invoice_name", order.getInvoiceName());
			param.put("invoice_phone", order.getInvoicePhone());
			param.put("invoice_bank", order.getInvoiceBank());
			param.put("invoice_bank_code", order.getInvoiceBankCode());
			param.put("invoice_address", order.getInvoiceAddress());
		}
		param.put("Invoice_mobile", order.getInvoiceMobile());
		param.put("invoice_receipt_address", order.getInvoiceReceiptAddress());
		
		param.put("payment", order.getPayment()+"");
		param.put("order_price", order.getOrderPrice().toString());
		param.put("freight", order.getFreight().toString());
		param.put("mode", order.getMode()+"");
		return param;
	}

	private String getSku(){
		List<Map<String,Object>> skuList = Lists.newArrayList();
		Map<String,Object> sku = null;
		List<CreOrdSku> orderItems = order.getCreOrdSkus();
		for(CreOrdSku item:orderItems){
				sku = Maps.newHashMap();
				sku.put("sku", item.getSku());
				sku.put("num", item.getNum());
				sku.put("price", item.getPrice());
				sku.put("mode", item.getMode());//价格模式：1-默认协议价
				skuList.add(sku);
		}
		return JsonUtil.toJson(skuList);
	}
	
}
