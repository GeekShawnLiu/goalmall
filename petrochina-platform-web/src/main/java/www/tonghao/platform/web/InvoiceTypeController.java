package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.platform.entity.InvoiceType;
import www.tonghao.platform.service.InvoiceTypeService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  

* <p>Title: InvoiceTypeController</p>  

* <p>Description: 发票类型管理控制类</p>  

* @author Yml  

* @date 2018年11月6日  

*/  
@Api(value="invoiceTypeController",description="发票类型管理api")
@RestController
@RequestMapping("/invoiceTypeController")
public class InvoiceTypeController {

	@Autowired
	private InvoiceTypeService invoiceTypeService;
	
	/**  
	 * <p>Title: list</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param invoiceType
	 * @param page
	 * @return  
	 */  
	@ApiOperation(value="分页查询列表",notes="获取分页查询列表数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public PageInfo<InvoiceType> list(@ModelAttribute InvoiceType invoiceType, @ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example = new Example(InvoiceType.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("isFrozen", invoiceType.getIsFrozen());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fullName", invoiceType.getFullName());
		CriteriaLikeUtil.criteriaLike(criteria, map);
		List<InvoiceType> list = invoiceTypeService.selectByExample(example);
		return new PageInfo<InvoiceType>(list);
	}
	
	
	/**  
	 * <p>Title: save</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param invoiceType
	 * @return  
	 */  
	@ApiOperation(value="添加数据",notes="添加数据api")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String,Object> save(@RequestBody InvoiceType invoiceType){
		int result = invoiceTypeService.saveInvoiceType(invoiceType);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: update</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param invoiceType
	 * @return  
	 */  
	@ApiOperation(value="更新数据",notes="更新数据api")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map<String,Object> update(@RequestBody InvoiceType invoiceType){
		invoiceType.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		int result = invoiceTypeService.updateNotNull(invoiceType);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@ApiOperation(value="根据id删除数据",notes="根据id删除数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Map<String, Object> delete(Long id){
		int result = invoiceTypeService.delete(id);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**  
	 * <p>Title: getById</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param id
	 * @return  
	 */  
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public InvoiceType getById(Long id){
		InvoiceType invoiceType = invoiceTypeService.selectByKey(id);
		return invoiceType;
	}
}
