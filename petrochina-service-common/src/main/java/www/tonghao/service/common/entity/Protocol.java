package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;



@ApiModel(value="协议")
public class Protocol extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 协议名称
     */
    @ApiModelProperty(value="协议名称")
    private String name;

    /**
     * 协议编号
     */
    @ApiModelProperty(value="协议编号")
    private String code;

    /**
     * 协议类型 1 超时直购，2超时竞价，3批量采购，4定点采购
     */
    @ApiModelProperty(value="协议类型 1 超时直购，2超时竞价，3批量采购，4定点采购")
    private Integer types;

    /**
     * 销售类型 1 厂家直销，2代理商供货
     */
    @ApiModelProperty(value="销售类型 1 厂家直销，2代理商供货")
    private Integer sales;

    /**
     * 交易限制，1直购，2竞价，3所有
     */
    @ApiModelProperty(value="交易限制，1直购，2竞价，3所有")
    private Integer stint;

    /**
     * 协议有效期开始时间
     */
    @ApiModelProperty(value="协议有效期开始时间")
    @Column(name = "start_time")
    private String startTime;

    /**
     * 协议有效期结束时间
     */
    @ApiModelProperty(value="协议有效期结束时间")
    @Column(name = "end_time")
    private String endTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 是否删除 0 默认 ，1删除
     */
    @ApiModelProperty(value="是否删除 0 默认 ，1删除")
    @Column(name = "is_delete")
    private Integer isDelete;
    
    @Transient
    @ApiModelProperty(value="供应商协议")
    private List<SupplierProtocol> supplierProtocols;

    
    @Transient
    @ApiModelProperty(value="供应商协议Id")
    private Long supplierProtocolId;
    
    @Transient
    @ApiModelProperty(value="供应商协议是否分配")
    private Integer isAssign;
    
    
    /**
     * 状态 1暂存，2执行,3 终止，4中止
     */
    @ApiModelProperty(value="状态 1暂存，2执行,3 终止，4中止")
    private Integer status;


    /**
     * 获取协议名称
     *
     * @return name - 协议名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置协议名称
     *
     * @param name 协议名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取协议编号
     *
     * @return code - 协议编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置协议编号
     *
     * @param code 协议编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取协议类型 1 超时直购，2超时竞价，3批量采购，4定点采购
     *
     * @return tyeps - 协议类型 1 超时直购，2超时竞价，3批量采购，4定点采购
     */
    public Integer getTypes() {
        return types;
    }

    /**
     * 设置协议类型 1 超时直购，2超时竞价，3批量采购，4定点采购
     *
     * @param tyeps 协议类型 1 超时直购，2超时竞价，3批量采购，4定点采购
     */
    public void setTypes(Integer types) {
        this.types = types;
    }

    /**
     * 获取销售类型 1 厂家直销，2代理商供货
     *
     * @return sales - 销售类型 1 厂家直销，2代理商供货
     */
    public Integer getSales() {
        return sales;
    }

    /**
     * 设置销售类型 1 厂家直销，2代理商供货
     *
     * @param sales 销售类型 1 厂家直销，2代理商供货
     */
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    /**
     * 获取交易限制，1直购，2竞价，3所有
     *
     * @return stint - 交易限制，1直购，2竞价，3所有
     */
    public Integer getStint() {
        return stint;
    }

    /**
     * 设置交易限制，1直购，2竞价，3所有
     *
     * @param stint 交易限制，1直购，2竞价，3所有
     */
    public void setStint(Integer stint) {
        this.stint = stint;
    }

    /**
     * 获取协议有效期开始时间
     *
     * @return start_time - 协议有效期开始时间
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 设置协议有效期开始时间
     *
     * @param startTime 协议有效期开始时间
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取协议有效期结束时间
     *
     * @return end_time - 协议有效期结束时间
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置协议有效期结束时间
     *
     * @param endTime 协议有效期结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取修改时间
     *
     * @return updated_at - 修改时间
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置修改时间
     *
     * @param updatedAt 修改时间
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取是否删除 0 默认 ，1删除
     *
     * @return is_delete - 是否删除 0 默认 ，1删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除 0 默认 ，1删除
     *
     * @param isDelete 是否删除 0 默认 ，1删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取状态 1暂存，2执行,3 终止，4中止
     *
     * @return status - 状态 1暂存，2执行,3 终止，4中止
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 1暂存，2执行,3 终止，4中止
     *
     * @param status 状态 1暂存，2执行,3 终止，4中止
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

	public List<SupplierProtocol> getSupplierProtocols() {
		return supplierProtocols;
	}

	public void setSupplierProtocols(List<SupplierProtocol> supplierProtocols) {
		this.supplierProtocols = supplierProtocols;
	}

	public Long getSupplierProtocolId() {
		return supplierProtocolId;
	}

	public void setSupplierProtocolId(Long supplierProtocolId) {
		this.supplierProtocolId = supplierProtocolId;
	}

	public Integer getIsAssign() {
		return isAssign;
	}

	public void setIsAssign(Integer isAssign) {
		this.isAssign = isAssign;
	}

    
}