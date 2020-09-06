package www.tonghao.service.common.mapper;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.AfterSaleAudit;

public interface AfterSaleAuditMapper extends BaseMapper<AfterSaleAudit> {
	
	public AfterSaleAudit selectByCreatedAtMax(@Param("afterSaleId") Long afterSaleId);

}
