package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.InvoiceAttr;
import www.tonghao.mall.api.jd.reqwrap.GetInvoiceListReq;
import www.tonghao.mall.api.jd.resultwrap.GetInvoiceListRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import springfox.documentation.spring.web.json.Json;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * 获取电子发票接口
 *
 */
public class GetInvoiceListApi extends AbstractBusinesApi<GetInvoiceListRes>{
	
	private static Log logger = LogFactory.getLog(GetInvoiceListApi.class);

	public GetInvoiceListApi(String jdOrderId){
		super(new GetInvoiceListReq(jdOrderId));
	}
	@Override
	protected GetInvoiceListRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		JsonNode resultMessageNode = rootNode.path("resultMessage");
		JsonNode resultCodeNode = rootNode.path("resultCode");
		boolean success = successNode.asBoolean();
		GetInvoiceListRes getInvoiceListRes = new GetInvoiceListRes();
		getInvoiceListRes.setSuccess(success);
		getInvoiceListRes.setResultCode(resultCodeNode.asText());
		getInvoiceListRes.setResultMessage(resultMessageNode.asText());
		if (success) {
			JsonNode resultNode = rootNode.path("result");
			Iterator<JsonNode> elements = resultNode.elements();
			List<InvoiceAttr> invoiceAttrs = new ArrayList<InvoiceAttr>();
			while (elements.hasNext()) {
				InvoiceAttr invoiceAttr = null;
				JsonNode realElement = elements.next(); 
				invoiceAttr = JsonUtil.toObject(realElement.toString(), InvoiceAttr.class);
				invoiceAttrs.add(invoiceAttr);
			}
			getInvoiceListRes.setResult(invoiceAttrs);
		}
		return getInvoiceListRes;
	}
	
}
