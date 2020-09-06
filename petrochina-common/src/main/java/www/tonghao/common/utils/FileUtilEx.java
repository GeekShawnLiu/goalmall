package www.tonghao.common.utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
public class FileUtilEx extends org.apache.commons.io.FileUtils {
	private static String tmpDir = System.getProperty("java.io.tmpdir") + File.separator + "sinopr" + File.separator;
	/**
	 * 转换成临时File对象
	 * @param multipartFile
	 * @return
	 */
	public static File multipartFileToTmpFile(MultipartFile multipartFile){
		File tempFile = new File(tmpDir+"/upload_"+UUID.randomUUID()+".tmp");
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdirs();
		}
		try {
			multipartFile.transferTo(tempFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempFile;
	}
	
	/**
	 * 转换成File对象
	 * @param multipartFile
	 * @return
	 */
	public static File multipartFileToFile(MultipartFile multipartFile){
		File tempFile = new File(tmpDir+"/upload_"+UUID.randomUUID()+"."+FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdirs();
		}
		try {
			multipartFile.transferTo(tempFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempFile;
	}
	
	
	
	/**
	 * 多个文件压缩成一个zip包
	 * @param files
	 * @param zipFile
	 * @throws IOException
	 */
	public static void zipFiles(File[] files,String zipFile) throws IOException{
		zipFiles(Arrays.asList(files), zipFile);
	}
	
	/**
	 * 多个文件压缩成一个zip包
	 * @param list
	 * @param zipFile
	 * @throws IOException
	 */
	public static void zipFiles(List<File> list,String 
			zipFile) throws IOException {
		ZipOutputStream zouts = new ZipOutputStream(new FileOutputStream(
				zipFile));
		for (int i = 0; i < list.size(); i++) {
			File file = list.get(i);
			FileInputStream fin = null;
			ZipEntry entry = null;
			// 创建复制缓冲区
			byte[] buf = new byte[4096];
			int readByte = 0;
			if (file.isFile()) {
				try {
					// 创建一个文件输入流
					fin = new FileInputStream(file);
					// 创建一个ZipEntry
					entry = new ZipEntry(FilenameUtils.getName(file.getName()));
					// 存储信息到压缩文件
					zouts.putNextEntry(entry);
					// 复制字节到压缩文件
					while ((readByte = fin.read(buf)) != -1) {
						zouts.write(buf, 0, readByte);
					}
					zouts.closeEntry();
					fin.close();
				} catch (Exception e) {
					e.getLocalizedMessage();
				}
			}
		}
		zouts.close();
	}
	
	/**
	 * 创建文件，可以包括创建多级文件目录 。
	 * <p>
	 * 根据抽象字串文件名新建文件，若文件的上级目录不存在，则先创建目录，再创建文件，返回新文件. 若文件存在,直接返回.
	 * </p>
	 * 
	 * @param filename
	 *            待创建的文件的抽象文件名称,若为null返回null;若此名称的文件已存在,则直接返回该文件.
	 * @return File 创建的文件
	 * @throws IOException
	 */
	public static File createFile(final String filename) throws IOException {

		if (filename == null) {
			return null;
		}
		else {
			return createFile(new File(filename));
		}
	}
	
	/**
	 * 创建文件，可以包括创建多级文件目录
	 * <p>
	 * 由文件对象创建文件，若文件的上级目录不存在，则先创建目录，再创建文件，返回新文件. 若文件存在,直接返回.
	 * </p>
	 * 
	 * @param file
	 *            待创建的文件
	 * @return File 创建的文件
	 * @throws IOException
	 */
	public static File createFile(final File file) throws IOException {

		if (!file.exists()) {
			createDirectoryRecursively(file.getParent());
			file.createNewFile();
		}
		return file;
	}
	
	/**
	 * 创建文件目录(包括子目录) 支持创建多级文件目录，如“d:/aaa/bbb/ccc”
	 * 
	 * @param directory
	 *            待创建的文件(夹),支持多级路径. 若为文件或null返回false; 若目录已存在则返回true;
	 * @return boolean
	 */
	public static boolean createDirectoryRecursively(String directory) {

		if (directory == null) {
			return false;
		}
		File pathname = new File(directory);
		if (pathname.exists()) {
			return !pathname.isFile();
		}
		else if (!pathname.isAbsolute()) {
			pathname = new File(pathname.getAbsolutePath());
		}
		final String parent = pathname.getParent();
		if ((parent == null) || !createDirectoryRecursively(parent)) {
			return false;
		}
		pathname.mkdir();
		return pathname.exists();
	}
	
	/**
     * 
     *〈简述〉移动文件
     *〈详细描述〉
     * @param srcFile 原文件
     * @param tagetFile 目标文件
     * @return 成功返回true,失败返回false
     */
    public static boolean moveFileExt(File srcFile, File tagetFile){
        return srcFile.renameTo(tagetFile);
    }
    
    /**
     * 文件下载HttpServletRequest|HttpServletResponse
     *
     * @see DownloadUtil
     * @param request   请求
     * @param response  响应
     * @param filepath  文件全路径
     * @param filename  下载文件名
     * @return
     */
    public static boolean downloadFile(HttpServletRequest request, HttpServletResponse response, String filepath, String filename){
        if(StringUtils.isBlank(filepath)){
            System.out.println("filePath must not be empty !");
            return false;
        }
        response.reset();
        String userAgent = request.getHeader("User-Agent");
        InputStream in = null;
        OutputStream out = null;
        try {
            File file = new File(filepath);
            if(StringUtils.isBlank(filename)){
                filename = file.getName();
            }
            if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
            } else {
                filename = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", filename));
            response.addHeader("Content-Length", "" + file.length());
            response.setCharacterEncoding("UTF-8");
            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(response.getOutputStream());
            IOUtils.copy(in, out);
            out.flush();
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
    }
    
    /**
     * 
     * Description: 文件下载
     * 
     * @data 2019年5月9日
     * @param 
     * @return
     */
    public static boolean downloadFile(InputStream in, HttpServletResponse response, String fileName){
        response.reset();
        OutputStream out = null;
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;"+ "filename="+ new String(fileName.getBytes("UTF8"), "ISO8859-1") );  
            response.setCharacterEncoding("UTF-8");
            out = new BufferedOutputStream(response.getOutputStream());
            IOUtils.copy(in, out);
            out.flush();
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
    }
}
