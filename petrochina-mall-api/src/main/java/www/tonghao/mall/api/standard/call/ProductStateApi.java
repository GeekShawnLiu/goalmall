package www.tonghao.mall.api.standard.call;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.StateResultAttr;
import www.tonghao.mall.api.standard.reqwrap.ProductStateReq;
import www.tonghao.mall.api.standard.resultwrap.ProductStateRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

/**
 * 上下架状态接口
 *
 */
public class ProductStateApi extends AbstractBusinesApi<ProductStateRes>{
	
	private static Log logger = LogFactory.getLog(ProductStateApi.class);

	public ProductStateApi(String emallcode,String sku){
		super(new ProductStateReq(emallcode,sku));
	}
	@Override
	protected ProductStateRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductStateRes res = new ProductStateRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			List<StateResultAttr> stateList = Lists.newArrayList();
			StateResultAttr resultAttr = null;
			for(JsonNode node:resultNode){
				resultAttr = new StateResultAttr();
				resultAttr.setSku(node.path("sku").asText());
				resultAttr.setState(node.path("state").asInt());
				stateList.add(resultAttr);
			}
			res.setResult(stateList);
		}else{
			JsonNode desc = rootNode.path("desc");
			res.setDesc(desc.asText());
		}
		return res;
	}
	
}
