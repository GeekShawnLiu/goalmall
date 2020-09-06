package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Brand;


public interface BrandService extends BaseService<Brand> {
	/**
	 * 添加或者修改品牌
	 * @param brand
	 * @return
	 */
   public int saveOrUpdate(Brand brand);
   
   
   
}
