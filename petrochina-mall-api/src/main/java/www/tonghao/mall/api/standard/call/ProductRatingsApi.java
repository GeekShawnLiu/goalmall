package www.tonghao.mall.api.standard.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.RatingsAttr;
import www.tonghao.mall.api.standard.reqwrap.ProductRatingsReq;
import www.tonghao.mall.api.standard.resultwrap.ProductRatingsRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * 商品好评度接口
 * @author developer001
 *
 */
public class ProductRatingsApi extends AbstractBusinesApi<ProductRatingsRes>{

	private static Log logger = LogFactory.getLog(ProductRatingsApi.class);
	
	public ProductRatingsApi(String emallcode,String sku){
		super(new ProductRatingsReq(emallcode, sku));
	}
	@Override
	protected ProductRatingsRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductRatingsRes productRatingsRes=new ProductRatingsRes();
		productRatingsRes.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			List<RatingsAttr> attrs=new ArrayList<RatingsAttr>();
			RatingsAttr attr=null;
			for (JsonNode jsonNode : resultNode) {
				attr=new RatingsAttr();
				JsonNode average_path = jsonNode.path("average");
				JsonNode medium_path = jsonNode.path("medium");
				JsonNode good_path = jsonNode.path("good");
				JsonNode sku_path = jsonNode.path("sku");
				JsonNode bad_path = jsonNode.path("bad");
				attr.setAverage(average_path.asInt());
				attr.setMedium(medium_path.asDouble());
				attr.setBad(bad_path.asDouble());
				attr.setGood(good_path.asDouble());
				attr.setSku(sku_path.asText());
				attrs.add(attr);
			}
			productRatingsRes.setResult(attrs);
		}else{
			JsonNode descNode = rootNode.path("desc");
			productRatingsRes.setDesc(descNode.asText());
		}
		
		
		return null;
	}
	
}
