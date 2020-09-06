package www.tonghao.service.common.mapper;

import java.util.List;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ProtocolAgent;


public interface ProtocolAgentMapper extends BaseMapper<ProtocolAgent> {
	
	/**
	 */
	 List<ProtocolAgent> findSupplierAgentListByEntity(ProtocolAgent entity);
}