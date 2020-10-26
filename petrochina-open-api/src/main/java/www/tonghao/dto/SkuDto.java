package www.tonghao.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SkuDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sku;

    private Integer num;

    private BigDecimal price;

    private Integer mode;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }
}
