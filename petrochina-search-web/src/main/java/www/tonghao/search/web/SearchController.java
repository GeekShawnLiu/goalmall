package www.tonghao.search.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.StringUtil;
import www.tonghao.common.warpper.ProductParamQuery;
import www.tonghao.service.common.entity.OrgSupplier;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.response.IProductResponse;
import www.tonghao.service.common.service.OrgSupplierService;
import www.tonghao.service.common.service.ProductSearchService;
import www.tonghao.utils.UserUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Api(value="searchController",description="商城搜索api")
@RestController
@Scope("singleton")
public class SearchController {

	@Autowired
	private ProductSearchService productSearchService;
	
	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Autowired
	private OrgSupplierService orgSupplierService;
	
	/**
	 * 商品搜索列表
	 * @param mc_id
	 * @param tds
	 * @param page
	 * @param brands
	 * @param suppliers
	 * @param priceRanges
	 * @param paramItems
	 * @param orderby
	 * @param isAsc
	 * @return
	 */
	@ApiOperation(value="商品搜索列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="keywords",value="搜索关键字",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="brands",value="品牌",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="suppliers",value="供应商",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="priceRanges",value="价格区间",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="paramItems",value="参数项",required=false,dataType="int[]",paramType="query"),
		@ApiImplicitParam(name="orderby",value="排序",required=false,dataType="string"),
		@ApiImplicitParam(name="isArc",value="是否升序",required=false,dataType="boolean"),
		@ApiImplicitParam(name="cid",value="商城一级品目id",required=false,dataType="int"),
	})
	@GetMapping("/search/product")
    public IProductResponse product(@ModelAttribute PageBean page
    		,String keywords
    		,@RequestParam(value="brands[]",required=false) Long[] brands
    		,@RequestParam(value="suppliers[]",required=false)Long[] suppliers
    		,@RequestParam(value="priceRanges[]",required=false)String[] priceRanges
    		,@RequestParam(value="paramItems[]",required=false)String[] paramItems
    		,String orderby,Boolean isAsc,Long cid, HttpServletRequest request){
		Set<Long> usableSupplierIds = null;
		if(suppliers!=null){
			usableSupplierIds = Sets.newHashSetWithExpectedSize(suppliers.length);
			for(Long sid:suppliers){
				usableSupplierIds.add(sid);
			}
		}else{
			usableSupplierIds = suppliersMapper.findUsableByProtocol();
		}

		Users user = UserUtil.getUser(request);
		//根据机构判断商品展示
		Set<Long> orgSupllierSet = new HashSet<>();
		if(user != null && user.getType() != null){
			if(user.getType() == 1){
				Example example = new Example(OrgSupplier.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("orgId", user.getDepId());
				List<OrgSupplier> selectByExample = orgSupplierService.selectByExample(example);
				if(CollectionUtils.isNotEmpty(selectByExample)){
					selectByExample.forEach(s -> {
						if(s != null){
							orgSupllierSet.add(s.getSupplierId());
						}
					});
				}
				usableSupplierIds.retainAll(orgSupllierSet);
			}else if(user.getType() == 4){
				orgSupllierSet.add(user.getTypeId());
				usableSupplierIds.retainAll(orgSupllierSet);
			}
		}
		Map<String,Object> queryfilter = Maps.newHashMap();
		
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
		
		queryfilter.put("brandIds", brands);
		queryfilter.put("orderby", orderby);
		queryfilter.put("isAsc", isAsc);
		queryfilter.put("priceRanges", priceRanges);
		queryfilter.put("cid", cid);
		return productSearchService.searcherProduct(keywords, page, usableSupplierIds, queryfilter);
	}
	
	
	/**
	 * 商品搜索列表
	 * @param mc_id
	 * @param tds
	 * @param page
	 * @param brands
	 * @param suppliers
	 * @param priceRanges
	 * @param paramItems
	 * @param orderby
	 * @param isAsc
	 * @return
	 */
	@ApiOperation(value="商品搜索列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="keywords",value="搜索关键字",required=true,dataType="string",paramType="query"),
		@ApiImplicitParam(name="brands",value="品牌",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="suppliers",value="供应商",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="priceRanges",value="价格区间",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="paramItems",value="参数项",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="orderby",value="排序",required=false,dataType="string"),
		@ApiImplicitParam(name="isArc",value="是否升序",required=false,dataType="boolean"),
	})
	@GetMapping("/search/product/wx")
    public IProductResponse product(@ModelAttribute PageBean page
    		,String keywords
    		,@RequestParam(value="brands",required=false) String brands
    		,@RequestParam(value="suppliers",required=false)String suppliers
    		,@RequestParam(value="priceRanges",required=false)String priceRanges
    		,@RequestParam(value="paramItems",required=false)String paramItems
    		,String orderby,Boolean isAsc,Long cid, HttpServletRequest request){
		Set<Long> usableSupplierIds = null;
		if(!StringUtils.isEmpty(suppliers)){
			usableSupplierIds = Sets.newHashSetWithExpectedSize(suppliers.split(",").length);
			String[] split = suppliers.split(",");
			for(String sid:split){
				usableSupplierIds.add(Long.parseLong(sid));
			}
		}else{
			usableSupplierIds = suppliersMapper.findUsableByProtocol();
		}
		Users user = UserUtil.getUser(request);
		//根据机构判断商品展示
		Set<Long> orgSupllierSet = new HashSet<>();
		if(user != null && user.getType() != null){
			if(user.getType() == 1){
				Example example = new Example(OrgSupplier.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("orgId", user.getDepId());
				List<OrgSupplier> selectByExample = orgSupplierService.selectByExample(example);
				if(CollectionUtils.isNotEmpty(selectByExample)){
					selectByExample.forEach(s -> {
						if(s != null){
							orgSupllierSet.add(s.getSupplierId());
						}
					});
				}
				usableSupplierIds.retainAll(orgSupllierSet);
			}else if(user.getType() == 4){
				orgSupllierSet.add(user.getTypeId());
				usableSupplierIds.retainAll(orgSupllierSet);
			}
		}
		Map<String,Object> queryfilter = Maps.newHashMap();
		
		if(StringUtil.strIsNotEmpty(paramItems)){
			List<ProductParamQuery> ppqList = Lists.newArrayList();
			ProductParamQuery ppq = null;
			Map<Long,ProductParamQuery> ppqMap = Maps.newHashMap();
			if(StringUtil.strIsNotEmpty(paramItems)){
				for(String str:paramItems.split(",")){
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
			}
			Set<Entry<Long, ProductParamQuery>> entrySet = ppqMap.entrySet();
			for(Entry<Long, ProductParamQuery> entry:entrySet){
				ppqList.add(entry.getValue());
			}
			queryfilter.put("productParameters", ppqList);
		}
		if(StringUtil.strIsNotEmpty(brands)) {
			Long rIds[] = (Long[])ConvertUtils.convert(brands.split(","),Long.class);
			queryfilter.put("brandIds", rIds);
		}
		queryfilter.put("orderby", orderby);
		queryfilter.put("isAsc", isAsc);
		if(StringUtil.strIsNotEmpty(priceRanges)) {
			queryfilter.put("priceRanges", priceRanges.split(","));
		}
		queryfilter.put("cid", cid);
		return productSearchService.searcherProduct(keywords, page, usableSupplierIds, queryfilter);
	}
}
