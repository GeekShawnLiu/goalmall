package www.tonghao.service.common.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.crypt.BCrypt;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.ValidatorUtil;
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.Shop;
import www.tonghao.service.common.entity.ShopCatalogs;
import www.tonghao.service.common.entity.SupplierBank;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UserRoles;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.RolesMapper;
import www.tonghao.service.common.mapper.ShopCatalogsMapper;
import www.tonghao.service.common.mapper.ShopMapper;
import www.tonghao.service.common.mapper.SupplierBankMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.mapper.UserRolesMapper;
import www.tonghao.service.common.mapper.UsersMapper;
import www.tonghao.service.common.service.SupplierRegisterService;

/**
 * @Description:供应注册
 * @date 2019年6月18日
 */
@Service("supplierRegisterService")
public class SupplierRegisterServiceImpl extends BaseServiceImpl<Suppliers> implements SupplierRegisterService{

	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private RolesMapper rolesMapper;
	
	@Autowired
	private SuppliersMapper supplierMapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private ShopCatalogsMapper shopCatalogsMapper;
	
	@Autowired
	private SupplierBankMapper supplierBankMapper;
	@Autowired
	private UserRolesMapper userRolesMapper;
	
	
	/**
	 * 注册（提交审核）
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> registerInfo(Users user, Suppliers supplier, Shop shop) {
		Map<String, Object> checkUserInfo = checkUserInfo(user);
		Map<String, Object> checkSupplierInfo = checkSupplierInfo(supplier);
		Map<String, Object> checkShopInfo = checkShopInfo(shop);
		if(MapUtils.isNotEmpty(checkUserInfo) || MapUtils.isNotEmpty(checkSupplierInfo) || MapUtils.isNotEmpty(checkShopInfo)){
			return ResultUtil.error("校验不通过，请仔细检查");
		}
		String newDate = DateUtilEx.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		Long[] catalogsIds = shop.getCatalogsId();//品目
		
		/**
		 * 供应商信息
		 */
		supplier.setCreatedAt(newDate);
		supplier.setStatus(0);
		supplier.setSubmissionTime(DateUtilEx.timeFormat(new Date()));
		int supplierResult = supplierMapper.insertSelective(supplier);
		Long supplierId = supplier.getId();
		if(supplierResult >0){
			/**
			 * 用户信息
			 */
			user.setTypeId(supplierId);
			user.setCreatedAt(newDate);
			String encryptedPassword = BCrypt.hashpw(user.getEncryptedPassword(), BCrypt.gensalt());
			user.setEncryptedPassword(encryptedPassword);
			user.setType(4);//4供应商
			user.setIsKey(1);
			Roles entity=new Roles();
			entity.setCode("BD_GYS");
			Roles roles = rolesMapper.selectOne(entity);
			int userResult = usersMapper.insertSelective(user);
			if(userResult > 0){
				if(roles!=null){
					UserRoles ur=new UserRoles();
					ur.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					ur.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					ur.setRoleId(roles.getId());
					ur.setUserId(user.getId());
					userRolesMapper.insertSelective(ur);
				}
				Suppliers su = new Suppliers();
				su.setId(supplierId);
				su.setUserId(user.getId());
				su.setCreatedAt(newDate);
				supplierMapper.updateByPrimaryKeySelective(su);
				
				/**
				 * 店铺信息
				 */
				shop.setSupplierId(supplierId);
				shop.setCreatedAt(newDate);
				int shopResult = shopMapper.insertSelective(shop);
				
				//店铺id
				Long shopId = shop.getId();
				if(shopResult >0 && catalogsIds !=null){
					List<Long> asList = Arrays.asList(catalogsIds);
					if(asList !=null && asList.size() >0){
						for(Long catalogsId : asList){
							ShopCatalogs shopCatalogs = new ShopCatalogs();
							shopCatalogs.setCreatedAt(newDate);
							shopCatalogs.setShopId(shopId);
							shopCatalogs.setCatalogsId(catalogsId);
							shopCatalogsMapper.insertSelective(shopCatalogs);
						}
					}
					
					Map<String, Object> dataMap = new HashMap<>();
					dataMap.put("supplierId", supplierId);
					return ResultUtil.resultMessage(HttpResponseCode.OK, "提交成功", dataMap);
				}
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
		return ResultUtil.error("提交失败");
	}
	
	/**
	 * @Description:校验用户信息
	 * @date 2019年6月18日
	 */
	@Override
	public Map<String, Object> checkUserInfo(Users user){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(user.getLoginName())){
			map.put("error_loginName", "请填写用户名");
		}
		if(StringUtils.isBlank(user.getRealName())){
			map.put("error_realName", "请填写姓名");
		}
		if(StringUtils.isNotBlank(user.getMobile())){
			boolean moblie = ValidatorUtil.isMoblie(user.getMobile());
			if(!moblie){
				map.put("error_mobile", "手机号格式不正确");
			}
		}else{
			map.put("error_mobile", "请填写手机号");
		}
		if(StringUtils.isBlank(user.getMobileCode())){
			map.put("error_mobileCode", "请填写验证码");
		}
		
		if(StringUtils.isNotBlank(user.getEmail())){
			boolean email = ValidatorUtil.isEmail(user.getEmail());
			if(!email){
				map.put("error_email", "邮箱格式不正确");
			}
		}
		
		String encryptedPassword = user.getEncryptedPassword();
		String confirmPassword = user.getConfirmPassword();
		boolean flag = true;
		if(StringUtils.isBlank(encryptedPassword)){
			flag = false;
			map.put("error_encryptedPassword", "请填写密码");
		}
		if(StringUtils.isBlank(confirmPassword)){
			flag = false;
			map.put("error_confirmPassword", "请填写确认密码");
		}
		if(flag && !encryptedPassword.equals(confirmPassword)){
			map.put("error_confirmPassword", "两次密码不一致");
		}
		return map;
	}
	
