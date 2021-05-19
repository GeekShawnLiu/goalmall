package www.tonghao.platform.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@ApiModel(value="sku流水号")
@Table(name = "sku_dict")
public class SkuDict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 前缀
     */
    @ApiModelProperty(value="前缀")
    @Column(name = "prefix")
    private String prefix;

    /**
     * 流水号
     */
    @ApiModelProperty(value="流水号")
    @Column(name = "serial_num")
    private Integer serialNum;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }
}
