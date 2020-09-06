package www.tonghao.common.utils;

import java.io.Serializable;

/**
 * 身份
 * @author developer001
 *
 */
public class Principal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private Long id;

	/** 用户名 */
	private String username;
	
	/** 用户类型：1：采购人，2：集采机构，3：监管机构，4：供应商:5：专家，6：采购中心 */
	private Integer type;
	
	/**供应商ID*/
	private Long supplierId;
	
	/* 真实姓名 */
	private String realname;
	
	/*单位名称*/
	private String depName;
	
	/**
	 * 基层单位code
	 */
	private String departmentCode;
	
	/** 是否后台管理员(能进入后台) **/
	private boolean isAdmin;
	
	 /** 是否超级管理员 **/
	private boolean isSuperAdmin;
	
	 /** 是否平台管理员 **/
	private boolean isPlatformAdmin;
	
	/** 地址ID */
	private Long areaId;
	
	/** 地址名称 */
	private String areaName;
	
	public Principal(){}
	
	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 * @param realname
	 *            真实姓名
	 */
	public Principal(Long id,String username,String realname){
		this.id = id;
		this.username = username;
		this.realname = realname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	
	/**
	 * 是否允许直购
	 * @return
	 */
	public boolean isAllowPurchase(){
		if(getType()!=null&&getType()==1){
			return true;
		}
		return false;
	}

	public boolean isPlatformAdmin() {
		return isPlatformAdmin;
	}

	public void setPlatformAdmin(boolean isPlatformAdmin) {
		this.isPlatformAdmin = isPlatformAdmin;
	}
	
}
