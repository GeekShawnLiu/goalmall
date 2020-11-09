package www.tonghao.mall.web.controller.mall;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.Constant;
import www.tonghao.common.entity.ProductContacts;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.PriceUtil;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.warpper.PriceRangeModel;
import www.tonghao.common.warpper.PriceTrend;
import www.tonghao.common.warpper.ProductAttributeModel;
import www.tonghao.common.warpper.ProductParamQuery;
import www.tonghao.common.warpper.ProductStock;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.ProductCompareRow;
import www.tonghao.mall.service.MallCatalogsService;
import www.tonghao.mall.service.MallProductService;
import www.tonghao.mall.service.OrderItemsService;
import www.tonghao.mall.warpper.GoodsResponse;
import www.tonghao.mall.warpper.SimilarProduct;
import www.tonghao.service.common.entity.OrgSupplier;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Review;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.OrgSupplierService;
import www.tonghao.service.common.service.ProductLogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.ReviewService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
@Api(value="mallGoodsController",description="商城商品api")
@RestController(value="mallGoodsController")
public class GoodsController {
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private MallProductService mallProductService;
	
	@Autowired
	private MallCatalogsService mallCatalogsService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private OrderItemsService orderItemsService;
	
	@Autowired
	private ProductLogsService productLogsService;
	
	@Autowired
	private OrgSupplierService orgSupplierService;
	
