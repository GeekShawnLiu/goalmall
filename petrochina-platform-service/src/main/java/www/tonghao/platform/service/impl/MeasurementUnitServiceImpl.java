package www.tonghao.platform.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.platform.entity.MeasurementUnit;
import www.tonghao.platform.entity.UnitCatalogs;
import www.tonghao.platform.mapper.MeasurementUnitMapper;
import www.tonghao.platform.mapper.UnitCatalogsMapper;
import www.tonghao.platform.service.MeasurementUnitService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("measurementUnitService")
@Transactional
public class MeasurementUnitServiceImpl extends BaseServiceImpl<MeasurementUnit> implements MeasurementUnitService {

	@Autowired
	private MeasurementUnitMapper measurementUnitMapper;
	
	@Autowired
	private UnitCatalogsMapper unitCatalogsMapper;

	@Override
	public int saveOrUpdate(MeasurementUnit measurementUnit) {
		int result = 0;
		if (measurementUnit.getId() != null) {
			//修改计量单位信息
			measurementUnit.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result = measurementUnitMapper.updateByPrimaryKeySelective(measurementUnit);
			if (result > 0) {
				//删除旧的单位品目关联关系
				UnitCatalogs unitCatalogs = new UnitCatalogs();
				unitCatalogs.setUnitId(measurementUnit.getId());
				unitCatalogsMapper.delete(unitCatalogs);
				//保存新的单位品目关联关系
				if (measurementUnit.getCatalogsIdArry() != null && measurementUnit.getCatalogsIdArry().length > 0) {
					unitCatalogsMapper.insertBatch(measurementUnit.getId(), measurementUnit.getCatalogsIdArry());
				}
			}
		} else {
			//新增保存计量单位基本信息
			measurementUnit.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			measurementUnit.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			result = measurementUnitMapper.insertSelective(measurementUnit);
			//保存新的单位品目关联关系
			if (measurementUnit.getCatalogsIdArry() != null && measurementUnit.getCatalogsIdArry().length > 0) {
				unitCatalogsMapper.insertBatch(measurementUnit.getId(), measurementUnit.getCatalogsIdArry());
			}
		}
		return result;
	}

	@Override
	public List<MeasurementUnit> find(Map<String, Object> map) {
		return measurementUnitMapper.find(map);
	}

	
	
}
