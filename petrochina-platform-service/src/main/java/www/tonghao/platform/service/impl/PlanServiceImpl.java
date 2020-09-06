package www.tonghao.platform.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.platform.entity.Plan;
import www.tonghao.platform.entity.PlanItem;
import www.tonghao.platform.mapper.PlanItemMapper;
import www.tonghao.platform.mapper.PlanMapper;
import www.tonghao.platform.service.PlanService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Seq;
import www.tonghao.service.common.mapper.SeqMapper;

@Service("planService")
public class PlanServiceImpl extends BaseServiceImpl<Plan> implements PlanService{

	@Autowired
	private PlanMapper planMapper;

	@Autowired
	private PlanItemMapper planItemMapper;
	
	@Autowired
	private SeqMapper seqMapper;
	
	private static String[] STR_7={"C05"};//车辆保险
	
	private static String[] STR_8={"C06"};//加油
	
	private static String[] STR_9={"C0402"};//维修定点
	
	//其余定点
	private static String[] STR_6={"C02","C03","C0401","C0403","C07","C08","B02","B01","B03",
		"A01","A08","A10","A0702","A0703","A0601","A0602","A0603","A020308","A0506","A0507","A0508","A0101","A0102","A0103","A0104","A0105"
		,"A0601","A060201","A060202","A060203","A060301","A060302"};
	
	
	
