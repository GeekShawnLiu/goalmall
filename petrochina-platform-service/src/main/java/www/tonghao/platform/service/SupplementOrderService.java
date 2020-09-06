package www.tonghao.platform.service;

import java.util.List;
import java.util.Map;

import www.tonghao.platform.entity.SupplementOrder;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Users;

public interface SupplementOrderService extends BaseService<SupplementOrder>{

	/**
	 * 
	 * Description: 保存
	 * 
	 * @data 2019年6月24日
	 * @param 
	 * @return
	 */
	public Map<String, Object> save(SupplementOrder entity, Users user);
	
	/**
	 * 
	 * Description: 列表查询
	 * 
	 * @data 2019年6月24日
	 * @param 
	 * @return
	 */
	public List<SupplementOrder> findByMap(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 查看补单信息详情
	 * 
	 * @data 2019年6月25日
	 * @param 
	 * @return
	 */
	public SupplementOrder view(Long id);
}
