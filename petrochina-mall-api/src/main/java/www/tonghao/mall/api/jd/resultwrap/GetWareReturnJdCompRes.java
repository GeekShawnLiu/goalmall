package www.tonghao.mall.api.jd.resultwrap;

import java.util.List;

import www.tonghao.mall.api.jd.attwrap.ComponentExportAttr;
import www.tonghao.mall.core.ResultWrap;

/**
 * 
 * Description: 查询商品逆向配送接口返回数据
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetWareReturnJdCompRes implements ResultWrap {

	private boolean success;

	private String resultMessage;

	private String resultCode;

	/**
	 * 可售后时，商品返回京东的方式
	 */
	private List<ComponentExportAttr> result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public List<ComponentExportAttr> getResult() {
		return result;
	}

	public void setResult(List<ComponentExportAttr> result) {
		this.result = result;
	}
}
