package www.tonghao.mall.api.standard.resultwrap;

import java.util.List;

import www.tonghao.mall.api.standard.attwrap.ImageResultAttr;
import www.tonghao.mall.core.ResultWrap;



/**
 * 产品图片api结果类
 *
 */
public class ProductImageRes implements ResultWrap{
	private boolean success;
	private String desc;
	private List<ImageResultAttr> result;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<ImageResultAttr> getResult() {
		return result;
	}
	public void setResult(List<ImageResultAttr> result) {
		this.result = result;
	}
	
	
}
