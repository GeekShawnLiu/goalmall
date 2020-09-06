	package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import www.tonghao.common.Constant;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.warpper.FileInfo.FileType;
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.service.common.service.FileService;

@Api(value="fileController",description="附件api")
@RestController
@RequestMapping("/fileController")
public class FileController {
	
	private static final String ARTICLE_UPLOAD_PATH_PREFIX = "/article";
	
	private static final String PRODUCT_UPLOAD_PATH_PREFIX = "/product";
	
	@Autowired
	private FileService fileService;
	
	/**  
	 * <p>Title: upload</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param request
	 * @param response
	 * @param pathPrefix
	 * @return
	 * @throws Exception  
	 */  
	@ApiOperation(value="文件上传",notes="文件上传api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pathPrefix",value="文件存储前缀,如：/article, /product",required=false,dataType="String",paramType="query"),
	})
	@RequestMapping(value = "/upload")
	public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response, @Param("pathPrefix") String pathPrefix)
			throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFileMap().entrySet()
				.iterator().next().getValue();
		
		String filename = FilenameUtils.getName(multipartFile
				.getOriginalFilename());
		String uploadPath = Constant.IMAGE_UPLOAD_PATH;
		//根据图片内容判断是否为图片文件
		InputStream inputStream = multipartFile.getInputStream();
		BufferedImage bi = ImageIO.read(inputStream);
		if(bi == null){
			//不是图片
			uploadPath = Constant.FILE_UPLOAD_PATH;
		}
		String destPath = null;
		if (StringUtils.isNotBlank(pathPrefix)) {
			destPath = pathPrefix + uploadPath;
		}else {
			destPath = uploadPath;
		}
		
		String ctxFileUrl = fileService.upload(FileType.file, multipartFile, destPath, false);
		if (StringUtils.isNotEmpty(ctxFileUrl)) {
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("fileName", filename);
			data.put("path", ctxFileUrl);
			data.put("fileSize", multipartFile.getSize());
			return ResultUtil.resultMessage(HttpResponseCode.OK, " ", data);
		}else {
			return ResultUtil.resultMessage(HttpResponseCode.BAD_REQUEST, filename + "：上传失败！", null);
		}
	}
	
	/**  
	 * <p>Title: downloadFile</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param request
	 * @param response
	 * @param path
	 * @param fileName  
	 */  
	@ApiOperation(value="文件下载",notes="文件下载api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="path",value="文件访问路径",required=true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="fileName",value="文件名称",required=false,dataType="String",paramType="query"),
	})
	@RequestMapping(value = "/downloadFile", method=RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String path, String fileName){
		fileService.downloadFile(response, path, fileName);
	}
}
