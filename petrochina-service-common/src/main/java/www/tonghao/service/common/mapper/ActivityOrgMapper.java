package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ActivityOrg;

public interface ActivityOrgMapper extends BaseMapper<ActivityOrg> {
	
	List<ActivityOrg> selectOrgByActivityId(Map<String, Object> map);
}