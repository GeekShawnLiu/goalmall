package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.StockAttr;
import www.tonghao.mall.api.jd.reqwrap.StockReq;
import www.tonghao.mall.api.jd.resultwrap.StockRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * 批量获取库存
 * 商品列表页使用
 */
public class ProductStockApi extends AbstractBusinesApi<StockRes> {
	private static Log logger = LogFactory.getLog(ProductStockApi.class);
	public ProductStockApi(String sku,String area){
		super(new StockReq(sku, area));
	}
	@Override
	protected StockRes resolver(String result) {
        logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		StockRes res=new StockRes();
		res.setSuccess(success);
		if(success){
			List<StockAttr> attrs=new ArrayList<StockAttr>();
			StockAttr attr=null;
			JsonNode resultNode = rootNode.path("result");
			JsonNode result_Node = JsonUtil.readTree(resultNode.asText());
			for (JsonNode jsonNode : result_Node) {
				attr=new StockAttr();
				JsonNode state_path = jsonNode.path("state");
				JsonNode area_path = jsonNode.path("area");
				JsonNode desc_path = jsonNode.path("desc");
				JsonNode sku_path = jsonNode.path("sku");
				attr.setState(state_path.asInt());
				attr.setArea(area_path.asText());
				attr.setDesc(desc_path.asText());
				attr.setSku(sku_path.asText());
				attrs.add(attr);
			}
			res.setResult(attrs);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultMessage(resultMessageNode.asText());
			res.setResultCode(resultCodeNode.asText());
		}
		return res;
	}

}
