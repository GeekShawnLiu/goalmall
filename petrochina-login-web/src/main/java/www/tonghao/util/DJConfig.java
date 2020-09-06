package www.tonghao.util;

public class DJConfig {
	
  //#keystore的类型
  public static final String keyStoreType="jks";
  //keystore的路径
  public static final String keystoreFile="C:\\ca\\zbfkeystore.keystore";
  //keystore的解析密码
  public static final String password="123456";
  //私钥的解析密码
  public static final String friendPassword="123456";
  //私钥别名
  public static final String alias="zbf";
  //应用名称
  public static final String appCode="testcas";
  //认证编码
  public static final String authorization="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBeuKXR+yXI1jE0ETCKZyjXxVzXIKP9r0xDf3Ed3o4vxfaL8M4vp5oA9lBUE0EDn3/I+o3v6oK4AL2gj+0gg99rwxg9pN6MBVIv8VdkJvliti9uzj8EPvQ5q01ZpcqjHoyUx1p4uuBKawieOFCSqwm5YgBg2hFaMKhhxyZypU5ZwIDAQAB";
  //公钥序列号
  public static final String sn="10f40e756980815d";
  //签名算法名称
  public static final String SIGNATURE_ALGORITHM="SHA256WithRSA";
  //获取token路径
  public static final String token_url="http://11.11.160.30:8081/ngiam-rst/v1/api/token/getToken";
  //获取user路径
  public static final String user_url="http://11.11.160.30:8081/ngiam-rst/v1/api/psn/userSearch";
  //获取组织机构路径
  public static final String org_url="http://11.11.160.30:8081/ngiam-rst/v1/api/org/orgSearch";
}
