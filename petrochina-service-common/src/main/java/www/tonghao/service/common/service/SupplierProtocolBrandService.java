package www.tonghao.service.common.service;

import java.util.HashMap;
import java.util.List;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.SupplierProtocolBrand;

public interface SupplierProtocolBrandService extends BaseService<SupplierProtocolBrand> {

	/**  
	 * <p>Title: getSupplierProtocolBrands</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param map
	 * @return  
	 */  
	List<Brand> getSupplierProtocolBrands(HashMap<String, Object> map);

}
