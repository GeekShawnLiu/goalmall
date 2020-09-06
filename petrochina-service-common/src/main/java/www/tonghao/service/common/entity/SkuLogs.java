package www.tonghao.service.common.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "sku_logs")
public class SkuLogs extends BaseEntity {

    /**
     * 消息id
     */
    @Column(name = "mess_id")
    private String messId;

    /**
     * 供应商code
     */
    @Column(name = "mall_code")
    private String mallCode;

    /**
     * sku
     */
    @Column(name = "mess_sku")
    private String messSku;

    /**
     * 1 添加，2删除，3修改，4上架，5下架,6价格,7主图,8 订单拆分消息，9妥投订单消息，10取消订单消息，11售后
     */
    private Integer type;

    /**
     * 消息创建时间
     */
    @Column(name = "mess_time")
    private String messTime;

    /**
     * 0没删除，1删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 数据创建时间
     */
    @Column(name = "create_at")
    private String createAt;


    /**
     * 获取消息id
     *
     * @return mess_id - 消息id
     */
    public String getMessId() {
        return messId;
    }

    /**
     * 设置消息id
     *
     * @param messId 消息id
     */
    public void setMessId(String messId) {
        this.messId = messId;
    }

    /**
     * 获取供应商code
     *
     * @return mall_code - 供应商code
     */
    public String getMallCode() {
        return mallCode;
    }

    /**
     * 设置供应商code
     *
     * @param mallCode 供应商code
     */
    public void setMallCode(String mallCode) {
        this.mallCode = mallCode;
    }

    /**
     * 获取sku
     *
     * @return mess_sku - sku
     */
    public String getMessSku() {
        return messSku;
    }

    /**
     * 设置sku
     *
     * @param messSku sku
     */
    public void setMessSku(String messSku) {
        this.messSku = messSku;
    }

    /**
     * 获取1 添加，2删除，3修改
     *
     * @return type - 1 添加，2删除，3修改
     */
    public Integer getType() {
        return type;
    }

    /**
     *1 添加，2删除，3修改，4上架，5下架,6价格,7主图
     *
     * @param1 添加，2删除，3修改，4上架，5下架,6价格,7主图
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取消息创建时间
     *
     * @return mess_time - 消息创建时间
     */
    public String getMessTime() {
        return messTime;
    }

    /**
     * 设置消息创建时间
     *
     * @param messTime 消息创建时间
     */
    public void setMessTime(String messTime) {
        this.messTime = messTime;
    }

    /**
     * 获取0没删除，1删除
     *
     * @return is_delete - 0没删除，1删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置0没删除，1删除
     *
     * @param isDelete 0没删除，1删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取数据创建时间
     *
     * @return create_at - 数据创建时间
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * 设置数据创建时间
     *
     * @param createAt 数据创建时间
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}