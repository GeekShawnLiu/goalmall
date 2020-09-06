package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.tonghao.service.common.base.BaseEntity;
/**
 * 

* <p>Title: BiddingOrdersItems</p>  

* <p>Description: </p>  

* @author mys  

* @date 2018年11月23日
 */
@Table(name = "bidding_orders_items")
public class BiddingOrdersItems extends BaseEntity{
	private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private String createAt;

    /**
     * 更新时间
     */
    @Column(name = "update_at")
    private String updateAt;

    /**
     * 商品品牌名称
     */
    @ApiModelProperty(value="商品品牌名称")
    private String name;

    /**
     * 商品品目名称
     */
    @ApiModelProperty(value="商品品目名称")
    @Column(name = "catalog_name")
    private String catalogName;

    /**
     * 采购数量
     */
    @ApiModelProperty(value="采购数量")
    private Integer num;

    /**
     * 商品型号
     */
    @ApiModelProperty(value="商品型号")
    private String model;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remarks;
    
    @ApiModelProperty(value="商品ID")
    @Column(name="product_id")
    private Long productId;
    
    @ApiModelProperty(value="单价")
    private BigDecimal price;
    
    @ApiModelProperty(value="供应商ID")
    private Long suppliersId;
    
	@ApiModelProperty(value="计量单位")
    private String unit;

    public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@ApiModelProperty(value="商品品目ID")
    @Column(name="catalog_id")
    private Long catalogId;
    
    @ApiModelProperty(value="商品所属序号")
    @Column(name="type_num")
    private Integer typeNum;
    
    @Transient
    private  BiddingOrdersSuppliers BiddingOrdersSuppliers;
    public BiddingOrdersSuppliers getBiddingOrdersSuppliers() {
		return BiddingOrdersSuppliers;
	}

	public void setBiddingOrdersSuppliers(BiddingOrdersSuppliers biddingOrdersSuppliers) {
		BiddingOrdersSuppliers = biddingOrdersSuppliers;
	}

	@Transient
    private Integer count;
    
	
	@Transient
	private String productUrl;
    public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	@Transient
    private List<BiddingBrandList> biddingBrandLists;
    
    
    public List<BiddingBrandList> getBiddingBrandLists() {
		return biddingBrandLists;
	}

	public void setBiddingBrandLists(List<BiddingBrandList> biddingBrandLists) {
		this.biddingBrandLists = biddingBrandLists;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTypeNum() {
		return typeNum;
	}

	public void setTypeNum(Integer typeNum) {
		this.typeNum = typeNum;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
     * 订单ID
     */
    private Long orderId;
    /**
     * 是否删除 0 显示 1 删除
     */
    private Integer isDelete;
    public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
     * 获取创建时间
     *
     * @return create_at - 创建时间
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
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
     * 获取商品品牌名称
     *
     * @return name - 商品品牌名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品品牌名称
     *
     * @param name 商品品牌名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品品目名称
     *
     * @return catalog_name - 商品品目名称
     */
    public String getCatalogName() {
        return catalogName;
    }

    /**
     * 设置商品品目名称
     *
     * @param catalogName 商品品目名称
     */
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    /**
     * 获取采购数量
     *
     * @return num - 采购数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置采购数量
     *
     * @param num 采购数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取商品型号
     *
     * @return model - 商品型号
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置商品型号
     *
     * @param model 商品型号
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Long getSuppliersId() {
  		return suppliersId;
  	}

  	public void setSuppliersId(Long suppliersId) {
  		this.suppliersId = suppliersId;
  	}

}