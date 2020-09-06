package www.tonghao.mall.api.standard.call;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.OrderCheckr;
import www.tonghao.mall.api.standard.attwrap.OrderCheckrAttr;
import www.tonghao.mall.api.standard.reqwrap.OrderCheckrReq;
import www.tonghao.mall.api.standard.resultwrap.OrderCheckrRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 对账接口
 */
public class OrderCheckrApi extends AbstractBusinesApi<OrderCheckrRes> {

	private static Log logger = LogFactory.getLog(OrderCheckrApi.class);
	
	public OrderCheckrApi(String mallCode,String bdate,String edate){
		super(new OrderCheckrReq(mallCode, bdate, edate));
	}
	@Override
	protected OrderCheckrRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderCheckrRes checkrRes=new OrderCheckrRes();
		checkrRes.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			OrderCheckrAttr attr=new OrderCheckrAttr();
			JsonNode numPath = resultNode.path("num");
			attr.setNum(numPath.asInt());
			JsonNode ordersPath = resultNode.path("orders");
			List<OrderCheckr> checkrs=new ArrayList<OrderCheckr>();
			OrderCheckr checkr=null;
			for (JsonNode jsonNode : ordersPath) {
				checkr=new OrderCheckr();
				JsonNode orderId_path = jsonNode.path("order_id");
				JsonNode state_path = jsonNode.path("state");
				JsonNode invoice_state_path = jsonNode.path("invoice_state");
				JsonNode total_price_path = jsonNode.path("total_price");
				checkr.setOrderId(orderId_path.asText());
				checkr.setState(state_path.asInt());
				checkr.setInvoiceState(invoice_state_path.asInt());
				checkr.setTotalPrice(total_price_path.decimalValue());
				checkrs.add(checkr);
			}
			checkrRes.setResult(attr);
			
		}else{
			JsonNode descNode = rootNode.path("desc");
			checkrRes.setDesc(descNode.toString());
		}
		return checkrRes;
	}

}
