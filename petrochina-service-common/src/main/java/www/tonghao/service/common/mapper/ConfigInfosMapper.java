package www.tonghao.service.common.mapper;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ConfigInfos;

public interface ConfigInfosMapper extends BaseMapper<ConfigInfos> {
	
	ConfigInfos findByEnergySavingProductId(@Param("energySavingProductId") Long energySavingProductId);
}