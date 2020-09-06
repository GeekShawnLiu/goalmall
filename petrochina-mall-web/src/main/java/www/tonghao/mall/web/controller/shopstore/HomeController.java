package www.tonghao.mall.web.controller.shopstore;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.PriceUtil;
import www.tonghao.common.warpper.PriceRangeModel;
import www.tonghao.common.warpper.ProductParamQuery;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.mall.service.MallCatalogsService;
import www.tonghao.mall.service.MallProductService;
import www.tonghao.mall.warpper.GoodsResponse;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.service.SuppliersService;

import com.beust.jcommander.internal.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 商城-店铺首页
 * @author lxh
 *
 */
@Api(value="storeHomeController",description="店铺首页api")
@RestController
@RequestMapping(value="/shopstore")
public class HomeController {
    

    @Autowired
    private MallCatalogsService mallCatalogsService;
    
	@Autowired
	private MallProductService mallProductService;
	
	@Autowired
	private SuppliersService suppliersService;
	
    /**
     * 分页查询商品列表
     */
	@ApiOperation(value="品目商品列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="cid",value="商品品目",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="brands",value="品牌",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="priceRanges",value="价格区间",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="paramItems",value="参数项",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="orderby",value="排序",required=false,dataType="string"),
		@ApiImplicitParam(name="isArc",value="是否升序",required=false,dataType="boolean"),
	})
    @RequestMapping(value="/{sid}/goods",method=RequestMethod.GET)
	public GoodsResponse goods(@PathVariable Long sid
    		,@ModelAttribute PageBean page
    		,@RequestParam(value="cid",required=false) Long cid
    		,@RequestParam(value="brands[]",required=false) Long[] brands
    		,@RequestParam(value="priceRanges[]",required=false)String[] priceRanges
    		,@RequestParam(value="paramItems[]",required=false)String[] paramItems
    		,String orderby,Boolean isAsc){
		PageHelper.startPage(page.getPage(), page.getRows());
		GoodsResponse goodsResponse = new GoodsResponse();
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("mcid", cid);
		queryfilter.put("andCidEq", true);
		Long[] suppliers = new Long[]{sid};
		processListQueryfilter(queryfilter, brands, suppliers, priceRanges, paramItems);
		
		if(StringUtils.isNotEmpty(orderby)&&isAsc!=null){
			String orderByCondition = "tb."+orderby+" "+(isAsc?"asc":"desc");
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
	
	@ApiOperation(value="列表页默认显示参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="brands",value="品牌",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="suppliers",value="供应商",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="priceRanges",value="价格区间",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="paramItems",value="参数项",required=false,dataType="int[]",paramType="query"),
	})
	@GetMapping(value = "/{sid}/goods/sparams")
    public GoodsResponse defaultShowParams(@PathVariable Long sid
    		,@RequestParam(value="cid",required=false)Long cid
    		,@RequestParam(value="brands[]",required=false) Long[] brands
    		,@RequestParam(value="priceRanges[]",required=false)String[] priceRanges
    		,@RequestParam(value="paramItems[]",required=false)String[] paramItems){
		GoodsResponse goodsResponse = new GoodsResponse();
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("mcid", cid);
		queryfilter.put("andCidEq", true);
		
		Long[] suppliers = new Long[]{sid};
		processListQueryfilter(queryfilter, brands, suppliers, priceRanges, paramItems);
		
		entity.setQueryfilter(queryfilter);
		goodsResponse.setBrands(mallProductService.getQueryResultBrands(entity));
		goodsResponse.setSuppliers(mallProductService.getQueryResultSuppliers(entity));
		
		entity.setCatalogId(cid);
		goodsResponse.setPriceRanges(mallProductService.getPriceRanges(entity));
		goodsResponse.setProductParameters(mallProductService.getQueryResultParameters(entity));
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
	@GetMapping(value = "/{sid}/goods/hparams")
    public GoodsResponse defaultHideParams(@PathVariable Long sid
    		,@RequestParam(value="cid",required=false)Long cid
    		,@RequestParam(value="brands[]",required=false) Long[] brands
    		,@RequestParam(value="priceRanges[]",required=false)String[] priceRanges
    		,@RequestParam(value="paramItems[]",required=false)String[] paramItems){
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("mcid", cid);
		queryfilter.put("andCidEq", true);
		
		Long[] suppliers = new Long[]{sid};
		processListQueryfilter(queryfilter, brands, suppliers, priceRanges, paramItems);
		
		entity.setQueryfilter(queryfilter);
		GoodsResponse goodsResponse = new GoodsResponse();
		goodsResponse.setProductParameters(mallProductService.getQueryResultParameters(entity));
		return goodsResponse;
	}
	
	/**
	 * 供应商明细
	 * @param sid
	 * @return
	 */
	@RequestMapping(value="/{sid}/detail",method=RequestMethod.GET)
	public Suppliers detail(@PathVariable Long sid){
		return suppliersService.selectByKey(sid);
    }
    
    
    /**
     * 获取平台品目树
     * @param sid supplierId
     * @return
     */
    @ApiOperation(value="获取平台品目树",notes="获取平台品目树api")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="sid",value="供应商id",required=true,dataType="int",paramType="query")
    })
    @GetMapping(value="/getCatalogTree")
    public List<TreeNode> getCatalogList(Long sid) {
    	return mallCatalogsService.getSupplierTreeList(sid);
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
    
}
