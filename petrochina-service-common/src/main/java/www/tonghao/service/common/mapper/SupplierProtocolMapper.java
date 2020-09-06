package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.SupplierProtocol;

public interface SupplierProtocolMapper extends BaseMapper<SupplierProtocol> {
	
	public SupplierProtocol getSupplierProtocolByProtocolId(@Param("protocolId") Long protocolId);
	
	/**
	 * 条件查询
	 * @param map
	 * @return
	 */
	public SupplierProtocol findEntityBySupplierProtocol(Map<String, Object> map);
	
	/**
	 * 统计分析
	 * @return
	 */
	public List selectDistinctSupplier();
	
}