package www.tonghao.mall.api.sn.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;

import com.suning.api.entity.govbus.OrderlogistnewGetResponse.PackageIds;

/**
 * 获取订单物流详情（新）封装结果集
 *
 */
public class OrderTrackRes implements ResultWrap{
	private boolean success;
	private String isPackage;	//Y：有包裹信息；N：暂无包裹信息
	private String orderId;		//订单号
	private List<PackageIds> packageIds;	//物流信息
	private String error_code;	//API错误码
	private String error_msg;	//错误码中文描述
	public String getIsPackage() {
		return isPackage;
	}
	public void setIsPackage(String isPackage) {
		this.isPackage = isPackage;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public List<PackageIds> getPackageIds() {
		return packageIds;
	}
	public void setPackageIds(List<PackageIds> packageIds) {
		this.packageIds = packageIds;
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
