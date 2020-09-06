package www.tonghao.mall.api.jd.reqwrap;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

/**
 * 
 * Description: 填写发运信息接口请求参数
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class UpdateSendSkuReq implements ReqWrap {

	/**
	 * 服务单号
	 * 必填
	 */
	private String afsServiceId;

	/**
	 * 运费
	 * 必填
	 */
	private BigDecimal freightMoney;
	
	/**
	 * 发运公司：圆通快递、申通快递、韵达快递、中通快递、宅急送、EMS、顺丰快递
	 * 必填
	 */
	private String expressCompany;
	
	/**
	 * 发货日期，格式为yyyy-MM-dd HH:mm:ss
	 * 必填
	 */
	private String deliverDate;
	
	/**
	 * 货运单号，最大50字符
	 * 必填
	 */
	private String expressCode;
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/" + Constant.JD_CODE + "/updateSendSku", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> param = new HashMap<>();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("param", getParam());
		return param;
	}

	public String getParam() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("afsServiceId", afsServiceId);
		paramMap.put("freightMoney", freightMoney);
		paramMap.put("expressCompany", expressCompany);
		paramMap.put("deliverDate", deliverDate);
		paramMap.put("expressCode", expressCode);
		return JsonUtil.toJson(paramMap);
	}
	
	public UpdateSendSkuReq(String afsServiceId, BigDecimal freightMoney, String expressCompany, String deliverDate, String expressCode) {
		this.afsServiceId = afsServiceId;
		this.freightMoney = freightMoney;
		this.expressCompany = expressCompany;
		this.deliverDate = deliverDate;
		this.expressCode = expressCode;
	}

	public String getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(String afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public BigDecimal getFreightMoney() {
		return freightMoney;
	}

	public void setFreightMoney(BigDecimal freightMoney) {
		this.freightMoney = freightMoney;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public UpdateSendSkuReq() {
	}
}
