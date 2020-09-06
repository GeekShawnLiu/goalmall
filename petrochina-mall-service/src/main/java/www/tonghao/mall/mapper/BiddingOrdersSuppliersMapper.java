package www.tonghao.mall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import www.tonghao.mall.entity.BiddingOrdersSuppliers;
import www.tonghao.service.common.base.BaseMapper;

public interface BiddingOrdersSuppliersMapper extends BaseMapper<BiddingOrdersSuppliers> {
	List<BiddingOrdersSuppliers> getSuppliersByOrderId(@Param("orderId")Long orderId);
	List<BiddingOrdersSuppliers> getSupplierByOrderId(@Param("orderId")Long orderId);
	List<BiddingOrdersSuppliers> findDeal(Map<String, Object> map);
	int deleteSuppliers(Map<String, Object> map);
	BiddingOrdersSuppliers selectSupplier(@Param("itemsId") Long itemsId,@Param("suppliersId")Long suppliersId);
	int  selectCountBySuppliers(@Param("orderId")Long orderId);
	List<Long> selectOrderIds(@Param("supplierId")Long supplierId);
	int findOrder(Map<String, Object> map);
}