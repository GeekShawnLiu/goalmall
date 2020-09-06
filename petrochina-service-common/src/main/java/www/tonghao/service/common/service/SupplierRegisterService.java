package www.tonghao.service.common.service;

import java.util.Map;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Shop;
import www.tonghao.service.common.entity.SupplierBank;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;

/**
 * @Description:供应商注册
 * @date 2019年6月18日
 */
public interface SupplierRegisterService extends BaseService<Suppliers>{ 
	
	/**
	 * @Description:注册（提交审核）
	 * @date 2019年6月20日
	 */
	public Map<String, Object> registerInfo(Users user, Suppliers supplier, Shop shop);
	
	/**
	 * @Description:校验用户信息
	 * @date 2019年6月20日
	 */
	public Map<String, Object> checkUserInfo(Users user);
	
	/**
	 * @Description:校验供应商信息
	 * @date 2019年6月20日
	 */
	public Map<String, Object> checkSupplierInfo(Suppliers supplier);
	
	/**
	 * @Description:校验店铺信息
	 * @date 2019年6月20日
	 */
	public Map<String, Object> checkShopInfo(Shop shop);
	
	/**
	 * @Description:查询供应商信息（包含用户和店铺）
	 * @date 2019年6月24日
	 */
	Suppliers selectSupplierInfo(Long id);
	
	/**
	 * @Description:修改信息
	 * @date 2019年7月8日
	 */
	public Map<String, Object> updateInfo(Users user, Suppliers supplier, Shop shop);
	
	/**
	 * @Description:保存供应商银行信息
	 * @date 2019年7月25日
	 */
	public Map<String, Object> saveOrUpdateSupplierBankInfo(SupplierBank supplierBank);
	
}
