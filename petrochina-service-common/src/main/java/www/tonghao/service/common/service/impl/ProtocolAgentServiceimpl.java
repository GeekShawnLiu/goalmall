package www.tonghao.service.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProtocolAgent;
import www.tonghao.service.common.entity.ProtocolAgentArea;
import www.tonghao.service.common.entity.SupplierProtocol;
import www.tonghao.service.common.entity.SupplierProtocolCatalog;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.mapper.ProtocolAgentAreaMapper;
import www.tonghao.service.common.mapper.ProtocolAgentMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.service.AreasService;
import www.tonghao.service.common.service.ProtocolAgentService;
import www.tonghao.service.common.service.SupplierProtocolCatalogService;
import www.tonghao.service.common.service.SupplierProtocolService;

@Service("protocolAgentService")
public class ProtocolAgentServiceimpl extends BaseServiceImpl<ProtocolAgent> implements ProtocolAgentService {

	@Autowired
	private ProtocolAgentMapper protocolAgentMapper;
	@Autowired
	private AreasService areasService;
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	@Autowired
	private SupplierProtocolCatalogService supplierProtocolCatalogService;
	@Autowired
	private SupplierProtocolService supplierProtocolService;
	@Autowired
	private SuppliersMapper suppliersMapper;
	@Autowired
	private ProtocolAgentAreaMapper protocolAgentAreaMapper;
	
	@Override
	public int saveProtocolAgent(ProtocolAgent agent) {
		agent.setIsDelete(0);
		Suppliers supper = suppliersMapper.selectByPrimaryKey(agent.getSupplierAgentId());
		agent.setAgentName(supper.getName());
		String areaIds = agent.getAreaIds();
		int save = this.save(agent);
		if(areaIds!=null){
			String[] split = areaIds.split(",");
			ProtocolAgentArea agentArea=null;
			for (String string : split) {
				Areas ares = areasService.selectByKey(Long.parseLong(string));
				agentArea=new ProtocolAgentArea();
				agentArea.setAreaId(Long.parseLong(string));
				agentArea.setAreaName(ares.getName());
				agentArea.setAreaTreeIds(ares.getTreeIds());
				agentArea.setAreaTreeNames(ares.getTreeNames());
				agentArea.setProtocolAgentId(agent.getId());
				protocolAgentAreaMapper.insert(agentArea);
			}
		}
		if(agent.getCatalogsIds()!=null){
			String[] split = agent.getCatalogsIds().split(",");
			SupplierProtocolCatalog catalog=null;
			SupplierProtocol entity=new SupplierProtocol();
			entity.setId(agent.getSupplierProtocolId());
			entity.setIsAssign(1);
			supplierProtocolService.updateNotNull(entity);
			for (String string : split) {
				PlatformCatalogs selectByKey = platformCatalogsMapper.selectByPrimaryKey(Long.parseLong(string));
				catalog=new SupplierProtocolCatalog();
				catalog.setSupperAnentId(agent.getId());
				catalog.setCatalogsId(selectByKey.getId());
				catalog.setCatalogsName(selectByKey.getName());
				catalog.setSupplierId(supper.getId());
				catalog.setProtocolId(agent.getProtocolId());
				catalog.setType(2);
				supplierProtocolCatalogService.save(catalog);
				
			}
		}
		
		return save;
	}

	@Override
	public int updateProtocolAgent(ProtocolAgent agent) {
		return 1;
	}

	@Override
	public List<ProtocolAgent> findSupplierAgentListByEntity(ProtocolAgent entity,Long supplierId) {
		entity.setSupplierAgentId(supplierId);
		return protocolAgentMapper.findSupplierAgentListByEntity(entity);
	}

}
