package www.tonghao.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.platform.entity.SpecialDate;
import www.tonghao.platform.mapper.SpecialDateMapper;
import www.tonghao.platform.service.SpecialDateService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("specialDateService")
public class SpecialDateServiceImpl extends BaseServiceImpl<SpecialDate> implements SpecialDateService {

	@Autowired
	private SpecialDateMapper specialDateMapper;

	@Override
	public int saveOrUpdate(SpecialDate date) {
	   int result_default=0;
		if(date.getId()!=null){
			date.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=specialDateMapper.updateByPrimaryKeySelective(date);
		}else{
			date.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=specialDateMapper.insert(date);
		}
		return result_default;
	}
	
	/**
	 * 查询指定年的休息日
	 */
	public List<String> findHolidays(@Param("year") String year) {
		return specialDateMapper.findHolidays(year);
	}
	
	
}
