package www.tonghao.mall.api.stb.call;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

import www.tonghao.mall.api.stb.reqwarp.ProductSkusReq;
import www.tonghao.mall.api.stb.resultwrap.ProductSkusRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class ProductSkusApi extends AbstractBusinesApi<ProductSkusRes> {

	private static Log logger = LogFactory.getLog(ProductSkusApi.class);
	
	public ProductSkusApi(String pageNum) {
		super(new ProductSkusReq(pageNum));
	}
	
	@Override
	protected ProductSkusRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductSkusRes productSkusRes=new ProductSkusRes();
		productSkusRes.setSuccess(success);
		if(success) {
			JsonNode resultNode = rootNode.path("result");
			String[] results = resultNode.asText().split(",");
			System.out.println(results.length);
			List<String> resultList = Lists.newArrayList(results);
			productSkusRes.setSkus(resultList);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			productSkusRes.setDesc(descNode.asText());
		}
		return productSkusRes;
	}

}
