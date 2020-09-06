package www.tonghao.mall.job.service.impl;

import java.util.Date;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.CollectionUtil;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.mall.api.jd.attwrap.ProductPoolResultAttr;
import www.tonghao.mall.api.jd.resultwrap.ProductPoolRes;
import www.tonghao.mall.api.standard.attwrap.CatalogPoolAttr;
import www.tonghao.mall.api.standard.resultwrap.CatalogPoolRes;
import www.tonghao.mall.core.XmlConfig;
import www.tonghao.mall.job.Constant;
import www.tonghao.mall.job.service.CatalogInitService;
import www.tonghao.mall.job.util.JdUtilApi;
import www.tonghao.mall.job.util.StandardUtilApi;
import www.tonghao.service.common.entity.EmallCatalogs;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.EmallCatalogsMapper;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;
import www.tonghao.service.common.service.SuppliersService;

@Service("catalogInitService")
public class CatalogInitServiceImpl implements CatalogInitService {

	
	@Autowired
	private SuppliersService suppliersService;
	@Autowired
	private EmallCatalogsMapper emallCatalogsMapper;
	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	
	@Override
	public void catalogInitJob() {
		List<Node> node = XmlConfig.getNode("/api_config/common/emall_mapping/mall");
		for (Node node2 : node) {
			Element element = (Element)node2;
			String emallcode = element.attributeValue("code");
			Suppliers sup = new Suppliers();
			sup.setCode(emallcode);
			Suppliers suppliers = suppliersService.selectEntityOne(sup);
			if(suppliers == null){
				continue;
			}
			if(suppliers.getCode().equals(Constant.MALL_JD_CODE)){
				//catalogInitJd(suppliers);
			}else if(suppliers.getCode().equals(Constant.MALL_STB_CODE)){
				continue;
			}else{
				catalogInitStandard(suppliers);
			}
		}
	}

	public void catalogInitJd(Suppliers suppliers){
		ProductPoolRes productPoolApi = JdUtilApi.ProductPoolApi();
		if(productPoolApi.isSuccess()){
			List<ProductPoolResultAttr> result = productPoolApi.getResult();
			if(!CollectionUtil.collectionIsEmpty(result)){
				EmallCatalogs  emallCatalogs=null;
				for (ProductPoolResultAttr productPoolResultAttr : result) {
					String id = productPoolResultAttr.getPage_num();
					String name = productPoolResultAttr.getName();
					emallCatalogs=new EmallCatalogs();
					emallCatalogs.setEmallCatalogsId(id);
					emallCatalogs.setEmallCode(suppliers.getCode());
					emallCatalogs.setEmallId(suppliers.getId());
					EmallCatalogs ec = emallCatalogsMapper.selectOne(emallCatalogs);
					if(ec==null){
						emallCatalogs.setEmallName(suppliers.getName());
						emallCatalogs.setEmallCatalogsName(name);
						emallCatalogs.setCreatedAt(DateUtilEx.timeToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						emallCatalogs.setUpdatedAt(DateUtilEx.timeToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						PlatformCatalogs pc = platformCatalogsMapper.selectByPrimaryKey(Long.parseLong(id));
						if(pc!=null){
							emallCatalogs.setCatalogsId(pc.getId());
						}
						emallCatalogsMapper.insertSelective(emallCatalogs);
					}
				}
			}
		}
	}
	
	
	
	public void catalogInitStandard(Suppliers suppliers){
		CatalogPoolRes catalogPool = StandardUtilApi.catalogPoolApi(suppliers.getCode());
		if(catalogPool.isSuccess()){
			List<CatalogPoolAttr> result = catalogPool.getResult();
			if(!CollectionUtil.collectionIsEmpty(result)){
				EmallCatalogs  emallCatalogs=null;
				for (CatalogPoolAttr catalogPoolAttr : result) {
					String id = catalogPoolAttr.getId();
					String name = catalogPoolAttr.getName();
					emallCatalogs=new EmallCatalogs();
					emallCatalogs.setEmallCatalogsId(id);
					emallCatalogs.setEmallCode(suppliers.getCode());
					emallCatalogs.setEmallId(suppliers.getId());
					EmallCatalogs ec = emallCatalogsMapper.selectOne(emallCatalogs);
					if(ec==null){
						emallCatalogs.setEmallName(suppliers.getName());
						emallCatalogs.setEmallCatalogsName(name);
						emallCatalogs.setCreatedAt(DateUtilEx.timeToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						emallCatalogs.setUpdatedAt(DateUtilEx.timeToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						PlatformCatalogs pc = platformCatalogsMapper.selectByPrimaryKey(Long.parseLong(id));
						if(pc!=null){
							emallCatalogs.setCatalogsId(pc.getId());
						}
						emallCatalogsMapper.insertSelective(emallCatalogs);
					}
				}
			}
		}
	}
}
