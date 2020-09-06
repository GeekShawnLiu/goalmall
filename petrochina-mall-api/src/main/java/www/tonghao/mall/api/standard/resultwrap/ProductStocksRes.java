package www.tonghao.mall.api.standard.resultwrap;

import java.util.List;

import www.tonghao.mall.api.standard.attwrap.ProductStockAttr;
import www.tonghao.mall.core.ResultWrap;



/**
 * 产品库存接口结果类
 *
 */
public class ProductStocksRes implements ResultWrap {
	private boolean success;
	private String desc;
	private List<ProductStockAttr> result;
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
	public List<ProductStockAttr> getResult() {
		return result;
	}
	public void setResult(List<ProductStockAttr> result) {
		this.result = result;
	}
}
