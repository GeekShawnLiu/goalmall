package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.attwrap.PriceAttr;
import www.tonghao.mall.api.jd.attwrap.ProductOther;
import www.tonghao.mall.api.jd.attwrap.StateResultAttr;
import www.tonghao.mall.api.jd.call.ProductPricesApi;
import www.tonghao.mall.api.jd.call.ProductStateApi;
import www.tonghao.mall.api.jd.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.jd.resultwrap.ProductStateRes;
import www.tonghao.platform.service.UpperLowerHistoryService;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.EmallCatalogs;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.service.BrandService;
import www.tonghao.service.common.service.EmallCatalogsService;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.SuppliersService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@RestController
@RequestMapping("/emallCatalogs")
@Api(value="EmallCatalogsController",description="品目映射")
public class EmallCatalogsController {

	@Autowired
	private EmallCatalogsService emallCatalogsService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	
	@Autowired
	private SuppliersService suppliersService;
	@Autowired
	private UpperLowerHistoryService upperLowerHistoryService;
	@Autowired
	private BrandMapper brandMapper;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="emallCatalogName",value="电商品目",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="catalogName",value="商城品目",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="isMapping",value="是否映射",required=false,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<EmallCatalogs> getPage(@ModelAttribute PageBean page,String supplierName,String emallCatalogName,String catalogName,String isMapping){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("supplierName", supplierName);
		map.put("emallCatalogName", emallCatalogName);
		map.put("catalogName", catalogName);
		map.put("isMapping", isMapping);
		List<EmallCatalogs> emallCatalogsList = emallCatalogsService.getEmallCatalogsList(map);
		return new PageInfo<EmallCatalogs>(emallCatalogsList);
	}
	

	@ApiOperation(value="匹配品台品目",notes="匹配品台品目api")
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public Map<String, Object> update(Long id,Long catalogId){
		EmallCatalogs ec = emallCatalogsService.selectByKey(id);
		if(ec!=null){
			ec.setCatalogsId(catalogId);
			int updateNotNull = emallCatalogsService.updateNotNull(ec);
			if(updateNotNull>0){
				ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
				newCachedThreadPool.execute(new Runnable() {
					@Override
					public void run() {
                      if(catalogId!=null){
                    	  Example example=new Example(Products.class);
                    	  Criteria createCriteria = example.createCriteria();
                    	  createCriteria.andEqualTo("emallCatalogeId",ec.getEmallCatalogsId());
                    	  List<Products> p_list = productsService.selectByExample(example);
                    	  if(!CollectionUtil.collectionIsEmpty(p_list)){
                    		  for (Products products : p_list) {
                    			  Suppliers supper = suppliersService.selectByKey(products.getSupplierId());
                    			  if(Constant.JD_CODE.equals(supper.getCode())&&products.getIsDelete()==0){
                    				  PlatformCatalogs pc = platformCatalogsService.selectByKey(catalogId);
                    				  products.setCatalogId(catalogId);
                    				  products.setCatalogName(pc.getName());
                    				  Brand brandId = getBrandId(products.getMallBrandName());
                    				  if(brandId!=null){
                    					  products.setBrandId(brandId.getId());
                        				  products.setBrandName(brandId.getName());
                    				  }
                    				  productPrice(products,supper);
    								  productState(products,supper);
    								  if(supper.getIsPrice()==0){
    									  check(products,supper);
    								  }
                    			  }
								  
							}
                    	  }
                      }						
					}
				});
				
				return ResultUtil.success("匹配成功");
			}
		}
		return ResultUtil.error("匹配成功");
	}
	
	@ApiOperation(value="清空品台品目",notes="清空品台品目api")
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public Map<String, Object> delete(Long id){
		EmallCatalogs ec = emallCatalogsService.selectByKey(id);
		if(ec!=null){
			ec.setCatalogsId(null);
			int updateAll = emallCatalogsService.updateAll(ec);
			if(updateAll>0){
				return ResultUtil.success("清空成功");
			}
		}
		return ResultUtil.error("清空失败");
	}
	
