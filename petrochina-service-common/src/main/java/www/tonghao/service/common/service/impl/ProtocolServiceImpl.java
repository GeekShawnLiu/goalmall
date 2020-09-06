package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.entity.SupplierProtocol;
import www.tonghao.service.common.entity.SupplierProtocolBrand;
import www.tonghao.service.common.entity.SupplierProtocolCatalog;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.mapper.ProtocolMapper;
import www.tonghao.service.common.mapper.SupplierProtocolBrandMapper;
import www.tonghao.service.common.service.ProtocolService;
import www.tonghao.service.common.service.SupplierProtocolCatalogService;
import www.tonghao.service.common.service.SupplierProtocolService;

@Service("protocolService")
public class ProtocolServiceImpl extends BaseServiceImpl<Protocol> implements ProtocolService {

	@Autowired
	private ProtocolMapper protocolMapper;
	
	@Autowired
	private SupplierProtocolService supplierProtocolService;
	
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	@Autowired
	private SupplierProtocolCatalogService supplierProtocolCatalogService;
	@Autowired
	private SupplierProtocolBrandMapper supplierProtocolBrandMapper;
	@Autowired
	private BrandMapper brandMapper;
	@Override
	public int saveProtocol(Protocol protocol) {
		protocol.setIsDelete(0);
		protocol.setCreatedAt(DateUtilEx.timeFormat(new Date()));
	    int save = this.save(protocol);
	    List<SupplierProtocol> supplierProtocols = protocol.getSupplierProtocols();
	    if(!CollectionUtils.isEmpty(supplierProtocols)){
	    	SupplierProtocolCatalog catalog=null;
	    	for (SupplierProtocol supplierProtocol : supplierProtocols) {
	    		supplierProtocol.setStatus(1);
	    		supplierProtocol.setIsAssign(2);
	    		supplierProtocol.setProtocolId(protocol.getId());
	    		supplierProtocolService.save(supplierProtocol);
	    		String brandIds = supplierProtocol.getBrandIds();
	    		if(!StringUtils.isEmpty(brandIds)){
	    			String[] split = brandIds.split(",");
	    			SupplierProtocolBrand brand=null;
	    			for (String string : split) {
	    				Brand br = brandMapper.selectByPrimaryKey(Long.parseLong(string));
	    				brand=new SupplierProtocolBrand();
	    				brand.setProtocolId(protocol.getId());
	    				brand.setBrandId(br.getId());
	    				brand.setBrandName(br.getName());
	    				brand.setSupplierId(supplierProtocol.getSupplierId());
	    				brand.setSupplierProtocolId(supplierProtocol.getId());
	    				supplierProtocolBrandMapper.insert(brand);
					}
	    		}
	    		if(supplierProtocol.getCatalogsIds()!=null&&!"".equals(supplierProtocol.getCatalogsIds())){
	    			String[] catalogsIds = supplierProtocol.getCatalogsIds().split(",");
	    			for (String string : catalogsIds) {
	    				PlatformCatalogs pf = platformCatalogsMapper.selectByPrimaryKey(Long.parseLong(string));
	    				catalog=new SupplierProtocolCatalog();
	    				catalog.setSupperAnentId(supplierProtocol.getId());
	    				catalog.setCatalogsId(pf.getId());
	    				catalog.setCatalogsName(pf.getName());
	    				catalog.setType(1);
	    				catalog.setProtocolId(protocol.getId());
	    				catalog.setSupplierId(supplierProtocol.getSupplierId());
	    				supplierProtocolCatalogService.save(catalog);
					}
	    		}
	    		
			}
	    }
		return save;
	}

	@Override
	public Protocol getProtocolById(Long id) {
		return protocolMapper.getProtocolById(id);
	}

