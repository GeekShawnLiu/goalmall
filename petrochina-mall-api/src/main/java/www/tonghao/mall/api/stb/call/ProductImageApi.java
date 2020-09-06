package www.tonghao.mall.api.stb.call;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;

import www.tonghao.mall.api.stb.attwrap.Image;
import www.tonghao.mall.api.stb.attwrap.ProductImageAttr;
import www.tonghao.mall.api.stb.reqwarp.ProductImageReq;
import www.tonghao.mall.api.stb.resultwrap.ProductImageRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

public class ProductImageApi extends AbstractBusinesApi<ProductImageRes> {
	private static Log logger = LogFactory.getLog(ProductImageApi.class);
	public ProductImageApi(String skus) {
		super(new ProductImageReq(skus));
	}
	@Override
	protected ProductImageRes resolver(String result) {
		logger.info(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		ProductImageRes imageRes=new ProductImageRes();
		imageRes.setSuccess(success);
		if(success) {
			JsonNode resultNode = rootNode.path("result");
			List<ProductImageAttr> attrs=new ArrayList<>();
			System.out.println(resultNode.toString());
			if(resultNode.toString().equals("[")) {
				for (JsonNode node : resultNode) {
					Iterator<String> fieldNames = node.fieldNames();
					while (fieldNames.hasNext()) {
						ProductImageAttr attr=new ProductImageAttr();
						String sku = fieldNames.next();
						attr.setSku(sku);
						List<Image> images=new ArrayList<>();
						JsonNode path = node.path(sku);
						for (JsonNode jsonNode : path) {
							Image image=new Image();
							int isPrimary = jsonNode.path("isPrimary").asInt();
							int orderSort = jsonNode.path("orderSort").asInt();
							String pathImage = jsonNode.path("path").asText();
							image.setIsPrimary(isPrimary);
							image.setOrder(orderSort);
							image.setPath(pathImage);
							images.add(image);
						}
						attr.setImages(images);
						attrs.add(attr);
					}
					
				}
			}else {
				Iterator<String> fieldNames = resultNode.fieldNames();
				while (fieldNames.hasNext()) {
					ProductImageAttr attr=new ProductImageAttr();
					String sku = fieldNames.next();
					attr.setSku(sku);
					JsonNode path = resultNode.path(sku);
					List<Image> images=new ArrayList<>();
					for (JsonNode jsonNode : path) {
						Image image=new Image();
						int isPrimary = jsonNode.path("isPrimary").asInt();
						int orderSort = jsonNode.path("orderSort").asInt();
						String pathImage = jsonNode.path("path").asText();
						image.setIsPrimary(isPrimary);
						image.setOrder(orderSort);
						image.setPath(pathImage);
						images.add(image);
					}
					attr.setImages(images);
					attrs.add(attr);
				}
			}
			imageRes.setImagesList(attrs);
		}else {
			JsonNode descNode = rootNode.path("resultMessage");
			imageRes.setDesc(descNode.asText());
		}
		return imageRes;
	}

}
