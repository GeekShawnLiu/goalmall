package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@ApiModel(value = "对接平台信息")
@Table(name = "platform_info")
public class PlatformInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "平台名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "平台code")
    @Column(name = "code")
    private String code;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "created_at")
    private String createdAt;

    @ApiModelProperty(value = "修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    @ApiModelProperty(value = "是否删除 0 默认 ，1删除")
    @Column(name = "is_delete")
    private Integer isDelete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
