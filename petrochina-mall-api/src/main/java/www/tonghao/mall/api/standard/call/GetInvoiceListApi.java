package www.tonghao.mall.api.standard.call;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.reqwrap.GetInvoiceListReq;
import www.tonghao.mall.api.standard.resultwrap.GetInvoiceListRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 获取电子发票接口
 */
public class GetInvoiceListApi extends AbstractBusinesApi<GetInvoiceListRes> {

	private static Log logger = LogFactory.getLog(GetInvoiceListApi.class);
	
	public GetInvoiceListApi(String emallcode,String order_id){
		super(new GetInvoiceListReq(emallcode,order_id));
	}
	@Override
	protected GetInvoiceListRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		GetInvoiceListRes getInvoiceListRes = new GetInvoiceListRes();
		getInvoiceListRes.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			Map<String, List<String>> mapRes = new HashMap<String, List<String>>(); 
			Iterator<Entry<String, JsonNode>> jsonNodes = resultNode.fields();
			while (jsonNodes.hasNext()) {  
		        Entry<String, JsonNode> node = jsonNodes.next();
		        String orderId = node.getKey();
		        logger.info(orderId);  
		        Iterator<JsonNode> elements = node.getValue().elements();
		        List<String> links = new ArrayList<String>();
		        while (elements.hasNext()) { 
		        	JsonNode realElement = elements.next();  
		        	String invoiceLink = realElement.toString();
		        	logger.info(realElement.toString());  
		            links.add(invoiceLink);
		        }
		        mapRes.put(orderId, links);
		    }
			getInvoiceListRes.setResult(mapRes);
		}else {
			JsonNode desc = rootNode.path("desc");
			getInvoiceListRes.setDesc(desc.asText());
		}
		return getInvoiceListRes;
	}
	
}
