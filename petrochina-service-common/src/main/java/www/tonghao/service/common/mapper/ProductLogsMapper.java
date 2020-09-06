package www.tonghao.service.common.mapper;

import java.util.List;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ProductLogs;


public interface ProductLogsMapper extends BaseMapper<ProductLogs> {
	
	/**
	 * 条件查询列表
	 * @param entity
	 * @return
	 */
	List<ProductLogs> findListByEntity(ProductLogs entity);
}