	private void productPrice(Products pro,Suppliers supper){
		try {
			ProductPricesApi api=new ProductPricesApi(pro.getSku());
			ProductPriceRes call = api.call();
			if(call.isSuccess()){
				List<PriceAttr> result = call.getResult();
				if(!CollectionUtil.collectionIsEmpty(result)){
					PriceAttr priceAttr = result.get(0);
					pro.setMarketPrice(priceAttr.getJdPrice());
					pro.setPrice(priceAttr.getPrice());
					pro.setProtocolPrice(priceAttr.getPrice());
				}
			}else{
				pro.setStatus(4);
				saveUpperLowerHistory(pro.getId(), 4, supper.getId(), call.getResultMessage());
			}
		} catch (Exception e) {
			pro.setStatus(4);
			saveUpperLowerHistory(pro.getId(), 4, supper.getId(), "价格接口异常");
		}
		productsService.updateNotNull(pro);
		
	}
	
	private void productState(Products pro,Suppliers supper){
		try {
			ProductStateApi api=new ProductStateApi(pro.getSku());
			ProductStateRes call = api.call();
			if(call.isSuccess()){
				List<StateResultAttr> result = call.getResult();
				if(!CollectionUtil.collectionIsEmpty(result)){
					StateResultAttr stateResultAttr = result.get(0);
					if(stateResultAttr.getState()==0){//下架
						pro.setStatus(4);
						saveUpperLowerHistory(pro.getId(), 4, supper.getId(), Constant.EMALL_ERROR);
					}else if(stateResultAttr.getState()==1){//上架
						if(supper.getIsPrice()==0){
							pro.setStatus(3);
							saveUpperLowerHistory(pro.getId(), 3, supper.getId(), Constant.EMALL_SUCCESS);
						}
					}
				}
			}else{
				pro.setStatus(4);
				saveUpperLowerHistory(pro.getId(), 4, supper.getId(), call.getResultMessage());
			}
		} catch (Exception e) {
			pro.setStatus(4);
			saveUpperLowerHistory(pro.getId(), 4, supper.getId(), "上下架接口异常");
		}
		productsService.updateNotNull(pro);
	}
	
	private void saveUpperLowerHistory(Long productId, int type, Long supplierId, String content) {
		UpperLowerHistory history = new UpperLowerHistory();
		history.setCreatedAt(DateUtilEx.timeToDate(new Date(), "YYYY-MM-dd HH:mm:ss"));
		history.setUpdatedAt(DateUtilEx.timeToDate(new Date(), "YYYY-MM-dd HH:mm:ss"));
		history.setType(type);
		history.setReason(content);
		history.setProductId(productId);
		history.setOperateId(supplierId);
		upperLowerHistoryService.save(history);
	}
	public void check(Products pro,Suppliers supplier) {
		if (pro != null) {
			if (pro.getStatus() == 3) {
				if (isNull(pro.getBrandName())) {// 判断商品型号或者品牌
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), Constant.MODEL_ERROR);
				}
				if (pro.getProtocolPrice() == null || pro.getMarketPrice() == null
						|| pro.getProtocolPrice().compareTo(new BigDecimal("0")) == 0) {// 判断价格
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), Constant.PRODUCT_PRICE);
				}
				if (pro.getProtocolPrice() != null && pro.getMarketPrice() != null
						&& pro.getProtocolPrice().compareTo(pro.getMarketPrice()) > 0) {// 判断价格
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), Constant.PRICE_ERROR);
				}

				if (isNull(pro.getDetail())) {// 判断详情
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), Constant.PRODUCT_DETAIL);
				}
				if (isNull(pro.getCoverUrl())) {// 判断主图
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), Constant.PRODUCT_IMAGE);
				}
				if(isNull(pro.getCatalogName())) {
					pro.setStatus(4);
					saveUpperLowerHistory(pro.getId(), 4, supplier.getId(), "品目没映射自动下架");
				}
				productsService.updateNotNull(pro);
			}
		}
	}
	private boolean isNull(String str) {
		if (str == null || "null".equals(str)) {
			return true;
		}
		return false;
	}
	/**
	 * 获取对应品牌
	 * */
	public Brand getBrandId(String emalllBrand) {
		Brand return_brand=new Brand();
		List<Brand> list = brandMapper.findByMappingName(emalllBrand);
		if(!CollectionUtils.isEmpty(list)){
			oth:
				for (Brand brand : list) {
					String mappingName = brand.getMappingName();
					String[] split = mappingName.split("\\|");
					for (String string : split) {
						if(string.equals(emalllBrand)){
							return_brand=brand;
							break oth;
						}
					}
				}
				return return_brand;
		}else{
			return null;
		}
	}
}
