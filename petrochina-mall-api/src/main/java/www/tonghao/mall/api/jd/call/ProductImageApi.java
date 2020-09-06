package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.Image;
import www.tonghao.mall.api.jd.attwrap.ImageResultAttr;
import www.tonghao.mall.api.jd.reqwrap.ProductImageReq;
import www.tonghao.mall.api.jd.resultwrap.ProductImageRes;
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
    private String sku;
	public ProductImageApi(String sku) {
		super(new ProductImageReq(sku));
		this.sku=sku;
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
			List<Image> images=null;
			if(!StringUtils.isEmpty(sku)){
				String[] sku_s = sku.split(",");
				for (String skus : sku_s) {
					resultwrap=new ImageResultAttr();
					resultwrap.setSku(skus);
					JsonNode path = resultNode.path(skus);
					images=new ArrayList<Image>();
					for (JsonNode jsonNode : path) {
						image=new Image();
						JsonNode id_path = jsonNode.path("id");
						JsonNode skuId_path = jsonNode.path("skuId");
						JsonNode path_path = jsonNode.path("path");
						JsonNode created_path = jsonNode.path("created");
						JsonNode modified_path = jsonNode.path("modified");
						JsonNode yn_path = jsonNode.path("yn");
						JsonNode isPrimary_path = jsonNode.path("isPrimary");
						JsonNode orderSort_path = jsonNode.path("orderSort");
						JsonNode position_path = jsonNode.path("position");
						JsonNode type_path = jsonNode.path("type");
						JsonNode features_path = jsonNode.path("features");
						image.setId(id_path.asText());
						image.setSkuId(skuId_path.asText());
						image.setPath(path_path.asText());
						image.setCreated(created_path.asText());
						image.setModified(modified_path.asText());
						image.setYn(yn_path.asInt());
						image.setIsPrimary(isPrimary_path.asInt());
						image.setOrderSort(orderSort_path.asInt());
						image.setPosition(position_path.asInt());
						image.setType(type_path.asText());
						image.setFeatures(features_path.asText());
						images.add(image);
					}
					resultwrap.setImages(images);
					resultwrapList.add(resultwrap);
				}
				res.setResult(resultwrapList);
			}
			
			res.setResult(resultwrapList);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
	
}
