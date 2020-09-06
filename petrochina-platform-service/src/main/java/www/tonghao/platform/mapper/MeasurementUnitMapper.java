package www.tonghao.platform.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.platform.entity.MeasurementUnit;
import www.tonghao.service.common.base.BaseMapper;


public interface MeasurementUnitMapper extends BaseMapper<MeasurementUnit> {

	List<MeasurementUnit> find(Map<String, Object> map);
}