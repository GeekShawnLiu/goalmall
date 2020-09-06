package www.tonghao.service.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.InternalNested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.enums.IndexMappingType;
import www.tonghao.common.es.ElasticSearchClient;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.PriceUtil;
import www.tonghao.common.utils.ProductUtil;
import www.tonghao.common.utils.ValidatorUtil;
import www.tonghao.common.warpper.DeliveryVo;
import www.tonghao.common.warpper.PayWayVo;
import www.tonghao.common.warpper.PriceRangeModel;
import www.tonghao.common.warpper.ProductAttributeModel;
import www.tonghao.common.warpper.ProductParamQuery;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.Catalogs;
import www.tonghao.service.common.entity.DeliveryWayEntity;
import www.tonghao.service.common.entity.Parameter;
import www.tonghao.service.common.entity.ParameterItem;
import www.tonghao.service.common.entity.PayWayEntity;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductParameter;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.ActivityProductMapper;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.mapper.CatalogsMapper;
import www.tonghao.service.common.mapper.DeliveryWayMapper;
import www.tonghao.service.common.mapper.ParameterItemMapper;
import www.tonghao.service.common.mapper.ParameterMapper;
import www.tonghao.service.common.mapper.PayWayMapper;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.mapper.ProductParameterMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.response.IProductResponse;
import www.tonghao.service.common.response.ProductSearchResponse;
import www.tonghao.service.common.service.ProductSearchService;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

@Service
public class ProductSearchServiceImpl implements ProductSearchService, InitializingBean {

	private static Log logger = LogFactory.getLog(ProductSearchServiceImpl.class);
	
	@Autowired(required=false)
	private ElasticSearchClient elasticSearchClient;
	
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	
	@Autowired
	private CatalogsMapper catalogsMapper;
	
	@Autowired
	private PayWayMapper payWayMapper;
	
	@Autowired
	private DeliveryWayMapper deliveryWayMapper;
	
	@Autowired
	private ProductParameterMapper productParameterMapper;
	
	@Autowired
	private ParameterMapper parameterMapper;
	
	@Autowired
	private ParameterItemMapper parameterItemMapper;
	
	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private ActivityProductMapper activityProductMapper;
	
	@Override
	public void createIndex() {
		logger.info("createIndex  " + IndexMappingType.products.getIndexName());
        try {
            // 创建索引库
            if (isIndexExists()) {
                logger.info("Index  " + IndexMappingType.products.getIndexName() + " already exits!");
            } else {
                CreateIndexRequest cIndexRequest = new CreateIndexRequest(IndexMappingType.products.getIndexName());
                // 执行创建index请求
                CreateIndexResponse cIndexResponse = elasticSearchClient.getConnection().admin().indices().create(cIndexRequest)
                        .actionGet();
                if (cIndexResponse.isAcknowledged()) {
                    // 创建商品映射
                	createMapping();
                    logger.info("create index successfully！");
                } else {
                    logger.info("Fail to create index!");
                }

            }

        } catch (Exception e) {
            logger.info("createIndex  Exception " + e.getLocalizedMessage());
            e.printStackTrace();
        }
	}

