package www.tonghao.service.common.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import www.tonghao.common.warpper.FileInfo;
import www.tonghao.common.warpper.FileInfo.FileType;
import www.tonghao.common.warpper.FileInfo.OrderType;

/**
 * 文件service接口
 * @author developer001
 *
 */
public interface FileService {
	
	/**
	 * 文件上传(异步)
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 访问URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile);

	/**
	 * 文件上传
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @param relativePath
	 *            是否返回相对路径
	 * @return 访问URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile, boolean relativePath);
	
	/**
	 * 文件上传
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @param relativePath
	 *            是否返回相对路径
	 * @return 访问URL
	 */
	String upload(FileType fileType, MultipartFile multipartFile, String uploadPath,boolean relativePath);
	
	/**
	 * 文件浏览
	 * 
	 * @param path
	 *            浏览路径
	 * @param fileType
	 *            文件类型
	 * @param orderType
	 *            排序类型
	 * @return 文件信息
	 */
	List<FileInfo> browser(String path, FileType fileType, OrderType orderType);

	/**  
	 * <p>Title: downloadFile</p>  
	 * <p>Description: 下载附件</p>  
	 * @author Yml  
	 * @param response
	 * @param path  
	 * @param fileName 
	 */  
	void downloadFile(HttpServletResponse response, String path, String fileName);
}
