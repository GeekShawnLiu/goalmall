package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "supplier_file")
public class SupplierFile extends BaseEntity {
   

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 路径
     */
    private String path;

    /**
     * 图片名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 0
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 文件code
     */
    @Column(name = "file_code")
    private String fileCode;


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
     * @return supplier_id
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * @param supplierId
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取路径
     *
     * @return path - 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置路径
     *
     * @param path 路径
     */
    public void setPath(String path) {
        this.path = path;
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

    /**
     * 获取文件大小
     *
     * @return file_size - 文件大小
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小
     *
     * @param fileSize 文件大小
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取0
     *
     * @return is_delete - 0
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置0
     *
     * @param isDelete 0
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取文件类型
     *
     * @return file_type - 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型
     *
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取文件code
     *
     * @return file_code - 文件code
     */
    public String getFileCode() {
        return fileCode;
    }

    /**
     * 设置文件code
     *
     * @param fileCode 文件code
     */
    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}