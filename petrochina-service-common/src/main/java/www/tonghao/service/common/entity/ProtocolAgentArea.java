package www.tonghao.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "protocol_agent_area")
public class ProtocolAgentArea extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 地区id
     */
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 地区id字符串
     */
    @Column(name = "area_tree_ids")
    private String areaTreeIds;

    /**
     * 地区名称
     */
    @Column(name = "area_name")
    private String areaName;
    
    /**
     * 名称字符串
     */
    @Column(name = "area_tree_names")
    private String areaTreeNames;
    
    @Column(name = "protocol_agent_id")
    private Long protocolAgentId;


    /**
     * 获取地区id
     *
     * @return area_id - 地区id
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * 设置地区id
     *
     * @param areaId 地区id
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取地区id字符串
     *
     * @return area_tree_ids - 地区id字符串
     */
    public String getAreaTreeIds() {
        return areaTreeIds;
    }

    /**
     * 设置地区id字符串
     *
     * @param areaTreeIds 地区id字符串
     */
    public void setAreaTreeIds(String areaTreeIds) {
        this.areaTreeIds = areaTreeIds;
    }

    /**
     * 获取地区名称
     *
     * @return area_name - 地区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置地区名称
     *
     * @param areaName 地区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取名称字符串
     *
     * @return area_tree_names - 名称字符串
     */
    public String getAreaTreeNames() {
        return areaTreeNames;
    }

    /**
     * 设置名称字符串
     *
     * @param areaTreeNames 名称字符串
     */
    public void setAreaTreeNames(String areaTreeNames) {
        this.areaTreeNames = areaTreeNames;
    }

	public Long getProtocolAgentId() {
		return protocolAgentId;
	}

	public void setProtocolAgentId(Long protocolAgentId) {
		this.protocolAgentId = protocolAgentId;
	}
    
    
}