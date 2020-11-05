package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;
import www.tonghao.service.common.base.BaseEntity;

@ApiModel(value = "平台对接协议")
@Table(name = "protocol")
public class Protocol extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "协议名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "协议编号")
    @Column(name = "code")
    private String code;

    @ApiModelProperty(value = "协议有效期开始时间")
    @Column(name = "start_time")
    private String startTime;

    @ApiModelProperty(value = "协议有效期结束时间")
    @Column(name = "end_time")
    private String endTime;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "created_at")
    private String createdAt;

    @ApiModelProperty(value = "修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    @ApiModelProperty(value = "是否删除 0 默认 1删除")
    @Column(name = "is_delete")
    private Integer isDelete;

    @ApiModelProperty(value = "状态 1待执行 2执行中 3已终止 4已中止")
    private Integer status;

    @ApiModelProperty(value = "对接平台id")
    @Column(name = "platform_info_id")
    private Long platformInfoId;

    @ApiModelProperty(value = "对接平台code")
    @Column(name = "platform_info_code")
    private String platformInfoCode;

    @Transient
    private List<Long> productIds;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPlatformInfoId() {
        return platformInfoId;
    }

    public void setPlatformInfoId(Long platformInfoId) {
        this.platformInfoId = platformInfoId;
    }

    public String getPlatformInfoCode() {
        return platformInfoCode;
    }

    public void setPlatformInfoCode(String platformInfoCode) {
        this.platformInfoCode = platformInfoCode;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}