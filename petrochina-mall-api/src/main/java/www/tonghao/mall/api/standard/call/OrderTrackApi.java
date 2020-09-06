package www.tonghao.mall.api.standard.call;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.Track;
import www.tonghao.mall.api.standard.attwrap.TrackAttr;
import www.tonghao.mall.api.standard.reqwrap.OrderTrackReq;
import www.tonghao.mall.api.standard.resultwrap.OrderTrackRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

/**
 * 订单配送接口
 * @author developer001
 *	
 */
public class OrderTrackApi extends AbstractBusinesApi<OrderTrackRes>{
	private static Log log = LogFactory.getLog(OrderTrackApi.class);
	public OrderTrackApi(String emallcode,String tradeOrderId){
		super(new OrderTrackReq(emallcode,tradeOrderId));
	}
	@Override
	protected OrderTrackRes resolver(String result) {
		log.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderTrackRes res = new OrderTrackRes();
		res.setSuccess(success);
		if(success){
			TrackAttr orderTrack= new TrackAttr();
			JsonNode resultNode = rootNode.path("result");
			JsonNode orderTrackNode = resultNode.path("track");
			String orderId = resultNode.path("order_id").toString();
			orderTrack.setOrder_id(orderId);
			
			List<Track> trackList = Lists.newArrayList();
			Track track = null;
			for(JsonNode node:orderTrackNode){
				track = new Track();
				JsonNode operateTimeNode = node.path("operate_time");
				JsonNode contentNode = node.path("content");
				JsonNode operatorNode = node.path("operator");
				track.setOperate_time(operateTimeNode.asText());
				track.setContent(contentNode.asText());
				track.setOperator(operatorNode.asText());
				trackList.add(track);
			}
			orderTrack.setTrack(trackList);
			res.setResult(orderTrack);
		}
		return res;
	}
	
}
