package www.tonghao.common.warpper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 树节点模型
 * @author developer001
 *
 */
@ApiModel(value="树模型对象")
public class TreeNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@ApiModelProperty(value="主键ID")
	private Long id;
	
	/**
	 * 名称
	 */
	@ApiModelProperty(value="名称")
	private String name;
	
	/**
	 * 排序
	 */
	@ApiModelProperty(value="排序")
	private Float sort;
	
	/**
	 * 父级ID
	 */
	@ApiModelProperty(value="父级ID")
	private Long parentId;
	
	/**
	 * 层级数
	 */
	@ApiModelProperty(value="层级数")
	private int grade = 0;
	
	/**
	 * 父级节点
	 */
	@ApiModelProperty(value="父级节点")
	private TreeNode parent;
	
	/**
	 * 子节点
	 */
	@ApiModelProperty(value="子节点")
	private List<TreeNode> children;
	
	/**
	 * 图标
	 */
	@ApiModelProperty(value="图标")
	private String icon;
	
	/**
	 * 链接地址
	 */
	@ApiModelProperty(value="链接地址")
	private String linkUrl;
	
	/**
	 * 拓展字符串
	 */
	@ApiModelProperty(value="拓展字符串")
	private String extStr;
	
	/**
	 * 是否存在子节点
	 */
	private boolean isExistChildren = false;
	
	/**
	 * 拓展对象
	 */
	@ApiModelProperty(value="拓展对象")
	private Object extObj;
	
	@ApiModelProperty(value="h5 品目图片")
	private String mobileImg;
	
    @ApiModelProperty(value="h5 品目两倍图")
    private String imgFormat;
    
    /**
     * 品目展示平台
     */
    @ApiModelProperty(value="一级品目展示平台")
    private String displayPlatform;
	
	public TreeNode(){}
	public TreeNode(Long id,Long parentId,String name){
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}
	public TreeNode(Long id,Long parentId,String name,Float sort){
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.sort = sort;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getSort() {
		return sort;
	}
	public void setSort(Float sort) {
		this.sort = sort;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public String getExtStr() {
		return extStr;
	}
	public void setExtStr(String extStr) {
		this.extStr = extStr;
	}
	public Object getExtObj() {
		return extObj;
	}
	public void setExtObj(Object extObj) {
		this.extObj = extObj;
	}
	public boolean isExistChildren() {
		return isExistChildren;
	}
	public void setExistChildren(boolean isExistChildren) {
		this.isExistChildren = isExistChildren;
	}
	public String getMobileImg() {
		return mobileImg;
	}
	public void setMobileImg(String mobileImg) {
		this.mobileImg = mobileImg;
	}
	public String getImgFormat() {
		return imgFormat;
	}
	public void setImgFormat(String imgFormat) {
		this.imgFormat = imgFormat;
	}
	public String getDisplayPlatform() {
		return displayPlatform;
	}
	public void setDisplayPlatform(String displayPlatform) {
		this.displayPlatform = displayPlatform;
	}
}
