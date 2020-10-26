package www.tonghao.dto;

import java.io.Serializable;

public class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    private String content;

    private String operate_time;

    private String operator;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(String operate_time) {
        this.operate_time = operate_time;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
