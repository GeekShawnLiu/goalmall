package www.tonghao.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * @Author: zbf
 * @Date: 2019/5/8 16:27
 */
public class GetPrivateKey {

    /**
     *
     * @param keyStore 用户证书库
     * @param alias     私钥别名
     * @param password  私钥密码
     * @return 用户本地的秘钥对
     */
    public static KeyPair getKeyPair(KeyStore keyStore, String alias, char[] password){
        try{
            Key key = keyStore.getKey(alias, password);
            if (key instanceof PrivateKey){
                Certificate certificate = keyStore.getCertificate(alias);
                PublicKey publicKey = certificate.getPublicKey();
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        }catch (UnrecoverableKeyException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (KeyStoreException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将私钥从String类型转为java.security.PrivateKey
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey parsePrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 获取用户私钥
     * @param keyStoreType 用户证书库类型
     * @param keystoreFile  证书库路径
     * @param password  证书库密码
     * @param friendPassword 用户本地私钥密码
     * @param alias 私钥别名
     * @return 用户私钥
     */
    public static PrivateKey getPrivateKey(String keyStoreType, String keystoreFile, String password, String friendPassword, String alias) {
        PrivateKey privateKey = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(new FileInputStream(keystoreFile), password.toCharArray());
            KeyPair keyPair = getKeyPair(keyStore, alias, friendPassword.toCharArray()); //注意这里的密码是你的别名对应的密码，不指定的话就是你的keystore的解析密码
            //从秘钥对中获取私钥信息
            String key = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
            //转为java.security.PrivateKey
            privateKey = parsePrivateKey(key);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

}
