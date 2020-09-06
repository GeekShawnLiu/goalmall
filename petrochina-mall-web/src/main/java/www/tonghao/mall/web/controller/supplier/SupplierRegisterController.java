package www.tonghao.mall.web.controller.supplier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.service.MallCatalogsService;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.entity.DictionaryData;
import www.tonghao.service.common.entity.EnterpriseNature;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Shop;
import www.tonghao.service.common.entity.SupplierBank;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.SupplierBankMapper;
import www.tonghao.service.common.service.AreasService;
import www.tonghao.service.common.service.DictionaryDataService;
import www.tonghao.service.common.service.EnterpriseNatureService;
import www.tonghao.service.common.service.MobileCodeService;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ShopCatalogsService;
import www.tonghao.service.common.service.SupplierRegisterService;

/**
 * @Description:供应商注册
 * @date 2019年6月18日
 */
@Api(value="supplierRegisterController",description="供应商注册")
@RestController
@RequestMapping("/supplierRegister")
public class SupplierRegisterController {
	
	@Autowired
	private SupplierRegisterService supplierRegisterService;
	
	@Value("${smsurl}")
	private String smsurl;
	
	@Autowired
	private MobileCodeService mobileCodeService;
	
	@Autowired
	private AreasService areasService;
	
	@Autowired
	private EnterpriseNatureService enterpriseNatureService;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	
	@Autowired
	private MallCatalogsService mallCatalogsService;
	
	@Autowired
	private ShopCatalogsService shopCatalogsService;
	
	@Autowired
	private SupplierBankMapper supplierBankMapper;
	
	@Autowired
	private DictionaryDataService dictionaryDataService;
	
	/**
	 * @Description:注册
	 * @date 2019年6月18日
	 */
	@ApiOperation(value="提交")
	@PostMapping("/registerInfo")
	public Map<String, Object> registerInfo(Users user, Suppliers supplier, Shop shop){
		Map<String, Object> result = supplierRegisterService.registerInfo(user, supplier, shop);
		return result;
	}
	
	/**
	 * @Description:校验用户信息
	 * @date 2019年6月20日
	 */
	@ApiOperation(value="校验第一步用户信息")
	@PostMapping("/checkUserInfo")
	public Map<String, Object> checkUserInfo(Users user){
		Map<String, Object> map = supplierRegisterService.checkUserInfo(user);
		if(MapUtils.isNotEmpty(map)){
			return ResultUtil.resultMessage(ResultUtil.ERROR, "", map);
		}
		return ResultUtil.success("");
	}
	
	/**
	 * @Description:供应商信息
	 * @date 2019年6月20日
	 */
	@ApiOperation(value="校验第二步供应商信息")
	@PostMapping("/checkSupplierInfo")
	public Map<String, Object> checkSupplierInfo(Suppliers supplier){
		Map<String, Object> map = supplierRegisterService.checkSupplierInfo(supplier);
		if(MapUtils.isNotEmpty(map)){
			return ResultUtil.resultMessage(ResultUtil.ERROR, "", map);
		}
		return ResultUtil.success("");
	}
	
	@ApiOperation(value="校验第三步店铺信息")
	@PostMapping("/checkShopInfo")
	public Map<String, Object> checkShopInfo(Shop shop){
		Map<String, Object> map = supplierRegisterService.checkShopInfo(shop);
		if(MapUtils.isNotEmpty(map)){
			return ResultUtil.resultMessage(ResultUtil.ERROR, "", map);
		}
		return ResultUtil.success("");
	}
	
	/**
	 * @Description:获取验证码
	 * @date 2019年6月18日
	 */
	@ApiOperation(value="获取验证码")
	@ApiImplicitParams({
		@ApiImplicitParam(name="mobile",value="手机号",required=true,dataType="string")
	})
	@GetMapping("/getMobileCode")
	public Map<String,Object> sendMobile(String mobile){
		return mobileCodeService.sendMobile(mobile, smsurl);
	}
	
