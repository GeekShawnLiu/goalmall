package www.tonghao.mall.web.controller.purchaser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.jwt.TokenAuthenticationService;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.OrderItemsService;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.OrderSplitItem;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.ActivityService;
import www.tonghao.service.common.service.OrderSplitItemService;
import www.tonghao.service.common.service.UsersService;
import www.tonghao.utils.UserUtil;

@RestController
@RequestMapping("customer")
public class WangYiIframeController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderSplitItemService orderSplitItemService;
	
	@Autowired
    private OrderItemsService orderItemsService;
	
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping("user")
	public Object getUser(HttpServletRequest request){
		try {
			String userName = TokenAuthenticationService.getAuthenticationUser(request);
			Users users = usersService.findByLoginName(userName, 1);
			Map<String, String> map=new HashMap<String, String>();
			if(users!=null){
				map.put("name", users.getRealName());
				map.put("mobile", users.getMobile());
				map.put("loginName", users.getLoginName());
				map.put("deptName", users.getDepName());
				map.put("email", users.getEmail());
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("orders")
	public Object getOrder(HttpServletRequest request){
		try {
			String userName = TokenAuthenticationService.getAuthenticationUser(request);
			Users users = usersService.findByLoginName(userName, 1);
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			if(users!=null){
				PageHelper.startPage(1, 5);
				Example order_example=new Example(Orders.class);
				Criteria order_createCriteria = order_example.createCriteria();
				order_createCriteria.andEqualTo("userId", users.getId());
				order_createCriteria.andEqualTo("isDelete", 0);
				order_createCriteria.andIsNull("parentId");
				order_example.setOrderByClause(" created_at desc");
				List<Orders> orderList = ordersService.selectByExample(order_example);
				PageInfo<Orders> info=new PageInfo<Orders>(orderList);
				if(!CollectionUtil.collectionIsEmpty(info.getList())){
					for (Orders orders : info.getList()) {
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("sn", orders.getSn());
						map.put("emallSn", orders.getEmallSn());
						map.put("supplierName", orders.getSupplierName());
						map.put("createAt", orders.getCreatedAt());
						map.put("payType", orders.getPayName());
						map.put("total", orders.getTotal());
						List<Map<String, Object>> list_map=new ArrayList<>();
						Example child_example=new Example(Orders.class);
						Criteria child_createCriteria = child_example.createCriteria();
						child_createCriteria.andEqualTo("parentId", orders.getId());
						List<Orders> child_orderList = ordersService.selectByExample(child_example);
						if(!CollectionUtil.collectionIsEmpty(child_orderList)){
							for (Orders child_orders : child_orderList) {
								Map<String, Object>  map_order=new HashMap<>();
								map_order.put("emallSn", child_orders.getEmallSn());
								Example orderSplit_example=new Example(OrderSplitItem.class);
								Criteria orderSplit_createCriteria = orderSplit_example.createCriteria();
								orderSplit_createCriteria.andEqualTo("childEmallSn", child_orders.getEmallSn());
								List<OrderSplitItem> orderSplitItemList = orderSplitItemService.selectByExample(orderSplit_example);
								if(!CollectionUtil.collectionIsEmpty(orderSplitItemList)){
									String product_name="";
									String activity_name="";
									for (OrderSplitItem orderSplitItem : orderSplitItemList) {
										Example orderItems_example=new Example(OrderItems.class);
										Criteria orderItems_createCriteria = orderItems_example.createCriteria();
										orderItems_createCriteria.andEqualTo("productId", orderSplitItem.getProductId());
										orderItems_createCriteria.andEqualTo("orderId", orders.getId());
										List<OrderItems> orderItemsList = orderItemsService.selectByExample(orderItems_example);
										if(!CollectionUtil.collectionIsEmpty(orderItemsList)){
											OrderItems orderItems = orderItemsList.get(0);
											product_name+=orderItems.getName()+",";
											//活动名称
											if(orderItems.getActivityId() != null){
												Activity activity = activityService.selectByKey(orderItems.getActivityId());
												if(activity != null){
													activity_name += activity.getName()+",";
												}
											}
										}
									}
									if(product_name.length()>0){
										product_name=product_name.substring(0,product_name.length()-1);
									}
									if(activity_name.length()>0){
										activity_name=activity_name.substring(0,activity_name.length()-1);
									}
									map_order.put("product", product_name);
									map_order.put("activity", activity_name);
								}
								list_map.add(map_order);
							}
						}
						map.put("child", list_map);
						if(CollectionUtil.collectionIsEmpty(list_map)){
							String product_name="";
							String activity_name="";
							Example orderItems_example=new Example(OrderItems.class);
							Criteria orderItems_createCriteria = orderItems_example.createCriteria();
							orderItems_createCriteria.andEqualTo("orderId", orders.getId());
							List<OrderItems> orderItemsList = orderItemsService.selectByExample(orderItems_example);
							if(!CollectionUtil.collectionIsEmpty(orderItemsList)){
								for (OrderItems orderItems2 : orderItemsList) {
									product_name+=orderItems2.getName()+",";
									//活动名称
									if(orderItems2.getActivityId() != null){
										Activity activity = activityService.selectByKey(orderItems2.getActivityId());
										if(activity != null){
											activity_name += activity.getName()+",";
										}
									}
								}
								if(product_name.length()>0){
									product_name=product_name.substring(0,product_name.length()-1);
								}
								if(activity_name.length()>0){
									activity_name=activity_name.substring(0,activity_name.length()-1);
								}
							}
							map.put("product", product_name);
							map.put("activity", activity_name);
						}
						list.add(map);
					}
				}
				
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
