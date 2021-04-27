package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "third_platform_catalogs")
public class ThirdPlatformCatalogs extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "catalog_id")
    @ApiModelProperty(value="平台品目id")
    private String catalogId;

    @Column(name = "catalog_name")
    @ApiModelProperty(value="平台品目名称")
    private String catalogName;

    @Column(name = "tree_names")
    @ApiModelProperty(value="平台品目层级名称")
    private String treeNames;

    @Column(name = "platform_info_id")
    @ApiModelProperty(value="平台信息id")
    private Long platformInfoId;

    @Column(name = "platform_info_code")
    @ApiModelProperty(value="平台信息code")
    private String platformInfoCode;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getTreeNames() {
        return treeNames;
    }

    public void setTreeNames(String treeNames) {
        this.treeNames = treeNames;
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
