package www.tonghao.platform.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import www.tonghao.common.constant.RedisKeyConstant;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.service.UpperLowerHistoryService;
import www.tonghao.service.common.entity.ProductChangePrice;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.ProductsList;
import www.tonghao.service.common.entity.UpperLowerHistory;
import www.tonghao.service.common.mapper.UpperLowerHistoryMapper;
import www.tonghao.service.common.service.ProductChangePriceService;
import www.tonghao.service.common.service.ProductsService;

@RestController
@RequestMapping("/changeProductPrice")
@Api(value="ChangeProductPriceController",description="商品改价")
public class ChangeProductPriceController {

	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private ProductChangePriceService productChangePriceService;
	
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private UpperLowerHistoryService upperLowerHistoryService;
	
	/**
	 * 
	 * @param page
	 * @param productName 商品名称
	 * @param brandName  品牌名称
	 * @param model  型号
	 * @param beginOldDiscount  开始调价前折扣率
	 * @param endOldDiscount   结束调价前折扣率
	 * @param supplierName   供应商名称
	 * @param protocolName   协议名称
	 * @param sku   sku
	 * @param ischangePrice  是否调价  0否 1是
	 * @param catalogName   品目名称
	 * @return
	 */
	@ApiOperation(value="分页查询",notes="分页查询改价api")
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public PageInfo<Products> getPage(@ModelAttribute PageBean page,String productName,String brandName,String model,Float beginOldDiscount,Float endOldDiscount,String supplierName,String protocolName,String sku,String ischangePrice,String catalogId,Float beginNewDiscount,Float endNewDiscount){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("productName", productName);
		map.put("brandName", brandName);
		map.put("model", model);
		map.put("beginOldDiscount", beginOldDiscount);
		map.put("endOldDiscount", endOldDiscount);
		map.put("beginNewDiscount", beginNewDiscount);
		map.put("endNewDiscount", endNewDiscount);
		map.put("supplierName", supplierName);
		map.put("protocolName", protocolName);
		map.put("sku", sku);
		map.put("ischangePrice", ischangePrice);
		map.put("catalogId", catalogId);
		List<Products> product_list = productsService.selectChangePrice(map);
		return new PageInfo<>(product_list);
	}
	
	
	@SuppressWarnings("finally")
	@ApiOperation(value="单个商品改价",notes="单个商品改价api")
	@RequestMapping(value="/updatePrice",method=RequestMethod.POST)
	public Map<String,Object> updatePrice(@RequestBody Products products){
		boolean value= redisDao.setNx(RedisKeyConstant.UPDATEBATCHPRICE_KEY,"true");
		if(!value){
			return ResultUtil.resultMessage(0, "有调价程序正在运行，请稍后再试！");
		}else{
			int updatePrice=0;
			try {
				updatePrice = productsService.updatePrice(products);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				redisDao.deleteKey(RedisKeyConstant.UPDATEBATCHPRICE_KEY);
			}
			if(updatePrice>0) {
				return ResultUtil.resultMessage(updatePrice, "调价成功");
			}else {
				return ResultUtil.resultMessage(0, "调价失败");
			}
		}
		
	}

	
	@SuppressWarnings("finally")
	@ApiOperation(value="批量改价所有",notes="批量改价所有api")
	@RequestMapping(value="/updateBatchPriceResult",method=RequestMethod.GET)
	public Map<String,Object> updateBatchPriceResult(String productName,String brandName,String model,Float beginOldDiscount,Float endOldDiscount,String supplierName,String protocolName,String sku,String ischangePrice,String catalogId,Float beginNewDiscount,Float endNewDiscount,Float newDiscount){
		boolean value= redisDao.setNx(RedisKeyConstant.UPDATEBATCHPRICE_KEY,"true");
		if(!value){
			return ResultUtil.resultMessage(0, "有调价程序正在运行，请稍后再试！");
		}else {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("productName", productName);
			map.put("brandName", brandName);
			map.put("model", model);
			map.put("beginOldDiscount", beginOldDiscount);
			map.put("endOldDiscount", endOldDiscount);
			map.put("beginNewDiscount", beginNewDiscount);
			map.put("endNewDiscount", endNewDiscount);
			map.put("supplierName", supplierName);
			map.put("protocolName", protocolName);
			map.put("sku", sku);
			map.put("ischangePrice", ischangePrice);
			map.put("catalogId", catalogId);
			map.put("newDiscount", newDiscount);
			System.out.println(map);
			int result_num =0;
			try {
				result_num = productsService.updateBatchPriceResult(map);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				redisDao.deleteKey(RedisKeyConstant.UPDATEBATCHPRICE_KEY);
			}
			if(result_num>0) {
				return ResultUtil.resultMessage(result_num, "调价成功");
			}else {
				return ResultUtil.resultMessage(result_num, "调价失败");
			}
		}
	}
	
	
	@SuppressWarnings("finally")
	@ApiOperation(value="批量改价",notes="批量商品改价api")
	@RequestMapping(value="/updateBatchPrice",method=RequestMethod.POST)
	public Map<String,Object> updateBatchPrice(@RequestBody ProductsList productList){
		boolean value= redisDao.setNx(RedisKeyConstant.UPDATEBATCHPRICE_KEY,"true");
		if(!value){
			return ResultUtil.resultMessage(0, "有调价程序正在运行，请稍后再试！");
		}else {
			Map<String, Object> updateBatchPrice=null;
			try {
				updateBatchPrice = productsService.updateBatchPrice(productList);	
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				redisDao.deleteKey(RedisKeyConstant.UPDATEBATCHPRICE_KEY);
			}
			if(updateBatchPrice!=null){
				int result_num = (int) updateBatchPrice.get("result");
				if(result_num>0) {
					return ResultUtil.resultMessage(result_num, "调价成功");
				}else {
					return ResultUtil.resultMessage(result_num, updateBatchPrice.get("message").toString());
				}
			}else{
				return ResultUtil.resultMessage(0, "调价失败");
			}
		}
	}
	
