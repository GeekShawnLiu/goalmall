package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;


@ApiModel(value="购物车项")
@Table(name = "cart_items")
public class CartItems extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
     * 数量
     */
	@ApiModelProperty(value="数量")
    private Integer num;

    /**
     * 产品ID
     */
	@ApiModelProperty(value="产品ID")
    @Column(name = "product_id")
    private Long productId;

    /**
     * 购物车ID
     */
	@ApiModelProperty(value="购物车ID")
    @Column(name = "cart_id")
    private Long cartId;

    /**
     * 供应商ID
     */
	@ApiModelProperty(value="供应商ID")
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 是否选中
     */
	@ApiModelProperty(value="是否选中")
    @Column(name = "is_checked")
    private Boolean isChecked;
    
    /**
     * 根据商家分组显示,标注当前是否是分组的购物车项
     */
    @Transient
    private boolean isSupplierGroup = false;
    
    /**
     * 统计供应商运费
     */
    @ApiModelProperty(value="统计供应商运费")
    @Transient
    public BigDecimal totalSupplierDeliverFee;
    
    /**
     * 购物车
     */
    @ApiModelProperty(value="购物车")
    @Transient
    private Cart cart;
    
    /**
     * 供应商支付方式
     * 	supplierId >> 支付方式
     */
    @Transient
    private Map<Long,List<PayWay>> supplierPayWays;
    
    /**
     * 商品
     */
    @ApiModelProperty(value="商品")
    @Transient
    private Products product;
    
    /**
     * 供应商
     */
    @ApiModelProperty(value="供应商")
    @Transient
    private Suppliers supplier;
    
    /**
     * 供应商小计金额
     */
    @Transient
    @ApiModelProperty(value="供应商小计金额")
    public BigDecimal totalSupplierSubtotal;
    
    /**
     * 活动ID
     */
	@ApiModelProperty(value="活动ID")
    @Column(name = "activity_id")
    private Long activityId;
	
	@ApiModelProperty(value="活动")
    @Transient
	private Activity activity;

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

    /**
     * 获取数量
     *
     * @return num - 数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置数量
     *
     * @param num 数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取产品ID
     *
     * @return product_id - 产品ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置产品ID
     *
     * @param productId 产品ID
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取购物车ID
     *
     * @return cart_id - 购物车ID
     */
    public Long getCartId() {
        return cartId;
    }

    /**
     * 设置购物车ID
     *
     * @param cartId 购物车ID
     */
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    /**
     * 获取供应商ID
     *
     * @return supplier_id - 供应商ID
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置供应商ID
     *
     * @param supplierId 供应商ID
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取是否选中
     *
     * @return is_checked - 是否选中
     */
    public Boolean getIsChecked() {
        return isChecked;
    }

    /**
     * 设置是否选中
     *
     * @param isChecked 是否选中
     */
    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

	public boolean isSupplierGroup() {
		return isSupplierGroup;
	}

	public void setSupplierGroup(boolean isSupplierGroup) {
		this.isSupplierGroup = isSupplierGroup;
	}

	public BigDecimal getTotalSupplierDeliverFee() {
		return totalSupplierDeliverFee;
	}

	public void setTotalSupplierDeliverFee(BigDecimal totalSupplierDeliverFee) {
		this.totalSupplierDeliverFee = totalSupplierDeliverFee;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Suppliers getSupplier() {
		return supplier;
	}

	public void setSupplier(Suppliers supplier) {
		this.supplier = supplier;
	}

	public BigDecimal getTotalSupplierSubtotal() {
		return totalSupplierSubtotal;
	}

	public void setTotalSupplierSubtotal(BigDecimal totalSupplierSubtotal) {
		this.totalSupplierSubtotal = totalSupplierSubtotal;
	}
	
	public Map<Long, List<PayWay>> getSupplierPayWays() {
		return supplierPayWays;
	}

	public void setSupplierPayWays(Map<Long, List<PayWay>> supplierPayWays) {
		this.supplierPayWays = supplierPayWays;
	}

	/**
	 * 获得价格
	 * @return
	 */
	public BigDecimal getPrice(){
		Products mp = getProduct();
		if(mp != null && mp.getPrice() != null){
			return mp.getPrice();
		}else{
			return BigDecimal.ZERO;
		}
	}
	
	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	public BigDecimal getSubtotal() {
		if (getNum() != null) {
			return getPrice().multiply(new BigDecimal(getNum()));
		} else {
			return new BigDecimal(0);
		}
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
}