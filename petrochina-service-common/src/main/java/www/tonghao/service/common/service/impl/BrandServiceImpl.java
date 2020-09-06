package www.tonghao.service.common.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.service.BrandService;

@Service("brandService")
public class BrandServiceImpl extends BaseServiceImpl<Brand> implements BrandService {

	@Autowired
	private BrandMapper brandMapper;
	@Override
	public int saveOrUpdate(Brand brand) {
		int result_default=0;
		if(brand.getId()!=null){
			brand.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			//result_default= brandMapper.updateByPrimaryKeySelective(brand);
			result_default = updateAll(brand);
		}else{
			brand.setIsDelete(0);
			brand.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			//result_default=brandMapper.insert(brand);
			result_default = save(brand);
		}
		return result_default;
	}

}
