package www.tonghao.platform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.SupplementOrder;
import www.tonghao.service.common.base.BaseMapper;

public interface SupplementOrderMapper extends BaseMapper<SupplementOrder>{
	
	/**
	 * 
	 * Description: 根据id查询
	 * 
	 * @data 2019年6月24日
	 * @param 
	 * @return
	 */
	SupplementOrder selectById(@Param("id")Long id);
	
	/**
	 * 
	 * Description: 分页查询列表
	 * 
	 * @data 2019年6月24日
	 * @param 
	 * @return
	 */
	List<SupplementOrder> findByMap(Map<String, Object> map);
}
