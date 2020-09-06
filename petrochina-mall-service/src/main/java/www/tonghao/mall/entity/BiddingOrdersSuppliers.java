package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
/**
 * 

* <p>Title: BiddingOrdersSuppliers</p>  

* <p>Description: </p>  

* @author mys  

* @date 2018年11月23日
 */
@Table(name = "bidding_orders_suppliers")
public class BiddingOrdersSuppliers extends BaseEntity{

	private static final long serialVersionUID = 1L;

    /**
     * 创建日期
     */
    @Column(name = "create_at")
    private String createAt;

    /**
     * 更新时间
     */
    @Column(name = "update_at")
    private String updateAt;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value="供应商名称")
    private String name;

    /**
     * 供应商报价
     */
    @ApiModelProperty(value="供应商报价")
    private Double price;

    /**
     * 供应商供货周期
     */
    @ApiModelProperty(value="供应商供货周期")
    private String cycle;

    /**
     * 竞价订单ID
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 竞价商品ID
     */
    @Column(name = "items_id")
    private Long itemsId;

    @Column(name="suppliers_id")
    private Long suppliersId;

    /**
     * 竞价状态  0 未成交 1 成交
     */
    @ApiModelProperty(value="竞价状态  0 未成交 1 成交")
    @Column(name="b_status")
    private Integer bStatus;
    
    @Transient
    private Integer ranking;
    
    @ApiModelProperty(value="总报价")
    @Column(name="total_price")
    private Double totalPrice;
    
    @ApiModelProperty(value="服务报价")
    @Column(name="service_price")
    private Double servicePrice;
    
    @ApiModelProperty("联系人")
    private String linkman;
    
    @ApiModelProperty("联系电话")
    private String tel;
    
    private String cert;
    
    public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Transient
    private BiddingOrdersItems biddingOrdersItems;
    
    public BiddingOrdersItems getBiddingOrdersItems() {
		return biddingOrdersItems;
	}

	public void setBiddingOrdersItems(BiddingOrdersItems biddingOrdersItems) {
		this.biddingOrdersItems = biddingOrdersItems;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	@ApiModelProperty(value="商品Id")
    @Column(name="product_id")
    private Long productId;
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getbStatus() {
		return bStatus;
	}

	public void setbStatus(Integer bStatus) {
		this.bStatus = bStatus;
	}

	public Long getSuppliersId() {
		return suppliersId;
	}

	public void setSuppliersId(Long suppliersId) {
		this.suppliersId = suppliersId;
	}

	/**
     * 获取创建日期
     *
     * @return create_at - 创建日期
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建日期
     *
     * @param createAt 创建日期
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取更新时间
     *
     * @return update_at - 更新时间
     */
    public String getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置更新时间
     *
     * @param updateAt 更新时间
     */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * 获取供应商名称
     *
     * @return name - 供应商名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置供应商名称
     *
     * @param name 供应商名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取供应商报价
     *
     * @return price - 供应商报价
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置供应商报价
     *
     * @param price 供应商报价
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取供应商供货周期
     *
     * @return cycle - 供应商供货周期
     */
    public String getCycle() {
        return cycle;
    }

    /**
     * 设置供应商供货周期
     *
     * @param cycle 供应商供货周期
     */
    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    /**
     * 获取竞价订单ID
     *
     * @return order_id - 竞价订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置竞价订单ID
     *
     * @param orderId 竞价订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取竞价商品ID
     *
     * @return items_id - 竞价商品ID
     */
    public Long getItemsId() {
        return itemsId;
    }

    /**
     * 设置竞价商品ID
     *
     * @param itemsId 竞价商品ID
     */
    public void setItemsId(Long itemsId) {
        this.itemsId = itemsId;
    }
}