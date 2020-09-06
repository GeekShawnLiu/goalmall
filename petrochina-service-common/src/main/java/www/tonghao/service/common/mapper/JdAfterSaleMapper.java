package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.JdAfterSale;

public interface JdAfterSaleMapper extends BaseMapper<JdAfterSale> {
	
	/**
	 * 
	 * Description: 条件查询
	 * 
	 * @data 2019年7月18日
	 * @param 
	 * @return
	 */
	public List<JdAfterSale> findListByMap(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 根据jd售后服务单号条件查询
	 * 
	 * @data 2019年7月26日
	 * @param 
	 * @return
	 */
	public List<JdAfterSale> selectByAfsServiceId(Map<String, Object> map);
	
	/**
	 * 
	 * Description: h5售后列表
	 * 
	 * @data 2019年7月18日
	 * @param 
	 * @return
	 */
	public List<JdAfterSale> findH5ListByMap(Map<String, Object> map);
}