package www.tonghao.mall.web.controller.supplier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.Bill;
import www.tonghao.mall.entity.BillDetail;
import www.tonghao.mall.entity.Orders;
import www.tonghao.mall.service.BillDetailService;
import www.tonghao.mall.service.BillService;
import www.tonghao.mall.service.OrdersService;
import www.tonghao.service.common.entity.MappingArea;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.MappingAreaService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 采购对账
 * @author developer001
 *
 */
@Api(value="purchaserBillController",description="供应商对账")
@RestController
@RequestMapping("/supplier/bill")
public class BillSupplierController {
	
	@Autowired
	private BillService billService;
	@Autowired
	private BillDetailService billDetailService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private MappingAreaService mappingAreaService;
	
	@RequestMapping("/execl")
	public void execl() {
		File file=new File("F:\\sms2\\hei.xlsx");
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = workbook.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum();
		System.out.println(lastRowNum);
		Example example=new Example(MappingArea.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andLike("areaName", "%黑龙江-大兴安岭地区%");
		List<MappingArea> selectByExample = mappingAreaService.selectByExample(example);
		System.out.println(selectByExample.size());
		for (MappingArea mappingArea : selectByExample) {
			ugs:
			for (int j = 0; j < lastRowNum; j++) {
				XSSFRow row = sheet.getRow(j);
				String stringCellValue = row.getCell(3).getStringCellValue();
				String[] split = stringCellValue.split("-");
				if(split.length>=5) {
					if(stringCellValue.contains("中国-"+mappingArea.getAreaName())) {
						String ids = row.getCell(2).getStringCellValue();
						String id = ids.replace("100000-", "");
						System.out.println(ids+"-=-="+stringCellValue);
						mappingArea.setMappingCode(id);
						mappingAreaService.updateNotNull(mappingArea);
						break ugs;
					}
				}
			}
		}
		
			
	}
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="title",value="账单名称/标题",dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="状态",dataType="string",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Bill> getPage(@ModelAttribute PageBean page
			,String title,String status, HttpServletRequest request){
		PageHelper.startPage(page.getPage(), page.getRows());
		
		Users user = UserUtil.getUser(request);
		
		Example example=new Example(Bill.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("createUserId", user.getId());
		if(StringUtils.isNotEmpty(title)){
			createCriteria.andLike("title", "%"+title+"%");
		}
		if(StringUtils.isNotEmpty(status)){
			createCriteria.andEqualTo("status", Integer.parseInt(status));
		}
		createCriteria.andEqualTo("isDelete", 0);
		List<Bill> list = billService.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)){
			for (Bill bill : list) {
				Example example_count=new Example(BillDetail.class);
				Criteria createCriteria_count = example_count.createCriteria();
				createCriteria_count.andEqualTo("billId", bill.getId());
				long count = billDetailService.selectCountByExample(example_count);
				bill.setNumber(count);
			}
		}
		
		
		return new PageInfo<Bill>(list);
	}
	@ApiOperation(value="供应商查看对账单详情",notes="供应商查看对账单详情Api")
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Bill getById(Long id){
		Bill bill = billService.selectByKey(id);
		if(bill!=null){
			Example example=new Example(BillDetail.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("billId", bill.getId());
			List<BillDetail> BillDetailList = billDetailService.selectByExample(example);
		    if(!CollectionUtils.isEmpty(BillDetailList)){
		    	for (BillDetail billDetail : BillDetailList) {
		    		Orders order = ordersService.selectByKey(billDetail.getOrderId());
		    		billDetail.setOrders(order);
		    	}
		    }
		    bill.setDetails(BillDetailList);
		}
		return bill;
	}
	
	
	@ApiOperation(value="获取采购人",notes="获取采购人Api")
	@RequestMapping(value="/getPurchaser",method=RequestMethod.GET)
	public List<Orders> getPurchaser(HttpServletRequest request){
		List<Orders> ordersByDistinct=null;
		Users user = UserUtil.getUser(request);
		if(user!=null){
			ordersByDistinct = ordersService.getOrdersByDistinct(user.getTypeId());
		}
		return ordersByDistinct;
	}
	
	@ApiOperation(value="供应商创建对账单",notes="供应商创建对账单Api")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Map<String, Object> addBid(@ModelAttribute Bill bill, HttpServletRequest request){
		Users user = UserUtil.getUser(request);
		if(user!=null){
			bill.setCreateUserId(user.getId());
			bill.setCreateUserName(user.getRealName());
			int saveBill = billService.saveBill(bill);
			return ResultUtil.result(saveBill, 0);
		}
		return null;
	}
	
	@ApiOperation(value="获取订单",notes="获取订单api")
	@RequestMapping(value="/getOrder",method=RequestMethod.GET)
	public List<Orders> getOrder(Long userId, HttpServletRequest request){
		List<Orders> orderList=null;
		Users user = UserUtil.getUser(request);
		if(user!=null){
			Example example=new Example(Orders.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("supplierId", user.getTypeId());
			createCriteria.andEqualTo("isDelete", 0);
			createCriteria.andEqualTo("ordersState", "completed");
			createCriteria.andEqualTo("billStatus", 0);
			createCriteria.andEqualTo("userId", userId);
			createCriteria.andEqualTo("payway","账期支付");
			orderList = ordersService.selectByExample(example);
		}
		return orderList;
	}
	
	
	@ApiOperation(value="删除账单",notes="删除账单api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
		
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int delete = billService.deleteBill(id);
		return ResultUtil.result(delete, 0);
	}
	
	
	@ApiOperation(value="删除账单明细",notes="删除账单明细api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/deleteDetail",method=RequestMethod.DELETE)
	public Map<String, Object> deleteDetail(Long id){
		int delete = billService.deleteBillDetail(id);
		return ResultUtil.result(delete, 0);
	}
	
	
	@ApiOperation(value="账单修改",notes="供应商创建对账单Api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String, Object> update(@ModelAttribute Bill bill){
		int updateBill = billService.updateBill(bill);
		return ResultUtil.result(updateBill, 0);
	}
	
	@ApiOperation(value="添加对账单明细",notes="添加对账单明细Api")
	@RequestMapping(value="/saveDetail",method=RequestMethod.POST)
	public Map<String, Object> saveDetail(@ModelAttribute BillDetail billDetail){
		int save_count = billDetailService.saveDetail(billDetail);
		return ResultUtil.result(save_count, 0);
	}
	
}