	/**
	 * 
	 * @param page
	 * @param productName 商品名称
	 * @param brandName  品牌名称
	 * @param model  型号
	 * @param beginOldDiscount  开始调价前折扣率
	 * @param endOldDiscount   结束调价前折扣率
	 * @param supplierName   供应商名称
	 * @param protocolName   协议名称
	 * @param sku   sku
	 * @param catalogName   品目名称
	 * @return
	 */
	@ApiOperation(value="分页查询",notes="分页查询改价api")
	@RequestMapping(value="/toPutawaylist",method=RequestMethod.GET)
	public PageInfo<Products> toPutawaylist(@ModelAttribute PageBean page,String productName,String brandName,String model,String supplierName,String protocolName,String sku,String status,String catalogId){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("productName", productName);
		map.put("brandName", brandName);
		map.put("model", model);
		map.put("supplierName", supplierName);
		map.put("protocolName", protocolName);
		map.put("sku", sku);
		map.put("catalogId", catalogId);
		map.put("status", status);
		List<Products> product_list = productsService.selectproductPutaway(map);
		return new PageInfo<>(product_list);
	}
	
	@ApiOperation(value="单个上架",notes="单个上架api")
	@RequestMapping(value="/toUp",method=RequestMethod.GET)
	public Map<String,Object> toUp(Long id){
		if(id==null){
			return ResultUtil.resultMessage(0, "上架失败");
		}
		boolean value= redisDao.setNx(RedisKeyConstant.TOPUTAWAY_KEY,"true");
		if(!value){
			return ResultUtil.resultMessage(0, "有上架程序正在运行，请稍后再试！");
		}else{
			int result_num=0;
			try {
				result_num= productsService.updateToUp(id);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				redisDao.deleteKey(RedisKeyConstant.TOPUTAWAY_KEY);
			}
			if(result_num>0) {
				return ResultUtil.resultMessage(result_num, "上架成功");
			}else {
				return ResultUtil.resultMessage(result_num, "上架失败");
			}
		}
    }
	
