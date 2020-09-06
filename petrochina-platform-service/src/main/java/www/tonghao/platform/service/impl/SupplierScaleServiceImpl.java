package www.tonghao.platform.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.platform.entity.SupplierScale;
import www.tonghao.platform.mapper.SupplierScaleMapper;
import www.tonghao.platform.service.SupplierScaleService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("supplierScaleService")
public class SupplierScaleServiceImpl extends BaseServiceImpl<SupplierScale> implements SupplierScaleService {

	@Autowired
	private SupplierScaleMapper supplierScaleMapper;
	@Override
	public int saveOrUpdate(SupplierScale scale) {
		int result_default=0;
		if(scale.getId()!=null){
			scale.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=supplierScaleMapper.updateByPrimaryKeySelective(scale);
		}else{
			scale.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result_default=supplierScaleMapper.insert(scale);
		}
		return result_default;
	}

	
	
	
}
