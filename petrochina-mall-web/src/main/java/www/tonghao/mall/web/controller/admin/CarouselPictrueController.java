package www.tonghao.mall.web.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.mall.entity.CarouselPictrue;
import www.tonghao.mall.service.CarouselPictrueService;

/**
 * @Description:轮播图
 * @date 2019年4月24日
 */
@Api(value="carouselPictrueController", description="轮播图")
@RestController
@RequestMapping("/carouselPictrue")
public class CarouselPictrueController {
	
	@Autowired
	private CarouselPictrueService carouselPictrueService;
	
	/**
	 * @Description:列表
	 * @date 2019年4月24日
	 */
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="activityName",value="名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="type",value="类型",required=false,dataType="int",paramType="query")
	})
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public PageInfo<CarouselPictrue> list(@ModelAttribute PageBean page, String activityName, Integer type){
		CarouselPictrue carouselPictrue = new CarouselPictrue();
		if(StringUtils.isNotBlank(activityName)){
			carouselPictrue.setActivityName(activityName);
		}
		carouselPictrue.setType(type);
		PageInfo<CarouselPictrue> selectList = carouselPictrueService.selectList(carouselPictrue);
		return selectList;
	}

	/**
	 * @Description:新增
	 * @date 2019年4月24日
	 */
	@ApiOperation(value="新增")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Map<String, Object> add(@RequestBody CarouselPictrue carouselPictrue){
		if(carouselPictrue !=null && carouselPictrue.getRank() !=null){
			String selectMaxRank = carouselPictrueService.selectMaxRank(null, carouselPictrue.getRank());
			if(selectMaxRank !=null){
				return ResultUtil.resultMessage("error", selectMaxRank);
			}
		}
		carouselPictrue.setIsDelete(0);
		carouselPictrue.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		int result = carouselPictrueService.save(carouselPictrue);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**
	 * @Description:修改
	 * @date 2019年4月24日
	 */
	@ApiOperation(value="修改")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public Map<String, Object> update(@RequestBody CarouselPictrue carouselPictrue){
		if(carouselPictrue !=null && carouselPictrue.getRank() !=null){
			String selectMaxRank = carouselPictrueService.selectMaxRank(carouselPictrue.getId(), carouselPictrue.getRank());
			if(selectMaxRank !=null){
				return ResultUtil.resultMessage("error", selectMaxRank);
			}
		}
		
		carouselPictrue.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = carouselPictrueService.updateNotNull(carouselPictrue);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**
	 * @Description:id获取
	 * @date 2019年4月28日
	 */
	@ApiOperation(value="id获取")
	@RequestMapping(value="/getCarouselPictrue", method=RequestMethod.GET)
	public CarouselPictrue getCarouselPictrue(Long id){
		CarouselPictrue carouselPictrue = carouselPictrueService.selectByKey(id);
		return carouselPictrue;
	}
	
	/**
	 * @Description:删除
	 * @date 2019年4月24日
	 */
	@ApiOperation(value="删除")
	@RequestMapping(value="/del", method=RequestMethod.DELETE)
	public Map<String, Object> del(Long id){
		CarouselPictrue carouselPictrue = new CarouselPictrue();
		carouselPictrue.setId(id);
		carouselPictrue.setIsDelete(1);
		carouselPictrue.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = carouselPictrueService.updateNotNull(carouselPictrue);
		return ResultUtil.resultMessage(result, "");
	}
}
