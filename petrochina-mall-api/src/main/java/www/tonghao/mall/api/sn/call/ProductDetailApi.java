package www.tonghao.mall.api.sn.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.sn.attwrap.ProductDetailAttr;
import www.tonghao.mall.api.sn.reqwrap.ProductDetailReq;
import www.tonghao.mall.api.sn.resultwrap.ProductDetailRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractSnApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.ProdDetailGetRequest;
import com.suning.api.entity.govbus.ProdDetailGetResponse;
import com.suning.api.entity.govbus.ProdDetailGetResponse.Param;
import com.suning.api.entity.govbus.ProdDetailGetResponse.ProdParams;
import com.suning.api.exception.SuningApiException;

/**
 * 获取商品详情
 */
public class ProductDetailApi extends AbstractSnApi<ProductDetailRes>{

	private static Log logger = LogFactory.getLog(ProductDetailApi.class);
	
	private String skuId;
	
	public ProductDetailApi(String skuId){
		super(new ProductDetailReq());
		this.skuId = skuId;
	}
	
	@Override
	protected ProductDetailRes resolver(DefaultSuningClient client) {
		String result = result(client);
		logger.info("ProductDetailApi result=="+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("sn_responseContent");
		ProductDetailRes res = new ProductDetailRes();
		JsonNode errorNode = successNode.findPath("sn_error");
		if (!errorNode.isMissingNode()) {
			res.setSuccess(false);
			JsonNode errorCodeNode = errorNode.path("error_code");
			JsonNode errorMsgNode = errorNode.path("error_msg");
			res.setError_code(errorCodeNode.asText());
			res.setError_msg(errorMsgNode.asText());
			logger.error("苏宁获取商品详情错误,错误码["+errorCodeNode.asText()+"],错误信息"+errorMsgNode.asText());
		}else{
			res.setSuccess(true);
			ProductDetailAttr attr=new ProductDetailAttr();
			JsonNode bodyNode = successNode.path("sn_body");
			JsonNode prodDetailNode = bodyNode.path("getProdDetail");
			JsonNode skuIdNode = prodDetailNode.path("skuId");
			JsonNode weightNode = prodDetailNode.path("weight");
			JsonNode imageNode = prodDetailNode.path("image");
			JsonNode brandNode = prodDetailNode.path("brand");
			JsonNode modelNode = prodDetailNode.path("model");
			JsonNode nameNode = prodDetailNode.path("name");
			JsonNode productAreaNode = prodDetailNode.path("productArea");
			JsonNode saleUnitNode = prodDetailNode.path("saleUnit");
			JsonNode categoryNode = prodDetailNode.path("category");
			JsonNode packlistingNode = prodDetailNode.path("packlisting");
			JsonNode introductionNode = prodDetailNode.path("introduction");
			JsonNode stateNode = prodDetailNode.path("state");
			
			JsonNode prodParamsNode = prodDetailNode.path("prodParams");
			JsonNode descNode = prodParamsNode.path("desc");
			JsonNode paramNode = prodParamsNode.path("param");
			ProdParams prodParam = new ProdParams();
			prodParam.setDesc(descNode.asText());
			List<Param> params = new ArrayList<Param>();
			Param param = null;
			for(JsonNode node : paramNode){
				param = new Param();
				JsonNode coreFlagNode = node.path("coreFlag");	//核心参数
				JsonNode snparameterCodeNode = node.path("snparameterCode");	//参数代码
				JsonNode snparameterdescNode = node.path("snparameterdesc");	//参数描述
				JsonNode snparametersCodeNode = node.path("snparametersCode");	//参数组代码
				JsonNode snparametersDescNode = node.path("snparametersDesc");	//参数组名称
				JsonNode snparameterSequenceNode = node.path("snparameterSequence");	//参数排序码
				JsonNode snparameterValNode = node.path("snparameterVal");		//参数描述值
				JsonNode snsequenceNode = node.path("snsequence");				//参数组排序
				param.setCoreFlag(coreFlagNode.asText());
				param.setSnparameterCode(snparameterCodeNode.asText());
				param.setSnparameterdesc(snparameterdescNode.asText());
				param.setSnparametersCode(snparametersCodeNode.asText());
				param.setSnparametersDesc(snparametersDescNode.asText());
				param.setSnparameterSequence(snparameterSequenceNode.asText());
				param.setSnparameterVal(snparameterValNode.asText());
				param.setSnsequence(snsequenceNode.asText());
				params.add(param);
			}
			prodParam.setParam(params);
			attr.setSkuId(skuIdNode.asText());
			attr.setWeight(weightNode.asText());
			attr.setImage(imageNode.asText());
			attr.setBrand(brandNode.asText());
			attr.setModel(modelNode.asText());
			attr.setName(nameNode.asText());
			attr.setProductArea(productAreaNode.asText());
			attr.setSaleUnit(saleUnitNode.asText());
			attr.setCategory(categoryNode.asText());
			attr.setIntroduction(introductionNode.asText());
			attr.setState(stateNode.asInt());
			attr.setPacklisting(packlistingNode.asText());
			attr.setProdParams(prodParam);
			res.setResult(attr);
		}
		return res;
	}


	@Override
	protected String result(DefaultSuningClient client) {
		ProdDetailGetRequest request = new ProdDetailGetRequest();
		request.setCheckParam(true);
		request.setSkuId(skuId);
		ProdDetailGetResponse response = null;
		try {
			response =client.excute(request);
		} catch (SuningApiException e) {
			e.printStackTrace();
			logger.error("获取商品详情失败！");
		}
		return response.getBody();
	}
	
}
