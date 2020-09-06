package www.tonghao.mall.api.standard.call;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.ProductStockAttr;
import www.tonghao.mall.api.standard.reqwrap.ProductStocksReq;
import www.tonghao.mall.api.standard.resultwrap.ProductStocksRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

/**
 * 产品库存接口
 *
 */
public class ProductStocksApi extends AbstractBusinesApi<ProductStocksRes> {
	private static Log logger = LogFactory.getLog(ProductStocksApi.class);
    public ProductStocksApi(String emallcode, String area, String sku) {
        super(new ProductStocksReq(emallcode, area, sku));
    }

    @Override
    protected ProductStocksRes resolver(String result) {
    	logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
        JsonNode success = rootNode.path("success");
        boolean asBoolean = success.asBoolean();
        ProductStocksRes re = new ProductStocksRes();
        List<ProductStockAttr> getStockList = Lists.newArrayList();
        re.setSuccess(asBoolean);
        if(asBoolean){
        	JsonNode resultNode = rootNode.path("result");
        	for (JsonNode node : resultNode) {
            	ProductStockAttr ps = new ProductStockAttr();
                ps.setArea(node.path("area").asText());
                ps.setDesc(node.path("desc").asText());
                ps.setSku(node.path("sku").asText());
                ps.setNum(node.path("num").asInt());
                getStockList.add(ps);
            }
        	re.setResult(getStockList);
        }else{
        	JsonNode desc = rootNode.path("desc");
        	re.setDesc(desc.asText());
        }
        return re;
    }

}
