package www.tonghao.mall.job.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.jd.resultwrap.CityRes;
import www.tonghao.mall.api.jd.resultwrap.CountyRes;
import www.tonghao.mall.api.jd.resultwrap.ProvincesRes;
import www.tonghao.mall.api.jd.resultwrap.TownRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.JDAddressService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.service.common.entity.MappingArea;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.MappingAreaMapper;
import www.tonghao.service.common.service.SuppliersService;

@Service("jdAddressService")
public class JDAddressServiceImpl implements JDAddressService {

	@Autowired
	private MappingAreaMapper mappingAreaMapper;
	
	@Autowired
	private SuppliersService suppliersService;
	@Override
	public void executeJDAddressJob() {
		Suppliers sup = new Suppliers();
		sup.setCode(Constant.MALL_JD_CODE);
		Suppliers suppliers = suppliersService.selectEntityOne(sup);
		if(suppliers!=null){
			ProvincesRes areaProvincesApis = JdUtilApi.AreaProvincesApis();
			Map<String, String> result = areaProvincesApis.getResult();
			Iterator<Entry<String, String>> areaProvincesApis_iterator = result.entrySet().iterator();
			while (areaProvincesApis_iterator.hasNext()) {
				Entry<String, String> next = areaProvincesApis_iterator.next();
				String areaProvincesApis_key = next.getKey();  //名称  省
				String areaProvincesApis_value = next.getValue(); //id
				
				CityRes areaCitiesApis = JdUtilApi.AreaCitiesApis(areaProvincesApis_value);
				if(areaCitiesApis.isSuccess()){
					Map<String, String> areaCities_result = areaCitiesApis.getResult();
					Iterator<Entry<String, String>> areaCities_iterator = areaCities_result.entrySet().iterator();
					while (areaCities_iterator.hasNext()) {
						Entry<String, String> areaCities_next = areaCities_iterator.next();
						String areaCities_key = areaCities_next.getKey();  // 名称市
						String areaCities_value = areaCities_next.getValue();//id
						
						
						CountyRes areaCountyApis = JdUtilApi.AreaCountyApis(areaCities_value);
						if(areaCountyApis.isSuccess()){
							Map<String, String> areaCountyApis_result = areaCountyApis.getResult();
							Iterator<Entry<String, String>> areaCountyApis_iterator = areaCountyApis_result.entrySet().iterator();
							while (areaCountyApis_iterator.hasNext()) {
								Entry<String, String> areaCountyApis_next = areaCountyApis_iterator.next();
								String areaCountyApis_key = areaCountyApis_next.getKey();// 名称 镇
								String areaCountyApis_value = areaCountyApis_next.getValue();
								
								TownRes areaTownApis = JdUtilApi.AreaTownApis(areaCountyApis_value);
								if(areaTownApis.isSuccess()){
									Map<String, String> areaTownApis_result = areaTownApis.getResult();
									Iterator<Entry<String, String>> areaTownApis_iterator = areaTownApis_result.entrySet().iterator();
									while (areaTownApis_iterator.hasNext()) {
										Entry<String, String> areaTownApis_next = areaTownApis_iterator.next();
										String areaTownApis_key = areaTownApis_next.getKey();
										String areaTownApis_value = areaTownApis_next.getValue();
										MappingArea area=new MappingArea();
										area.setAreaName(areaProvincesApis_key+"_"+areaCities_key+"_"+areaCountyApis_key+"_"+areaTownApis_key);
										area.setMappingCode(areaProvincesApis_value+"_"+areaCities_value+"_"+areaCountyApis_value+"_"+areaTownApis_value);
										area.setEmallCode(suppliers.getCode());
										area.setCreatedAt(DateUtilEx.timeToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
										area.setUpdatedAt(DateUtilEx.timeToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
										mappingAreaMapper.insertSelective(area);
									}									
								}else{
									MappingArea area=new MappingArea();
									area.setAreaName(areaProvincesApis_key+"_"+areaCities_key+"_"+areaCountyApis_key);
									area.setMappingCode(areaProvincesApis_value+"_"+areaCities_value+"_"+areaCountyApis_value);
									area.setEmallCode(suppliers.getCode());
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
