package www.tonghao.dto;

import java.io.Serializable;
import java.util.List;

public class SelectTrackDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Track> track;

    private List<Waybill> waybill;

    public List<Track> getTrack() {
        return track;
    }

    public void setTrack(List<Track> track) {
        this.track = track;
    }

    public List<Waybill> getWaybill() {
        return waybill;
    }

    public void setWaybill(List<Waybill> waybill) {
        this.waybill = waybill;
    }
}
