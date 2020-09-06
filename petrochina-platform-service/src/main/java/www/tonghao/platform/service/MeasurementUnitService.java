package www.tonghao.platform.service;

import java.util.List;
import java.util.Map;

import www.tonghao.platform.entity.MeasurementUnit;
import www.tonghao.service.common.base.BaseService;

public interface MeasurementUnitService extends BaseService<MeasurementUnit> {

	/**  
	 * <p>Title: saveOrUpdate</p>  
	 * <p>Description: 保存或修改计量单位</p>  
	 * @author Yml  
	 * @param measurementUnit
	 * @return  
	 */  
	int saveOrUpdate(MeasurementUnit measurementUnit);

	List<MeasurementUnit> find(Map<String, Object> map);

}
