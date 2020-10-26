package www.tonghao.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderSelectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String order_id;

    /**
     * 状态 0 是新建  1是妥投完成  -1是拒收 4是退换货中
     */
    private Integer state;

    /**
     * 状态 0 未确认  1 已确认
     */
    private Integer submit_state;

    /**
     * 发货状态  0 未发货  1已发货（全部）  2 部分发货
     */
    private Integer deliver_state;

    /**
     * 订单总价价格
     */
    private BigDecimal total_price;

    /**
     * 采购商品列表
     */
    private List<SkuDto> skus;

    /**
     * 已退货的商品
     */
    private List<SkuDto> return_skus;

    /**
     * 订单类型：1 是父订单， 2 是子订单
     */
    private Integer type;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSubmit_state() {
        return submit_state;
    }

    public void setSubmit_state(Integer submit_state) {
        this.submit_state = submit_state;
    }

    public Integer getDeliver_state() {
        return deliver_state;
    }

    public void setDeliver_state(Integer deliver_state) {
        this.deliver_state = deliver_state;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public List<SkuDto> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuDto> skus) {
        this.skus = skus;
    }

    public List<SkuDto> getReturn_skus() {
        return return_skus;
    }

    public void setReturn_skus(List<SkuDto> return_skus) {
        this.return_skus = return_skus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
