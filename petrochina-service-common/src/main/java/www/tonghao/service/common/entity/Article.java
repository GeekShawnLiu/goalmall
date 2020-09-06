package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.PlatformCatalogs;

@ApiModel("文章")
public class Article extends BaseEntity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("创建日期")
    @Column(name = "created_at")
    private String createdAt;

	@ApiModelProperty("修改日期")
    @Column(name = "updated_at")
    private String updatedAt;
    
    /**
     * 发布日期
     */
    @Column(name = "publish_at")
    @ApiModelProperty(value="发布日期")
    private String publishAt;

    /**
     * 作者
     */
    @ApiModelProperty("作者")
    private String author;

    /**
     * 状态 0:暂存 1：提交 2：发布 3：退回  4：撤回
     */
    @Column(name = "status")
    @ApiModelProperty(value="状态 0:暂存 1：提交 2：发布 3：退回  4：撤回")
    private String status;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String title;

    /**
     * 摘要
     */
    @ApiModelProperty("摘要")
    private String abstracts;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Float priority;

    /**
     * 栏目
     */
    @Column(name = "article_type_id")
    private Long articleTypeId;

    /**
     * 作者id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 正文
     */
    @ApiModelProperty("正文")
    private String content;
    
    @Transient
    private ArticleType articleType;
    
    /**
     * 供应商id
     */
    @Column(name = "supplier_id")
    private Long supplierId;
    
    /**
     * 供应商名称
     */
    @Column(name = "supplier_name")
    private String supplierName;
    
    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    @Column(name = "org_name")
    private String orgName;
    
    /**
     * 机构id
     */
    @Column(name = "org_id")
    private Long orgId;
    
    /**
     * 品目id
     */
    @Column(name = "catalog_id")
    private Integer catalogId;
    
    /**
     * 成交金额
     */
    @Column(name = "turnover")
    private BigDecimal turnover;
    
    @Transient
    private String price;
    
    /*
     * 定点公告类型  1车辆维保  2 车辆保险 3 法律服务
     */
    @ApiModelProperty("定点公告类型  1车辆维保  2 车辆保险 3 法律服务 4 竞价公告")
    @Column(name="fix_type")
    private Integer fixType;
    
   
	/**
     * 点击量
     */
    @ApiModelProperty("点击量")
    private int hits; 
    
    @ApiModelProperty("业务类型  1 定点 ; 2 竞价 ; 3 超市直购 4 批量集采")
    @Column(name="business_type")
    private Integer businessType;
    
    @ApiModelProperty("业务状态  1 竞价 2 成交  3 中止  4 失败")
    @Column(name="business_status")
    private Integer businessStatus;
    
    @ApiModelProperty("业务ID")
    @Column(name="business_id")
    private Long businessId;
    
    @ApiModelProperty("展示图片路径pc端")
    @Column(name="img_path_pc")
    private String imgPathPc;
    
    @ApiModelProperty("展示图片路径h5")
    @Column(name="img_path_h5")
    private String imgPathH5;
    
    @ApiModelProperty("展示图片路径小程序")
    @Column(name="img_path_wx")
    private String imgPathWx;
    
    @Transient
    private List<ArticleFile> articleFile;
    @Transient
    private Organization organization;
    @Transient
    private PlatformCatalogs platformCatalogs;
    
    public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getFixType() {
		return fixType;
	}

	public void setFixType(Integer fixType) {
		this.fixType = fixType;
	}

    public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(Integer businessStatus) {
		this.businessStatus = businessStatus;
	}
	
	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

    public PlatformCatalogs getPlatformCatalogs() {
		return platformCatalogs;
	}

	public void setPlatformCatalogs(PlatformCatalogs platformCatalogs) {
		this.platformCatalogs = platformCatalogs;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

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
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取名称
     *
     * @return title - 名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置名称
     *
     * @param title 名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取摘要
     *
     * @return abstracts - 摘要
     */
    public String getAbstracts() {
        return abstracts;
    }

    /**
     * 设置摘要
     *
     * @param abstracts 摘要
     */
    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
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
     * 获取栏目
     *
     * @return article_type_id - 栏目
     */
    public Long getArticleTypeId() {
        return articleTypeId;
    }

    /**
     * 设置栏目
     *
     * @param articleTypeId 栏目
     */
    public void setArticleTypeId(Long articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

    /**
     * 获取作者id
     *
     * @return user_id - 作者id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置作者id
     *
     * @param userId 作者id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取正文
     *
     * @return content - 正文
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置正文
     *
     * @param content 正文
     */
    public void setContent(String content) {
        this.content = content;
    }

	public String getPublishAt() {
		return publishAt;
	}

	public void setPublishAt(String publishAt) {
		this.publishAt = publishAt;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	public BigDecimal getTurnover() {
		return turnover;
	}

	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}
	
	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public List<ArticleFile> getArticleFile() {
		return articleFile;
	}

	public void setArticleFile(List<ArticleFile> articleFile) {
		this.articleFile = articleFile;
	}

	/**
	 * 获取发布日期年月
	 * @return
	 */
	@Transient
	public String getPublishAtYearMonth(){
		String p = getPublishAt();
		if(StringUtils.isNotEmpty(p)&&p.length()>=7){
			return p.substring(0, 7);
		}
		return null;
	}
	
	/**
	 * 获取发布日期日
	 * @return
	 */
	@Transient
	public String getPublishAtDay(){
		String p = getPublishAt();
		if(StringUtils.isNotEmpty(p)&&p.length()>=10){
			return p.substring(8, 10);
		}
		return null;
	}

	public String getImgPathPc() {
		return imgPathPc;
	}

	public void setImgPathPc(String imgPathPc) {
		this.imgPathPc = imgPathPc;
	}

	public String getImgPathH5() {
		return imgPathH5;
	}

	public void setImgPathH5(String imgPathH5) {
		this.imgPathH5 = imgPathH5;
	}

	public String getImgPathWx() {
		return imgPathWx;
	}

	public void setImgPathWx(String imgPathWx) {
		this.imgPathWx = imgPathWx;
	}
	
}