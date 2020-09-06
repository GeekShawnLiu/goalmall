package www.tonghao.mall.web.controller.purchaser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import www.tonghao.mall.service.BillDetailService;
import www.tonghao.mall.service.BillService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 采购对账
 * @author developer001
 *
 */
@Api(value="purchaserBillController",description="采购人对账")
@RestController
@RequestMapping("/purchaser/bill")
public class BillController {
	
	@Autowired
	private BillService billService;
	@Autowired
	private BillDetailService billDetailService;
	
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
		createCriteria.andEqualTo("purchaserUid", user.getId());
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
	
	@ApiOperation(value="采购人审核通过",notes="采购人审核通过Api")
	@RequestMapping(value="/dillCheck",method=RequestMethod.GET)
	public Map<String, Object> dillCheck(Long id){
		Bill bill = billService.selectByKey(id);
		bill.setStatus(2);
		int updateNotNull = billService.updateNotNull(bill);
		return ResultUtil.result(updateNotNull, 0);
	}
	
	@ApiOperation(value="采购人退回",notes="采购人退回Api")
	@RequestMapping(value="/dillBack",method=RequestMethod.GET)
	public Map<String, Object> dillBack(Long id){
		Bill bill = billService.selectByKey(id);
		bill.setStatus(3);
		int updateNotNull = billService.updateNotNull(bill);
		return ResultUtil.result(updateNotNull, 0);
	}
}
