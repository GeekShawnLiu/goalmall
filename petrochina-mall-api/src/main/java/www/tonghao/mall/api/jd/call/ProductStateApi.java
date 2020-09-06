package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.StateResultAttr;
import www.tonghao.mall.api.jd.reqwrap.ProductStateReq;
import www.tonghao.mall.api.jd.resultwrap.ProductStateRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 *上下架状态接口
 */
public class ProductStateApi  extends AbstractBusinesApi<ProductStateRes> {
	private static Log logger = LogFactory.getLog(ProductStateApi.class);
	
	public ProductStateApi(String sku){
		super(new ProductStateReq(sku));
	}
	
	@Override
	protected ProductStateRes resolver(String result) {
		logger.info("result = "+result);
		
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductStateRes productStateRes=new ProductStateRes();
		productStateRes.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			List<StateResultAttr> attrs=new ArrayList<StateResultAttr>();
			StateResultAttr attr=null;
			for (JsonNode jsonNode : resultNode) {
				attr=new StateResultAttr();
				attr.setSku(jsonNode.path("sku").asText());
				attr.setState(jsonNode.path("state").asInt());
				attrs.add(attr);
			}
			productStateRes.setResult(attrs);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			productStateRes.setResultMessage(resultMessageNode.asText());
			productStateRes.setResultCode(resultCodeNode.asText());
		}
		return productStateRes;
	}

}
