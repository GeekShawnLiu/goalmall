package www.tonghao;


public class Test {
   public static void main(String[] args) {
	  /* Map<String, String> map = new HashMap<>();
		map.put("code", "1234");
		map.put("time", "1");
		map.put("phone", "15510505500");
		//验证码缓存
		String doGet = HttpClient.doGet("http://148.70.52.60/jbzprd/sendcode", map);*/
		
	   /*接口名称：/sendtemmessage 
	   参数：String[] phoneNumbers,手机号；
	               String[] params, 需要发送的内容
	               String template  模板id*/
	   //"李万林", "2823716763", "2019-07-19", "积分支付", "2019-06-19", "15510505500"
		/*Map<String, String> map = new HashMap<>();
		map.put("phoneNumbers", "15510505500");
		map.put("params","李万林,2019-07-19,2823716763,积分支付,2019-06-19");
		map.put("template", "375749");*/
	   
	    /*Map<String, String> map = new HashMap<>();
		map.put("phoneNumbers", "13331056686");
		map.put("params", "1234,1");
		map.put("template", "387019");*/
	    /*String str="18810870188,陈冰（工）@13603289025,凌维泽@18640147708,杨少鹏@15909964501,包明明@13581855888,张靖雯@18809964181,曹虹@13095169033,裴旭光@18601936306,代伟亮@18211030406,陈先辉@18600895497,孔繁飞@13552161063,王新喆@13611208290,李强（系）@13999009388,彭建华@18706860307,于长海@18600672559,田凤婷@18610290800,樊星@13810333177,韩宏志@18611286651,张煊@18811085276,刘洋（系）@15201078578,张天玲@18901032099,陈利华@13810601328,蒋勇@18510022016,于良桂@15313168917,谭东杰@15600018701,杨铎@18502380011,刘培@18618277011,薛正燕@13911220961,李所清@18813093087,苏美帆@15353650765,郭麒@18500974600,陈元山@13953127420,刘光明@13911625270,郝庆超@13466733946,蒋小龙@15210281550,杨勤@15130342119,李惠军@18610080199,元红萍@15901080822,康潇瑜@15178812821,李萍（小）@18716000361,王常友@15513023831,郭强@15901127798,赵迪（东）@13241905388,潘渊成@18310050237,BefioPaulinEpaphrodite@18515199035,张馨艺@17710340614,张开尧@13121267208,OoiKhengWah@15912453156,林燕@13933216202,崔为民@18181947808,刘虎（成）@15184319562,富丹丹@18780067170,张超@13522902710,怀南@18618103447,郝泽祥@13381070592,刘涛（应）@18910063052,姬迎宾@13488674498,黄谦@15600229255,傅饶@18511907839,刘光来@18519766743,张易@13976420003,王与意@18510248182,黄小宁@18611010951,周健松@18611663232,张楠@18600021770,居晓霆@18234046005,陈小龙@18514254536,李岳明@18979737318,谢勇平@15810052705,康伟红@16803208310,庞滨@18610685092,郑铁军@13811340858,吴相林@18500956634,李明昕@13472210767,吕艳@18201547887,李颖@18513740675,张大哲@18401793687,周游@18611710099,黄兆越@13716348280,郭鹏@15321275650,张延伟@13439239147,高可心@18510453682,蒋发明@13691593961,刘兵兵@18740454480,张乐乐@13522339865,孟令培@13671341583,郭丽梅";
	    String[] split = str.split("@");
	    System.out.println(split.length);
	    for (int i = 0; i < split.length; i++) {
	    	String[] mob=split[i].split(",");
	    	System.out.println(mob[0]+"-=-="+mob[1]);
	    	Map<String, String> map = new HashMap<>();
			map.put("phoneNumbers", mob[0]);
			map.put("params",mob[1]+",2019-08-08 08:00:00,2019-08-09 14:00:00");
			map.put("template", "391467");
			String doGet = HttpClient.doGet("http://148.70.52.60/jbzprd/sendtemmessage", map);
			System.out.println(doGet);
		}*/
	    
	  /*  
	   Map<String, String> map = new HashMap<>();
		map.put("phoneNumbers", "15510505500");
		map.put("params","李万林,2019-08-08 08:00:00,2019-08-09 14:00:00");
		map.put("template", "391467");
	   
	   
		//验证码缓存
		String doGet = HttpClient.doGet("http://148.70.52.60/jbzprd/sendtemmessage", map);
		System.out.println(doGet);*/
	   
	   /*String[]  str = {"1","2","3","4","5"};
	   List<String> asList = Arrays.asList(str);
	   int num=asList.size()%2==0?asList.size()/2:asList.size()/2+1;
	   System.out.println(num);
	   for(int i=0;i<num;i++){
		   List<String> subList=null;
		   if(num-1==i){
			   subList= asList.subList(i*2, str.length);
		   }else{
			   subList= asList.subList(i*2, (i+1)*2); 
		   }
		   
		   System.out.println(subList.toString());
	   }*/
	   
	   /*String mobile222 = "";
	   
	   
	   
	   String mobile111 = "";
	   System.out.println(mobile111.split(",").length);
	   System.out.println(mobile222.split(",").length);
	   
	   Map<String, String> map = new HashMap<>();
		map.put("phoneNumbers", mobile222);
		map.put("params", "");
		map.put("template", "418029");
		String doGet = HttpClient.doGet("http://148.70.52.60/jbzprd/sendtemmessage", map);
	   System.out.println(doGet);*/
	   
} 
}
