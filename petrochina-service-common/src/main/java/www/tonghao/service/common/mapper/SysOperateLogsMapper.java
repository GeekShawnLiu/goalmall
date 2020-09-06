package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Map;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.SysOperateLogs;


public interface SysOperateLogsMapper extends BaseMapper<SysOperateLogs> {
	
	/**
	 * 新增
	 * @param entity
	 * @return
	 */
	int add(SysOperateLogs entity);
	
	/**
	 * 
	 * Description: 登录日志查询
	 * 
	 * @data 2019年8月8日
	 * @param 
	 * @return
	 */
	List<SysOperateLogs> selectLoginOperate(Map<String, Object> map);
}