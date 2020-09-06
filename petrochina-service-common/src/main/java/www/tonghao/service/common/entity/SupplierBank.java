package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

/**
 * @Description:供应商银行信息
 * @date 2019年7月25日
 */
@Table(name = "supplier_bank")
public class SupplierBank extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	//供应商id
	@Column(name = "supplier_id")
	private Long supplierId;
	
	//开户行
	@Column(name = "opening_bank")
	private String openingBank;
	
	//开户行账号名
	@Column(name = "account_name")
	private String accountName;
	
	//开户行账号
	@Column(name = "account_num")
	private String accountNum;
	
	//银行所在地
	@Column(name = "areas_id")
	private Long areasId;
	
	//开户行支行名称
	@Column(name = "sub_branch_name")
	private String subBranchName;
	
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
    
    @Transient
    private String areaTreeIds;

    @Transient
    private String areaTreeNames;

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}


	public Long getAreasId() {
		return areasId;
	}

	public void setAreasId(Long areasId) {
		this.areasId = areasId;
	}

	public String getSubBranchName() {
		return subBranchName;
	}

	public void setSubBranchName(String subBranchName) {
		this.subBranchName = subBranchName;
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

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getAreaTreeIds() {
		return areaTreeIds;
	}

	public void setAreaTreeIds(String areaTreeIds) {
		this.areaTreeIds = areaTreeIds;
	}

	public String getAreaTreeNames() {
		return areaTreeNames;
	}

	public void setAreaTreeNames(String areaTreeNames) {
		this.areaTreeNames = areaTreeNames;
	}
}
