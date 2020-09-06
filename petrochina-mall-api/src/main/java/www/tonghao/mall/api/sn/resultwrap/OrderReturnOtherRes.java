package www.tonghao.mall.api.sn.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;

import com.suning.api.entity.govbus.ReturnpartorderAddResponse.InfoList;

/**
 * 部分退货接口结果集
 *
 */
public class OrderReturnOtherRes implements ResultWrap{
	private List<InfoList> infoList;	//部分退货明细
	private String isSuccess;	//Y-成功；N-失败
	private String orderId;		//政企订单号
	private String error_code;	//API错误码
	private String error_msg;	//错误码中文描述
	private boolean success;
	public List<InfoList> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<InfoList> infoList) {
		this.infoList = infoList;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
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
