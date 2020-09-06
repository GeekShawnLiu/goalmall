package www.tonghao.mall.api.sn.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;

import com.suning.api.entity.govbus.FullAddressGetResponse.ResultInfo;
/**
 * 苏宁全量地址封装结果集
 */
public class AddressGetRes implements ResultWrap{
	private String error_code;	//API错误码
	private String error_msg;	//错误码中文描述
	private boolean success;
	private List<ResultInfo> resultInfo; //地址数组
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
	public List<ResultInfo> getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(List<ResultInfo> resultInfo) {
		this.resultInfo = resultInfo;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
