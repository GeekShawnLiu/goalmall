package www.tonghao.platform.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.entity.MappingArea;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.service.AreasService;
import www.tonghao.service.common.service.MappingAreaService;
import www.tonghao.service.common.service.SuppliersService;


@RestController
@RequestMapping("mappingArea")
public class MappingAreaController {
	@Autowired
	private MappingAreaService mappingAreaService;
	@Autowired
	private AreasService areasService;
	@Autowired
	private SuppliersService suppliersService;
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public PageInfo<MappingArea> page(@ModelAttribute PageBean page,String supplierName,String areaName){
		PageHelper.startPage(page.getPage(), page.getRows());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("supplierName", supplierName);
		map.put("areaName", areaName);
		List<MappingArea> mappingList = mappingAreaService.selectByMappingArea(map);
		return new PageInfo<MappingArea>(mappingList);
	}
	
	@RequestMapping(value="getById",method=RequestMethod.GET)
	public MappingArea getById(Long id){
		MappingArea mappingArea = mappingAreaService.selectByKey(id);
		if(mappingArea!=null&&mappingArea.getEmallCode()!=null){
			Suppliers entity=new Suppliers();
			entity.setStatus(1);
			entity.setIsDelete((byte)0);
			entity.setCode(mappingArea.getEmallCode());
			Suppliers sup = suppliersService.selectEntityOne(entity);
			mappingArea.setSupplierName(sup.getName());
		}
		if(mappingArea.getAreaId()!=null){
			Areas areas = areasService.selectByKey(mappingArea.getAreaId());
			mappingArea.setTreeNames(areas.getTreeNames());
		}
		
		return mappingArea;
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Map<String,Object> update(@RequestBody MappingArea mappingArea){
		int updateNotNull = mappingAreaService.updateNotNull(mappingArea);
		return ResultUtil.resultMessage(updateNotNull, "");
	}
	
}
