package www.tonghao.mall.api.standard.call;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.PriceAttr;
import www.tonghao.mall.api.standard.reqwrap.ProductPriceReq;
import www.tonghao.mall.api.standard.resultwrap.ProductPriceRes;
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
	public ProductPricesApi(String emallcode,String sku){
		super(new ProductPriceReq(emallcode, sku));
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
					p.setSku(node.path("sku").asText());
					p.setPrice(new BigDecimal(node.path("price").asText()));
					p.setMall_price(new BigDecimal(node.path("mall_price").asText()));
					getPriceList.add(p);
				}
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
