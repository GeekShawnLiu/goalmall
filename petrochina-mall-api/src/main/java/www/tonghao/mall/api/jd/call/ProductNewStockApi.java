package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import www.tonghao.mall.api.jd.attwrap.NewStockAttr;
import www.tonghao.mall.api.jd.entity.Sku;
import www.tonghao.mall.api.jd.reqwrap.NewStockReq;
import www.tonghao.mall.api.jd.resultwrap.NewStockRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 批量获取库存
 * 下单时调用
 *
 */
public class ProductNewStockApi extends AbstractBusinesApi<NewStockRes> {
	private static Log logger = LogFactory.getLog(FreightApi.class);
	
	public ProductNewStockApi(String area,List<Sku> list){
		super(new NewStockReq(list,area));
	}
	
	@Override
	protected NewStockRes resolver(String result) {
		logger.info("result = "+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		NewStockRes newStockRes=new NewStockRes();
		newStockRes.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			
			JsonNode result_Node = JsonUtil.readTree(resultNode.asText());
			/*JsonNode object=null;
			String string = resultNode.toString();
			if(string.lastIndexOf("\"")>0){
				string=string.substring(1, string.length()-1);
				string=string.replaceAll("\\\\", "");
				object= JsonUtil.toObject(string, JsonNode.class);
			}else{
				object=resultNode;
			}*/
			List<NewStockAttr> list=new ArrayList<NewStockAttr>();
			NewStockAttr attr=null;
			for (JsonNode jsonNode : result_Node) {
				attr=new NewStockAttr();
				JsonNode skuId_path = jsonNode.path("skuId");
				JsonNode areaId_path = jsonNode.path("areaId");
				JsonNode stockStateId_path = jsonNode.path("stockStateId");
				JsonNode stockStateDesc_path = jsonNode.path("stockStateDesc");
				JsonNode remainNum_path = jsonNode.path("remainNum");
				attr.setSkuId(skuId_path.asText());
				attr.setAreaId(areaId_path.asText());
				attr.setStockStateId(stockStateId_path.asInt());
				attr.setStockStateDesc(stockStateDesc_path.asText());
				attr.setRemainNum(remainNum_path.asInt());
				list.add(attr);
			}
			newStockRes.setResult(list);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			newStockRes.setResultCode(resultCodeNode.asText());
			newStockRes.setResultMessage(resultMessageNode.asText());
		}
		return newStockRes;
	}

}
