package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@Table(name="shop_update_catalogs")
public class ShopUpdateCatalogs extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "供应商修改记录id")
	@Column(name = "supplier_update_id")
	private Long supplierUpdateId;
	
	@ApiModelProperty(value = "品目id")
	@Column(name = "catalogs_id")
	private Long catalogsId;
	
	@ApiModelProperty(value = "品目名称")
	@Transient
	private String catalogsName;

	public Long getSupplierUpdateId() {
		return supplierUpdateId;
	}

	public void setSupplierUpdateId(Long supplierUpdateId) {
		this.supplierUpdateId = supplierUpdateId;
	}

	public Long getCatalogsId() {
		return catalogsId;
	}

	public void setCatalogsId(Long catalogsId) {
		this.catalogsId = catalogsId;
	}

	public String getCatalogsName() {
		return catalogsName;
	}

	public void setCatalogsName(String catalogsName) {
		this.catalogsName = catalogsName;
	}
	
}
