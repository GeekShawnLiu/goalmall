package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@ApiModel("定时器")
@Table(name = "sys_task")
public class SysTask extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="开始时间")
    @Column(name = "created_at")
    private String createdAt;

    @ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 任务名称
     */
    @ApiModelProperty(value="任务名称")
    @Column(name = "task_name")
    private String taskName;

    /**
     * 说明
     */
    @ApiModelProperty(value="说明")
    @Column(name = "task_remark")
    private String taskRemark;

    /**
     * 任务类
     */
    @ApiModelProperty(value="任务类")
    @Column(name = "job_class")
    private String jobClass;

    /**
     * 规则表达式
     */
    @ApiModelProperty(value="规则表达式")
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 是否启用 0启用 1暂停
     */
    @ApiModelProperty(value="是否启用 1启用 0暂停")
    @Column(name = "is_enable")
    private Short isEnable;

    /**
     * 执行时间
     */
    @ApiModelProperty(value="执行时间")
    @Column(name = "job_time")
    private Date jobTime;
    
    /**
     * 类别，1spe项目定时器，2商城专用定时器
     */
    @Column(name = "type")
    private String type;

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
     * 获取任务名称
     *
     * @return task_name - 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     *
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取说明
     *
     * @return task_remark - 说明
     */
    public String getTaskRemark() {
        return taskRemark;
    }

    /**
     * 设置说明
     *
     * @param taskRemark 说明
     */
    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    /**
     * 获取任务类
     *
     * @return job_class - 任务类
     */
    public String getJobClass() {
        return jobClass;
    }

    /**
     * 设置任务类
     *
     * @param jobClass 任务类
     */
    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    /**
     * 获取规则表达式
     *
     * @return cron_expression - 规则表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 设置规则表达式
     *
     * @param cronExpression 规则表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 获取是否启用
     *
     * @return is_enable - 是否启用
     */
    public Short getIsEnable() {
        return isEnable;
    }

    /**
     * 设置是否启用
     *
     * @param isEnable 是否启用
     */
    public void setIsEnable(Short isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * 获取执行时间
     *
     * @return job_time - 执行时间
     */
    public Date getJobTime() {
        return jobTime;
    }

    /**
     * 设置执行时间
     *
     * @param jobTime 执行时间
     */
    public void setJobTime(Date jobTime) {
        this.jobTime = jobTime;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
    
}