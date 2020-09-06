package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Shop;

/**
 * @Description:店铺
 * @date 2019年6月19日
 */
public interface ShopMapper extends BaseMapper<Shop>{

	List<Shop> selectByid(@Param("supplierId") Long supplierId);
}
