package www.tonghao.platform.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import www.tonghao.platform.entity.Plan;
import www.tonghao.service.common.base.BaseService;

public interface PlanService extends BaseService<Plan> {

	public int savePlan(Plan plan);
	
	/**
	 * 条件查询 
	 */
	List<Plan> findList(Map<String, Object> map);

	/**
	 * @param id
	 * @return
	 */
	public Plan findById(Long id);

	/**
	 * 条件查询，关联计划明细品目、采购方式
	 * @param map
	 * @return
	 */
	public List<Plan> find(Map<String, Object> map);
	
	/**
	 * 查询基层单位可用计划
	 * @param departmentCode 基层单位Code
	 * @param procurementMethod 采购方式 网上超市13，定点08，批量10
	 * @param isCollect 是否集采
	 * @return
	 */
	List<Plan> findUsableListBydepartmentCode(String departmentCode,String procurementMethod,Boolean isCollect);
	
	/**
	 * 获取计划项目编号
	 * @param orgAreaCode 单位区域编码
	 * 				单位区域编码，在财厅同步信息里有，保存到我们的采购人库里，取创建项目人的所在单位的区域编码
	 * @param orgCode  机构编码
	 * @param systemCode 系统识别码
	 * 			1、2、3是超市有计划采购，4、超市无计划采购、5是批量集采，6是出车辆保险、加油、维修之外的定点采购，7是车辆保险，8加油，9维修订单采购
	 * @param max 当前最大序列值
	 * @param length 序列号长度 
	 * @return
	 */
	public String getSeqSn(String orgAreaCode, String orgCode, String systemCode,Long max,int length);
	
	/**
	 * 统计分析:计划完成率
	 * @param map 
	 * @return
	 */
	public List<Map<String, Object>> selectFinishRate(Map<String, Object> map);
	
	/**
	 * 返回计划
	 * @param plan 计划
	 * @param revertAmount 返回金额
	 */
	public void revertPlan(Plan plan,BigDecimal revertAmount);
	
}
