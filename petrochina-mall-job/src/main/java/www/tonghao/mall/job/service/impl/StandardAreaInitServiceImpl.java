package www.tonghao.mall.job.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.standard.call.AreaCountyApi;
import www.tonghao.mall.api.standard.resultwrap.CityRes;
import www.tonghao.mall.api.standard.resultwrap.CountyRes;
import www.tonghao.mall.api.standard.resultwrap.ProvincesRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.StandardAreaInitService;
import www.tonghao.mall.job.util.StandardUtilApi;
import www.tonghao.service.common.entity.MappingArea;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.MappingAreaMapper;
import www.tonghao.service.common.service.SuppliersService;

@Service("standardAreaInitService")
public class StandardAreaInitServiceImpl implements StandardAreaInitService{

	@Autowired
	private MappingAreaMapper mappingAreaMapper;
	
	@Autowired
	private SuppliersService suppliersService;
	
	@Override
	public void StandardAreaInitJob() {
		Suppliers sup = new Suppliers();
		sup.setCode(Constant.MALL_DL_CODE);
		Suppliers supplier = suppliersService.selectEntityOne(sup);
		if(supplier!=null){
			ProvincesRes areaProvinces = StandardUtilApi.areaProvincesApi(supplier.getCode());
			if(areaProvinces.isSuccess()){
				Map<String, String> result = areaProvinces.getResult();
				if(!MapUtils.isEmpty(result)){
					Iterator<Entry<String, String>> iterator = result.entrySet().iterator();
					while(iterator.hasNext()){
						
						Entry<String, String> next = iterator.next();
						String areaProvinces_value = next.getKey();//一级地区名称
						String areaProvinces_id = next.getValue();//一级地区id
						
						CityRes areaCities = StandardUtilApi.areaCitiesApi(supplier.getCode(), areaProvinces_id);
						if(areaCities.isSuccess()){
							Map<String, String> resultCities= areaCities.getResult();
							if(!MapUtils.isEmpty(resultCities)){
								Iterator<Entry<String, String>> cities_iterator = resultCities.entrySet().iterator();
								while(cities_iterator.hasNext()){
									Entry<String, String> cities_next = cities_iterator.next();
									String cities_value = cities_next.getKey();//二级地址名称
									String cities_id = cities_next.getValue();//二级地址id
									
									
									CountyRes areaCounty = StandardUtilApi.areaCountyApi(supplier.getCode(), cities_id);
									if(areaCounty.isSuccess()){
										Map<String, String> resultCounty= areaCounty.getResult();
										if(!MapUtils.isEmpty(resultCounty)){
											Iterator<Entry<String, String>> county_iterator = resultCounty.entrySet().iterator();
											while(county_iterator.hasNext()){
												Entry<String, String> county_next = county_iterator.next();
												String county_value = county_next.getKey();//三级地址名称
												String county_id = county_next.getValue();//三级地址id
												
												MappingArea area=new MappingArea();
												area.setAreaName(areaProvinces_value+"_"+cities_value+"_"+county_value);
												area.setMappingCode(areaProvinces_id+"_"+cities_id+"_"+county_id);
												area.setEmallCode(supplier.getCode());
												area.setCreatedAt(DateUtilEx.timeToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
												area.setUpdatedAt(DateUtilEx.timeToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
												mappingAreaMapper.insertSelective(area);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
