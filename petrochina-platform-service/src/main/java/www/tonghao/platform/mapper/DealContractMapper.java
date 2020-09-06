package www.tonghao.platform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.DealContract;
import www.tonghao.service.common.base.BaseMapper;

public interface DealContractMapper extends BaseMapper<DealContract> {
	 /**
		 * 查询计划编号数量
		 * @return
		 */
		Integer findCountByPlanCode(@Param(value="planCode") String planCode);
		
		/**
		 * 查询合同列表
		 */
		List<DealContract> find(Map<String, Object> map);
		
		/*
		 * 根据id查找
		 */
		DealContract getById(@Param("id")Long id);
		
		/**
		 * 
		 */
		int updateStatus(DealContract dealContract);
	
}