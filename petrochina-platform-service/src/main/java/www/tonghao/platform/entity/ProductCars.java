package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


/**  
* <p>Title: ProductCars</p>  
* <p>Description: 车辆价格信息</p>  
* @author YML  
* @date 2019年2月15日  
*/ 
@Table(name = "product_cars")
public class ProductCars  extends BaseEntity{

    /** 
	 * 
	 */  
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 地区id
     */
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 地区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    @ApiModelProperty("市场价")
    private BigDecimal marketPrice;

    /**
     * 协议价
     */
    @ApiModelProperty("协议价")
    private BigDecimal price;

    /**
     * 折扣率
     */
    @ApiModelProperty("折扣率")
    private BigDecimal rate;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 车辆类型    1：新能源车辆 2：非新能源车辆
     */
    @Column(name = "car_type")
    @ApiModelProperty("车辆类型    1：新能源车辆 2：非新能源车辆")
    private Integer carType;

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
     * 获取地区id
     *
     * @return area_id - 地区id
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * 设置地区id
     *
     * @param areaId 地区id
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取地区名称
     *
     * @return area_name - 地区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置地区名称
     *
     * @param areaName 地区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取市场价
     *
     * @return market_price - 市场价
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * 设置市场价
     *
     * @param marketPrice 市场价
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取协议价
     *
     * @return price - 协议价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置协议价
     *
     * @param price 协议价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取折扣率
     *
     * @return rate - 折扣率
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 设置折扣率
     *
     * @param rate 折扣率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取车辆类型    1：新能源车辆 2：非新能源车辆
     *
     * @return car_type - 车辆类型    1：新能源车辆 2：非新能源车辆
     */
    public Integer getCarType() {
        return carType;
    }

    /**
     * 设置车辆类型    1：新能源车辆 2：非新能源车辆
     *
     * @param carType 车辆类型    1：新能源车辆 2：非新能源车辆
     */
    public void setCarType(Integer carType) {
        this.carType = carType;
    }
}