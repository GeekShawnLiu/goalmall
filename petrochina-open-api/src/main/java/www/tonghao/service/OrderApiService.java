package www.tonghao.service;

import www.tonghao.dto.OrderDto;

public interface OrderApiService {

    /**
     * 预下订单
     *
     * @param orderDto
     * @param platformCode
     * @return
     */
    String submit(OrderDto orderDto, String platformCode);

    /**
     * 订单确认
     *
     * @param orderSn
     * @param platformCode
     * @return
     */
    String confirm(String orderSn, String platformCode);

    /**
     * 订单取消
     *
     * @param orderSn
     * @param platformCode
     * @return
     */
    String cancel(String orderSn, String platformCode);

    /**
     * 订单查询
     *
     * @param orderSn
     * @param platformCode
     * @return
     */
    String select(String orderSn, String platformCode);

    /**
     * 物流信息
     *
     * @param orderSn
     * @param platformCode
     * @return
     */
    String track(String orderSn, String platformCode);

    /**
     * 电子发票信息
     *
     * @param orderSn
     * @param platformCode
     * @return
     */
    String getInvoiceList(String orderSn, String platformCode);
}
