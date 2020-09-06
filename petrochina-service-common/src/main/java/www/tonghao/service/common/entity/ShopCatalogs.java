package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "shop_catalogs")
public class ShopCatalogs extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "shop_id")
	private Long shopId;
	
	@Column(name = "catalogs_id")
	private Long catalogsId;

	@Column(name = "created_at")
	private String createdAt;
    
    @Transient
    private String catalogsName;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getCatalogsId() {
		return catalogsId;
	}

	public void setCatalogsId(Long catalogsId) {
		this.catalogsId = catalogsId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCatalogsName() {
		return catalogsName;
	}

	public void setCatalogsName(String catalogsName) {
		this.catalogsName = catalogsName;
	}
}
