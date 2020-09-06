package www.tonghao.mall.api.stb.call;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.ProductDetailAttr;
import www.tonghao.mall.api.stb.reqwarp.ProductDetailReq;
import www.tonghao.mall.api.stb.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class ProductDetailApi extends AbstractBusinesApi<ProductDetailRes> {
	private static Log logger = LogFactory.getLog(ProductDetailApi.class);
	public ProductDetailApi(String sku) {
		super(new ProductDetailReq(sku));
	}
	
	@Override
	protected ProductDetailRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductDetailRes detailRes=new ProductDetailRes();
		detailRes.setSuccess(success);
		if(success) {
			ProductDetailAttr attr=new ProductDetailAttr();
			JsonNode resultNode = rootNode.path("result");
			String sku = resultNode.path("sku").asText();
			String name = resultNode.path("name").asText();
			String model = resultNode.path("goodsModel").asText();
			String weight = resultNode.path("weight").asText();
			String state = resultNode.path("state").asText();//1上架 0下架
			String unit = resultNode.path("saleUnit").asText();
			String param = resultNode.path("param").asText();
			/*String url = resultNode.path("imagePath").asText();*/
			String brand_name = resultNode.path("brandName").asText();
			String image_path = resultNode.path("imagePath").asText();
			String introduction = resultNode.path("introduction").asText();
			String category = resultNode.path("category").asText(); //category 是id
			String wareQD = resultNode.path("wareQD").asText();
			String productArea = resultNode.path("productArea").asText();
			String upc = resultNode.path("upc").asText();
			
			attr.setSku(sku);
			attr.setName(name);
			attr.setModel(isNull(model)?null:model);
			attr.setWeight(weight);
			attr.setState(state);
			attr.setUnit(isNull(unit)?null:unit);
			attr.setParam(isNull(param)?null:param);
			attr.setBrand_name(isNull(brand_name)?null:brand_name);
			attr.setImage_path(isNull(image_path)?null:image_path);
			attr.setIntroduction(isNull(introduction)?null:introduction);
			attr.setCategory(category);
			attr.setWare(isNull(wareQD)?null:wareQD);
			attr.setProductArea(isNull(productArea)?null:productArea);
			attr.setUpc(upc);
			detailRes.setAttr(attr);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			detailRes.setDesc(descNode.asText());
		}
		
		return detailRes;
	}
	private boolean isNull(String str){
		if(str==null||"null".equals(str)){
			return true;
		}
		return false;
	}
}
