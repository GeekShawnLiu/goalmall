package www.tonghao.mall.api.jd.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import www.tonghao.mall.api.jd.attwrap.ProductAttr;
import www.tonghao.mall.api.jd.attwrap.ProductOther;
import www.tonghao.mall.api.jd.reqwrap.ProductReq;
import www.tonghao.mall.api.jd.resultwrap.ProductRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 获取产品详情
 *
 */
public class ProductDetailApi extends AbstractBusinesApi<ProductRes> {
	private static Log logger = LogFactory.getLog(ProductDetailApi.class);
	public ProductDetailApi(String sku){
		super(new ProductReq(sku));
	}
	@Override
	protected ProductRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductRes productRes=new ProductRes();
		productRes.setSuccess(success);
		if(success){
			ProductAttr attr=new ProductAttr();
			ProductOther other=new ProductOther();
			JsonNode resultNode = rootNode.path("result");
			JsonNode skuNode = resultNode.path("sku");
			other.setSku(skuNode.asText());
			JsonNode imagePathNode = resultNode.path("imagePath");
			other.setImagePath(imagePathNode.asText());
			JsonNode weightNode = resultNode.path("weight");
			other.setWeight(weightNode.asText());
			JsonNode stateNode = resultNode.path("state");
			other.setState(stateNode.asInt());
			JsonNode brandNameNode = resultNode.path("brandName");
			other.setBrandName(brandNameNode.asText());
			JsonNode nameNode = resultNode.path("name");
			other.setName(nameNode.asText());
			JsonNode productAreaNode = resultNode.path("productArea");
			other.setProductArea(productAreaNode.asText());
			JsonNode upcNode = resultNode.path("upc");
			other.setUpc(upcNode.asText());
			JsonNode saleUnitNode = resultNode.path("saleUnit");
			other.setSaleUnit(saleUnitNode.asText());
			JsonNode categoryNode = resultNode.path("category");
			other.setCategory(categoryNode.asText());
			JsonNode introductionNode = resultNode.path("introduction");
			other.setIntroduction(introductionNode.asText());
			JsonNode paramNode = resultNode.path("param");
			other.setParam(paramNode.asText());
			JsonNode wareQDNode = resultNode.path("wareQD");
			other.setWareQD(wareQDNode.asText());
			JsonNode eleGift_Node = resultNode.path("eleGift");
			other.setEleGift(eleGift_Node.asText());
			JsonNode shouhouNode = resultNode.path("shouhou");
			other.setShouhou(shouhouNode.asText());
			JsonNode appintroduceNode = resultNode.path("appintroduce");
			other.setAppintroduce(appintroduceNode.asText());
			JsonNode wxintroductionNode = resultNode.path("wxintroduction");
			other.setWxintroduction(wxintroductionNode.asText());
			JsonNode isFactoryShipNode = resultNode.path("isFactoryShip");
			other.setIs_factory_ship(isFactoryShipNode.asInt());
			if(paramNode.asText()!=null&&!"".equals(paramNode.asText())){
				Document doc = Jsoup.parse(paramNode.asText());
				Elements select = doc.select("td[class=tdTitle]");
				if(select!=null){
					for (Element element : select) {
						if("型号".equals(element.text())){
							Element nextElementSibling = element.nextElementSibling();
							other.setModel(nextElementSibling.text());
						}
					}
				}
			}
			attr.setProduct(other);
			productRes.setResult(attr);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			productRes.setResultCode(resultCodeNode.asText());
			productRes.setResultMessage(resultMessageNode.asText());
		}
		return productRes;
	}

}
