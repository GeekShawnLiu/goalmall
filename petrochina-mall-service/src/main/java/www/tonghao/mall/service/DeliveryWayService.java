package www.tonghao.mall.service;

import java.util.List;
import java.util.Map;

import www.tonghao.mall.entity.DeliveryWay;
import www.tonghao.service.common.base.BaseService;

public interface DeliveryWayService  extends BaseService<DeliveryWay> {


	/**  
	 * <p>Title: saveOrUpdate</p>  
	 * <p>Description: 保存或修改方法</p>  
	 * @author Yml  
	 * @param deliveryWay
	 * @return  
	 */  
	int saveOrUpdate(DeliveryWay deliveryWay);

	/**  
	 * <p>Title: find</p>  
	 * <p>Description: 查询方法</p>  
	 * @author Yml  
	 * @param map
	 * @return  
	 */  
	List<DeliveryWay> find(Map<String, Object> map);
}