	@Override
	public int updateProtocol(Protocol protocol) {
		protocol.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int update = this.updateNotNull(protocol);
		List<SupplierProtocol> supplierProtocols = protocol.getSupplierProtocols();
		if(!CollectionUtils.isEmpty(supplierProtocols)){
			SupplierProtocolCatalog catalog=null;
			for (SupplierProtocol supplierProtocol : supplierProtocols) {
				if(supplierProtocol.getId()!=null){
					if(supplierProtocol.getCatalogsIds()!=null&&!"".equals(supplierProtocol.getCatalogsIds())&&!"null".equals(supplierProtocol.getCatalogsIds())){
						String[] catalogsIds = supplierProtocol.getCatalogsIds().split(",");
						Example example=new Example(SupplierProtocolCatalog.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("supperAnentId", supplierProtocol.getId());
						createCriteria.andEqualTo("type", 1);
						List<SupplierProtocolCatalog> selectByExample = supplierProtocolCatalogService.selectByExample(example);
						if(!CollectionUtils.isEmpty(selectByExample)){
							for (SupplierProtocolCatalog supplierProtocolCatalog : selectByExample) {
								supplierProtocolCatalogService.delete(supplierProtocolCatalog.getId());
							}
						}
						for (String string : catalogsIds) {
		    				PlatformCatalogs pf = platformCatalogsMapper.selectByPrimaryKey(Long.parseLong(string));
		    				catalog=new SupplierProtocolCatalog();
		    				catalog.setSupperAnentId(supplierProtocol.getId());
		    				catalog.setCatalogsId(pf.getId());
		    				catalog.setCatalogsName(pf.getName());
		    				catalog.setType(1);
		    				catalog.setProtocolId(protocol.getId());
		    				catalog.setSupplierId(supplierProtocol.getSupplierId());
		    				supplierProtocolCatalogService.save(catalog);
						}
					}
					if(!StringUtils.isEmpty(supplierProtocol.getBrandIds()) && !"null".equals(supplierProtocol.getBrandIds())){
						String[] split = supplierProtocol.getBrandIds().split(",");
						Example example=new Example(SupplierProtocolBrand.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("supplierProtocolId", supplierProtocol.getId());
						List<SupplierProtocolBrand> selectByExample = supplierProtocolBrandMapper.selectByExample(example);
						if(!CollectionUtils.isEmpty(selectByExample)){
							for (SupplierProtocolBrand supplierProtocolBrand : selectByExample) {
								supplierProtocolBrandMapper.deleteByPrimaryKey(supplierProtocolBrand.getId());
							}
						}
						SupplierProtocolBrand brand=null;
		    			for (String string : split) {
		    				Brand br = brandMapper.selectByPrimaryKey(Long.parseLong(string));
		    				brand=new SupplierProtocolBrand();
		    				brand.setProtocolId(protocol.getId());
		    				brand.setBrandId(br.getId());
		    				brand.setBrandName(br.getName());
		    				brand.setSupplierId(supplierProtocol.getSupplierId());
		    				brand.setSupplierProtocolId(supplierProtocol.getId());
		    				supplierProtocolBrandMapper.insert(brand);
						}
					}
					supplierProtocolService.updateNotNull(supplierProtocol);
				}else{
					supplierProtocol.setStatus(1);
					supplierProtocol.setIsAssign(2);
					supplierProtocol.setProtocolId(protocol.getId());
					supplierProtocolService.save(supplierProtocol);
					if(supplierProtocol.getCatalogsIds()!=null&&!"".equals(supplierProtocol.getCatalogsIds())&&!"null".equals(supplierProtocol.getCatalogsIds())){
		    			String[] catalogsIds = supplierProtocol.getCatalogsIds().split(",");
		    			for (String string : catalogsIds) {
		    				PlatformCatalogs pf = platformCatalogsMapper.selectByPrimaryKey(Long.parseLong(string));
		    				catalog=new SupplierProtocolCatalog();
		    				catalog.setSupperAnentId(supplierProtocol.getId());
		    				catalog.setCatalogsId(pf.getId());
		    				catalog.setCatalogsName(pf.getName());
		    				catalog.setType(1);
		    				catalog.setProtocolId(protocol.getId());
		    				catalog.setSupplierId(supplierProtocol.getSupplierId());
		    				supplierProtocolCatalogService.save(catalog);
						}
		    		}
					if(!StringUtils.isEmpty(supplierProtocol.getBrandIds()) && !"null".equals(supplierProtocol.getBrandIds())){
						String[] split = supplierProtocol.getBrandIds().split(",");
						SupplierProtocolBrand brand=null;
		    			for (String string : split) {
		    				Brand br = brandMapper.selectByPrimaryKey(Long.parseLong(string));
		    				brand=new SupplierProtocolBrand();
		    				brand.setProtocolId(protocol.getId());
		    				brand.setBrandId(br.getId());
		    				brand.setBrandName(br.getName());
		    				brand.setSupplierId(supplierProtocol.getSupplierId());
		    				brand.setSupplierProtocolId(supplierProtocol.getId());
		    				supplierProtocolBrandMapper.insert(brand);
						}
					}
				}
			}
		}
		
		
		return update;
	}

	@Override
	public List<Protocol> getProtocolBySupplier(Map<String, Object> map) {
		return protocolMapper.getProtocolBySupplier(map);
	}

	@Override
	public List<Protocol> getUsableProtocolBySupplier(Long supplierId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status", 2);
		map.put("compareCurrDate", DateUtilEx.format(new Date(), "yyyy-MM-dd"));
		map.put("supplierId", supplierId);
		return getProtocolBySupplier(map);
	}

}
