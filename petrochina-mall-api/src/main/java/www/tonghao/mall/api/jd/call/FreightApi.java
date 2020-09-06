package www.tonghao.mall.api.jd.call;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.FreightAttr;
import www.tonghao.mall.api.jd.entity.Freight;
import www.tonghao.mall.api.jd.reqwrap.FreightReq;
import www.tonghao.mall.api.jd.resultwrap.FreightRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 邮费接口
 *
 */
public class FreightApi extends AbstractBusinesApi<FreightRes> {
	private static Log logger = LogFactory.getLog(FreightApi.class);
	public FreightApi(Freight freight){
		super(new FreightReq(freight));
	}
	@Override
	protected FreightRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		FreightRes freightRes=new FreightRes();
		freightRes.setSuccess(success);
		if(success){
			FreightAttr freight=new FreightAttr();
			JsonNode resultNode = rootNode.path("result");
			JsonNode freight_path = resultNode.path("freight");
			JsonNode baseFreight_path = resultNode.path("baseFreight");
			JsonNode remoteRegionFreight_path = resultNode.path("remoteRegionFreight");
			JsonNode remoteSku_path = resultNode.path("remoteSku");
			freight.setFreight(freight_path.decimalValue());
			freight.setBaseFreight(baseFreight_path.decimalValue());
			List<String> resultAttr = JsonUtil.toObject(remoteSku_path.toString(), new TypeReference<List<String>>(){});
			freight.setRemoteRegionFreight(remoteRegionFreight_path.decimalValue());
			freight.setRemoteSku(resultAttr);
			freightRes.setFreightAttr(freight);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			freightRes.setResultCode(resultCodeNode.asText());
			freightRes.setResultMessage(resultMessageNode.asText());
		}
		return freightRes;
	}
}
