package www.tonghao.common.utils;

public class AssistUtil {
	
	/**
	 * 获取支付方式类型
	 * @param payway
	 * @return 1货到付款 2账期支付
	 */
	public static Integer getPayWayType(String payway){
		if(payway.contains("货到")){
			return 1;
		}else if(payway.contains("账期")){
			return 2;
		}
		return null;
	}
	
	/**
	 * 获取配送方式类型
	 * @param deliveryWay
	 * @return 1办公室 2楼下 3单位
	 */
	public static Integer getDeliveryType(String deliveryWay){
		if(deliveryWay.contains("办公")){
			return 1;
		}else if(deliveryWay.contains("楼")){
			return 2;
		}else if(deliveryWay.contains("单位")){
			return 3;
		}
		return null;
	}
	
	/**
	 * 获取发票类型
	 * @param invoiceType
	 * @return 1：增值税普通发票、2：增值税专用发票、3：电子发票
	 */
	public static String getInvoiceType(String invoiceType){
		if(invoiceType.contains("普通")){
			return "1";
		}else if(invoiceType.contains("专用")){
			return "2";
		}else if(invoiceType.contains("电子")){
			return "3";
		}
		return null;
	}
	
}
