package www.tonghao.service.common.entity;

import www.tonghao.service.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "order_track")
public class OrderTrack extends BaseEntity {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "content")
    private String content;

    @Column(name = "operate_time")
    private String operateTime;

    @Column(name = "operator")
    private String operator;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
