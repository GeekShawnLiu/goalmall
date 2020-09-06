package www.tonghao.mall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import www.tonghao.mall.entity.DeliveryWay;
import www.tonghao.service.common.base.BaseMapper;

@Component(value="mallDeliveryWayMapper")
public interface DeliveryWayMapper extends BaseMapper<DeliveryWay> {

	List<DeliveryWay> find(Map<String, Object> map);
	
	/**
	 * 查询供应商可用的配送方式
	 * @param supplierId
	 * @return
	 */
	List<DeliveryWay> findSupplierUsableList(@Param("supplierId")Long supplierId);
}