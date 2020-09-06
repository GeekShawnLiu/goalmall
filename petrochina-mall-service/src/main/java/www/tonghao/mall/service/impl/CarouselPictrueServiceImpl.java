package www.tonghao.mall.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.mall.entity.CarouselPictrue;
import www.tonghao.mall.mapper.CarouselPictrueMapper;
import www.tonghao.mall.service.CarouselPictrueService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Users;

import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("carouselPictrueService")
@Transactional
public class CarouselPictrueServiceImpl extends BaseServiceImpl<CarouselPictrue> implements CarouselPictrueService{

	@Autowired
	private CarouselPictrueMapper carouselPictrueMapper;
	
	@Override
	public PageInfo<CarouselPictrue> selectList(CarouselPictrue carouselPictrue) {
		List<CarouselPictrue> selectList = carouselPictrueMapper.selectList(carouselPictrue);
		return new PageInfo<CarouselPictrue>(selectList);
	}

	@Override
	public List<CarouselPictrue> selectIndexPictrue(Integer type, Users user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		if(user != null){
			map.put("userId", user.getId());
		}
		List<CarouselPictrue> list = carouselPictrueMapper.selectIndexPictrueByMap(map);
		if(type != null && (type == 4 || type == 5 || type == 6) && user != null){
			map.put("orgId", user.getDepId());
			List<CarouselPictrue> selectActivityPictrue = carouselPictrueMapper.selectActivityPictrue(map);
			if(CollectionUtils.isNotEmpty(selectActivityPictrue)){
				list.addAll(selectActivityPictrue);
			}
		}
		return list;
	}

	@Override
	public String selectMaxRank(Long id, Long rank) {
		Example example = new Example(CarouselPictrue.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		if(id !=null){
			createCriteria.andNotEqualTo("id", id);
		}
		createCriteria.andEqualTo("rank", rank);
		List<CarouselPictrue> selectByExample = carouselPictrueMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(selectByExample)){
			Long maxRank = carouselPictrueMapper.selectMaxRank();
			return "排序已存在，最大已排序到 " + maxRank;
		}
		return null;
	}

}
