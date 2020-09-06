package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.platform.entity.Attachment;
import www.tonghao.platform.enums.AttachType;
import www.tonghao.platform.service.AttachmentService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  
* <p>Title: AttchmentController</p>  
* <p>Description:附件管理 </p>  
* @author YML  
* @date 2019年1月21日  
*/ 
@Api(value="attchmentController",description="附件管理api")
@RestController
@RequestMapping("/attchmentController")
public class AttchmentController {
	
	@Autowired
	private AttachmentService attachmentService;
	
	/**  
	 * <p>Title: list</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param article
	 * @param page
	 * @return  
	 */  
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<Attachment> list(String fileType, String fileName, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example = new Example(Attachment.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		if (StringUtils.isNotBlank(fileType)) {
			createCriteria.andEqualTo("fileType", fileType);
		}
		Map<String, Object> maplike = new HashMap<String, Object>();
		maplike.put("fileName", fileName);
		CriteriaLikeUtil.criteriaLike(createCriteria, maplike);
		List<Attachment> list = attachmentService.selectByExample(example);
		return new PageInfo<Attachment>(list);
	}
	
	/**  
	 * <p>Title: getAttachType</p>
	 * <p>Description: 获取附件所有类型</p>  
	 * @author YML 
	 * @return 
	 */
	@ApiOperation(value="获取附件所有类型",notes="获取附件所有类型api")
	@RequestMapping(value="/getAttachType",method=RequestMethod.GET)
	public List<String> getAttachType(){
		List<String> types = new ArrayList<String>();
		AttachType[] attachTypes = AttachType.values();
		for (AttachType attachType : attachTypes) {
			String str = attachType.getTypeStr();
			String name = attachType.getTypeName();
			types.add(str + "@" + name);
		}
		return types;
	}
	
	/**  
	 * <p>Title: save</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param attachment
	 * @return  
	 */  
	@ApiOperation(value="添加数据",notes="添加数据api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String,Object> save(@RequestBody Attachment attachment, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if (user != null) {
			attachment.setUserId(user.getId());
			attachment.setIsDelete(0);
			attachment.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			attachment.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			int result = attachmentService.saveSelective(attachment);
			return ResultUtil.resultMessage(result, "");
		}else {
			return ResultUtil.resultMessage(0, "未检测到登录");
		}
	}
	
	/**  
	 * <p>Title: update</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param attachment
	 * @return  
	 */  
	@ApiOperation(value="更新数据",notes="更新数据api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String,Object> update(@RequestBody Attachment attachment){
		attachment.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		int result = attachmentService.updateNotNull(attachment);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: fakeDelete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="逻辑删除数据",notes="逻辑删除数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/fakeDelete",method=RequestMethod.POST)
	public Map<String, Object> fakeDelete(Long id){
		Attachment attachment = new Attachment();
		attachment.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		attachment.setIsDelete(1);
		attachment.setId(id);
		int result = attachmentService.updateNotNull(attachment);
		return ResultUtil.resultMessage(result, "");
	}
	
	
	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="删除数据",notes="删除数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int result = attachmentService.delete(id);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: getById</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="根据id查询",notes="查询单条api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Attachment getById(Long id){
		Attachment attachment = attachmentService.selectByKey(id);
		return attachment;
	}
	
}
