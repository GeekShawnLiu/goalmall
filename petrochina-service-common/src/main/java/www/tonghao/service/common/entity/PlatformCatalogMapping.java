package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@ApiModel(value = "平台品目信息映射")
@Table(name = "platform_catalog_mapping")
public class PlatformCatalogMapping extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品目id")
    @Column(name = "catalog_id")
    private Long catalogId;

    @ApiModelProperty(value = "平台品目id")
    @Column(name = "platform_catalog_id")
    private String platformCatalogId;

    @ApiModelProperty(value = "平台品目名称")
    @Column(name = "platform_catalog_name")
    private String platformCatalogName;

    @ApiModelProperty(value = "平台品目层级名称")
    @Column(name = "platform_catalog_tree_name")
    private String platformCatalogTreeName;

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

    public String getPlatformCatalogId() {
        return platformCatalogId;
    }

    public void setPlatformCatalogId(String platformCatalogId) {
        this.platformCatalogId = platformCatalogId;
    }

    public String getPlatformCatalogName() {
        return platformCatalogName;
    }

    public void setPlatformCatalogName(String platformCatalogName) {
        this.platformCatalogName = platformCatalogName;
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
}
