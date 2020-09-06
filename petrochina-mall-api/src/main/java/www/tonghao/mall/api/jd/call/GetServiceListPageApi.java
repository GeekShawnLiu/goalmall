package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.jd.attwrap.AfsServicebyCustomerPinAttr;
import www.tonghao.mall.api.jd.attwrap.ServiceListPageAttr;
import www.tonghao.mall.api.jd.reqwrap.GetServiceListPageReq;
import www.tonghao.mall.api.jd.resultwrap.GetServiceListPageRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * Description: 查询服务单概要Api
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetServiceListPageApi extends AbstractBusinesApi<GetServiceListPageRes>{

	private static Log logger = LogFactory.getLog(GetServiceListPageApi.class);
	
	public GetServiceListPageApi(String jdOrderId, Integer pageIndex, Integer pageSize){
		super(new GetServiceListPageReq(jdOrderId, pageIndex, pageSize));
	}
	
	@Override
	protected GetServiceListPageRes resolver(String result) {
		logger.info("result = " + result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		GetServiceListPageRes res = new GetServiceListPageRes();
		res.setSuccess(success);
		if(success){
			JsonNode jsonNodeAttr = rootNode.path("result");
			if(jsonNodeAttr != null){
				ServiceListPageAttr serviceListPageAttr = new ServiceListPageAttr();
				serviceListPageAttr.setTotalNum(jsonNodeAttr.path("totalNum").asInt());
				serviceListPageAttr.setPageSize(jsonNodeAttr.path("pageSize").asInt());
				serviceListPageAttr.setPageNum(jsonNodeAttr.path("pageNum").asInt());
				serviceListPageAttr.setPageIndex(jsonNodeAttr.path("pageIndex").asInt());
				JsonNode serviceInfoList = jsonNodeAttr.path("serviceInfoList");
				if(serviceInfoList != null && !serviceInfoList.isNull() && serviceInfoList.isArray()){
					List<AfsServicebyCustomerPinAttr> list = new ArrayList<>();
					for (JsonNode jsonNode : serviceInfoList) {
						AfsServicebyCustomerPinAttr afsServicebyCustomerPinAttr = new AfsServicebyCustomerPinAttr();
						afsServicebyCustomerPinAttr.setAfsServiceId(jsonNode.path("afsServiceId").asInt());
						afsServicebyCustomerPinAttr.setCustomerExpect(jsonNode.path("customerExpect").asInt());
						afsServicebyCustomerPinAttr.setCustomerExpectName(jsonNode.path("customerExpectName").asText());
						afsServicebyCustomerPinAttr.setAfsApplyTime(jsonNode.path("afsApplyTime").asText());
						afsServicebyCustomerPinAttr.setOrderId(jsonNode.path("orderId").asLong());
						afsServicebyCustomerPinAttr.setWareId(jsonNode.path("wareId").asLong());
						afsServicebyCustomerPinAttr.setWareName(jsonNode.path("wareName").asText());
						afsServicebyCustomerPinAttr.setAfsServiceStep(jsonNode.path("afsServiceStep").asInt());
						afsServicebyCustomerPinAttr.setAfsServiceStepName(jsonNode.path("afsServiceStepName").asText());
						afsServicebyCustomerPinAttr.setCancel(jsonNode.path("cancel").asInt());
						list.add(afsServicebyCustomerPinAttr);
					}
					serviceListPageAttr.setServiceInfoList(list);
				}
				res.setResult(serviceListPageAttr);
			}
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
}
