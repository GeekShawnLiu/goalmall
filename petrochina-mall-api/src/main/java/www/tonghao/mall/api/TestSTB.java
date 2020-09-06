package www.tonghao.mall.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import www.tonghao.mall.api.stb.attwrap.CreateOrderAttr;
import www.tonghao.mall.api.stb.attwrap.Image;
import www.tonghao.mall.api.stb.attwrap.Message;
import www.tonghao.mall.api.stb.attwrap.MessageAttr;
import www.tonghao.mall.api.stb.attwrap.MessageOrder;
import www.tonghao.mall.api.stb.attwrap.MessageSku;
import www.tonghao.mall.api.stb.attwrap.OrderSkus;
import www.tonghao.mall.api.stb.attwrap.ProductDetailAttr;
import www.tonghao.mall.api.stb.attwrap.ProductImageAttr;
import www.tonghao.mall.api.stb.attwrap.ProductPoolAttr;
import www.tonghao.mall.api.stb.attwrap.ProductPriceAttr;
import www.tonghao.mall.api.stb.attwrap.ProductStateAttr;
import www.tonghao.mall.api.stb.attwrap.ProductStocksAttr;
import www.tonghao.mall.api.stb.attwrap.SelectOrderAttr;
import www.tonghao.mall.api.stb.call.AccessTokenApi;
import www.tonghao.mall.api.stb.call.ConfirmOrderApi;
import www.tonghao.mall.api.stb.call.GetMessageApi;
import www.tonghao.mall.api.stb.call.MessageDelApi;
import www.tonghao.mall.api.stb.call.OrderCancelApi;
import www.tonghao.mall.api.stb.call.OrderCreateApi;
import www.tonghao.mall.api.stb.call.OrderTrackApi;
import www.tonghao.mall.api.stb.call.ProductDetailApi;
import www.tonghao.mall.api.stb.call.ProductImageApi;
import www.tonghao.mall.api.stb.call.ProductPoolApi;
import www.tonghao.mall.api.stb.call.ProductPriceApi;
import www.tonghao.mall.api.stb.call.ProductSkusApi;
import www.tonghao.mall.api.stb.call.ProductStateApi;
import www.tonghao.mall.api.stb.call.ProductStocksApi;
import www.tonghao.mall.api.stb.call.SelectOrderApi;
import www.tonghao.mall.api.stb.entity.Order;
import www.tonghao.mall.api.stb.resultwrap.ConfirmOrderRes;
import www.tonghao.mall.api.stb.resultwrap.MessageRes;
import www.tonghao.mall.api.stb.resultwrap.OrderCancelRes;
import www.tonghao.mall.api.stb.resultwrap.OrderCreateRes;
import www.tonghao.mall.api.stb.resultwrap.OrderTrackRes;
import www.tonghao.mall.api.stb.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.stb.resultwrap.ProductImageRes;
import www.tonghao.mall.api.stb.resultwrap.ProductPoolRes;
import www.tonghao.mall.api.stb.resultwrap.ProductPriceRes;
import www.tonghao.mall.api.stb.resultwrap.ProductSkusRes;
import www.tonghao.mall.api.stb.resultwrap.ProductStateRes;
import www.tonghao.mall.api.stb.resultwrap.ProductStocksRes;
import www.tonghao.mall.api.stb.resultwrap.SelectOrderRes;

