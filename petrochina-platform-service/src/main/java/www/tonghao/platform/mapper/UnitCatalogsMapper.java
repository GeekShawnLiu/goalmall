package www.tonghao.platform.mapper;

import org.apache.ibatis.annotations.Param;

import www.tonghao.platform.entity.UnitCatalogs;
import www.tonghao.service.common.base.BaseMapper;

public interface UnitCatalogsMapper extends BaseMapper<UnitCatalogs> {

	/**  
	 * <p>Title: insertBatch</p>  
	 * <p>Description: 批量添加计量单位与品目关联</p>  
	 * @author Yml  
	 * @param id
	 * @param catalogsIdArry
	 * @return  
	 */  
	int insertBatch(@Param("unitId") Long unitId, @Param("catalogsIdArry") Integer[] catalogsIdArry);
}