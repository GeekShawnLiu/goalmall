package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;


@ApiModel(value="节能清单")
@Table(name = "energy_saving_products")
public class EnergySavingProducts extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * json解析过来的，外层包围的id
     */
    @Column(name = "str_id")
    private String strId;

    /**
     * cell的id
     */
    @Column(name = "cell_id")
    private String cellId;

    /**
     * cell的id的截取部分
     */
    @Column(name = "cell_id_right")
    private String cellIdRight;

    /**
     * 型号
     */
    private String model;

    /**
     * 证书号
     */
    @ApiModelProperty(value="证书号")
    @Column(name = "cert_no")
    private String certNo;

    /**
     * 截止日期
     */
    private Integer deadline;

    /**
     * 品牌
     */
    @ApiModelProperty(value="品牌")
    private String brand;

    /**
     * 品目
     */
    @ApiModelProperty(value="官方品目名称")
    @Column(name = "item_name")
    private String itemName;
    
    /**
     * 品目编号
     */
    @ApiModelProperty(value="品目编号")
    @Column(name = "item_code")
    private String itemCode;
    
    /**
     * 制造商
     */
    @ApiModelProperty(value="制造商")
    private String manufacturer;

    /**
     * 期数
     */
    @ApiModelProperty(value="期数")
    private String period;

    /**
     * 类别
     */
    @Column(name = "function_type")
    private String functionType;

    /**
     * 注释
     */
    private String remark;

    @Column(name = "period_no")
    private Integer periodNo;
    
    @Column(name = "model_str")
    private String modelStr;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 对应财政品目id
     */
    @ApiModelProperty(value="对应财政品目id")
    @Column(name = "category_id")
    private String categoryId;
    
    @Transient
    private ConfigInfos configInfos;
    
    /**
     * 获取json解析过来的，外层包围的id
     *
     * @return str_id - json解析过来的，外层包围的id
     */
    public String getStrId() {
        return strId;
    }

    /**
     * 设置json解析过来的，外层包围的id
     *
     * @param strId json解析过来的，外层包围的id
     */
    public void setStrId(String strId) {
        this.strId = strId;
    }

    /**
     * 获取cell的id
     *
     * @return cell_id - cell的id
     */
    public String getCellId() {
        return cellId;
    }

    /**
     * 设置cell的id
     *
     * @param cellId cell的id
     */
    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    /**
     * 获取cell的id的截取部分
     *
     * @return cell_id_right - cell的id的截取部分
     */
    public String getCellIdRight() {
        return cellIdRight;
    }

    /**
     * 设置cell的id的截取部分
     *
     * @param cellIdRight cell的id的截取部分
     */
    public void setCellIdRight(String cellIdRight) {
        this.cellIdRight = cellIdRight;
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
     * 获取证书号
     *
     * @return certificate_no - 证书号
     */
    public String getCertNo() {
        return certNo;
    }

    /**
     * 设置证书号
     *
     * @param certificateNo 证书号
     */
    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    /**
     * 获取截止日期
     *
     * @return deadline - 截止日期
     */
    public Integer getDeadline() {
        return deadline;
    }

    /**
     * 设置截止日期
     *
     * @param deadline 截止日期
     */
    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
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
     * 获取品目
     *
     * @return item - 品目
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置品目
     *
     * @param item 品目
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取制造商
     *
     * @return manufacturer - 制造商
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 设置制造商
     *
     * @param manufacturer 制造商
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * 获取期数
     *
     * @return period - 期数
     */
    public String getPeriod() {
        return period;
    }

    /**
     * 设置期数
     *
     * @param period 期数
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 获取类别
     *
     * @return function_type - 类别
     */
    public String getFunctionType() {
        return functionType;
    }

    /**
     * 设置类别
     *
     * @param functionType 类别
     */
    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    /**
     * 获取注释
     *
     * @return remark - 注释
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置注释
     *
     * @param remark 注释
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

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
     * @return period_no
     */
    public Integer getPeriodNo() {
        return periodNo;
    }

    /**
     * @param periodNo
     */
    public void setPeriodNo(Integer periodNo) {
        this.periodNo = periodNo;
    }

    /**
     * @return model_str
     */
    public String getModelStr() {
        return modelStr;
    }

    /**
     * @param modelStr
     */
    public void setModelStr(String modelStr) {
        this.modelStr = modelStr;
    }

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public ConfigInfos getConfigInfos() {
		return configInfos;
	}

	public void setConfigInfos(ConfigInfos configInfos) {
		
		this.configInfos = configInfos;
	}
    
}