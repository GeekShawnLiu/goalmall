package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.ProductEvaluation;
import www.tonghao.service.common.entity.Users;

/**
 * 
 * Description: 商品评价
 * 
 * @version 2019年6月26日
 * @since JDK1.8
 */
public interface ProductEvaluationService extends BaseService<ProductEvaluation>{

	/**
	 * 
	 * Description: 新增商品评价
	 * 
	 * @data 2019年6月26日
	 * @param 
	 * @return
	 */
	Map<String, Object> save(ProductEvaluation productEvaluation, Users user);
	
	/**
	 * 
	 * Description: 商品评价详情
	 * 
	 * @data 2019年6月26日
	 * @param 
	 * @return
	 */
	public ProductEvaluation productEvaluationView(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 获取商品所有的评价
	 * 
	 * @data 2019年6月26日
	 * @param 
	 * @return
	 */
	public List<ProductEvaluation> productEvaluationList(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 获取商品所有的评价
	 * 
	 * @data 2019年6月26日
	 * @param 
	 * @return
	 */
	public ProductEvaluation getProductRate(Long productId);
}
