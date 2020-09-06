package www.tonghao.mall.support;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import www.tonghao.common.constant.InvoiceConstant;
import www.tonghao.common.utils.BigDecimalUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.CartItems;
import www.tonghao.mall.entity.Invoices;
import www.tonghao.mall.entity.OrderForm;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.entity.ReceiverAddresses;
import www.tonghao.mall.mapper.ReceiverAddressesMapper;
import www.tonghao.mall.service.MallProductService;
import www.tonghao.mall.service.PayWayService;
import www.tonghao.service.common.entity.OrderInvoice;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.entity.Parameter;
import www.tonghao.service.common.entity.ProductParameter;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.service.OrganizationService;
import www.tonghao.service.common.service.SeqService;

import com.google.common.collect.Lists;


@Component
public class OrderHelper {

	@Autowired
	private SeqService seqService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private PayWayService payWayService;
	
	@Autowired
	private MallProductService mallProductService;
	
	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Autowired
	private ReceiverAddressesMapper receiverAddressesMapper;
	
	public Map<String, Object> splitOrdersBySupplier(List<CartItems> cartItems, OrderForm orderForm, Users user){
		//拆分购物车项
		Map<Long, List<CartItems>> supplierMap = new HashMap<Long, List<CartItems>>();
		cartItems.forEach(ci -> {
			if(ci.getSupplierId() != null){
				if(supplierMap.get(ci.getSupplierId()) == null){
					List<CartItems> suppliersCartItemList = new ArrayList<CartItems>();
					suppliersCartItemList.add(ci);
					supplierMap.put(ci.getSupplierId(), suppliersCartItemList);
				}else{
					List<CartItems> suppliersCartItemList = supplierMap.get(ci.getSupplierId());
					suppliersCartItemList.add(ci);
					supplierMap.put(ci.getSupplierId(), suppliersCartItemList);
				}
			}
		});
		Organization org = organizationService.selectByKey(user.getDepId());
		List<Orders> orderList = Lists.newCopyOnWriteArrayList();
		Iterator<Entry<Long, List<CartItems>>> iterator = supplierMap.entrySet().iterator();
		while (iterator.hasNext()) {  
			Entry<Long, List<CartItems>> next = iterator.next();
			Long supplierId = next.getKey();
			List<CartItems> suppliersCartItemList = next.getValue();
			Suppliers supplier = suppliersMapper.selectByPrimaryKey(supplierId);
			Orders supplierOrder = new Orders();
			/*supplierOrder.setSn(seqService.getSeqSn(DateUtilEx.timeToDate(new Date(),"yyyyMMdd")));*/
			supplierOrder.setSn(seqService.getSeqSn(DateUtilEx.timeToDate(new Date(),"yyyyMMddHHmmss")));
			supplierOrder.setTitle(org.getName()+"采购订单");
			supplierOrder.setUserId(user.getId());
			supplierOrder.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			supplierOrder.setIsDelete(0);
			supplierOrder.setPurchaserName(org.getName());
			supplierOrder.setPurchaserRegionCode(org.getRegionCode());
			supplierOrder.setRemark(orderForm.getRemark());
			supplierOrder.setPayId(orderForm.getPaywayId());
			supplierOrder.setPayway("账期支付");
			supplierOrder.setAcceptStatus(0);
			supplierOrder.setContractStatus(0);
			
			//收货人
			ReceiverAddresses receiverAddresses = receiverAddressesMapper.selectByPrimaryKey(orderForm.getReceiverAddressId());
			supplierOrder.setAddressId(receiverAddresses.getId());
			supplierOrder.setAreaId(receiverAddresses.getAreaId());
			supplierOrder.setConsigneeName(receiverAddresses.getConsigneeName());
			supplierOrder.setMobile(receiverAddresses.getMobile());
			supplierOrder.setPhone(receiverAddresses.getPhone());
			supplierOrder.setAddr(receiverAddresses.getAreaName()+" "+receiverAddresses.getAddr());
			supplierOrder.setZipCode(receiverAddresses.getZipCode());
			supplierOrder.setEmail(receiverAddresses.getEmail());
			
			//供应商
			supplierOrder.setSupplierId(supplier.getId());
			supplierOrder.setSupplierName(supplier.getName());
			supplierOrder.setSupplier(supplier);
			supplierOrder.setOrderType(1);
			
			//发票
			supplierOrder.setInvoiceTitle(InvoiceConstant.COMPANY);
			supplierOrder.setInvoiceTaxCode(InvoiceConstant.ID_CODE);
			supplierOrder.setInvoiceBank(InvoiceConstant.BANK);
			supplierOrder.setInvoiceBankAccount(InvoiceConstant.BANK_ACCOUNT);
			supplierOrder.setInvoicePhone(InvoiceConstant.PHONE);
			supplierOrder.setInvoiceType(getInvoices(InvoiceConstant.INVOICE_TYPE));
			supplierOrder.setItems(processOrder(supplierOrder, suppliersCartItemList));
			//采购人发票信息
			Long invoiceId = orderForm.getInvoiceId();
			OrderInvoice orderInvoice = new OrderInvoice();
			if(orderForm.getInvoice() != null){
				Invoices invoices = orderForm.getInvoice();
				orderInvoice.setUserId(user.getId());
				orderInvoice.setInvoiceType(invoices.getInvoiceType());
				orderInvoice.setEnterprise(invoices.getEnterprise());
				orderInvoice.setIdCode(invoices.getIdCode());
				orderInvoice.setCompany(invoices.getCompany());
				orderInvoice.setPhone(invoices.getPhone());
				orderInvoice.setBank(invoices.getBank());
				orderInvoice.setBankAccount(invoices.getBankAccount());
				orderInvoice.setAddress(invoices.getAddress());
				orderInvoice.setInvoiceId(invoiceId);
				orderInvoice.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			}
			supplierOrder.setOrderInvoice(orderInvoice);
			supplierOrder.setOrderSource(orderForm.getOrderSource());
			orderList.add(supplierOrder);
			//订单金额判断  不满足起送价不能下单
			if(supplier.getFreight() != null && BigDecimalUtil.compareTo(supplierOrder.getItemsPrice(), supplier.getFreight()) < 0){
				if("jd".equals(supplier.getCode())){
					return ResultUtil.error("京东商品99包邮，劳保积分不能抵扣运费，请凑够99元下单！");
				}else{
					return ResultUtil.error(supplier.getName() + "订单金额不满" + supplier.getFreight() + "元，不允许下单");
				}
			}
        }  
		Map<String, Object> resultMap = ResultUtil.success("");
		resultMap.put("orderList", orderList);
		return resultMap;
	}
	
