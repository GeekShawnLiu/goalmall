package www.tonghao.mall.api.standard.call;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.standard.attwrap.Image;
import www.tonghao.mall.api.standard.attwrap.ImageResultAttr;
import www.tonghao.mall.api.standard.reqwrap.ProductImageReq;
import www.tonghao.mall.api.standard.resultwrap.ProductImageRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

/**
 * 图片接口
 *
 */
public class ProductImageApi extends AbstractBusinesApi<ProductImageRes>{
	
	private static Log logger = LogFactory.getLog(ProductImageApi.class);

	public ProductImageApi(String emallcode,String sku) {
		super(new ProductImageReq(emallcode,sku));
	}
	@Override
	protected ProductImageRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductImageRes res = new ProductImageRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			List<ImageResultAttr> resultwrapList = Lists.newArrayList();
			ImageResultAttr resultwrap = null;
			Image image = null;
			for(JsonNode node:resultNode){
				resultwrap = new ImageResultAttr();
				resultwrap.setSku(node.path("sku").asText());
				JsonNode imagesNode = node.path("images");
				List<Image> imageList = Lists.newArrayList();
				for(JsonNode imageNode:imagesNode){
					image = new Image();
					image.setPath(imageNode.path("path").asText());
					image.setOrder(imageNode.path("order").asInt());
					imageList.add(image);
				}
				resultwrap.setImages(imageList);
				resultwrapList.add(resultwrap);
			}
			res.setResult(resultwrapList);
		}else{
			JsonNode desc = rootNode.path("desc");
			res.setDesc(desc.asText());
		}
		return res;
	}
	
}
