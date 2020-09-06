package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.jd.attwrap.ComponentExportAttr;
import www.tonghao.mall.api.jd.reqwrap.GetCustomerExpectCompReq;
import www.tonghao.mall.api.jd.resultwrap.GetCustomerExpectCompRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * Description: 查询商品售后类型Api
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetCustomerExpectCompApi extends AbstractBusinesApi<GetCustomerExpectCompRes>{

	private static Log logger = LogFactory.getLog(GetCustomerExpectCompApi.class);
	
	public GetCustomerExpectCompApi(String jdOrderId, String skuId) {
		super(new GetCustomerExpectCompReq(jdOrderId, skuId));
	}

	@Override
	protected GetCustomerExpectCompRes resolver(String result) {
		logger.info("result = " + result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		GetCustomerExpectCompRes res = new GetCustomerExpectCompRes();
		res.setSuccess(success);
		if(success){
			List<ComponentExportAttr> list = new ArrayList<>();
			JsonNode jsonNode = rootNode.path("result");
			if(jsonNode != null && jsonNode.isArray()){
				for (JsonNode jsonNode2 : jsonNode) {
					ComponentExportAttr componentExportAttr = new ComponentExportAttr();
					componentExportAttr.setCode(jsonNode2.path("code").asText());
					componentExportAttr.setName(jsonNode2.path("name").asText());
					list.add(componentExportAttr);
				}
			}
			res.setResult(list);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
}
