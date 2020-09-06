package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.SuppliersUpdate;

public interface SuppliersUpdateMapper extends BaseMapper<SuppliersUpdate>{

	List<SuppliersUpdate> findList(Map<String, Object> map);
}
