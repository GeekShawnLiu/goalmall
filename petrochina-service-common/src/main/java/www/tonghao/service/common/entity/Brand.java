package www.tonghao.service.common.entity;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;

import www.tonghao.common.utils.PinyinUtil;
import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="品牌")
public class Brand extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value="品牌名称")
    private String name;

    /**
     * logo
     */
    @ApiModelProperty(value="logo地址")
    private String logo;

    /**
     * 网址
     */
    @ApiModelProperty(value="网址")
    private String url;

    /**
     * 首字母拼音
     */
    @ApiModelProperty(value="首字母拼音")
    private String pinyin;

    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Float priority;

    /**
     * 删除标识
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 默认0：启用，1:停用
     */
    @ApiModelProperty(value="默认0：启用，1:停用")
    @Column(name = "is_frozen")
    private Integer isFrozen;

    /**
     * 简称
     */
    @ApiModelProperty(value="简称")
    @Column(name = "short_name")
    private String shortName;
    
    /**
     * 品牌映射
     */
    @ApiModelProperty(value="品牌映射")
    @Column(name = "mapping_name")
    private String mappingName;

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
     * 获取品牌名称
     *
     * @return name - 品牌名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置品牌名称
     *
     * @param name 品牌名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取logo
     *
     * @return logo - logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置logo
     *
     * @param logo logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 获取网址
     *
     * @return url - 网址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置网址
     *
     * @param url 网址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取首字母拼音
     *
     * @return pinyin - 首字母拼音
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 设置首字母拼音
     *
     * @param pinyin 首字母拼音
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * 获取排序
     *
     * @return priority - 排序
     */
    public Float getPriority() {
        return priority;
    }

    /**
     * 设置排序
     *
     * @param priority 排序
     */
    public void setPriority(Float priority) {
        this.priority = priority;
    }

    /**
     * 获取删除标识
     *
     * @return is_delete - 删除标识
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置删除标识
     *
     * @param isDelete 删除标识
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取默认0：启用，1:停用
     *
     * @return is_frozen - 默认0：启用，1:停用
     */
    public Integer getIsFrozen() {
        return isFrozen;
    }

    /**
     * 设置默认0：启用，1:停用
     *
     * @param isFrozen 默认0：启用，1:停用
     */
    public void setIsFrozen(Integer isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * 获取简称
     *
     * @return short_name - 简称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置简称
     *
     * @param shortName 简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

	public String getMappingName() {
		return mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}
    
	/**
	 * 得到首字母大写拼音
	 * @return
	 */
    public String getFirstUpperCasePinyin(){
    	if(StringUtils.isNotEmpty(getPinyin())){
    		return getPinyin();
    	}else{
    		String py = PinyinUtil.firstUpperCase(getName());
    		if(py.matches("^[a-zA-Z]+$")){
    			return py.toUpperCase();
    		}
    	}
    	return "其他";
    }
}