package www.tonghao.mall.api.jd.resultwrap;

import java.util.List;

import www.tonghao.mall.core.ResultWrap;

public class ProductSkusRes implements ResultWrap {
	private boolean success;
	private String resultMessage;
	private String resultCode;
	
	private int pageCount;
	private List<String> skuIds;
	
	
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
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<String> getSkuIds() {
		return skuIds;
	}
	public void setSkuIds(List<String> skuIds) {
		this.skuIds = skuIds;
	}
	
	
	
	
}
