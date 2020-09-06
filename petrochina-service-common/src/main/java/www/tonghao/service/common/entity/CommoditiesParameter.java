package www.tonghao.service.common.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


/**  
* <p>Title: CommoditiesParameter</p>  
* <p>Description: 聚合商品参数</p>  
* @author YML  
* @date 2018年12月19日  
*/ 
@Table(name = "commodities_parameter")
public class CommoditiesParameter extends BaseEntity{

    /** 
	 * 
	 */  
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "commodities_id")
    private Long commoditiesId;

    /**
     * 大数据平台参数id
     */
    @Column(name = "stand_param_id")
    private String standParamId;

    /**
     * 大数据平台参数值id
     */
    @Column(name = "stand_value_id")
    private String standValueId;

    /**
     * 1单选, 2复选, 3录入, 4自动填充,5其他参数
     */
    private Integer ttype;

    /**
     * 排序越小越靠前
     */
    private Float position;

    /**
     * 参数值
     */
    @Column(name = "param_value")
    private String paramValue;

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
     * @return commodities_id
     */
    public Long getCommoditiesId() {
        return commoditiesId;
    }

    /**
     * @param commoditiesId
     */
    public void setCommoditiesId(Long commoditiesId) {
        this.commoditiesId = commoditiesId;
    }

    /**
     * 获取大数据平台参数id
     *
     * @return stand_param_id - 大数据平台参数id
     */
    public String getStandParamId() {
        return standParamId;
    }

    /**
     * 设置大数据平台参数id
     *
     * @param standParamId 大数据平台参数id
     */
    public void setStandParamId(String standParamId) {
        this.standParamId = standParamId;
    }

    /**
     * 获取大数据平台参数值id
     *
     * @return stand_value_id - 大数据平台参数值id
     */
    public String getStandValueId() {
        return standValueId;
    }

    /**
     * 设置大数据平台参数值id
     *
     * @param standValueId 大数据平台参数值id
     */
    public void setStandValueId(String standValueId) {
        this.standValueId = standValueId;
    }

    /**
     * 获取1单选, 2复选, 3录入, 4自动填充,5其他参数
     *
     * @return ttype - 1单选, 2复选, 3录入, 4自动填充,5其他参数
     */
    public Integer getTtype() {
        return ttype;
    }

    /**
     * 设置1单选, 2复选, 3录入, 4自动填充,5其他参数
     *
     * @param ttype 1单选, 2复选, 3录入, 4自动填充,5其他参数
     */
    public void setTtype(Integer ttype) {
        this.ttype = ttype;
    }

    /**
     * 获取排序越小越靠前
     *
     * @return position - 排序越小越靠前
     */
    public Float getPosition() {
        return position;
    }

    /**
     * 设置排序越小越靠前
     *
     * @param position 排序越小越靠前
     */
    public void setPosition(Float position) {
        this.position = position;
    }

    /**
     * 获取参数值
     *
     * @return param_value - 参数值
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 设置参数值
     *
     * @param paramValue 参数值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}