package www.tonghao.common.enums;

/**  

* <p>Title: RoleCode</p>  

* <p>Description: 内置角色编码定义</p>  

* @author Yml  

* @date 2018年11月23日  

*/  
public enum RoleCode {
	
	ADMIN_R("ADMIN_R11", "超级管理员"),
	PLATFORM_R("PLATFORM_R", "平台管理员"),
	SUPERVISE_R("SUPERVISE_R", "监管人员"),
	PURCHASER_R("PURCHASER_R", "采购人"),
	SUPPLIER_R("SUPPLIER_R", "供应商"),
	MALL_EMALL_R("MALL_EMALL_R", "超市电商"),
	MALL_MANUFACTURER_R("MALL_MANUFACTURER_R", "超市厂商"),
	MALL_AGENT_R("MALL_AGENT_R", "超市代理商"),
	BATCH_MANUFACTURER_R("BATCH_MANUFACTURER_R", "批量厂商"),
	BATCH_AGENT_R("BATCH_AGENT_R", "批量代理商"),
	FIX_SUPPLIER_R("FIX_SUPPLIER_R", "定点供应商");
	
	// 成员变量  
    private String roleCode;  
    private String roleName;  
    
    // 构造方法  
    private RoleCode(String roleCode, String roleName) {  
        this.roleCode = roleCode;  
        this.roleName = roleName;  
    }
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	} 
    
	public static void main(String[] args) {
		System.out.println(RoleCode.SUPPLIER_R.roleCode);
	}
}
