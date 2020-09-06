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
import www.tonghao.service.common.entity.DictionaryData;
import www.tonghao.service.common.entity.DictionaryType;
import www.tonghao.service.common.service.DictionaryDataService;
import www.tonghao.service.common.service.DictionaryTypeService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/dictionaryData")
@Api(value = "DictionaryDataController", description = "字典数据api")
public class DictionaryDataController {

	@Autowired
	private DictionaryDataService dictionaryDataService;
	
	@Autowired
	private DictionaryTypeService dictionaryTypeService;

	@ApiOperation(value = "字典数据-分页查询列表", notes = "获取字典类型数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "int", paramType = "query"), 
		@ApiImplicitParam(name = "type", value = "类型id", required = true, dataType = "int", paramType = "query"), 
	})
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public PageInfo<DictionaryData> list(@ModelAttribute PageBean page, @ModelAttribute DictionaryData entity) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<DictionaryData> list = dictionaryDataService.findList(entity);
		return new PageInfo<>(list);
	}

	@ApiOperation(value = "字典数据-根据id查询")
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public DictionaryData getById(Long id) {
		return dictionaryDataService.selectByKey(id);
	}
	
	@ApiOperation(value = "字典数据-保存")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody DictionaryData entity) {
		return dictionaryDataService.saveEntity(entity);
	}

	@ApiOperation(value = "字典数据-修改")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Map<String, Object> update(@RequestBody DictionaryData entity) {
		return dictionaryDataService.updateEntity(entity);
	}

	@ApiOperation(value = "字典数据-删除")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Map<String, Object> delete(Long id) {
		return dictionaryDataService.deleteEntity(id);
	}

	@ApiOperation(value = "获取字典类型树")
	@RequestMapping(value = "/getDictionaryTypeTree", method = RequestMethod.GET)
	public List<DictionaryType> getDictionaryTypeTree(){
		return dictionaryDataService.getDictionaryTypeTree();
	}
}
