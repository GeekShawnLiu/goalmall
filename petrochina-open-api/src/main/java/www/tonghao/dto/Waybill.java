package www.tonghao.dto;

import java.io.Serializable;
import java.util.List;

public class Waybill implements Serializable {

    private static final long serialVersionUID = 1L;

    private String order_id;

    private Integer logistic_type;

    private String logistic_name;

    private String logistic_no;

    private String shipment_id;

    private List<ShipmentItems> shipment_items;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Integer getLogistic_type() {
        return logistic_type;
    }

    public void setLogistic_type(Integer logistic_type) {
        this.logistic_type = logistic_type;
    }

    public String getLogistic_name() {
        return logistic_name;
    }

    public void setLogistic_name(String logistic_name) {
        this.logistic_name = logistic_name;
    }

    public String getLogistic_no() {
        return logistic_no;
    }

    public void setLogistic_no(String logistic_no) {
        this.logistic_no = logistic_no;
    }

    public String getShipment_id() {
        return shipment_id;
    }

    public void setShipment_id(String shipment_id) {
        this.shipment_id = shipment_id;
    }

    public List<ShipmentItems> getShipment_items() {
        return shipment_items;
    }

    public void setShipment_items(List<ShipmentItems> shipment_items) {
        this.shipment_items = shipment_items;
    }
}
