package www.tonghao.mall.api.jd.attwrap;

import java.util.List;

public class TrackAttr {
	private String  jdOrderId;
	private List<Track> orderTrack;
	public String getJdOrderId() {
		return jdOrderId;
	}
	public void setJdOrderId(String jdOrderId) {
		this.jdOrderId = jdOrderId;
	}
	public List<Track> getOrderTrack() {
		return orderTrack;
	}
	public void setOrderTrack(List<Track> orderTrack) {
		this.orderTrack = orderTrack;
	}
	
}
