package www.tonghao.mall.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.mall.entity.Floor;
import www.tonghao.mall.entity.FloorBrand;
import www.tonghao.mall.entity.FloorPlatformCatalog;
import www.tonghao.mall.entity.MallProducts;
import www.tonghao.mall.mapper.FloorBrandMapper;
import www.tonghao.mall.mapper.FloorMapper;
import www.tonghao.mall.mapper.FloorPlatformCatalogMapper;
import www.tonghao.mall.service.FloorService;
import www.tonghao.mall.service.MallProductService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Users;

import com.beust.jcommander.internal.Maps;

/**
 * 楼层
 * @author developer001
 *
 */
@Service("mallFloorService")
public class FloorServiceImpl extends BaseServiceImpl<Floor> implements FloorService{
    
    @Autowired
    FloorMapper floorMapper;
    @Override
    public Floor findById(Long id) {
        return floorMapper.findById(id);
    }
	
	@Autowired
	private FloorBrandMapper floorBrandMapper;
	
	@Autowired
	private FloorPlatformCatalogMapper floorPlatformCatalogMapper;
	
	@Autowired
	private MallProductService mallProductService;
	
	@Override
	public List<Floor> processList(List<Floor> list) {
		if(list==null){
			return null;
		}
		for(Floor floor:list){
			floor.setFloorBrands(findBrandsByFloor(floor.getId()));
			floor.setFloorPlatformCatalogs(findCatalogsByFloor(floor.getId()));
		}
		return list;
	}
	
	/**
	 * 查询楼层品牌
	 * @param floorId
	 * @return
	 */
	private List<FloorBrand> findBrandsByFloor(Long floorId){
		Example example = new Example(FloorBrand.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("floorId", floorId);
		example.setOrderByClause(" sort asc ");
		RowBounds rowbounds = new RowBounds(0,6);
		return floorBrandMapper.selectByExampleAndRowBounds(example, rowbounds);
	}
	
	private List<FloorPlatformCatalog> findCatalogsByFloor(Long floorId){
		return floorPlatformCatalogMapper.findMallByFloorId(floorId);
	}
	
	public List<MallProducts> findCatalogProducts(Long catalogId, Users user){
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("mcid", catalogId);
		queryfilter.put("andCidEq", true);
		//queryfilter.put("limitNum", 7);
		queryfilter.put("orderByCondition", "tb.created_at desc");
		//根据机构判断商品展示
//		if(user != null && user.getType() != null){
//			if(user.getType() == 1){
//				queryfilter.put("orgId", user.getDepId());
//			}else if(user.getType() == 4){
//				queryfilter.put("supplierId", user.getTypeId());
//			}
//		}
		entity.setQueryfilter(queryfilter);
		return mallProductService.findListByEntity(entity);
	}
	
	public List<MallProducts> findCatalogBenefitProducts(Long[] catalogIds, Users user){
		Products entity = new Products();
		Map<String, Object> queryfilter = Maps.newHashMap();
		queryfilter.put("inCatalogIds", catalogIds);
		queryfilter.put("orderByCondition", "(1-tb.price/tb.market_price) desc");
		//根据机构判断商品展示
//		if(user != null && user.getType() != null){
//			if(user.getType() == 1){
//				queryfilter.put("orgId", user.getDepId());
//			}else if(user.getType() == 4){
//				queryfilter.put("supplierId", user.getTypeId());
//			}
//		}
		entity.setQueryfilter(queryfilter);
		return mallProductService.findBenefitProductsByEntity(entity);
	}

	public List<MallProducts> getFloorProducts(Long floorId, Integer num, Users user){
		Floor floor = floorMapper.findById(floorId);
		if(floor == null){
			return new ArrayList<MallProducts>();
		}
		List<MallProducts> productsList = new ArrayList<MallProducts>();
		if(!CollectionUtils.isEmpty(floor.getFloorPlatformCatalogs())){
			List<FloorPlatformCatalog> floorPlatformCatalogs = floor.getFloorPlatformCatalogs();
			f1: for (FloorPlatformCatalog floorPlatformCatalog : floorPlatformCatalogs) {
				Products entity = new Products();
				Map<String, Object> queryfilter = Maps.newHashMap();
				queryfilter.put("mcid", floorPlatformCatalog.getPlatformCatalogId());
				queryfilter.put("andCidEq", true);
				queryfilter.put("limitNum", num);
				queryfilter.put("orderByCondition", "tb.created_at desc");
				//根据机构判断商品展示
				if(user != null && user.getType() != null){
					if(user.getType() == 1){
						queryfilter.put("orgId", user.getDepId());
					}else if(user.getType() == 4){
						queryfilter.put("supplierId", user.getTypeId());
					}
				}
				entity.setQueryfilter(queryfilter);
				List<MallProducts> findListByEntity = mallProductService.findListByEntity(entity);
				if(!CollectionUtils.isEmpty(findListByEntity)){
					for (MallProducts mallProducts : findListByEntity) {
						productsList.add(mallProducts);
						if(productsList.size() >= num){
							break f1;
						}
					}
				}
			}
		}
		return productsList;
	}
}
