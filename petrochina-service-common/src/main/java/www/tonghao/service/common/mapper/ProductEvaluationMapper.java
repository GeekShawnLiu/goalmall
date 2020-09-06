package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ProductEvaluation;

/**
 * 
 * Description: 商品评价
 * 
 * @version 2019年6月26日
 * @since JDK1.8
 */
public interface ProductEvaluationMapper extends BaseMapper<ProductEvaluation>{

	/**
	 * 
	 * Description: 查询商品的所有评价
	 * 
	 * @data 2019年6月26日
	 * @param 
	 * @return
	 */
	List<ProductEvaluation> findByProductId(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 查询用户商品评价信息
	 * 
	 * @data 2019年6月26日
	 * @param 
	 * @return
	 */
	List<ProductEvaluation> findByMap(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 查询好评率 已经评价个数
	 * 
	 * @data 2019年6月28日
	 * @param 
	 * @return
	 */
	ProductEvaluation findRateByProductId(@Param("productId")Long productId);
}
