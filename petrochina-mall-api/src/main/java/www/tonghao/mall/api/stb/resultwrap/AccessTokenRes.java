package www.tonghao.mall.api.stb.resultwrap;

import www.tonghao.mall.api.stb.attwrap.AccessTokenResultAttr;
import www.tonghao.mall.core.ResultWrap;

public class AccessTokenRes implements ResultWrap {
	private boolean success;
	private String desc;
	private AccessTokenResultAttr accessTokenResultAttr;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public AccessTokenResultAttr getAccessTokenResultAttr() {
		return accessTokenResultAttr;
	}
	public void setAccessTokenResultAttr(AccessTokenResultAttr accessTokenResultAttr) {
		this.accessTokenResultAttr = accessTokenResultAttr;
	}
	
	
	
}
