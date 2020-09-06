package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "dictionary_data")
public class DictionaryData extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "编码")
	@Column(name = "code")
	private String code;

	@ApiModelProperty(value = "名称")
	@Column(name = "name")
	private String name;

	@ApiModelProperty(value = "描述")
	@Column(name = "description")
	private String description;

	@ApiModelProperty(value = "类型Id")
	@Column(name = "type")
	private Long type;

	@ApiModelProperty(value = "创建时间")
	@Column(name = "created_at")
	private String createdAt;

	@ApiModelProperty(value = "修改时间")
	@Column(name = "updated_at")
	private String updatedAt;

	@ApiModelProperty(value = "删除标识 0：未删除，1：删除")
	@Column(name = "is_delete")
	private Integer isDelete;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
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

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