	/**
	 * 加工订单
	 * @param supplier
	 * @param supplierOrder
	 * @param cartItemList
	 * @return
	 */
	private List<OrderItems> processOrder(Orders supplierOrder, List<CartItems> cartItemList){
		List<OrderItems> orderItems = Lists.newCopyOnWriteArrayList();
		BigDecimal orderPriceSubtotal = BigDecimal.ZERO;
		BigDecimal orderProtocolPriceSubtotal = BigDecimal.ZERO;
		OrderItems item = null;
		for (int i = 0; i < cartItemList.size(); i++) {
			CartItems cartItem = cartItemList.get(i);
			Products mallProduct = cartItem.getProduct();
			item = new OrderItems();
			item.setProductId(mallProduct.getId());
			item.setName(mallProduct.getName());
			item.setCatalogName(mallProduct.getCatalogName());
			item.setSn(mallProduct.getSku());
			item.setThumbnail(mallProduct.getCoverUrl());
			item.setPrice(mallProduct.getPrice());
			item.setNum(cartItem.getNum());
			item.setMarketPrice(mallProduct.getMarketPrice());
			item.setBrand(mallProduct.getBrandName());
			item.setModel(mallProduct.getModel());
			item.setParams(getProductParams(mallProduct));
			item.setActivityId(cartItem.getActivityId());
			item.setProtocolPrice(mallProduct.getProtocolPrice());
			//售价总和
			orderPriceSubtotal = orderPriceSubtotal.add(mallProduct.getPrice().multiply(new BigDecimal(cartItem.getNum())));
			//协议价总和
			orderProtocolPriceSubtotal = orderProtocolPriceSubtotal.add(mallProduct.getProtocolPrice().multiply(new BigDecimal(cartItem.getNum())));
			orderItems.add(item);
		}
		supplierOrder.setFreight(BigDecimal.ZERO);
		//售价总金额
		supplierOrder.setItemsPrice(orderPriceSubtotal);
		//售价支付总金额
		supplierOrder.setTotal(supplierOrder.getItemsPrice().add(supplierOrder.getFreight()));
		//协议价总金额
		supplierOrder.setProtocolTotal(orderProtocolPriceSubtotal);
		//协议价支付总金额
		supplierOrder.setPaidAmount(orderProtocolPriceSubtotal.add(supplierOrder.getFreight()));//默认支付金额等于订单金额
		return orderItems;
	}
	
	private String getProductParams(Products mallProduct){
		List<ProductParameter> list = mallProductService.getRelationByProductId(mallProduct.getId());
		if(list!=null&&list.size()>0){
			StringBuilder builder = new StringBuilder();
			for(ProductParameter item:list){
				Parameter parameter = item.getParameter();
				if(parameter!=null&&StringUtils.isNotEmpty(parameter.getName())&&StringUtils.isNotEmpty(item.getParamValue())){
					builder.append(parameter.getName()).append("：").append(item.getParamValue()).append("；");
				}
			}
			return builder.toString();
		}
		return null;
	}
	
	/**
	 * 
	 * Description: 获取发票类型
	 * 
	 * @data 2019年5月8日
	 * @param 
	 * @return
	 */
	public String getInvoices(Integer type){
		String result = "";
		switch (type) {
		case 1:
			result = "增值税普通发票";
			break;
		case 2:
			result = "增值税专用发票";
			break;
		case 3:
			result = "电子发票";
			break;
		}
		return result;
	}
}
