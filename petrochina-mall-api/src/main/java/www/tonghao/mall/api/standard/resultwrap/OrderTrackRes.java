package www.tonghao.mall.api.standard.resultwrap;

import www.tonghao.mall.api.standard.attwrap.TrackAttr;
import www.tonghao.mall.core.ResultWrap;


public class OrderTrackRes implements ResultWrap{
	private boolean success;
	private TrackAttr result;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public TrackAttr getResult() {
		return result;
	}
	public void setResult(TrackAttr result) {
		this.result = result;
	}
	
}
