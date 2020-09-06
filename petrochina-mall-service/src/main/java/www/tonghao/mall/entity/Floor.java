package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.common.warpper.TreeNode;
import www.tonghao.service.common.base.BaseEntity;

@ApiModel(value="楼层")
public class Floor extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="创建时间")
	@Column(name = "created_at")
    private String createdAt;

	@ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 楼层名
     */
    @ApiModelProperty(value="楼层名")
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 字体颜色
     */
	@ApiModelProperty(value="字体颜色")
    @Column(name = "font_color")
    private String fontColor;

    /**
     * 图标url
     */
    @Column(name = "icon_url")
    @ApiModelProperty(value="图标url")
    private String iconUrl;
    
    /**
     * 楼层品目
     */
    @Column(name = "floor_catalog")
    @ApiModelProperty(value="楼层品目")
    private String floorCatalog;
    
    /**
     * 品牌列表
     */
    @Transient
    @ApiModelProperty(value="品牌列表")
    private List<FloorBrand> floorBrands;
    
    /**
     * 品目列表
     */
    @Transient
    @ApiModelProperty(value="品目列表")
    private List<FloorPlatformCatalog> floorPlatformCatalogs;
    
    @Transient
    @ApiModelProperty(value="平台品目树")
    private List<TreeNode> platformCatalogs;
    
    /**
     * @return created_at
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return updated_at
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取楼层名
     *
     * @return name - 楼层名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置楼层名
     *
     * @param name 楼层名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取字体颜色
     *
     * @return font_color - 字体颜色
     */
    public String getFontColor() {
        return fontColor;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     */
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    /**
     * 获取图标url
     *
     * @return icon_url - 图标url
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * 设置图标url
     *
     * @param iconUrl 图标url
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    
    /**
     * 获得楼层品目
     * @return floorCatalog -楼层品目
     */
    public String getFloorCatalog() {
        return floorCatalog;
    }

    /**
     * 设置楼层品目
     * @param floorCatalog -楼层品目
     */
    public void setFloorCatalog(String floorCatalog) {
        this.floorCatalog = floorCatalog;
    }
    
    /**
     * 获得楼层品牌
     * @return  floorBrands
     */
    public List<FloorBrand> getFloorBrands() {
        return floorBrands;
    }

    /**
     * 设置楼层品牌
     * @param floorBrands 
     */
    public void setFloorBrands(List<FloorBrand> floorBrands) {
        this.floorBrands = floorBrands;
    }

    /**
     * 获得楼层品目
     * @return  floorPlatformCatalogs
     */
    public List<FloorPlatformCatalog> getFloorPlatformCatalogs() {
        return floorPlatformCatalogs;
    }

    /**
     * 设置楼层品目
     * @param floorPlatformCatalogs 
     */
    public void setFloorPlatformCatalogs(List<FloorPlatformCatalog> floorPlatformCatalogs) {
        this.floorPlatformCatalogs = floorPlatformCatalogs;
    }
    
    /**
     * 获得平台品目树
     * @return platformCatalogs
     */
    public List<TreeNode> getPlatformCatalogs() {
        return platformCatalogs;
    }

    /**
     * 设置平台品目树
     * @param platformCatalogs 
     */
    public void setPlatformCatalogs(List<TreeNode> list) {
        this.platformCatalogs = list;
    }

}