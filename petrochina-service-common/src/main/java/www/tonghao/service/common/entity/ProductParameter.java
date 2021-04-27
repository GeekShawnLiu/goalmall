package www.tonghao.service.common.entity;

import java.util.List;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "product_parameter")
public class ProductParameter extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "product_id")
    private Long productId;

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
     * 1单选, 2复选, 3录入, 4自动填充,5详细参数
     */
    private Integer ttype;

    /**
     * 参数值
     */
    @Column(name = "param_value")
    private String paramValue;

    /**
     * 平台品目id
     */
    @Column(name = "platform_catalogs_id")
    private Long platformCatalogsId;

    /**
     * 排序越小越靠前
     */
    @ApiModelProperty(value="排序越小越靠前")
    @Column(name = "position")
    private Float position;

    /**
     * 父级参数id
     */
    @Column(name = "parent_param_id")
    private Long parentParamId;

    /**
     * 父级参数名称
     */
    @Column(name = "parent_param_value")
    private String parentParamValue;

    /**
     * 参数项id
     */
    @Column(name = "param_id")
    private Long paramId;
    
    
    @Transient
    private Parameter parameter;
    
    /**
     * 商品参数items
     */
    @Transient
    private List<ParameterItem> parameterItems;

    @Transient
    private Boolean isKey;//是否主要参数
    
	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public List<ParameterItem> getParameterItems() {
		return parameterItems;
	}

	public void setParameterItems(List<ParameterItem> parameterItems) {
		this.parameterItems = parameterItems;
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
     * @return product_id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
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

    /**
     * 获取平台品目id
     *
     * @return platform_catalogs_id - 平台品目id
     */
    public Long getPlatformCatalogsId() {
        return platformCatalogsId;
    }

    /**
     * 设置平台品目id
     *
     * @param platformCatalogsId 平台品目id
     */
    public void setPlatformCatalogsId(Long platformCatalogsId) {
        this.platformCatalogsId = platformCatalogsId;
    }

	public Float getPosition() {
		return position;
	}

	public void setPosition(Float position) {
		this.position = position;
	}
    
	
	
	public void setKey(Boolean isKey) {
		this.isKey = isKey;
	}

	/**
	 * 是否主要参数
	 * @return
	 */
    public Boolean isKey(){
    	if(isKey!=null){
    		return isKey;
    	}
    	
    	Parameter p = getParameter();
    	if(p!=null&&p.getIsKey()!=null&&p.getIsKey().equals("true")){
    		return true;
    	}
    	return false;
    }

    public Long getParentParamId() {
        return parentParamId;
    }

    public void setParentParamId(Long parentParamId) {
        this.parentParamId = parentParamId;
    }

    public String getParentParamValue() {
        return parentParamValue;
    }

    public void setParentParamValue(String parentParamValue) {
        this.parentParamValue = parentParamValue;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }
}