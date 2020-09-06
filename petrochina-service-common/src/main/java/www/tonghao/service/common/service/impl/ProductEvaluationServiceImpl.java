package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.ProductEvaluation;
import www.tonghao.service.common.entity.ProductEvaluationFile;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.ProductEvaluationFileMapper;
import www.tonghao.service.common.mapper.ProductEvaluationMapper;
import www.tonghao.service.common.service.ProductEvaluationService;

@Service("productEvaluationService")
public class ProductEvaluationServiceImpl extends BaseServiceImpl<ProductEvaluation> implements ProductEvaluationService{

	@Autowired
	private ProductEvaluationMapper productEvaluationMapper;
	
	@Autowired
	private ProductEvaluationFileMapper productEvaluationFileMapper;
	
	@Override
	public Map<String, Object> save(ProductEvaluation productEvaluation, Users user) {
		if(productEvaluation.getProductId() == null){
			return ResultUtil.error("请选择商品");
		}
		productEvaluation.setIsDelete(0);
		productEvaluation.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		productEvaluation.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		productEvaluation.setUserId(user.getId());
		int insertSelective = productEvaluationMapper.insertSelective(productEvaluation);
		if(insertSelective > 0){
			List<ProductEvaluationFile> fileList = productEvaluation.getFileList();
			if(!CollectionUtils.isEmpty(fileList)){
				fileList.forEach(file -> {
					file.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					file.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					file.setIsDelete(0);
					file.setProductId(productEvaluation.getProductId());
					file.setProductEvaluationId(productEvaluation.getId());
					productEvaluationFileMapper.insertSelective(file);
				});
			}
			return ResultUtil.success("添加成功");
		}else{
			return ResultUtil.error("添加失败");
		}
	}
	
	
	@Override
	public List<ProductEvaluation> productEvaluationList(Map<String, Object> map) {
		return productEvaluationMapper.findByProductId(map);
	}

	@Override
	public ProductEvaluation productEvaluationView(Map<String, Object> map) {
		List<ProductEvaluation> list = productEvaluationMapper.findByMap(map);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}


	@Override
	public ProductEvaluation getProductRate(Long productId) {
		return productEvaluationMapper.findRateByProductId(productId);
	}
}
