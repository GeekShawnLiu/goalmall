package www.tonghao.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: petrochina
 * @description: 公众号支付返回
 * @author:
 * @create: 2019-08-09 10:30
 */
@ApiModel("公众号支付返回")
public class JsApiResult {


    @ApiModelProperty(value="统一支付接口返回的prepay_id参数值")
    private String prepayId;

    @ApiModelProperty(value="支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符")
    private String timeStamp;

    @ApiModelProperty(value=" 支付签名随机串，不长于 32 位")
    private String nonceStr;

    @ApiModelProperty(value="签名方式，默认为'SHA1'，使用新版支付需传入'MD5'")
    private String signType;

    @ApiModelProperty(value="支付签名")
    private String paySign;

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
