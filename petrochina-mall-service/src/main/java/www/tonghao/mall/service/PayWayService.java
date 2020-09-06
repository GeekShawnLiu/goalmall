package www.tonghao.mall.service;

import java.util.List;
import java.util.Map;

import www.tonghao.mall.entity.PayWay;
import www.tonghao.service.common.base.BaseService;

public interface PayWayService extends BaseService<PayWay> {

	/**  
	 * <p>Title: saveOrUpdate</p>  
	 * <p>Description: 保存或修改方法</p>  
	 * @author Yml  
	 * @param payWay
	 * @return  
	 */  
	int saveOrUpdate(PayWay payWay);

	/**  
	 * <p>Title: find</p>  
	 * <p>Description: 查询方法</p>  
	 * @author Yml  
	 * @param map
	 * @return  
	 */  
	List<PayWay> find(Map<String, Object> map);
	
	/**
	 * 查询供应商可用的支付方式
	 * @param id
	 * @return
	 */
	List<PayWay> findSupplierUsableList(Long id);

}
