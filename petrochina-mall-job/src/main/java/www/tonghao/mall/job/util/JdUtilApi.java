package www.tonghao.mall.job.util;

import java.util.List;

import www.tonghao.mall.api.jd.call.AreaCitiesApi;
import www.tonghao.mall.api.jd.call.AreaCountyApi;
import www.tonghao.mall.api.jd.call.AreaProvincesApi;
import www.tonghao.mall.api.jd.call.AreaTownApi;
import www.tonghao.mall.api.jd.call.GetServiceDetailInfoApi;
import www.tonghao.mall.api.jd.call.MessageApi;
import www.tonghao.mall.api.jd.call.MessageDelApi;
import www.tonghao.mall.api.jd.call.OrderSelectApi;
import www.tonghao.mall.api.jd.call.OrderSelectChildrenApi;
import www.tonghao.mall.api.jd.call.ProductDetailApi;
import www.tonghao.mall.api.jd.call.ProductImageApi;
import www.tonghao.mall.api.jd.call.ProductPoolApi;
import www.tonghao.mall.api.jd.call.ProductPricesApi;
import www.tonghao.mall.api.jd.call.ProductSkusApi;
import www.tonghao.mall.api.jd.call.ProductStateApi;
import www.tonghao.mall.api.jd.call.TrackApi;
import www.tonghao.mall.api.jd.resultwrap.CategorysRes;
import www.tonghao.mall.api.jd.resultwrap.CityRes;
import www.tonghao.mall.api.jd.resultwrap.CountyRes;
import www.tonghao.mall.api.jd.resultwrap.GetServiceDetailInfoRes;
import www.tonghao.mall.api.jd.resultwrap.MessageDelRes;
import www.tonghao.mall.api.jd.resultwrap.MessageRes;
import www.tonghao.mall.api.jd.resultwrap.OrderSelectRes;
import www.tonghao.mall.api.jd.resultwrap.ProductImageRes;
import www.tonghao.mall.api.jd.resultwrap.ProductPoolRes;
import www.tonghao.mall.api.jd.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.jd.resultwrap.ProductRes;
import www.tonghao.mall.api.jd.resultwrap.ProductSkusRes;
import www.tonghao.mall.api.jd.resultwrap.ProductStateRes;
import www.tonghao.mall.api.jd.resultwrap.ProvincesRes;
import www.tonghao.mall.api.jd.resultwrap.TownRes;
import www.tonghao.mall.api.jd.resultwrap.TrackRes;

public class JdUtilApi {

	public static ProductPoolRes ProductPoolApi(){
		ProductPoolApi api=new ProductPoolApi();
		return api.call();
	}
	
	public static ProductSkusRes ProductSkusApi(String pageNum,int pageNo ){
		ProductSkusApi api=new ProductSkusApi(pageNum, pageNo);
		return api.call();
	}
	
	public static ProductRes ProductDetailApi(String sku){
		ProductDetailApi api=new ProductDetailApi(sku);
		return api.call();
	}
	
	public static ProductImageRes ProductImageApi(String sku){
		ProductImageApi api=new ProductImageApi(sku);
		return api.call();
	}
	
	public static ProductPriceRes ProductPricesApi(String sku){
		ProductPricesApi api=new ProductPricesApi(sku);
		return api.call();
	}
	
	public static ProductStateRes ProductStateApi(String sku){
		ProductStateApi api=new ProductStateApi(sku);
		return api.call();
	}
	
	public static ProvincesRes AreaProvincesApis(){
		AreaProvincesApi api=new AreaProvincesApi();
		return api.call();
	}
	
	public static CityRes AreaCitiesApis(String provinceId){
		AreaCitiesApi api=new AreaCitiesApi(provinceId);
		return api.call();
	}
	public static CountyRes AreaCountyApis(String cityId){
		AreaCountyApi api=new AreaCountyApi(cityId);
		return api.call();
	}
	public static TownRes AreaTownApis(String countyId){
		AreaTownApi api=new AreaTownApi(countyId);
		return api.call();
	}
	public static CategorysRes CategorysApi(String pageNo,String pageSize,String parentId,String catClass){
		www.tonghao.mall.api.jd.call.CategorysApi api=new www.tonghao.mall.api.jd.call.CategorysApi(pageNo, pageSize, parentId, catClass);
		return api.call();
	}
	
	public static MessageRes MessageApis(Integer type){
		MessageApi api=new MessageApi(type);
		return api.call();
	}
	
	public static MessageDelRes MessageDelApis(String messId){
		MessageDelApi api=new MessageDelApi(messId);
		return api.call();
	}
	
	public static TrackRes selectTrack(String orderId){
		TrackApi trackApi = new TrackApi(orderId);
		return trackApi.call();
	}

	public static OrderSelectRes selectOrder(String orderId){
		OrderSelectApi orderSelectApi = new OrderSelectApi(orderId);
		return orderSelectApi.call();
	}
	
	public static OrderSelectRes selectOrderChildren(String orderId){
		OrderSelectChildrenApi orderSelectApi=new OrderSelectChildrenApi(orderId);
		return orderSelectApi.call();
	}
	
	public static GetServiceDetailInfoRes selectServiceDetailInfo(String afsServiceId, List<Integer> list){
		GetServiceDetailInfoApi GetServiceDetailInfoApi = new GetServiceDetailInfoApi(afsServiceId, list);
		return GetServiceDetailInfoApi.call();
	}
}