	/**
	 * @Description:校验验证码是否有效
	 * @date 2019年6月18日
	 */
	@GetMapping("/checkMobileCode")
	public Map<String,Object> checkMobileCode(String mobile){
		if(mobile !=null && redisDao.isNotKey(mobile)){
			return ResultUtil.success("");
		}else{
			return ResultUtil.error("请重新发送验证码");
		}
	}
	
	/**
	 * @Description:获取地区
	 * @date 2019年6月21日
	 */
	@ApiOperation(value="获取地区")
	@ApiImplicitParams({
		@ApiImplicitParam(name="parentId",value="上级ID",required=false,dataType="long",paramType="query"),
	})
	@RequestMapping(value="/getArea",method=RequestMethod.GET)
	public List<Areas> getArea(Long parentId){
		Example example = new Example(Areas.class);
		Criteria criteria = example.createCriteria();
		if (parentId == null) {
			criteria.andEqualTo("treeDepth", 2);
		}else {
			criteria.andEqualTo("parentId", parentId);
		}
		example.setOrderByClause("id asc");
		List<Areas> list = areasService.selectByExample(example);
		return list;
	}
	
	/**
	 * @Description:
	 * @date 2019年6月21日
	 */
	@ApiOperation(value="获取企业性质")
	@GetMapping("/getEnterpriseNatureList")
	public List<EnterpriseNature> getEnterpriseNatureList(){
		List<EnterpriseNature> selectByExample = enterpriseNatureService.selectByExample(null);
		return selectByExample;
	}
	
	/**
	 * @Description:获取供应商信息（包含用户和店铺）
	 * @date 2019年6月24日
	 */
	@ApiOperation(value="获取供应商信息（包含用户和店铺）")
	@ApiImplicitParams({
		@ApiImplicitParam(name="supplierId",value="供应商id",required=true,dataType="long",paramType="query"),
	})
	@GetMapping("/getSupplierInfo")
	public Suppliers getSupplierInfo(Long supplierId){
		Suppliers selectSupplierInfo = supplierRegisterService.selectSupplierInfo(supplierId);
		return selectSupplierInfo;
	}
	
	/**
	 * @Description:修改提交审核
	 * @date 2019年7月8日
	 */
	@ApiOperation(value="修改提交")
	@PostMapping("/updateInfo")
	public Map<String, Object> updateInfo(Users user, Suppliers supplier, Shop shop, Long supplierId, Long userId, Long shopId){
		if(supplierId ==null || userId ==null || shopId ==null){
			return ResultUtil.error("对不起，更新失败");
		}
		
		supplier.setId(supplierId);
		user.setId(userId);
		shop.setId(shopId);
		Map<String, Object> result = supplierRegisterService.updateInfo(user, supplier, shop);
		return result;
	}
	
	/**
	 * @Description:经营范围
	 * @date 2019年7月9日
	 */
	@ApiOperation(value="获取品目")
	@RequestMapping(value="/getPlatformCatalogsAll",method={RequestMethod.GET})
	public List<PlatformCatalogs> getPlatformCatalogsAll(Long shopId){
		/*Example example = new Example(PlatformCatalogs.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("isDelete", 0);
		List<PlatformCatalogs> platformCatalogsList = platformCatalogsService.selectByExample(example);
		
		if(shopId !=null && CollectionUtils.isNotEmpty(platformCatalogsList)){
			for(PlatformCatalogs platformCatalogs : platformCatalogsList){
				ShopCatalogs sc = new ShopCatalogs();
				sc.setShopId(shopId);
				sc.setCatalogsId(platformCatalogs.getId());
				ShopCatalogs selectEntityOne = shopCatalogsService.selectEntityOne(sc);
				if(selectEntityOne !=null){
					platformCatalogs.setChecked(true);
				}else{
					platformCatalogs.setChecked(false);
				}
			}
		}
		return platformCatalogsList;*/
		return platformCatalogsService.selectShopCatalogs(shopId);
	}
	
