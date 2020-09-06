package www.tonghao.platform.mapper;

import java.util.List;

import www.tonghao.platform.entity.DealContractTpl;
import www.tonghao.service.common.base.BaseMapper;


public interface DealContractTplMapper extends BaseMapper<DealContractTpl> {
	
	List<DealContractTpl> find();
}