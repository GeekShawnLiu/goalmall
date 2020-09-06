package www.tonghao.platform.service;

import www.tonghao.platform.entity.IndustryCategory;
import www.tonghao.service.common.base.BaseService;


public interface IndustryCategoryService extends BaseService<IndustryCategory> {
	/**
	 * 添加或者删除行业类别
	 * @return
	 */
	public int saveOrUpdate(IndustryCategory industryCategory);
   
}
