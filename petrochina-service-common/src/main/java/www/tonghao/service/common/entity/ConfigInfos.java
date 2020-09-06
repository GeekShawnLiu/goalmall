package www.tonghao.service.common.entity;

import java.util.Date;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "config_infos")
public class ConfigInfos extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 企业名称
     */
    private String manufacturer;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 型号
     */
    private String model;

    /**
     * CPU核数
     */
    @Column(name = "cpu_hs")
    private Integer cpuHs;

    /**
     * 硬盘总容量
     */
    @Column(name = "yp_zrl")
    private Integer ypZrl;

    /**
     * 内存总容量
     */
    @Column(name = "nc_zrl")
    private Integer ncZrl;

    /**
     * CPU主频
     */
    @Column(name = "cpu_zp")
    private Float cpuZp;

    /**
     * 显存频率
     */
    @Column(name = "xc_pl")
    private String xcPl;

    /**
     * xc_wk 显存位宽
     */
    @Column(name = "xc_wk")
    private String xcWk;

    /**
     * 跟清单对应异常备注
     */
    private String remark;

    /**
     * 期数数字
     */
    @Column(name = "period_no")
    private Integer periodNo;

    /**
     * 对应的清单
     */
    @Column(name = "energy_saving_product_id")
    private Integer energySavingProductId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Transient
    private String configure;
    
    /**
     * 获取企业名称
     *
     * @return manufacturer - 企业名称
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 设置企业名称
     *
     * @param manufacturer 企业名称
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * 获取品牌
     *
     * @return brand - 品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置品牌
     *
     * @param brand 品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 获取型号
     *
     * @return model - 型号
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置型号
     *
     * @param model 型号
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取CPU核数
     *
     * @return cpu_hs - CPU核数
     */
    public Integer getCpuHs() {
        return cpuHs;
    }

    /**
     * 设置CPU核数
     *
     * @param cpuHs CPU核数
     */
    public void setCpuHs(Integer cpuHs) {
        this.cpuHs = cpuHs;
    }

    /**
     * 获取硬盘总容量
     *
     * @return yp_zrl - 硬盘总容量
     */
    public Integer getYpZrl() {
        return ypZrl;
    }

    /**
     * 设置硬盘总容量
     *
     * @param ypZrl 硬盘总容量
     */
    public void setYpZrl(Integer ypZrl) {
        this.ypZrl = ypZrl;
    }

    /**
     * 获取内存总容量
     *
     * @return nc_zrl - 内存总容量
     */
    public Integer getNcZrl() {
        return ncZrl;
    }

    /**
     * 设置内存总容量
     *
     * @param ncZrl 内存总容量
     */
    public void setNcZrl(Integer ncZrl) {
        this.ncZrl = ncZrl;
    }

    /**
     * 获取CPU主频
     *
     * @return cpu_zp - CPU主频
     */
    public Float getCpuZp() {
        return cpuZp;
    }

    /**
     * 设置CPU主频
     *
     * @param cpuZp CPU主频
     */
    public void setCpuZp(Float cpuZp) {
        this.cpuZp = cpuZp;
    }

    /**
     * 获取显存频率
     *
     * @return xc_pl - 显存频率
     */
    public String getXcPl() {
        return xcPl;
    }

    /**
     * 设置显存频率
     *
     * @param xcPl 显存频率
     */
    public void setXcPl(String xcPl) {
        this.xcPl = xcPl;
    }

    /**
     * 获取xc_wk
     *
     * @return xc_wk - xc_wk
     */
    public String getXcWk() {
        return xcWk;
    }

    /**
     * 设置xc_wk
     *
     * @param xcWk xc_wk
     */
    public void setXcWk(String xcWk) {
        this.xcWk = xcWk;
    }

    /**
     * 获取跟清单对应异常备注
     *
     * @return remark - 跟清单对应异常备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置跟清单对应异常备注
     *
     * @param remark 跟清单对应异常备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取期数数字
     *
     * @return period_no - 期数数字
     */
    public Integer getPeriodNo() {
        return periodNo;
    }

    /**
     * 设置期数数字
     *
     * @param periodNo 期数数字
     */
    public void setPeriodNo(Integer periodNo) {
        this.periodNo = periodNo;
    }

    /**
     * 获取对应的清单
     *
     * @return energy_saving_product_id - 对应的清单
     */
    public Integer getEnergySavingProductId() {
        return energySavingProductId;
    }

    /**
     * 设置对应的清单
     *
     * @param energySavingProductId 对应的清单
     */
    public void setEnergySavingProductId(Integer energySavingProductId) {
        this.energySavingProductId = energySavingProductId;
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

	public String getConfigure() {
		StringBuffer stringBuffer = new StringBuffer("");
		stringBuffer.append("CPU核数:");
		stringBuffer.append(this.getCpuHs());
		stringBuffer.append(",硬盘总容量：");
		stringBuffer.append(this.getYpZrl());
		stringBuffer.append(",内存总容量：");
		stringBuffer.append(this.getNcZrl());
		stringBuffer.append(",CPU主频：");
		stringBuffer.append(this.getCpuZp());
		stringBuffer.append(",显存频率：");
		stringBuffer.append(this.getXcPl());
		stringBuffer.append(",显存位宽：");
		stringBuffer.append(this.getXcWk());
		return stringBuffer.toString();
	}

	public void setConfigure(String configure) {
		this.configure = configure;
	}
    
    
}