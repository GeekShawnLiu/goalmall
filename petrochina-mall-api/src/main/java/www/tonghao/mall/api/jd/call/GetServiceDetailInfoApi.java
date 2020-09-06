package www.tonghao.mall.api.jd.call;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.jd.attwrap.CompatibleServiceDetailDTO;
import www.tonghao.mall.api.jd.attwrap.ServiceAftersalesAddressInfoDTO;
import www.tonghao.mall.api.jd.attwrap.ServiceCustomerInfoDTO;
import www.tonghao.mall.api.jd.attwrap.ServiceDetailInfoDTO;
import www.tonghao.mall.api.jd.attwrap.ServiceExpressInfoDTO;
import www.tonghao.mall.api.jd.attwrap.ServiceFinanceDetailInfoDTO;
import www.tonghao.mall.api.jd.attwrap.ServiceTrackInfoDTO;
import www.tonghao.mall.api.jd.reqwrap.GetServiceDetailInfoReq;
import www.tonghao.mall.api.jd.resultwrap.GetServiceDetailInfoRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * Description: 查询服务单明细Api
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class GetServiceDetailInfoApi extends AbstractBusinesApi<GetServiceDetailInfoRes>{

	private static Log logger = LogFactory.getLog(GetServiceDetailInfoApi.class);
	
	public GetServiceDetailInfoApi(String afsServiceId, List<Integer> appendInfoSteps){
		super(new GetServiceDetailInfoReq(afsServiceId, appendInfoSteps));
	}
	
	@Override
	protected GetServiceDetailInfoRes resolver(String result) {
		logger.info("result = " + result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		GetServiceDetailInfoRes res = new GetServiceDetailInfoRes();
		res.setSuccess(success);
		if(success){
			JsonNode jsonNodeAttr = rootNode.path("result");
			if(jsonNodeAttr != null){
				CompatibleServiceDetailDTO compatibleServiceDetailDTO = new CompatibleServiceDetailDTO();
				compatibleServiceDetailDTO.setAfsServiceId(jsonNodeAttr.path("afsServiceId").asInt());
				compatibleServiceDetailDTO.setCustomerExpect(jsonNodeAttr.path("customerExpect").asInt());
				compatibleServiceDetailDTO.setAfsApplyTime(jsonNodeAttr.path("afsApplyTime").asText());
				compatibleServiceDetailDTO.setOrderId(jsonNodeAttr.path("orderId").asLong());
				compatibleServiceDetailDTO.setIsHasInvoice(jsonNodeAttr.path("isHasInvoice").asInt());
				compatibleServiceDetailDTO.setIsNeedDetectionReport(jsonNodeAttr.path("isNeedDetectionReport").asInt());
				compatibleServiceDetailDTO.setIsHasPackage(jsonNodeAttr.path("isHasPackage").asInt());
				JsonNode questionPicNode = jsonNodeAttr.path("questionPic");
				if(questionPicNode != null && questionPicNode.asText() != null){
					compatibleServiceDetailDTO.setQuestionPic(questionPicNode.asText());
				}
				compatibleServiceDetailDTO.setAfsServiceStep(jsonNodeAttr.path("afsServiceStep").asInt());
				compatibleServiceDetailDTO.setAfsServiceStepName(jsonNodeAttr.path("afsServiceStepName").asText());
				compatibleServiceDetailDTO.setApproveNotes(jsonNodeAttr.path("approveNotes").asText());
				JsonNode questionDescNode = jsonNodeAttr.path("questionDesc");
				if(questionDescNode != null && questionDescNode.asText() != null){
					compatibleServiceDetailDTO.setQuestionDesc(questionDescNode.asText());
				}
				compatibleServiceDetailDTO.setApprovedResult(jsonNodeAttr.path("approvedResult").asInt());
				compatibleServiceDetailDTO.setApprovedResultName(jsonNodeAttr.path("approvedResultName").asText());
				compatibleServiceDetailDTO.setProcessResult(jsonNodeAttr.path("processResult").asInt());
				compatibleServiceDetailDTO.setProcessResultName(jsonNodeAttr.path("processResultName").asText());
				//客户信息
				JsonNode serviceCustomerInfoDTONode = jsonNodeAttr.path("serviceCustomerInfoDTO");
				if(serviceCustomerInfoDTONode != null && !serviceCustomerInfoDTONode.isNull()){
					ServiceCustomerInfoDTO serviceCustomerInfoDTO = new ServiceCustomerInfoDTO();
					serviceCustomerInfoDTO.setCustomerPin(serviceCustomerInfoDTONode.path("customerPin").asText());
					serviceCustomerInfoDTO.setCustomerName(serviceCustomerInfoDTONode.path("customerName").asText());
					serviceCustomerInfoDTO.setCustomerContactName(serviceCustomerInfoDTONode.path("customerContactName").asText());
					serviceCustomerInfoDTO.setCustomerTel(serviceCustomerInfoDTONode.path("customerTel").asText());
					serviceCustomerInfoDTO.setCustomerMobilePhone(serviceCustomerInfoDTONode.path("customerMobilePhone").asText());
					JsonNode customerEmailNode = serviceCustomerInfoDTONode.path("customerEmail");
					if(customerEmailNode != null && customerEmailNode.asText() != null){
						serviceCustomerInfoDTO.setCustomerEmail(customerEmailNode.asText());
					}
					JsonNode customerPostcodeNode = serviceCustomerInfoDTONode.path("customerPostcode");
					if(customerPostcodeNode != null && customerPostcodeNode.asText() != null){
						serviceCustomerInfoDTO.setCustomerPostcode(customerPostcodeNode.asText());
					}
					compatibleServiceDetailDTO.setServiceCustomerInfoDTO(serviceCustomerInfoDTO);
				}
				//售后地址信息
				JsonNode serviceAftersalesAddressInfoDTONode = jsonNodeAttr.path("serviceAftersalesAddressInfoDTO");
				if(serviceAftersalesAddressInfoDTONode != null && !serviceAftersalesAddressInfoDTONode.isNull()){
					ServiceAftersalesAddressInfoDTO serviceAftersalesAddressInfoDTO = new ServiceAftersalesAddressInfoDTO();
					serviceAftersalesAddressInfoDTO.setAddress(serviceAftersalesAddressInfoDTONode.path("address").asText());
					serviceAftersalesAddressInfoDTO.setTel(serviceAftersalesAddressInfoDTONode.path("tel").asText());
					serviceAftersalesAddressInfoDTO.setLinkMan(serviceAftersalesAddressInfoDTONode.path("linkMan").asText());
					JsonNode postCodeNode = serviceAftersalesAddressInfoDTONode.path("postCode");
					if(postCodeNode != null && postCodeNode.asText() != null){
						serviceAftersalesAddressInfoDTO.setPostCode(postCodeNode.asText());
					}
					compatibleServiceDetailDTO.setServiceAftersalesAddressInfoDTO(serviceAftersalesAddressInfoDTO);
				}
				//客户发货信息
				JsonNode serviceExpressInfoDTONode = jsonNodeAttr.path("serviceExpressInfoDTO");
				if(serviceExpressInfoDTONode != null && !serviceExpressInfoDTONode.isNull()){
					ServiceExpressInfoDTO serviceExpressInfoDTO = new ServiceExpressInfoDTO();
					serviceExpressInfoDTO.setAfsServiceId(serviceExpressInfoDTONode.path("afsServiceId").asInt());
					serviceExpressInfoDTO.setFreightMoney(serviceExpressInfoDTONode.path("freightMoney").asText());
					serviceExpressInfoDTO.setExpressCompany(serviceExpressInfoDTONode.path("expressCompany").asText());
					serviceExpressInfoDTO.setDeliverDate(serviceExpressInfoDTONode.path("deliverDate").asText());
					serviceExpressInfoDTO.setExpressCode(serviceExpressInfoDTONode.path("expressCode").asText());
					compatibleServiceDetailDTO.setServiceExpressInfoDTO(serviceExpressInfoDTO);
				}
				//退款明细 
				JsonNode serviceFinanceDetailInfoDTOsNode = jsonNodeAttr.path("serviceFinanceDetailInfoDTOs");
				if(serviceFinanceDetailInfoDTOsNode != null && !serviceFinanceDetailInfoDTOsNode.isNull() && serviceFinanceDetailInfoDTOsNode.isArray()){
					List<ServiceFinanceDetailInfoDTO> list = new ArrayList<>();
					for (JsonNode jsonNode : serviceFinanceDetailInfoDTOsNode) {
						ServiceFinanceDetailInfoDTO serviceFinanceDetailInfoDTO = new ServiceFinanceDetailInfoDTO();
						serviceFinanceDetailInfoDTO.setRefundWay(jsonNode.path("refundWay").asInt());
						serviceFinanceDetailInfoDTO.setRefundWayName(jsonNode.path("refundWayName").asText());
						serviceFinanceDetailInfoDTO.setStatus(jsonNode.path("status").asInt());
						serviceFinanceDetailInfoDTO.setStatusName(jsonNode.path("statusName").asText());
						serviceFinanceDetailInfoDTO.setRefundPrice(new BigDecimal(jsonNode.path("refundPrice").asDouble()));
						serviceFinanceDetailInfoDTO.setWareName(jsonNode.path("wareName").asText());
						serviceFinanceDetailInfoDTO.setWareId(jsonNode.path("wareId").asInt());
						list.add(serviceFinanceDetailInfoDTO);
					}
					compatibleServiceDetailDTO.setServiceFinanceDetailInfoDTOs(list);
				}
				//服务单追踪信息
				JsonNode serviceTrackInfoDTOsNode = jsonNodeAttr.path("serviceTrackInfoDTOs");
				if(serviceTrackInfoDTOsNode != null && !serviceTrackInfoDTOsNode.isNull() && serviceTrackInfoDTOsNode.isArray()){
					List<ServiceTrackInfoDTO> list = new ArrayList<>();
					for (JsonNode jsonNode : serviceTrackInfoDTOsNode) {
						ServiceTrackInfoDTO serviceTrackInfoDTO = new ServiceTrackInfoDTO();
						serviceTrackInfoDTO.setAfsServiceId(jsonNode.path("afsServiceId").asInt());
						serviceTrackInfoDTO.setTitle(jsonNode.path("title").asText());
						serviceTrackInfoDTO.setContext(jsonNode.path("context").asText());
						serviceTrackInfoDTO.setCreateDate(jsonNode.path("createDate").asText());
						serviceTrackInfoDTO.setCreateName(jsonNode.path("createName").asText());
						serviceTrackInfoDTO.setCreatePin(jsonNode.path("createPin").asText());
						list.add(serviceTrackInfoDTO);
					}
					compatibleServiceDetailDTO.setServiceTrackInfoDTOs(list);
				}
				//服务单商品明细
				JsonNode serviceDetailInfoDTOsNode = jsonNodeAttr.path("serviceDetailInfoDTOs");
				if(serviceDetailInfoDTOsNode != null && !serviceDetailInfoDTOsNode.isNull() && serviceDetailInfoDTOsNode.isArray()){
					List<ServiceDetailInfoDTO> list = new ArrayList<>();
					for (JsonNode jsonNode : serviceDetailInfoDTOsNode) {
						ServiceDetailInfoDTO serviceDetailInfoDTO = new ServiceDetailInfoDTO();
						serviceDetailInfoDTO.setWareId(jsonNode.path("wareId").asInt());
						serviceDetailInfoDTO.setWareName(jsonNode.path("wareName").asText());
						serviceDetailInfoDTO.setWareBrand(jsonNode.path("wareBrand").asText());
						serviceDetailInfoDTO.setAfsDetailType(jsonNode.path("afsDetailType").asInt());
						JsonNode wareDescribeNode = jsonNode.path("wareDescribe");
						if(wareDescribeNode != null && wareDescribeNode.asText() != null){
							serviceDetailInfoDTO.setWareDescribe(wareDescribeNode.asText());
						}
						list.add(serviceDetailInfoDTO);
					}
					compatibleServiceDetailDTO.setServiceDetailInfoDTOs(list);
				}
				//获取服务单允许的操作列表
				JsonNode allowOperationsNode = jsonNodeAttr.path("allowOperations");
				if(allowOperationsNode != null && allowOperationsNode.isArray()){
					List<Integer> list = new ArrayList<>();
					for (JsonNode jsonNode : allowOperationsNode) {
						list.add(jsonNode.asInt());
					}
					compatibleServiceDetailDTO.setAllowOperations(list);
				}
				res.setResult(compatibleServiceDetailDTO);
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
