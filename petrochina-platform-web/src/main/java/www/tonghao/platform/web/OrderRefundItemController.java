package www.tonghao.platform.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.constant.SmSConstant;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.HttpClient;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.entity.MeasurementUnit;
import www.tonghao.service.common.entity.JobOrders;
import www.tonghao.service.common.entity.OrderRefundItem;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.JobOrdersMapper;
import www.tonghao.service.common.service.OrderRefundItemService;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.utils.UserUtil;

@RestController
@RequestMapping("/orderRefundItem")
public class OrderRefundItemController {

	@Autowired
	private OrderRefundItemService orderRefundItemService;
	@Autowired
	private JobOrdersMapper jobOrdersMapper;
	@Autowired
	private UsersService usersService;
	
	
	@Value("${webSiteAddress}")
	private String webSiteAddress;
	@ApiOperation(value="分页查询列表",notes="获取列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="orderSn",value="订单号",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="userName",value="退款人",required=false,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<OrderRefundItem> list(@ModelAttribute PageBean page,String userName,String orderSn){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("orderSn", orderSn);
		map.put("userName", userName);
		List<OrderRefundItem> orderFundItem = orderRefundItemService.getOrderFundItem(map);
		return new PageInfo<OrderRefundItem>(orderFundItem);
	}
	
	@ApiOperation(value="退款",notes="退款api")
	@RequestMapping(value="/Refund",method=RequestMethod.GET)
	public  Map<String,Object> Refund(Long id,HttpServletRequest request) throws Exception{
		Users user = UserUtil.getUser(request);
		if(id!=null&&user!=null){
			OrderRefundItem ori = orderRefundItemService.selectByKey(id);
			if(ori!=null){
				if(ori.getRefundStatus()==0||ori.getRefundStatus()==3){
					int refund = orderRefundItemService.refund(id, webSiteAddress+"/mall/orderPayPrice/refundCallBack", user);
					if(refund>0){
						return ResultUtil.success("退款成功,2到3个工作日到账");
					}else{
						return ResultUtil.success("退款失败");
					}
				}else{
					if(ori.getRefundStatus()==4){
						return ResultUtil.success("退款已拒绝，不能进行退款操作");
					}else{
						return ResultUtil.success("请不要重复提交退款操作");
					}
				}
			}
		}
		return ResultUtil.success("参数错误");
		
	}
	
	@ApiOperation(value="拒绝退款",notes="拒绝退款api")
	@RequestMapping(value="/refundOver",method=RequestMethod.GET)
	public  Map<String,Object> refundOver(Long id,HttpServletRequest request,String advice) throws Exception{
		Users user = UserUtil.getUser(request);
		if(id!=null&&user!=null){
			OrderRefundItem ori = orderRefundItemService.selectByKey(id);
			if(ori!=null){
				if(ori.getRefundStatus()==0){
					ori.setRefundStatus(4);
					ori.setAdvice(advice);
					int updateNotNull = orderRefundItemService.updateNotNull(ori);
					if(updateNotNull>0){
						Example example=new Example(JobOrders.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("sn", ori.getOrderSn());
						List<JobOrders> jo = jobOrdersMapper.selectByExample(example);
						if(!CollectionUtil.collectionIsEmpty(jo)){
							JobOrders jobOrders = jo.get(0);
							jobOrders.setIsCancel(3);
							Users users = usersService.selectByKey(jobOrders.getUserId());
							Map<String, String> map = new HashMap<>();
							map.put("phoneNumbers", users.getMobile());
							map.put("params", users.getRealName()+","+jobOrders.getSn()+","+advice);
							map.put("template", SmSConstant.REFUND_TEMPLATE_ID);
							HttpClient.doGet(SmSConstant.SMSURL, map);
							
							jobOrdersMapper.updateByPrimaryKeySelective(jobOrders);
						}
						return ResultUtil.success("拒绝退款成功");
					}else{
						return ResultUtil.success("拒绝退款失败");
					}
				}else{
					if(ori.getRefundStatus()==1||ori.getRefundStatus()==2||ori.getRefundStatus()==3){
						return ResultUtil.success("已进行退款操作，不能拒绝退款");
					}else{
						return ResultUtil.success("请不要重复提交拒绝退款操作");
					}
				}
			}
		}
		return ResultUtil.success("参数错误");
	}
	
}
