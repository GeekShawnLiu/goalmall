package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.CustomerExpectCompAttr;
import www.tonghao.mall.api.jd.reqwrap.CustomerExpectCompReq;
import www.tonghao.mall.api.jd.resultwrap.CustomerExpectCompRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 *根据订单号、商品编号查询支持的服务类型
 */
public class CustomerExpectCompApi extends AbstractBusinesApi<CustomerExpectCompRes> {
	private static Log logger = LogFactory.getLog(CustomerExpectCompApi.class);
	public CustomerExpectCompApi(String orderId,String sku){
		super(new CustomerExpectCompReq(orderId, sku));
	}

	@Override
	protected CustomerExpectCompRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		CustomerExpectCompRes res=new CustomerExpectCompRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			CustomerExpectCompAttr attr=null;
			List<CustomerExpectCompAttr> attrs=new ArrayList<CustomerExpectCompAttr>();
			for (JsonNode jsonNode : resultNode) {
				attr=new CustomerExpectCompAttr();
				attr.setCode(jsonNode.path("code").asText());
				attr.setName(jsonNode.path("name").asText());
				attrs.add(attr);
			}
			res.setResult(attrs);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
	
}
