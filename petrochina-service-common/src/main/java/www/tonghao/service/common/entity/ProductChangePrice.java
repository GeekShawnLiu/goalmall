package www.tonghao.service.common.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import www.tonghao.service.common.base.BaseEntity;

@Table(name = "product_change_price")
@ApiModel(value="商品改价记录")
public class ProductChangePrice extends BaseEntity {
	
	@Column(name = "product_id")
	private Long productId;
	
	@Column(name = "market_price")
	private BigDecimal marketPrice; //改价前市场价
	
	@Column(name = "protocol_price")
	private BigDecimal protocolPrice;//改价前协议价
	
	@Column(name = "price")
	private BigDecimal price;//改价前售价
	
	
	
	
	@Column(name = "new_market_price")
	private BigDecimal newMarketPrice; //改价后市场价
	
	@Column(name = "new_protocol_price")
	private BigDecimal newProtocolPrice;//改价后协议价
	
	@Column(name = "new_price")
	private BigDecimal newPrice;//改价后售价
	
	@Column(name = "create_at")
	private String  createAt;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getProtocolPrice() {
		return protocolPrice;
	}

	public void setProtocolPrice(BigDecimal protocolPrice) {
		this.protocolPrice = protocolPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public BigDecimal getNewMarketPrice() {
		return newMarketPrice;
	}

	public void setNewMarketPrice(BigDecimal newMarketPrice) {
		this.newMarketPrice = newMarketPrice;
	}

	public BigDecimal getNewProtocolPrice() {
		return newProtocolPrice;
	}

	public void setNewProtocolPrice(BigDecimal newProtocolPrice) {
		this.newProtocolPrice = newProtocolPrice;
	}

	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}
	
	
	
	
}
