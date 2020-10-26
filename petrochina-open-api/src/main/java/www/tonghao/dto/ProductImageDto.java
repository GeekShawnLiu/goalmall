package www.tonghao.dto;

import java.io.Serializable;
import java.util.List;

public class ProductImageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sku;

    private List<ImageDto> images;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }
}
