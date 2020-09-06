package www.tonghao.mall.web.controller.purchaser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.mall.entity.Invoices;
import www.tonghao.mall.entity.ReceiverAddresses;
import www.tonghao.mall.service.InvoicesService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 发票
 * @author developer001
 *
 */
@Api(value="purchaserInvoicesController",description="采购人发票")
@RestController(value="purchaserInvoicesController")
@RequestMapping("/purchaser/invoices")
public class InvoicesController {
	
	@Autowired
	private InvoicesService invoicesService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Invoices> getPage(@ModelAttribute PageBean page, HttpServletRequest request){
		PageHelper.startPage(page.getPage(), page.getRows());
		Users user = UserUtil.getUser(request);
		if(user != null){
			Example example = new Example(Invoices.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("userId",user.getId());
			List<Invoices> list = invoicesService.selectByExample(example);
			return new PageInfo<Invoices>(list);
		}
		return null;
	}
	
	@ApiOperation(value="根据ID查询",notes="根据ID查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="ID",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Invoices getById(Long id, HttpServletRequest request){
		Invoices entity = invoicesService.selectByKey(id);
		Users user = UserUtil.getUser(request);
		if(entity!=null&&entity.getUserId().equals(user.getId())){
			return entity;
		}
		return null;
	}
	
	@ApiOperation(value="设置默认",notes="设置默认Api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="long"),
		
	})
	@RequestMapping(value="/setDefault",method=RequestMethod.PUT)
	public Map<String, Object> setDefault(Long id, HttpServletRequest request){
		Invoices entity = invoicesService.selectByKey(id);
		Users user = UserUtil.getUser(request);
		if(entity!=null&&entity.getUserId().equals(user.getId())){
			
			Example example=new Example(ReceiverAddresses.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("userId",user.getId());
			List<Invoices> list = invoicesService.selectByExample(example);
			for (Invoices receiverAddresses : list) {
				if(receiverAddresses.getIsDefault()){
					receiverAddresses.setIsDefault(false);
					invoicesService.updateNotNull(receiverAddresses);
				}
			}
			
			entity.setIsDefault(true);
			invoicesService.updateNotNull(entity);
			return ResultUtil.success("");
		}
		return ResultUtil.error("操作失败");
	}
	
	@ApiOperation(value="添加",notes="添加api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="company",value="公司名称",required=true,dataType="string"),
		@ApiImplicitParam(name="idCode",value="纳税人识别号",required=true,dataType="string"),
	})
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Map<String, Object> add(@RequestBody Invoices entity, HttpServletRequest request){
		
		if(StringUtils.isEmpty(entity.getCompany())){
			return ResultUtil.error("公司名称不能为空");
		}
		if(entity.getInvoiceType()==null){
			return ResultUtil.error("请选择发票类型");
		}
		if(StringUtils.isEmpty(entity.getIdCode())){
			return ResultUtil.error("纳税人识别号不能为空");
		}
		if(StringUtils.isEmpty(entity.getAddress())){
			return ResultUtil.error("注册地址不能为空");
		}
		if(StringUtils.isEmpty(entity.getPhone())){
			return ResultUtil.error("注册电话不能为空");
		}
		if(StringUtils.isEmpty(entity.getBank())){
			return ResultUtil.error("开户银行不能为空");
		}
		if(StringUtils.isEmpty(entity.getBankAccount())){
			return ResultUtil.error("开户账号不能为空");
		}
		Users user = UserUtil.getUser(request);
		entity.setIsDefault(entity.getIsDefault()==null?false:entity.getIsDefault());
		entity.setUserId(user.getId());
		entity.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		invoicesService.save(entity);
		return ResultUtil.success("");
	}
	
	@ApiOperation(value="修改",notes="修改api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="ID",required=true,dataType="long"),
		@ApiImplicitParam(name="company",value="公司名称",required=false,dataType="string"),
	})
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public Map<String, Object> upadte(@RequestBody Invoices entity, HttpServletRequest request){
		if(entity.getId()==null){
			return ResultUtil.error("ID不能为空");
		}
		
		if(StringUtils.isEmpty(entity.getCompany())){
			return ResultUtil.error("公司名称不能为空");
		}
		if(entity.getInvoiceType()==null){
			return ResultUtil.error("请选择发票类型");
		}
		if(StringUtils.isEmpty(entity.getIdCode())){
			return ResultUtil.error("纳税人识别号不能为空");
		}
		if(StringUtils.isEmpty(entity.getAddress())){
			return ResultUtil.error("注册地址不能为空");
		}
		if(StringUtils.isEmpty(entity.getPhone())){
			return ResultUtil.error("注册电话不能为空");
		}
		if(StringUtils.isEmpty(entity.getBank())){
			return ResultUtil.error("开户银行不能为空");
		}
		if(StringUtils.isEmpty(entity.getBankAccount())){
			return ResultUtil.error("开户账号不能为空");
		}
		
		Invoices invoices = invoicesService.selectByKey(entity.getId());
		Users user = UserUtil.getUser(request);
		if(invoices!=null&&invoices.getUserId().equals(user.getId())){
			invoicesService.updateNotNull(entity);
			return ResultUtil.success("");
		}
		return ResultUtil.error("操作失败");
	}
	
	@ApiOperation(value="移除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="long"),
	})
	@RequestMapping(value="/remove",method=RequestMethod.DELETE)
	public Map<String, Object> remove(Long id, HttpServletRequest request){
		if(id==null){
			return ResultUtil.error("ID不能为空");
		}
		Invoices invoices = invoicesService.selectByKey(id);
		Users user = UserUtil.getUser(request);
		if(invoices!=null&&invoices.getUserId().equals(user.getId())){
			invoicesService.delete(id);
			return ResultUtil.success("");
		}
		return ResultUtil.error("操作失败");
	}
}
