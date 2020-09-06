package www.tonghao.mall.service;

import java.util.List;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.PlatformCatalogs;

public interface MallPlatformCatalogsService extends BaseService<PlatformCatalogs> {
	
	/**
	 * 获取平台品目
	 * 	 @see getUsableNodeList
	 * @return
	 */
	@Deprecated
	List<PlatformCatalogs> getPlatformCatalogs();
	
}
