package www.tonghao.mall.api.sn.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;

import com.suning.api.entity.govbus.BatchProdSaleStatusGetResponse.OnShelvesList;
/**
 * 苏宁商品上下架封装结果集
 */
public class ProductStateRes implements ResultWrap{
	private String error_code;	//API错误码
	private String error_msg;	//错误码中文描述
	private boolean success;
	private List<OnShelvesList> onShelvesList; //上下架数组
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
	public List<OnShelvesList> getOnShelvesList() {
		return onShelvesList;
	}
	public void setOnShelvesList(List<OnShelvesList> onShelvesList) {
		this.onShelvesList = onShelvesList;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
