package www.tonghao.service.common.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "product_logs")
public class ProductLogs extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 商品ID
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 日志类型:1：价格变动日志
     */
    private Integer type;

    /**
     * 修改之前值
     */
    @Column(name = "before_value")
    private String beforeValue;

    /**
     * 修改之后值
     */
    @Column(name = "after_value")
    private String afterValue;

    /**
     * 创建人
     */
    private String creater;

    @Column(name = "creater_uid")
    private Long createrUid;

    /**
     * @return created_at
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return updated_at
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取商品ID
     *
     * @return product_id - 商品ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置商品ID
     *
     * @param productId 商品ID
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取日志类型:1：价格变动日志
     *
     * @return type - 日志类型:1：价格变动日志
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置日志类型:1：价格变动日志
     *
     * @param type 日志类型:1：价格变动日志
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取修改之前值
     *
     * @return before_value - 修改之前值
     */
    public String getBeforeValue() {
        return beforeValue;
    }

    /**
     * 设置修改之前值
     *
     * @param beforeValue 修改之前值
     */
    public void setBeforeValue(String beforeValue) {
        this.beforeValue = beforeValue;
    }

    /**
     * 获取修改之后值
     *
     * @return after_value - 修改之后值
     */
    public String getAfterValue() {
        return afterValue;
    }

    /**
     * 设置修改之后值
     *
     * @param afterValue 修改之后值
     */
    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue;
    }

    /**
     * 获取创建人
     *
     * @return creater - 创建人
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置创建人
     *
     * @param creater 创建人
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * @return creater_uid
     */
    public Long getCreaterUid() {
        return createrUid;
    }

    /**
     * @param createrUid
     */
    public void setCreaterUid(Long createrUid) {
        this.createrUid = createrUid;
    }
    
    @Override
    public boolean isCudLog() {
    	return false;
    }
}