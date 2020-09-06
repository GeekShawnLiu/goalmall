package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ProductPics;

public interface ProductPicsMapper extends BaseMapper<ProductPics> {
	
	/**  
	 * <p>Title: getByProductId</p>  
	 * <p>Description: 查询产品图片</p>  
	 * @author Yml  
	 * @param productId
	 * @return  
	 */  
	List<ProductPics> getByProductId(@Param("productId") Long productId);
}