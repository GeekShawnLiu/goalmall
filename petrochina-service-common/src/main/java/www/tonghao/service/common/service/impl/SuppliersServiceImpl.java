package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.entity.ShopCatalogs;
import www.tonghao.service.common.entity.SupplierProtocol;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.ProtocolMapper;
import www.tonghao.service.common.mapper.ShopCatalogsMapper;
import www.tonghao.service.common.mapper.SupplierProtocolMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.service.SupplierFileService;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.service.common.service.UsersService;


@Service("suppliersService")
@Transactional
public class SuppliersServiceImpl extends BaseServiceImpl<Suppliers> implements SuppliersService {

	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private ProtocolMapper protocolMapper;
	
	@Autowired
	private SupplierProtocolMapper supplierProtocolMapper;
	
	@Autowired
	private SupplierFileService supplierFileService;
	
	@Autowired
	private ShopCatalogsMapper shopCatalogsMapper;
	
	@Override
	public List<Suppliers> findListByEntity(Suppliers entity) {
		return suppliersMapper.findListByEntity(entity);
	}
	
	
	@Override
	public int saveOrUpdate(Suppliers suppliers) {
		int result_default=0;
		if(suppliers.getId()!=null){
			/*if(!CollectionUtils.isEmpty(suppliers.getSupFileList())){
				for (SupplierFile iterable_element : suppliers.getSupFileList()) {
					iterable_element.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					iterable_element.setSupplierId(suppliers.getId());
					iterable_element.setIsDelete(0);
					iterable_element.setFileName(iterable_element.getFileName());
					iterable_element.setFileSize(iterable_element.getFileSize());
					iterable_element.setFileCode(iterable_element.getFileCode());
					supplierFileService.save(iterable_element);
				}
			}*/
			suppliers.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=updateNotNull(suppliers);
			//result_default=suppliersMapper.updateByPrimaryKeySelective(suppliers);
		}else{
			suppliers.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=suppliersMapper.insert(suppliers);
		}
		return result_default;
	}

	@Override
	public List<Suppliers> getSuppliersAll(Map<String, Object> map) {
		return suppliersMapper.getSuppliersAll(map);
	}

	@Override
	public Suppliers getSuppliersById(Long id) {
		return suppliersMapper.getSuppliersById(id);
	}

	@Override
	public List<Suppliers> getSupplierIds(Long[] ids) {
		return suppliersMapper.getSupplierIds(ids);
	}

	@Override
	public List<Suppliers> getMallSuppliers() {
		return suppliersMapper.getMallSuppliers();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveSupplierAndUser(List<Suppliers> suppliers) {
		int default_result=0;
		if(!CollectionUtils.isEmpty(suppliers)){ //查询协议
			String pro_code=suppliers.get(0).getAgreementCode();
			Protocol selectOne =null;
			if(!StringUtils.isEmpty(pro_code)){ 
				Protocol record=new Protocol();
				record.setCode(pro_code);
				record.setIsDelete(0);
				selectOne= protocolMapper.selectOne(record);
			}
			Suppliers supers=null;
			SupplierProtocol spl=null;
			SupplierProtocol sptl=null; 
			for (Suppliers supp : suppliers) {//遍历
				spl=new SupplierProtocol();
				supers=new Suppliers();
				supers.setCreditCode(supp.getCreditCode());
				Suppliers sp = suppliersMapper.selectOne(supers); //根据信用代码查询供应商是否存在
				spl.setProtocolId(selectOne.getId());
				if(sp!=null){//由当前这个供应商
                	spl.setSupplierId(sp.getId());
                	sptl= supplierProtocolMapper.selectOne(spl);//查询是否有当前的协议
                	spl.setContact(sp.getContactsName());
                	spl.setContactPhone(sp.getContactsMobile());
                }else{//没有当前的供应商
                	supp.setIsDelete((byte)0);
                	default_result+=save(supp);//添加供应商
                	//default_result+=suppliersMapper.insert(supp);//添加供应商
    				Users users = supp.getUsers();
    				users.setTypeId(supp.getId());
    				users.setIsDelete(0);
    				usersService.save(users);//添加用户
                	spl.setSupplierId(supp.getId());
                	sptl= supplierProtocolMapper.selectOne(spl);//查询是否有当前的协议
                	spl.setContact(supp.getContactsName());
                	spl.setContactPhone(supp.getContactsMobile());
                }
                if(sptl==null){//如果协议不存在添加
                	spl.setStatus(1);
                	default_result+=supplierProtocolMapper.insert(spl);
            	}
			}
		}
		return default_result;
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveSupplierApi(Suppliers suppliers) {
		int default_result=0;
		suppliers.setIsDelete((byte)0);
    	//default_result+=suppliersMapper.insert(suppliers);//添加供应商
    	default_result+=save(suppliers);//添加供应商
    	Users users = suppliers.getUsers();
		users.setTypeId(suppliers.getId());
		users.setIsDelete(0);
		default_result+=usersService.save(users);//添加用户
		return default_result;
	}


	@Override
	public List<Suppliers> findUsableListIdIn(Set<Long> ids) {
		return suppliersMapper.findUsableListIdIn(ids);
	}


	@Override
	public List<Suppliers> findNoUserSuppliers(Map<String, Object> map) {
		return suppliersMapper.findNoUserSuppliers(map);
	}


	@Override
	public Suppliers selectSupplierInfo(Long id) {
		Suppliers supplier = suppliersMapper.selectSupplierInfo(id);
		if(supplier.getShop() !=null){
			Long shopId = supplier.getShop().getId();
			List<ShopCatalogs> shopCatalogsList = shopCatalogsMapper.selectShopCatalogsList(shopId);
			if(CollectionUtils.isNotEmpty(shopCatalogsList)){
				StringBuilder sb = new StringBuilder();
				for(ShopCatalogs shopCatalogs : shopCatalogsList){
					String catalogsName = shopCatalogs.getCatalogsName();
					sb.append(catalogsName);
					sb.append("，");
				}
				String catalogsName = sb.substring(0, sb.length() -1);
				supplier.getShop().setCatalogsName(catalogsName);
			}
		}
		return supplier;
	}


	@Override
	public List<Suppliers> selectByActivity(Map<String, Object> map) {
		return suppliersMapper.selectByActivity(map);
	}
}
