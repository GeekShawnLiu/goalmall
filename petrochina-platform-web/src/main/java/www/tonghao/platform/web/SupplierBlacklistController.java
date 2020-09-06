package www.tonghao.platform.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
import www.tonghao.platform.entity.SupplierBlacklist;
import www.tonghao.platform.entity.SupplierIntegrity;
import www.tonghao.platform.service.SupplierBlacklistService;
import www.tonghao.platform.service.SupplierIntegrityService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/supplierBlacklist")
public class SupplierBlacklistController {

	@Autowired
	private SupplierBlacklistService supplierBlacklistService;
	@Autowired
	private SupplierIntegrityService supplierIntegrityService;
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<SupplierBlacklist> test(@ModelAttribute PageBean page,String supplierName){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(SupplierBlacklist.class);
		Criteria createCriteria = example.createCriteria();
		if(!StringUtils.isBlank(supplierName)){
			createCriteria.andLike("supplierName", "%"+supplierName+"%");
		}
		List<SupplierBlacklist> list = supplierBlacklistService.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)){
			for (SupplierBlacklist supplierBlacklist : list) {
				Example example_supplier=new Example(SupplierIntegrity.class);
				Criteria createCriteria_supplier= example_supplier.createCriteria();
				createCriteria_supplier.andEqualTo("supplierId", supplierBlacklist.getSupplierId());
				List<SupplierIntegrity> supplier_list = supplierIntegrityService.selectByExample(example_supplier);
			    BigDecimal bigDecimal=new BigDecimal("0");
				if(!CollectionUtils.isEmpty(supplier_list)){
			    	for (SupplierIntegrity supplierIntegrity : supplier_list) {
			    		bigDecimal=bigDecimal.add(new BigDecimal(supplierIntegrity.getDeductingScore()+""));
					}
			    }
				BigDecimal subtract = new BigDecimal("100").subtract(bigDecimal);
				supplierBlacklist.setScore(subtract.doubleValue());
			}
		}
		return new PageInfo<SupplierBlacklist>(list);
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String, Object> save(@RequestBody SupplierBlacklist integrity,HttpServletRequest request) throws ParseException{
		integrity.setCreateAt(DateUtilEx.timeFormat(new Date()));
		Example example=new Example(SupplierBlacklist.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("supplierId", integrity.getSupplierId());
		List<SupplierBlacklist> supplierBlacklist = supplierBlacklistService.selectByExample(example);
		if(!CollectionUtils.isEmpty(supplierBlacklist)){
			return ResultUtil.result(0, 0);
		}else{
			int save = supplierBlacklistService.save(integrity);
			return ResultUtil.result(save, 0);
		}
	}
	
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public SupplierBlacklist getById(Long id){
		SupplierBlacklist supplier_Blacklist=null;
		Example example=new Example(SupplierBlacklist.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("supplierId", id);
		List<SupplierBlacklist> supplierBlacklist = supplierBlacklistService.selectByExample(example);
		if(!CollectionUtils.isEmpty(supplierBlacklist)){
			supplier_Blacklist = supplierBlacklist.get(0);
			Example example_supplier=new Example(SupplierIntegrity.class);
			Criteria createCriteria_supplier= example_supplier.createCriteria();
			createCriteria_supplier.andEqualTo("supplierId", supplier_Blacklist.getSupplierId());
			List<SupplierIntegrity> supplier_list = supplierIntegrityService.selectByExample(example_supplier);
		    BigDecimal bigDecimal=new BigDecimal("0");
			if(!CollectionUtils.isEmpty(supplier_list)){
		    	for (SupplierIntegrity supplierIntegrity : supplier_list) {
		    		bigDecimal=bigDecimal.add(new BigDecimal(supplierIntegrity.getDeductingScore()+""));
				}
		    }
			BigDecimal subtract = new BigDecimal("100").subtract(bigDecimal);
			supplier_Blacklist.setScore(subtract.doubleValue());
			
		}
		return supplier_Blacklist;
	}
	
}
