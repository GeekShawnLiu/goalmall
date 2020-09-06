package www.tonghao.login.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import www.tonghao.common.utils.Base64;

/**
 * 
 * Description: RSA加密
 * 
 * @version 2019年7月31日
 * @since JDK1.8
 */
public class RSAEncrypt {

	/**
	 * 
	 * Description: 获取私钥
	 * 
	 * @data 2019年7月31日
	 * @param
	 * @return
	 */
	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr 公钥数据字符串
	 * @throws Exception 加载公钥时产生的异常
	 */
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("公钥数据为空");
		}
	}
	
	/**
	 * 
	 * Description: 私钥解密
	 * 
	 * @data 2019年7月31日
	 * @param
	 * @return
	 */
	public static String decrypt(String privateKeyStr, String pwdText)
			throws Exception {
		if(privateKeyStr == null){
			throw new Exception("解密私钥为空, 请设置");
		}
		byte[] cipherData = Base64.decode(pwdText);
		RSAPrivateKey privateKey = loadPrivateKeyByStr(privateKeyStr);
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return new String(output);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception("输入错误");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("密文数据已损坏");
		}
	}
	
	
	/**
	 * 公钥加密过程
	 * 
	 * @param publicKey 公钥
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception 加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();

			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("明文数据已损坏");
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		String pwd = "G3KeDVx0Ntb/5+vIyFyhqwsd/Rb7oW2AHNX3CxhBzrnjjKnOgNb3byTd+KoAAnU6xsI35aaZlko8xNYPQrLlDt4Yn/C/vkcJsfpi0mFgbkqUEZHQajlcOjMhAjRqi8DuBrny5JScAOi/egVKuSuWE/fD+DzkRmFx1CNkygD+jqs=";
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM2v47m702hB/FetGzV3u4/S0qjbcYOuaReOyY4LWXEOK+Cp2wd9J3BcdNXfk5s6WoW1VO6pmfC8MF8Vy8oPAvwkv+cYIqSDFAPQ0SNqGFPu8UtegjIzTJwd31Mtv7ICG4M8ynv7piWGY8tIcN1qyqW2iXLmWCDzpMkw7Mw0am2vAgMBAAECgYATxcDCbgfYkd9MHlvrO+NP5BhUGyRCYFXB6fv+J6EleRNGuY1QnvtXlcu1c5Sg628gQ1oyxyMBPVX5DgNVHTRQFKkUdIoK0oYqb3dr0z72KOTfLoazGkKg/b7tHlRgOuRi+pxwv3Jee+z1Wlkn9je4j5M+SkR0Qsvn55GmpiTJIQJBAOii5tpc2Biq0rTk/JK+/4IzLR1lcL6ZGmqONt23jkXInHUrHhRXOkFUDvzKE+vQKI/InWmQ8kk4KiuPhEsPOs0CQQDiWB67o7DsZwoyHW4WdrE9pT8coIJZ+W3a+tkh+OtdNosl60yEMeiTbaGqzD1piT+T00mkU4OKuOeykYdPdEJrAkEAgDqqvNpIUvB1zDgb8FdPR1kmGPn9N1H0+Z8CJxwyU8zMUNk/bz7VYYUP7c8FRAotEXQnMDUxF6LZu8ETzrvKUQJBALzMmEYYEln+0QhgeBWQx+MN1YkBcfmvDy1blIa75UeJWaQ1k0upfcCL8BBR8N/5NekOkxzY9J5gAhR3Q5DI/yUCQGDCutiBA6LluqcDHQmkdYKCTNH28uCmZuPUckXSSC5gbT6z7UJgYTHbtd9wKegb8tEk5bCwMtcj841xSRhljag=";
		System.out.println(decrypt(privateKey, pwd));
	}
}
