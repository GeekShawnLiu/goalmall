package www.tonghao.platform.service;

import www.tonghao.platform.entity.QuickLink;
import www.tonghao.service.common.base.BaseService;

public interface QuickLinkService extends BaseService<QuickLink> {
	/**
	 * 添加或修改快速链接
	 * @param link
	 * @return
	 */
   public int saveOrUpdate(QuickLink link);
}
