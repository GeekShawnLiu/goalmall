package www.tonghao.mall.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.enums.OrderStatus;
import www.tonghao.common.utils.BigDecimalUtil;
import www.tonghao.mall.entity.OrderItems;
import www.tonghao.mall.mapper.OrderItemsMapper;
import www.tonghao.mall.service.OrderItemsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("mallOrderItemsService")
public class OrderItemsServiceImpl extends BaseServiceImpl<OrderItems> implements OrderItemsService{

	@Autowired
	private OrderItemsMapper orderItemsMapper;
	
	@Override
	public OrderItems findById(Long id) {
		return orderItemsMapper.findById(id);
	}

	@Override
	public List<OrderItems> findProductOrderItems(Long productId) {
		return orderItemsMapper.findProductOrderItems(productId);
	}

	@Override
	public List<OrderItems> findByMap(Map<String, Object> map) {
		return orderItemsMapper.findByMap(map);
	}

	@Override
	public List<OrderItems> productStatisticalList(Map<String, Object> map) {
		List<OrderItems> list = orderItemsMapper.productStatisticalList(map);
		if(CollectionUtils.isNotEmpty(list)){
			list.forEach(oi -> {
				if(oi != null){
					//品目
					String twoCatalogName = oi.getTwoCatalogName();
					if(twoCatalogName != null){
						String[] split = twoCatalogName.split("_");
						if(split.length > 1){
							oi.setTwoCatalogName(split[1]);
							oi.setOneCatalogName(split[0]);
						}
					}
				}
			});
		}
		return list;
	}

	@Override
	public void downloadProductStatistical(Map<String, Object> map, HttpServletResponse response) {
		List<OrderItems> list = orderItemsMapper.productStatisticalList(map);

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFFont createFont = workbook.createFont();
		createFont.setFontHeightInPoints((short) 12);// 设置字体

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.LEFT); // 内容居左
		cellStyle.setFont(createFont);
		
		XSSFCellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle1.setFont(createFont);
		
		XSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setAlignment(HorizontalAlignment.RIGHT);// 水平居右
		cellStyle2.setFont(createFont);
		
