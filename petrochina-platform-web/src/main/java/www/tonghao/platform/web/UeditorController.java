package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import www.tonghao.common.Constant;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.common.warpper.FileInfo;
import www.tonghao.common.warpper.FileInfo.FileType;
import www.tonghao.service.common.service.FileService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * ueditor服务器端接口
 * @author Administrator
 */
@Api(value="ueditorController",description="ueditor服务器端接口")
@RestController
@RequestMapping("/ueditor")
public class UeditorController{
	
	private static final String UPLOAD_PATH_PREFIX = "/ueditor";
	// 状态
	private static final String STATE = "state";
	// URL
	private static final String URL = "url";
	// TITLE
	private static final String TITLE = "title";
	// 文件原名
	private static final String ORIGINAL = "original";
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 上传
	 * @param fileType 上传文件类型
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@ApiOperation(value="ueditor文件上传",notes="ueditor文件上传api")
	/*
	@ApiImplicitParams({
		@ApiImplicitParam(name="fileType",value="上传文件类型",required=true,dataType="enum",paramType="enum"),
	})
	*/
	@RequestMapping(value = "/upload")
	public Map<String,String> upload(@RequestParam(value = "Type", required = false)FileType fileType
			,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFileMap().entrySet()
				.iterator().next().getValue();
		
		String filename = FilenameUtils.getName(multipartFile
				.getOriginalFilename());
		
		String uploadPath = null;
		if(fileType==FileType.image){
			uploadPath = UPLOAD_PATH_PREFIX + Constant.IMAGE_UPLOAD_PATH;
		}else{
			uploadPath = UPLOAD_PATH_PREFIX + Constant.FILE_UPLOAD_PATH;
		}
		
		String ctxFileUrl = fileService.upload(fileType, multipartFile,uploadPath,false);
		
		Map<String,String> map = Maps.newHashMap();
		map.put(STATE, "SUCCESS");
		map.put(URL, ctxFileUrl);
		map.put(TITLE, filename);
		map.put(ORIGINAL, filename);
		return map;
	}

	@RequestMapping(value = "/listimage")
	public String listimage(int start,int size,HttpServletRequest request,HttpServletResponse response)
					throws Exception {
		
		List<FileInfo> fileInfos = fileService.browser(UPLOAD_PATH_PREFIX+Constant.IMAGE_UPLOAD_PATH, FileType.image, null);
		List<Map<String,String>> urls = Lists.newArrayList();
		fileInfos.forEach(e -> {
			Map<String,String> urlsMap = Maps.newHashMap();
			urlsMap.put(URL, e.getUrl());
			urls.add(urlsMap);
		});
		Map<String	,Object> map = Maps.newHashMap();
		map.put(STATE, "SUCCESS");
		map.put("list", urls);
		map.put("start", start);
		map.put("total", fileInfos.size());
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		String callbackName = request.getParameter("callback");
		if (callbackName != null) {
			String res = callbackName+"("+toUnicode(JsonUtil.toJson(map))+");";
			return res;
		} 
		
		return JsonUtil.toJson(map);
	}
	
	public String toUnicode (String input) {
		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();
		
		for ( char ch : chars ) {
			
			if ( ch < 256 ) {
				builder.append( ch );
			} else {
				builder.append( "\\u" +  Integer.toHexString( ch& 0xffff ) );
			}
			
		}
		
		return builder.toString();
		
	}
	
	/**
	 * callback参数验证
	 */
	public boolean validCallbackName ( String name ) {
		if (name.matches( "^[a-zA-Z_]+[\\w0-9_]*$" ) ) {
			return true;
		}
		return false;
	}
	
}