	@Override
	public boolean isIndexExists() {
		boolean flag = false;
        try {
            IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(IndexMappingType.products.getIndexName());
            IndicesExistsResponse inExistsResponse = elasticSearchClient.getConnection().admin().indices()
                    .exists(inExistsRequest).actionGet();
            if (inExistsResponse.isExists()) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
	}

	@Override
	public void createMapping() {
		try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject(IndexMappingType.products.getMappingName())
                    .startObject("properties")
                    .startObject("id").
                            field("type", "integer").
                            endObject()
                    .startObject("createdAt").
                            field("type", "date").
                            field("format", "yyyy-MM-dd HH:mm:ss").
                            endObject()
                    .startObject("updatedAt").
                            field("type", "date").
                            field("format", "yyyy-MM-dd HH:mm:ss").
                            endObject()
                    .startObject("name").
                            field("type", "text").
                            field("store", true).
                            field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").
                            endObject()
                    .startObject("price").
                            field("type", "double").
                            endObject()
                    .startObject("marketPrice").
                            field("type", "double").
                            endObject()
                    .startObject("coverUrl").
                            field("type", "text").
                            endObject()
                    .startObject("unit").
                            field("type", "text").
                            endObject()
                    .startObject("weight").
                            field("type", "text").
                            endObject()
                    .startObject("brandId").
                            field("type", "integer").
                            endObject()
                    .startObject("brandName").
                            field("type", "text").
                            field("store", true).
                            field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").
                            endObject()
                    .startObject("model").
                            field("type", "text").
                            endObject()
                    .startObject("supplierId").
                            field("type", "integer").
                            endObject()
                    .startObject("sales").
                            field("type", "integer").
                            endObject()
                    .startObject("hits").
                            field("type", "integer").
                            endObject()
                    .startObject("isMarketable").
                            field("type", "integer").
                            endObject()
                    .startObject("monthSales").
                            field("type", "integer").
                            endObject()
                    .startObject("isDelete").
                            field("type", "integer").
                            endObject()
                    .startObject("sku").
                            field("type", "text").
                            endObject()
                    .startObject("status").
                            field("type", "integer").
                            endObject()
                    .startObject("protocolId").
                            field("type", "integer").
                            endObject()
                    .startObject("catalogId").
                            field("type", "integer").
                            endObject()
                    .startObject("catalogName").
                            field("type", "text").
                            field("store", true).
                            field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").
                            endObject()
                    .startObject("supplierName").
                            field("type", "text").
                            field("store", true).
                            field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").
                            endObject()
                    .startObject("protocolName").
                            field("type", "text").
                            field("store", true).
                            field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").
                            endObject()
                    .startObject("manufacturerIdent").
                            field("type", "text").
                            field("store", false).
                            endObject()
                    .startObject("afterSaleService").
                            field("type", "text").
                            field("store", false).
                            endObject()
                    .startObject("detail").
                            field("type", "text").
                            field("store", true).
                            field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").
                            endObject()
                    .startObject("govCatalogId").
                            field("type", "text").
                            endObject()
                    .startObject("factoryURL").
                            field("type", "text").
                            endObject()
                    .startObject("url").
                            field("type", "text").
                            endObject()
                    .startObject("parameters").
                            field("type", "nested").
                            endObject()
                    .startObject("activitys").	//活动
                            field("type", "nested").
                            endObject()
                    .startObject("catalogTypes").
                            field("type", "text").
                            endObject()
                    .startObject("financeCatalogTypes").
                            field("type", "text").
                            endObject()
                    .startObject("paywayArray").
                            field("type", "nested").
                            endObject()
                    .startObject("deliveryArray").
                            field("type", "nested").
                            endObject()
                    .startObject("firstCatalogId").
                            field("type", "integer").
                            endObject()
                    .startObject("secondCatalogId").
                            field("type", "integer").
                            endObject().
                     endObject()
                    .endObject()
                    .endObject();
            
            PutMappingRequest mapping = Requests.putMappingRequest(IndexMappingType.products.getIndexName()).type(IndexMappingType.products.getMappingName()).source(builder);
            elasticSearchClient.getConnection().admin().indices().putMapping(mapping).actionGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public String findIndexId(long id) {
		if(!isEnable()){
			return null;
		}
		// 创建查询索引
        SearchRequestBuilder srb = elasticSearchClient.getConnection().prepareSearch(IndexMappingType.products.getIndexName());
        srb.setTypes(IndexMappingType.products.getMappingName());
        GetResponse getResponse = elasticSearchClient.getConnection().prepareGet(IndexMappingType.products.getIndexName(),IndexMappingType.products.getMappingName(), id+"").setOperationThreaded(false).get();
        // 查询条件封装
        /*BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.matchQuery("id", id));
        srb.setQuery(bqb);
        // 设置是否按查询匹配度排序
        srb.setExplain(true);*/
        // 返回搜索匹配信息
        if(getResponse.getSourceAsString()!=null) {
        	return id+"";
        }
        /*SearchResponse response = srb.execute().actionGet();
        SearchHits hits = response.getHits();
        if (hits.getTotalHits() > 0) {
            logger.info("索引库中存在_id为" + hits.getAt(0).getId() + "的商品信息");
            return hits.getAt(0).getId();
        }*/
		return null;
	}

	@Override
	public boolean addData(Products product) {
		if (!isEnable()) {
			return false;
		}
		// 判断索引中是否已经存在该商品
		/*String goodsIndexId = findIndexId(product.getId());
		if (goodsIndexId != null) {
			boolean b = updateData(goodsIndexId, product);
			if (b) {
				return b;
			}
		}*/

		try {
			Map<String, Object> source = buildProductSourceData(product);
			IndexResponse response = elasticSearchClient
					.getConnection()
					.prepareIndex(IndexMappingType.products.getIndexName(),
							IndexMappingType.products.getMappingName(),
							product.getId()+"").setSource(source)
					.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL)
					.execute().actionGet();
			logger.info("新增商品ES结果=" + response.status().getStatus() + "！id=" + response.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updateData error:" + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean updateData(String indexId, Products product) {
		if(!isEnable()){
			return false;
		}
		PlatformCatalogs platformCatalogs = product.getPlatformCatalogs();
		Catalogs catalogs = null;
		Long firstCatalogsId = null;
		Long secondCatalogsId = null;
		if(platformCatalogs==null){
			platformCatalogs = platformCatalogsMapper.selectByPrimaryKey(product.getCatalogId());
			if(platformCatalogs!=null&&platformCatalogs.getCatalogId()!=null){
				catalogs = catalogsMapper.selectByPrimaryKey(platformCatalogs.getCatalogId());
			}
		}else{
			if(platformCatalogs.getCatalogId()!=null){
				catalogs = catalogsMapper.selectByPrimaryKey(platformCatalogs.getCatalogId());
			}
		}
		//一级二级品目
		if(platformCatalogs != null){
			secondCatalogsId = platformCatalogs.getMallCatalogId();
			if(secondCatalogsId != null){
				firstCatalogsId = platformCatalogsMapper.selectMallCatalogsIdByMid(secondCatalogsId);
			}
		}
		try{
			XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject();
			xContentBuilder.field("id", product.getId());
			xContentBuilder.field("createdAt", product.getCreatedAt()!=null?product.getCreatedAt():null);
			xContentBuilder.field("updatedAt", product.getUpdatedAt()!=null?product.getUpdatedAt():null);
			xContentBuilder.field("name", product.getName());
			xContentBuilder.field("price", product.getPrice()!=null?product.getPrice():"");
			xContentBuilder.field("marketPrice", product.getMarketPrice()!=null?product.getMarketPrice():"");
			xContentBuilder.field("coverUrl", product.getCoverUrl());
			xContentBuilder.field("unit", product.getUnit());
			xContentBuilder.field("weight", product.getWeight());
			xContentBuilder.field("brandId", product.getBrandId());
			xContentBuilder.field("brandName", product.getBrandName());
			xContentBuilder.field("model", product.getModel());
			xContentBuilder.field("supplierId", product.getSupplierId());
			xContentBuilder.field("sales", product.getSales()!=null?product.getSales():0);
			xContentBuilder.field("isMarketable", product.getIsMarketable()!=null?product.getIsMarketable():1);
			xContentBuilder.field("monthSales", product.getMonthSales()!=null?product.getMonthSales():0);
			xContentBuilder.field("isDelete", product.getIsDelete()!=null?product.getIsDelete():0);
			xContentBuilder.field("sku", product.getSku());
			xContentBuilder.field("status", product.getStatus());
			xContentBuilder.field("protocolId", product.getProtocolId());
			xContentBuilder.field("catalogId", product.getCatalogId());
			xContentBuilder.field("catalogName", product.getCatalogName()!=null?product.getCatalogName():"");
			xContentBuilder.field("manufacturerIdent", product.getManufacturerIdent());
			xContentBuilder.field("afterSaleService", product.getAfterSaleService());
			xContentBuilder.field("detail", product.getDetail());
			xContentBuilder.field("govCatalogId", product.getGovCatalogId());
			xContentBuilder.field("factoryURL", product.getFactoryURL());
			xContentBuilder.field("url", ProductUtil.getMallProductUrl(product.getCatalogId(), product.getId()));
			xContentBuilder.field("parameters", getParameterSourcesData(getParameters(product)));
			xContentBuilder.field("catalogTypes", platformCatalogs!=null?platformCatalogs.getTypes():null);
			xContentBuilder.field("financeCatalogTypes", catalogs!=null?catalogs.getTypes():null);
			xContentBuilder.field("paywayArray", getPayWayArraySourcesData(product.getSupplierId()));
			xContentBuilder.field("deliveryArray", getDeliveryArraySourcesData(product.getSupplierId()));
			//活动activitys
			xContentBuilder.field("activitys", getActivityIds(product.getId()));
			//一级二级品目
			xContentBuilder.field("secondCatalogId", secondCatalogsId);
			xContentBuilder.field("firstCatalogId", firstCatalogsId);
			xContentBuilder.endObject();
		
			UpdateResponse response = elasticSearchClient.getConnection().prepareUpdate(IndexMappingType.products.getIndexName(), IndexMappingType.products.getMappingName(), indexId)
		                .setDoc(xContentBuilder)
		                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE)
		                .get();
			 
			 logger.info("修改产品ES结果：" + response.getIndex() + " Types: " + response.getType() + " V_ID: " + response.getId() + " Version: " + response.getVersion());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean deleteData(String indexId) {
		elasticSearchClient.getConnection().prepareDelete(IndexMappingType.products.getIndexName(), IndexMappingType.products.getMappingName(), indexId).setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL).get();
		return true;
	}

	/**
	 * 构建商品source
	 * @param product
	 * @return
	 */
	private Map<String,Object> buildProductSourceData(Products product){
		Map<String,Object> source = new HashMap<String, Object>();
		PlatformCatalogs platformCatalogs = product.getPlatformCatalogs();
		Catalogs catalogs = null;
		if(platformCatalogs==null){
			platformCatalogs = platformCatalogsMapper.selectByPrimaryKey(product.getCatalogId());
			if(platformCatalogs!=null&&platformCatalogs.getCatalogId()!=null){
				catalogs = catalogsMapper.selectByPrimaryKey(platformCatalogs.getCatalogId());
			}
		}else{
			if(platformCatalogs.getCatalogId()!=null){
				catalogs = catalogsMapper.selectByPrimaryKey(platformCatalogs.getCatalogId());
			}
		}
		Long firstCatalogsId = null;
		Long secondCatalogsId = null;
		if(platformCatalogs != null){
			secondCatalogsId = platformCatalogs.getMallCatalogId();
			if(secondCatalogsId != null){
				firstCatalogsId = platformCatalogsMapper.selectMallCatalogsIdByMid(secondCatalogsId);
			}
		}
		source.put("id", product.getId());
		source.put("createdAt", product.getCreatedAt());
		source.put("updatedAt", product.getUpdatedAt());
		source.put("name", product.getName());
		source.put("price", product.getPrice());
		source.put("marketPrice", product.getMarketPrice());
		source.put("coverUrl", product.getCoverUrl());
		source.put("unit", product.getUnit());
		source.put("weight", product.getWeight());
		source.put("brandId", product.getBrandId());
		source.put("brandName", product.getBrandName());
		source.put("model", product.getModel());
		source.put("supplierId", product.getSupplierId());
		source.put("sales", product.getSales()!=null?product.getSales():0);
		source.put("hits", product.getHits()!=null?product.getHits():0);
		source.put("isMarketable", product.getIsMarketable()!=null?product.getIsMarketable():1);
		source.put("monthSales", product.getMonthSales()!=null?product.getMonthSales():0);
		source.put("isDelete", product.getIsDelete()!=null?product.getIsDelete():0);
		source.put("sku", product.getSku());
		source.put("status", product.getStatus());
		source.put("protocolId", product.getProtocolId());
		source.put("catalogId", product.getCatalogId());
		source.put("catalogName", product.getCatalogName());
		source.put("supplierName", product.getSupplierName());
		source.put("protocolName", product.getProtocolName());
		source.put("manufacturerIdent", product.getManufacturerIdent());
		source.put("afterSaleService", product.getAfterSaleService());
		source.put("detail", product.getDetail());
		source.put("govCatalogId", product.getGovCatalogId());
		source.put("factoryURL", product.getFactoryURL());
		source.put("url", ProductUtil.getMallProductUrl(product.getCatalogId(), product.getId()));
		source.put("parameters", getParameterSourcesData(getParameters(product)));
		source.put("catalogTypes", platformCatalogs!=null?platformCatalogs.getTypes():null);
		source.put("financeCatalogTypes", catalogs!=null?catalogs.getTypes():null);
		source.put("paywayArray", getPayWayArraySourcesData(product.getSupplierId()));
		source.put("deliveryArray", getDeliveryArraySourcesData(product.getSupplierId()));
		source.put("isCompleteMachine", product.getIsCompleteMachine());
		source.put("guaranteePeriod", product.getGuaranteePeriod());
		source.put("commoditiesId", product.getCommoditiesId());
		source.put("isBatch", product.getIsBatch());
		source.put("isEnergySaving", product.getIsEnergySaving());
		//活动activitys
		source.put("activitys", getActivityIds(product.getId()));
		//一级二级品目
		source.put("secondCatalogId", secondCatalogsId);
		source.put("firstCatalogId", firstCatalogsId);
		return source;
	}
	
	
	private List<Map<String,Object>> getParameterSourcesData(List<ProductParameter> productParameters){
		List<Map<String,Object>> source = new ArrayList<Map<String,Object>>();
		if(productParameters!=null&&productParameters.size()>0){
			productParameters.forEach(productParameter -> {
				Parameter parameter = productParameter.getParameter();
				Map<String,Object> sourceMap = new HashMap<String,Object>();
				sourceMap.put("id", productParameter.getId());
				sourceMap.put("parameterId", parameter!=null?parameter.getId():null);
				sourceMap.put("isKey", productParameter.isKey());
				sourceMap.put("name", parameter!=null?parameter.getName():null);
				sourceMap.put("standParamId", productParameter.getStandParamId());
				sourceMap.put("position", productParameter.getPosition());
				sourceMap.put("ttype", productParameter.getTtype());
				sourceMap.put("parameterItems", getParameterItemsSourcesData(productParameter.getParameterItems()));
				source.add(sourceMap);
			});
		}
		return source;
	}
	
	private List<Map<String,Object>> getParameterItemsSourcesData(List<ParameterItem> parameterItems){
		List<Map<String,Object>> source = Lists.newArrayList();
		if(parameterItems!=null&&parameterItems.size()>0){
			parameterItems.forEach(parameterItem -> {
				Map<String,Object> sourceMap = new HashMap<String,Object>();
				sourceMap.put("id", parameterItem.getId());
				sourceMap.put("name", parameterItem.getName());
				sourceMap.put("standParamId", parameterItem.getStandParamId());
				sourceMap.put("standValueId", parameterItem.getStandValueId());
				sourceMap.put("position", parameterItem.getPosition());
				sourceMap.put("parameterId", parameterItem.getParameterId());
				source.add(sourceMap);
			});
		}
		return source;
	}
	
	private List<Map<String,Object>> getPayWayArraySourcesData(Long supplierId){
		List<PayWayEntity> payWays = payWayMapper.findSupplierUsableList(supplierId);
		List<Map<String,Object>> source = Lists.newArrayList();
		payWays.forEach(payWay -> {
			Map<String,Object> sourceMap = new HashMap<String,Object>();
			sourceMap.put("id", payWay.getId());
			sourceMap.put("name", payWay.getName());
			source.add(sourceMap);
		});
		return source;
	}
	
	private List<Map<String,Object>> getDeliveryArraySourcesData(Long supplierId){
		List<DeliveryWayEntity> deliveryWays = deliveryWayMapper.findSupplierUsableList(supplierId);
		List<Map<String,Object>> source = Lists.newArrayListWithCapacity(deliveryWays.size());
		deliveryWays.forEach(deliveryWay -> {
			Map<String,Object> sourceMap = new HashMap<String,Object>();
			sourceMap.put("id", deliveryWay.getId());
			sourceMap.put("name", deliveryWay.getName());
			source.add(sourceMap);
		});
		return source;
	}
	
	private List<ProductParameter> getParameters(Products product){
		List<ProductParameter> pps = product.getProductParameters();
		if(pps==null){
			pps = productParameterMapper.getByProductId(product.getId());
		}
		if(pps!=null){
			for(ProductParameter pp:pps){
				Parameter p = null;
				if(StringUtils.isNotEmpty(pp.getStandParamId())){
					p = parameterMapper.getOneByStandParamId(pp.getStandParamId());
				}
				if(p!=null){
					pp.setParameter(p);
				}
				pp.setParameterItems(getParameterItems(pp.getStandValueId()));
			}
		}
		return pps;
	}
	
	private Parameter getParameters(String standParamId){
		if(StringUtils.isNotEmpty(standParamId)){
			Parameter parameter = parameterMapper.getOneByStandParamId(standParamId);
			return parameter;
		}
		return null;
	}
	
	private List<ParameterItem> getParameterItems(ProductParameter productParameter){
		if(StringUtils.isNotEmpty(productParameter.getStandValueId())&&StringUtils.isNotEmpty(productParameter.getParamValue())){
			String[] valueIds = productParameter.getStandValueId().split(",");
			String[] paramValues = productParameter.getParamValue().split(",");
			if(valueIds.length>0&&paramValues.length>0&&valueIds.length==paramValues.length){
				List<ParameterItem> piList = Lists.newArrayList();
				ParameterItem pItem = null;
				//Set<String> standValueSet = Sets.newHashSet();//去重
				for(int i=0;i<valueIds.length;i++){
					//if(!standValueSet.contains(valueIds[i])){
						pItem = new ParameterItem();
						pItem.setStandValueId(valueIds[i]);
						pItem.setName(paramValues[i]);
						piList.add(pItem);
					//}
				}
				return piList;
			}
		}
		return null;
	}
	
	private List<ParameterItem> getParameterItems(String standValueIds){
		if(StringUtils.isNotEmpty(standValueIds)){
			String[] valueIds = standValueIds.split(",");
			if(valueIds.length>0){
				return parameterItemMapper.getByStandValueIds(valueIds);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IProductResponse searcherProduct(String keywords, PageBean page, Set<Long> usableSupplierIds, Map<String, Object> queryfilter) {
		ProductSearchResponse searchResponse = new ProductSearchResponse();
        
		if(usableSupplierIds == null || usableSupplierIds.size() == 0){
			PageInfo<Products> pageInfo = new PageInfo<Products>(new ArrayList<>());
            pageInfo.setPageNum(page.getPage());
            pageInfo.setPageSize(page.getRows());
            pageInfo.setPages(0);
            pageInfo.setTotal(0);
            searchResponse.setPage(pageInfo);
			return searchResponse;
		}
		
        try {
            // 创建查询索引
            SearchRequestBuilder searchRequestBuilder = elasticSearchClient.getConnection().prepareSearch(IndexMappingType.products.getIndexName())
                    .setTypes(IndexMappingType.products.getMappingName())
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH); //设置查询类型：1.SearchType.DFS_QUERY_THEN_FETCH 精确查询； 2.SearchType.SCAN 扫描查询,无序
            // 设置查询条件
            QueryBuilder qb; 
            if (StringUtils.isBlank(keywords)) {
                // 匹配全部
                qb = QueryBuilders.matchAllQuery();
            } else {
            	//qb = QueryBuilders.multiMatchQuery(keywords, "name","catalogName","brandName","model","sn","sku","url").operator(Operator.AND);
            	//如果是商品地址搜索
            	if(keywords.toLowerCase().startsWith("http") && keywords.toLowerCase().contains("html/product.html?")){
            		keywords = "/html/product.html?"+StringUtils.substringAfter(keywords, "html/product.html?");
            		qb = QueryBuilders.multiMatchQuery(keywords, "name","catalogName","brandName","model","sn","sku","url").operator(Operator.AND).minimumShouldMatch("75%");
            	}else{
            		Map<String, Float> fieldsMap = new HashMap<String, Float>();
            		fieldsMap.put("catalogName", 10f);
                	fieldsMap.put("name", 9f);
                	fieldsMap.put("model", 8f);
                	fieldsMap.put("brandName", 7f);
                	fieldsMap.put("supplierName", 6f);
                	qb = QueryBuilders.multiMatchQuery(keywords).fields(fieldsMap).analyzer("ik_max_word");
            	}
               
            }
            
            BoolQueryBuilder bqb = QueryBuilders.boolQuery();
            bqb.must(qb); 
            
            bqb.must(QueryBuilders.matchQuery("status",3));
            bqb.must(QueryBuilders.matchQuery("isDelete",0));
            
            //如果是查询批量
            if(queryfilter.get("queryBatch")!=null){
            	bqb.must(QueryBuilders.matchQuery("isBatch",1));
            }
            //品目查询
            if(queryfilter.get("cid") != null){
            	bqb.must(QueryBuilders.matchQuery("firstCatalogId", queryfilter.get("cid")));
            }
            
            if(queryfilter.get("brandIds")!=null){
            	Long[] brandIds = (Long[])queryfilter.get("brandIds");
                bqb.must(QueryBuilders.termsQuery("brandId", brandIds));
            	//bqb.must(QueryBuilders.matchQuery("brandId", queryfilter.get("brandId")));
            }
            
            if(usableSupplierIds!=null){
            	//协议类供应商
                Long[] supplierIds = new Long[usableSupplierIds.size()];
                usableSupplierIds.toArray(supplierIds);
                bqb.must(QueryBuilders.termsQuery("supplierId", supplierIds));	
            }
            
            if(queryfilter.get("priceRanges")!=null){
            	String[] priceRanges = (String[])queryfilter.get("priceRanges");
    			PriceRangeModel priceRangeModel = PriceUtil.calRange(priceRanges);
    			RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("price");
   				rangeQuery.gte(priceRangeModel.getBegin());
    			if(priceRangeModel.getEnd()>0){
    				rangeQuery.lte(priceRangeModel.getEnd());
    			}
    			bqb.must(rangeQuery);
    		}
            
            if(queryfilter.get("productParameters")!=null){
            	List<ProductParamQuery> ppqList = (List<ProductParamQuery>)queryfilter.get("productParameters");
            	for(ProductParamQuery item:ppqList){
            		NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("parameters", QueryBuilders.matchQuery("parameters.id", item.getId()), ScoreMode.Total);
                	bqb.must(nestedQuery);
                	
            		Set<String> standValueIdSet = item.getStandValueIds();
            		String[] standValueIds = new String[standValueIdSet.size()];
            		standValueIdSet.toArray(standValueIds);
            		NestedQueryBuilder nestedQuery2 = QueryBuilders.nestedQuery("parameters", QueryBuilders.termsQuery("parameters.parameterItems.standValueId", standValueIds), ScoreMode.Total);
                	bqb.must(nestedQuery2);	
            	}
            }
            
            if(queryfilter.get("orderby")!=null&&queryfilter.get("isAsc")!=null){
            	String orderBy = queryfilter.get("orderby").toString();
            	boolean isAsc = (boolean)queryfilter.get("isAsc");
            	searchRequestBuilder.addSort(orderBy, isAsc?SortOrder.ASC:SortOrder.DESC);
            }
            searchRequestBuilder.setQuery(bqb);
            
            // 分页
            searchRequestBuilder.setFrom(page.getRows() * (page.getPage() - 1));
            searchRequestBuilder.setSize(page.getRows());
            
            // 设置是否按查询匹配度排序
            searchRequestBuilder.setExplain(true);
            
            //分组
            TermsAggregationBuilder brandAggregation = AggregationBuilders.terms("brand").field("brandId").size(200);
            TermsAggregationBuilder supplierAggregation = AggregationBuilders.terms("supplier").field("supplierId").size(200);
            
            TermsAggregationBuilder proParamIds = AggregationBuilders.terms("proParamIds").field("parameters.id").size(200);
            NestedAggregationBuilder productParameterAggregation = AggregationBuilders.nested("productParamters","parameters").subAggregation(proParamIds);
            //TermsAggregationBuilder mallCatalogAggregation = AggregationBuilders.terms("mallCatalog").field("catalogId").size(200);
            //添加分组信息
            searchRequestBuilder.addAggregation(brandAggregation);
            searchRequestBuilder.addAggregation(supplierAggregation);
            
            searchRequestBuilder.addAggregation(productParameterAggregation);
            searchRequestBuilder.addAggregation(AggregationBuilders.max("maxPrice").field("price"));
            //searchRequestBuilder.addAggregation(mallCatalogAggregation);
            
            // 设置高亮显示
            HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
            highlightBuilder.preTags("<em style=\"color:red\">");
            highlightBuilder.postTags("</em>");
            
//            highlightBuilder.preTags("<span style=\"color:red\">");
//            highlightBuilder.postTags("</span>");
            searchRequestBuilder.highlighter(highlightBuilder);
            
            // 返回搜索响应信息
            SearchResponse response = searchRequestBuilder.execute().actionGet();
            
            processProductSearchResponse(response, searchResponse);
            
            SearchHits hits = response.getHits();
            long totalNum = hits.getTotalHits();
            int total = (int) totalNum;
            
            List<Products> products = getSearchProducts(hits);
           
            PageInfo<Products> pageInfo = new PageInfo<Products>(products);
            int pages = total / page.getRows();
            if (total % page.getRows() != 0) {
            	pages++;
            }
            pageInfo.setPageNum(page.getPage());
            pageInfo.setPageSize(page.getRows());
            pageInfo.setPages(pages);
            pageInfo.setTotal(total);
            searchResponse.setPage(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResponse;
	}
	
	/**
	 * 处理搜索结果
	 * @param hits
	 * @return
	 */
	private void processProductSearchResponse(SearchResponse  searchResponse, ProductSearchResponse productResponse){
		//解析返回数据，获取分组名称为emall的数据
        Terms supplierTerms = searchResponse.getAggregations().get("supplier");
        List<? extends Bucket> emallbuckets = supplierTerms.getBuckets();
        List<Suppliers> supplierList = new ArrayList<Suppliers>();
        Suppliers supplier = null;
        if (!emallbuckets.isEmpty()) {
            for (Bucket bucket : emallbuckets) {
                Suppliers supplierPO = suppliersMapper.selectByPrimaryKey(bucket.getKey());
                if (supplierPO != null) {
                	supplier = new Suppliers();
                	supplier.setId(supplierPO.getId());
                	supplier.setName(supplierPO.getName());
                	supplier.setNickName(supplierPO.getNickName());
                    supplierList.add(supplier);
                }
            }
        }
        
      //解析返回数据，获取分组名称为brand的数据
      Terms brandTerms = searchResponse.getAggregations().get("brand");
      List<? extends Bucket> brandbuckets = brandTerms.getBuckets();
      List<Brand> brandList = new ArrayList<Brand>();
      Brand brand = null;
      if (!brandbuckets.isEmpty()) {
    	  for (Bucket bucket : brandbuckets) {
    		 brand = brandMapper.selectByPrimaryKey(bucket.getKey());
    		 if(brand!=null){
    			 brandList.add(brand);
    		 }
    	  }
      }
      
      List<ProductParameter> productParameterList = Lists.newArrayList();
      InternalNested productParamtersTerms = searchResponse.getAggregations().get("productParamters");
      Terms proParamTerms = productParamtersTerms.getAggregations().get("proParamIds");
      List<? extends Bucket> proParamBuckets = proParamTerms.getBuckets();
      if(!proParamBuckets.isEmpty()){
      	for (Bucket bucket : proParamBuckets) {
      		ProductParameter productParameter = productParameterMapper.selectByPrimaryKey(bucket.getKey());
      		if(productParameter!=null&&!productParameterList.equals(productParameter)){
      			Parameter parameter = getParameters(productParameter.getStandParamId());
      			List<ParameterItem> parameterItems = getParameterItems(productParameter);
      			if(parameter!=null&&parameterItems!=null){
      				productParameter.setParameter(parameter);
      				productParameter.setParameterItems(parameterItems);
      				productParameterList.add(productParameter);
      			}
      		}
      	}
      }
       
      Map<String,ProductParameter> groupKeys = new HashMap<String, ProductParameter>();
		
      productParameterList.forEach(pp -> {
			String groupKey = pp.getStandParamId();
			if(!groupKeys.containsKey(groupKey)){
				groupKeys.put(groupKey, pp);
			}else{
				ProductParameter productParameter = groupKeys.get(groupKey);
				List<ParameterItem> productParameterItems = productParameter.getParameterItems();
				List<ParameterItem> items = pp.getParameterItems();
				if(items!=null){
					if(productParameterItems==null){
						productParameterItems = Lists.newArrayList();
					}
					Set<String> standValueIdSet = new HashSet<String>(); //去重
					productParameterItems.forEach(ppi -> {
						standValueIdSet.add(ppi.getStandValueId());
					});
					for(ParameterItem item:items){
						if(!standValueIdSet.contains(item.getStandValueId())){
							standValueIdSet.add(item.getStandValueId());
							productParameterItems.add(item);
						}
					}
				}
			}
		});
		
		List<ProductParameter> productParameterResultList = Lists.newArrayList();
		groupKeys.forEach((key,value) -> {
			if(value.getParameter()!=null&&value.getParameterItems()!=null&&value.getParameterItems().size()>1){
				productParameterResultList.add(value);
			}
		});
		
      InternalMax mixPriceTerms = searchResponse.getAggregations().get("maxPrice");
      if (mixPriceTerms != null && ValidatorUtil.isMoneyValue(mixPriceTerms.getValueAsString())) {
    	  productResponse.setPriceRanges(PriceUtil.getRanges(new BigDecimal(mixPriceTerms.getValue())));
      }
      
      productResponse.setSuppliers(supplierList);
      productResponse.setBrands(brandList);
      productResponse.setProductParameters(productParameterResultList);
	}
	
	/**
	 * 得到搜索结果商品集
	 * @param hits
	 * @return
	 */
	private List<Products> getSearchProducts(SearchHits hits){
		List<Products> productList = Lists.newArrayList();
		
		for(SearchHit hit : hits){
            productList.add(getSearchProduct(hit));
		}
		return productList;
	}
	
	@SuppressWarnings("unchecked")
	private Products getSearchProduct(SearchHit hit){
		Map<String, Object> map = hit.getSourceAsMap();
		//处理高亮片段
        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        HighlightField nameField = highlightFields.get("name");
        if (nameField != null) {
            Text[] fragments = nameField.fragments();
            String nameTmp = "";
            for (Text text : fragments) {
                nameTmp += text;
            }
            //将高亮片段组装到结果中去
            map.put("name", nameTmp);
        }
        
        long id = Long.parseLong(map.get("id").toString());
        long supplierId = Long.parseLong(map.get("supplierId").toString());
        String supplierName = null;
        Suppliers s = suppliersMapper.selectByPrimaryKey(supplierId);
        if(s!=null){
    		supplierName = s.getName();
    	}
        /*if(map.get("supplierName")!=null){
        	supplierName = map.get("supplierName").toString();
        }else{
        	Suppliers s = suppliersService.selectByKey(supplierId);
        	if(s!=null){
        		supplierName = s.getName();
        	}
        }*/
        String sku = null;
        if(map.get("sku")!=null){
        	sku = map.get("sku").toString();
        }
        String name = map.get("name").toString();
        String model = null;
        if(map.get("model") != null){
        	model = map.get("model").toString();
        }
        BigDecimal price = null;
        if (StringUtils.isNotEmpty((String)map.get("price"))) {
            price = new BigDecimal((String)map.get("price"));
        }
        
        // 市场价
        BigDecimal marketPrice = null;
        if (StringUtils.isNotEmpty((String)map.get("marketPrice"))) {
            marketPrice = new BigDecimal((String)map.get("marketPrice"));
        }
        
        String coverUrl = map.get("coverUrl").toString();
        if (map.get("coverUrl")!=null) {
        	coverUrl = map.get("coverUrl").toString();
        }
        // 品牌id
        Long brandId = null;
        if (map.get("brandId")!=null) {
            brandId = Long.parseLong(map.get("brandId").toString());
        }
        
        String brandName = null;
        if (map.get("brandName")!=null) {
        	brandName = map.get("brandName").toString();
        }
        
        Long catalogId = null;
        if (map.get("catalogId")!=null) {
        	catalogId = Long.parseLong(map.get("catalogId").toString());
        }
        String catalogName = null;
        if (map.get("catalogName")!=null) {
        	catalogName = map.get("catalogName").toString();
        }
        
        String catalogTypes = null;
        if (map.get("catalogTypes")!=null) {
        	catalogTypes = map.get("catalogTypes").toString();
        }
        
        String financeCatalogTypes = null;
        if (map.get("financeCatalogTypes")!=null) {
        	financeCatalogTypes = map.get("financeCatalogTypes").toString();
        }
        
        PayWayVo[] payways = null;
        if(map.get("paywayArray")!=null){
        	List<Map<String,Object>> paywayMaps = (List<Map<String,Object>>)map.get("paywayArray");
        	payways = new PayWayVo[paywayMaps.size()];
        	
        	for (int i = 0; i < paywayMaps.size(); i++) {
        		Map<String, Object> paywayMap = paywayMaps.get(i);
        		Long paywayId = Long.parseLong(paywayMap.get("id").toString());
        	 	String paywayName = paywayMap.get("name").toString();
        	 	payways[i]=new PayWayVo(paywayId, paywayName);
			}
        }
        
        DeliveryVo[] deliverys = null;
        if(map.get("deliveryArray")!=null){
        	List<Map<String,Object>> deliveryMaps = (List<Map<String,Object>>)map.get("paywayArray");
        	deliverys = new DeliveryVo[deliveryMaps.size()];
        	
        	for (int i = 0; i < deliveryMaps.size(); i++) {
        		Map<String, Object> deliveryMap = deliveryMaps.get(i);
        		Long deliveryId = Long.parseLong(deliveryMap.get("id").toString());
        	 	String deliveryName = deliveryMap.get("name").toString();
        		deliverys[i]=new DeliveryVo(deliveryId, deliveryName);
			}
        }
        
        Products product = new Products();
        product.setId(id);
        product.setSupplierId(supplierId);
        product.setName(name);
        product.setSupplierName(supplierName);
        product.setSku(sku);
        product.setPrice(price);
        product.setMarketPrice(marketPrice);
        product.setCoverUrl(coverUrl);
        product.setCatalogId(catalogId);
        product.setCatalogName(catalogName);
        product.setBrandId(brandId);
        product.setBrandName(brandName);
        product.setModel(model);
        product.setSupplier(s);
        
        Object parametersObj = map.get("parameters");
        if(parametersObj!=null && parametersObj instanceof List){
        	List<Map<String,Object>> paramList = (List<Map<String,Object>>)parametersObj;
        	List<ProductParameter> productParameters = Lists.newArrayListWithCapacity(paramList.size());
        	ProductParameter proParam = null;
        	Parameter param = null;
        	for(Map<String,Object> parameter:paramList){
        	 proParam = new ProductParameter();
        	 param = new Parameter();
        	 Integer proParamId = (Integer)parameter.get("id");
        	 Integer paramterId = (Integer)parameter.get("parameterId");
       		 String paramName = (String)parameter.get("name");
       		 String standParamId = (String)parameter.get("standParamId");
       		 Integer ttype = (Integer)parameter.get("ttype");
       		 Float position = null;
       		 if(parameter.get("position")!=null){
       			position = Float.parseFloat(parameter.get("position").toString());
       		 }
       		 Boolean isKey = (Boolean)parameter.get("isKey");
       		 
       		 param.setId(paramterId!=null?Long.parseLong(paramterId.toString()):null);
       		 param.setName(paramName);
       		 
       		 proParam.setId(proParamId!=null?Long.parseLong(proParamId.toString()):null);
       		 proParam.setStandParamId(standParamId);
       		 proParam.setTtype(ttype);
       		 proParam.setKey(isKey);
       		 proParam.setPosition(position);
       		 List<Map<String,Object>> parameterItems =(List<Map<String,Object>>)parameter.get("parameterItems");
       		 if(parameterItems!=null){
       			 List<ParameterItem> parameterItemList = Lists.newArrayListWithCapacity(parameterItems.size());
       			 ParameterItem parameterItem = null;
       			 for(Map<String,Object> paramItem : parameterItems){
       				 parameterItem = new ParameterItem();
       				 Integer paramItemId = (Integer)paramItem.get("id");
       				 Integer paramId = (Integer)paramItem.get("parameterId");
       				 String paramItemStandParamId = (String)paramItem.get("standParamId");
       				 String paramItemStandValueId = (String)paramItem.get("standValueId");
       				 Float paramItemPosition = (Float)paramItem.get("position");
       				if(paramItem.get("position")!=null){
       					paramItemPosition = Float.parseFloat(paramItem.get("position").toString());
       	       		 }
       				 parameterItem.setId(paramItemId!=null?Long.parseLong(paramItemId.toString()):null);
       				 parameterItem.setParameterId(paramId!=null?Long.parseLong(paramId.toString()):null);
       				 parameterItem.setStandParamId(paramItemStandParamId);
       				 parameterItem.setStandValueId(paramItemStandValueId);
       				 parameterItem.setPosition(paramItemPosition);
       				 parameterItemList.add(parameterItem);
       			 }
       			proParam.setParameterItems(parameterItemList);
       		 }
       		 proParam.setParameter(param);
       		 productParameters.add(proParam);
       	 }
         product.setProductParameters(productParameters);
        }
        
        ProductAttributeModel attributeModel = new ProductAttributeModel();
        attributeModel.setProductId(id);
        attributeModel.setAggregationNum(getAggregationNum(product));
        if(catalogTypes!=null){
        	attributeModel.setCollect(ValidatorUtil.isContainStringFormArray(catalogTypes.split(","), "collection"));
        }
        if(financeCatalogTypes!=null){
        	String[] ts = financeCatalogTypes.split(",");
        	attributeModel.setEnergyConservation(ValidatorUtil.isContainStringFormArray(ts, "1"));
        	attributeModel.setEnvironmentalProtection(ValidatorUtil.isContainStringFormArray(ts, "2"));
        }
        attributeModel.setPayWayArray(payways);
        attributeModel.setDeliveryArray(deliverys);
        product.setAttributeModel(attributeModel);
        return product;
	}
	
	/**
	 * 得到聚合数量
	 * @return
	 */
	private int getAggregationNum(Products product){
		if(product.getCatalogId()==null||product.getBrandId()==null||product.getModel()==null){
			return 1;
		}
		
		try {
            // 创建查询索引
            SearchRequestBuilder searchRequestBuilder = elasticSearchClient.getConnection().prepareSearch(IndexMappingType.products.getIndexName())
                    .setTypes(IndexMappingType.products.getMappingName())
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH); //设置查询类型：1.SearchType.DFS_QUERY_THEN_FETCH 精确查询； 2.SearchType.SCAN 扫描查询,无序
            //设置查询条件
            QueryBuilder qb = QueryBuilders.matchAllQuery();
            
            BoolQueryBuilder bqb = QueryBuilders.boolQuery();
            bqb.must(qb);
            
            bqb.must(QueryBuilders.matchQuery("status",3));
            bqb.must(QueryBuilders.matchQuery("isDelete",0));
            
            bqb.must(QueryBuilders.matchQuery("catalogId",product.getCatalogId()));
            bqb.must(QueryBuilders.matchQuery("brandId",product.getBrandId()));
            bqb.must(QueryBuilders.matchQuery("model",product.getModel()));
            
            List<ProductParameter> productParameters = product.getProductParameters();
            if(productParameters!=null&&productParameters.size()>0){
            	for(ProductParameter pp:productParameters){
            		if(pp.isKey()){
            			NestedQueryBuilder nestedStartRangeQuery = QueryBuilders.nestedQuery("parameters", QueryBuilders.matchQuery("parameters.id", pp.getId()), ScoreMode.Total);
                        bqb.must(nestedStartRangeQuery);
            		}
            	}
            }
            /*
            Long[] supplierIds = new Long[usableSupplierIds.size()];
            usableSupplierIds.toArray(supplierIds);
            //TermsQueryBuilder termsSupplierId = QueryBuilders.termsQuery("supplierId", supplierIds);
            //bqb.must(QueryBuilders.termsQuery("supplierId", supplierIds));
            */
            searchRequestBuilder.setQuery(bqb);
            
            // 设置是否按查询匹配度排序
            searchRequestBuilder.setExplain(true);
            
            // 返回搜索响应信息
            SearchResponse response = searchRequestBuilder.execute().actionGet();
            
            SearchHits hits = response.getHits();
            long totalNum = hits.getTotalHits();
            int total = (int) totalNum;
            return total;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return 1;
	}

	
	@Override
	public boolean isEnable() {
		if(elasticSearchClient != null){
			return elasticSearchClient.isEnable();
		}
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(isEnable()){
			createIndex();
		}
	}

	public List<Map<String, Object>> getActivityIds(Long productId){
		List<Activity> list = activityProductMapper.selActivityIdByProduct(productId);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(CollectionUtils.isNotEmpty(list)){
			list.forEach(ac -> {
				Map<String, Object> map = new HashMap<String, Object>();
				if(ac != null){
					map.put(ac.getId() + "", ac.getName());
					result.add(map);
				}
			});
		}
		return result;
	}

	@Override
	public void addBulkDatas(List<Products> products) {
		int totalCount = 0;
        BulkRequestBuilder bulkRequest = elasticSearchClient.getConnection().prepareBulk();
        Map<String, Object> source = null;
        for (Products mp : products) {
        	
        	// 判断索引中是否已经存在该商品
    		String goodsIndexId = findIndexId(mp.getId());
    		if (goodsIndexId != null) {
    			boolean b = updateData(goodsIndexId, mp);
    			if (b) {
    				continue;
    			}
    		}
            source = buildProductSourceData(mp);
            IndexRequestBuilder lrb = elasticSearchClient.getConnection()
                    .prepareIndex(IndexMappingType.products.getIndexName(), IndexMappingType.products.getMappingName())
                    .setSource(source);
            bulkRequest.add(lrb);
            if (bulkRequest.numberOfActions() % 5000 == 0) {
                logger.info("开始执行" + bulkRequest.numberOfActions() + "条数据同步...");
                bulkRequest.execute().actionGet();
                totalCount += bulkRequest.numberOfActions();
                logger.info("执行" + bulkRequest.numberOfActions() + "条数据完毕...");
                bulkRequest.request().requests().clear();
            }
        }
        if (bulkRequest.numberOfActions() > 0) {
            bulkRequest.execute().actionGet();
            totalCount += bulkRequest.numberOfActions();
        }
        logger.info("批量构建索引成功：" + bulkRequest.toString() + "共计：" + totalCount + "条");
	}

	@Override
	public void deleteIndex() {
		try {
			if (isIndexExists()) {
				DeleteIndexResponse dResponse = elasticSearchClient
						.getConnection().admin().indices().prepareDelete(IndexMappingType.products.getIndexName())
						.execute().actionGet();
				if (dResponse.isAcknowledged()) {
					logger.info("delete index: " + IndexMappingType.products.getIndexName() + "  successfully!");
				} else {
					logger.info("Fail to delete index: " + IndexMappingType.products.getIndexName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
