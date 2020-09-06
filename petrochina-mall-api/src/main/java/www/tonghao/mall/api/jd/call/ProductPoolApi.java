package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.ProductPoolResultAttr;
import www.tonghao.mall.api.jd.reqwrap.ProductPoolReq;
import www.tonghao.mall.api.jd.resultwrap.ProductPoolRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 获取商品池接口
 *
 */
public class ProductPoolApi extends AbstractBusinesApi<ProductPoolRes> {
	private static Log logger = LogFactory.getLog(ProductPoolApi.class);
	
	public ProductPoolApi() {
		super(new ProductPoolReq());
	}
	
	@Override
	protected ProductPoolRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		
		boolean success = successNode.asBoolean();
		ProductPoolRes poolRes=new ProductPoolRes();
		poolRes.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			ProductPoolResultAttr attr=null;
			List<ProductPoolResultAttr> list=new ArrayList<ProductPoolResultAttr>();
			for (JsonNode jsonNode : resultNode) {
				attr=new ProductPoolResultAttr();
				JsonNode name_path = jsonNode.path("name");
			    JsonNode pageNum_path = jsonNode.path("page_num");
			    attr.setName(name_path.asText());
			    attr.setPage_num(pageNum_path.asText());
			    list.add(attr);
			}
			poolRes.setResult(list);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			poolRes.setResultCode(resultCodeNode.asText());
			poolRes.setResultMessage(resultMessageNode.asText());
		}
		return poolRes;
	}

}
