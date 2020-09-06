package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.reqwrap.ProductSkusReq;
import www.tonghao.mall.api.jd.resultwrap.ProductSkusRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;
import www.tonghao.mall.core.ResultWrap;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 获取sku
 *
 */
public class ProductSkusApi extends AbstractBusinesApi<ProductSkusRes> {
	private static Log logger = LogFactory.getLog(ProductPoolApi.class);
	
	public ProductSkusApi(String pageNum,int pageNo){
		super(new ProductSkusReq(pageNum, pageNo));
	}
	
	@Override
	protected ProductSkusRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductSkusRes res = new ProductSkusRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			JsonNode pageCount_node = resultNode.path("pageCount");
			res.setPageCount(pageCount_node.asInt());
			JsonNode skuIds_node = resultNode.path("skuIds");
			List<String> list=new ArrayList<String>();
			if(skuIds_node.isArray()){
				for (JsonNode node : skuIds_node) {
					list.add(node.asText());
				}
				res.setSkuIds(list);
			}
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultMessage(resultMessageNode.asText());
			res.setResultCode(resultCodeNode.asText());
		}
		return res;
	}

}
