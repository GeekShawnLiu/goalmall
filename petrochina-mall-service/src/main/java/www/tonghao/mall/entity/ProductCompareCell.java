package www.tonghao.mall.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品比较单元格
 * @author developer001
 *
 */
@ApiModel(value="商品比较单元格")
public class ProductCompareCell implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductCompareCell(){}
	
	public ProductCompareCell(String name,String value){
		this.name = name;
		this.value = value;
	}
	
	public ProductCompareCell(String name,String value,int type){
		this.name = name;
		this.value = value;
		this.type = type;
	}
	
	@ApiModelProperty("列名称")
	private String name;
	
	@ApiModelProperty("列值")
	private String value;
	
	/**
	 * 0：普通文本 ； 1：图片路径
	 */
	@ApiModelProperty("类型:0：普通文本 ； 1：图片路径")
	private int type;
	
	@ApiModelProperty("url链接")
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
