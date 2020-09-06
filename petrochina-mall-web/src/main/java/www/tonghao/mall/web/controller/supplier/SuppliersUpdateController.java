package www.tonghao.mall.web.controller.supplier;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.SuppliersUpdate;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.SuppliersUpdateService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/suppliersUpdate")
public class SuppliersUpdateController {

	@Autowired
	private SuppliersUpdateService suppliersUpdateService;
	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	
	@ApiOperation(value = "供应商变更记录列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public PageInfo<SuppliersUpdate> list(HttpServletRequest request, @ModelAttribute PageBean page){
		Users user = UserUtil.getUser(request);
		if(user != null){
			PageHelper.startPage(page.getPage(), page.getRows());
			Map<String, Object> map = new HashMap<>();
			if(user.getType() == 4 && user.getTypeId() != null){
				map.put("supplierId", user.getTypeId());
			}
			List<SuppliersUpdate> list = suppliersUpdateService.findList(map);
			return new PageInfo<SuppliersUpdate>(list);
		}
		return null;
	}
	
	@ApiOperation(value = "查询变更记录详情")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public SuppliersUpdate list(HttpServletRequest request, Long id){
		Users user = UserUtil.getUser(request);
		if(user != null ){
			SuppliersUpdate findById = suppliersUpdateService.findById(id);
			return findById;
		}
		return null;
	}
	
	@ApiOperation(value = "查询变更前的信息")
	@RequestMapping(value = "/getOldInfo", method = RequestMethod.GET)
	public Suppliers getOldInfo(HttpServletRequest request, Long supplierId){
		Users user = UserUtil.getUser(request);
		if(user != null){
			Suppliers oldInfo = suppliersUpdateService.getOldInfo(supplierId);
			return oldInfo;
		}
		return null;
	}
	
	@ApiOperation(value = "获取供应商最近一次变更记录")
	@RequestMapping(value = "/getLastUpdateInfo", method = RequestMethod.GET)
	public SuppliersUpdate getLastUpdateInfo(HttpServletRequest request, Long supplierId){
		Users user = UserUtil.getUser(request);
		if(user != null){
			Map<String, Object> map = new HashMap<>();
			map.put("supplierId", supplierId);
			List<SuppliersUpdate> list = suppliersUpdateService.findList(map);
			if(CollectionUtils.isNotEmpty(list)){
				return list.get(0);
			}
		}
		return null;
	}
	
	@ApiOperation(value = "提交申请")
	@RequestMapping(value = "/submitUpdate", method = RequestMethod.POST)
	public Map<String, Object> submitUpdate(HttpServletRequest request, @RequestBody SuppliersUpdate suppliersUpdate){
		Users user = UserUtil.getUser(request);
		if(user != null && user.getType() == 4 && user.getTypeId() != null){
			suppliersUpdate.setSupplierId(user.getTypeId());
			return suppliersUpdateService.submitUpdate(suppliersUpdate);
		}
		return ResultUtil.error("登录信息异常");
	}
	
	@ApiOperation(value = "审核")
	@RequestMapping(value = "/auditUpdate", method = RequestMethod.POST)
	public Map<String, Object> auditUpdate(HttpServletRequest request, @RequestBody SuppliersUpdate suppliersUpdate){
		Users user = UserUtil.getUser(request);
		if(user != null){
			return suppliersUpdateService.auditUpdate(suppliersUpdate);
		}
		return ResultUtil.error("登录信息异常");
	}
	
	/**
	 * @Description:经营范围
	 * @date 2019年7月9日
	 */
	@ApiOperation(value="获取品目")
	@RequestMapping(value="/getCatalogsTree",method={RequestMethod.GET})
	public List<PlatformCatalogs> getCatalogsTree(Long supplierId){
		return suppliersUpdateService.getCatalogsTree(supplierId);
	}
}
