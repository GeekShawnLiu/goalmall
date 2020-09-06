package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ShopCatalogs;

/**
 * @Description:店铺品目
 * @date 2019年6月19日
 */
public interface ShopCatalogsMapper extends BaseMapper<ShopCatalogs>{
	
	public List<ShopCatalogs> selectShopCatalogsList(@Param("shopId") Long shopId);

	int deleteByshopId(@Param("shopId") Long shopId);
}
