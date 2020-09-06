package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import www.tonghao.mall.api.jd.attwrap.CategorysAttr;
import www.tonghao.mall.api.jd.reqwrap.CategorysReq;
import www.tonghao.mall.api.jd.resultwrap.CategorysRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

public class CategorysApi extends AbstractBusinesApi<CategorysRes>{

	public CategorysApi(String pageNo,String pageSize,String parentId,String catClass){
		super(new CategorysReq(pageNo, pageSize, parentId, catClass));
	}
	public CategorysApi(String pageNo,String pageSize){
		super(new CategorysReq(pageNo, pageSize));
	}
	public CategorysApi(String pageNo,String pageSize,String catClass){
		super(new CategorysReq(pageNo, pageSize,catClass));
	}
	
	@Override
	protected CategorysRes resolver(String result) {
		System.out.println(result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		CategorysRes res=new CategorysRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			JsonNode resultNodeCategorys = resultNode.path("categorys");
			CategorysAttr attr=null;
			List<CategorysAttr> attrs=new ArrayList<>();
			for (JsonNode jsonNode : resultNodeCategorys) {
				attr=new CategorysAttr();
				attr.setCatClass(jsonNode.path("catClass").asText());
				attr.setState(jsonNode.path("state").asText());
				attr.setCatId(jsonNode.path("catId").asText());
				attr.setParentId(jsonNode.path("parentId").asText());
				attr.setName(jsonNode.path("name").asText());
				attrs.add(attr);
			}
			
			res.setResult(attrs);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}
    
} 
