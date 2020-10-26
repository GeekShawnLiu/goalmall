package www.tonghao.dto;

import java.io.Serializable;
import java.util.Map;

public class MessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Map<String, Object> result;

    private Integer type;

    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
