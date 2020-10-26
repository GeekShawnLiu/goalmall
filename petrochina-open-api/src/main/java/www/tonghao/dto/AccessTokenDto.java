package www.tonghao.dto;

import java.io.Serializable;

public class AccessTokenDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String timestamp;

    private String sign;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
