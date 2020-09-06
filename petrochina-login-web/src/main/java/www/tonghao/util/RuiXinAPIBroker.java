package www.tonghao.util;

import com.cnpc.ruixin.sdk.RuiXinAPI;
import com.cnpc.ruixin.sdk.envirenment.EnvType;

public class RuiXinAPIBroker {
	private static volatile  RuiXinAPI rxAPI = null;
	private RuiXinAPIBroker() {}
	
	/**
	 * to get a singleton instance of ruixin api
	 * @param appCode
	 * @param appKey
	 * @param env
	 * @return
	 */
	public static RuiXinAPI getRuiXinAPI(String appCode, String appKey, EnvType env) {
		if (null == rxAPI) {
			synchronized (RuiXinAPIBroker.class) {
				if(null == rxAPI) { //recheck
					rxAPI = RuiXinAPI.CreateAPI(appCode, appKey, env);
				}
			}
		}
		return rxAPI;
	}
}
