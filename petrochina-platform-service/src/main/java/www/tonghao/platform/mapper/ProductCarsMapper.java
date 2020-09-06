package www.tonghao.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.ProductCars;
import www.tonghao.service.common.base.BaseMapper;

public interface ProductCarsMapper extends BaseMapper<ProductCars> {
	
	List<ProductCars> getByProductId(@Param("productId") Long productId);
}