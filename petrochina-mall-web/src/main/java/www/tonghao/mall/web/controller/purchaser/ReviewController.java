package www.tonghao.mall.web.controller.purchaser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaEqualsUtil;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.OrderItemsService;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.Review;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.ReviewService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 评价
 * @author developer001
 *
 */
@Api(value="purchaserReviewController",description="采购人评价")
@RestController
@RequestMapping("/purchaser/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderItemsService orderItemsService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="sn",value="订单号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="productId",value="商品Id",required=false,dataType="long",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Review> getPage(@ModelAttribute PageBean page
			,String sn,Long productId, HttpServletRequest request){
		PageHelper.startPage(page.getPage(), page.getRows());
		
		Users user = UserUtil.getUser(request);
		
		Example example=new Example(Review.class);
		Map<String, Object> mapeq=new HashMap<String, Object>();
		Criteria createCriteria = example.createCriteria();
		mapeq.put("userId", user.getId());
		CriteriaEqualsUtil.criteriaEquals(createCriteria, mapeq);
		List<Review> list = reviewService.selectByExample(example);
		
		return new PageInfo<Review>(list);
	}
	
	@ApiOperation(value="评价订单项",notes="获取评价订单项api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="订单项ID",required=true,dataType="long"),
	})
	@GetMapping(value="/getOrderitem")
	public OrderItems getOrderItem(Long id, HttpServletRequest request){
		OrderItems orderItem = orderItemsService.findById(id);
		Orders order = orderItem.getOrder();
		Users user = UserUtil.getUser(request);
		if(order.getUserId().equals(user.getId())){
			return orderItem;
		}
		return null;
	}
	
	@ApiOperation(value="提交评价",notes="修改或添加api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="orderItemId",value="订单项ID",required=true,dataType="long"),
		@ApiImplicitParam(name="qualityGrade",value="质量评分",required=true,dataType="string"),
		@ApiImplicitParam(name="serviceGrade",value="服务评分",required=true,dataType="string"),
		@ApiImplicitParam(name="deliveryGrade",value="送货评分",required=true,dataType="string"),
		@ApiImplicitParam(name="priceGrade",value="价格评分",required=true,dataType="string"),
	})
	@PostMapping(value="/commit")
	public Map<String,Object> commit(Review review, HttpServletRequest request){
		if(review.getOrderItemId()==null){
			return ResultUtil.error("订单项不能为空");
		}
		if(review.getQualityGrade()==null){
			return ResultUtil.error("质量评价不能为空");
		}
		if(review.getServiceGrade()==null){
			return ResultUtil.error("服务评价不能为空");
		}
		if(review.getDeliveryGrade()==null){
			return ResultUtil.error("送货评价不能为空");
		}
		if(review.getPriceGrade()==null){
			return ResultUtil.error("价格评价不能为空");
		}
		
		Users user = UserUtil.getUser(request);
		OrderItems orderItem = orderItemsService.findById(review.getOrderItemId());
		Orders order = ordersService.findById(orderItem.getOrderId());
		if(order==null||!order.getUserId().equals(user.getId())){
			return ResultUtil.error("订单不存在");
		}
		
		Example example=new Example(Review.class);
		Map<String, Object> mapeq=new HashMap<String, Object>();
		Criteria createCriteria = example.createCriteria();
		mapeq.put("userId", user.getId());
		mapeq.put("orderItemId", review.getOrderItemId());
		CriteriaEqualsUtil.criteriaEquals(createCriteria, mapeq);
		long count = reviewService.selectCountByExample(example);
		if(count>0){
			return ResultUtil.error("商品已评价");
		}
		
		review.setUserId(user.getId());
		review.setProductId(orderItem.getProductId());
		review.setProductName(orderItem.getName());
		review.setProductPic(orderItem.getThumbnail());
		review.setOrderCreatedAt(order.getCreatedAt());
		review.setOrderSn(order.getSn());
		review.setIsAnonymous(review.getIsAnonymous()!=null?review.getIsAnonymous():false);
		reviewService.save(review);
		return ResultUtil.success("");
	}
}
