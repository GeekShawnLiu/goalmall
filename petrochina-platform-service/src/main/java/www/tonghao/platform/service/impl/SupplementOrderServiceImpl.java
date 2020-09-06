package www.tonghao.platform.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.platform.entity.SupplementOrder;
import www.tonghao.platform.entity.SupplementOrderItem;
import www.tonghao.platform.mapper.SupplementOrderItemMapper;
import www.tonghao.platform.mapper.SupplementOrderMapper;
import www.tonghao.platform.service.SupplementOrderService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Users;

@Service("supplementOrderService")
public class SupplementOrderServiceImpl extends BaseServiceImpl<SupplementOrder> implements SupplementOrderService {

	@Autowired
	private SupplementOrderMapper supplementOrderMapper;
	
	@Autowired
	private SupplementOrderItemMapper supplementOrderItemmapper;

	@Override
	public Map<String, Object> save(SupplementOrder supplementOrder, Users user) {
		List<SupplementOrderItem> supplementOrderItemList = supplementOrder.getSupplementOrderItemList();
		if(CollectionUtils.isEmpty(supplementOrderItemList)){
			return ResultUtil.error("商品信息不能为空");
		}
		//校验补单信息
		//用户名
		if(StringUtils.isEmpty(supplementOrder.getUserName())){
			return ResultUtil.error("用户名不能为空");
		}
	    //收货人
		if(StringUtils.isEmpty(supplementOrder.getConsigneeName())){
			return ResultUtil.error("收货人不能为空");
		}
		//收货地址
	    if(StringUtils.isEmpty(supplementOrder.getConsigneeAddr())){
	    	return ResultUtil.error("收货地址不能为空");
	    }
	    //电话
	    if(StringUtils.isEmpty(supplementOrder.getMobile())){
	    	return ResultUtil.error("电话不能为空");
	    }
		//保存补单信息
		supplementOrder.setUserId(user.getId());
		supplementOrder.setIsDelete(0);
		supplementOrder.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		supplementOrder.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		if(4 == user.getType()){
			//供应商
			supplementOrder.setSupplierId(user.getTypeId());
		}
		supplementOrder.setSn(DateUtilEx.timeToDate(new Date(),"yyyyMMddHHmmss"));
		int insertSelective = supplementOrderMapper.insertSelective(supplementOrder);
		if(insertSelective > 0){
			for (SupplementOrderItem supplementOrderItem : supplementOrderItemList) {
				if(supplementOrderItem != null && supplementOrderItem.getProductId() != null){
					supplementOrderItem.setSupplementOrderId(supplementOrder.getId());
					supplementOrderItem.setIsDelete(0);
					supplementOrderItem.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					supplementOrderItem.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					supplementOrderItem.setId(null);
					supplementOrderItemmapper.insertSelective(supplementOrderItem);
				}
			}
			return ResultUtil.success("添加成功");
		}else{
			return ResultUtil.error("添加失败");
		}
	}

	@Override
	public List<SupplementOrder> findByMap(Map<String, Object> map) {
		return supplementOrderMapper.findByMap(map);
	}

	@Override
	public SupplementOrder view(Long id) {
		SupplementOrder supplementOrder = supplementOrderMapper.selectById(id);
		return supplementOrder;
	}
}
