package www.tonghao.common.warpper;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件信息
 * @author developer001
 *
 */
public class FileInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 文件类型
	 */
	public enum FileType {

		/** 图片 */
		image,

		/** 文件 */
		file
	}

	/**
	 * 排序类型
	 */
	public enum OrderType {

		/** 名称 */
		name,

		/** 大小 */
		size,

		/** 类型 */
		type
	}

	/** 名称 */
	private String name;

	/** URL */
	private String url;
	
	/** 是否为目录 */
	private Boolean isDirectory;

	/** 大小 */
	private Long size;

	/** 最后修改日期 */
	private Date lastModified;

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取URL
	 * 
	 * @return URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置URL
	 * 
	 * @return URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsDirectory() {
		return isDirectory;
	}

	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