public class TestSTB {
	
	
	
	
  public static void main(String[] args) {
	  /*AccessTokenApi accessTokenApi=new AccessTokenApi();
	  String cacheToken = AccessTokenApi.getCacheToken();
	  System.out.println(cacheToken);*/
	  /*ProductPoolApi api=new ProductPoolApi();
	  ProductPoolRes call = api.call();
	  List<ProductPoolAttr> productPoolAttrs = call.getProductPoolAttrs();
	  for (ProductPoolAttr productPoolAttr : productPoolAttrs) {
		  System.out.println(productPoolAttr.getId()+"-=-="+productPoolAttr.getName());
	  }*/
	  
	  
	  /*List<String> list=new ArrayList<>();
	ProductPoolApi api=new ProductPoolApi();
	  ProductPoolRes call = api.call();
	  List<ProductPoolAttr> productPoolAttrs = call.getProductPoolAttrs();
	  for (ProductPoolAttr productPoolAttr : productPoolAttrs) {
		  ProductSkusApi api_sku=new ProductSkusApi(productPoolAttr.getId());  
		  ProductSkusRes call_sku = api_sku.call();
		  if(call_sku.isSuccess()) {
			  List<String> skus = call_sku.getSkus();
			  if(skus!=null&&!skus.toString().equals("[]")) {
				  String string = skus.get(0);
				  System.out.println(string);
				  list.addAll(skus);
			  }
			  
		  }
	}
System.out.println(list.size());

*/



	  /*3000026748EA
	  3000013865EA
	  3000013866EA
	  3000017962EA
	  ProductSkusApi api=new ProductSkusApi("1");  
	  ProductSkusRes call = api.call();
	  List<String> skus = call.getSkus();
	  for (String string : skus) {
		System.out.println(string);
	}*/
	  /*ProductDetailApi api=new ProductDetailApi("8800015794EA");
	  ProductDetailRes call = api.call();
	  if(call.isSuccess()) {
		  ProductDetailAttr attr = call.getAttr();
		  System.out.println(attr.getName());
	  }else {
		  System.out.println(call.isSuccess());
	  }
	  */
	  
	/* ProductStateApi api=new ProductStateApi("8800003187EA");
	  ProductStateRes call = api.call();
	  List<ProductStateAttr> list1 = call.getList();
	  for (ProductStateAttr productStateAttr : list1) {
		System.out.println(productStateAttr.getSku()+"-=-="+productStateAttr.getState());
	}*/
	  
	/*ProductImageApi api=new ProductImageApi("8800002461EA");
	  ProductImageRes call = api.call();
	  List<ProductImageAttr> imagesList = call.getImagesList();
	  for (ProductImageAttr productImageAttr : imagesList) {
		  System.out.println(productImageAttr.getSku());
		  List<Image> images = productImageAttr.getImages();
		  for (Image image : images) {
			System.out.println(image.getPath());
			System.out.println(image.getIsPrimary());
		}
	}*/
	  
	  /*ProductPriceApi api=new ProductPriceApi("8800026268EA");
	  ProductPriceRes call = api.call();
	  List<ProductPriceAttr> attrs = call.getAttrs();
	  ProductPriceAttr productPriceAttr2 = attrs.get(0);
	  System.out.println(productPriceAttr2.getMall_price());
	  for (ProductPriceAttr productPriceAttr : attrs) {
		System.out.println(productPriceAttr.getSku()+"-=-="+productPriceAttr.getPrice()+"-=-="+productPriceAttr.getMall_price());
	}*/
	  
	 /* List<Map<String, String>> list=new ArrayList<>();
	  Map<String, String> area1=new HashMap<String, String>();
	  area1.put("sku", "8800028657EA");
	  area1.put("num", "1");
	  list.add(area1);
	  ProductStocksApi api=new ProductStocksApi("1_2801_54775", list);
	  ProductStocksRes call = api.call();
	  List<ProductStocksAttr> productStocksAttrs = call.getProductStocksAttrs();
	  for (ProductStocksAttr productStocksAttr : productStocksAttrs) {
		System.out.println(productStocksAttr.getArea()+"-=-="+productStocksAttr.getDesc()+"-="+productStocksAttr.getNum()+"-=-="+productStocksAttr.getSku());
	}*/
	  
	  
	  //4-48206-58506
	  /*Map<String, String> map1=new HashMap<>();
	  map1.put("skuId", "1100217603EA");
	  map1.put("num", "2");
	  
	  Map<String, String> map2=new HashMap<>();
	  map2.put("skuId", "3000017962EA");
	  map2.put("num", "3");
	  
	  Map<String, String> map3=new HashMap<>();
	  map3.put("skuId", "1100200796EA");
	  map3.put("num", "3");
	  
	  List<Map<String, String>> list=new ArrayList<>();
	  list.add(map1);
	  list.add(map2);
	  list.add(map3);
	  
	  Order order=new Order();
	  order.setThirdOrder("yggc000004");
	  order.setSku(list);
	  order.setName("李万林");
	  order.setProvince("4");
	  order.setCity("48206");
	  order.setCounty("58506");
	  order.setAddress("北京市西城区");
	  order.setZip("100000");
	  order.setMobile("15510505500");
	  order.setEmail("519978232@qq.com");
	  order.setRemark("测试订单");
	  order.setCreatorName("李万林");
	  order.setCreatorMobile("15510505500");
	  order.setCreatedTime("2019-05-07 17:15:55");
	  order.setInvoiceState("1");
	  order.setInvoiceType("1");
	  order.setCompanyName("北京阳光公采");
	  order.setInvoiceContent("1");
	  order.setPayment("1");
	  order.setCustomerName("北京中油瑞飞信息技术有限责任公司");*/
	  //yggc0000019000008295,yggc0000029000008295
	 /* OrderCreateApi api=new OrderCreateApi(order);
	  OrderCreateRes call = api.call();
	  CreateOrderAttr createOrderAttr = call.getCreateOrderAttr();
	  System.out.println(createOrderAttr.getSupplierOrderId());*/
	  
	  
	/* ConfirmOrderApi api=new ConfirmOrderApi("yggc0000049000008295");
	  ConfirmOrderRes call = api.call();
	  System.out.println(call.getResult());*/
	  
	  /*OrderCancelApi api=new OrderCancelApi("yggc0000029000008295");
	  OrderCancelRes call = api.call();
	  System.out.println(call.getResult());*/
	  
	/*OrderTrackApi api=new OrderTrackApi("4501300563");
	  OrderTrackRes call = api.call();*/
	  
	  
	  /*GetMessageApi api=new GetMessageApi(13);
	  MessageRes call = api.call();*/
	 /*GetMessageApi api=new GetMessageApi(2);
	  MessageRes call = api.call();
	  System.out.println(call.isSuccess());
	  List<MessageAttr> attr = call.getAttr();*/
	 /* for (MessageAttr messageAttr : attr) {
		  MessageSku message = (MessageSku) messageAttr.getMessage();
		  System.out.println(messageAttr.getType()+"-=-="+message.getSkuId()+"-=-="+message.getState());
	}*/
	 /*MessageDelApi api=new MessageDelApi("72935647");
	  api.call();*/
	  GetMessageApi api=new GetMessageApi(6);
	  MessageRes call = api.call();
	/*GetMessageApi api=new GetMessageApi(1);
	  MessageRes call = api.call();
	  System.out.println(call.isSuccess());
	  if(call.isSuccess()) {
		  List<MessageAttr> attr = call.getAttr();
		  for (MessageAttr messageAttr : attr) {
			MessageOrder messageOrder=(MessageOrder)messageAttr.getMessage();
			System.out.println(messageOrder.getOrderId());
		}
	  }*/
	/* SelectOrderApi api=new SelectOrderApi("201905301617290000019000002123",1);
	  SelectOrderRes call = api.call();
	  if(call.isSuccess()) {
		  SelectOrderAttr attr = call.getAttr();
		  System.out.println(attr.getOrderState());
		  System.out.println(attr.getSubmitState());
		  System.out.println(attr.getType());
		  
		  www.tonghao.mall.api.stb.attwrap.Order getpOrder = attr.getpOrder();
		  List<OrderSkus> sku = getpOrder.getSku();
		  for (OrderSkus orderSkus : sku) {
			System.out.println(orderSkus.getSkuId()+"-=-="+orderSkus.getNum());
		  }
		  
		  
		  
	  }*/
  }
}
