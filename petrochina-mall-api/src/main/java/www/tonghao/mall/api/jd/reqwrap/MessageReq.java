package www.tonghao.mall.api.jd.reqwrap;

import java.util.Map;

import www.tonghao.mall.api.Constant;
import www.tonghao.mall.api.jd.call.AccessTokenApi;
import www.tonghao.mall.core.HttpMethod;
import www.tonghao.mall.core.ReqWrap;
import www.tonghao.mall.core.XmlConfig;

import com.google.common.collect.Maps;

/**
 * 1 代表订单拆分变更 
 *   2 代表商品价格变更
 * 4 商品上下架变更消息 
 * 5 代表该订单已妥投 
 * 6 代表添加、删除商品池内商品 
 *   10 代表订单取消（不区分取消原因）
 * 12 代表配送单生成 
 * 13 换新订单生成 
 *   16 商品介绍及规格参数变更消息 
 * 18 订单等待确认收货 
 * 23 订单配送退货 
 * 25 新订单消息 
 *   26 预定订单
 * 28 售后服务单状态变更 
 *
 */
public class MessageReq implements ReqWrap {

	private int type;
	public MessageReq(int type){
		this.type=type;
	}
	
	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.post;
	}

	@Override
	public String getApiUrl() {
		return XmlConfig.getAttributeValue("/api_config/"+Constant.JD_CODE+"/getMessage", "value");
	}

	@Override
	public Map<String, String> getParams() {
		Map<String,String> param = Maps.newHashMap();
		param.put("token", AccessTokenApi.getCacheToken(Constant.JD_CODE));
		param.put("type", type+"");
		return param;
	}

}
