package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Shop;
import www.tonghao.service.common.entity.ShopCatalogs;
import www.tonghao.service.common.entity.ShopUpdateCatalogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.SuppliersUpdate;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.mapper.ShopCatalogsMapper;
import www.tonghao.service.common.mapper.ShopMapper;
import www.tonghao.service.common.mapper.ShopUpdateCatalogsMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.mapper.SuppliersUpdateMapper;
import www.tonghao.service.common.mapper.UsersMapper;
import www.tonghao.service.common.service.SuppliersUpdateService;

@Service("suppliersUpdateService")
public class SuppliersUpdateServiceImpl extends BaseServiceImpl<SuppliersUpdate> implements SuppliersUpdateService {

	@Autowired
	private SuppliersUpdateMapper suppliersUpdateMapper;

	@Autowired
	private SuppliersMapper suppliersMapper;

	@Autowired
	private ShopCatalogsMapper shopCatalogsMapper;
	
	@Autowired
	private ShopUpdateCatalogsMapper shopUpdateCatalogsMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private SuppliersMapper supplierMapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	
	@Override
	public List<SuppliersUpdate> findList(Map<String, Object> map) {
		List<SuppliersUpdate> list = suppliersUpdateMapper.findList(map);
		return list;
	}

	@Override
	public SuppliersUpdate findById(Long id) {
		SuppliersUpdate suppliersUpdate = suppliersUpdateMapper.selectByPrimaryKey(id);
		if(suppliersUpdate != null){
			List<ShopUpdateCatalogs> shopUpdateCatalogsList = shopUpdateCatalogsMapper.selectByUpdateId(suppliersUpdate.getId());
			if(CollectionUtils.isNotEmpty(shopUpdateCatalogsList)){
				StringBuilder sb = new StringBuilder();
				for (ShopUpdateCatalogs shopUpdateCatalogs : shopUpdateCatalogsList) {
					String catalogsName = shopUpdateCatalogs.getCatalogsName();
					sb.append(catalogsName);
					sb.append("，");
				}
				String catalogsName = sb.substring(0, sb.length() - 1);
				suppliersUpdate.setCatalogsName(catalogsName);
			}
		}
		return suppliersUpdate;
	}

	@Override
	public Suppliers getOldInfo(Long supplierId) {
		Suppliers supplier = suppliersMapper.selectSupplierInfo(supplierId);
		if (supplier.getShop() != null) {
			Long shopId = supplier.getShop().getId();
			List<ShopCatalogs> shopCatalogsList = shopCatalogsMapper.selectShopCatalogsList(shopId);
			if (CollectionUtils.isNotEmpty(shopCatalogsList)) {
				StringBuilder sb = new StringBuilder();
				for (ShopCatalogs shopCatalogs : shopCatalogsList) {
					String catalogsName = shopCatalogs.getCatalogsName();
					sb.append(catalogsName);
					sb.append("，");
				}
				String catalogsName = sb.substring(0, sb.length() - 1);
				supplier.getShop().setCatalogsName(catalogsName);
			}
		}
		return supplier;
	}

