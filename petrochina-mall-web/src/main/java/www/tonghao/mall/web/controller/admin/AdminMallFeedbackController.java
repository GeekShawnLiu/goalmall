package www.tonghao.mall.web.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.PageBean;
import www.tonghao.mall.entity.MallFeedback;
import www.tonghao.mall.service.MallFeedbackService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Api(value="adminMallFeedbackController",description="管理员商城意见反馈api")
@RestController(value="adminMallFeedbackController")
@RequestMapping(value="/admin/mall_feedback")
public class AdminMallFeedbackController {
	
	@Autowired
	private MallFeedbackService mallFeedbackService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<MallFeedback> getPage(@ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		List<MallFeedback> list = mallFeedbackService.findListByEntity(null);
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
