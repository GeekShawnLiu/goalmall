package www.tonghao.service.common.mapper;

import java.util.HashMap;
import java.util.List;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.SupplierProtocolBrand;


public interface SupplierProtocolBrandMapper extends BaseMapper<SupplierProtocolBrand> {

	/**  
	 * <p>Title: getSupplierProtocolBrands</p>  
	 * <p>Description: 根据协议查询供应商品牌</p>  
	 * @author Yml  
	 * @param map
	 * @return  
	 */  
	List<Brand> getSupplierProtocolBrands(HashMap<String, Object> map);
}