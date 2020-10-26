package www.tonghao.service.common.entity;

import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "message_pool")
public class MessagePool extends BaseEntity {

    /**
     * 消息类型
     * type=2 商品价格变更
     * type=4 代表商品上下架变更消息
     * type=6 代表添加、删除商品池内的商品
     * type=16 商品介绍及规格参数变更消息
     * type=5 订单已完成
     * type=10 代表订单取消，不区分取消原因
     * type=12 配送单生成成功消息
     */
    @Column(name = "type")
    private Long type;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "update_at")
    private String updateAt;

    /**
     * type = 4 : 1上架 2下架
     * type = 6 : 1添加 2删除
     * type = 10 : 1取消 2拒收
     */
    @Column(name = "state")
    private Integer state;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "market_price")
    private BigDecimal marketPrice;

    @Column(name = "is_delete")
    private int isDelete;

    @Column(name = "sku")
    private String sku;

    @Column(name = "order_sn")
    private String orderSn;

    @Column(name = "platform_code")
    private String platformCode;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
}
