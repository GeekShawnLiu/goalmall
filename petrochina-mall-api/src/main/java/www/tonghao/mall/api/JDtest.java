package www.tonghao.mall.api;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import www.tonghao.mall.api.jd.attwrap.CheckAreaAttr;
import www.tonghao.mall.api.jd.attwrap.ChildrenOrderRepVO;
import www.tonghao.mall.api.jd.attwrap.Message;
import www.tonghao.mall.api.jd.attwrap.MessageAttr;
import www.tonghao.mall.api.jd.attwrap.MessageProduct;
import www.tonghao.mall.api.jd.attwrap.OrderSelectAttr;
import www.tonghao.mall.api.jd.attwrap.ParentOrderRepVO;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.api.jd.call.AreaProvincesApi;
import www.tonghao.mall.api.jd.call.AvailableNumberCompApi;
import www.tonghao.mall.api.jd.call.CategorysApi;
import www.tonghao.mall.api.jd.call.CheckAreaApi;
import www.tonghao.mall.api.jd.call.GetCustomerExpectCompApi;
import www.tonghao.mall.api.jd.call.MessageApi;
import www.tonghao.mall.api.jd.call.MessageDelApi;
import www.tonghao.mall.api.jd.call.OrderSelectApi;
import www.tonghao.mall.api.jd.call.OrderSelectChildrenApi;
import www.tonghao.mall.api.jd.call.ProductDetailApi;
import www.tonghao.mall.api.jd.call.ProductNewStockApi;
import www.tonghao.mall.api.jd.call.ProductPoolApi;
import www.tonghao.mall.api.jd.call.ProductPricesApi;
import www.tonghao.mall.api.jd.call.ProductSkusApi;
import www.tonghao.mall.api.jd.call.ProductStateApi;
import www.tonghao.mall.api.jd.call.ProductStockApi;
import www.tonghao.mall.api.jd.call.TrackApi;
import www.tonghao.mall.api.jd.entity.Sku;
import www.tonghao.mall.api.jd.resultwrap.AccessTokenRes;
import www.tonghao.mall.api.jd.resultwrap.CategorysRes;
import www.tonghao.mall.api.jd.resultwrap.CheckAreaRes;
import www.tonghao.mall.api.jd.resultwrap.MessageRes;
import www.tonghao.mall.api.jd.resultwrap.OrderSelectRes;

