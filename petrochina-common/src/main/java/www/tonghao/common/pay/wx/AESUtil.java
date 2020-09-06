package www.tonghao.common.pay.wx;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


import sun.misc.BASE64Decoder;

public class AESUtil {
	/** 
     * 密钥算法 
     */  
    private static final String ALGORITHM = "AES";  
    /** 
     * 加解密算法/工作模式/填充方式 
     */  
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";  
    /** 
     * 生成key 
     */  
  
    /** 
     * AES解密 
     *  
     * @param base64Data 
     * @return 
     * @throws Exception 
     */  
    public static String decryptData(String base64Data,String appKey) throws Exception { 
    	SecretKeySpec secretKeySpec = new SecretKeySpec(WXPayUtil.MD5(appKey).toLowerCase().getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);  
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);  
        return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(base64Data)));  
    }  
}
