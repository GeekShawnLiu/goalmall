package www.tonghao.service.common.mapper;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Protocol;

import java.util.List;
import java.util.Map;

public interface ProtocolMapper extends BaseMapper<Protocol> {


    /**
     * 条件查询
     *
     * @param map
     * @return
     */
    List<Protocol> selectByMap(Map<String, Object> map);
}