	@Override
	public Map<String, Object> submitUpdate(SuppliersUpdate suppliersUpdate) {
		Map<String, Object> map = new HashMap<>();
		map.put("supplierId", suppliersUpdate.getSupplierId());
		List<SuppliersUpdate> list = findList(map);
		if(CollectionUtils.isNotEmpty(list)){
			SuppliersUpdate suppliersUpdate2 = list.get(0);
			if(suppliersUpdate2.getAuditStatus() != null && suppliersUpdate2.getAuditStatus() == 1){
				return ResultUtil.error("信息正在审核，请勿重复提交");
			}
		}
		if(suppliersUpdate.getUsers() == null){
			return ResultUtil.error("未获取到用户信息");
		}else{
			Users users = suppliersUpdate.getUsers();
			suppliersUpdate.setUserId(users.getId());
			suppliersUpdate.setUserLoginName(users.getLoginName());
			suppliersUpdate.setUserRealName(users.getRealName());
			suppliersUpdate.setUserMobile(users.getMobile());
			suppliersUpdate.setUserEmail(users.getEmail());
		}
		if(suppliersUpdate.getShop() == null){
			return ResultUtil.error("未获取到店铺信息");
		}else{
			Shop shop = suppliersUpdate.getShop();
			suppliersUpdate.setShopId(shop.getId());
			suppliersUpdate.setShopName(shop.getShopName());
			suppliersUpdate.setShopLogo(shop.getLog());
			suppliersUpdate.setCustomerServiceEmail(shop.getCustomerServiceEmail());
			suppliersUpdate.setCustomerServicePhone(shop.getCustomerServicePhone());
		}
		if(suppliersUpdate.getCatalogsId() == null || suppliersUpdate.getCatalogsId().length() == 0){
			return ResultUtil.error("请选择店铺品目");
		}
		suppliersUpdate.setIsDelete(0);
		suppliersUpdate.setSubmitAt(DateUtilEx.timeFormat(new Date()));
		suppliersUpdate.setAuditStatus(1);
		int insertSelective = suppliersUpdateMapper.insertSelective(suppliersUpdate);
		if(insertSelective > 0){
			String catalogsId = suppliersUpdate.getCatalogsId();
			String[] split = catalogsId.split(",");
			if(split != null && split.length > 0){
				for (String l : split) {
					ShopUpdateCatalogs shopUpdateCatalogs = new ShopUpdateCatalogs();
					shopUpdateCatalogs.setSupplierUpdateId(suppliersUpdate.getId());
					shopUpdateCatalogs.setCatalogsId(Long.parseLong(l));
					shopUpdateCatalogsMapper.insertSelective(shopUpdateCatalogs);
				}
			}
			return ResultUtil.success("提交成功");
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> auditUpdate(SuppliersUpdate suppliersUpdate) {
		SuppliersUpdate update = new SuppliersUpdate();
		update.setId(suppliersUpdate.getId());
		update.setAuditStatus(suppliersUpdate.getAuditStatus());
		update.setReason(suppliersUpdate.getReason());
		update.setAuditAt(DateUtilEx.timeFormat(new Date()));
		int audit = suppliersUpdateMapper.updateByPrimaryKeySelective(update);
		if(audit > 0){
			try {
				//如果审核通过  修改原来的值
				Integer auditStatus = update.getAuditStatus();
				if(auditStatus == 2){
					SuppliersUpdate s = suppliersUpdateMapper.selectByPrimaryKey(update.getId());
					//用户信息
					Users user = new Users();
					user.setId(s.getUserId());
					user.setRealName(s.getUserRealName());
					user.setMobile(s.getUserMobile());
					user.setEmail(s.getUserEmail());
					usersMapper.updateByPrimaryKeySelective(user);
					//供应商信息
					Suppliers supplier = new Suppliers();
					supplier.setId(s.getSupplierId());
					supplier.setName(s.getName());
					supplier.setNickName(s.getNickName());
					supplier.setLegalName(s.getLegalName());
					supplier.setLegalIdNumber(s.getLegalIdNumber());
					supplier.setLegalIdCard(s.getLegalIdCard());
					supplier.setContactsName(s.getContactsName());
					supplier.setContactsFax(s.getContactsFax());
					supplier.setContactsPhone(s.getContactsPhone());
					supplier.setRegisterDate(s.getRegisterDate());
					supplier.setCompanyWebsite(s.getCompanyWebsite());
					supplier.setRegisterAddress(s.getRegisterAddress());
					supplier.setRegisterZipCode(s.getRegisterZipCode());
					supplier.setIndustry(s.getIndustry());
					supplier.setCreditCode(s.getCreditCode());
					supplier.setLicenceImg(s.getLicenceImg());
					supplier.setRegisteredCapital(s.getRegisteredCapital());
					supplier.setCurrencyType(s.getCurrencyType());
					supplier.setNature(s.getNature());
					supplier.setLicenceValidityEnd(s.getLicenceValidityEnd());
					supplier.setTaxpayerQualification(s.getTaxpayerQualification());
					supplier.setRemark(s.getRemark());
					supplier.setBusinessScope(s.getBusinessScope());
					supplierMapper.updateByPrimaryKeySelective(supplier);
					
					//店铺信息
					Shop shop = new Shop();
					shop.setId(s.getShopId());
					shop.setShopName(s.getShopName());
					shop.setLog(s.getShopLogo());
					shop.setCustomerServiceEmail(s.getCustomerServiceEmail());
					shop.setCustomerServicePhone(s.getCustomerServicePhone());
					int shopUpdate = shopMapper.updateByPrimaryKeySelective(shop);
					if(shopUpdate > 0){
						//品目信息
						shopCatalogsMapper.deleteByshopId(s.getShopId());
						List<ShopUpdateCatalogs> selectByUpdateId = shopUpdateCatalogsMapper.selectByUpdateId(s.getId());
						if(CollectionUtils.isNotEmpty(selectByUpdateId)){
							for (ShopUpdateCatalogs shopUpdateCatalogs : selectByUpdateId) {
								ShopCatalogs shopCatalogs = new ShopCatalogs();
								shopCatalogs.setCreatedAt(DateUtilEx.timeFormat(new Date()));
								shopCatalogs.setShopId(s.getShopId());
								shopCatalogs.setCatalogsId(shopUpdateCatalogs.getCatalogsId());
								shopCatalogsMapper.insertSelective(shopCatalogs);
							}
						}
					}
					return ResultUtil.success("审核成功");
				}else{
					return ResultUtil.success("审核成功");
				}
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
				e.printStackTrace();
			}
		}
		return ResultUtil.error("审核失败");
	}

	@Override
	public List<PlatformCatalogs> getCatalogsTree(Long supplierId) {
		List<Shop> selectByid = shopMapper.selectByid(supplierId);
		if(CollectionUtils.isNotEmpty(selectByid)){
			Shop shop = selectByid.get(0);
			return platformCatalogsMapper.selectShopCatalogs(shop.getId());
		}
		return null;
	}
}
