package www.tonghao.service.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.ProtocolAgentArea;
import www.tonghao.service.common.mapper.ProtocolAgentAreaMapper;
import www.tonghao.service.common.service.ProtocolAgentAreaService;

@Service("protocolAgentAreaService")
public class ProtocolAgentAreaServiceImpl extends BaseServiceImpl<ProtocolAgentArea> implements ProtocolAgentAreaService {

	@Autowired
	private ProtocolAgentAreaMapper protocolAgentAreaMapper;
}
