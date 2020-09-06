package www.tonghao.mall.api.sn.attwrap;

/**
 * 
 * 物流消息接口
 */
public class MessageTrack implements Message {
	
   private String orderNo;
   
   private String orderItemNo;
   
   private String sku;
   //1.商品出库,2. 商品妥投 ,3.商品拒收 ,4.商品退货 
   private int status;
   
   private int type;
   
   private String id;
public String getOrderNo() {
	return orderNo;
}
public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}
public String getOrderItemNo() {
	return orderItemNo;
}
public void setOrderItemNo(String orderItemNo) {
	this.orderItemNo = orderItemNo;
}
public String getSku() {
	return sku;
}
public void setSku(String sku) {
	this.sku = sku;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
   
   
   
}
