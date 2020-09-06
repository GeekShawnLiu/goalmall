/**
 * 
 */
package www.tonghao.common.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;


/**
 * AES加密
 * @author developer001
 *
 */
public class AES {
	public static final String INIT_VECTOR = "RandomInitVector";

	/**
	 * 加密
	 * @param key 密钥
	 * @param value 加密数据
	 * @return
	 */
	public static String encrypt(String key, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64Utils.encodeToString(encrypted);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param key 密钥
	 * @param value 解密数据
	 * @return
	 */
	public static String decrypt(String key, String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64Utils.decodeFromString(encrypted));

			return new String(original);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
