package www.tonghao.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.Pays;
import www.tonghao.service.common.base.BaseMapper;

public interface PaysMapper extends BaseMapper<Pays> {
	List<Pays> getByContractId(@Param("contractId")String contractId);
}