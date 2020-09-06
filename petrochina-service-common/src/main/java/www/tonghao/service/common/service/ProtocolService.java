package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Protocol;

public interface ProtocolService extends BaseService<Protocol> {
	/**
	 * 添加协议
	 */
	public int saveProtocol(Protocol protocol);
	
	
	/**
	 * 修改协议
	 */
	public int updateProtocol(Protocol protocol);
	
	/**
	 * 根据id 查询协议以及明细
	 * @param id
	 * @return
	 */
	public Protocol getProtocolById(Long id);
	
	/**
	 * 根据供应商id查询协议
	 * @param id
	 * @return
	 */
	public List<Protocol> getProtocolBySupplier(Map<String, Object> map);
	
	/**
	 * 查询供应商可用协议
	 * @param supplierId
	 * @return
	 */
	public List<Protocol> getUsableProtocolBySupplier(Long supplierId);
}
