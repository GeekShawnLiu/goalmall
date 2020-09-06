package www.tonghao.mall.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.mall.entity.BiddingOrders;
import www.tonghao.service.common.base.BaseMapper;
/**
 * 

* <p>Title: BiddingOrdersMapper</p>  

* <p>Description: </p>  

* @author mys  

* @date 2018年11月23日
 */
public interface BiddingOrdersMapper extends BaseMapper<BiddingOrders> {
	
	List<BiddingOrders> find(Map<String, Object> map);
	List<BiddingOrders> findStatus(Map<String, Object> map);

	List<BiddingOrders> findPurchase(Map<String, Object> map);
	List<BiddingOrders> findPlan(Map<String, Object> map);
	
	/**
	 * 根据ID 查询
	 */
	BiddingOrders getById(@Param("id")Long id);
	List<BiddingOrders> getByIds(@Param("id")Long id);
	String selectMaxCode(@Param("code") String code);
	void updateStatusByID(Map<String, Object> map);
	int chooseSuppliers(Map<String, Object> map);
	BigDecimal countBiddingTotal();
}