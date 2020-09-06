package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@ApiModel(value="特殊日期")
@Table(name = "special_date")
public class SpecialDate extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
    @Column(name = "date_time")
    private String dateTime;

    /**
     * 状态 1工作日，0休息日
     */
    @ApiModelProperty(value="状态 1工作日，0休息日")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;
    
    /**
     * 获取日期
     *
     * @return date_time - 日期
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * 设置日期
     *
     * @param dateTime 日期
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * 获取状态 1工作日，0休息日
     *
     * @return status - 状态 1工作日，0休息日
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 1工作日，0休息日
     *
     * @param status 状态 1工作日，0休息日
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取修改时间
     *
     * @return updated_at - 修改时间
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置修改时间
     *
     * @param updatedAt 修改时间
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}