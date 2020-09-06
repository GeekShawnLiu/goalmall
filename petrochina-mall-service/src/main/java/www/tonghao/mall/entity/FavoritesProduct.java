package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.common.enums.FavorutesProductType;
import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.entity.Products;

@Table(name = "favorites_product")
@ApiModel("商品收藏夹")
public class FavoritesProduct extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 商品ID
	 */
	@Column(name = "product_id")
	private Long productId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	@Column(name = "created_at")
	private String createdAt;

	/**
	 * 修改时间
	 */
	@Column(name = "updated_at")
	private String updatedAt;

	@Column(name = "type")
	private FavorutesProductType type;// 直购、批量

	@ApiModelProperty("商品")
	@Transient
	private Products product;

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
	 * @param userId
	 *            用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取商品ID
	 *
	 * @return product_id - 商品ID
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * 设置商品ID
	 *
	 * @param productId
	 *            商品ID
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

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
	 * @param createdAt
	 *            创建时间
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
	 * @param updatedAt
	 *            修改时间
	 */
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public FavorutesProductType getType() {
		return type;
	}

	public void setType(FavorutesProductType type) {
		this.type = type;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}
}