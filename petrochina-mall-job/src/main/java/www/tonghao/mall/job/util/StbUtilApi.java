package www.tonghao.mall.job.util;

import www.tonghao.mall.api.stb.call.GetMessageApi;
import www.tonghao.mall.api.stb.call.MessageDelApi;
import www.tonghao.mall.api.stb.call.OrderTrackApi;
import www.tonghao.mall.api.stb.call.ProductDetailApi;
import www.tonghao.mall.api.stb.call.ProductImageApi;
import www.tonghao.mall.api.stb.call.ProductPoolApi;
import www.tonghao.mall.api.stb.call.ProductPriceApi;
import www.tonghao.mall.api.stb.call.ProductSkusApi;
import www.tonghao.mall.api.stb.call.ProductStateApi;
import www.tonghao.mall.api.stb.call.SelectOrderApi;
import www.tonghao.mall.api.stb.resultwrap.MessageDelRes;
import www.tonghao.mall.api.stb.resultwrap.MessageRes;
import www.tonghao.mall.api.stb.resultwrap.OrderTrackRes;
import www.tonghao.mall.api.stb.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.stb.resultwrap.ProductImageRes;
import www.tonghao.mall.api.stb.resultwrap.ProductPoolRes;
import www.tonghao.mall.api.stb.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.stb.resultwrap.ProductSkusRes;
import www.tonghao.mall.api.stb.resultwrap.ProductStateRes;
import www.tonghao.mall.api.stb.resultwrap.SelectOrderRes;

public class StbUtilApi {

	public static ProductPoolRes ProductPoolApi() {
		ProductPoolApi api=new ProductPoolApi();
		return api.call();
	}
	
	public static ProductSkusRes productSku(String poolId) {
		ProductSkusApi api=new ProductSkusApi(poolId);
		return api.call();
	}
	
	public static ProductDetailRes productDetail(String skuId) {
		ProductDetailApi api=new ProductDetailApi(skuId);
		return api.call();
	}
	
	public static ProductStateRes ProductStateApi(String skuId) {
		ProductStateApi api=new ProductStateApi(skuId);
		return api.call();
	}
	
	public static ProductPriceRes ProductPriceApi(String skuId) {
		ProductPriceApi api=new ProductPriceApi(skuId);
		return api.call();
	}
	
	public static ProductImageRes ProductImageApi(String skuId) {
		ProductImageApi api=new ProductImageApi(skuId);
		return api.call();
	}
	
	public static MessageRes getMessage(int type) {
		GetMessageApi api=new GetMessageApi(type);
		return api.call();
		
	}
	
	public static MessageDelRes delMessage(String mssId) {
		MessageDelApi api=new MessageDelApi(mssId);
		return api.call();
	}
	
	public static SelectOrderRes selectOrder(String supplierOrderId,int isParent) {
		SelectOrderApi api=new SelectOrderApi(supplierOrderId, isParent);
		return api.call();
	}
	
	public static  OrderTrackRes selectTrack(String supplierOrderId) {
		OrderTrackApi api=new OrderTrackApi(supplierOrderId);
		return api.call();
	}
	
}
