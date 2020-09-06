package www.tonghao.mall.api.jd.attwrap;

public class CategorysAttr {
  private String catClass;// 0：一级分类；1：二级分类；2：三级分类； 
  
  private String catId;// 分类 ID 
  
  private String name;
  
  private String parentId;//父分类 ID
  
  private String state;//1：有效；0：无效； 

public String getCatClass() {
	return catClass;
}

public void setCatClass(String catClass) {
	this.catClass = catClass;
}

public String getCatId() {
	return catId;
}

public void setCatId(String catId) {
	this.catId = catId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getParentId() {
	return parentId;
}

public void setParentId(String parentId) {
	this.parentId = parentId;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}
  
  
}
