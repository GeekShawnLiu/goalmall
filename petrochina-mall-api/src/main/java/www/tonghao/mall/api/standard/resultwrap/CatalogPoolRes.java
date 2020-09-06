package www.tonghao.mall.api.standard.resultwrap;

import java.util.List;

import www.tonghao.mall.api.standard.attwrap.CatalogPoolAttr;
import www.tonghao.mall.core.ResultWrap;



/**
 * 品目池结果封装
 * @author developer001
 *
 */
public class CatalogPoolRes implements ResultWrap{
	private boolean success;
	private String desc;
	private List<CatalogPoolAttr> result;
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
	public List<CatalogPoolAttr> getResult() {
		return result;
	}
	public void setResult(List<CatalogPoolAttr> result) {
		this.result = result;
	}
	
}
