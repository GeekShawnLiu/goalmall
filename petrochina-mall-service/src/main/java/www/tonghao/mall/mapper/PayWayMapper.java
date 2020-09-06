package www.tonghao.mall.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import www.tonghao.mall.entity.PayWay;
import www.tonghao.service.common.base.BaseMapper;

@Component(value="mallPayWayMapper")
public interface PayWayMapper extends BaseMapper<PayWay> {

	List<PayWay> find(Map<String, Object> map);
	
	/**
	 * 查询供应商可用的支付方式
	 * @param id
	 * @return
	 */
	List<PayWay> findSupplierUsableList(Long id);
}