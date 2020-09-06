package www.tonghao.service.common.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Brand;

public interface BrandMapper extends BaseMapper<Brand> {
	
	/**
	 * 查询可用的包含ID的集合
	 * @param ids
	 * @return
	 */
	List<Brand> findUsableListIdIn(@Param("ids")Set<Long> ids);
	
	/**
	 * 查询库中对应品牌
	 * @param name
	 * @return
	 */
	List<Brand> findByMappingName(String name);
	
	
	List<Brand> selectByGroupId();
}