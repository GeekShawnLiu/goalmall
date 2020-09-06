package www.tonghao.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.SupplementOrderItem;
import www.tonghao.service.common.base.BaseMapper;

public interface SupplementOrderItemMapper extends BaseMapper<SupplementOrderItem>{

	/**
	 * 
	 * Description: 根据补单信息查询补单商品明细
	 * 
	 * @data 2019年6月24日
	 * @param 
	 * @return
	 */
	List<SupplementOrderItem> findBySupplementOrderId(@Param("supplementOrderId")Long supplementOrderId);
} 
