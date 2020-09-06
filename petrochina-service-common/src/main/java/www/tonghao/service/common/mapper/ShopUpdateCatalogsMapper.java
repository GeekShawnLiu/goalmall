package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.ShopUpdateCatalogs;

public interface ShopUpdateCatalogsMapper extends BaseMapper<ShopUpdateCatalogs>{

	List<ShopUpdateCatalogs> selectByUpdateId(@Param("supplierUpdateId")Long supplierUpdateId);
}
