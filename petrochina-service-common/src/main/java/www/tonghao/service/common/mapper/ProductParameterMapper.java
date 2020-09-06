package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ProductParameter;

public interface ProductParameterMapper extends BaseMapper<ProductParameter> {
	
	List<ProductParameter> getByProductId(@Param("productId") Long productId);
	/**
	 * 查询商品列表的参数
	 * @param productIds
	 * @return
	 */
	List<ProductParameter> findListByProductIds(@Param("productIds")List<Long> productIds);
	
	List<Long> selectProduct(Map<String, Object> map);
	
	List<ProductParameter> getRelationByProductId(@Param("productId") Long productId);
}