package www.tonghao.mall.web.controller.purchaser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.entity.IntegralUser;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.IntegralUserService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageInfo;

/**
 * @Description:我的积分
 * @date 2019年5月5日
 */
@Api(value="myIntegralController", description="我的积分")
@RestController
@RequestMapping("/myIntegral")
public class MyIntegralController {
	
	@Autowired
	private IntegralUserService integralUserService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPage", method=RequestMethod.GET)
	public PageInfo<IntegralUser> getPageMyIntegral(@ModelAttribute PageBean page, HttpServletRequest request){
		PageInfo<IntegralUser> integralUserList = null;
		Users user = UserUtil.getUser(request);
		if(user !=null){
			integralUserList = integralUserService.selectIntegralUser(page, user.getId());
		}
		return integralUserList;
	}
	
	/**
	 * @Description:手机端我的积分
	 * @date 2019年5月15日
	 */
	@ApiOperation(value="手机端我的积分")
	@RequestMapping(value="/getMyIntegral", method=RequestMethod.GET)
	public List<IntegralUser> getMyIntegral(HttpServletRequest request){
		List<IntegralUser> integralUserList = new ArrayList<IntegralUser>();
		Users user = UserUtil.getUser(request);
		if(user !=null){
			integralUserList = integralUserService.selectIntegralUser(user.getId());
		}
		return integralUserList;
	}
	
	/**
	 * @Description:查询个人积分信息
	 * @date 2019年5月15日
	 */
	@ApiOperation(value="查询个人积分信息")
	@RequestMapping(value="/getUserIntegralInfo", method=RequestMethod.GET)
	public IntegralUser getUserIntegralInfo(Long id){
		IntegralUser integralUser = new IntegralUser();
		if(id !=null){
			integralUser = integralUserService.getUserIntegralInfo(id);
		}
		return integralUser;
	}
	
	@ApiOperation(value="个人可用积分总额")
	@GetMapping(value = "/integralTotal")
	public BigDecimal integralTotal(HttpServletRequest request){
		List<IntegralUser> integralUserList = new ArrayList<IntegralUser>();
		Users user = UserUtil.getUser(request);
		BigDecimal total = new BigDecimal(0);
		if(user !=null){
			integralUserList = integralUserService.selectIntegralUser(user.getId());
			for (IntegralUser integralUser : integralUserList) {
				if(integralUser.getIsOffline() == 0){
					int compareDate = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), integralUser.getActivityEndAt(), "yyyy-MM-dd HH:mm:ss");
					int compareDate2 = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), integralUser.getActivityOnlineAt(), "yyyy-MM-dd HH:mm:ss");
					int compareDate3 = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), integralUser.getActivityStartAt(), "yyyy-MM-dd HH:mm:ss");
					//活动开始时间 /活动上线时间 > 现在时间  < 活动结束时间，
					if(compareDate == -1 && compareDate2 == 1 && compareDate3 == 1){
						total = total.add(integralUser.getBalance());
					}
				}
			}
		}
		return total;
	}
}
