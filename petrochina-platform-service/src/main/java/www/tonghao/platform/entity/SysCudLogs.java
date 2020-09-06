package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "sys_cud_logs")
public class SysCudLogs extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 增改删类型
     */
    private String type;

    /**
     * 操作日志ID
     */
    @Column(name = "operate_logs_id")
    private Long operateLogsId;

    /**
     * 表名
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 表注释
     */
    @Column(name = "table_comment")
    private String tableComment;

    /**
     * 类名
     */
    @Column(name = "entity_class")
    private String entityClass;

    /**
     * 增改删数据
     */
    @Column(name = "data_content")
    private String dataContent;

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
     * 获取增改删类型
     *
     * @return type - 增改删类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置增改删类型
     *
     * @param type 增改删类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取操作日志ID
     *
     * @return operate_logs_id - 操作日志ID
     */
    public Long getOperateLogsId() {
        return operateLogsId;
    }

    /**
     * 设置操作日志ID
     *
     * @param operateLogsId 操作日志ID
     */
    public void setOperateLogsId(Long operateLogsId) {
        this.operateLogsId = operateLogsId;
    }

    /**
     * 获取表名
     *
     * @return table_name - 表名
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置表名
     *
     * @param tableName 表名
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取表注释
     *
     * @return table_comment - 表注释
     */
    public String getTableComment() {
        return tableComment;
    }

    /**
     * 设置表注释
     *
     * @param tableComment 表注释
     */
    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    /**
     * 获取类名
     *
     * @return entity_class - 类名
     */
    public String getEntityClass() {
        return entityClass;
    }

    /**
     * 设置类名
     *
     * @param entityClass 类名
     */
    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 获取增改删数据
     *
     * @return data_content - 增改删数据
     */
    public String getDataContent() {
        return dataContent;
    }

    /**
     * 设置增改删数据
     *
     * @param dataContent 增改删数据
     */
    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }
    
    @Override
    public boolean isCudLog() {
    	return false;
    }
}