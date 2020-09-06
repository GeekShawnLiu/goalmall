package www.tonghao.mall.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import www.tonghao.mall.api.standard.attwrap.CatalogPoolAttr;
import www.tonghao.mall.api.standard.attwrap.CreOrdSku;
import www.tonghao.mall.api.standard.attwrap.OrderCreateAttr;
import www.tonghao.mall.api.standard.attwrap.Track;
import www.tonghao.mall.api.standard.attwrap.TrackAttr;
import www.tonghao.mall.api.standard.call.AccessTokenApi;
import www.tonghao.mall.api.standard.call.AreaCitiesApi;
import www.tonghao.mall.api.standard.call.AreaCountyApi;
import www.tonghao.mall.api.standard.call.AreaProvincesApi;
import www.tonghao.mall.api.standard.call.CatalogPoolApi;
import www.tonghao.mall.api.standard.call.OrderCancelApi;
import www.tonghao.mall.api.standard.call.OrderConfirmApi;
import www.tonghao.mall.api.standard.call.OrderCreateApi;
import www.tonghao.mall.api.standard.call.OrderSelectApi;
import www.tonghao.mall.api.standard.call.OrderTrackApi;
import www.tonghao.mall.api.standard.call.ProductPricesApi;
import www.tonghao.mall.api.standard.call.ProductStocksApi;
import www.tonghao.mall.api.standard.entity.Order;
import www.tonghao.mall.api.standard.resultwrap.AccessTokenRes;
import www.tonghao.mall.api.standard.resultwrap.CatalogPoolRes;
import www.tonghao.mall.api.standard.resultwrap.OrderCreateRes;
import www.tonghao.mall.api.standard.resultwrap.OrderTrackRes;
import www.tonghao.mall.api.standard.resultwrap.ProductStocksRes;

public class DLTest {

	public static void main(String[] args) {
		AccessTokenApi accessTokenApi=new AccessTokenApi("dl");
		AccessTokenRes call = accessTokenApi.call();
		
		/*CatalogPoolApi api=new CatalogPoolApi("dl");
		CatalogPoolRes call = api.call();
		if(call.isSuccess()){
			if(!CollectionUtils.isEmpty(call.getResult())){
				for (CatalogPoolAttr string : call.getResult()) {
					System.out.println(string.getId()+"-=-="+string.getName());
				}
			}else{
				System.out.println("---");
			}
		}*/
		
		/*ProductPricesApi api=new ProductPricesApi("dl", "100000798PCS");
		api.call();*/
		
		
		/*ProductStocksApi api=new ProductStocksApi("dl", "10_1_1020", "100000798PCS");
		ProductStocksRes call = api.call();*/
		
		
		  Order order=new Order();
		  List<CreOrdSku> creOrdSkus=new ArrayList<CreOrdSku>();
		  CreOrdSku creOrdSku=new CreOrdSku();
		  creOrdSku.setMode(1);
		  creOrdSku.setNum(1);
		  creOrdSku.setSku("100000798PCS");
		  creOrdSku.setPrice(new BigDecimal("54.80"));
		  creOrdSkus.add(creOrdSku);
		  order.setYggcOrder("zsy006");
		  order.setCreOrdSkus(creOrdSkus);
		  order.setName("测试收货人");
		  order.setProvince("10");
		  order.setCity("17");
		  order.setCounty("1013");
		  order.setAddress("北京市西城区佟麟阁路尚信商务");
		  order.setZip("111111");
		  order.setPhone("15154566709");
		  order.setMobile("15510505500");
		  order.setEmail("519978232@qq.com");
		  order.setRemark("测试测试");
		  order.setDepName("测试单位");
		  order.setInvoiceTitle("测试");
		  order.setInvoiceType(2);
		  order.setInvoiceOrgCode("23123");
		  order.setInvoiceName("测试");
		  order.setInvoicePhone("15512323121");
		  order.setInvoiceBank("交通银行");
		  order.setInvoiceBankCode("4402022817626356123");
		  order.setInvoiceAddress("测试地址");
		  order.setInvoiceMobile("15254566709");
		  order.setInvoiceReceiptAddress("测试地址");
		  order.setPayment(2);
		  order.setOrderPrice(new BigDecimal("54.80"));
		  order.setFreight(new BigDecimal("0"));
		  order.setMode(1);
		  
		  //44619354
		/*OrderCreateApi api=new OrderCreateApi("dl", order);
		  OrderCreateRes call = api.call();
			if(call.isSuccess()){
				OrderCreateAttr result = call.getResult();
				System.out.println(result.getOrderPrice());
			}*/
		
		  
		/*  OrderCancelApi api=new OrderCancelApi("dl", "49104416");
		  api.call();*/
		  
		/*AreaProvincesApi api=new AreaProvincesApi("dl");
		api.call();*/
		/*AreaCitiesApi api=new AreaCitiesApi("dl","10");
		api.call();*/
		
		/*AreaCountyApi api=new AreaCountyApi("dl","364");
		api.call();*/
		  
		  /*OrderSelectApi  api=new OrderSelectApi("dl", "49104416");
		  api.call();*/
		  
		  /*OrderConfirmApi api=new OrderConfirmApi("dl", "44328670");
		  api.call();*/
		  
		  /*OrderTrackApi api=new OrderTrackApi("dl", "44328670");
		  OrderTrackRes orderTrackRes=api.call();
		  if(orderTrackRes.isSuccess()){
			  TrackAttr result = orderTrackRes.getResult();
			  List<Track> track = result.getTrack();
			  for (Track track2 : track) {
				System.out.println(track2.getContent()+"-=-="+track2.getOperate_time()+"-=-="+track2.getOperator());
			}
		  }*/
		  
	}
	
	
}
