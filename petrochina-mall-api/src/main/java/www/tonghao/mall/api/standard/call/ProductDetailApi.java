package www.tonghao.mall.api.standard.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.Attributes;
import www.tonghao.mall.api.standard.attwrap.ProductDetailAttr;
import www.tonghao.mall.api.standard.reqwrap.ProductDetailReq;
import www.tonghao.mall.api.standard.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

public class ProductDetailApi extends AbstractBusinesApi<ProductDetailRes>{
	private static Log logger = LogFactory.getLog(ProductDetailApi.class);
	
	public ProductDetailApi(String emallcode,String sku) {
		super(new ProductDetailReq(emallcode,sku));
	}
	@Override
	protected ProductDetailRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductDetailRes res = new ProductDetailRes();
		res.setSuccess(success);
		if (success) {
			ProductDetailAttr resultwrap = new ProductDetailAttr();
			JsonNode resultNode = rootNode.path("result");
			JsonNode skuNode = resultNode.path("sku");
			JsonNode urlNode = resultNode.path("url");
			JsonNode modelNode = resultNode.path("model");
			JsonNode weightNode = resultNode.path("weight");
			JsonNode imgPathNode = resultNode.path("image_path");
			JsonNode stateNode = resultNode.path("state");
			JsonNode brandNameNode = resultNode.path("brand_name");
			JsonNode nameNode = resultNode.path("name");
			JsonNode productAreaNode = resultNode.path("product_area");
			JsonNode upcNode = resultNode.path("upc");
			JsonNode unitNode = resultNode.path("unit");
			JsonNode categoryNode = resultNode.path("category");
			JsonNode serviceNode = resultNode.path("service");
			JsonNode attributesNode = resultNode.path("attributes");
			Attributes attributes=null;
			List<Attributes> attrs=new ArrayList<Attributes>();
			for (JsonNode attrNode : attributesNode) {
				attributes=new Attributes();
				JsonNode attributeID_path = attrNode.path("attributeID");
				JsonNode attributeName_path = attrNode.path("attributeName");
				JsonNode valueID_path = attrNode.path("valueID");
				JsonNode value_path = attrNode.path("value");
				attributes.setAttributeID(attributeID_path.asText());
				attributes.setAttributeName(attributeName_path.asText());
				attributes.setValueID(valueID_path.asText());
				attributes.setValue(value_path.asText());
				attrs.add(attributes);
			}
			JsonNode introductionNode = resultNode.path("introduction");
			JsonNode paramNode = resultNode.path("param");
			JsonNode wareNode = resultNode.path("ware");
			JsonNode saleActivesNode = resultNode.path("sale_actives");
			
			
			resultwrap.setName(nameNode.asText());
			resultwrap.setSku(skuNode.asText());
			resultwrap.setModel(modelNode.asText());
			resultwrap.setWeight(weightNode.asText());
			resultwrap.setState(stateNode.asText());
			resultwrap.setUnit(stringNull(unitNode.asText()));
			resultwrap.setParam(paramNode.asText());
			resultwrap.setUrl(urlNode.asText());
			resultwrap.setBrand_name(stringNull(brandNameNode.asText()));
			resultwrap.setImage_path(imgPathNode.asText());
			resultwrap.setIntroduction(introductionNode.asText());
			resultwrap.setWare(stringNull(wareNode.asText()));
			resultwrap.setCategory(categoryNode.asText());
			resultwrap.setAttributes(attrs);
			resultwrap.setProductArea(stringNull(productAreaNode.asText()));
			resultwrap.setUpc(stringNull(upcNode.asText()));
			resultwrap.setService(stringNull(serviceNode.asText()));
			resultwrap.setSaleActives(saleActivesNode.asInt());
			res.setResult(resultwrap);
		}else{
			JsonNode descNode = rootNode.path("desc");
			res.setDesc(descNode.asText());
		}
		return res;
	}

	public String stringNull(String str){
		if(str==null){
			return null;
		}else{
			if("".equals(str)){
				return null;
			}
			if("null".equals(str)){
				return null;
			}
		}
		return str;
	}
}
