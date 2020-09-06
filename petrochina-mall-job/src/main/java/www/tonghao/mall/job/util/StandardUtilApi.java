package www.tonghao.mall.job.util;

import www.tonghao.mall.api.standard.call.AreaCitiesApi;
import www.tonghao.mall.api.standard.call.AreaCountyApi;
import www.tonghao.mall.api.standard.call.AreaProvincesApi;
import www.tonghao.mall.api.standard.call.CatalogPoolApi;
import www.tonghao.mall.api.standard.call.MessageApi;
import www.tonghao.mall.api.standard.call.MessageDelApi;
import www.tonghao.mall.api.standard.call.ProductDetailApi;
import www.tonghao.mall.api.standard.call.ProductImageApi;
import www.tonghao.mall.api.standard.call.ProductPricesApi;
import www.tonghao.mall.api.standard.call.ProductSkuApi;
import www.tonghao.mall.api.standard.call.ProductStateApi;
import www.tonghao.mall.api.standard.resultwrap.CatalogPoolRes;
import www.tonghao.mall.api.standard.resultwrap.CityRes;
import www.tonghao.mall.api.standard.resultwrap.CountyRes;
import www.tonghao.mall.api.standard.resultwrap.MessageDelRes;
import www.tonghao.mall.api.standard.resultwrap.MessageRes;
import www.tonghao.mall.api.standard.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.standard.resultwrap.ProductImageRes;
import www.tonghao.mall.api.standard.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.standard.resultwrap.ProductSkusRes;
import www.tonghao.mall.api.standard.resultwrap.ProductStateRes;
import www.tonghao.mall.api.standard.resultwrap.ProvincesRes;

public class StandardUtilApi {

	
	public static CatalogPoolRes catalogPoolApi(String mallcode){
		CatalogPoolApi api=new CatalogPoolApi(mallcode);
		return api.call();
	}
	
	public static ProductSkusRes productSkuApi(String mallcode,String poolId){
		ProductSkuApi api=new ProductSkuApi(mallcode, poolId);
		return api.call();
	}
	
	public static ProductDetailRes productDetailApi(String mallcode,String sku){
		ProductDetailApi api=new ProductDetailApi(mallcode, sku);
		return api.call();
	}
	
	public static  ProductPriceRes productPricesApi(String mallcode,String sku){
		ProductPricesApi api=new ProductPricesApi(mallcode, sku);
		return api.call();
	}
	
	public static ProductStateRes productStateApi(String mallcode,String sku){
		ProductStateApi api=new ProductStateApi(mallcode, sku);
		return api.call();
	}
	
	public static ProductImageRes productImageApi(String mallcode,String sku){
		ProductImageApi api=new ProductImageApi(mallcode, sku);
		return api.call();
	}
	
	
	public static ProvincesRes areaProvincesApi(String mallcode){
		AreaProvincesApi api=new AreaProvincesApi(mallcode);
		return api.call();
	}
	public static  CityRes areaCitiesApi(String mallcode,String provinceId){
		AreaCitiesApi api=new AreaCitiesApi(mallcode, provinceId);
		return api.call();
	}
	public static CountyRes areaCountyApi(String mallcode,String cityId){
		AreaCountyApi api=new AreaCountyApi(mallcode, cityId);
		return api.call();
	}
	
	public static MessageRes getMessage(String mallcode,Integer type){
		MessageApi api=new MessageApi(mallcode, type);
		return api.call();
	}
	
	public static MessageDelRes messageDel(String mallcode,String messageId){
		MessageDelApi api=new MessageDelApi(messageId, mallcode);
		return api.call();
	}
	
}
