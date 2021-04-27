package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Protocol;

import java.util.List;
import java.util.Map;

public interface ProtocolService extends BaseService<Protocol> {

    List<Protocol> selectByMap(Map<String, Object> map);

    Map<String, Object> saveEntity(Protocol protocol);

    Map<String, Object> updateEntity(Protocol protocol);

    Map<String, Object> deleteEntity(Long id);

    Map<String, Object> updateStatus(Protocol protocol);

}
