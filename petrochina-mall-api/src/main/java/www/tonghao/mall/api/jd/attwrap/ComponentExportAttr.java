package www.tonghao.mall.api.jd.attwrap;

public class ComponentExportAttr {

	/**
	 * 服务类型码：退货(10)、换货(20)、维修(30)
	 */
	private String code;
	
	/**
	 * 服务类型名称：退货、换货、维修
	 */
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ComponentExportAttr() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentExportAttr(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	
}