		XSSFSheet sheet = workbook.createSheet("商品统计");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 3000);
		sheet.setColumnWidth(9, 5000);
		sheet.setColumnWidth(10, 5000);
		sheet.setColumnWidth(20, 4000);
		sheet.setColumnWidth(28, 4000);
		sheet.setColumnWidth(29, 4000);
		sheet.setColumnWidth(30, 4000);
		XSSFRow row0 = sheet.createRow(0);

		String[] title = { "序号", "组织机构", "用户名", "商品编码", "商品名称", "平台订单",
				"供应商名称", "电商订单", "电商子订单", "下单时间", "完成时间", "订单状态", "成本价", "售价",
				"数量", "毛利", "毛利率", "一级分类", "二级分类", "三级分类", "活动名称", "支付形式",
				"积分消费", "个人消费", "消费合计", "剩余积分", "是否包邮", "运费", "收件人姓名", "收件地址",
				"收件人电话" };
		for (int i = 0; i < title.length; i++) {
			XSSFCell title0 = row0.createCell(i);
			title0.setCellValue(title[i]);
			title0.setCellStyle(cellStyle1);
		}

		for (int i = 0; i < list.size(); i++) {
			OrderItems orderItems = list.get(i);
			XSSFRow row = sheet.createRow(i + 1);
			XSSFCell createCell0 = row.createCell(0);
			createCell0.setCellValue(i + 1);
			createCell0.setCellStyle(cellStyle1);
			//组织机构
			XSSFCell createCell1 = row.createCell(1);
			createCell1.setCellValue(orderItems.getOrder().getPurchaserName());
			createCell1.setCellStyle(cellStyle);
			//用户名
			XSSFCell createCell2 = row.createCell(2);
			createCell2.setCellValue(orderItems.getOrder().getUserName());
			createCell2.setCellStyle(cellStyle);
			//商品编码
			XSSFCell createCell3 = row.createCell(3);
			createCell3.setCellValue(orderItems.getSn());
			createCell3.setCellStyle(cellStyle);
			//商品名称
			XSSFCell createCell4 = row.createCell(4);
			createCell4.setCellValue(orderItems.getName());
			createCell4.setCellStyle(cellStyle);
			//平台订单
			XSSFCell createCell5 = row.createCell(5);
			createCell5.setCellValue(orderItems.getOrder().getSn());
			createCell5.setCellStyle(cellStyle);
			//供应商名称
			XSSFCell createCell6 = row.createCell(6);
			createCell6.setCellValue(orderItems.getOrder().getSupplierName());
			createCell6.setCellStyle(cellStyle);
			//电商订单
			XSSFCell createCell7 = row.createCell(7);
			createCell7.setCellValue(orderItems.getOrder().getEmallSn());
			createCell7.setCellStyle(cellStyle);
			//电商子订单
			XSSFCell createCell8 = row.createCell(8);
			createCell8.setCellValue(orderItems.getOrder().getChildOrderEmallSns());
			createCell8.setCellStyle(cellStyle);
			//下单时间
			XSSFCell createCell9 = row.createCell(9);
			createCell9.setCellValue(orderItems.getOrder().getCreatedAt());
			createCell9.setCellStyle(cellStyle);
			//完成时间
			XSSFCell createCell10 = row.createCell(10);
			createCell10.setCellValue(orderItems.getOrder().getCompletedAt());
			createCell10.setCellStyle(cellStyle);
			//订单状态
			XSSFCell createCell11 = row.createCell(11);
			OrderStatus ordersState = orderItems.getOrder().getOrdersState();
			if(ordersState.equals(OrderStatus.commit)){
				createCell11.setCellValue("已提交");
			}else if(ordersState.equals(OrderStatus.confirmed)){
				createCell11.setCellValue("待收货");
			}else if(ordersState.equals(OrderStatus.cancelled)){
				createCell11.setCellValue("已取消");
			}else if(ordersState.equals(OrderStatus.completed)){
				createCell11.setCellValue("已完成");
			}else{
				createCell11.setCellValue("");
			}
			createCell11.setCellStyle(cellStyle1);
			//成本价
			XSSFCell createCell12 = row.createCell(12);
			createCell12.setCellValue(orderItems.getProtocolPrice().setScale(2)+"");
			createCell12.setCellStyle(cellStyle2);
			//售价
			XSSFCell createCell13 = row.createCell(13);
			createCell13.setCellValue(orderItems.getPrice().setScale(2)+"");
			createCell13.setCellStyle(cellStyle2);
			//数量
			XSSFCell createCell14= row.createCell(14);
			createCell14.setCellValue(orderItems.getNum()+"");
			createCell14.setCellStyle(cellStyle2);
			//毛利
			XSSFCell createCell15= row.createCell(15);
			createCell15.setCellValue(orderItems.getProfit().setScale(2)+"");
			createCell15.setCellStyle(cellStyle2);
			//毛利率
			XSSFCell createCell16= row.createCell(16);
			createCell16.setCellValue(orderItems.getProfitRate());
			createCell16.setCellStyle(cellStyle2);
			//品目
			String twoCatalogName = orderItems.getTwoCatalogName();
			if(twoCatalogName != null){
				String[] split = twoCatalogName.split("_");
				if(split.length > 1){
					orderItems.setTwoCatalogName(split[1]);
					orderItems.setOneCatalogName(split[0]);
				}
			}
			//一级分类
			XSSFCell createCell17= row.createCell(17);
			createCell17.setCellValue(orderItems.getOneCatalogName());
			createCell17.setCellStyle(cellStyle);
			//二级分类
			XSSFCell createCell18= row.createCell(18);
			createCell18.setCellValue(orderItems.getTwoCatalogName());
			createCell18.setCellStyle(cellStyle);
			//三级分类
			XSSFCell createCell19= row.createCell(19);
			createCell19.setCellValue(orderItems.getThreeCatalogName());
			createCell19.setCellStyle(cellStyle);
			//活动名称
			XSSFCell createCell20= row.createCell(20);
			createCell20.setCellValue(orderItems.getOrder().getActivityNames());
			createCell20.setCellStyle(cellStyle);
			//支付形式
			XSSFCell createCell21= row.createCell(21);
			createCell21.setCellValue(orderItems.getOrder().getPayName());
			createCell21.setCellStyle(cellStyle1);
			//积分消费
			XSSFCell createCell22= row.createCell(22);
			createCell22.setCellValue(orderItems.getOrder().getPayIntegral().setScale(2)+"");
			createCell22.setCellStyle(cellStyle2);
			//个人消费
			XSSFCell createCell23= row.createCell(23);
			createCell23.setCellValue(orderItems.getOrder().getPayMoney().setScale(2)+"");
			createCell23.setCellStyle(cellStyle2);
			//消费合计
			XSSFCell createCell24= row.createCell(24);
			createCell24.setCellValue(orderItems.getOrder().getTotal().setScale(2)+"");
			createCell24.setCellStyle(cellStyle2);
			//剩余积分
			XSSFCell createCell25= row.createCell(25);
			createCell25.setCellValue(orderItems.getOrder().getBalanceIntegral().setScale(2)+"");
			createCell25.setCellStyle(cellStyle2);
			//是否包邮
			XSSFCell createCell26 = row.createCell(26);
			createCell26.setCellValue(BigDecimalUtil.compareTo(orderItems.getOrder().getFreight(), new BigDecimal("0.00")) == 0 ? "是" : "否");
			createCell26.setCellStyle(cellStyle1);
			//运费
			XSSFCell createCell27 = row.createCell(27);
			createCell27.setCellValue(orderItems.getOrder().getFreight().setScale(2)+"");
			createCell27.setCellStyle(cellStyle2);
			//收件人姓名
			XSSFCell createCell28 = row.createCell(28);
			createCell28.setCellValue(orderItems.getOrder().getConsigneeName());
			createCell28.setCellStyle(cellStyle);
			//收件地址
			XSSFCell createCell29 = row.createCell(29);
			createCell29.setCellValue(orderItems.getOrder().getAddr());
			createCell29.setCellStyle(cellStyle);
			//收件人电话
			XSSFCell createCell30 = row.createCell(30);
			createCell30.setCellValue(orderItems.getOrder().getMobile());
			createCell30.setCellStyle(cellStyle1);
		}
		ServletOutputStream fileOut = null;
		try {
			fileOut = response.getOutputStream();
			String fileName = new String("商品统计".getBytes("UTF-8"), "ISO8859-1");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			fileOut = response.getOutputStream();
			workbook.write(fileOut);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	@Override
	public OrderItems selectByOrderAndProduct(Long orderId, Long productId) {
		return orderItemsMapper.selectByOrderAndProduct(orderId, productId);
	}
}
