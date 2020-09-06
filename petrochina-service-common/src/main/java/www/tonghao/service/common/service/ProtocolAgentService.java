package www.tonghao.service.common.service;

import java.util.List;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.ProtocolAgent;

public interface ProtocolAgentService extends BaseService<ProtocolAgent> {

	/**
	 * 保存协议代理商
	 * @param agent
	 * @return
	 */
	public int saveProtocolAgent(ProtocolAgent agent);
	
	/**
	 * 修改协议代理商
	 * @param agent
	 * @return
	 */
	public int updateProtocolAgent(ProtocolAgent agent);
	
	/**
	 * 查找供应商指定协议类型的代理商列表
	 * @param entity
	 * @param supplierId
	 * @return
	 */
	 List<ProtocolAgent> findSupplierAgentListByEntity(ProtocolAgent entity,Long supplierId);
}
