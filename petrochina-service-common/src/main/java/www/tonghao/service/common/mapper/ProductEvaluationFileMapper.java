package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ProductEvaluationFile;

/**
 * 
 * Description: 商品评价附件
 * 
 * @version 2019年6月26日
 * @since JDK1.8
 */
public interface ProductEvaluationFileMapper extends BaseMapper<ProductEvaluationFile>{

	/**
	 * 
	 * Description: 根据评价id查询附件
	 * 
	 * @data 2019年6月26日
	 * @param 
	 * @return
	 */
	List<ProductEvaluationFile> findByproductEvaluationId(@Param("productEvaluationId")Long productEvaluationId);
}
