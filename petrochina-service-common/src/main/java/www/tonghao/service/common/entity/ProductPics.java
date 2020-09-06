package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import www.tonghao.service.common.base.BaseEntity;

@Table(name = "product_pics")
public class ProductPics extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 大图
     */
    private String large;

    /**
     * 中图
     */
    private String medium;

    /**
     * 原图
     */
    private String source;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 图片站点
     */
    private String site;

    /**
     * 标题
     */
    private String title;

    /**
     * 排序
     */
    private Float priority;

    /**
     * 图片名称
     */
    @Column(name = "file_name")
    private String fileName;

    @Transient
    private MultipartFile file;
    
    @Transient
    private Integer isDelete;
    
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
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取大图
     *
     * @return large - 大图
     */
    public String getLarge() {
        return large;
    }

    /**
     * 设置大图
     *
     * @param large 大图
     */
    public void setLarge(String large) {
        this.large = large;
    }

    /**
     * 获取中图
     *
     * @return medium - 中图
     */
    public String getMedium() {
        return medium;
    }

    /**
     * 设置中图
     *
     * @param medium 中图
     */
    public void setMedium(String medium) {
        this.medium = medium;
    }

    /**
     * 获取原图
     *
     * @return source - 原图
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置原图
     *
     * @param source 原图
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取缩略图
     *
     * @return thumbnail - 缩略图
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 设置缩略图
     *
     * @param thumbnail 缩略图
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 获取图片站点
     *
     * @return site - 图片站点
     */
    public String getSite() {
        return site;
    }

    /**
     * 设置图片站点
     *
     * @param site 图片站点
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
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
     * 获取图片名称
     *
     * @return file_name - 图片名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置图片名称
     *
     * @param fileName 图片名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	/**
	 * 判断是否为空
	 * 
	 * @return 是否为空
	 */
	public boolean isEmpty() {
		return (getFile() == null || getFile().isEmpty()) && (StringUtils.isEmpty(getSource()) || StringUtils.isEmpty(getLarge()) || StringUtils.isEmpty(getMedium()) || StringUtils.isEmpty(getThumbnail()));
	}

}