public class JDtest {
    /**
     * 时间格式
     */
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	 /**
     * main方法
     * @param args
     */
    public static void main(String[] args) {
        
    	/*AccessTokenApi accessTokenApi=new AccessTokenApi();
    	AccessTokenRes call = accessTokenApi.call();*/
    	//6995
    	/*CategorysApi api=new CategorysApi("1", "5000","","0");
    	CategorysRes call = api.call();
    	System.out.println(call.getResult().size());*/
    	
    	/**
    	 * 20190717
    	 */
    	/*ProductPoolApi api=new ProductPoolApi();
    	api.call();*/
    	
    	/**
    	 * 1028756
    	 */
    	/*ProductSkusApi api=new ProductSkusApi("20190717",1 );
    	api.call();*/
    	
    	/*ProductPricesApi api=new ProductPricesApi("1012921");
    	api.call();*/
    	
    	
    	/*ProductDetailApi api=new ProductDetailApi("4603751");
    	api.call();*/
    	
    	/*ProductStateApi api=new ProductStateApi("1028756");
    	api.call();*/
    	
    	/*MessageApi api=new MessageApi(16);
    	MessageRes call = api.call();
    	List<MessageAttr> result = call.getResult();
    	for (MessageAttr messageAttr : result) {
    		MessageProduct message =(MessageProduct) messageAttr.getMessage();
    		MessageDelApi api2=new MessageDelApi(messageAttr.getId());
    		api2.call();
    		
    		
		}*/
    	/*MessageApi api=new MessageApi(5);
    	api.call();*/
    	/*MessageDelApi api2=new MessageDelApi("45700757061");
		api2.call();*/
    	/*AreaProvincesApi api=new AreaProvincesApi();
    	api.call();*/
    	
    	/*AvailableNumberCompApi api=new AvailableNumberCompApi("100484720780", "1545858");
    	api.call();*/
    	
    	/*GetCustomerExpectCompApi api=new GetCustomerExpectCompApi("100484720780", "1545858");
    	api.call();*/
    	/**
    	 * 
    	 *  206660
			206700
			851474
			1119934
			1280271
			1379592
			1438526
			1569068
			2015246
			3903991
			3919595
			5325340
			1_2809_51217
    	 */
    	List<Sku> list=new ArrayList<>();
    	Sku sku1=new Sku();
    	sku1.setSkuId("3903991");
    	sku1.setNum(1);
    	list.add(sku1);
    	Sku sku2=new Sku();
    	sku2.setSkuId("206660");
    	sku2.setNum(1);
    	list.add(sku2);
    	Sku sku3=new Sku();
    	sku3.setSkuId("206700");
    	sku3.setNum(1);
    	list.add(sku3);
    	Sku sku4=new Sku();
    	sku4.setSkuId("1379592");
    	sku4.setNum(2);
    	list.add(sku4);
    	Sku sku5=new Sku();
    	sku5.setSkuId("3919595");
    	sku5.setNum(1);
    	list.add(sku5);
    	Sku sku6=new Sku();
    	sku6.setSkuId("2015246");
    	sku6.setNum(1);
    	list.add(sku6);
    	Sku sku7=new Sku();
    	sku7.setSkuId("1280271");
    	sku7.setNum(2);
    	list.add(sku7);
    	Sku sku8=new Sku();
    	sku8.setSkuId("1119934");
    	sku8.setNum(2);
    	list.add(sku8);
    	Sku sku9=new Sku();
    	sku9.setSkuId("1569068");
    	sku9.setNum(2);
    	list.add(sku9);
    	Sku sku10=new Sku();
    	sku10.setSkuId("851474");
    	sku10.setNum(2);
    	list.add(sku10);
    	Sku sku11=new Sku();
    	sku11.setSkuId("1438526");
    	sku11.setNum(1);
    	list.add(sku11);
    	Sku sku12=new Sku();
    	sku12.setSkuId("5325340");
    	sku12.setNum(1);
    	list.add(sku12);
    	
    	/*ProductNewStockApi api=new ProductNewStockApi("1_2809_51217",list);
    	api.call();*/
    	OrderSelectChildrenApi api=new OrderSelectChildrenApi("101006956750");
    	OrderSelectRes call = api.call();
    	OrderSelectAttr result = call.getResult();
    	/*ParentOrderRepVO getpOrder = result.getpOrder();
    	System.out.println(getpOrder.getSku().size());*/
    	/*TrackApi api=new TrackApi("101009551467");
    	api.call();*/
    	
    	
    	/*String str="182610,188056,188057,189795,206641,206660,206673,206690,206695,206700,206781,231415,235162,237566,246517,272123,290461,312606,312608,329873,519755,603050,615036,627913,701382,758086,819155,822992,829020,836075,845303,845313,845315,845317,851474,851549,851555,851739,851850,852488,852521,852532,852535,853188,853222,854232,865213,865223,865239,865242,865244,879370,879387,879388,906175,906180,906183,909717,909718,933697,1003066,1003069,1003077,1003079,1004472,1012921,1012927,1012952,1022146,1032887,1036236,1036237,1036741,1039778,1039866,1039880,1039910,1068548,1068549,1071847,1073833,1075103,1090151,1094869,1102527,1102530,1102536,1102538,1102539,1119934,1119935,1120709,1120715,1120733,1120734,1125276,1131257,1165230,1184675,1184678,1184681,1190580,1211587,1217064,1217076,1218759,1236032,1236034,1237316,1239731,1241849,1257287,1257289,1269279,1269280,1269303,1269377,1280260,1280261,1280262,1280263,1280265,1280271,1282388,1282390,1282396,1291060,1291066,1296702,1306761,1309213,1309226,1312709,1320368,1323259,1335940,1335947,1335948,1335954,1339000,1346468,1352358,1374810,1379592,1383137,1383139,1416501,1428996,1432939,1437337,1437344,1438526,1455414,1459039,1470202,1495493,1519445,1520578,1520581,1520587,1545858,1552480,1569068,1595334,1595841,1599550,1647805,1647807,1662069,1743166,1743207,1758999,1759004,1772691,1782838,1816748,1819348,1841324,1841432,1841462,1858759,1931640,1943952,1944008,1954188,1954210,1961302,1974528,1975378,1975460,1975463,1978165,1978251,1978460,1989509,1989565,2005437,2015244,2015246,2066232,2108740,2108767,2174577,2201516,2201992,2202087,2202097,2249218,2256440,2257681,2273632,2273642,2299418,2304437,2304459,2349850,2350637,2350651,2351776,2368019,2368105,2372854,2373737,2447426,2461038,2490446,2490452,2490468,2490589,2490597,2490601,2523753,2566344,2587634,2635437,2646498,2647927,2706688,2725149,2758797,2786216,2787296,2795972,2796639,2796661,2796663,2865832,2873540,2886296,2888241,2912872,3009421,3034383,3068217,3068219,3071973,3080225,3092062,3100365,3100369,3108162,3165509,3166589,3169734,3207554,3221864,3298184,3298242,3323886,3331215,3343407,3347203,3373416,3379813,3398876,3398890,3398918,3431731,3472495,3595410,3595430,3674839,3686871,3709799,3723618,3777618,3816621,3821834,3837757,3853859,3895219,3903953,3903991,3907301,3919595,3926928,4018863,4018865,4030713,4073483,4075581,4113041,4139585,4139603,4139605,4166399,4189391,4204286,4281012,4281028,4291056,4291132,4365489,4402458,4411349,4432391,4432902,4486444,4486466,4540576,4553393,4564540,4575664,4576185,4606971,4607997,4608037,4624204,4678178,4713934,4732099,4746494,4765192,4765198,4796399,4817178,4848274,4857992,4880198,4925548,4927606,5020791,5037771,5052221,5089788,5120352,5139777,5154398,5227100,5264422,5267006,5268247,5268249,5298464,5302459,5307727,5316142,5325340,5392289,5463388,5506800,5544116,5550350,5658363,5658371,5658395,5769947,5793716,5835227,5851301,5911379,5911531,5920381,5922716,5950572,5962712,5962718,5977079,5981493,6012148,6024937,6024969,6034762,6038430,6038432,6100980,6112758,6115529,6141672,6150177,6213744,6213746,6325308,6337156,6473111,6582008,6906454,6930866,7035598,7160395,7184065,7184079,7189886,7249193,7293614,7293640,7404569,7417302,7475333,7476110,7507163,7507183,7507187,7534227,7535022,7564497,7631419,7736787,7784767,7913978,8094672,8130043,8130055,8139793,8414028,8460894,8460936,8478149,8501494,8501496,8525512,8545349,8622236,8626547,8833875,12179687022,100000040910,100000095959,100000149736,100000149746,100000177484,100000178488,100000178490,100000286947,100000348552,100000401323,100000525219,100000749914,100000757220,100000764550,100000764568,100001112383,100001147620,100001649467,100002013071,100002042370,100002070648,100002155081,100002288040,100002390689,100002542476,100002553023,100002727342,100002739234,100002841826,100002973743,100002973751,100002973753,100002973757,100003676855,100003676857,100004034440,100004674186,100004674192,100004760052,100004760064,100006038854,100006168588,100000178486,100000286921,100000322020,100000383607,100000402993,100001419450,1323828,1323829,1432849,1432866,1432898,1432899,1432953,1433208,1680643,1945630,2003569,2230810,2375916,4018869,4564516,4734592,4755623,5456030,6219483,7129774,7381556,8143858,8356375,865205,865216,865229,8674001,8674029,8804931,953407";
    	String[] split = str.split(",");
    	List<String> list=new ArrayList<>();
    	for (String sku : split) {
    		CheckAreaApi api=new CheckAreaApi(sku, "31", "2704", "2705");
    		CheckAreaRes call = api.call();
    		if(call.isSuccess()){
    			List<CheckAreaAttr> attr = call.getAttr();
    			CheckAreaAttr checkAreaAttr = attr.get(0);
    			if(checkAreaAttr.isAreaRestrict()==false){
    				list.add(checkAreaAttr.getSkuId());
    				System.out.println(checkAreaAttr.getSkuId());
    			}
    		}
		}
    	1_2809_51217
    	System.out.println(list.toArray());*/
    	/*CheckAreaApi api=new CheckAreaApi("206781","31", "2704", "2705");
		CheckAreaRes call = api.call();*/
    /*	CheckAreaApi api=new CheckAreaApi("7381556", "31", "2704", "2705");
		CheckAreaRes call = api.call();
    	
    	*/
    	
    	/*String str1="312606,2273632,312608,603050,272123,1068549,4606971,1552480,4281028,2273642,5307727,1236405,1239731,100000401323,1739089,4113041,3398876,3398908,4857039,2635437,100002013071,3169734,5139777,5547598,100002288040,3068217,5227100,3398890,731520,4924058,2384380,100000764550,3837785,1989509,2647927,206641,1989565,2372854,7160395,627913,188057,830455,188056,4678178,933697,4848274,1758999,7507163,1039778,329873,6930866,5851301,7631419,1545858,100000757220,7564497,2795972,2646498,3919595,1339000,4713934,6906454,3431731,7035598,7249193,3092062,2108740,1241849,2015246,1383139,2886296,4204286,5544116,1125276,2108767,4576185,5962718,3100369,1131257,1383137,5911531,2587634,8622236,1291066,1437337,8833875,100000149736,3472495,1437344,1816748,3723618,3331215,4411349,3100365,836075,100000149746,1455414,1470202,4624204,1291060,7535022";
    	String str2="188056,188057,206641,272123,312606,312608,329873,603050,627913,836075,933697,1039778,1068549,1125276,1131257,1239731,1241849,1291060,1291066,1339000,1383137,1383139,1437337,1437344,1455414,1470202,1545858,1552480,1758999,1816748,1989509,1989565,2015246,2108740,2108767,2273632,2273642,2372854,2587634,2635437,2646498,2647927,2795972,2886296,3068217,3092062,3100365,3100369,3169734,3331215,3398876,3398890,3431731,3472495,3723618,3919595,4113041,4204286,4281028,4411349,4576185,4606971,4624204,4678178,4713934,4848274,5139777,5227100,5307727,5544116,5851301,5911531,5962718,6906454,6930866,7035598,7160395,7249193,7507163,7535022,7564497,7631419,8622236,8833875,100000149736,100000149746,100000401323,100000757220,100000764550,100002013071,100002288040,1236405,5547598,4924058,1739089,731520,4857039,2384380,3398908,3837785";
    	String[] split1 = str1.split(",");
    	String[] split2 = str2.split(",");
    	
    	for (String strA : split1) {
    		boolean bool=false;
    		for (String strB : split2) {
    			if(strA.equals(strB)){
    				bool=true;
    			}
    		}
    		if(!bool){
    			System.out.println(strA);
    		}
		}
    	
    }*/
    }
  
}
