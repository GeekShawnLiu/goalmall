package www.tonghao.mall.job.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.mall.api.jd.attwrap.CategorysAttr;
import www.tonghao.mall.api.jd.resultwrap.CategorysRes;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.JDCategorysService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.service.common.entity.EmallCatalogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.EmallCatalogsMapper;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.service.SuppliersService;

@Service("jdCategorysService")
public class JDCategorysServiceImpl implements JDCategorysService {
	@Autowired
	private SuppliersService suppliersService;
	@Autowired
	private EmallCatalogsMapper emallCatalogsMapper;
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	@Override
	public void executeJDCategorysJob() {
		Suppliers sup = new Suppliers();
		sup.setCode(Constant.MALL_JD_CODE);
		Suppliers suppliers = suppliersService.selectEntityOne(sup);
		if(suppliers!=null){
			CategorysRes one_category = JdUtilApi.CategorysApi("1", "5000", "", "0");
			if(one_category.isSuccess()){
				List<CategorysAttr> result = one_category.getResult();
				for (CategorysAttr categorysAttr : result) {
					if(categorysAttr.getState().equals("1")){
						String one_catId = categorysAttr.getCatId();
						String one_name = categorysAttr.getName();
						CategorysRes two_category = JdUtilApi.CategorysApi("1", "5000", one_catId, "1");
					    if(two_category.isSuccess()){
					    	List<CategorysAttr> two_category_result = two_category.getResult();
					    	for (CategorysAttr two : two_category_result) {
					    		if(two.getState().equals("1")){
					    			String two_catId = two.getCatId();
									String two_name = two.getName();
									CategorysRes third_category = JdUtilApi.CategorysApi("1", "5000", two_catId, "2");
									if(third_category.isSuccess()){
										List<CategorysAttr> third_category_result = third_category.getResult();
										for (CategorysAttr third : third_category_result) {
											if(third.getState().equals("1")){
												EmallCatalogs catalogs=new EmallCatalogs();
												catalogs.setEmallId(suppliers.getId());
												catalogs.setEmallCode(suppliers.getCode());
												catalogs.setEmallName(suppliers.getName());
												catalogs.setEmallCatalogsId(third.getCatId());
												catalogs.setEmallCatalogsName(one_name+"_"+two_name+"_"+third.getName());
												emallCatalogsMapper.insertSelective(catalogs);
											}
											
										}
									}else{
										EmallCatalogs catalogs=new EmallCatalogs();
										catalogs.setEmallId(suppliers.getId());
										catalogs.setEmallCode(suppliers.getCode());
										catalogs.setEmallName(suppliers.getName());
										catalogs.setEmallCatalogsId(two_catId);
										catalogs.setEmallCatalogsName(one_name+"_"+two_name);
										emallCatalogsMapper.insertSelective(catalogs);
									}
					    		}
							}
					    }else{
					    	EmallCatalogs catalogs=new EmallCatalogs();
							catalogs.setEmallId(suppliers.getId());
							catalogs.setEmallCode(suppliers.getCode());
							catalogs.setEmallName(suppliers.getName());
							catalogs.setEmallCatalogsId(one_catId);
							catalogs.setEmallCatalogsName(one_name);
							emallCatalogsMapper.insertSelective(catalogs);
					    }
					}
					
				}
			}
			
		}
	}

	
}
