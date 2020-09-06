package www.tonghao.mall.api.standard.call;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.reqwrap.ProductSkuReq;
import www.tonghao.mall.api.standard.resultwrap.ProductSkusRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 获取商品编号SKU接口
 * @author developer001
 *
 */
public class ProductSkuApi extends AbstractBusinesApi<ProductSkusRes>{

	private static Log logger = LogFactory.getLog(ProductSkuApi.class);
	
	public ProductSkuApi(String emallcode,String poolId){
		super(new ProductSkuReq(emallcode,poolId));
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
			String resultJson = rootNode.path("result").toString();
			List<String> resultAttr = JsonUtil.toObject(resultJson, new TypeReference<List<String>>(){});
			res.setResult(resultAttr);
		}else{
			JsonNode descNode = rootNode.path("desc");
			res.setDesc(descNode.asText());
		}
		return res;
	}
	
}
