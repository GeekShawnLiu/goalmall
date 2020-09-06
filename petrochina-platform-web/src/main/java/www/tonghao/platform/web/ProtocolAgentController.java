package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.entity.ProtocolAgent;
import www.tonghao.service.common.entity.ProtocolAgentArea;
import www.tonghao.service.common.entity.SupplierProtocolCatalog;
import www.tonghao.service.common.service.AreasService;
import www.tonghao.service.common.service.ProtocolAgentAreaService;
import www.tonghao.service.common.service.ProtocolAgentService;
import www.tonghao.service.common.service.ProtocolService;
import www.tonghao.service.common.service.SupplierProtocolCatalogService;

@RestController
@RequestMapping("/protocolAgent")
@Api(value="protocolAgentController",description="代理商协议")
public class ProtocolAgentController {
  
	@Autowired
	private ProtocolAgentService protocolAgentService;
	
	@Autowired
	private ProtocolService protocolService;
	@Autowired
	private SupplierProtocolCatalogService supplierProtocolCatalogService;
	@Autowired
	private ProtocolAgentAreaService protocolAgentAreaService;
	
	@Autowired
	private AreasService areasService;
	
	
	@ApiOperation(value="根据协议id和厂商协议id查询所有代理商",notes="根据协议id和厂商协议id查询所有代理商api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="supplierProtocolId",value="厂商协议id",required=true,dataType="Long",paramType="query"),
		@ApiImplicitParam(name="protocolId",value="协议id",required=true,dataType="Long",paramType="query"),
		
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public Map<String,Object> test(Long supplierProtocolId,Long protocolId){
		Map<String, Object> map=new HashMap<String, Object>();
		Example example=new Example(ProtocolAgent.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("protocolId", protocolId);
		createCriteria.andEqualTo("supplierProtocolId", supplierProtocolId);
		createCriteria.andEqualTo("isDelete", 0);
		List<ProtocolAgent> selectByExample = protocolAgentService.selectByExample(example);
		if(!CollectionUtils.isEmpty(selectByExample)){
			for (ProtocolAgent protocolAgent : selectByExample) {
				Example catalogsName=new Example(SupplierProtocolCatalog.class);
				Criteria createCriteria2 = catalogsName.createCriteria();
				createCriteria2.andEqualTo("supperAnentId", protocolAgent.getId());
				createCriteria2.andEqualTo("type", 2);
				List<SupplierProtocolCatalog> selectByExample2 = supplierProtocolCatalogService.selectByExample(catalogsName);
				List<String> collect = selectByExample2.stream().map(SupplierProtocolCatalog::getCatalogsName).collect(Collectors.toList());
				
				Example area_ex=new Example(ProtocolAgentArea.class);
				Criteria createCriteria_area = area_ex.createCriteria();
				createCriteria_area.andEqualTo("protocolAgentId",protocolAgent.getId());
				
				List<ProtocolAgentArea> selectByExample3 = protocolAgentAreaService.selectByExample(area_ex);
				List<String> area_str= selectByExample3.stream().map(ProtocolAgentArea::getAreaName).collect(Collectors.toList());
				protocolAgent.setAreaNames(String.join(",", area_str.toArray(new String[area_str.size()])));
				protocolAgent.setCatalogsName(String.join(",", collect.toArray(new String[collect.size()])));
		  }
		}
		map.put("protocolAgent", selectByExample);
		Protocol protocol = protocolService.getProtocolById(protocolId);
		map.put("protocol", protocol);
		return map;
	}
	
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		ProtocolAgent entity=new ProtocolAgent();
		entity.setId(id);
		entity.setIsDelete(1);
		int delete = protocolAgentService.updateNotNull(entity);
		return ResultUtil.result(delete, 0);
	}
	@ApiOperation(value="添加",notes="添加api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String, Object> save(@RequestBody ProtocolAgent protocol){
		int save = protocolAgentService.saveProtocolAgent(protocol);
		return ResultUtil.result(save, 0);
	}
	@RequestMapping(value="/getArea",method={RequestMethod.GET,RequestMethod.POST})
	public List<Areas> getArea(Long supplierId){
		List<Areas> areasList=null;
			Example example=new Example(Areas.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andLike("treeIds", "%370000%");
			areasList = areasService.selectByExample(example);
		return areasList;
	}
}
