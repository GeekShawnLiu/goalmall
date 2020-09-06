package www.tonghao.platform.mapper;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.ProductsExt;
import www.tonghao.service.common.base.BaseMapper;

public interface ProductsExtMapper extends BaseMapper<ProductsExt> {
	
	ProductsExt findByProductId(@Param("productId") Long productId);
}