package www.tonghao.service.common.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.CommoditiesParameter;

public interface CommoditiesParameterMapper extends BaseMapper<CommoditiesParameter> {

	void getByCommoditiesId(@Param("commoditiesId") Long commoditiesId);

	List<CommoditiesParameter> find(HashMap<String, Object> map);
}