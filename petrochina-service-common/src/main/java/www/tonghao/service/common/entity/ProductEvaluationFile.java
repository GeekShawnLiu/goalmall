package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

/**
 * 
 * Description: 商品评价附件表
 * 
 * @version 2019年6月26日
 * @since JDK1.8
 */
@Table(name="product_evaluation_file")
public class ProductEvaluationFile extends BaseEntity{

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
    
    @ApiModelProperty(value="商品评价id")
    @Column(name = "product_evaluation_id")
    private Long productEvaluationId;
    
    @ApiModelProperty(value="附件路径")
    @Column(name = "path")
    private String path;

    @ApiModelProperty(value="附件名称")
    @Column(name = "file_name")
    private String fileName;

    @ApiModelProperty(value="附件大小")
    @Column(name = "file_size")
    private Long fileSize;

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

	public Long getProductEvaluationId() {
		return productEvaluationId;
	}

	public void setProductEvaluationId(Long productEvaluationId) {
		this.productEvaluationId = productEvaluationId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
    
}
