package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.Track;
import www.tonghao.mall.api.jd.attwrap.TrackAttr;
import www.tonghao.mall.api.jd.reqwrap.TrackReq;
import www.tonghao.mall.api.jd.resultwrap.TrackRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 *物流接口api
 */
public class TrackApi extends AbstractBusinesApi<TrackRes> {
	private static Log logger = LogFactory.getLog(TrackApi.class);
	public TrackApi(String orderId){
		super(new TrackReq(orderId));
	}
	
	@Override
	protected TrackRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		TrackRes res=new TrackRes();
		res.setSuccess(success);
		if(success){
			TrackAttr attr=new TrackAttr(); 
			JsonNode resultNode = rootNode.path("result");
			JsonNode jdOrderId_Node = resultNode.path("jdOrderId");
			attr.setJdOrderId(jdOrderId_Node.asText());
			JsonNode orderTrack_Node = resultNode.path("orderTrack");
			List<Track> list=new ArrayList<Track>();
			Track track=null;
			for (JsonNode jsonNode : orderTrack_Node) {
				track=new Track();
				track.setContent(jsonNode.path("content").asText());
				track.setMsgTime(jsonNode.path("msgTime").asText());
				track.setOperator(jsonNode.path("operator").asText());
				list.add(track);
			}
			attr.setOrderTrack(list);
			res.setResult(attr);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultMessage(resultMessageNode.asText());
			res.setResultCode(resultCodeNode.asText());
		}
		return res;
	}

}
