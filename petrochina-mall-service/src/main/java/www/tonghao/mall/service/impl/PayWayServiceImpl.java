package www.tonghao.mall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.entity.PaySupplier;
import www.tonghao.mall.entity.PayWay;
import www.tonghao.mall.mapper.PaySupplierMapper;
import www.tonghao.mall.mapper.PayWayMapper;
import www.tonghao.mall.service.PayWayService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;


@Service("payWayService")
@Transactional
public class PayWayServiceImpl extends BaseServiceImpl<PayWay> implements PayWayService{

	@Autowired
	private PayWayMapper payWayMapper;
	
	@Autowired
	private PaySupplierMapper paySupplierMapper;
	
	@Override
	public int saveOrUpdate(PayWay payWay) {
		int result = 0;
		payWay.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		payWay.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		if (payWay.getId() != null) {
			result = updateNotNull(payWay);
			if (result > 0 && StringUtils.isNotBlank(payWay.getSupplierIds())) {
				//先删除之前关联
				PaySupplier record = new PaySupplier();
				record.setPayWayId(payWay.getId());
				paySupplierMapper.delete(record);
				//保存最新选中供应商
				String[] supplierIds = payWay.getSupplierIds().split(",");
				for (String str : supplierIds) {
					PaySupplier entity = new PaySupplier();
					entity.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					entity.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					entity.setPayWayId(payWay.getId());
					entity.setSupplierId(Long.parseLong(str));
					paySupplierMapper.insertSelective(entity);
				}
			}
		}else {
			result = saveSelective(payWay);
			if (result > 0 && StringUtils.isNotBlank(payWay.getSupplierIds())) {
				//保存最新选中供应商
				String[] supplierIds = payWay.getSupplierIds().split(",");
				for (String str : supplierIds) {
					PaySupplier entity = new PaySupplier();
					entity.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					entity.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
					entity.setPayWayId(payWay.getId());
					entity.setSupplierId(Long.parseLong(str));
					paySupplierMapper.insertSelective(entity);
				}
			}
		}
		return result;
	}

	@Override
	public List<PayWay> find(Map<String, Object> map) {
		List<PayWay> list = payWayMapper.find(map);
		return list;
	}

	@Override
	public List<PayWay> findSupplierUsableList(Long id) {
		return payWayMapper.findSupplierUsableList(id);
	}

}
