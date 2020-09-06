package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

/**
 * 
 * Description: 京东售后运货单
 * 
 * @version 2019年7月18日
 * @since JDK1.8
 */
@Table(name="jd_after_sale_waybill")
public class JdAfsWaybill extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * jd售后id
	 */
	@Column(name="jd_afs_id")
	private Long jdAfsId;
	
	/**
	 * 服务单号
	 * 必填
	 */
	@Column(name = "afs_service_id")
    private String afsServiceId;

	/**
	 * 运费
	 * 必填
	 */
	@Column(name = "freight_money")
	private BigDecimal freightMoney;
	
	/**
	 * 发运公司：圆通快递、申通快递、韵达快递、中通快递、宅急送、EMS、顺丰快递
	 * 必填
	 */
	@Column(name = "express_company")
	private String expressCompany;
	
	/**
	 * 发货日期，格式为yyyy-MM-dd HH:mm:ss
	 * 必填
	 */
	@Column(name = "deliver_date")
	private String deliverDate;
	
	/**
	 * 货运单号，最大50字符
	 * 必填
	 */
	@Column(name = "express_code")
	private String expressCode;
	
	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 删除标识 0：未删除，1：删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "status")
    @ApiModelProperty(value="提交状态 1成功 2失败")
    private Integer status;

	public Long getJdAfsId() {
		return jdAfsId;
	}

	public void setJdAfsId(Long jdAfsId) {
		this.jdAfsId = jdAfsId;
	}

	public String getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(String afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public BigDecimal getFreightMoney() {
		return freightMoney;
	}

	public void setFreightMoney(BigDecimal freightMoney) {
		this.freightMoney = freightMoney;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
