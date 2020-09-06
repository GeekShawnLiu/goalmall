package www.tonghao.mall.web.controller.mall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.JsonUtil;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.service.AreasService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


@Api(value="mallAreasController",description="商城地址api")
@RestController(value="mallAreasController")
@RequestMapping(value="/area")
public class MallAreasController {
	
	@Autowired
	private AreasService areasService;
	
	
	/**
	 * 省
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/ajax_provinces.json",method = RequestMethod.GET)
	public Map<String,Object> ajaxProvinces(HttpServletResponse response) {
		 List<Areas> list = findListByDepth(2,null);
		 List<Map<String, Object>> data = dataWarpper(list, 2);
		 Map<String,Object> result = Maps.newHashMap();
		result.put("provinces", JsonUtil.toJson(data));
		return result;
	}
	
	/**
	 * 市
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/ajax_cities.json",method = RequestMethod.GET)
	public @ResponseBody
	Map<String,Object> ajaxCities(HttpServletResponse response) {
		List<Areas> list = findListByDepth(3,null);
		List<Map<String, Object>> data = dataWarpper(list, 3);
		Map<String,Object> result = Maps.newHashMap();
		result.put("cities", JsonUtil.toJson(data));
		return result;
	}
	
	/**
	 * 地区
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/ajax_counties.json",method = RequestMethod.GET)
	public @ResponseBody
	Map<String,Object> ajaxCounties(HttpServletResponse response) {
		 List<Areas> list = findListByDepth(4,null);
		 List<Map<String, Object>> data = dataWarpper(list, 4);
		 Map<String,Object> result = Maps.newHashMap();
		result.put("areas", JsonUtil.toJson(data));
		return result;
	}
	
	/**
	 * 级联加载
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/load_cascade",method = RequestMethod.POST)
	public @ResponseBody List<Areas> loadCascade(Long id){
		Example example = new Example(Areas.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", id);
		return areasService.selectByExample(example);
	}
	
	private List<Areas> findListByDepth(Integer depth,Long id){
		Areas areas = new Areas();
		if(id!=null){
			areas.setId(id);
		}
		areas.setTreeDepth(depth);
		areas.setPublished(true);
		return areasService.findListByEntity(areas);
	}
	
	/**
	 * 数据封装
	 * @param areas
	 * @param ancestryDepth
	 * @return
	 */
	private List<Map<String,Object>> dataWarpper(List<Areas> areas,int depth){
		 if(areas!=null&&areas.size()>0){
			 List<Map<String,Object>> data = Lists.newArrayList();
			 Map<String,Object> dataWarpper = null;
			 if(depth==2){
				 for (int i = 0; i < areas.size(); i++) {
					 dataWarpper = Maps.newLinkedHashMap();
					 Areas item = areas.get(i);
					 dataWarpper.put("id", item.getId());
					 dataWarpper.put("provinceName", item.getName());
					 dataWarpper.put("indexId", i+1);
					 data.add(dataWarpper);
				 }
			 } else if(depth==3){
				 for (int i = 0; i < areas.size(); i++) {
					 dataWarpper = Maps.newLinkedHashMap();
					 Areas item = areas.get(i);
					 Long provinceId = item.getParent()!=null?item.getParent().getId():null;
					 dataWarpper.put("id", item.getId());
					 dataWarpper.put("provinceId", provinceId);
					 dataWarpper.put("name",  item.getName());
					 data.add(dataWarpper);
				 }
			 }else if(depth==4){
				 for (int i = 0; i < areas.size(); i++) {
					 dataWarpper = Maps.newLinkedHashMap();
					 Areas item = areas.get(i);
					 Areas city = item.getParent();
					 Long cityId = city!=null?city.getId():null;
					 String cityName = city!=null?city.getName():null; 
					 
					 Areas  province = city!=null&&city.getParent()!=null?city.getParent():null;
					 Long provinceId = province!=null?province.getId():null ;
					 dataWarpper.put("id", item.getId());
					 dataWarpper.put("cityId", cityId);
					 dataWarpper.put("cityName", cityName);
					 dataWarpper.put("provinceId", provinceId);
					 dataWarpper.put("areaName",  item.getName());
					 data.add(dataWarpper);
				 }
			 }
			 return data;
		 }
		 return  null;
	}
	
	@ApiOperation(value="根据等级查询地区",notes="根据等级查询地区api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="level",value="level",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getLevel",method={RequestMethod.GET,RequestMethod.POST})
	public List<Areas> getLevel(Integer level){
		Example example=new Example(Areas.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("treeDepth", level);
		List<Areas> selectByExample = areasService.selectByExample(example);
		return selectByExample;
	}
	
	@ApiOperation(value="根据id获取下一级所有节点",notes="根据id获取下一级所有节点api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=false,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getChildrenByLevel",method={RequestMethod.GET,RequestMethod.POST})
	public List<Areas> getChildrenByLevel(Long id){
		Example example=new Example(Areas.class);
		Criteria createCriteria = example.createCriteria();
		if(id!=null){
			createCriteria.andEqualTo("parentId", id);
		}else{
			createCriteria.andEqualTo("treeDepth", 1);
		}
		List<Areas> selectByExample = areasService.selectByExample(example);
		return selectByExample;
	}
}
