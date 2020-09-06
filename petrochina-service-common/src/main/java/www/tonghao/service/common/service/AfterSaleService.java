package www.tonghao.service.common.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.AfterSale;
import www.tonghao.service.common.entity.Users;

public interface AfterSaleService extends BaseService<AfterSale>{

	/**
	 * @Description:提交售后申请
	 * @date 2019年7月11日
	 */
	public Map<String, Object> submitApply(AfterSale afterSale);
	
	/**
	 * @Description:获取售后列表
	 * @date 2019年7月12日
	 */
	public PageInfo<AfterSale> getAfterSaleList(PageBean page, Example example);
	
	/**
	 * @Description:重新提交售后申请
	 * @date 2019年7月11日
	 */
	public Map<String, Object> resubmitSubmitApply(AfterSale afterSale);
	
	/**
	 * @Description:退款
	 * @date 2019年7月23日
	 */
	public Map<String, Object> refund(Users user, Long afterSaleId);
}
