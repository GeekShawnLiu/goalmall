package www.tonghao.service.common.mapper;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.AfterSale;

public interface AfterSaleMapper extends BaseMapper<AfterSale>{
	
	/**
	 * @Description:今天最大的售后编号
	 * @date 2019年7月12日
	 */
	public AfterSale selectAfterSaleSnByToDay();

}
