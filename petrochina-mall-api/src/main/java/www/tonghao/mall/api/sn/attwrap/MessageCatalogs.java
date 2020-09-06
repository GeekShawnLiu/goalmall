package www.tonghao.mall.api.sn.attwrap;

public class MessageCatalogs implements Message {
  private String oldcategoryId;
  
  private String oldcategoryName;
  
  private String categoryId;
  
  private String categoryName;
  
  //0.更新，1.修改,2.删除
  private int status;
  
  private String time;
  private String id;

  
  private int type;
public String getOldcategoryId() {
	return oldcategoryId;
}

public void setOldcategoryId(String oldcategoryId) {
	this.oldcategoryId = oldcategoryId;
}

public String getOldcategoryName() {
	return oldcategoryName;
}

public void setOldcategoryName(String oldcategoryName) {
	this.oldcategoryName = oldcategoryName;
}

public String getCategoryId() {
	return categoryId;
}

public void setCategoryId(String categoryId) {
	this.categoryId = categoryId;
}

public String getCategoryName() {
	return categoryName;
}

public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}

public int getStatus() {
	return status;
}

public void setStatus(int status) {
	this.status = status;
}

public String getTime() {
	return time;
}

public void setTime(String time) {
	this.time = time;
}

public int getType() {
	return type;
}

public void setType(int type) {
	this.type = type;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}
  
  
  
}
