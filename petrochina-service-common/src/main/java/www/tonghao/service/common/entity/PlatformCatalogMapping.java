package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@ApiModel(value = "对接第三方品目信息映射")
@Table(name = "platform_catalog_mapping")
public class PlatformCatalogMapping extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品目id")
    @Column(name = "catalog_id")
    private Long catalogId;

    @ApiModelProperty(value = "平台品目id")
    @Column(name = "third_platform_catalog_id")
    private Long thirdPlatformCatalogId;

    @ApiModelProperty(value = "对接平台id")
    @Column(name = "platform_info_id")
    private Long platformInfoId;

    @ApiModelProperty(value = "对接平台code")
    @Column(name = "platform_info_code")
    private String platformInfoCode;

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
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

    public Long getThirdPlatformCatalogId() {
        return thirdPlatformCatalogId;
    }

    public void setThirdPlatformCatalogId(Long thirdPlatformCatalogId) {
        this.thirdPlatformCatalogId = thirdPlatformCatalogId;
    }
}