	/**
	 * 品目商品列表
	 * @param mc_id 商品平台品目ID 
	 * @param tds 树层级标识 0:末级 否则非末级111
	 * @return
	 */
	@ApiOperation(value="品目商品列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="brands",value="品牌",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="suppliers",value="供应商",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="priceRanges",value="价格区间",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="paramItems",value="参数项",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="orderby",value="排序",required=false,dataType="string"),
		@ApiImplicitParam(name="isArc",value="是否升序",required=false,dataType="boolean"),
	})
	@GetMapping(value = "/mc_goods/{mc_id}_{tds}")
    public GoodsResponse mcGoods(@PathVariable Long mc_id,@PathVariable Long tds
    		,String from
    		,@ModelAttribute PageBean page
    		,@RequestParam(value="brands[]",required=false) Long[] brands
    		,@RequestParam(value="suppliers[]",required=false)Long[] suppliers
    		,@RequestParam(value="priceRanges[]",required=false)String[] priceRanges
    		,@RequestParam(value="paramItems[]",required=false)String[] paramItems
    		,String orderby,Boolean isAsc, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		PageHelper.startPage(page.getPage(), page.getRows());
		GoodsResponse goodsResponse = new GoodsResponse();
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("mcid", mc_id);
		if(tds>0){
			queryfilter.put("andCidLike", true);
		}else{
			queryfilter.put("andCidEq", true);
		}
		queryfilter.put("tds", tds);
		if(StringUtils.isNotEmpty(from)&&from.equals("bid")){
			queryfilter.put("fromFlag", "bid");
		}else{
			queryfilter.put("fromFlag", "mall");
		}
		//根据机构判断商品展示
		if(user != null && user.getType() != null){
			if(user.getType() == 1){
				queryfilter.put("orgId", user.getDepId());
			}else if(user.getType() == 4){
				queryfilter.put("supplierId", user.getTypeId());
			}
		}
		
		processListQueryfilter(queryfilter, brands, suppliers, priceRanges, paramItems);
		
		if(StringUtils.isNotEmpty(orderby)&&isAsc!=null){
			String orderByCondition = "tb."+orderby+" "+(isAsc?"asc":"desc");
			queryfilter.put("orderByCondition", orderByCondition);
		}else{
			//默认按照价格升序
			String orderByCondition = "tb.price asc";
			queryfilter.put("orderByCondition", orderByCondition);
		}
		entity.setQueryfilter(queryfilter);
		List<MallProducts> list = mallProductService.findListByEntity(entity);
		
		list.forEach(mp -> {
			mp.setAttributeModel(mallProductService.getProductAttributeModel(mp));
		});
		
		goodsResponse.setPage(new PageInfo<>(list));
		
		return goodsResponse;
	}
	
	/**
	 * 列表查询条件参数处理
	 * @param queryfilter
	 * @param brands
	 * @param suppliers
	 * @param priceRanges
	 * @param paramItems
	 * @return
	 */
	private Map<String,Object> processListQueryfilter(Map<String, Object> queryfilter
			,Long[] brands,Long[] suppliers
			,String[] priceRanges,String[] paramItems){
		if(brands!=null){
			queryfilter.put("inBrandIds", brands);
		}
		if(suppliers!=null){
			queryfilter.put("inSupplierIds", suppliers);
		}
		if(priceRanges!=null&&priceRanges.length>0){
			PriceRangeModel priceRangeModel = PriceUtil.calRange(priceRanges);
			if(priceRangeModel.getBegin()>0){
				queryfilter.put("priceBegin", priceRangeModel.getBegin());
			}
			if(priceRangeModel.getEnd()>0){
				queryfilter.put("priceEnd", priceRangeModel.getEnd());
			}
		}
		if(paramItems!=null){
			List<ProductParamQuery> ppqList = Lists.newArrayList();
			ProductParamQuery ppq = null;
			Map<Long,ProductParamQuery> ppqMap = Maps.newHashMap();
			for(String str:paramItems){
				long ppId = Long.parseLong(str.split("_")[0]);
				String standValueId = str.split("_")[1];
				if(!ppqMap.containsKey(ppId)){
					ppq = new ProductParamQuery();
					Set<String> standValueIds = Sets.newHashSet();
					standValueIds.add(standValueId);
					ppq.setId(ppId);
					ppq.setStandValueIds(standValueIds);
					ppqMap.put(ppId, ppq);
				}else{
					ProductParamQuery ppqItem = ppqMap.get(ppId);
					Set<String> standValueIds = ppqItem.getStandValueIds();
					if(standValueIds==null){
						standValueIds = Sets.newHashSet();
					}
					standValueIds.add(standValueId);
				}
			}
			Set<Entry<Long, ProductParamQuery>> entrySet = ppqMap.entrySet();
			for(Entry<Long, ProductParamQuery> entry:entrySet){
				ppqList.add(entry.getValue());
			}
			queryfilter.put("productParameters", ppqList);
		}
		return queryfilter;
	}
	
	@ApiOperation(value="列表页默认显示参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="brands",value="品牌",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="suppliers",value="供应商",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="priceRanges",value="价格区间",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="paramItems",value="参数项",required=false,dataType="int[]",paramType="query"),
	})
	@GetMapping(value = "/mc_goods/{mc_id}_{tds}/sparams")
    public GoodsResponse defaultShowParams(@PathVariable Long mc_id,@PathVariable Long tds
    		,String from
    		,@ModelAttribute PageBean page
    		,@RequestParam(value="brands[]",required=false) Long[] brands
    		,@RequestParam(value="suppliers[]",required=false)Long[] suppliers
    		,@RequestParam(value="priceRanges[]",required=false)String[] priceRanges
    		,@RequestParam(value="paramItems[]",required=false)String[] paramItems
    		,HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		GoodsResponse goodsResponse = new GoodsResponse();
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("mcid", mc_id);
		if(tds>0){
			queryfilter.put("andCidLike", true);
		}else{
			queryfilter.put("andCidEq", true);
		}
		queryfilter.put("tds", tds);
		if(StringUtils.isNotEmpty(from)&&from.equals("bid")){
			queryfilter.put("fromFlag", "bid");
		}else{
			queryfilter.put("fromFlag", "mall");
		}
		//根据机构判断商品展示
		if(user != null && user.getType() != null){
			if(user.getType() == 1){
				queryfilter.put("orgId", user.getDepId());
			}else if(user.getType() == 4){
				queryfilter.put("supplierId", user.getTypeId());
			}
		}
		
		processListQueryfilter(queryfilter, brands, suppliers, priceRanges, paramItems);
		
		entity.setQueryfilter(queryfilter);
		goodsResponse.setBrands(mallProductService.getQueryResultBrands(entity));
		goodsResponse.setSuppliers(mallProductService.getQueryResultSuppliers(entity));
		
		entity.setCatalogId(mc_id);
		goodsResponse.setPriceRanges(mallProductService.getPriceRanges(entity));
		//goodsResponse.setProductParameters(mallProductService.getQueryResultParameters(entity));
		return goodsResponse;
	}
	
	@ApiOperation(value="列表页默认隐藏参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="brands",value="品牌",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="suppliers",value="供应商",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="priceRanges",value="价格区间",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="paramItems",value="参数项",required=false,dataType="int[]",paramType="query"),
	})
	@GetMapping(value = "/mc_goods/{mc_id}_{tds}/hparams")
    public GoodsResponse defaultHideParams(@PathVariable Long mc_id,@PathVariable Long tds
    		,String from
    		,@ModelAttribute PageBean page
    		,@RequestParam(value="brands[]",required=false) Long[] brands
    		,@RequestParam(value="suppliers[]",required=false)Long[] suppliers
    		,@RequestParam(value="priceRanges[]",required=false)String[] priceRanges
    		,@RequestParam(value="paramItems[]",required=false)String[] paramItems
    		,HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("mcid", mc_id);
		if(tds>0){
			queryfilter.put("andCidLike", true);
		}else{
			queryfilter.put("andCidEq", true);
		}
		queryfilter.put("tds", tds);
		if(StringUtils.isNotEmpty(from)&&from.equals("bid")){
			queryfilter.put("fromFlag", "bid");
		}else{
			queryfilter.put("fromFlag", "mall");
		}
		//根据机构判断商品展示
		if(user != null && user.getType() != null){
			if(user.getType() == 1){
				queryfilter.put("orgId", user.getDepId());
			}else if(user.getType() == 4){
				queryfilter.put("supplierId", user.getTypeId());
			}
		}
		processListQueryfilter(queryfilter, brands, suppliers, priceRanges, paramItems);
		
		entity.setQueryfilter(queryfilter);
		GoodsResponse goodsResponse = new GoodsResponse();
		goodsResponse.setProductParameters(mallProductService.getQueryResultParameters(entity));
		return goodsResponse;
	}
	
	
	@ApiOperation(value="商品品目导航")
	@ApiImplicitParams({
		@ApiImplicitParam(name="cid",value="品目编号",required=true,dataType="long",paramType="query"),
	})
	@GetMapping(value = "/goods/catalog_nodelist")
	public List<TreeNode> catalogNodeList(@RequestParam(required=true)Long cid){
		return mallCatalogsService.getNodeList(cid);
	}
	
	@ApiOperation(value="推荐商品")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/goods/recommend/{mc_id}_{tds}")
    public List<MallProducts> recommend(@PathVariable Long mc_id,@PathVariable Long tds
    		,String from
    		,@ModelAttribute PageBean page, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		PageHelper.startPage(page.getPage(), page.getRows());
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("mcid", mc_id);
		queryfilter.put("andCidEq", tds);
		if(StringUtils.isNotEmpty(from)&&from.equals("bid")){
			queryfilter.put("fromFlag", "bid");
		}else{
			queryfilter.put("fromFlag", "mall");
		}
		//根据机构判断商品展示
		if(user != null && user.getType() != null){
			if(user.getType() == 1){
				queryfilter.put("orgId", user.getDepId());
			}else if(user.getType() == 4){
				queryfilter.put("supplierId", user.getTypeId());
			}
		}
		queryfilter.put("orderByCondition", "tb.created_at desc");
		entity.setQueryfilter(queryfilter);
		List<MallProducts> list = mallProductService.findListByEntity(entity);
		return list;
	}
	
	@ApiOperation(value="热销商品")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@GetMapping(value = "/goods/hotsales/{mc_id}_{tds}")
    public List<MallProducts> hotsales(@PathVariable Long mc_id,@PathVariable Long tds
    		,String from
    		,@ModelAttribute PageBean page, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		PageHelper.startPage(page.getPage(), page.getRows());
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("mcid", mc_id);
		queryfilter.put("andCidEq", tds);
		if(StringUtils.isNotEmpty(from)&&from.equals("bid")){
			queryfilter.put("fromFlag", "bid");
		}else{
			queryfilter.put("fromFlag", "mall");
		}
		//根据机构判断商品展示
		if(user != null && user.getType() != null){
			if(user.getType() == 1){
				queryfilter.put("orgId", user.getDepId());
			}else if(user.getType() == 4){
				queryfilter.put("supplierId", user.getTypeId());
			}
		}
		queryfilter.put("orderByCondition", "tb.sales desc");
		entity.setQueryfilter(queryfilter);
		List<MallProducts> list = mallProductService.findListByEntity(entity);
		return list;
	}
	
	/**
	 * 批量统计聚合数量
	 * @param pids
	 * @return
	 */
	@GetMapping(value="/mc_goods/batch_attr_load")
	public List<ProductAttributeModel> batchAttr(@RequestParam(value="pids[]",required=true)Long[] pids){
		List<ProductAttributeModel> list = Lists.newArrayListWithExpectedSize(pids.length);
		for(Long pid:pids){
			list.add(countAggregation(pid));
		}
		return list;
	}
	
	/**
	 * 统计聚合数量
	 * @param pid
	 * @return
	 */
	@GetMapping(value="/mc_goods/load_attr")
	public ProductAttributeModel attr(@RequestParam(value="pid",required=true)Long pid){
		return countAggregation(pid);
	}
	
	private ProductAttributeModel countAggregation(Long pid){
		MallProducts mallProduct = mallProductService.findById(pid);
		ProductAttributeModel mode = new ProductAttributeModel();
		if(mallProduct!=null){
			if (mallProduct.getCatalogId() == null || mallProduct.getBrandId() == null || mallProduct.getModel() == null) {
				mode.setProductId(pid);
				mode.setAggregationNum(1);
			}else{
				mode.setProductId(pid);
				int count = mallProductService.getAggregationNum(mallProduct);
				mode.setAggregationNum(count);
			}
		}
		return mode;
	}
	
	@ApiOperation(value="商品明细")
	@GetMapping(value = "/goods/{pid}")
    public MallProducts detail(@PathVariable Long pid){
		return mallProductService.findById(pid);
	}
	
	@ApiOperation(value="商品聚合列表")
	@GetMapping(value = "/goods/{pid}/aggregation")
	public List<MallProducts> aggregationList(@PathVariable Long pid,String from){
		MallProducts product = mallProductService.findById(pid);
		List<MallProducts> list = new ArrayList<MallProducts>(); 
		if(product!=null){
			if (product.getCatalogId() == null || product.getBrandId() == null || product.getModel() == null) {
				list.add(product);
			}else{
				Map<String,Object> queryfilter = Maps.newHashMap();
				queryfilter.put("mcid", product.getCatalogId());
				queryfilter.put("andCidEq", 1);
				queryfilter.put("inParameterItems", mallProductService.getKeyProductParameterItemIds(product.getProductParameters()));
				product.setQueryfilter(queryfilter);
				list = mallProductService.findSupplierProtocolAggregationListByEntity(product);
			}
		}
		return list;
	}
	
	@ApiOperation(value="商品刷新点击量")
	@PostMapping(value = "/goods/{pid}/refreshHits")
	public Map<String, Object> refreshHits(@PathVariable Long pid){
		Products mallProduct = productsService.selectByKey(pid);
		if(mallProduct!=null){
			Products pro = new Products();
			int hits = mallProduct.getHits()!=null?mallProduct.getHits()+1:1;
			pro.setHits(hits);
			pro.setId(mallProduct.getId());
			productsService.updateHits(pro);
			return ResultUtil.success("");
		}
		return ResultUtil.error("");
	}
	
	@ApiOperation(value="商品评价",notes="商品评价api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="pid",value="商品Id",required=false,dataType="long",paramType="query"),
	})
	@RequestMapping(value="/goods/reviews",method=RequestMethod.GET)
	public PageInfo<Review> getReviewPage(@ModelAttribute PageBean page,Long pid){
		PageHelper.startPage(page.getPage(), page.getRows());
		Review entity = new Review();
		entity.setProductId(pid);
		List<Review> list = reviewService.findListByEntity(entity);
		return new PageInfo<Review>(list);
	}
	
	@ApiOperation(value="交易记录",notes="交易记录api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="pid",value="商品Id",required=false,dataType="long",paramType="query"),
	})
	@RequestMapping(value="/goods/trade",method=RequestMethod.GET)
	public PageInfo<OrderItems> trade(@ModelAttribute PageBean page,Long pid){
		PageHelper.startPage(page.getPage(), page.getRows());
		List<OrderItems> list = orderItemsService.findProductOrderItems(pid);
		return new PageInfo<OrderItems>(list);
	}
	
	@ApiOperation(value="商品对比")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pids",value="商品ids",required=false,dataType="int[]",paramType="query"),
	})
	@GetMapping(value = "/goods/compare")
    public List<ProductCompareRow> compare(@RequestParam(value="pids[]",required=false)Long[] pids){
		if(pids!=null){
			return mallProductService.getProductCompareRows(pids);
		}
		return null;
	}
	
	@ApiOperation(value="价格趋势")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pids",value="商品ids",required=false,dataType="int[]",paramType="query"),
	})
	@GetMapping(value="/goods/price_trend")
	public List<PriceTrend> priceTrend(@RequestParam(value="pid",required=true)Long pid){
		return productLogsService.getPriceTrend(pid);
	}
	@ApiOperation(value="联系人列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="cid",value="品目ID",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="sid",value="供应商ID",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="pid",value="协议ID",required=false,dataType="int",paramType="query")
	})
	@GetMapping(value = "/goods/contacts")
	public List<ProductContacts> contacts(Long cid,Long sid,Long pid){
		return mallProductService.getContacts(cid, sid, pid);
	}
	
	/**
	 * 商品检查
	 * @param pid
	 * @param areaId
	 * @param num
	 * @return 
	 */
	@ApiOperation(value="商品检查")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pid",value="商品ID",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="areaId",value="地区ID",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="num",value="占用库存数量",required=false,dataType="int",paramType="query")
	})
	@GetMapping(value = "/goods/check")
	public Map<String,Object> check(long pid,Long areaId,Integer num, HttpServletRequest request){
		MallProducts product = mallProductService.findById(pid);
		Map<String,Object> result = Maps.newHashMap();
		result.put("status", "error");
		result.put("message", "");
		result.put("stockState", 0);
		result.put("stockMsg", "");
		result.put("isPriceChange", false);
		result.put("emallUrl", "");
		if(product==null || 1 == product.getIsDelete()){
			result.put("message", "商品已失效");
			return result;
		}
		//根据机构判断商品展示
		Users user = UserUtil.getUser(request);
		if(user != null && user.getType() != null && product.getSupplierId() != null){
			if(user.getType() == 1 && user.getDepId() != null){
				Example example = new Example(OrgSupplier.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("orgId", user.getDepId());
				createCriteria.andEqualTo("supplierId", product.getSupplierId());
				List<OrgSupplier> selectByExample = orgSupplierService.selectByExample(example);
				if(CollectionUtils.isEmpty(selectByExample)){
					result.put("message", "无购买权限");
					return result;
				}
			}else if(user.getType() == 4){
				if(!product.getSupplierId().equals(user.getTypeId())){
					result.put("message", "无购买权限");
					return result;
				}
			}
		}
		
		BigDecimal poPrice = product.getPrice();
		result.put("price", poPrice);
		result.put("marketPrice", product.getMarketPrice());
		result.put("reduceAmount", product.getReduceAmount());
		if(product.getStatus()==4){
			result.put("message", "商品已下架");
			return result;
		}

		//2020-11-09 去掉电商一系列校验
		//电商上下架状态判断
//		mallProductService.vaProductPutawayStatus(product);
//		if(product.getStatus()==4){
//			result.put("message", "商品已下架");
//			return result;
//		}
		//电商价格校验接口
//		mallProductService.fillEmallUrlAndPriceAndRefreshState(product);
//		if(product.getStatus()==4){
//			result.put("message", "商品已下架");
//			return result;
//		}
		result.put("emallUrl", product.getEmallUrl());
		result.put("isPriceChange", poPrice!=null&&poPrice.compareTo(product.getPrice())==0?true:false);
		result.put("price", product.getPrice());
		result.put("marketPrice", product.getMarketPrice());
		result.put("reduceAmount", product.getReduceAmount());
		//电商库存校验接口
//		ProductStock stock = mallProductService.getProductStock(product,areaId,num);
//		if(stock.getState()==0){
//			result.put("message", "暂时无货");
//			return result;
//		}
		//查询商品区域购买限制
//		Map<String, Object> checkAreaLimit = mallProductService.checkAreaLimit(product, areaId);
//		if(!ResultUtil.isSuccess(checkAreaLimit)){
//			return checkAreaLimit;
//		}
		result.put("status", "success");
//		result.put("stockState", stock.getState());
//		result.put("stockMsg", stock.getMsg());
		return result;
	}
	
	@ApiOperation(value="相似商品列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pid",value="商品ID",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="count",value="查询数量",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="activityId",value="活动id",required=false,dataType="int",paramType="query")
	})
	@GetMapping(value = "/goods/{pid}/similar")
	public SimilarProduct similarProducts(@PathVariable long pid,Integer count, Long activityId, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		SimilarProduct res = new SimilarProduct();
		MallProducts product = mallProductService.findById(pid);
		if(product!=null){
			count = count==null?5:count;
			Set<Long> getKeyProductParameterIds = mallProductService.getKeyProductParameterItemIds(product.getProductParameters());
			Map<String, Object> similarConfigureMap = new HashMap<>();
			similarConfigureMap.put("currPid", pid);
			similarConfigureMap.put("catalogId", product.getCatalogId());
			similarConfigureMap.put("getKeyProductParameterIds", getKeyProductParameterIds);
			similarConfigureMap.put("count", count);
			similarConfigureMap.put("activityId", activityId);
			//根据机构判断商品展示
			if(user != null && user.getType() != null){
				if(user.getType() == 1){
					similarConfigureMap.put("orgId", user.getDepId());
				}else if(user.getType() == 4){
					similarConfigureMap.put("supplierId", user.getTypeId());
				}
			}
			List<MallProducts> similarConfigureProducts = mallProductService.findCurrConfigureList(similarConfigureMap);
			res.setSimilarConfigureProducts(similarConfigureProducts);
			if(product.getPrice()!=null){
				BigDecimal priceRegion = product.getPrice().multiply(Constant.SIMILAR_PRICE_RATE).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal beginPrice = product.getPrice().subtract(priceRegion);
				BigDecimal endPrice = product.getPrice().add(priceRegion);
				Map<String, Object> similarPriceMap = new HashMap<>();
	    		similarPriceMap.put("currPid", pid);
				similarPriceMap.put("catalogId", product.getCatalogId());
				similarPriceMap.put("beginPrice", beginPrice);
				similarPriceMap.put("endPrice", endPrice);
				similarPriceMap.put("count", count);
				similarPriceMap.put("activityId", activityId);
				//根据机构判断商品展示
				if(user != null && user.getType() != null){
					if(user.getType() == 1){
						similarConfigureMap.put("orgId", user.getDepId());
					}else if(user.getType() == 4){
						similarConfigureMap.put("supplierId", user.getTypeId());
					}
				}
				res.setSimilarPriceProducts(mallProductService.findCurrPriceList(similarPriceMap));
			}
		}
		return res;
	}
}
