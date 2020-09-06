package www.tonghao.mall.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.mall.entity.ReceiverAddresses;
import www.tonghao.mall.service.ReceiverAddressesService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

/**
 * 收货地址
 * @author developer001
 *
 */
@Service("mallReceiverAddressesService")
public class ReceiverAddressesServiceImpl extends BaseServiceImpl<ReceiverAddresses> implements ReceiverAddressesService{

	@Override
	public ReceiverAddresses findUserDefault(Long userId) {
		Example example = new Example(ReceiverAddresses.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("isDefault", true);
		List<ReceiverAddresses> list = selectByExample(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
