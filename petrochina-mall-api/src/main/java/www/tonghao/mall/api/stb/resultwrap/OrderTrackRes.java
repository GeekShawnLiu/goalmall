package www.tonghao.mall.api.stb.resultwrap;

import java.util.List;

import www.tonghao.mall.api.stb.attwrap.OrderTrackAttr;
import www.tonghao.mall.core.ResultWrap;

public class OrderTrackRes implements ResultWrap {
	private boolean success;
	private String desc;
	private OrderTrackAttr orderTrackAttr;
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
	public OrderTrackAttr getOrderTrackAttr() {
		return orderTrackAttr;
	}
	public void setOrderTrackAttr(OrderTrackAttr orderTrackAttr) {
		this.orderTrackAttr = orderTrackAttr;
	}
	
}
