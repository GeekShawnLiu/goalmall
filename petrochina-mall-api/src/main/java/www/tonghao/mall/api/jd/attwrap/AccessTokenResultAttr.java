package www.tonghao.mall.api.jd.attwrap;

public class AccessTokenResultAttr {
	private String uid;
	private String access_token;
	private String refresh_token;
	private long time;
	private long expires_in;
	private long refresh_token_expires;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
	public long getRefresh_token_expires() {
		return refresh_token_expires;
	}
	public void setRefresh_token_expires(long refresh_token_expires) {
		this.refresh_token_expires = refresh_token_expires;
	}
}