	/**
	 * @Description:校验供应商信息
	 * @date 2019年6月19日
	 */
	@Override
	public Map<String, Object> checkSupplierInfo(Suppliers supplier){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(supplier.getName())){
			map.put("error_supplierName", "请填写供应商名称");
		}
		if(StringUtils.isBlank(supplier.getNickName())){
			map.put("error_nickName", "请填写简称");
		}
		if(StringUtils.isBlank(supplier.getLegalName())){
			map.put("error_legalName", "请填写法人姓名");
		}
		/*if(StringUtils.isBlank(supplier.getLegalIdNumber())){
			map.put("error_legalIdNumber", "请填写法人身份证号");
		}
		if(StringUtils.isBlank(supplier.getLegalIdCard())){
			map.put("error_legalIdCard", "请上传法人身份证复印件");
		}*/
		if(StringUtils.isBlank(supplier.getContactsName())){
			map.put("error_contactsName", "请填写联系人姓名");
		}
		if(StringUtils.isBlank(supplier.getContactsPhone())){
			map.put("error_contactsPhone", "请填写联系人电话");
		}
		if(supplier.getRegisterAddressId() ==null){
			map.put("error_registerAddressId", "请选择企业注册地址");
		}
		if(StringUtils.isBlank(supplier.getCreditCode())){
			map.put("error_creditCode", "请填写统一社会信用代码");
		}
		if(StringUtils.isBlank(supplier.getIndustry())){
			map.put("error_industry", "请填写所属行业");
		}
		if(StringUtils.isBlank(supplier.getLicenceImg())){
			map.put("error_licenceImg", "请上传营业执照");
		}
		if(StringUtils.isBlank(supplier.getCurrencyType())){
			map.put("error_currencyType", "请填写注册币种");
		}
		if(supplier.getRegisteredCapital() ==null){
			map.put("error_registeredCapital", "请填写注册资本");
		}
		if(supplier.getNature() ==null){
			map.put("error_nature", "请选择企业性质");
		}
		if(StringUtils.isBlank(supplier.getLicenceValidity()) || StringUtils.isBlank(supplier.getLicenceValidityEnd())){
			map.put("error_licenceValidity", "请选择营业期限");
		}
		if(StringUtils.isBlank(supplier.getTaxpayerQualification())){
			map.put("error_taxpayerQualification", "请填写一般纳税人资质");
		}
		if(StringUtils.isBlank(supplier.getBusinessScope())){
			map.put("error_businessScope", "请填写经营范围");
		}
		if(StringUtils.isBlank(supplier.getRemark())){
			map.put("error_remark", "请填写企业简介");
		}
		return map;
	}
	
	/**
	 * @Description:校验店铺信息
	 * @date 2019年6月19日
	 */
	@Override
	public Map<String, Object> checkShopInfo(Shop shop){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(shop.getShopName())){
			map.put("error_shopName", "店铺名称");
		}
		if(StringUtils.isBlank(shop.getLog())){
			map.put("error_shopLog", "请上传店铺logo");
		}
		if(StringUtils.isNotBlank(shop.getCustomerServiceEmail())){
			boolean email = ValidatorUtil.isEmail(shop.getCustomerServiceEmail());
			if(!email){
				map.put("error_customerServiceEmail", "客服邮箱格式不正确");
			}
		}else{
			map.put("error_customerServiceEmail", "请填写客服邮箱");
		}
		if(StringUtils.isBlank(shop.getCustomerServicePhone())){
			map.put("error_customerServicePhone", "客服联系电话");
		}
		if(!(shop.getCatalogsId() !=null && shop.getCatalogsId().length >0)){
			map.put("error_catalogsId", "请选择品目");
		}
		
		return map;
	}

	@Override
	public Suppliers selectSupplierInfo(Long id) {
		Suppliers supplier = supplierMapper.selectSupplierInfo(id);
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
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> updateInfo(Users user, Suppliers supplier, Shop shop) {
		//Map<String, Object> checkUserInfo = checkUserInfo(user);
		Map<String, Object> checkSupplierInfo = checkSupplierInfo(supplier);
		Map<String, Object> checkShopInfo = checkShopInfo(shop);
		if(MapUtils.isNotEmpty(checkSupplierInfo) || MapUtils.isNotEmpty(checkShopInfo)){
			return ResultUtil.error("校验不通过，请仔细检查");
		}
		
		String newDate = DateUtilEx.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		/**
		 * 更新用户
		 */
		user.setUpdatedAt(newDate);
		String encryptedPassword = BCrypt.hashpw(user.getEncryptedPassword(), BCrypt.gensalt());
		user.setEncryptedPassword(encryptedPassword);
		int userResult = usersMapper.updateByPrimaryKeySelective(user);
		if(userResult > 0){
			/**
			 * 更新供应商
			 */
			supplier.setStatus(0);//0提交审核
			supplier.setUpdatedAt(newDate);
			supplier.setSubmissionTime(newDate);
			int supplierResult = supplierMapper.updateByPrimaryKeySelective(supplier);
			if(supplierResult >0){
				/**
				 * 更新店铺
				 */
				shop.setUpdatedAt(newDate);
				int shopResult = shopMapper.updateByPrimaryKeySelective(shop);
				if(shopResult >0){
					Example example  = new Example(ShopCatalogs.class);
					Criteria createCriteria = example.createCriteria();
					createCriteria.andEqualTo("shopId", shop.getId());
					int deleteByExample = shopCatalogsMapper.deleteByExample(example);
					if(deleteByExample >0 && shop.getCatalogsId() !=null){
						List<Long> asList = Arrays.asList(shop.getCatalogsId());
						if(asList !=null && asList.size() >0){
							for(Long catalogsId : asList){
								ShopCatalogs shopCatalogs = new ShopCatalogs();
								shopCatalogs.setCreatedAt(newDate);
								shopCatalogs.setShopId(shop.getId());
								shopCatalogs.setCatalogsId(catalogsId);
								shopCatalogsMapper.insertSelective(shopCatalogs);
							}
							
							Map<String, Object> dataMap = new HashMap<>();
							dataMap.put("supplierId", supplier.getId());
							return ResultUtil.resultMessage(HttpResponseCode.OK, "提交成功", dataMap);
						}
					}
				}
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
		return ResultUtil.error("提交失败");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> saveOrUpdateSupplierBankInfo(SupplierBank supplierBank) {
		Map<String, Object> map = new HashMap<>();
		if(StringUtils.isBlank(supplierBank.getOpeningBank())){
			map.put("error_openingBank", "开户银行不能为空");
		}
		if(StringUtils.isBlank(supplierBank.getAccountName())){
			map.put("error_openingBank", "银行开户账号名称不能为空");
		}
		if(StringUtils.isBlank(supplierBank.getAccountNum())){
			map.put("error_openingBank", "银行账号不能为空");
		}
		if(supplierBank .getAreasId() ==null){
			map.put("error_openingBank", "银行所在地不能为空");
		}
		if(StringUtils.isBlank(supplierBank.getSubBranchName())){
			map.put("error_openingBank", "开户行支行名称不能为空");
		}
		
		if(MapUtils.isNotEmpty(map)){
			return ResultUtil.resultMessage(HttpResponseCode.NOT_ACCEPTABLE, "校验失败", map);
		}
		int result = 0;
		if(supplierBank.getId() == null){
			supplierBank.setCreatedAt(DateUtilEx.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			result = supplierBankMapper.insertSelective(supplierBank);
		}else{
			supplierBank.setUpdatedAt(DateUtilEx.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			result = supplierBankMapper.updateByPrimaryKeySelective(supplierBank);
		}
		if(result > 0){
			Suppliers supplier = new Suppliers();
			supplier.setId(supplierBank.getSupplierId());
			supplier.setIsFillBank(1);
			supplier.setUpdatedAt(DateUtilEx.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			int update = supplierMapper.updateByPrimaryKeySelective(supplier);
			if(update > 0){
				return ResultUtil.resultMessage(result, "");
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
		return ResultUtil.error("");
	}
}
