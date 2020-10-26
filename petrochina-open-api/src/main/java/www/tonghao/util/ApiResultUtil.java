package www.tonghao.util;

import www.tonghao.common.utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class ApiResultUtil {

    public static String success(String desc) {
        return result(true, desc, null);
    }

    public static String success(String desc, Object result) {
        return result(true, desc, result);
    }

    public static String error(String msg) {
        return result(false, msg, null);
    }

    public static String result(boolean success, String desc, Object result) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        resultMap.put("desc", desc);
        resultMap.put("result", result);
        return JsonUtil.toJson(resultMap);
    }
}
