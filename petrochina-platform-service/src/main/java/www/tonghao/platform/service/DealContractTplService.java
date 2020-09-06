package www.tonghao.platform.service;

import java.util.List;

import www.tonghao.platform.entity.DealContractTpl;
import www.tonghao.service.common.base.BaseService;


/**
 * 成交合同模板
 * @author yggc
 *
 */
public interface DealContractTplService extends BaseService<DealContractTpl> {
	
	/**
	 * 根据类型获取可用的合同模板
	 * @param type 
     * 1：车辆维修
	 * 2：电梯空调维保
	 * 3：车辆保险
	 * 4：工程
	 * 5：定点货物
	 * 6：网上超市集采内直购、网上超市集采外直购
	 * 7：网上超市竞价
	 * 8：法律服务、印刷服务
	 * 9：车辆加油主卡
	 * 10：车辆加油副卡
	 * 11：物业按人工
	 * 12：物业按面积
	 * 13：保安
	 * 
	 * @return
	 */
	DealContractTpl getUsableByType(int type);
	
	List<DealContractTpl> find();
}
