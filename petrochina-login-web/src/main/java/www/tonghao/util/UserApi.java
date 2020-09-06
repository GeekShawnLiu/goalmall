package www.tonghao.util;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.common.utils.JsonUtil;


public class UserApi {
	public static void main(String[] args) {
		String str="{\"code\":\"SUCCESS\",\"data\":{\"content\":[{\"id\":\"5c137b37e50d9e5c7c52ce6f\",\"userName\":\"86461069\",\"displayName\":\"赵保丰\",\"nickName\":null,\"email\":\"\",\"mobile\":\"18510343252\",\"userEmail\":null,\"groupChain\":{\"50000000;65013741;50000674;50057442;65089813\":\"中国石油天然气集团公司;中油油服公司;东方地球物理公司;信息技术中心;信息安全技术部\"},\"groupChainCode\":null,\"groupChainName\":null,\"typeCode\":\"interior_user\",\"typeName\":null,\"enabled\":true,\"employeeTaggerState\":\"11\",\"certificateNumber\":null,\"extAttrs\":{\"employeeDuty\":null,\"gender\":\"true\",\"employeeEndDate\":null,\"createTime\":null,\"lastModifiedDate\":null,\"employeeGroup\":\"B\",\"employeeStartDate\":null}}],\"pageable\":{\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true},\"pageNumber\":0,\"pageSize\":50,\"offset\":0,\"paged\":true,\"unpaged\":false},\"last\":true,\"totalPages\":1,\"totalElements\":1,\"first\":true,\"numberOfElements\":1,\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true},\"size\":50,\"number\":0,\"empty\":false}}";
		JsonNode readTree = JsonUtil.readTree(str);
		JsonNode path = readTree.path("data").path("content");
		JsonNode jsonNode = path.get(0);
		String displayName = jsonNode.path("displayName").asText();
		String email = jsonNode.path("email").asText();
		String mobile = jsonNode.path("mobile").asText();
		JsonNode groupChain = jsonNode.path("groupChain");
		Iterator<String> fieldNames = groupChain.fieldNames();
		while (fieldNames.hasNext()) {
			String key = fieldNames.next();
			System.out.println(key);
			String value = groupChain.path(key).asText();
			System.out.println(value);
		}
	}
	
	
	public Map<String, String> getUser(String token, String validateKey) {
		//{"code":"SUCCESS","data":{"content":[{"id":"5c137b37e50d9e5c7c52ce6f","userName":"86461069","displayName":"赵保丰","nickName":null,"email":"","mobile":"18510343252","userEmail":null,"groupChain":{"50000000;65013741;50000674;50057442;65089813":"中国石油天然气集团公司;中油油服公司;东方地球物理公司;信息技术中心;信息安全技术部"},"groupChainCode":null,"groupChainName":null,"typeCode":"interior_user","typeName":null,"enabled":true,"employeeTaggerState":"11","certificateNumber":null,"extAttrs":{"employeeDuty":null,"gender":"true","employeeEndDate":null,"createTime":null,"lastModifiedDate":null,"employeeGroup":"B","employeeStartDate":null}}],"pageable":{"sort":{"sorted":false,"unsorted":true,"empty":true},"pageNumber":0,"pageSize":50,"offset":0,"paged":true,"unpaged":false},"last":true,"totalPages":1,"totalElements":1,"first":true,"numberOfElements":1,"sort":{"sorted":false,"unsorted":true,"empty":true},"size":50,"number":0,"empty":false}}
		Map<String, String> map=new HashMap<>();
		String signParam = getSign(token, validateKey);
		JSONObject param = new JSONObject();
		param.put("userName", "86461069");
		param.put("containChildOrg", "1");
		Map<String, String> headMap = new HashMap<>();
		headMap.put("Authorization", DJConfig.authorization);
		headMap.put("SignParam", signParam);
		String response = HttpUtils.doPost(DJConfig.user_url, param, headMap);
		JsonNode readTree = JsonUtil.readTree(response);
		if("SUCCESS".equals(readTree.path("code").asText())||"success".equals(readTree.path("code").asText())){
			JsonNode path = readTree.path("data").path("content");
			JsonNode jsonNode = path.get(0);
			String displayName = jsonNode.path("displayName").asText();
			map.put("displayName", displayName);
			String email = jsonNode.path("email").asText();
			map.put("email", email);
			String mobile = jsonNode.path("mobile").asText();
			map.put("mobile", mobile);
			JsonNode groupChain = jsonNode.path("groupChain");
			Iterator<String> fieldNames = groupChain.fieldNames();
			while (fieldNames.hasNext()) {
				String key = fieldNames.next();
				String value = groupChain.path(key).asText();
				map.put("orgNames", value);
				map.put("orgIds", key);
			}
		}
		
		return map;
	}

	private String getSign(String token, String validateKey) {
		// 三、签名token
		// 1、读取本地私钥
		PrivateKey privateKey = GetPrivateKey.getPrivateKey(
				DJConfig.keyStoreType, DJConfig.keystoreFile,
				DJConfig.password, DJConfig.friendPassword, DJConfig.alias);
		// 2、使用私钥签名token,获取签名值
		String sign = SignToken.getSign(token, DJConfig.SIGNATURE_ALGORITHM,
				DJConfig.authorization, privateKey);
		// 3、将签名值、PKI签发的公钥证书序列号、签名算法ID、验证标识封装为json并用base64转码，获取SignParam
		int algNum = SignToken.getAlgNum(DJConfig.SIGNATURE_ALGORITHM);
		JSONObject json = new JSONObject();
		json.put("sign", sign);
		json.put("sn", DJConfig.sn);
		json.put("signAlgrName", String.valueOf(algNum));
		json.put("validateKey", validateKey);
		String signParam = "";
		try {
			signParam = Base64.encodeBase64String(json.toString().getBytes(
					"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signParam;
	}
}
