package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "dictionary_type")
public class DictionaryType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "编码")
	@Column(name = "code")
	private String code;

	@ApiModelProperty(value = "名称")
	@Column(name = "name")
	private String name;
	
	@Transient
	private Long parentId;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
