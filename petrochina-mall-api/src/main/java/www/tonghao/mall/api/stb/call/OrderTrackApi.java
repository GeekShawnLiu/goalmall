package www.tonghao.mall.api.stb.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.OrderTrackAttr;
import www.tonghao.mall.api.stb.attwrap.Track;
import www.tonghao.mall.api.stb.reqwarp.OrderTrackReq;
import www.tonghao.mall.api.stb.resultwrap.OrderTrackRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class OrderTrackApi extends AbstractBusinesApi<OrderTrackRes> {

	private static Log logger = LogFactory.getLog(OrderTrackApi.class);
	public OrderTrackApi(String supplierOrderId) {
		super(new OrderTrackReq(supplierOrderId));
	}
	
	@Override
	protected OrderTrackRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderTrackRes orderTrackRes=new OrderTrackRes();
		orderTrackRes.setSuccess(success);
		if(success) {
			OrderTrackAttr orderTrackAttr=new OrderTrackAttr();
			JsonNode resultNode = rootNode.path("result");
			String supplierOrderId = resultNode.path("supplierOrderId").asText();
			JsonNode orderTrack = resultNode.path("orderTrack");
			List<Track> list=new ArrayList<>();
			for (JsonNode jsonNode : orderTrack) {
				Track attr=new Track();
				String msgTime = jsonNode.path("msgTime").asText();
				String content = jsonNode.path("contentMsg").asText();
				String operator = jsonNode.path("company").asText();
				attr.setMsgTime(msgTime);
				attr.setContent(content);
				attr.setOperator(operator);
				list.add(attr);
			}
			orderTrackAttr.setSupplierOrderId(supplierOrderId);
			orderTrackAttr.setTrackList(list);
			orderTrackRes.setOrderTrackAttr(orderTrackAttr);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			orderTrackRes.setDesc(descNode.asText());
		}
		return orderTrackRes;
	}

}
