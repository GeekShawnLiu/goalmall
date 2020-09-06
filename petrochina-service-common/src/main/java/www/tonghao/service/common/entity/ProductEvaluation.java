package www.tonghao.service.common.entity;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;

/**
 * 
 * Description: 商品评价表
 * 
 * @version 2019年6月26日
 * @since JDK1.8
 */
@Table(name="product_evaluation")
public class ProductEvaluation extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
    
    @ApiModelProperty(value="删除标识 默认0：未删除，1：删除")
    @Column(name = "is_delete")
    private Integer isDelete;
    
    @ApiModelProperty(value="商品id")
    @Column(name = "product_id")
    private Long productId;
    
    @ApiModelProperty(value="评价人id")
    @Column(name = "user_id")
    private Long userId;
    
    @ApiModelProperty(value="评价等级 1好评  2中评  3差评")
    @Column(name = "level")
    private Integer level;
    
    @ApiModelProperty(value="评价内容")
    @Column(name = "content")
    private String content;
    
    @ApiModelProperty(value="是否匿名  0匿名   1公开")
    @Column(name = "is_anonymous")
    private Integer isAnonymous;
    
    @ApiModelProperty(value="订单明细id")
    @Column(name = "item_id")
    private Long itemId;
    
    @Transient
    private Users user; //评价用户信息
    
    @Transient
    List<ProductEvaluationFile> fileList;
    
    @Transient
    private String productRate; //好评率
    
    @Transient
    private Integer highReviewNum; //好评数
    
    @Transient
    private Integer mediumReviewNum; //中评数
    
    @Transient
    private Integer badReviewNum; //差评数
    
    @Transient
    private Integer reviewTotalNum; //差评数

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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Integer isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public List<ProductEvaluationFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ProductEvaluationFile> fileList) {
		this.fileList = fileList;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getProductRate() {
		return productRate;
	}

	public void setProductRate(String productRate) {
		this.productRate = productRate;
	}

	public Integer getHighReviewNum() {
		return highReviewNum;
	}

	public void setHighReviewNum(Integer highReviewNum) {
		this.highReviewNum = highReviewNum;
	}

	public Integer getMediumReviewNum() {
		return mediumReviewNum;
	}

	public void setMediumReviewNum(Integer mediumReviewNum) {
		this.mediumReviewNum = mediumReviewNum;
	}

	public Integer getBadReviewNum() {
		return badReviewNum;
	}

	public void setBadReviewNum(Integer badReviewNum) {
		this.badReviewNum = badReviewNum;
	}

	public Integer getReviewTotalNum() {
		return reviewTotalNum;
	}

	public void setReviewTotalNum(Integer reviewTotalNum) {
		this.reviewTotalNum = reviewTotalNum;
	}
	
}
