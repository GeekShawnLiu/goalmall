package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.OrderReturnOtherReq;
import www.tonghao.mall.api.sn.resultwrap.OrderReturnOtherRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.ReturnpartorderAddRequest;
import com.suning.api.entity.govbus.ReturnpartorderAddRequest.Skus;
import com.suning.api.entity.govbus.ReturnpartorderAddResponse.InfoList;
import com.suning.api.entity.govbus.ReturnpartorderAddResponse;
import com.suning.api.exception.SuningApiException;

/**
 * 部分退货接口
 */
public class OrderReturnOtherApi extends AbstractSnApi<OrderReturnOtherRes>{

	private static Log logger = LogFactory.getLog(OrderReturnOtherApi.class);
	
	private String orderId;
	private List<Skus> skus;
	
	public OrderReturnOtherApi(String orderId,List<Skus> skus){
		super(new OrderReturnOtherReq());
		this.orderId = orderId;
		this.skus = skus;
	}
	
	@Override
	protected OrderReturnOtherRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("OrderReturnOtherApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		OrderReturnOtherRes res = new OrderReturnOtherRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁部分退货接口错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode addReturnpartorderNode = bodyNode.path("addReturnpartorder");
			
			JsonNode infoListNode = addReturnpartorderNode.path("infoList");
			List<InfoList> infoLists = new ArrayList<InfoList>();
			InfoList infoList = null;
			for(JsonNode node : infoListNode){
				infoList = new InfoList();
				JsonNode statusNode = node.path("status");		//处理结果1：成功，0：失败
				JsonNode skuIdNode = node.path("skuId");			//商品编码
				JsonNode unableReasonNode = node.path("unableReason");	//单行描述
				infoList.setStatus(statusNode.asText());
				infoList.setSkuId(skuIdNode.asText());
				infoList.setUnableReason(unableReasonNode.asText());
				infoLists.add(infoList);
			}
			res.setInfoList(infoLists);
			
			JsonNode isSuccessNode = addReturnpartorderNode.path("isSuccess");
			JsonNode orderIdNode = addReturnpartorderNode.path("orderId");
			res.setOrderId(orderIdNode.asText());
			res.setIsSuccess(isSuccessNode.asText());
		}
		return res;
	}

	@Override
	protected String result(DefaultSuningClient client) {
		ReturnpartorderAddRequest request = new ReturnpartorderAddRequest();
		request.setCheckParam(true);
		request.setOrderId(orderId);
		request.setSkus(skus);
		ReturnpartorderAddResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("部分退货失败！");
		}
		return response.getBody();
	}
	
}
