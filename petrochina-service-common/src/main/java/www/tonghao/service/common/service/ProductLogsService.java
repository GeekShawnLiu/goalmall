package www.tonghao.service.common.service;

import java.util.List;

import www.tonghao.common.warpper.PriceTrend;
import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.ProductLogs;

public interface ProductLogsService extends BaseService<ProductLogs> {
	
	/**
	 * 获取商品价格趋势
	 * @param pid
	 * @return
	 */
	List<PriceTrend> getPriceTrend(Long pid);
}
