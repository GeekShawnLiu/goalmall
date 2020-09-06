package www.tonghao.mall.api.standard.resultwrap;

import java.util.List;

import www.tonghao.mall.api.standard.attwrap.PriceAttr;
import www.tonghao.mall.core.ResultWrap;


/**
 * 产品价格api结果类
 * @author developer001
 *
 */
public class ProductPriceRes implements ResultWrap{
	private boolean success;
	private String desc;
	private List<PriceAttr> result;
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
	public List<PriceAttr> getResult() {
		return result;
	}
	public void setResult(List<PriceAttr> result) {
		this.result = result;
	}
	
}
