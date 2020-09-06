package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

@ApiModel(value="购物车")
public class Cart extends BaseEntity{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 创建日期
     */
	@ApiModelProperty(value="创建日期")
    @Column(name = "created_at")
    private String createdAt;

    /**
     * 修改时间
     */
	@ApiModelProperty(value="修改时间")
    @Column(name = "updated_at")
    private String updatedAt;

    /**
     * 用户ID
     */
	@ApiModelProperty(value="用户ID")
    @Column(name = "user_id")
    private Long userId;

	/**
     * 用户
     */
	@ApiModelProperty(value="用户")
    @Transient
    private Users user;
    
    /**
     * 购物车项
     */
	@ApiModelProperty(value="购物车项")
    @Transient
    private List<CartItems> cartItems;

    /**
     * 获取创建日期
     *
     * @return created_at - 创建日期
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建日期
     *
     * @param createdAt 创建日期
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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<CartItems> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}
	
	/**
	 * 得到所有的购物车项，根据供应商分组 
	 * 			购物车列表页用到
	 * @return
	 */
	public List<CartItems> getGroupItems(){
		List<CartItems> cartItems = getCartItems();
		List<Long> supplierIds = Lists.newCopyOnWriteArrayList();
		for (int i = 0; i < cartItems.size(); i++) {
			CartItems item = cartItems.get(i);
			Long supplierId = item.getSupplierId();
			item.setSupplierGroup(false);
			if(!supplierIds.contains(supplierId)){
				supplierIds.add(supplierId);
				item.setSupplierGroup(true);
				item.setTotalSupplierSubtotal(getTotalSupplierSubtotal(supplierId, cartItems));
			}
		}
		return cartItems;
	}
	
	/**
	 * 得到已选中的购物车项，根据供应商分组 订单确认页用到
	 * @return
	 */
	public List<CartItems> getCheckedGroupItems(){
		List<CartItems> result = Lists.newCopyOnWriteArrayList();
		List<CartItems> cartItems = getCartItems();
		List<Long> supplierIds = Lists.newCopyOnWriteArrayList();
		for(int i=0;i<cartItems.size();i++){
			CartItems item = cartItems.get(i);
			if(item.getIsChecked()){
				Long supplierId = item.getSupplierId();
				if(!supplierIds.contains(supplierId)){
					supplierIds.add(supplierId);
					item.setSupplierGroup(true);
					item.setTotalSupplierSubtotal(getTotalSupplierSubtotal(supplierId, cartItems));
				}
				result.add(item);
			}
		}
		return result;
	}
	
	/**
	 * 统计供应商小计金额
	 * @param supplierId
	 * @param cartItems
	 * @return
	 */
	private BigDecimal getTotalSupplierSubtotal(Long supplierId,List<CartItems> cartItems){
		BigDecimal totalSupplierSubtotal = new BigDecimal(0);
		for (int i = 0; i < cartItems.size(); i++) {
			CartItems item = cartItems.get(i);
			Suppliers supplier = item.getSupplier();
			if(item.getIsChecked()&&supplier!=null&&supplierId.equals(supplier.getId())){
				totalSupplierSubtotal = totalSupplierSubtotal.add(item.getSubtotal());
			}
		}
		return totalSupplierSubtotal;
	}
	
	/**
	 * 是否包含产品
	 * @param product
	 * @return
	 */
	@Transient
	@JsonIgnore
	public boolean contains(Products product, Long activityId){
		List<CartItems> items = getCartItems();
		boolean flag = false;
		if(product!=null&&items!=null){
			for(CartItems cartItem:items){
				if(cartItem.getProductId().equals(product.getId())){
					if(cartItem.getActivityId() == null && activityId == null){
						flag |= true;
					}else if(cartItem.getActivityId() != null && activityId != null && cartItem.getActivityId().equals(activityId)){
						flag |= true;
					}else{
						flag |= false;
					}
				}
			}
		}
		return flag;
	}
	
	@Transient
	@JsonIgnore
	public CartItems getCartItem(Products product, Long activityId){
		List<CartItems> items = getCartItems();
		if(product!=null&&items!=null){
			for(CartItems cartItem:items){
				if(cartItem.getProductId().equals(product.getId())){
					if(cartItem.getActivityId() == null && activityId == null){
						return cartItem;
					}else if(cartItem.getActivityId() != null && activityId != null && cartItem.getActivityId().equals(activityId)){
						return cartItem;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 购物车里有多少项
	 * @return
	 */
	@Transient
	public int getCount(){
		return getCartItems()==null?0:getCartItems().size();
	}
	
	/**
	 * 获取购物车金额
	 * @return
	 */
	@Transient
	public BigDecimal getAmount(){
		BigDecimal amount = new BigDecimal(0);
		List<CartItems> pCartItems = getCartItems();
		if (pCartItems != null) {
			for(CartItems cartItem:pCartItems){
				if (cartItem.getIsChecked()&& cartItem.getSubtotal() != null) {
					amount = amount.add(cartItem.getSubtotal());
				}
			}
		}
		return amount;
	}
    
}