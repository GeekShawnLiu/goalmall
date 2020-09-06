package www.tonghao.service.common.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.config.OrderStatusConfig;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.IntegralConsume;
import www.tonghao.service.common.entity.IntegralUser;
import www.tonghao.service.common.mapper.IntegralConsumeMapper;
import www.tonghao.service.common.mapper.IntegralUserMapper;
import www.tonghao.service.common.service.IntegralUserService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @Description:用户的积分
 * @date 2019年5月9日
 */
@Service("integralUserService")
public class IntegralUserServiceImpl extends BaseServiceImpl<IntegralUser> implements IntegralUserService{
	
	@Autowired
	private IntegralUserMapper integralUserMapper;
	
	@Autowired
	private IntegralConsumeMapper integralConsumeMapper;
	
	@Autowired
	private OrderStatusConfig orderStatusConfig;

/*	@Override
	@Transactional(rollbackFor=Exception.class)
	public String subtractBalance(Long userId, Long activityId, Long orderId, BigDecimal number) {
		IntegralUser iu = new IntegralUser();
		iu.setUserId(userId);
		iu.setActivityId(activityId);
		IntegralUser selectOne = integralUserMapper.selectOne(iu);
		if(selectOne !=null && (number.compareTo(selectOne.getBalance()) == -1 || number.compareTo(selectOne.getBalance()) == 0)){
			try {
				BigDecimal balance = selectOne.getBalance();
				BigDecimal newBalance = balance.subtract(number);
				
				*//**
				 * 更新余额
				 *//*
				IntegralUser integralUser = new IntegralUser();
				integralUser.setId(selectOne.getId());
				integralUser.setBalance(newBalance);
				integralUser.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				int result = integralUserMapper.updateByPrimaryKeySelective(integralUser);
				if(result > 0){
					IntegralConsume integralConsume = new IntegralConsume();
					integralConsume.setType(1);//1消费  2退还
					integralConsume.setIntegralUserId(selectOne.getId());
					integralConsume.setUserId(userId);
					integralConsume.setActivityId(activityId);
					integralConsume.setOrderId(orderId);
					integralConsume.setIntegralNum(number);
					integralConsume.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					int insertSelective = integralConsumeMapper.insertSelective(integralConsume);
					if(insertSelective > 0){
						return "success";
					}
				}
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
				return "error";
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
				e.printStackTrace();
				return "error";
			}
		}
		return "error";
	}*/
	
