package www.tonghao.platform.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.SpecialDate;
import www.tonghao.service.common.base.BaseService;

public interface SpecialDateService extends BaseService<SpecialDate> {

	/**
	 * 添加或者修改特殊日期
	 * @param date
	 * @return
	 */
	public int saveOrUpdate(SpecialDate date);
	
	/**
	 * 查询指定年的休息日
	 * @param year
	 * @return
	 */
	public List<String> findHolidays(@Param("year") String year);
	
}