	/**
	 * @Description:
	 * @date 2019年7月9日
	 */
	@ApiOperation(value="获取注册地址")
	@RequestMapping(value="/getRegisterAddress",method={RequestMethod.GET})
	public Map<String, Object> getRegisterAddress(Long threeAreaId){
		Areas areas = new Areas();
		Map<String, Object> map = new HashMap<>();
		//区
		Areas area = areasService.selectByKey(threeAreaId);
		map.put("areaId", area.getId());
		
		//市
		areas.setId(area.getParentId());
		Areas city = areasService.selectEntityOne(areas);
		map.put("cityId", city.getId());
		
		//省
		areas.setId(city.getParentId());
		Areas province = areasService.selectEntityOne(areas);
		map.put("provinceId", province.getId());
			
		//所有省
		Example example = new Example(Areas.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("treeDepth", 2);
		List<Areas> provinceList = areasService.selectByExample(example);
		map.put("provinceList", provinceList);
		
		//该省下所有市
		Example example1 = new Example(Areas.class);
		Criteria createCriteria1 = example1.createCriteria();
		createCriteria1.andEqualTo("parentId", province.getId());
		List<Areas> cityList = areasService.selectByExample(example1);
		map.put("cityList", cityList);
		
		//该市下所有区
		Example example2 = new Example(Areas.class);
		Criteria createCriteria2 = example2.createCriteria();
		createCriteria2.andEqualTo("parentId", city.getId());
		List<Areas> areaList = areasService.selectByExample(example2);
		map.put("areaList", areaList);
		return map;
	}
	
	/**
	 * @Description:保存供应商银行信息
	 * @date 2019年7月25日
	 */
	@ApiOperation(value="保存供应商银行信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="openingBank",value="开户行",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="supplierId",value="供应商id",required=true,dataType="long",paramType="query"),
		@ApiImplicitParam(name="accountName",value="开户行账号名",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="accountNum",value="开户行账号",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="areasId",value="银行所在地",required=true,dataType="long",paramType="query"),
		@ApiImplicitParam(name="subBranchName",value="开户行支行名称",required=true,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/saveSupplierBankInfo", method={RequestMethod.GET})
	public Map<String, Object> saveSupplierBankInfo(SupplierBank supplierBank){
		if(supplierBank.getSupplierId() ==null){
			return ResultUtil.error("保存失败");
		}
		
		Suppliers supplier = supplierRegisterService.selectByKey(supplierBank.getSupplierId());
		if(supplier !=null && supplier.getStatus() == 1){
			if((supplier.getIsFillBank() == 0 && supplierBank.getId() == null) || (supplier.getIsFillBank() == 1 && supplierBank.getId() != null)){
				Map<String, Object> result = supplierRegisterService.saveOrUpdateSupplierBankInfo(supplierBank);
				return result;
			}else{
				return ResultUtil.error("已经填写过银行信息，不可重复填写");
			}
		}
		return ResultUtil.error("保存失败");
	}
	
	
	/**
	 * @Description:获取供应商银行信息
	 * @date 2019年7月25日
	 */
	@ApiOperation(value="获取供应商银行信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="supplierId",value="supplierId",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getSupplierBankById", method={RequestMethod.GET})
	public SupplierBank getSupplierBankById(Long supplierId){
		SupplierBank supplierBank = supplierBankMapper.selectBySupplierId(supplierId);
		if(supplierBank != null && supplierBank.getAreasId() != null){
			Areas selectByKey = areasService.selectByKey(supplierBank.getAreasId());
			if(selectByKey != null){
				String treeIds = selectByKey.getTreeIds();
				supplierBank.setAreaTreeIds(treeIds);
				supplierBank.setAreaTreeNames(selectByKey.getTreeNames());
			}
		}
		return supplierBank;
	}
	
	@ApiOperation(value="获取币种信息")
	@RequestMapping(value="/getCurrency", method=RequestMethod.GET)
	public List<DictionaryData> getCurrency(){
		return dictionaryDataService.getDictionaryDataByType("BZ");
	}
}
