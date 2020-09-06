package www.tonghao.mall.api.jd.call;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.PriceAttr;
import www.tonghao.mall.api.jd.reqwrap.ProductPriceReq;
import www.tonghao.mall.api.jd.resultwrap.ProductPriceRes;
import www.tonghao.mall.core.AbstractBusinesApi;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 价格接口
 *
 */
public class ProductPricesApi extends AbstractBusinesApi<ProductPriceRes>{

	private static Log logger = LogFactory.getLog(ProductPricesApi.class);
	public ProductPricesApi(String sku){
		super(new ProductPriceReq(sku));
	}
	@Override
	protected ProductPriceRes resolver(String result) {
		logger.info("result = "+result);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		ProductPriceRes re = new ProductPriceRes();
		try {
			rootNode = mapper.readTree(result);
			JsonNode resultNode = rootNode.path("result");
			JsonNode successNode = rootNode.path("success");
			re.setSuccess(successNode.asBoolean());
			List<PriceAttr> getPriceList = null;
			if(successNode.asBoolean()){
				getPriceList = Lists.newArrayList();
				for (JsonNode node : resultNode) {
					PriceAttr p = new PriceAttr();
					p.setSkuId(node.path("skuId").asText());
					p.setPrice(new BigDecimal(node.path("price").asText()));
					p.setJdPrice(new BigDecimal(node.path("jdPrice").asText()));
					p.setMarketPrice(new BigDecimal(node.path("marketPrice").asText()));
					p.setNakedPrice(new BigDecimal(node.path("nakedPrice").asText()));
					p.setTaxPrice(new BigDecimal(node.path("taxPrice").asText()));
					p.setTax(new BigDecimal(node.path("tax").asText()));
					getPriceList.add(p);
				}
			}else{
				JsonNode resultMessageNode = rootNode.path("resultMessage");
				JsonNode resultCodeNode = rootNode.path("resultCode");
				re.setResultCode(resultCodeNode.asText());
				re.setResultMessage(resultMessageNode.asText());
			}
			re.setResult(getPriceList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return re;
	}
	
}
