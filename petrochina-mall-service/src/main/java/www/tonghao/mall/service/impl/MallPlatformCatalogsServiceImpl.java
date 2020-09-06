package www.tonghao.mall.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.mall.service.MallPlatformCatalogsService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.PlatformCatalogs;
import www.tonghao.service.common.mapper.PlatformCatalogsMapper;

import com.google.common.collect.Maps;

@Service("mallPlatformCatalogsService")
@Transactional
public class MallPlatformCatalogsServiceImpl extends BaseServiceImpl<PlatformCatalogs> implements MallPlatformCatalogsService {

	@Autowired
	private PlatformCatalogsMapper platformCatalogsMapper;
	
	public List<PlatformCatalogs> getPlatformCatalogs(){
	    PlatformCatalogs entity = new PlatformCatalogs();
        Map<String,Object> queryfilter = Maps.newHashMap();
        queryfilter.put("likeSystems", "mall-direct");
        entity.setQueryfilter(queryfilter);
        entity.setIsDelete(0);
        List<PlatformCatalogs> list = platformCatalogsMapper.findListByEntity(entity);
        return list;
	}

}
