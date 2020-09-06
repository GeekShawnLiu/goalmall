package www.tonghao.mall.api.jd.call;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import www.tonghao.mall.api.jd.attwrap.ChildrenOrderRepVO;
import www.tonghao.mall.api.jd.attwrap.OrderSelectAttr;
import www.tonghao.mall.api.jd.attwrap.OrderSkuVO;
import www.tonghao.mall.api.jd.attwrap.ParentOrderRepVO;
import www.tonghao.mall.api.jd.reqwrap.OrderSelectReq;
import www.tonghao.mall.api.jd.resultwrap.OrderSelectRes;
import www.tonghao.mall.api.utils.JsonUtil;
import www.tonghao.mall.core.AbstractBusinesApi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 订单查询
 *
 */
public class OrderSelectApi extends AbstractBusinesApi<OrderSelectRes> {
	private static Log log = LogFactory.getLog(OrderSelectApi.class);
	public OrderSelectApi(String orderId){
		super(new OrderSelectReq(orderId));
	}
	
	@Override
	protected OrderSelectRes resolver(String result) {
		log.info("OrderSelectApi result："+result);
		JsonNode rootNode = JsonUtil.readTree(result);
		JsonNode successNode = rootNode.path("success");
		boolean success = successNode.asBoolean();
		OrderSelectRes res=new OrderSelectRes();
		res.setSuccess(success);
		if(success){
			JsonNode resultNode = rootNode.path("result");
			JsonNode pOrder_Node = resultNode.path("pOrder");
			String pOrder = pOrder_Node.asText();
			OrderSelectAttr attr=new OrderSelectAttr();
			ParentOrderRepVO pVos=new ParentOrderRepVO();
			List<ChildrenOrderRepVO> childrenOrderRepVOs=new ArrayList<>();
			if(StringUtils.isNotEmpty(pOrder) && pOrder.equals("0")){
				JsonNode c_jdOrderId_path = resultNode.path("jdOrderId");
				JsonNode c_orderPrice_path = resultNode.path("orderPrice");
				JsonNode c_freight_path = resultNode.path("freight");
				JsonNode c_orderTaxPrice_path = resultNode.path("orderTaxPrice");
				JsonNode c_orderNakedPrice_path = resultNode.path("orderNakedPrice");
				JsonNode c_orderState_path = resultNode.path("orderState");
				JsonNode c_state_path = resultNode.path("state");
				/*JsonNode c_state_path = resultNode.path("state");
				JsonNode c_type_path = resultNode.path("type"); 
				JsonNode c_orderState_path = resultNode.path("orderState");
				JsonNode c_jdOrderState_path = resultNode.path("jdOrderState");
				JsonNode c_submitState_path = resultNode.path("submitState");*/
				pVos.setJdOrderId(c_jdOrderId_path.asText());
				pVos.setOrderPrice(c_orderPrice_path.decimalValue());
				pVos.setOrderTaxPrice(c_orderTaxPrice_path.decimalValue());
				pVos.setOrderNakedPrice(c_orderNakedPrice_path.decimalValue());
				pVos.setFreight(c_freight_path.decimalValue());
				pVos.setOrderState(c_orderState_path.asInt());
				pVos.setState(c_state_path.asInt());
				JsonNode c_sku_path = resultNode.path("sku");
				OrderSkuVO c_sk=null;
				List<OrderSkuVO> list=new ArrayList<OrderSkuVO>();
				for (JsonNode c_jsonNode : c_sku_path) {
					c_sk=new OrderSkuVO();
					c_sk.setCategory(c_jsonNode.path("category").asText());
					c_sk.setNakedPrice(c_jsonNode.path("nakedPrice").decimalValue());
					c_sk.setName(c_jsonNode.path("name").asText());
					c_sk.setNum(c_jsonNode.path("num").asInt());
					c_sk.setOid(c_jsonNode.path("oid").asInt());
					c_sk.setPrice(c_jsonNode.path("price").decimalValue());
					c_sk.setSkuId(c_jsonNode.path("skuId").asText());
					c_sk.setTax(c_jsonNode.path("tax").asInt());
					c_sk.setTaxPrice(c_jsonNode.path("taxPrice").decimalValue());
					c_sk.setType(c_jsonNode.path("type").asInt());
					list.add(c_sk);
				}
				pVos.setSku(list);
			}else{
				JsonNode c_jdOrderId_path = pOrder_Node.path("jdOrderId");
				JsonNode c_orderPrice_path = pOrder_Node.path("orderPrice");
				JsonNode c_freight_path = pOrder_Node.path("freight");
				JsonNode c_orderTaxPrice_path = pOrder_Node.path("orderTaxPrice");
				JsonNode c_orderNakedPrice_path = pOrder_Node.path("orderNakedPrice");
				JsonNode c_orderState_path = pOrder_Node.path("orderState");
				JsonNode c_state_path = pOrder_Node.path("state");
				pVos.setJdOrderId(c_jdOrderId_path.asText());
				pVos.setOrderPrice(c_orderPrice_path.decimalValue());
				pVos.setOrderTaxPrice(c_orderTaxPrice_path.decimalValue());
				pVos.setOrderNakedPrice(c_orderNakedPrice_path.decimalValue());
				pVos.setFreight(c_freight_path.decimalValue());
				pVos.setOrderState(c_orderState_path.asInt());
				pVos.setState(c_state_path.asInt());
				JsonNode c_sku_path = pOrder_Node.path("sku");
				OrderSkuVO c_sk=null;
				List<OrderSkuVO> list=new ArrayList<OrderSkuVO>();
				for (JsonNode c_jsonNode : c_sku_path) {
					c_sk=new OrderSkuVO();
					c_sk.setCategory(c_jsonNode.path("category").asText());
					c_sk.setNakedPrice(c_jsonNode.path("nakedPrice").decimalValue());
					c_sk.setName(c_jsonNode.path("name").asText());
					c_sk.setNum(c_jsonNode.path("num").asInt());
					c_sk.setOid(c_jsonNode.path("oid").asInt());
					c_sk.setPrice(c_jsonNode.path("price").decimalValue());
					c_sk.setSkuId(c_jsonNode.path("skuId").asText());
					c_sk.setTax(c_jsonNode.path("tax").asInt());
					c_sk.setTaxPrice(c_jsonNode.path("taxPrice").decimalValue());
					c_sk.setType(c_jsonNode.path("type").asInt());
					list.add(c_sk);
				}
				pVos.setSku(list);
				
				JsonNode cOrder_Node = resultNode.path("cOrder");
				ChildrenOrderRepVO childrenOrderRepVO=null;
				for (JsonNode jsonNode : cOrder_Node) {
					childrenOrderRepVO=new ChildrenOrderRepVO();
					JsonNode Children_jdOrderId_path = jsonNode.path("jdOrderId");
					JsonNode Children_orderPrice_path = jsonNode.path("orderPrice");
					JsonNode Children_freight_path = jsonNode.path("freight");
					JsonNode Children_orderTaxPrice_path = jsonNode.path("orderTaxPrice");
					JsonNode Children_orderNakedPrice_path = jsonNode.path("orderNakedPrice");
					JsonNode Children_orderState_path = jsonNode.path("orderState");
					JsonNode Children_state_path = jsonNode.path("state");
					childrenOrderRepVO.setJdOrderId(Children_jdOrderId_path.asText());
					childrenOrderRepVO.setOrderPrice(Children_orderPrice_path.decimalValue());
					childrenOrderRepVO.setFreight(Children_freight_path.decimalValue());
					childrenOrderRepVO.setOrderTaxPrice(Children_orderTaxPrice_path.decimalValue());
					childrenOrderRepVO.setOrderNakedPrice(Children_orderNakedPrice_path.decimalValue());
					childrenOrderRepVO.setOrderState(Children_orderState_path.asInt());
					childrenOrderRepVO.setState(Children_state_path.asInt());
					JsonNode ChildrenOrder_Node = jsonNode.path("sku");
					OrderSkuVO children_sk=null;
					List<OrderSkuVO> children_list=new ArrayList<OrderSkuVO>();
					for (JsonNode c_jsonNode : ChildrenOrder_Node) {
						children_sk=new OrderSkuVO();
						children_sk.setCategory(c_jsonNode.path("category").asText());
						children_sk.setNakedPrice(c_jsonNode.path("nakedPrice").decimalValue());
						children_sk.setName(c_jsonNode.path("name").asText());
						children_sk.setNum(c_jsonNode.path("num").asInt());
						children_sk.setOid(c_jsonNode.path("oid").asInt());
						children_sk.setPrice(c_jsonNode.path("price").decimalValue());
						children_sk.setSkuId(c_jsonNode.path("skuId").asText());
						children_sk.setTax(c_jsonNode.path("tax").asInt());
						children_sk.setTaxPrice(c_jsonNode.path("taxPrice").decimalValue());
						children_sk.setType(c_jsonNode.path("type").asInt());
						children_list.add(children_sk);
					}
					childrenOrderRepVO.setSku(children_list);
					childrenOrderRepVOs.add(childrenOrderRepVO);
				}
			}
			attr.setpOrder(pVos);
			attr.setcOrder(childrenOrderRepVOs);
			
			/*if(StringUtils.isNotEmpty(pOrder) && pOrder.equals("0")){
				ChildrenOrderRepVO childrenOrderRepVO=new ChildrenOrderRepVO();
				List<ChildrenOrderRepVO> c_list=new ArrayList<ChildrenOrderRepVO>();
				JsonNode c_jdOrderId_path = resultNode.path("jdOrderId");
				JsonNode c_orderPrice_path = resultNode.path("orderPrice");
				JsonNode c_freight_path = resultNode.path("freight");
				JsonNode c_orderTaxPrice_path = resultNode.path("orderTaxPrice");
				JsonNode c_orderNakedPrice_path = resultNode.path("orderNakedPrice");
				JsonNode c_state_path = resultNode.path("state");
				JsonNode c_type_path = resultNode.path("type"); 
				JsonNode c_orderState_path = resultNode.path("orderState");
				JsonNode c_jdOrderState_path = resultNode.path("jdOrderState");
				JsonNode c_submitState_path = resultNode.path("submitState");
				childrenOrderRepVO.setJdOrderId(c_jdOrderId_path.asText());
				childrenOrderRepVO.setOrderPrice(c_orderPrice_path.decimalValue());
				childrenOrderRepVO.setFreight(c_freight_path.decimalValue());
				childrenOrderRepVO.setOrderTaxPrice(c_orderTaxPrice_path.decimalValue());
				childrenOrderRepVO.setOrderNakedPrice(c_orderNakedPrice_path.decimalValue());
				childrenOrderRepVO.setState(c_state_path.asInt());
				childrenOrderRepVO.setType(c_type_path.asInt());
				childrenOrderRepVO.setpOrder(pOrder_Node.asText());
				childrenOrderRepVO.setOrderState(c_orderState_path.asInt());
				childrenOrderRepVO.setJdOrderState(c_jdOrderState_path.asInt());
				childrenOrderRepVO.setSubmitState(c_submitState_path.asInt());
				JsonNode c_sku_path = resultNode.path("sku");
				OrderSkuVO c_sk=null;
				List<OrderSkuVO> list=new ArrayList<OrderSkuVO>();
				for (JsonNode c_jsonNode : c_sku_path) {
					c_sk=new OrderSkuVO();
					c_sk.setCategory(c_jsonNode.path("category").asText());
					c_sk.setNakedPrice(c_jsonNode.path("nakedPrice").decimalValue());
					c_sk.setName(c_jsonNode.path("name").asText());
					c_sk.setNum(c_jsonNode.path("num").asInt());
					c_sk.setOid(c_jsonNode.path("oid").asInt());
					c_sk.setPrice(c_jsonNode.path("price").decimalValue());
					c_sk.setSkuId(c_jsonNode.path("skuId").asText());
					c_sk.setTax(c_jsonNode.path("tax").asInt());
					c_sk.setTaxPrice(c_jsonNode.path("taxPrice").decimalValue());
					c_sk.setType(c_jsonNode.path("type").asInt());
					list.add(c_sk);
				}
				childrenOrderRepVO.setSku(list);
				c_list.add(childrenOrderRepVO);
				attr.setcOrder(c_list);
			}else{
				List<ParentOrderRepVO> pVos=new ArrayList<ParentOrderRepVO>();
				ParentOrderRepVO orderRepVO=new ParentOrderRepVO();
				JsonNode p_jdOrderId_path = pOrder_Node.path("jdOrderId");
				JsonNode p_orderPrice_path = pOrder_Node.path("orderPrice");
				JsonNode p_freight_path = pOrder_Node.path("freight");
				JsonNode p_orderTaxPrice_path = pOrder_Node.path("orderTaxPrice");
				JsonNode p_orderNakedPrice_path = pOrder_Node.path("orderNakedPrice");
				orderRepVO.setJdOrderId(p_jdOrderId_path.asText());
				orderRepVO.setOrderPrice(p_orderPrice_path.decimalValue());
				orderRepVO.setFreight(p_freight_path.decimalValue());
				orderRepVO.setOrderTaxPrice(p_orderTaxPrice_path.decimalValue());
				orderRepVO.setOrderNakedPrice(p_orderNakedPrice_path.decimalValue());
				JsonNode p_sku_path = pOrder_Node.path("sku");
				OrderSkuVO p_sku=null;
				List<OrderSkuVO> p_list=new ArrayList<OrderSkuVO>();
				for (JsonNode jsonNode : p_sku_path) {
					p_sku=new OrderSkuVO();
					p_sku.setCategory(jsonNode.path("category").asText());
					p_sku.setNakedPrice(jsonNode.path("nakedPrice").decimalValue());
					p_sku.setName(jsonNode.path("name").asText());
					p_sku.setNum(jsonNode.path("num").asInt());
					p_sku.setOid(jsonNode.path("oid").asInt());
					p_sku.setPrice(jsonNode.path("price").decimalValue());
					p_sku.setSkuId(jsonNode.path("skuId").asText());
					p_sku.setTax(jsonNode.path("tax").asInt());
					p_sku.setTaxPrice(jsonNode.path("taxPrice").decimalValue());
					p_sku.setType(jsonNode.path("type").asInt());
					p_list.add(p_sku);
				}
				orderRepVO.setSku(p_list);
				pVos.add(orderRepVO);
				
				List<ChildrenOrderRepVO> c_list=new ArrayList<ChildrenOrderRepVO>();
				ChildrenOrderRepVO childrenOrderRepVO=null;
				OrderSkuVO c_sk=null;
				List<OrderSkuVO> c_list_vo=null;
				JsonNode cOrder_Node = resultNode.path("cOrder");
				for (JsonNode jsonNode : cOrder_Node) {
					c_list_vo=new ArrayList<OrderSkuVO>();
					childrenOrderRepVO=new ChildrenOrderRepVO();
					JsonNode c_jdOrderId_path = jsonNode.path("jdOrderId");
					JsonNode c_orderPrice_path = jsonNode.path("orderPrice");
					JsonNode c_freight_path = jsonNode.path("freight");
					JsonNode c_orderTaxPrice_path = jsonNode.path("orderTaxPrice");
					JsonNode c_orderNakedPrice_path = jsonNode.path("orderNakedPrice");
					JsonNode c_state_path = jsonNode.path("state");
					JsonNode c_type_path = jsonNode.path("type"); 
					JsonNode c_pOrder_path = jsonNode.path("pOrder"); 
					JsonNode c_orderState_path = jsonNode.path("orderState");
					JsonNode c_jdOrderState_path = jsonNode.path("jdOrderState");
					JsonNode c_submitState_path = jsonNode.path("submitState");
					childrenOrderRepVO.setJdOrderId(c_jdOrderId_path.asText());
					childrenOrderRepVO.setOrderPrice(c_orderPrice_path.decimalValue());
					childrenOrderRepVO.setFreight(c_freight_path.decimalValue());
					childrenOrderRepVO.setOrderTaxPrice(c_orderTaxPrice_path.decimalValue());
					childrenOrderRepVO.setOrderNakedPrice(c_orderNakedPrice_path.decimalValue());
					childrenOrderRepVO.setState(c_state_path.asInt());
					childrenOrderRepVO.setType(c_type_path.asInt());
					childrenOrderRepVO.setpOrder(c_pOrder_path.asText());
					childrenOrderRepVO.setOrderState(c_orderState_path.asInt());
					childrenOrderRepVO.setJdOrderState(c_jdOrderState_path.asInt());
					childrenOrderRepVO.setSubmitState(c_submitState_path.asInt());
					JsonNode c_sku_path = jsonNode.path("sku");
					for (JsonNode c_jsonNode : c_sku_path) {
						c_sk=new OrderSkuVO();
						c_sk.setCategory(c_jsonNode.path("category").asText());
						c_sk.setNakedPrice(c_jsonNode.path("nakedPrice").decimalValue());
						c_sk.setName(c_jsonNode.path("name").asText());
						c_sk.setNum(c_jsonNode.path("num").asInt());
						c_sk.setOid(c_jsonNode.path("oid").asInt());
						c_sk.setPrice(c_jsonNode.path("price").decimalValue());
						c_sk.setSkuId(c_jsonNode.path("skuId").asText());
						c_sk.setTax(c_jsonNode.path("tax").asInt());
						c_sk.setTaxPrice(c_jsonNode.path("taxPrice").decimalValue());
						c_sk.setType(c_jsonNode.path("type").asInt());
						c_list_vo.add(c_sk);
					}
					childrenOrderRepVO.setSku(c_list_vo);
					c_list.add(childrenOrderRepVO);
				}
				attr.setpOrder(pVos);
				attr.setcOrder(c_list);
			}*/
			res.setResult(attr);
		}else{
			JsonNode resultMessageNode = rootNode.path("resultMessage");
			JsonNode resultCodeNode = rootNode.path("resultCode");
			res.setResultCode(resultCodeNode.asText());
			res.setResultMessage(resultMessageNode.asText());
		}
		return res;
	}

	
	public static void main(String[] args) {
		String result = "{\"success\":true,\"resultMessage\":\"\",\"resultCode\":\"0000\",\"result\":{\"pOrder\":{\"jdOrderId\":97651348646,\"freight\":0.00,\"orderPrice\":27925.57,\"orderNakedPrice\":24713.94,\"sku\":[{\"category\":870,\"num\":2,\"price\":1799.00,\"tax\":13.00,\"oid\":0,\"name\":\"奥克斯（AUX）正1匹 冷暖 定速 空调挂机(KFR-25GW/NFO17+3)\",\"taxPrice\":206.97,\"skuId\":3917710,\"nakedPrice\":1592.03,\"type\":0},{\"category\":7371,\"num\":15,\"price\":4.27,\"tax\":13.00,\"oid\":0,\"name\":\"得力(deli)20张5号牛皮纸信封 邮局标准信封220*110mm3423-20\",\"taxPrice\":0.49,\"skuId\":759713,\"nakedPrice\":3.78,\"type\":0},{\"category\":4840,\"num\":10,\"price\":38.70,\"tax\":13.00,\"oid\":0,\"name\":\"齐心(Comix) 10个装 30mm加厚纯浆牛皮纸档案盒 A4资料盒 AP-30 办公文具\",\"taxPrice\":4.45,\"skuId\":786007,\"nakedPrice\":34.25,\"type\":0},{\"category\":9902,\"num\":1,\"price\":27.31,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）消防应急灯安全出口指示灯新国标疏散通道标志照明灯壁挂/吊挂式安装单面正向\",\"taxPrice\":3.14,\"skuId\":100002019706,\"nakedPrice\":24.17,\"type\":0},{\"category\":7372,\"num\":25,\"price\":11.76,\"tax\":13.00,\"oid\":0,\"name\":\"三木(SUNWOOD) 6382 100g通用橡皮筋/橡皮圈/橡胶圈牛皮筋 办公文具\",\"taxPrice\":1.35,\"skuId\":2796258,\"nakedPrice\":10.41,\"type\":0},{\"category\":4000,\"num\":10,\"price\":18.91,\"tax\":13.00,\"oid\":0,\"name\":\"班哲尼 强力无痕挂钩吸盘粘钩厨房免钉门后墙壁挂钩子卫生间浴室适用 出差旅行必备 10个装\",\"taxPrice\":2.17,\"skuId\":4824553,\"nakedPrice\":16.74,\"type\":0},{\"category\":694,\"num\":50,\"price\":29.90,\"tax\":13.00,\"oid\":0,\"name\":\"闪迪（SanDisk） 酷晶（CZ71） 8G金属迷你创意U盘 银灰色\",\"taxPrice\":3.44,\"skuId\":853541,\"nakedPrice\":26.46,\"type\":0},{\"category\":2603,\"num\":14,\"price\":15.50,\"tax\":13.00,\"oid\":0,\"name\":\"晨光(M&G)0.7mm蓝色子弹头按动圆珠笔中油笔原子笔 24支/盒BP0048\",\"taxPrice\":1.78,\"skuId\":241219,\"nakedPrice\":13.72,\"type\":0},{\"category\":4840,\"num\":30,\"price\":20.70,\"tax\":13.00,\"oid\":0,\"name\":\"得力(deli)三联镂空桌面文件框 三栏带标签稳固文件栏/文件筐/资料框 蓝色\",\"taxPrice\":2.38,\"skuId\":681740,\"nakedPrice\":18.32,\"type\":0},{\"category\":9902,\"num\":3,\"price\":55.50,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）嵌入式消防应急灯安全出口指示灯新国标疏散通道标志照明灯镶墙暗装双向【带底盒】\",\"taxPrice\":6.39,\"skuId\":100001515309,\"nakedPrice\":49.11,\"type\":0},{\"category\":9883,\"num\":2,\"price\":999.00,\"tax\":13.00,\"oid\":0,\"name\":\"中伟办公家具办公桌总裁桌贴实木皮经理桌1.4米\",\"taxPrice\":114.93,\"skuId\":4155276,\"nakedPrice\":884.07,\"type\":0},{\"category\":690,\"num\":50,\"price\":49.00,\"tax\":13.00,\"oid\":0,\"name\":\"罗技（Logitech）M110 鼠标 有线鼠标 办公鼠标 静音鼠标 对称鼠标 黑色 自营\",\"taxPrice\":5.64,\"skuId\":8963957,\"nakedPrice\":43.36,\"type\":0},{\"category\":4837,\"num\":100,\"price\":34.90,\"tax\":13.00,\"oid\":0,\"name\":\"齐心（COMIX）6卷装55mm*60y(54.9米) 高透明胶带 宽封箱胶带 办公文具JT5506-6\",\"taxPrice\":4.01,\"skuId\":880031,\"nakedPrice\":30.89,\"type\":0},{\"category\":730,\"num\":7,\"price\":125.00,\"tax\":13.00,\"oid\":0,\"name\":\"格之格CRG-912易加粉硒鼓NT-C0912CT适用佳能Canon LBP-3018 3108 3050 3100 3150 3010打印机粉盒\",\"taxPrice\":14.38,\"skuId\":325494,\"nakedPrice\":110.62,\"type\":0},{\"category\":4837,\"num\":10,\"price\":17.90,\"tax\":13.00,\"oid\":0,\"name\":\"得力(deli)高粘性棉纸双面胶带12mm*10y(9.1m/卷) 24卷/袋装 30401\",\"taxPrice\":2.06,\"skuId\":391817,\"nakedPrice\":15.84,\"type\":0},{\"category\":11974,\"num\":1,\"price\":120.60,\"tax\":13.00,\"oid\":0,\"name\":\"双鑫达 折叠床 单人床 办公室午睡午休床 护理陪护床 行军床简易床 B-011\",\"taxPrice\":13.87,\"skuId\":1599688,\"nakedPrice\":106.73,\"type\":0},{\"category\":12530,\"num\":2,\"price\":499.00,\"tax\":13.00,\"oid\":0,\"name\":\"中伟文件柜办公柜钢制铁皮柜资料柜档案柜储物柜中二斗文件柜\",\"taxPrice\":57.41,\"skuId\":3761786,\"nakedPrice\":441.59,\"type\":0},{\"category\":717,\"num\":10,\"price\":559.00,\"tax\":13.00,\"oid\":0,\"name\":\"得力（deli）11872 三防热敏标签打印纸 60mm*40mm不干胶条码打印纸 800张*48卷装\",\"taxPrice\":64.31,\"skuId\":3598649,\"nakedPrice\":494.69,\"type\":0},{\"category\":9902,\"num\":4,\"price\":55.50,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）嵌入式消防应急灯安全出口指示灯新国标疏散通道标志照明灯镶墙暗装向左【带底盒】\",\"taxPrice\":6.39,\"skuId\":100002019636,\"nakedPrice\":49.11,\"type\":0},{\"category\":17211,\"num\":2,\"price\":380.00,\"tax\":13.00,\"oid\":0,\"name\":\"中伟电脑椅办公椅员工椅会议椅家用电脑椅\",\"taxPrice\":43.72,\"skuId\":3392291,\"nakedPrice\":336.28,\"type\":0},{\"category\":9870,\"num\":1,\"price\":1188.00,\"tax\":13.00,\"oid\":0,\"name\":\"杜沃 沙发 北欧客厅家具 布艺沙发 简约小户型沙发组合 可拆洗三人沙发 懒人沙发 B1 1.82米 深灰\",\"taxPrice\":136.67,\"skuId\":7420082,\"nakedPrice\":1051.33,\"type\":0},{\"category\":727,\"num\":3,\"price\":24.00,\"tax\":13.00,\"oid\":0,\"name\":\"得力（deli）彩色白板磁钉黑板吸铁石/磁铁φ30mm 48粒/筒白板配件8725\",\"taxPrice\":2.76,\"skuId\":1814851,\"nakedPrice\":21.24,\"type\":0},{\"category\":13783,\"num\":10,\"price\":113.00,\"tax\":13.00,\"oid\":0,\"name\":\"振兴 收纳整理箱65L 塑料收纳箱65L大号韩式带盖带滑轮储物整理箱 CH8873\",\"taxPrice\":13.00,\"skuId\":4411927,\"nakedPrice\":100.00,\"type\":0},{\"category\":731,\"num\":3,\"price\":102.90,\"tax\":13.00,\"oid\":0,\"name\":\"佳能（Canon） CLI-851XL BK 高容黑色墨盒 （适用MX928、MG6400、iP7280、iX6880\",\"taxPrice\":11.84,\"skuId\":854783,\"nakedPrice\":91.06,\"type\":0},{\"category\":9902,\"num\":4,\"price\":55.50,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）嵌入式消防应急灯安全出口指示灯新国标疏散通道标志照明灯镶墙暗装向右【带底盒】\",\"taxPrice\":6.39,\"skuId\":100001515297,\"nakedPrice\":49.11,\"type\":0},{\"category\":736,\"num\":4,\"price\":45.00,\"tax\":13.00,\"oid\":0,\"name\":\"天章(TANGO)铜板标签打印纸40mm*30mm卷式不干胶条码打印纸 吊牌价格标签纸 1000张*6卷\",\"taxPrice\":5.18,\"skuId\":1805884,\"nakedPrice\":39.82,\"type\":0},{\"category\":4840,\"num\":96,\"price\":11.00,\"tax\":13.00,\"oid\":0,\"name\":\"得力(deli) 5106 ABA系列A4/60页资料册 蓝色 单只装\",\"taxPrice\":1.26,\"skuId\":681843,\"nakedPrice\":9.74,\"type\":0},{\"category\":9902,\"num\":1,\"price\":27.31,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）消防应急灯安全出口指示灯新国标疏散通道标志照明灯壁挂/吊挂式安装单面向右\",\"taxPrice\":3.14,\"skuId\":100001515317,\"nakedPrice\":24.17,\"type\":0},{\"category\":1259,\"num\":2,\"price\":0.00,\"tax\":13.00,\"oid\":3917710,\"name\":\"奥克斯（AUX）KFR-25W/NF+3 室外机（对应的室内机型号KFR-25G/NFO17+3）\",\"taxPrice\":0.00,\"skuId\":3917808,\"nakedPrice\":0.00,\"type\":1}],\"orderTaxPrice\":3211.63},\"orderState\":1,\"cOrder\":[{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97801130213,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":875.00,\"orderNakedPrice\":774.34,\"type\":2,\"sku\":[{\"category\":730,\"num\":7,\"price\":125.00,\"tax\":13.00,\"oid\":0,\"name\":\"格之格CRG-912易加粉硒鼓NT-C0912CT适用佳能Canon LBP-3018 3108 3050 3100 3150 3010打印机粉盒\",\"taxPrice\":14.38,\"skuId\":325494,\"nakedPrice\":110.62,\"type\":0}],\"orderTaxPrice\":100.66},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97798556233,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":308.70,\"orderNakedPrice\":273.18,\"type\":2,\"sku\":[{\"category\":731,\"num\":3,\"price\":102.90,\"tax\":13.00,\"oid\":0,\"name\":\"佳能（Canon） CLI-851XL BK 高容黑色墨盒 （适用MX928、MG6400、iP7280、iX6880\",\"taxPrice\":11.84,\"skuId\":854783,\"nakedPrice\":91.06,\"type\":0}],\"orderTaxPrice\":35.52},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97796417186,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":1414.05,\"orderNakedPrice\":1251.99,\"type\":2,\"sku\":[{\"category\":7371,\"num\":15,\"price\":4.27,\"tax\":13.00,\"oid\":0,\"name\":\"得力(deli)20张5号牛皮纸信封 邮局标准信封220*110mm3423-20\",\"taxPrice\":0.49,\"skuId\":759713,\"nakedPrice\":3.78,\"type\":0},{\"category\":7372,\"num\":25,\"price\":11.76,\"tax\":13.00,\"oid\":0,\"name\":\"三木(SUNWOOD) 6382 100g通用橡皮筋/橡皮圈/橡胶圈牛皮筋 办公文具\",\"taxPrice\":1.35,\"skuId\":2796258,\"nakedPrice\":10.41,\"type\":0},{\"category\":4840,\"num\":96,\"price\":11.00,\"tax\":13.00,\"oid\":0,\"name\":\"得力(deli) 5106 ABA系列A4/60页资料册 蓝色 单只装\",\"taxPrice\":1.26,\"skuId\":681843,\"nakedPrice\":9.74,\"type\":0}],\"orderTaxPrice\":162.06},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97801890381,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":309.70,\"orderNakedPrice\":274.13,\"type\":2,\"sku\":[{\"category\":4000,\"num\":10,\"price\":18.91,\"tax\":13.00,\"oid\":0,\"name\":\"班哲尼 强力无痕挂钩吸盘粘钩厨房免钉门后墙壁挂钩子卫生间浴室适用 出差旅行必备 10个装\",\"taxPrice\":2.17,\"skuId\":4824553,\"nakedPrice\":16.74,\"type\":0},{\"category\":11974,\"num\":1,\"price\":120.60,\"tax\":13.00,\"oid\":0,\"name\":\"双鑫达 折叠床 单人床 办公室午睡午休床 护理陪护床 行军床简易床 B-011\",\"taxPrice\":13.87,\"skuId\":1599688,\"nakedPrice\":106.73,\"type\":0}],\"orderTaxPrice\":35.57},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":5,\"jdOrderId\":97798151051,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":3756.00,\"orderNakedPrice\":3323.88,\"type\":2,\"sku\":[{\"category\":9883,\"num\":2,\"price\":999.00,\"tax\":13.00,\"oid\":0,\"name\":\"中伟办公家具办公桌总裁桌贴实木皮经理桌1.4米\",\"taxPrice\":114.93,\"skuId\":4155276,\"nakedPrice\":884.07,\"type\":0},{\"category\":12530,\"num\":2,\"price\":499.00,\"tax\":13.00,\"oid\":0,\"name\":\"中伟文件柜办公柜钢制铁皮柜资料柜档案柜储物柜中二斗文件柜\",\"taxPrice\":57.41,\"skuId\":3761786,\"nakedPrice\":441.59,\"type\":0},{\"category\":17211,\"num\":2,\"price\":380.00,\"tax\":13.00,\"oid\":0,\"name\":\"中伟电脑椅办公椅员工椅会议椅家用电脑椅\",\"taxPrice\":43.72,\"skuId\":3392291,\"nakedPrice\":336.28,\"type\":0}],\"orderTaxPrice\":432.12},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97801890413,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":1130.00,\"orderNakedPrice\":1000.00,\"type\":2,\"sku\":[{\"category\":13783,\"num\":10,\"price\":113.00,\"tax\":13.00,\"oid\":0,\"name\":\"振兴 收纳整理箱65L 塑料收纳箱65L大号韩式带盖带滑轮储物整理箱 CH8873\",\"taxPrice\":13.00,\"skuId\":4411927,\"nakedPrice\":100.00,\"type\":0}],\"orderTaxPrice\":130.00},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":5,\"jdOrderId\":97800162798,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":665.12,\"orderNakedPrice\":588.55,\"type\":2,\"sku\":[{\"category\":9902,\"num\":1,\"price\":27.31,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）消防应急灯安全出口指示灯新国标疏散通道标志照明灯壁挂/吊挂式安装单面正向\",\"taxPrice\":3.14,\"skuId\":100002019706,\"nakedPrice\":24.17,\"type\":0},{\"category\":9902,\"num\":3,\"price\":55.50,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）嵌入式消防应急灯安全出口指示灯新国标疏散通道标志照明灯镶墙暗装双向【带底盒】\",\"taxPrice\":6.39,\"skuId\":100001515309,\"nakedPrice\":49.11,\"type\":0},{\"category\":9902,\"num\":4,\"price\":55.50,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）嵌入式消防应急灯安全出口指示灯新国标疏散通道标志照明灯镶墙暗装向左【带底盒】\",\"taxPrice\":6.39,\"skuId\":100002019636,\"nakedPrice\":49.11,\"type\":0},{\"category\":9902,\"num\":4,\"price\":55.50,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）嵌入式消防应急灯安全出口指示灯新国标疏散通道标志照明灯镶墙暗装向右【带底盒】\",\"taxPrice\":6.39,\"skuId\":100001515297,\"nakedPrice\":49.11,\"type\":0},{\"category\":9902,\"num\":1,\"price\":27.31,\"tax\":13.00,\"oid\":0,\"name\":\"明德斯（mingds）消防应急灯安全出口指示灯新国标疏散通道标志照明灯壁挂/吊挂式安装单面向右\",\"taxPrice\":3.14,\"skuId\":100001515317,\"nakedPrice\":24.17,\"type\":0}],\"orderTaxPrice\":76.57},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97800075047,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":3945.00,\"orderNakedPrice\":3491.00,\"type\":2,\"sku\":[{\"category\":694,\"num\":50,\"price\":29.90,\"tax\":13.00,\"oid\":0,\"name\":\"闪迪（SanDisk） 酷晶（CZ71） 8G金属迷你创意U盘 银灰色\",\"taxPrice\":3.44,\"skuId\":853541,\"nakedPrice\":26.46,\"type\":0},{\"category\":690,\"num\":50,\"price\":49.00,\"tax\":13.00,\"oid\":0,\"name\":\"罗技（Logitech）M110 鼠标 有线鼠标 办公鼠标 静音鼠标 对称鼠标 黑色 自营\",\"taxPrice\":5.64,\"skuId\":8963957,\"nakedPrice\":43.36,\"type\":0}],\"orderTaxPrice\":454.00},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97797130529,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":5842.00,\"orderNakedPrice\":5169.90,\"type\":2,\"sku\":[{\"category\":717,\"num\":10,\"price\":559.00,\"tax\":13.00,\"oid\":0,\"name\":\"得力（deli）11872 三防热敏标签打印纸 60mm*40mm不干胶条码打印纸 800张*48卷装\",\"taxPrice\":64.31,\"skuId\":3598649,\"nakedPrice\":494.69,\"type\":0},{\"category\":727,\"num\":3,\"price\":24.00,\"tax\":13.00,\"oid\":0,\"name\":\"得力（deli）彩色白板磁钉黑板吸铁石/磁铁φ30mm 48粒/筒白板配件8725\",\"taxPrice\":2.76,\"skuId\":1814851,\"nakedPrice\":21.24,\"type\":0},{\"category\":736,\"num\":4,\"price\":45.00,\"tax\":13.00,\"oid\":0,\"name\":\"天章(TANGO)铜板标签打印纸40mm*30mm卷式不干胶条码打印纸 吊牌价格标签纸 1000张*6卷\",\"taxPrice\":5.18,\"skuId\":1805884,\"nakedPrice\":39.82,\"type\":0}],\"orderTaxPrice\":672.10},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97800162766,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":4894.00,\"orderNakedPrice\":4331.58,\"type\":2,\"sku\":[{\"category\":4840,\"num\":10,\"price\":38.70,\"tax\":13.00,\"oid\":0,\"name\":\"齐心(Comix) 10个装 30mm加厚纯浆牛皮纸档案盒 A4资料盒 AP-30 办公文具\",\"taxPrice\":4.45,\"skuId\":786007,\"nakedPrice\":34.25,\"type\":0},{\"category\":2603,\"num\":14,\"price\":15.50,\"tax\":13.00,\"oid\":0,\"name\":\"晨光(M&G)0.7mm蓝色子弹头按动圆珠笔中油笔原子笔 24支/盒BP0048\",\"taxPrice\":1.78,\"skuId\":241219,\"nakedPrice\":13.72,\"type\":0},{\"category\":4840,\"num\":30,\"price\":20.70,\"tax\":13.00,\"oid\":0,\"name\":\"得力(deli)三联镂空桌面文件框 三栏带标签稳固文件栏/文件筐/资料框 蓝色\",\"taxPrice\":2.38,\"skuId\":681740,\"nakedPrice\":18.32,\"type\":0},{\"category\":4837,\"num\":100,\"price\":34.90,\"tax\":13.00,\"oid\":0,\"name\":\"齐心（COMIX）6卷装55mm*60y(54.9米) 高透明胶带 宽封箱胶带 办公文具JT5506-6\",\"taxPrice\":4.01,\"skuId\":880031,\"nakedPrice\":30.89,\"type\":0},{\"category\":4837,\"num\":10,\"price\":17.90,\"tax\":13.00,\"oid\":0,\"name\":\"得力(deli)高粘性棉纸双面胶带12mm*10y(9.1m/卷) 24卷/袋装 30401\",\"taxPrice\":2.06,\"skuId\":391817,\"nakedPrice\":15.84,\"type\":0}],\"orderTaxPrice\":562.42},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97798784458,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":1188.00,\"orderNakedPrice\":1051.33,\"type\":2,\"sku\":[{\"category\":9870,\"num\":1,\"price\":1188.00,\"tax\":13.00,\"oid\":0,\"name\":\"杜沃 沙发 北欧客厅家具 布艺沙发 简约小户型沙发组合 可拆洗三人沙发 懒人沙发 B1 1.82米 深灰\",\"taxPrice\":136.67,\"skuId\":7420082,\"nakedPrice\":1051.33,\"type\":0}],\"orderTaxPrice\":136.67},{\"pOrder\":97651348646,\"orderState\":1,\"orderType\":1,\"jdOrderId\":97800386223,\"state\":1,\"freight\":0.00,\"submitState\":1,\"orderPrice\":3598.00,\"orderNakedPrice\":3184.06,\"type\":2,\"sku\":[{\"category\":870,\"num\":2,\"price\":1799.00,\"tax\":13.00,\"oid\":0,\"name\":\"奥克斯（AUX）正1匹 冷暖 定速 空调挂机(KFR-25GW/NFO17+3)\",\"taxPrice\":206.97,\"skuId\":3917710,\"nakedPrice\":1592.03,\"type\":0},{\"category\":1259,\"num\":2,\"price\":0.00,\"tax\":13.00,\"oid\":3917710,\"name\":\"奥克斯（AUX）KFR-25W/NF+3 室外机（对应的室内机型号KFR-25G/NFO17+3）\",\"taxPrice\":0.00,\"skuId\":3917808,\"nakedPrice\":0.00,\"type\":1}],\"orderTaxPrice\":413.94}],\"orderType\":1,\"submitState\":1,\"type\":1}}";
		OrderSelectApi orderSelectApi = new OrderSelectApi(null);
		OrderSelectRes resolver = orderSelectApi.resolver(result);
		List<ChildrenOrderRepVO> getcOrder = resolver.getResult().getcOrder();
		ParentOrderRepVO getpOrder = resolver.getResult().getpOrder();
		/*for (ChildrenOrderRepVO childrenOrderRepVO : getcOrder) {
			System.out.println(childrenOrderRepVO.getState());
		}*/
		System.out.println(getpOrder.getState());
		System.out.println(resolver.getResult().getcOrder());
		System.out.println(1);
		
		
	}
}
