package www.tonghao.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.dto.*;
import www.tonghao.mall.entity.Invoices;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.entity.ReceiverAddresses;
import www.tonghao.mall.mapper.InvoicesMapper;
import www.tonghao.mall.mapper.OrderItemsMapper;
import www.tonghao.mall.mapper.OrdersMapper;
import www.tonghao.mall.mapper.ReceiverAddressesMapper;
import www.tonghao.service.OrderApiService;
import www.tonghao.service.common.entity.*;
import www.tonghao.service.common.mapper.*;
import www.tonghao.service.common.service.SeqService;
import www.tonghao.util.ApiResultUtil;

import java.util.*;

@Service("orderApiService")
public class OrderApiServiceImpl implements OrderApiService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private ReceiverAddressesMapper receiverAddressesMapper;

    @Autowired
    private InvoicesMapper invoicesMapper;

    @Autowired
    private AreasMapper areasMapper;

    @Autowired
    private OrderTrackMapper orderTrackMapper;

    @Autowired
    private OrderElectronicInvoiceMapper orderElectronicInvoiceMapper;

    @Autowired
    private SeqService seqService;

    @Autowired
    private ProductQuotationMapper productQuotationMapper;

    @Override
    @Transactional
    public String submit(OrderDto orderDto, String platformCode) {
        try {
            String orderCheck = orderCheck(orderDto, platformCode);
            if (StringUtils.isNotBlank(orderCheck)) {
                return orderCheck;
            }
            String nowTimeFormat = DateUtilEx.timeFormat(new Date());
            List<SkuDto> skus = JsonUtil.toObject(orderDto.getSku(), new TypeReference<List<SkuDto>>() {
            });
            // 保存收货地址
            ReceiverAddresses receiverAddresses = new ReceiverAddresses();
            receiverAddresses.setConsigneeName(orderDto.getName());
            receiverAddresses.setZipCode(orderDto.getZip());
            receiverAddresses.setMobile(orderDto.getMobile());
            receiverAddresses.setPhone(orderDto.getPhone());
            receiverAddresses.setAreaId(Long.valueOf(orderDto.getCounty()));
            Areas addr = areasMapper.selectByPrimaryKey(Long.valueOf(orderDto.getCounty()));
            receiverAddresses.setAreaName(addr.getTreeNames());
            receiverAddresses.setAddr(orderDto.getAddress());
            receiverAddresses.setEmail(orderDto.getEmail());
            receiverAddresses.setCreatedAt(nowTimeFormat);
            receiverAddresses.setAlias(orderDto.getName());
            int address = receiverAddressesMapper.insertSelective(receiverAddresses);
            // 保存发票信息
            Invoices invoice = new Invoices();
            invoice.setInvoiceType(Short.parseShort(orderDto.getInvoice_type()));
            invoice.setPhone(orderDto.getInvoice_phone());
            invoice.setBank(orderDto.getInvoice_bank());
            invoice.setBankAccount(orderDto.getInvoice_bank_code());
            invoice.setAddress(orderDto.getInvoice_address());
            invoice.setIdCode(orderDto.getInvoice_org_code());
            invoice.setCompany(orderDto.getInvoice_title());
            invoice.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
            int inv = invoicesMapper.insertSelective(invoice);
            // 保存订单信息
            Orders orders = new Orders();
            String orderSn = seqService.getSeqSn("O" + platformCode);
            orders.setSn(orderSn);
            orders.setCreatedAt(nowTimeFormat);
            orders.setUpdatedAt(nowTimeFormat);
            orders.setTitle(platformCode + "采购订单");
            orders.setOrdersState(OrderStatus.commit);
            if (address > 0) {
                orders.setAddressId(receiverAddresses.getId());
            }
            orders.setAreaId(Long.parseLong(orderDto.getCounty()));
            orders.setConsigneeName(orderDto.getName());
            orders.setAddr(orderDto.getAddress());
            orders.setZipCode(orderDto.getZip());
            orders.setMobile(orderDto.getMobile());
            orders.setPhone(orderDto.getPhone());
            orders.setEmail(orderDto.getEmail());
            orders.setRemark(orderDto.getRemark());
            orders.setItemsPrice(orderDto.getOrder_price().subtract(orderDto.getFreight()));
            orders.setFreight(orderDto.getFreight());
            orders.setTotal(orderDto.getOrder_price());
            String invoiceType = "普通发票";
            String invoice_type = orderDto.getInvoice_type();
            switch (invoice_type) {
                case "1":
                    invoiceType = "普通发票";
                    break;
                case "2":
                    invoiceType = "增值税专用发票";
                    break;
                case "3":
                    invoiceType = "电子发票";
                    break;
            }
            orders.setInvoiceType(invoiceType);
            orders.setInvoiceTitle(orderDto.getInvoice_title());
            orders.setInvoiceTaxCode(orderDto.getInvoice_org_code());
            orders.setInvoicePhone(orderDto.getInvoice_phone());
            orders.setInvoiceBank(orderDto.getInvoice_bank());
            orders.setInvoiceBankAccount(orderDto.getInvoice_bank_code());
            if (inv > 0) {
                orders.setInvoiceId(invoice.getId());
            }
            orders.setIsDelete(0);
            String payWay = null;
            String payment = orderDto.getPayment();
            switch (payment) {
                case "1":
                    payWay = "货到付款";
                    break;
                case "2":
                    payWay = "集中支付";
                    break;
                case "3":
                    payWay = "在线支付";
                    break;
                case "4":
                    payWay = "支票";
                    break;
                case "5":
                    payWay = "账期支付";
                    break;
                case "6":
                    payWay = "先款后货";
                    break;
            }
            orders.setPayway(payWay);
            orders.setBillStatus(0);
            orders.setPlatformCode(platformCode);
            int i = ordersMapper.insertSelective(orders);
            if (i > 0) {
                // 保存订单商品信息
                List<OrderItems> orderItemsList = new ArrayList<>();
                OrderItems orderItems = null;
                for (SkuDto skuDto : skus) {
                    orderItems = new OrderItems();
                    String sku = skuDto.getSku();
                    ProductQuotation productQuotation = productQuotationMapper.selectBySku(sku, platformCode);
                    if(productQuotation != null){
                        Products products = productsMapper.selectByPrimaryKey(productQuotation.getProductId());
                        if (products != null) {
                            orderItems.setCreatedAt(nowTimeFormat);
                            orderItems.setUpdatedAt(nowTimeFormat);
                            orderItems.setName(products.getName());
                            orderItems.setPrice(skuDto.getPrice());
                            orderItems.setNum(skuDto.getNum());
                            orderItems.setCatalogName(products.getCatalogName());
                            orderItems.setMarketPrice(products.getMarketPrice());
                            orderItems.setSn(sku);
                            orderItems.setProductId(products.getId());
                            orderItems.setOrderId(orders.getId());
                            orderItems.setOrderSn(orders.getSn());
                            orderItems.setModel(products.getModel());
                            orderItems.setBrand(products.getBrandName());
                            orderItemsList.add(orderItems);
                        }
                    }
                }
                orderItemsMapper.insertList(orderItemsList);
                Map<String, Object> result = new HashMap<>();
                result.put("mall_order_id", orders.getSn());
                result.put("sku", skus);
                result.put("orderPrice", orders.getItemsPrice());
                result.put("freight", orders.getFreight());
                return ApiResultUtil.success("操作成功", result);
            } else {
                return ApiResultUtil.error("系统异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
            return ApiResultUtil.error("系统异常");
        }
    }

    @Override
    public String confirm(String orderSn, String platformCode) {
        Orders orders = ordersMapper.findBySn(orderSn);
        if (orders == null) {
            return ApiResultUtil.error("订单不存在");
        }
        orders.setOrdersState(OrderStatus.confirmed);
        orders.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
        int i = ordersMapper.updateByPrimaryKeySelective(orders);
        if (i > 0) {
            return ApiResultUtil.success("确认订单成功");
        } else {
            return ApiResultUtil.error("确认订单失败");
        }
    }

    @Override
    public String cancel(String orderSn, String platformCode) {
        Orders orders = ordersMapper.findBySn(orderSn);
        if (orders == null) {
            return ApiResultUtil.error("订单不存在");
        }
        if (!orders.getOrdersState().equals(OrderStatus.commit)) {
            return ApiResultUtil.error("该订单状态无法取消");
        }
        orders.setOrdersState(OrderStatus.cancelled);
        orders.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
        int i = ordersMapper.updateByPrimaryKeySelective(orders);
        if (i > 0) {
            return ApiResultUtil.success("取消订单成功");
        } else {
            return ApiResultUtil.error("取消订单失败");
        }
    }

    @Override
    public String select(String orderSn, String platformCode) {
        Orders orders = ordersMapper.findBySn(orderSn);
        if (orders == null) {
            return ApiResultUtil.error("订单不存在");
        }
        OrderSelectDto orderSelectDto = new OrderSelectDto();
        orderSelectDto.setOrder_id(orderSn);
        orderSelectDto.setState(orders.getOrdersState().equals(OrderStatus.completed) ? 1 : 0);
        orderSelectDto.setSubmit_state(orders.getOrdersState().equals(OrderStatus.commit) ? 0 : 1);
        orderSelectDto.setDeliver_state(orders.getOrdersState().equals(OrderStatus.receive) ? 1 : 0);
        orderSelectDto.setTotal_price(orders.getTotal());
        // 订单商品信息
        List<SkuDto> skuDtoList = new ArrayList<>();
        List<OrderItems> orderItemsList = orderItemsMapper.findListByOrderId(orders.getId());
        if (CollectionUtils.isNotEmpty(orderItemsList)) {
            SkuDto skuDto = null;
            for (OrderItems orderItems : orderItemsList) {
                skuDto = new SkuDto();
                skuDto.setSku(orderItems.getSn());
                skuDto.setNum(orderItems.getNum());
                skuDto.setPrice(orderItems.getPrice());
                skuDtoList.add(skuDto);
            }
        }
        orderSelectDto.setSkus(skuDtoList);
        orderSelectDto.setType(1);
        return ApiResultUtil.success("操作成功", orderSelectDto);
    }

    @Override
    public String track(String orderSn, String platformCode) {
        Orders orders = ordersMapper.findBySn(orderSn);
        if (orders == null) {
            return ApiResultUtil.error("订单不存在");
        }
        List<OrderTrack> orderTracks = orderTrackMapper.selectByOrderId(orders.getId());
        SelectTrackDto selectTrackDto = new SelectTrackDto();
        List<Track> trackList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderTracks)) {
            Track track = null;
            for (OrderTrack orderTrack : orderTracks) {
                track = new Track();
                track.setContent(orderTrack.getContent());
                track.setOperate_time(orderTrack.getOperateTime());
                track.setOperator(orderTrack.getOperator());
                trackList.add(track);
            }
        }
        selectTrackDto.setTrack(trackList);
        List<Waybill> waybillList = new ArrayList<>();
        Waybill waybill = new Waybill();
        waybill.setLogistic_name(orders.getLoginName());
        waybill.setLogistic_no(orders.getLogisticsSn());
        waybill.setLogistic_type(orders.getLogisticsType());
        waybillList.add(waybill);
        selectTrackDto.setWaybill(waybillList);
        return ApiResultUtil.success("操作成功", selectTrackDto);
    }

    @Override
    public String getInvoiceList(String orderSn, String platformCode) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        for (String sn : orderSn.split(",")) {
            Orders orders = ordersMapper.findBySn(orderSn);
            if (orders == null) {
                return ApiResultUtil.error("订单不存在");
            }
            Example example = new Example(OrderElectronicInvoice.class);
            Example.Criteria criteria = example.createCriteria();
            Example.Criteria orderId = criteria.andEqualTo("orderId", orders.getId());
            List<OrderElectronicInvoice> orderElectronicInvoices = orderElectronicInvoiceMapper.selectByExample(example);
            map = new HashMap<>();
            map.put(sn, orderElectronicInvoices);
            result.add(map);
        }
        return ApiResultUtil.success("操作成功", map);
    }

    /**
     * 订单参数校验
     *
     * @param orderDto
     * @return
     */
    private String orderCheck(OrderDto orderDto, String platformCode) {
        if (StringUtils.isBlank(orderDto.getYggc_order())) {
            return ApiResultUtil.error("yggc_order参数为空");
        }
        if (StringUtils.isBlank(orderDto.getSku())) {
            return ApiResultUtil.error("sku参数为空");
        }
        if (StringUtils.isBlank(orderDto.getName())) {
            return ApiResultUtil.error("收货人为空");
        }
        if (orderDto.getProvince() == null) {
            return ApiResultUtil.error("一级地址为空");
        }
        if (orderDto.getCity() == null) {
            return ApiResultUtil.error("二级地址为空");
        }
        if (orderDto.getCounty() == null) {
            return ApiResultUtil.error("三级地址为空");
        }
        if (StringUtils.isBlank(orderDto.getAddress())) {
            return ApiResultUtil.error("详细地址为空");
        }
        if (StringUtils.isBlank(orderDto.getPhone()) && StringUtils.isBlank(orderDto.getMobile())) {
            return ApiResultUtil.error("收货人联系方式为空");
        }
        if (StringUtils.isBlank(orderDto.getInvoice_title())) {
            return ApiResultUtil.error("发票抬头为空");
        }
        if (StringUtils.isBlank(orderDto.getInvoice_type())) {
            return ApiResultUtil.error("发票类型为空");
        }
        if (StringUtils.isBlank(orderDto.getInvoice_org_code())) {
            return ApiResultUtil.error("纳税人识别号为空");
        }
        if ("2".equals(orderDto.getInvoice_type())) {
            if (StringUtils.isBlank(orderDto.getInvoice_name())) {
                return ApiResultUtil.error("收票人为空");
            }
            if (StringUtils.isBlank(orderDto.getInvoice_phone())) {
                return ApiResultUtil.error("注册电话为空");
            }
            if (StringUtils.isBlank(orderDto.getInvoice_bank())) {
                return ApiResultUtil.error("开户银行为空");
            }
            if (StringUtils.isBlank(orderDto.getInvoice_bank_code())) {
                return ApiResultUtil.error("开户行账号为空");
            }
            if (StringUtils.isBlank(orderDto.getInvoice_address())) {
                return ApiResultUtil.error("注册地址为空");
            }
            if (StringUtils.isBlank(orderDto.getInvoice_mobile())) {
                return ApiResultUtil.error("收票联系电话为空");
            }
            if (StringUtils.isBlank(orderDto.getInvoice_receipt_address())) {
                return ApiResultUtil.error("收票地址为空");
            }
        }
        if (orderDto.getPayment() == null) {
            return ApiResultUtil.error("支付方式为空");
        }
        if (orderDto.getOrder_price() == null) {
            return ApiResultUtil.error("订单金额为空");
        }
        if (orderDto.getFreight() == null) {
            return ApiResultUtil.error("运费为空");
        }
        // 商品校验
        List<SkuDto> skus = JsonUtil.toObject(orderDto.getSku(), new TypeReference<List<SkuDto>>() {
        });
        for (SkuDto skuDto : skus) {
            ProductQuotation productQuotation = productQuotationMapper.selectBySku(skuDto.getSku(), platformCode);
            if (productQuotation == null) {
                return ApiResultUtil.error("商品：" + skuDto.getSku() + " 不存在");
            }
            Products products = productsMapper.selectByPrimaryKey(productQuotation.getProductId());
            if (products == null) {
                return ApiResultUtil.error("商品：" + skuDto.getSku() + " 不存在");
            }
            if (productQuotation.getStatus() == null || productQuotation.getStatus() != 3) {
                return ApiResultUtil.error("商品：" + skuDto.getSku() + " 已下架");
            }
            if (skuDto.getPrice() == null) {
                return ApiResultUtil.error("商品：" + skuDto.getSku() + " 价格为空");
            }
            if (productQuotation.getProtocolPrice().compareTo(skuDto.getPrice()) != 0) {
                return ApiResultUtil.error("商品：" + skuDto.getSku() + " 价格不一致，请重新获取价格下单");
            }
            if (skuDto.getNum() == null) {
                return ApiResultUtil.error("商品：" + skuDto.getSku() + " 数量为空");
            }
            if (productQuotation.getStock().compareTo(skuDto.getNum()) < 0) {
                return ApiResultUtil.error("商品：" + skuDto.getSku() + " 库存不足");
            }
        }
        return null;
    }
}
