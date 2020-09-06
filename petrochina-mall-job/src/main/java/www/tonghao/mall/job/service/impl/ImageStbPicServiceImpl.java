package www.tonghao.mall.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.mall.job.service.ImageStbPicService;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.ProductPics;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.mapper.ProductPicsMapper;
import www.tonghao.service.common.service.PlatformCatalogsService;
import www.tonghao.service.common.service.ProductsService;

@Service("imageStbPicService")
public class ImageStbPicServiceImpl implements ImageStbPicService {

	
	@Autowired
	private PlatformCatalogsService platformCatalogsService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private ProductPicsMapper productPicsMapper;
	@Override
	public void executeImageStbPicJob() {
		Example example=new Example(PlatformCatalogs.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("treeDepth", 3);
		createCriteria.andEqualTo("isDelete", 0);
		createCriteria.andEqualTo("isParent", "false");
		List<PlatformCatalogs> pc = platformCatalogsService.selectByExample(example);
		if(!CollectionUtil.collectionIsEmpty(pc)) {
			for (PlatformCatalogs platformCatalogs : pc) {
				product(platformCatalogs);
			}
		}
		
	}

	private void product(PlatformCatalogs platformCatalogs) {
		Example example_product=new Example(Products.class);
		Criteria createCriteria_product = example_product.createCriteria();
		createCriteria_product.andEqualTo("catalogId", platformCatalogs.getId());
		createCriteria_product.andIsNotNull("sku");
		List<Products> pro_list = productsService.selectByExample(example_product);
		if(!CollectionUtil.collectionIsEmpty(pro_list)) {
			for (Products products : pro_list) {
				
				Example example_pic=new Example(ProductPics.class);
				Criteria createCriteria = example_pic.createCriteria();
				createCriteria.andEqualTo("productId", products.getId());
				List<ProductPics> pic = productPicsMapper.selectByExample(example_pic);
				if(!CollectionUtil.collectionIsEmpty(pic)) {
					ProductPics productPics = pic.get(0);
					String large = productPics.getLarge();
					String large1=""; 
					String large2="";
					String large3="";
					if(large.indexOf("_1_")!=-1) {
						 large1 = large.replace("_1_", "_2_");
						 large2 = large.replace("_1_", "_3_");
						 large3 = large.replace("_1_", "_4_");
					}
                    if(large.indexOf("_2_")!=-1) {
                    	 large1 = large.replace("_2_", "_1_");
						 large2 = large.replace("_2_", "_3_");
						 large3 = large.replace("_2_", "_4_");
					}
					if(large.indexOf("_3_")!=-1) {
						large1 = large.replace("_3_", "_1_");
						large2 = large.replace("_3_", "_2_");
						large3 = large.replace("_3_", "_4_");	
					}
					if(large.indexOf("_4_")!=-1) {
						large1 = large.replace("_4_", "_1_");
						large2 = large.replace("_4_", "_2_");
						large3 = large.replace("_4_", "_3_");
					}
                    if(large.indexOf("_5_")!=-1) {
                    	large1 = large.replace("_5_", "_1_");
						large2 = large.replace("_5_", "_2_");
						large3 = large.replace("_5_", "_3_");
					}
                    List<ProductPics> list=new ArrayList<ProductPics>();
					if(StringUtils.isNotEmpty(large1)) {
						ProductPics pc=new ProductPics();
						pc.setLarge(large1);
						pc.setMedium(large1);
						pc.setSource(large1);
						pc.setProductId(productPics.getProductId());
						pc.setCreatedAt("2019-05-30 00:00:00");
						pc.setUpdatedAt("2019-05-30 00:00:00");
						list.add(pc);
					}
					if(StringUtils.isNotEmpty(large2)) {
						ProductPics pc=new ProductPics();
						pc.setLarge(large2);
						pc.setMedium(large2);
						pc.setSource(large2);
						pc.setProductId(productPics.getProductId());
						pc.setCreatedAt("2019-05-30 00:00:00");
						pc.setUpdatedAt("2019-05-30 00:00:00");
						list.add(pc);
					}
					if(StringUtils.isNotEmpty(large3)) {
						ProductPics pc=new ProductPics();
						pc.setLarge(large3);
						pc.setMedium(large3);
						pc.setSource(large3);
						pc.setProductId(productPics.getProductId());
						pc.setCreatedAt("2019-05-30 00:00:00");
						pc.setUpdatedAt("2019-05-30 00:00:00");
						list.add(pc);
					}
                    if(!CollectionUtil.collectionIsEmpty(list)) {
                    	for (ProductPics pp : list) {
                    		productPicsMapper.insertSelective(pp);
						}
                    }
                    
				}
			}
		}
		
	}
	
	
	
}
