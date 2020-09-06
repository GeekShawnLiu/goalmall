package www.tonghao.service.common.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.EnterpriseNature;
import www.tonghao.service.common.mapper.EnterpriseNatureMapper;
import www.tonghao.service.common.service.EnterpriseNatureService;

@Service("enterpriseNatureService")
public class EnterpriseNatureServiceImpl extends BaseServiceImpl<EnterpriseNature> implements EnterpriseNatureService {

	@Autowired
	private EnterpriseNatureMapper enterpriseNatureMapper;
	@Override
	public int saveOrUpdate(EnterpriseNature enterpriseNature) {
		int result_default=0;
		if(enterpriseNature.getId()!=null){
			enterpriseNature.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=enterpriseNatureMapper.updateByPrimaryKeySelective(enterpriseNature);
		}else{
			enterpriseNature.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=enterpriseNatureMapper.insert(enterpriseNature);
		}
		return result_default;
	}

}
