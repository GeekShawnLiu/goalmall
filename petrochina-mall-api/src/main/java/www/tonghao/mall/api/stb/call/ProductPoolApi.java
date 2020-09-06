package www.tonghao.mall.api.stb.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.ProductPoolAttr;
import www.tonghao.mall.api.stb.reqwarp.ProductPoolReq;
import www.tonghao.mall.api.stb.resultwrap.ProductPoolRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class ProductPoolApi extends AbstractBusinesApi<ProductPoolRes> {

	private static Log logger = LogFactory.getLog(ProductPoolApi.class);
	
	
	public ProductPoolApi() {
		super(new ProductPoolReq());
	}
	
	@Override
	protected ProductPoolRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductPoolRes poolRes=new ProductPoolRes();
		poolRes.setSuccess(success);
		if(success) {
			JsonNode resultNode = rootNode.path("result");
			List<ProductPoolAttr> attrs=new ArrayList<>();
			for(JsonNode node:resultNode){
				ProductPoolAttr attr=new ProductPoolAttr();
				String name = node.path("name").asText();
				String page_num = node.path("page_num").asText();
				attr.setId(page_num);
				attr.setName(name);
				attrs.add(attr);
			}
			poolRes.setProductPoolAttrs(attrs);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			poolRes.setDesc(descNode.asText());
		}
		return poolRes;
	}

}
