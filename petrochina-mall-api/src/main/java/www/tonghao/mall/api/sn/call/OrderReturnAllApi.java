package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.reqwrap.OrderReturnAllReq;
import www.tonghao.mall.api.sn.resultwrap.OrderReturnAllRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.ApplyRejectedAddRequest;
import com.suning.api.entity.govbus.ApplyRejectedAddRequest.Skus;
import com.suning.api.entity.govbus.ApplyRejectedAddResponse;
import com.suning.api.entity.govbus.ApplyRejectedAddResponse.InfoList;
import com.suning.api.exception.SuningApiException;

/**
 * 退货接口申请
 */
public class OrderReturnAllApi extends AbstractSnApi<OrderReturnAllRes>{

	private static Log logger = LogFactory.getLog(OrderReturnAllApi.class);
	
	private String orderId;
	private List<Skus> skus;
	public OrderReturnAllApi(String orderId,List<Skus> skus){
		super(new OrderReturnAllReq());
		this.orderId=orderId;
        this.skus=skus;
	}
	
	@Override
	protected OrderReturnAllRes resolver(DefaultSuningClient client) {
		String result = result(client);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		OrderReturnAllRes res = new OrderReturnAllRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁退货接口错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode addApplyRejectedNode = bodyNode.path("addApplyRejected");
			JsonNode orderIdNode = addApplyRejectedNode.path("orderId");
			res.setOrderId(orderIdNode.asText());
			JsonNode infoListNode = addApplyRejectedNode.path("infoList");
			List<InfoList> infoLists = new ArrayList<InfoList>();
			InfoList infoList = null;
			for(JsonNode node : infoListNode){
				infoList = new InfoList();
				JsonNode statusNode = node.path("status");		//处理结果1：成功，0：失败
				JsonNode skuIdNode = node.path("skuId");			//商品编码
				JsonNode unableReasonNode = node.path("unableReason");	//不能取消的原因
				infoList.setStatus(statusNode.asText());
				infoList.setSkuId(skuIdNode.asText());
				infoList.setUnableReason(unableReasonNode.asText());
				infoLists.add(infoList);
			}
			res.setInfoList(infoLists);
		}
		return res;
	}


	@Override
	protected String result(DefaultSuningClient client) {
		ApplyRejectedAddRequest  request = new ApplyRejectedAddRequest();//api入参校验逻辑开关
		request.setCheckParam(true);
		request.setOrderId(orderId);
		
		request.setSkus(skus);
		ApplyRejectedAddResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("退货接口申请失败！");
		}
		return response.getBody();
	}
	
}
