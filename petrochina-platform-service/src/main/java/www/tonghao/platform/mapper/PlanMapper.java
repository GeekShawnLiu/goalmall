package www.tonghao.platform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.Plan;
import www.tonghao.service.common.base.BaseMapper;

public interface PlanMapper extends BaseMapper<Plan> {
		List<Plan> getPlanById(@Param("planId")Long planId);
		/**
		 * 根据计划编号查找计划id
		 */
		Long  findIdByCode(@Param("planCode")String planCode);
		
		/**
		 * 条件查询 
		 */
		List<Plan> findList(Map<String, Object> map);
		
		/**
		 * @param id
		 * @return
		 */
		Plan findById(@Param("id")Long id);
		
		/**
		 * 条件查询，关联计划明细品目、采购方式
		 * @param map
		 * @return
		 */
		List<Plan> find(Map<String, Object> map);
		
		/**
		 * 查询基层单位可用计划
		 * @param departmentCode 基层单位Code
		 * @param procurementMethod 采购方式 网上超市13，定点08，批量10
		 * @param isCollect 是否集采
		 * @return
		 */
		List<Plan> findUsableListBydepartmentCode(@Param("departmentCode")String departmentCode
									,@Param("procurementMethod")String procurementMethod
									,@Param("isCollect")Boolean isCollect);
		
		/**
		 * 统计分析:计划完成率
		 * @param map 
		 * @return
		 */
		public List<Map<String, Object>> selectFinishRate(Map<String, Object> map);
}