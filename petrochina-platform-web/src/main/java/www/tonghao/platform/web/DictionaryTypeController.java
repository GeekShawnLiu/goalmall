package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.DictionaryType;
import www.tonghao.service.common.service.DictionaryTypeService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/dictionaryType")
@Api(value="dictionaryTypeController",description="字典类型api")
public class DictionaryTypeController {

	@Autowired
	private DictionaryTypeService dictionaryTypeService;

	@ApiOperation(value = "字典类型-分页查询列表", notes = "获取字典类型数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "int", paramType = "query"), 
	})
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public PageInfo<DictionaryType> list(@ModelAttribute PageBean page, @ModelAttribute DictionaryType entity) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<DictionaryType> list = dictionaryTypeService.findList(entity);
		return new PageInfo<>(list);
	}
	
	@ApiOperation(value = "字典类型-根据id查询")
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public DictionaryType getById(Long id) {
		return dictionaryTypeService.selectByKey(id);
	}

	@ApiOperation(value = "字典类型-保存")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody DictionaryType entity) {
		return dictionaryTypeService.saveEntity(entity);
	}

	@ApiOperation(value = "字典类型-修改")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Map<String, Object> update(@RequestBody DictionaryType entity) {
		return dictionaryTypeService.updateEntity(entity);
	}

	@ApiOperation(value = "字典类型-删除")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Map<String, Object> delete(Long id) {
		return ResultUtil.resultMessage(dictionaryTypeService.delete(id), "");
	}
}
