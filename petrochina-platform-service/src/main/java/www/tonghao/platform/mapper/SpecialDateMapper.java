package www.tonghao.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.SpecialDate;
import www.tonghao.service.common.base.BaseMapper;

public interface SpecialDateMapper extends BaseMapper<SpecialDate> {
	List<String> findHolidays(@Param("year") String year);
}