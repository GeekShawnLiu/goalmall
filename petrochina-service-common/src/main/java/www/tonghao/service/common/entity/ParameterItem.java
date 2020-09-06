package www.tonghao.service.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "parameter_item")
@ApiModel(value="参数明细")
public class ParameterItem extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 名称
     */
    @ApiModelProperty(value="明细名称")
    private String name;

    /**
     * 参数id
     */
    @ApiModelProperty(value="主信息id")
    @Column(name = "stand_param_id")
    private String standParamId;

    /**
     * 排序越小越靠前
     */
    @ApiModelProperty(value="排序越小越靠前")
    private Float position;

    /**
     * 平台id
     */
    @ApiModelProperty(value="平台id")
    @Column(name = "stand_value_id")
    private String standValueId;

    @Column(name = "parameter_id")
    @ApiModelProperty(value="参数id")
    private Long parameterId;


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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取参数id
     *
     * @return stand_param_id - 参数id
     */
    public String getStandParamId() {
        return standParamId;
    }

    /**
     * 设置参数id
     *
     * @param standParamId 参数id
     */
    public void setStandParamId(String standParamId) {
        this.standParamId = standParamId;
    }

    /**
     * 获取排序越小越靠前
     *
     * @return position - 排序越小越靠前
     */
    public Float getPosition() {
        return position;
    }

    /**
     * 设置排序越小越靠前
     *
     * @param position 排序越小越靠前
     */
    public void setPosition(Float position) {
        this.position = position;
    }

    /**
     * 获取平台id
     *
     * @return stand_value_id - 平台id
     */
    public String getStandValueId() {
        return standValueId;
    }

    /**
     * 设置平台id
     *
     * @param standValueId 平台id
     */
    public void setStandValueId(String standValueId) {
        this.standValueId = standValueId;
    }

    /**
     * @return parameter_id
     */
    public Long getParameterId() {
        return parameterId;
    }

    /**
     * @param parameterId
     */
    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }
}