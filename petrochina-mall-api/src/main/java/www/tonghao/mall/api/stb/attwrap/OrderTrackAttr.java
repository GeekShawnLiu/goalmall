package www.tonghao.mall.api.stb.attwrap;

import java.util.List;

public class OrderTrackAttr {
    private String supplierOrderId;
    
    private List<Track> trackList;

	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}

	public List<Track> getTrackList() {
		return trackList;
	}

	public void setTrackList(List<Track> trackList) {
		this.trackList = trackList;
	}
    
    
    
}
