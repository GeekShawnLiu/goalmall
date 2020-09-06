package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "system_setting")
public class SystemSetting extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "code")
	private String code;
	
	@Column(name = "set_value")
	private String setValue;
	
	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSetValue() {
		return setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
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
    
}
