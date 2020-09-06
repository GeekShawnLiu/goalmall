package www.tonghao.mall.api.jd.attwrap;


public class MessageAttr<T extends Message> {

	 private String id;
	  
	  private int type;
	  
	  private String time;
	  
	  private T message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}
	
	  
	  
	  
	
}
