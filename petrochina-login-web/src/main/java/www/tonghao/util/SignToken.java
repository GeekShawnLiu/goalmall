package www.tonghao.util;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 * @Author: zbf
 * @Date: 2019/5/8 16:07
 */
public class SignToken {


    /**
     * 签名token
     * @param token 被签名的token
     * @param alg   签名算法
     * @param authorization 服务端调用API密钥
     * @param privateKey  用户私钥
     * @return 签名值
     */
    public static String getSign(String token, String alg, String authorization, PrivateKey privateKey){
        //拼接auth_token
        String authToken = token + authorization;
        //获取签名值
        byte[] sign = null;
        try {
            Signature sig = Signature.getInstance(alg);
            sig.initSign(privateKey);
            sig.update(authToken.getBytes());
            sign = sig.sign();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        String base64sign = Base64.encodeBase64String(sign);
        return base64sign;
    }

    /**
     * 将签名算法名字置换为ID
     * @param algName 算法名
     * @return  算法ID
     */
    public static int getAlgNum(String algName){
        int algNum = 0;
        switch (algName.trim()){
            case "SHA1WithRSA":
                algNum = 1; break;
            case "SHA256WithRSA":
                algNum = 2; break;
            case "SHA384WithRSA":
                algNum = 3; break;
            case "SHA512WithRSA":
                algNum = 4; break;
            case "SM3WithSM2":
                algNum = 5; break;
            case "SHA1WithSM2":
                algNum = 6; break;
            case "SHA224WithSM2":
                algNum = 7; break;
            case "SHA256WithSM2":
                algNum = 8; break;
            case "SHA384WithSM2":
                algNum = 9; break;
            case "SHA512WithSM2":
                algNum = 10; break;
            case "SHA1/SM2":
                algNum = 11; break;
            case "MD2WithSM2":
                algNum = 12; break;
            case "MD4WithSM2":
                algNum = 13; break;
            case "MD5WithSM2":
                algNum = 14; break;
            case "SHA224WithRSA":
                algNum = 16; break;
        }
        return algNum;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String signParam = "eyJzaWduIjoiTTE4aVFjNEdKWEF5MFpHamZvRUcvUmsraDBUY09iTUtaQlQwTWZkU2hpaWxWYzFJek9HT0s1M05ZY3ZnK1lrWjI0VGhpTE9kUWdHckhoWjVmZU9UamVlazRhdEVueWp0ZC9HWmFScVo3UC9SM2NPU0ZJSXNFd2hpWGlhTFFVRVR2cU1XN0N3UGpMVFZnS055QWppNWZuV2lKQ3FJVlJhRmNTcFh5ZXYvWC83T1FDb3RzMUdRQ1dmOUl4dDJkTUQ3VDJqVXFBUFFTUUpTU1VKejcwNDVRbFZoQ2x6STJmVHBWSUxDY2FPWm5qVFZrQWkyNE9tblJFU1o2Q09Sb3JEUnhZMjFlalVtVGU3RGxZV2hZbHRpbk5KZTFYRXVubUd3MjFnTGZJVS9vMTlBVlJtblllelZpNUVmdkE3cko0K01XOWdEeUhYWUw4SmVwemZuM0V4d0VBPT0iLCJzbiI6IjEwZjQwZTc1Njk4MDgxNWQiLCJzaWduQWxnck5hbWUiOiIyIiwidmFsaWRhdGVLZXkiOiI1Y2QzY2QzZmU1MGQ5ZTdhNjk2NzhkN2UifQ==";
        String signJson = new String(Base64.decodeBase64(signParam), "UTF-8");
        System.out.println(signJson);
    }

}
