package www.tonghao.mall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.entity.DeliverySupplier;
import www.tonghao.mall.entity.DeliveryWay;
import www.tonghao.mall.mapper.DeliverySupplierMapper;
import www.tonghao.mall.mapper.DeliveryWayMapper;
import www.tonghao.mall.service.DeliveryWayService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("deliveryWayService")
@Transactional
public class DeliveryWayServiceImpl extends BaseServiceImpl<DeliveryWay> implements DeliveryWayService{
	
	@Autowired
	private DeliveryWayMapper deliveryWayMapper;
	
	@Autowired
	private DeliverySupplierMapper deliverySupplierMapper; 
	
	@Override
	public int saveOrUpdate(DeliveryWay deliveryWay) {
		int result = 0;
		deliveryWay.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		deliveryWay.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		if (deliveryWay.getId() != null) {
			result = updateNotNull(deliveryWay);
			if (result > 0 && StringUtils.isNotBlank(deliveryWay.getSupplierIds())) {
				//先删除之前关联
				DeliverySupplier record = new DeliverySupplier();
				record.setDeliveryId(deliveryWay.getId());
				deliverySupplierMapper.delete(record);
				//保存最新选中供应商
				String[] supplierIds = deliveryWay.getSupplierIds().split(",");
				for (String str : supplierIds) {
					DeliverySupplier entity = new DeliverySupplier();
					entity.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					entity.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					entity.setDeliveryId(deliveryWay.getId());
					entity.setSupplierId(Long.parseLong(str));
					deliverySupplierMapper.insertSelective(entity);
				}
			}
		}else {
			result = saveSelective(deliveryWay);
			if (result > 0 && StringUtils.isNotBlank(deliveryWay.getSupplierIds())) {
				//保存最新选中供应商
				String[] supplierIds = deliveryWay.getSupplierIds().split(",");
				for (String str : supplierIds) {
					DeliverySupplier entity = new DeliverySupplier();
					entity.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					entity.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					entity.setDeliveryId(deliveryWay.getId());
					entity.setSupplierId(Long.parseLong(str));
					deliverySupplierMapper.insertSelective(entity);
				}
			}
		}
		return result;
	}

	@Override
	public List<DeliveryWay> find(Map<String, Object> map) {
		List<DeliveryWay> list = deliveryWayMapper.find(map);
		return list;
	}
}
