package www.tonghao.mall.api.sn.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;

import com.suning.api.entity.govbus.ApplyRejectedAddResponse.InfoList;

/**
 * 退货订单接口结果集
 *
 */
public class OrderReturnAllRes implements ResultWrap{
	private boolean success;
	private List<InfoList> infoList;	//退货商品信息
	private String orderId;		//企业会员编号
	private String error_code;	//API错误码
	private String error_msg;	//错误码中文描述
	public List<InfoList> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<InfoList> infoList) {
		this.infoList = infoList;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
