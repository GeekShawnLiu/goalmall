package www.tonghao.platform.enums;

/**  
* <p>Title: AttachType</p>  
* <p>Description: 附件类型</p>  
* @author YML  
* @date 2019年1月21日  
*/ 
public enum AttachType {
	
	legal_service_fix("legal_service_fix", "法律服务定点供应商报价明细表"),//法律服务定点供应商报价明细表
	
	car_maint_fix("car_maint_fix", "公务车维保加价单"),
	
	contract_seal("contract_seal", "合同印章");
	
	private String typeStr;//附件类型（存储）
	
	private String typeName;//附件类型名称（展示）

	// 构造方法  
    private AttachType(String typeStr, String typeName) {  
        this.typeStr = typeStr;  
        this.typeName = typeName;  
    }
	
	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
