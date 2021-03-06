package www.tonghao.service.common.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Suppliers;

/**  

* <p>Title: DeliveryWay</p>  

* <p>Description: 配送管理</p>  

* @author Yml  

* @date 2018年11月16日  

*/  
@Table(name = "delivery_way")
public class DeliveryWayEntity extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 配送要求
     */
    private String name;

    /**
     * 排序
     */
    private Float priority;

    /**
     * 状态 0：启用  1:停用
     */
    private String status;
    
    @Transient
    private List<Suppliers> supplierList;
    
    /**
     * 关联供应商id
     */
    @Transient
    private String supplierIds;
    
    /**
     * 关联供应商名称
     */
    @Transient
    private String supplierName;

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
     * 获取配送要求
     *
     * @return name - 配送要求
     */
    public String getName() {
        return name;
    }

    /**
     * 设置配送要求
     *
     * @param name 配送要求
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * 获取状态 0：启用  1:停用
     *
     * @return status - 状态 0：启用  1:停用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 0：启用  1:停用
     *
     * @param status 状态 0：启用  1:停用
     */
    public void setStatus(String status) {
        this.status = status;
    }

	public List<Suppliers> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<Suppliers> supplierList) {
		this.supplierList = supplierList;
	}

	public String getSupplierIds() {
		return supplierIds;
	}

	public void setSupplierIds(String supplierIds) {
		this.supplierIds = supplierIds;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
    
}