	public String getSeqSn(String orgAreaCode, String orgCode, String systemCode,Long max,int length) {
		String name = "SDGP"+orgAreaCode+DateUtilEx.getIndexYear(new Date())+orgCode+systemCode;
		int count = seqMapper.countByName(name);
		if(count<1){
			Seq seq = new Seq();
			seq.setName(name);
			seq.setMax(max);
			seq.setLength(length);
			seq.setNext(1);
			seqMapper.insert(seq);
		}
		return name.substring(0, name.length()-1) +seqMapper.getSeqValue(name);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int savePlan(Plan plan) {
		int default_rsult=0;
		if(plan!=null){
			Plan record=new Plan();
			record.setPlanCode(plan.getPlanCode());
			record.setPlanId(plan.getPlanId());
			Plan selectOne = planMapper.selectOne(record);
			if(selectOne!=null){
				selectOne.setCreatedAt(plan.getCreatedAt());
				selectOne.setUseMonth(plan.getUseMonth());
				selectOne.setSuperior(plan.getSuperior());
				selectOne.setSuperiorCode(plan.getSuperiorCode());
				selectOne.setDepartment(plan.getDepartment());
				selectOne.setDepartmentCode(plan.getDepartmentCode());
				selectOne.setRegion(plan.getRegion());
				selectOne.setAgency(plan.getAgency());
				selectOne.setAgencyCode(plan.getAgencyCode());
				selectOne.setProcurementMethod(plan.getProcurementMethod());
				selectOne.setAuditNumber(plan.getAuditNumber());
				selectOne.setImportNumber(plan.getImportNumber());
				selectOne.setLinkman(plan.getLinkman());
				selectOne.setTel(plan.getTel());
				selectOne.setIsAdvance(plan.getIsAdvance());
				selectOne.setIntegratedCost(plan.getIntegratedCost());
				selectOne.setAdvancePlanId(plan.getAdvancePlanId());
				default_rsult+= planMapper.updateByPrimaryKeySelective(selectOne);
				List<PlanItem> items = plan.getItems();
				
				Example example=new Example(PlanItem.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("planRevId", selectOne.getPlanId());
				List<PlanItem> planItems = planItemMapper.selectByExample(example);
				if(!CollectionUtils.isEmpty(planItems)&&planItems.size()==1&&items.size()==1){
					PlanItem planItem = planItems.get(0);
					PlanItem new_item = items.get(0);
					planItem.setPlanId(selectOne.getId());
					planItem.setPlanRevId(selectOne.getPlanId());
					planItem.setCategoryCode(new_item.getCategoryCode());
					planItem.setCategoryName(new_item.getCategoryName());
					planItem.setUnit(new_item.getUnit());
					planItem.setNum(new_item.getNum());
					planItem.setSpec(new_item.getSpec());
					planItem.setBudgetIn(new_item.getBudgetIn());
					planItem.setBudgetFinance(new_item.getBudgetFinance());
					planItem.setBudgetSelf(new_item.getBudgetSelf());
					planItem.setImportCategorCode(new_item.getImportCategorCode());
					planItem.setImportCategorName(new_item.getImportCategorName());
					planItem.setImportProductName(new_item.getImportProductName());
					default_rsult+=planItemMapper.updateByPrimaryKeySelective(planItem);
				}else{
					default_rsult=0;
				}
			}else{
				List<PlanItem> items = plan.getItems();
				String meth = plan.getProcurementMethod();
				String project_code="";
				if(!StringUtils.isBlank(meth)){
					if("13".equals(meth)){//网上超市
						project_code=getSeqSn(plan.getRegion(), "01", "1", 100000L,6);
					}
					if("08".equals(meth)){//定点
						PlanItem planItem = items.get(0);
						if(Arrays.asList(STR_7).contains(planItem.getCategoryCode())){
							project_code=getSeqSn(plan.getRegion(), "01", "7", 700000L,6);
						}
						if(Arrays.asList(STR_8).contains(planItem.getCategoryCode())){
							project_code=getSeqSn(plan.getRegion(), "01", "8", 800000L,6);
						}
						if(Arrays.asList(STR_9).contains(planItem.getCategoryCode())){
							project_code=getSeqSn(plan.getRegion(), "01", "9", 900000L,6);
						}
						if(Arrays.asList(STR_6).contains(planItem.getCategoryCode())){
							project_code=getSeqSn(plan.getRegion(), "01", "6", 600000L,6);
						}
					}
					if("10".equals(meth)){//批量
						project_code=getSeqSn(plan.getRegion(), "01", "5", 500000L,6);
					}
				}
				plan.setProjectCode(project_code);
				plan.setStatus(0);
				plan.setUsageCount(0);
				default_rsult = planMapper.insert(plan);
				if(!CollectionUtils.isEmpty(items)){
					for (PlanItem planItem : items) {
						planItem.setPlanId(plan.getId());
						planItem.setPlanRevId(plan.getPlanId());
						planItem.setUsedBudgetFinance(new BigDecimal("0"));
						planItem.setUsedBudgetIn(new BigDecimal("0"));
						planItem.setUsedBudgetSelf(new BigDecimal("0"));
						planItem.setUsedBudget(new BigDecimal("0"));
						BigDecimal budgetTotal=planItem.getBudgetIn().add(planItem.getBudgetFinance()).add(planItem.getBudgetSelf());
						planItem.setBudgetTotal(budgetTotal);
						default_rsult+=planItemMapper.insert(planItem);
					}
				}
			}
		}
		return default_rsult;
	}
	
	/**
	 * 条件查询 
	 */
	public List<Plan> findList(Map<String, Object> map){
		return planMapper.findList(map);
	}

	@Override
	public Plan findById(Long id) {
		return planMapper.findById(id);
	}

	@Override
	public List<Plan> find(Map<String, Object> map) {
		return planMapper.find(map);
	}

	@Override
	public List<Plan> findUsableListBydepartmentCode(String departmentCode,String procurementMethod,Boolean isCollect) {
		List<Plan> list = planMapper.findUsableListBydepartmentCode(departmentCode,procurementMethod,isCollect);
		return list.stream().filter(p -> p.getTotalBalance().compareTo(BigDecimal.ZERO)>0).collect(Collectors.toList());
	}
	
	/**
	 * 统计分析:计划完成率
	 */
	public List<Map<String, Object>> selectFinishRate(Map<String, Object> map){
		return planMapper.selectFinishRate(map);
	}

	@Override
	public void revertPlan(Plan plan, BigDecimal revertAmount) {

		List<PlanItem> planItems = plan.getItems();
		
		BigDecimal waitRecoveryAmount = revertAmount;//等待恢复金额
		for(PlanItem planItem:planItems){
			BigDecimal usedBudget = planItem.getUsedBudget().subtract(waitRecoveryAmount);
			if(usedBudget.compareTo(BigDecimal.ZERO)>=0){
				planItem.setUsedBudget(usedBudget);
				planItemMapper.updateByPrimaryKeySelective(planItem);
				break;
			}else{
				waitRecoveryAmount = waitRecoveryAmount.subtract(planItem.getUsedBudget());
				planItem.setUsedBudget(BigDecimal.ZERO);
				planItemMapper.updateByPrimaryKeySelective(planItem);
			}
		}
		
		int usageCount = plan.getUsageCount()-1;
		plan.setUsageCount(usageCount);
		if(usageCount<1){
			plan.setStatus(0);
		}else{
			plan.setStatus(1);
		}
		updateNotNull(plan);
	}
	
}
