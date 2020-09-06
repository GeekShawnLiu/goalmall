package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Seq;

public interface SeqService extends BaseService<Seq> {

	/**
	 * 
	 * @param orgAreaCode  单位区域编码
	 * 			单位区域编码，在财厅同步信息里有，保存到我们的采购人库里，取创建项目人的所在单位的区域编码
	 * @param orgCode  机构编码
	 * 			01是集采机构编码  配在我们采购单位根节点 
	 * @param systemCode  系统识别码
	 * 		1是超市直购、2是超市竞价、3是批量采购、4、定点采购、C是超市直购无计划采购，J是超市竞价无计划采购，P是批量采购无计划采购，D是定点无计划采购
	 * @return
	 */
	String getSeqSn(String code);
	
	/*
	 * update start by yanfeng 2019-02-18 合同编号由计划编号改成项目编号 +下划线+三位流水号
	 * 			
	 */
	/**
	 * 根据计划编号获取合同编号
	 * @param planCode
	 * 		编号规则：合同对应的计划编号_三位流水，流水码从001开始
	 * @return
	 *//*
	String getContractSn(String planCode);
	
	*//**
	 * 根据项目编号获取合同编号
	 * @param projectCode 项目编号
	 * 		编号规则：合同对应的项目编号_三位流水，流水码从001开始
	 * @return
	 */
	//String getContractSn(String projectCode);
	/*
	 * update end by yanfeng 2019-02-18 
	 */
	/**
	 * start update by yanfeng 2019-03-12 项目编号改成计划编号
	 * @param planCode
	 * @return
	 */
	String getContractSn(String planCode);
	
	
	/**
	 * 获取售后编号
	 * @return
	 */
	String getSaleAfterSn();
}
