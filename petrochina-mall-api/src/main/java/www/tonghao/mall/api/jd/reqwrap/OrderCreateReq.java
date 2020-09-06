package www.tonghao.mall.api.jd.reqwrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.api.jd.entity.ExtContent;
import www.tonghao.mall.api.jd.entity.JdOrder;
import www.tonghao.mall.api.jd.entity.OrderSku;
import www.tonghao.mall.api.jd.entity.SkuPrice;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class OrderCreateReq implements ReqWrap {

	private JdOrder jdOrder;
	
	public OrderCreateReq(JdOrder jdOrder){
		this.jdOrder=jdOrder;
	}
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/submitOrder", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("thirdOrder", jdOrder.getThirdOrder());
		param.put("sku", getSku());
		param.put("name", jdOrder.getName());
		param.put("province", jdOrder.getProvince());
		param.put("city", jdOrder.getCity());
		param.put("county", jdOrder.getCounty());
		if(!StringUtils.isBlank(jdOrder.getTown())){
			param.put("town", jdOrder.getTown());
		}else{
			param.put("town", "0");
		}
		param.put("address", jdOrder.getAddress());
		if(!StringUtils.isBlank(jdOrder.getZip())){
			param.put("zip",jdOrder.getZip());
		}
		if(!StringUtils.isBlank(jdOrder.getPhone())){
			param.put("phone",jdOrder.getPhone());
		}
		param.put("mobile",jdOrder.getMobile());
		param.put("email",jdOrder.getEmail());
		if(!StringUtils.isBlank(jdOrder.getRemark())){
			param.put("remark",jdOrder.getRemark());
		}
		param.put("invoiceState",jdOrder.getInvoiceState()+"");
		param.put("invoiceType",jdOrder.getInvoiceType()+"");
		param.put("selectedInvoiceTitle",jdOrder.getSelectedInvoiceTitle()+"");
		if(jdOrder.getSelectedInvoiceTitle()==5){
			param.put("companyName",jdOrder.getCompanyName());
		}
		param.put("regCode",jdOrder.getRegCode());
		param.put("invoiceContent",jdOrder.getInvoiceContent()+"");
		param.put("paymentType",jdOrder.getPaymentType()+"");
		if(jdOrder.getPaymentType()==4){
			param.put("isUseBalance","1");
		}else{
			param.put("isUseBalance","0");
		} 
		param.put("submitState",jdOrder.getSubmitState()+"");
		if(jdOrder.getInvoiceType()==2 && jdOrder.getInvoiceState()==1){
			param.put("regCompanyName",jdOrder.getRegCompanyName());
			param.put("regAddr",jdOrder.getRegAddr());
			param.put("regPhone",jdOrder.getRegPhone());
			param.put("regBank",jdOrder.getRegBank());
			param.put("regBankAccount",jdOrder.getRegBankAccount());
			param.put("invoiceName",jdOrder.getInvoiceName());
			param.put("invoiceProvice",jdOrder.getInvoiceProvice());
			param.put("invoiceCity",jdOrder.getInvoiceCity());
			param.put("invoiceCounty",jdOrder.getInvoiceCounty());
			param.put("invoiceAddress",jdOrder.getInvoiceAddress());
			
		}
		param.put("invoicePhone",jdOrder.getInvoicePhone());
		if(jdOrder.getExtContents()!=null){
			param.put("extContent",getExtContent());
		}
		param.put("doOrderPriceMode",jdOrder.getDoOrderPriceMode()+"");
		if(jdOrder.getDoOrderPriceMode()==1){
			param.put("orderPriceSnap",getOrderPriceSnap());
		}
		if(!StringUtils.isBlank(jdOrder.getPoNo())){
			param.put("poNo",jdOrder.getPoNo());
		}
		if(!StringUtils.isBlank(jdOrder.getCustomerName())){
			param.put("customerName",jdOrder.getCustomerName());
		}
		return param;
	}
	private String getSku(){
		List<Map<String,Object>> skuList = Lists.newArrayList();
		List<OrderSku> sku = jdOrder.getSku();
		Map<String,Object> map=null;
		for (OrderSku orderSku : sku) {
			map=new HashMap<String, Object>();
			map.put("skuId", orderSku.getSkuId());
			map.put("num", orderSku.getNum());
			map.put("bNeedAnnex", orderSku.isbNeedAnnex());
			map.put("bNeedGift", orderSku.isbNeedGift());
			map.put("price", orderSku.getPrice());
			skuList.add(map);
		}
		return JsonUtil.toJson(skuList);
	}
	private String getExtContent(){
		if(jdOrder.getExtContents()!=null){
			Map<String,Object> map=new HashMap<String, Object>();
			ExtContent extContents = jdOrder.getExtContents();
			map.put("paymentType", extContents.getPaymentType());
			map.put("skus", JsonUtil.toJson(extContents.getSkus()));
			map.put("orderPrice", extContents.getOrderPrice());
			return JsonUtil.toJson(map);
		}
		return null;
	}
	private String getOrderPriceSnap(){
		List<Map<String,Object>> skuList = Lists.newArrayList();
		Map<String,Object> map=null;
		List<SkuPrice> orderPriceSnap = jdOrder.getOrderPriceSnap();
		for (SkuPrice skuPrice : orderPriceSnap) {
			map=new HashMap<String, Object>();
			map.put("price", skuPrice.getPrice());
			map.put("skuId", skuPrice.getSkuId());
			skuList.add(map);
		}
		return JsonUtil.toJson(skuList);
	}

}