	/**
	 * @Description:积分支付，减余额
	 */
	@Override
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public String subtractBalance(Long userId, Long activityId, Long orderId, BigDecimal number) {
		IntegralUser iu = new IntegralUser();
		iu.setUserId(userId);
		iu.setActivityId(activityId);
		List<IntegralUser> integralUserList = integralUserMapper.select(iu);
		if(CollectionUtils.isNotEmpty(integralUserList)){
			//该活动下所有的积分余额
			BigDecimal totalBalance = BigDecimal.ZERO;
			for(IntegralUser integralUser : integralUserList){
				totalBalance = totalBalance.add(integralUser.getBalance());
			}
			
			if(number.compareTo(totalBalance)<=0){
				try {
					boolean flag = true;
					for(IntegralUser integralUser : integralUserList){
						if(number != BigDecimal.ZERO){
							BigDecimal balance = integralUser.getBalance();
							//余额等于或大于支付金额
							if(balance.compareTo(number) >=0){
								BigDecimal newBalance = balance.subtract(number);
								//更新余额
								boolean updateBalance = updateBalance(integralUser.getId(), newBalance, number, userId, activityId, orderId);
								if(updateBalance){
									break;
								}else{
									flag = false;
									break;
								}
							}else{
								BigDecimal newBalance = balance.subtract(integralUser.getBalance());
								//更新余额
								boolean updateBalance = updateBalance(integralUser.getId(), newBalance, integralUser.getBalance(), userId, activityId, orderId);
								if(updateBalance){
									number = number.subtract(integralUser.getBalance());
								}else{
									flag = false;
									break;
								}
							}
						}
					}
					
					if(flag){
						return "success";
					}else{
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
						return "error";
					}
				} catch (Exception e) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
					e.printStackTrace();
					return "error";
				}
			}
		}
		return "error";
	}
	
	/**
	 * @Description:更新余额
	 * @date 2019年7月10日
	 * newBalance: 要更新的余额
	 * number：要记录的支付金额
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean updateBalance(Long integralUserId, BigDecimal newBalance, BigDecimal number, Long userId, Long activityId, Long orderId){
		IntegralUser integralUser = new IntegralUser();
		integralUser.setId(integralUserId);
		integralUser.setBalance(newBalance);
		integralUser.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = integralUserMapper.updateByPrimaryKeySelective(integralUser);
		if(result > 0){
			IntegralConsume integralConsume = new IntegralConsume();
			integralConsume.setType(1);//1消费  2退还
			integralConsume.setIntegralUserId(integralUserId);
			integralConsume.setUserId(userId);
			integralConsume.setActivityId(activityId);
			integralConsume.setOrderId(orderId);
			integralConsume.setIntegralNum(number);
			integralConsume.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			int insertSelective = integralConsumeMapper.insertSelective(integralConsume);
			if(insertSelective > 0){
				return true;
			}
		}
		return false;
	}
	
	

	/**
	 * @Description:我的积分
	 * @date 2019年5月5日
	 */
	@Override
	public PageInfo<IntegralUser> selectIntegralUser(PageBean page, Long userId) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<IntegralUser> integralUserList = integralUserMapper.selectIntegralUser(userId);
		if(CollectionUtils.isNotEmpty(integralUserList)){
			integralUserList.forEach(integralUser ->{
				int compareDate = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), integralUser.getActivityEndAt(), "yyyy-MM-dd HH:mm:ss");
				if(integralUser.getIsOffline() == 0){
					int compareDate2 = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), integralUser.getActivityOnlineAt(), "yyyy-MM-dd HH:mm:ss");
					int compareDate3 = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), integralUser.getActivityStartAt(), "yyyy-MM-dd HH:mm:ss");
					//活动开始时间 /活动上线时间 > 现在时间  < 活动结束时间，
					if(compareDate == -1 && compareDate2 == 1 && compareDate3 == 1){
						integralUser.setIsExchange(true);
					}else{
						integralUser.setIsExchange(false);
					}
				}else{
					integralUser.setIsExchange(false);
				}
				if(compareDate == 1){
					integralUser.setBalance(BigDecimal.ZERO);
				}
			});
		}
		return new PageInfo<IntegralUser>(integralUserList);
	}

	/**
	 * @Description:查询全部用户积分
	 * @date 2019年5月5日
	 */
	@Override
	public PageInfo<IntegralUser> selectAllIntegralUser(PageBean page, IntegralUser integralUser) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<IntegralUser> integralUserList = integralUserMapper.selectAllIntegralUser(integralUser);
		if(CollectionUtils.isNotEmpty(integralUserList)){
			for(IntegralUser iu : integralUserList){
				BigDecimal total = iu.getTotal();
				BigDecimal balance = iu.getBalance();
				if(total.compareTo(balance) == 1){
					BigDecimal consumed = total.subtract(balance);
					iu.setConsumed(consumed);
				}else if(total.compareTo(balance) == 0){
					iu.setConsumed(BigDecimal.ZERO);
				}
			}
		}
		return new PageInfo<IntegralUser>(integralUserList);
	}


	/**
	 * @Description:导出excel
	 * @date 2019年5月7日
	 */
	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, IntegralUser integralUser) {
		List<IntegralUser> list = integralUserMapper.selectAllIntegralUser(integralUser);
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFFont createFont = workbook.createFont();
		createFont.setFontHeightInPoints((short)12);//设置字体
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.LEFT); //内容居左
		
		XSSFCellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setAlignment(HorizontalAlignment.CENTER);//标题水平居中
		cellStyle1.setFont(createFont);
		
		XSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth(0,5000);
		sheet.setColumnWidth(1,7000);
		sheet.setColumnWidth(2,7000);
		sheet.setColumnWidth(3,4000);
		sheet.setColumnWidth(4,4000);
		sheet.setColumnWidth(5,4000);
		XSSFRow row0 = sheet.createRow(0);
		
		XSSFCell title0 = row0.createCell(0);
		title0.setCellValue("姓名");
		title0.setCellStyle(cellStyle1);
		
		XSSFCell title1 = row0.createCell(1);
		title1.setCellValue("积分名称");
		title1.setCellStyle(cellStyle1);
		
		XSSFCell title2 = row0.createCell(2);
		title2.setCellValue("活动名称");
		title2.setCellStyle(cellStyle1);
		
		XSSFCell title3 = row0.createCell(3);
		title3.setCellValue("积分总额");
		title3.setCellStyle(cellStyle1);
		
		XSSFCell title4 = row0.createCell(4);
		title4.setCellValue("未消费积分");
		title4.setCellStyle(cellStyle1);
		
		XSSFCell title5 = row0.createCell(5);
		title5.setCellValue("已消费积分");
		title5.setCellStyle(cellStyle1);

		for(int i=0; i<list.size(); i++){
			String realName = list.get(i).getRealName();
			String integralName = list.get(i).getIntegralName(); 
			String activityName = list.get(i).getActivityName();
			BigDecimal total = list.get(i).getTotal();
			BigDecimal balance = list.get(i).getBalance(); //未消费
			BigDecimal consumed = BigDecimal.ZERO; //已消费
			
			if(total.compareTo(balance) == 1){
				consumed = total.subtract(balance);
			}
			
			XSSFRow row = sheet.createRow(i+1);
			XSSFCell createCell0 = row.createCell(0);
			createCell0.setCellValue(realName ==null? "": realName);
			createCell0.setCellStyle(cellStyle);
			
			XSSFCell createCell1 = row.createCell(1);
			createCell1.setCellValue(integralName ==null? "": integralName);
			createCell1.setCellStyle(cellStyle);
			
			XSSFCell createCell2 = row.createCell(2);
			createCell2.setCellValue(activityName ==null? "": activityName);
			createCell2.setCellStyle(cellStyle);
			
			XSSFCell createCell3 = row.createCell(3);
			createCell3.setCellValue(total.setScale(2).toString());
			createCell3.setCellStyle(cellStyle);
			
			XSSFCell createCell4 = row.createCell(4);
			createCell4.setCellValue(balance.setScale(2).toString());
			createCell4.setCellStyle(cellStyle);
			
			XSSFCell createCell5 = row.createCell(5);
			createCell5.setCellValue(consumed.setScale(2).toString());
			createCell5.setCellStyle(cellStyle);
		}
		ServletOutputStream fileOut=null;
		try{
			fileOut=response.getOutputStream();
			String fileName = new String("用户积分".getBytes("UTF-8"),"ISO-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");
			fileOut=response.getOutputStream();
			workbook.write(fileOut);
		}catch(Exception e1){	
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
	public List<IntegralUser> selectIntegralUser(Long userId) {
		List<IntegralUser> integralUserList = integralUserMapper.selectIntegralUser(userId);
		if(CollectionUtils.isNotEmpty(integralUserList)){
			for(IntegralUser integralUser : integralUserList){
				int compareDate = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), integralUser.getActivityEndAt(), "yyyy-MM-dd HH:mm:ss");
				int compareDate2 = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), integralUser.getActivityOnlineAt(), "yyyy-MM-dd HH:mm:ss");
				int compareDate3 = DateUtilEx.compareDate(DateUtilEx.timeFormat(new Date()), integralUser.getActivityStartAt(), "yyyy-MM-dd HH:mm:ss");
				/*if(integralUser.getIsOffline() == 0){
					//活动开始时间 /活动上线时间 > 现在时间  < 活动结束时间，
					if(compareDate == -1 && compareDate2 == 1 && compareDate3 == 1){
						integralUser.setIsExchange(true);
					}else{
						integralUser.setIsExchange(false);
					}
				}else{
					integralUser.setIsExchange(false);
				}*/
				
				if(integralUser.getIsOffline() == 0 && compareDate == -1 && compareDate2 == 1 && compareDate3 == 1){
					integralUser.setStatus(1); //去兑换
				}else if(integralUser.getIsOffline() == 0 && compareDate3 == -1){
					integralUser.setStatus(2); //未开始
				}else{
					integralUser.setStatus(3); //已结束
				}
			}
		}
		return integralUserList;
	}

	/**
	 * @Description:查询用户积分信息
	 * @date 2019年5月15日
	 */
	@Override
	public IntegralUser getUserIntegralInfo(Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		IntegralUser integralUser = integralUserMapper.selectAllIntegralUser(map);
		if(integralUser !=null){
			BigDecimal total = integralUser.getTotal();
			BigDecimal balance = integralUser.getBalance();
			if(total.compareTo(balance) == 1){
				BigDecimal consumed = total.subtract(balance);
				integralUser.setConsumed(consumed);
			}else if(total.compareTo(balance) == 0){
				integralUser.setConsumed(BigDecimal.ZERO);
			}
		}
		return integralUser;
	}

	/**
	 * @Description:查询积分消费
	 * @date 2019年5月29日
	 */
	@Override
	public PageInfo<IntegralConsume> selectIntegralConsume(PageBean page, Map<String, Object> map) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<IntegralConsume> selectIntegralConsume = integralConsumeMapper.selectIntegralConsume(map);
		if(selectIntegralConsume !=null && selectIntegralConsume.size() >0){
			Map<String, String> orderstatus = orderStatusConfig.getOrderstatus();
			selectIntegralConsume.forEach(integralConsume ->{
				String ordersState = orderstatus.get(integralConsume.getOrdersState());
				integralConsume.setOrdersState(ordersState);
			});
		}
		return new PageInfo<IntegralConsume>(selectIntegralConsume);
	}

	/**
	 * @Description:积分退款，加余额
	 */
	/*@Override
	@Transactional(rollbackFor=Exception.class)
	public String addBalance(Long userId, Long activityId, Long orderId, BigDecimal number, String reason) {
		IntegralUser iu = new IntegralUser();
		iu.setUserId(userId);
		iu.setActivityId(activityId);
		IntegralUser selectOne = integralUserMapper.selectOne(iu);
		if(selectOne !=null){
			BigDecimal balance = selectOne.getBalance();
			BigDecimal newBalance = balance.add(number);
			*//**
			 * 更新余额
			 *//*
			IntegralUser integralUser = new IntegralUser();
			integralUser.setId(selectOne.getId());
			integralUser.setBalance(newBalance);
			integralUser.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			int result = integralUserMapper.updateByPrimaryKeySelective(integralUser);
			if(result >0){
				IntegralConsume integralConsume = new IntegralConsume();
				integralConsume.setType(2);//1消费  2退还
				integralConsume.setIntegralUserId(selectOne.getId());
				integralConsume.setUserId(userId);
				integralConsume.setActivityId(activityId);
				integralConsume.setOrderId(orderId);
				integralConsume.setIntegralNum(number);
				integralConsume.setReason(reason);
				integralConsume.setCreatedAt(DateUtilEx.timeFormat(new Date()));
				int insertSelective = integralConsumeMapper.insertSelective(integralConsume);
				if(insertSelective > 0){
					return "success";
				}
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
			return "error";
		}
		return "error";
	}*/
	
	@Override
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRES_NEW)
	public String addBalance(Long userId, Long activityId, Long orderId, BigDecimal number, String reason) {
		IntegralUser iu = new IntegralUser();
		iu.setUserId(userId);
		iu.setActivityId(activityId);
		List<IntegralUser> integralUserList = integralUserMapper.select(iu);
		if(CollectionUtils.isNotEmpty(integralUserList)){
			Boolean flag = false;
			
			//超出的余额
			BigDecimal tmpBalance = BigDecimal.ZERO;
			for(IntegralUser integralUser : integralUserList){
				//分配的总积分
				BigDecimal total = integralUser.getTotal();
				//余额
				BigDecimal balance = integralUser.getBalance();
				//新的余额
				BigDecimal newBalance = balance.add(number).add(tmpBalance);
				if(total.compareTo(newBalance) >=0){
					boolean updateIntegralUser = updateIntegralUser(userId, activityId, orderId, newBalance, number, integralUser.getId(), reason);
					if(updateIntegralUser){
						flag = true;
						//清除超出的余额
						tmpBalance = BigDecimal.ZERO;
						break;
					}
				}else{
					BigDecimal subtract = newBalance.subtract(total);
					tmpBalance = tmpBalance.add(subtract);
					
					//需要更新的余额
					BigDecimal updateBalance = newBalance.subtract(tmpBalance);
					updateIntegralUser(userId, activityId, orderId, updateBalance, number, integralUser.getId(), reason);
				}
			}
			
			if(flag){
				return "success";
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
				return "error";
			}
		}
		return "error";
	}
	
	/**
	 * @Description:更新余额
	 * @date 2019年7月22日
	 * newBalance: 要更新的余额
	 * number：要记录的支付金额
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private boolean updateIntegralUser(Long userId, Long activityId, Long orderId, BigDecimal newBalance, BigDecimal number, Long integralUserId, String reason){
		IntegralUser integralUser = new IntegralUser();
		integralUser.setId(integralUserId);
		integralUser.setBalance(newBalance);
		integralUser.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = integralUserMapper.updateByPrimaryKeySelective(integralUser);
		if(result >0){
			IntegralConsume integralConsume = new IntegralConsume();
			integralConsume.setType(2);//1消费  2退还
			integralConsume.setIntegralUserId(integralUserId);
			integralConsume.setUserId(userId);
			integralConsume.setActivityId(activityId);
			integralConsume.setOrderId(orderId);
			integralConsume.setIntegralNum(number);
			integralConsume.setReason(reason);
			integralConsume.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			int insertSelective = integralConsumeMapper.insertSelective(integralConsume);
			if(insertSelective >0){
				return true;
			}
		}
		return false;
	}
	
	

	@Override
	public void exportConsumeInfoExcel(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
		List<IntegralConsume> list = integralConsumeMapper.selectIntegralConsume(map);
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFFont createFont = workbook.createFont();
		createFont.setFontHeightInPoints((short)12);//设置字体
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.LEFT); //内容居左
		
		XSSFCellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setAlignment(HorizontalAlignment.CENTER);//标题水平居中
		cellStyle1.setFont(createFont);
		
		XSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth(0,5000);
		sheet.setColumnWidth(1,7000);
		sheet.setColumnWidth(2,7000);
		sheet.setColumnWidth(3,7000);
		sheet.setColumnWidth(4,9000);
		sheet.setColumnWidth(5,7000);
		sheet.setColumnWidth(6,4000);
		sheet.setColumnWidth(7,4000);
		sheet.setColumnWidth(8,7000);
		XSSFRow row0 = sheet.createRow(0);
		
		XSSFCell title0 = row0.createCell(0);
		title0.setCellValue("用户名");
		title0.setCellStyle(cellStyle1);
		
		XSSFCell title1 = row0.createCell(1);
		title1.setCellValue("采购单位");
		title1.setCellStyle(cellStyle1);
		
		XSSFCell title2 = row0.createCell(2);
		title2.setCellValue("积分名称");
		title2.setCellStyle(cellStyle1);
		
		XSSFCell title3 = row0.createCell(3);
		title3.setCellValue("订单编号");
		title3.setCellStyle(cellStyle1);
		
		XSSFCell title4 = row0.createCell(4);
		title4.setCellValue("电商订单编号");
		title4.setCellStyle(cellStyle1);
		
		XSSFCell title5 = row0.createCell(5);
		title5.setCellValue("供应商");
		title5.setCellStyle(cellStyle1);
		
		XSSFCell title6 = row0.createCell(6);
		title6.setCellValue("金额");
		title6.setCellStyle(cellStyle1);
		
		XSSFCell title7 = row0.createCell(7);
		title7.setCellValue("订单状态");
		title7.setCellStyle(cellStyle1);
		
		XSSFCell title8 = row0.createCell(8);
		title8.setCellValue("下单时间");
		title8.setCellStyle(cellStyle1);

		Map<String, String> orderstatus = orderStatusConfig.getOrderstatus();
		for(int i=0; i<list.size(); i++){
			String realName = list.get(i).getRealName();
			String depName = list.get(i).getDepName();
			String integralName = list.get(i).getIntegralName();
			String sn = list.get(i).getSn();
			String emallSn = list.get(i).getEmallSn();
			String supplierName = list.get(i).getSupplierName();
			BigDecimal total = list.get(i).getTotal();
			String orderCreatedAt = list.get(i).getOrderCreatedAt();
			
			String ordersState = list.get(i).getOrdersState();
			ordersState = orderstatus.get(ordersState);
			
			XSSFRow row = sheet.createRow(i+1);
			XSSFCell createCell0 = row.createCell(0);
			createCell0.setCellValue(realName ==null? "": realName);
			createCell0.setCellStyle(cellStyle);
			
			XSSFCell createCell1 = row.createCell(1);
			createCell1.setCellValue(depName ==null? "": depName);
			createCell1.setCellStyle(cellStyle);
			
			XSSFCell createCell2 = row.createCell(2);
			createCell2.setCellValue(integralName ==null? "": integralName);
			createCell2.setCellStyle(cellStyle);
			
			XSSFCell createCell3 = row.createCell(3);
			createCell3.setCellValue(sn ==null? "": sn);
			createCell3.setCellStyle(cellStyle);
			
			XSSFCell createCell4 = row.createCell(4);
			createCell4.setCellValue(emallSn ==null? "": emallSn);
			createCell4.setCellStyle(cellStyle);
			
			XSSFCell createCell5 = row.createCell(5);
			createCell5.setCellValue(supplierName ==null? "": supplierName);
			createCell5.setCellStyle(cellStyle);
			
			XSSFCell createCell6 = row.createCell(6);
			createCell6.setCellValue(total == null ? "" : total + "");
			createCell6.setCellStyle(cellStyle);
			
			XSSFCell createCell7 = row.createCell(7);
			createCell7.setCellValue(ordersState ==null? "": ordersState);
			createCell7.setCellStyle(cellStyle);
			
			XSSFCell createCell8 = row.createCell(8);
			createCell8.setCellValue(orderCreatedAt ==null? "": orderCreatedAt);
			createCell8.setCellStyle(cellStyle);
		}
		ServletOutputStream fileOut=null;
		try{
			fileOut=response.getOutputStream();
			String fileName = new String("积分消费明细".getBytes("UTF-8"),"ISO-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");
			fileOut=response.getOutputStream();
			workbook.write(fileOut);
		}catch(Exception e){	
			e.printStackTrace();
		}finally{
			if (fileOut != null) {
				try{
					fileOut.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<IntegralUser> findIntegralPrompt(Long userId) {
		return integralUserMapper.findIntegralPrompt(userId);
	}

	@Override
	public int updateIntegralPrompt(Long userId, Long activityId) {
		return integralUserMapper.updateIntegralPrompt(userId, activityId);
	}
}
