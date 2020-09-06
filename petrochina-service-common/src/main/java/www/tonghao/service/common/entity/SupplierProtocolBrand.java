package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "supplier_protocol_brand")
public class SupplierProtocolBrand extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 品牌名称
     */
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 品牌id
     */
    @Column(name = "brand_id")
    private Long brandId;

    /**
     * 供应商id
     */
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 协议供应商关联id
     */
    @Column(name = "supplier_protocol_id")
    private Long supplierProtocolId;

    /**
     * 协议id
     */
    @Column(name = "protocol_id")
    private Long protocolId;


    /**
     * 获取品牌名称
     *
     * @return brand_name - 品牌名称
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置品牌名称
     *
     * @param brandName 品牌名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取品牌id
     *
     * @return brand_id - 品牌id
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * 设置品牌id
     *
     * @param brandId 品牌id
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取供应商id
     *
     * @return supplier_id - 供应商id
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置供应商id
     *
     * @param supplierId 供应商id
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取协议供应商关联id
     *
     * @return supplier_protocol_id - 协议供应商关联id
     */
    public Long getSupplierProtocolId() {
        return supplierProtocolId;
    }

    /**
     * 设置协议供应商关联id
     *
     * @param supplierProtocolId 协议供应商关联id
     */
    public void setSupplierProtocolId(Long supplierProtocolId) {
        this.supplierProtocolId = supplierProtocolId;
    }

    /**
     * 获取协议id
     *
     * @return protocol_id - 协议id
     */
    public Long getProtocolId() {
        return protocolId;
    }

    /**
     * 设置协议id
     *
     * @param protocolId 协议id
     */
    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }
}