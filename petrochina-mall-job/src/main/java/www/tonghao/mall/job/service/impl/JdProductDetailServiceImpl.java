package www.tonghao.mall.job.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.mall.api.jd.attwrap.ProductAttr;
import www.tonghao.mall.api.jd.attwrap.ProductOther;
import www.tonghao.mall.api.jd.resultwrap.ProductRes;
import www.tonghao.mall.job.service.JdProductDetailService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.service.common.entity.EmallCatalogs;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.mapper.BrandMapper;
import www.tonghao.service.common.mapper.EmallCatalogsMapper;
import www.tonghao.service.common.mapper.IntegralConsumeMapper;
import www.tonghao.service.common.mapper.ProductPicsMapper;
import www.tonghao.service.common.mapper.SkuLogsMapper;
import www.tonghao.service.common.mapper.UpperLowerHistoryMapper;
import www.tonghao.service.common.service.IntegralUserService;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;
import www.tonghao.service.common.service.SuppliersService;

@Service("jdProductDetailService")
public class JdProductDetailServiceImpl implements JdProductDetailService {

	@Autowired
	private SuppliersService suppliersService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private ProductPicsMapper productPicsMapper;
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	@Autowired
	private EmallCatalogsMapper emallCatalogsMapper;
	@Override
	public void executedProductDetailjob() {
		Example example=new Example(Products.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("supplierId", 23);
		createCriteria.andIsNull("appintroduce");
		List<Products> pro = productsService.selectByExample(example);
		if(!CollectionUtil.collectionIsEmpty(pro)){
			for (Products products : pro) {
				ProductRes productDetailApi = JdUtilApi.ProductDetailApi(products.getSku());
				if(productDetailApi.isSuccess()){
					ProductAttr result = productDetailApi.getResult();
					ProductOther po = (ProductOther) result.getProduct();
					products.setAppintroduce(po.getAppintroduce());
					products.setWxintroduction(po.getWxintroduction());
					products.setIsFactoryShip(po.getIs_factory_ship());
					productsService.updateNotNull(products);
				}
			}
		}
	}

}
