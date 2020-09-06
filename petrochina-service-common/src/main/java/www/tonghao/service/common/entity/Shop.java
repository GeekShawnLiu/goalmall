package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:店铺
 * @date 2019年6月19日
 */
public class Shop extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "supplier_id")
	private Long supplierId;
	
	@Column(name = "shop_name")
	private String shopName;
	
	private String log;
	
	//客服邮箱
	@Column(name = "customer_service_email")
	private String customerServiceEmail;
	
	//客服联系电话
	@Column(name = "customer_service_phone")
	private String customerServicePhone;
	
	@Column(name = "created_at")
	private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
    
    //经营范围
    @Transient
    private Long[] catalogsId;
    
    @Transient
    private String catalogsName;

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getCustomerServiceEmail() {
		return customerServiceEmail;
	}

	public void setCustomerServiceEmail(String customerServiceEmail) {
		this.customerServiceEmail = customerServiceEmail;
	}

	public String getCustomerServicePhone() {
		return customerServicePhone;
	}

	public void setCustomerServicePhone(String customerServicePhone) {
		this.customerServicePhone = customerServicePhone;
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

	public Long[] getCatalogsId() {
		return catalogsId;
	}

	public void setCatalogsId(Long[] catalogsId) {
		this.catalogsId = catalogsId;
	}

	public String getCatalogsName() {
		return catalogsName;
	}

	public void setCatalogsName(String catalogsName) {
		this.catalogsName = catalogsName;
	}
	
	
}
