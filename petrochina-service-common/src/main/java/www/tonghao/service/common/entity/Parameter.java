package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@ApiModel(value="参数")
public class Parameter extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 名称
     */
    @ApiModelProperty(value="主参类别名称")
    private String name;

    /**
     * 平台参数id
     */
    @ApiModelProperty(value="主参数平台id")
    @Column(name = "stand_param_id")
    private String standParamId;

    /**
     * 是否关键参数
     */
    @ApiModelProperty(value="是否关键参数")
    @Column(name = "is_key")
    private String isKey;

    /**
     * 参数值范围
     */
    @ApiModelProperty(value="销售参数")
    @Column(name = "is_sellable")
    private String isSellable;

    /**
     * 1单选, 2复选, 3录入, 4自动填充
     */
    @ApiModelProperty(value="1单选, 2复选, 3录入, 4自动填充")
    private Integer ttype;

    /**
     * 排序越小越靠前
     */
    @ApiModelProperty(value="排序越小越靠前")
    private Float position;

    /**
     * 品目id
     */
    @ApiModelProperty(value="品目id")
    @Column(name = "catalogs_id")
    private Long catalogsId;
    
    @Transient
    @ApiModelProperty("参数排序")
    private Float itemPosition;
    
    @Transient
    @ApiModelProperty("参数名称")
    private String itemName;
    
    /**
     * 参数值
     */
    @Transient
    private List<ParameterItem> parameterItems;


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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取平台参数id
     *
     * @return stand_param_id - 平台参数id
     */
    public String getStandParamId() {
        return standParamId;
    }

    /**
     * 设置平台参数id
     *
     * @param standParamId 平台参数id
     */
    public void setStandParamId(String standParamId) {
        this.standParamId = standParamId;
    }

    /**
     * 获取是否关键参数
     *
     * @return is_key - 是否关键参数
     */
    public String getIsKey() {
        return isKey;
    }

    /**
     * 设置是否关键参数
     *
     * @param isKey 是否关键参数
     */
    public void setIsKey(String isKey) {
        this.isKey = isKey;
    }

    /**
     * 获取参数值范围
     *
     * @return is_sellable - 参数值范围
     */
    public String getIsSellable() {
        return isSellable;
    }

    /**
     * 设置参数值范围
     *
     * @param isSellable 参数值范围
     */
    public void setIsSellable(String isSellable) {
        this.isSellable = isSellable;
    }

    /**
     * 获取1单选, 2复选, 3录入, 4自动填充
     *
     * @return ttype - 1单选, 2复选, 3录入, 4自动填充
     */
    public Integer getTtype() {
        return ttype;
    }

    /**
     * 设置1单选, 2复选, 3录入, 4自动填充
     *
     * @param ttype 1单选, 2复选, 3录入, 4自动填充
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
     * 获取品目id
     *
     * @return catalogs_id - 品目id
     */
    public Long getCatalogsId() {
        return catalogsId;
    }

    /**
     * 设置品目id
     *
     * @param catalogsId 品目id
     */
    public void setCatalogsId(Long catalogsId) {
        this.catalogsId = catalogsId;
    }

	public Float getItemPosition() {
		return itemPosition;
	}

	public void setItemPosition(Float itemPosition) {
		this.itemPosition = itemPosition;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public List<ParameterItem> getParameterItems() {
		return parameterItems;
	}

	public void setParameterItems(List<ParameterItem> parameterItems) {
		this.parameterItems = parameterItems;
	}
    
}