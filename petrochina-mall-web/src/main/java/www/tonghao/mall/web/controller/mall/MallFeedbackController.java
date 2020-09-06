package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.ValidatorUtil;
import www.tonghao.mall.entity.MallFeedback;
import www.tonghao.mall.service.MallFeedbackService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Api(value="mallMallFeedbackController",description="商城意见反馈api")
@RestController(value="mallMallFeedbackController")
@RequestMapping(value="/m_feedback")
public class MallFeedbackController {
	
	@Autowired
	private MallFeedbackService mallFeedbackService;
	
	@ApiOperation(value="保存")
	@PostMapping(value = "/save")
	public Map<String,Object> save(MallFeedback entity, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user==null){
			return ResultUtil.error("请先登录");
		}
		if(!ValidatorUtil.isValidLengthRange(entity.getDescription(), 1,125)){
			return ResultUtil.error("描述长度在1~255之间");
		}
		if(!ValidatorUtil.isValidLengthRange(entity.getContent(), 1,200)){
			return ResultUtil.error("内容长度在1~800之间");
		}
		if(StringUtils.isNotEmpty(entity.getCreater())){
			if(!ValidatorUtil.isValidLengthRange(entity.getCreater(), 1,20)){
				return ResultUtil.error("建议人长度在1~20之间");
			}
		}else{
			entity.setCreater(user.getRealName());
		}
		
		if(!ValidatorUtil.isValidLengthRange(entity.getContactPhone(), 1,25)){
			return ResultUtil.error("联系电话长度在1~25之间");
		}
		
		if(!ValidatorUtil.isMoblie(entity.getContactPhone())&&!ValidatorUtil.isPhone(entity.getContactPhone())){
			return ResultUtil.error("请输入正确的电话或手机号码");
		}
		entity.setCreaterUid(user.getId());
		entity.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		mallFeedbackService.save(entity);
		return ResultUtil.success("");
	}
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<MallFeedback> getPage(@ModelAttribute PageBean page, HttpServletRequest request){
		PageHelper.startPage(page.getPage(), page.getRows());
		Users user = UserUtil.getUser(request);
		List<MallFeedback> list = new ArrayList<MallFeedback>();
		if(user != null){
			MallFeedback mallFeedback = new MallFeedback();
			mallFeedback.setCreaterUid(user.getId());
			list = mallFeedbackService.findListByEntity(mallFeedback);
		}
		return new PageInfo<MallFeedback>(list);
	}
	
	@ApiOperation(value="查询明细",notes="查询明细api")
	@ApiImplicitParams({
        @ApiImplicitParam(name="id",value="ID",required=true,dataType="int",paramType="query"),
    })
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public MallFeedback detail(Long id){
		return mallFeedbackService.findById(id);
	}
}
