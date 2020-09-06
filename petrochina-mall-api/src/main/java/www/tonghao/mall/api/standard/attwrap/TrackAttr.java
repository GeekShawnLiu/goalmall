package www.tonghao.mall.api.standard.attwrap;

import java.util.List;

public class TrackAttr {
	private String  order_id;
	private List<Track> track;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public List<Track> getTrack() {
		return track;
	}
	public void setTrack(List<Track> track) {
		this.track = track;
	}
	
}
