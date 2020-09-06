package www.tonghao.mall.api.stb.reqwarp;

import java.util.Map;

import com.google.common.collect.Maps;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.stb.call.AccessTokenApi;
import www.tonghao.mall.api.stb.entity.Order;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

public class OrderCreateReq implements ReqWrap {

	private Order order;
	public OrderCreateReq(Order order) {
		this.order=order;
	}
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.STB_CODE+"/submitOrder", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken());
		param.put("thirdOrder", order.getThirdOrder());
		param.put("Sku", JsonUtil.toJson(order.getSku()));
		param.put("name", order.getName());
		param.put("province", order.getProvince());
		param.put("city", order.getCity());
		param.put("county", order.getCounty());
		param.put("address", order.getAddress());
		param.put("zip", order.getZip());
		param.put("phone", order.getPhone());
		param.put("mobile", order.getMobile());
		param.put("email", order.getEmail());
		param.put("remark", order.getRemark());
		param.put("creatorName", order.getCreatorName());
		param.put("creatorPhone", order.getCreatorPhone());
		param.put("creatorMobile", order.getCreatorMobile());
		param.put("createdTime", order.getCreatedTime());
		param.put("invoiceState", order.getInvoiceState());
		param.put("invoiceType", order.getInvoiceType());
		param.put("companyName", order.getCompanyName());
		if("2".equals(order.getInvoiceType())) {
			param.put("companyAdd", order.getCompanyAdd());
			param.put("BillingPhone", order.getBillingPhone());
			param.put("TaxNo", order.getTaxNo());
			param.put("BankName", order.getBankName());
			param.put("BankAccno", order.getBankAccno());
		}
		param.put("invoiceContent", order.getInvoiceContent());
		param.put("payment", order.getPayment());
		param.put("customerName", order.getCustomerName());
		return param;
	}

}
