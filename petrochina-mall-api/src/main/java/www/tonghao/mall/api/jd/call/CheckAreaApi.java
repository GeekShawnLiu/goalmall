package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import www.tonghao.mall.api.jd.attwrap.CheckAreaAttr;
import www.tonghao.mall.api.jd.reqwrap.CheckAreaReq;
import www.tonghao.mall.api.jd.resultwrap.CheckAreaRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

public class CheckAreaApi extends AbstractBusinesApi<CheckAreaRes>{

	public CheckAreaApi(String sku,String province,String city,String county){
		super(new CheckAreaReq(sku, province, city, county));
	}
	
	@Override
	protected CheckAreaRes resolver(String result) {
		System.out.println(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		CheckAreaRes areaRes=new CheckAreaRes();
		boolean success = successNode.asBoolean();
		areaRes.setSuccess(success);
		if(success){
			List<CheckAreaAttr> areaAttrs=new ArrayList<CheckAreaAttr>();
			JsonNode resultPath = rootNode.path("result");
			String resultStr = resultPath.asText();
			JsonNode readTree = JsonUtil.readTree(resultStr);
			CheckAreaAttr areaAttr=null;
			for (JsonNode jsonNode : readTree) {
				areaAttr=new CheckAreaAttr();
				areaAttr.setSkuId(jsonNode.path("skuId").asText());
				areaAttr.setAreaRestrict(jsonNode.path("isAreaRestrict").asBoolean());
				areaAttrs.add(areaAttr);
			}
			areaRes.setAttr(areaAttrs);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			areaRes.setResultCode(resultCodeNode.asText());
			areaRes.setResultMessage(resultMessageNode.asText());
		}
		return areaRes;
	}

}
