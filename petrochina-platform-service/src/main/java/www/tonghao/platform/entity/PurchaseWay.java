package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@ApiModel(value="采购方式")
@Table(name = "purchase_way")
public class PurchaseWay extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    
    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 修改时间
     */
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 采购方式代码
     */
    @ApiModelProperty(value="采购方式代码")
    private String code;

    /**
     * 默认0：启用，1:停用
     */
    @ApiModelProperty(value="是否启用")
    @Column(name = "is_frozen")
    private Integer isFrozen;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Float priority;

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
     * 获取采购方式代码
     *
     * @return code - 采购方式代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置采购方式代码
     *
     * @param code 采购方式代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取默认0：启用，1:停用
     *
     * @return is_frozen - 默认0：启用，1:停用
     */
    public Integer getIsFrozen() {
        return isFrozen;
    }

    /**
     * 设置默认0：启用，1:停用
     *
     * @param isFrozen 默认0：启用，1:停用
     */
    public void setIsFrozen(Integer isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * 获取排序
     *
     * @return priority - 排序
     */
    public Float getPriority() {
        return priority;
    }

    /**
     * 设置排序
     *
     * @param priority 排序
     */
    public void setPriority(Float priority) {
        this.priority = priority;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
}