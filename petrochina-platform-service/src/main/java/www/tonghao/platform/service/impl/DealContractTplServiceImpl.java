package www.tonghao.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.platform.entity.DealContractTpl;
import www.tonghao.platform.mapper.DealContractTplMapper;
import www.tonghao.platform.service.DealContractTplService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("dealContractTplService")
public class DealContractTplServiceImpl extends BaseServiceImpl<DealContractTpl> implements DealContractTplService {
	
	@Autowired
	private DealContractTplMapper dealContractTplMapper;

	@Override
	public DealContractTpl getUsableByType(int type) {
		Example example = new Example(DealContractTpl.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("type",type);
		criteria.andEqualTo("status",1);
		criteria.andEqualTo("isDelete",0);
		List<DealContractTpl> list = selectByExample(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<DealContractTpl> find() {
		return dealContractTplMapper.find();
	}

}
