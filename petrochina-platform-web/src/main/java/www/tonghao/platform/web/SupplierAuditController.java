package www.tonghao.platform.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.service.SupplierAuditService;
import www.tonghao.service.common.service.SuppliersService;

/**
 * @Description:供应商审核
 * @date 2019年6月24日
 */
@Api(value="supplierAuditController",description="供应商审核")
@RestController
@RequestMapping("/supplierAudit")
public class SupplierAuditController {
	
	@Autowired
	private SupplierAuditService supplierAuditService;
	
	@Autowired
	private SuppliersService suppliersService;
	
	@ApiOperation(value="列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="nick",value="简称",required=false,dataType="string",paramType="query"),
		@ApiImplicitParam(name="status",value="状态0未审核，1通过，2未通过",required=false,dataType="int",paramType="query"),
	})
	@GetMapping("/list")
	public PageInfo<Suppliers> list(PageBean page, String supplierName, String nick, Integer status){
		Suppliers supplier = new Suppliers();
		if(StringUtils.isNotBlank(supplierName)){
			supplier.setName(supplierName);
		}
		if(StringUtils.isNotBlank(nick)){
			supplier.setNickName(nick);
		}
		if(status !=null){
			supplier.setStatus(status);
		}
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> queryfilter = new HashMap<>();
		queryfilter.put("orderByCondition", "s.submission_time DESC");
		supplier.setQueryfilter(queryfilter);
		List<Suppliers> list = suppliersService.findListByEntity(supplier);
		return new PageInfo<Suppliers>(list);
	}
	
	@ApiOperation(value="审核")
	@ApiImplicitParams({
		@ApiImplicitParam(name="reason",value="理由",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="supplierId",value="供应商id",required=false,dataType="long",paramType="query"),
		@ApiImplicitParam(name="status",value="1通过，2不通过",required=false,dataType="int",paramType="query"),
		@ApiImplicitParam(name="code",value="供应商code",required=false,dataType="String",paramType="query"),
	})
	@GetMapping("/audit")
	public Map<String, Object> audit(String reason, Long supplierId, Integer status, String code){
		Map<String, Object> audit = supplierAuditService.audit(reason, supplierId, status, code);
		return audit;
	}
	
	/**
	 * @Description:获取供应商信息（包含用户和店铺）
	 * @date 2019年6月24日
	 */
	@ApiOperation(value="获取供应商信息（包含用户和店铺）")
	@ApiImplicitParams({
		@ApiImplicitParam(name="supplierId",value="供应商id",required=false,dataType="long",paramType="query"),
	})
	@GetMapping("/getSupplierInfo")
	public Suppliers getSupplierInfo(Long supplierId){
		Suppliers selectSupplierInfo = suppliersService.selectSupplierInfo(supplierId);
		return selectSupplierInfo;
	}
}
