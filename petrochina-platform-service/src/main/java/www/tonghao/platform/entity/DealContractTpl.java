package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


/**  
* <p>Title: DealContractTpl</p>  
* <p>Description: 合同模板</p>  
* @author YML  
* @date 2019年1月16日  
*/ 
@Table(name = "deal_contract_tpl")
public class DealContractTpl extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 模板名称
     */
    private String name;

   /**
    * 1：车辆维修
	* 2：电梯空调维保
	* 3：车辆保险
	* 4：工程
	* 5：定点货物
	* 6：网上超市集采内直购、网上超市集采外直购
	* 7：网上超市竞价
	* 8：法律服务、印刷服务
	* 9：车辆加油主卡
	* 10：车辆加油副卡
	* 11：物业按人工
	* 12：物业按面积
	* 13：保安
    */
    private Integer type;

    /**
     * 模板内容
     */
    private String content;
    
    /** 
     * 模板状态: 0 暂存 1 启用 2 停用
     */  
    @ApiModelProperty("模板状态: 0 暂存 1 启用 2 停用")
    @Column(name = "status")
    private Integer status;
    
    /** 
     * 是否删除 0:否 1:是
     */  
    @ApiModelProperty("是否删除 0:否 1:是")
    @Column(name = "is_delete")
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
     * 获取模板名称
     *
     * @return name - 模板名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置模板名称
     *
     * @param name 模板名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取合同类型 1货物 ；2工程 ；3服务
     *
     * @return type - 合同类型 1货物 ；2工程 ；3服务
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置合同类型 1货物 ；2工程 ；3服务
     *
     * @param type 合同类型 1货物 ；2工程 ；3服务
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取模板内容
     *
     * @return content - 模板内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置模板内容
     *
     * @param content 模板内容
     */
    public void setContent(String content) {
        this.content = content;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
}