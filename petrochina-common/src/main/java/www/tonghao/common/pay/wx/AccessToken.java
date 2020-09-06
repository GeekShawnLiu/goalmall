package www.tonghao.common.pay.wx;

/**
 * @program: petrochina
 * @description:
 * @author:
 * @create: 2019-08-09 16:09
 */
public class AccessToken {

    private String access_token;

    private String openid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
