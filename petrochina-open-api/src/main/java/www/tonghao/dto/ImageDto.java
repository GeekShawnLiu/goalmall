package www.tonghao.dto;

import java.io.Serializable;

public class ImageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String path;

    private Integer order;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
