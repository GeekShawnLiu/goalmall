package www.tonghao.mall.api.standard.attwrap;

public class Attributes {
	
	/**
	 * 标准参数项的 ID
	 */
   private String attributeID;
   
   /**
	 * 贵方参数项的名称
	 */
   private String attributeName;
   
   /**
	 * 标准参数值的 ID
	 */
   private String valueID;
   
   /**
	 * 贵方参数值
	 */
   private String value;

public String getAttributeID() {
	return attributeID;
}

public void setAttributeID(String attributeID) {
	this.attributeID = attributeID;
}

public String getAttributeName() {
	return attributeName;
}

public void setAttributeName(String attributeName) {
	this.attributeName = attributeName;
}

public String getValueID() {
	return valueID;
}

public void setValueID(String valueID) {
	this.valueID = valueID;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}
   
   
   
}
