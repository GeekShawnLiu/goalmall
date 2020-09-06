package www.tonghao.service.common.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import www.tonghao.common.Constant;
import www.tonghao.common.utils.FreemarkerUtils;
import www.tonghao.common.warpper.FileInfo;
import www.tonghao.common.warpper.FileInfo.FileType;
import www.tonghao.common.warpper.FileInfo.OrderType;
import www.tonghao.service.common.service.FileService;

import com.google.common.collect.Lists;

import freemarker.template.TemplateException;

/**
 * 文件服务
 * @author developer001
 *
 */
@Service("fileService")
public class FileServiceImpl implements FileService{
	@Value("${imgSiteUrl}")
	private String imgSiteUrl;
	
	@Value("${uploadBasePath}")
	private String uploadBasePath;
	
	@Override
	public String upload(FileType fileType, MultipartFile multipartFile) {
		return upload(fileType, multipartFile, false);
	}
	
	public String upload(FileType fileType, MultipartFile multipartFile, boolean relativePath) {
		return upload(fileType, multipartFile, null, relativePath);
	}

	@Override
	public String upload(FileType fileType, MultipartFile multipartFile, String uploadPath, boolean relativePath) {
		if (multipartFile == null) {
			return null;
		}
		if(StringUtils.isBlank(uploadPath)){
			if (fileType == FileType.image) {
				uploadPath = Constant.IMAGE_UPLOAD_PATH;
			} else {
				uploadPath = Constant.FILE_UPLOAD_PATH;
			}
		}
		
		try {
			String path = FreemarkerUtils.process(uploadPath, null)+ UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			String destPath = uploadBasePath + path;
			File destFile = new File(destPath);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			FileUtils.writeByteArrayToFile(destFile, multipartFile.getBytes());
			if(relativePath){
				return path;
			}
			String urlPath=imgSiteUrl+path;
			return urlPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<FileInfo> browser(String path, FileType fileType, OrderType orderType) {
		if (path != null) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (!path.endsWith("/")) {
				path += "/";
			}
		} else {
			path = "/";
		}
		
		String browsePath = null;
		try {
			browsePath = FreemarkerUtils.process(path, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		List<FileInfo> fileInfos = Lists.newArrayList();
		if (browsePath.indexOf("..") >= 0) {
			return fileInfos;
		}
		fileInfos = browser(browsePath);
		if (orderType == OrderType.size) {
			Collections.sort(fileInfos, new SizeComparator());
		} else if (orderType == OrderType.type) {
			Collections.sort(fileInfos, new TypeComparator());
		} else {
			Collections.sort(fileInfos, new NameComparator());
		}
		return fileInfos;
	}
	
	public List<FileInfo> browser(String path) {
		List<FileInfo> fileInfos =Lists.newArrayList();
		File directory = new File(Constant.UPLOAD_BASE_PATH+path);
		if (directory.exists() && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(file.getName());
				fileInfo.setUrl(Constant.imgSiteUrl + path + file.getName());
				fileInfo.setIsDirectory(file.isDirectory());
				fileInfo.setSize(file.length());
				fileInfo.setLastModified(new Date(file.lastModified()));
				fileInfos.add(fileInfo);
			}
		}
		return fileInfos;
	}
	
	private class NameComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getName(), fileInfos2.getName()).toComparison();
		}
	}

	private class SizeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getSize(), fileInfos2.getSize()).toComparison();
		}
	}

	private class TypeComparator implements Comparator<FileInfo> {
		public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
			return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(FilenameUtils.getExtension(fileInfos1.getName()), FilenameUtils.getExtension(fileInfos2.getName())).toComparison();
		}
	}

	@Override
	public void downloadFile(HttpServletResponse response, String path, String fileName) {
		if (StringUtils.isNotEmpty(path)) {
			String realPath = path.replace(Constant.imgSiteUrl, Constant.UPLOAD_BASE_PATH);
			// 以流的形式下载文件。
	        File realFile = new File(realPath);
			// 清空response
			response.reset();
			InputStream in = null;
			OutputStream toClient = null;
			try {
				if (StringUtils.isEmpty(fileName)) {
					fileName = realPath.substring(realPath.lastIndexOf("/") + 1);
				}
				response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
				// 以流的形式下载文件
				in = new BufferedInputStream(new FileInputStream(realFile));
				byte[] buffer = new byte[in.available()];
				in.read(buffer);
				toClient = new BufferedOutputStream(response.getOutputStream());
				toClient.write(buffer);
				toClient.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					toClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
}