	@ApiOperation(value="批量上架",notes="批量上架api")
	@RequestMapping(value="/toUpBatch",method=RequestMethod.GET)
	public Map<String,Object> toUpBatch(String ids){
		if(StringUtils.isBlank(ids)){
			return ResultUtil.resultMessage(0, "上架失败");
		}
		boolean value= redisDao.setNx(RedisKeyConstant.TOPUTAWAY_KEY,"true");
		if(!value){
			return ResultUtil.resultMessage(0, "有上架程序正在运行，请稍后再试！");
		}else{
			int result_num =0;
			try {
				result_num = productsService.updateToUpBatch(ids);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				redisDao.deleteKey(RedisKeyConstant.TOPUTAWAY_KEY);
			}
			if(result_num>0) {
				return ResultUtil.resultMessage(result_num, "上架成功");
			}else {
				return ResultUtil.resultMessage(result_num, "上架失败");
			}
		}
    }
	@ApiOperation(value="批量下架",notes="批量下架api")
	@RequestMapping(value="/toDownBatch",method=RequestMethod.GET)
	public Map<String,Object> toDownBatch(String ids){
		if(StringUtils.isBlank(ids)){
			return ResultUtil.resultMessage(0, "下架失败");
		}
		boolean value= redisDao.setNx(RedisKeyConstant.TODOWN_KEY,"true");
		if(!value){
			return ResultUtil.resultMessage(0, "有下架程序正在运行，请稍后再试！");
		}else{
			int result_num =0;
			try {
				String[] id = ids.split(",");
				for (String str : id) {
					Products pro = productsService.selectByKey(Long.parseLong(str));
					pro.setStatus(4);
					pro.setIsShow(4);
					result_num=productsService.updateNotNull(pro);
					if(result_num>0){
						UpperLowerHistory history=new UpperLowerHistory();
						history.setCreatedAt(DateUtilEx.timeToDate(new Date(), "YYYY-MM-dd HH:mm:ss"));
						history.setUpdatedAt(DateUtilEx.timeToDate(new Date(), "YYYY-MM-dd HH:mm:ss"));
						history.setType(4);
						history.setReason("运营手动下架");
						history.setProductId(pro.getId());
						history.setOperateId(pro.getSupplierId());
						upperLowerHistoryService.saveSelective(history);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				redisDao.deleteKey(RedisKeyConstant.TODOWN_KEY);
			}
			if(result_num>0) {
				return ResultUtil.resultMessage(result_num, "下架成功");
			}else {
				return ResultUtil.resultMessage(result_num, "下架失败");
			}
		}
    }
	
	@ApiOperation(value="单个下架",notes="单个下架api")
	@RequestMapping(value="/toDown",method=RequestMethod.GET)
	public Map<String,Object> toDown(Long id){
		if(id==null){
			return ResultUtil.resultMessage(0, "下架失败");
		}
		boolean value= redisDao.setNx(RedisKeyConstant.TODOWN_KEY,"true");
		if(!value){
			return ResultUtil.resultMessage(0, "有下架程序正在运行，请稍后再试！");
		}else{
			int result_num=0;
			try {
				Products pro = productsService.selectByKey(id);
				pro.setStatus(4);
				pro.setIsShow(4);
				result_num=productsService.updateNotNull(pro);
				if(result_num>0){
					UpperLowerHistory history=new UpperLowerHistory();
					history.setCreatedAt(DateUtilEx.timeToDate(new Date(), "YYYY-MM-dd HH:mm:ss"));
					history.setUpdatedAt(DateUtilEx.timeToDate(new Date(), "YYYY-MM-dd HH:mm:ss"));
					history.setType(4);
					history.setReason("运营手动下架");
					history.setProductId(pro.getId());
					history.setOperateId(pro.getSupplierId());
					upperLowerHistoryService.saveSelective(history);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				redisDao.deleteKey(RedisKeyConstant.TODOWN_KEY);
			}
			if(result_num>0) {
				return ResultUtil.resultMessage(result_num, "下架成功");
			}else {
				return ResultUtil.resultMessage(result_num, "下架失败");
			}
		}
    }
	
	
}
