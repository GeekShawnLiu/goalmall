package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Integral;
import www.tonghao.service.common.entity.IntegralOrg;
import www.tonghao.service.common.entity.Organization;
import www.tonghao.service.common.mapper.IntegralMapper;
import www.tonghao.service.common.mapper.IntegralOrgMapper;
import www.tonghao.service.common.mapper.OrganizationMapper;
import www.tonghao.service.common.service.IntegralService;

/**
 * @Description:积分
 * @date 2019年4月26日
 */
@Service("integralService")
public class IntegralServiceImpl extends BaseServiceImpl<Integral> implements IntegralService{
	@Autowired
	private IntegralMapper integralMapper;
	
	@Autowired
	private IntegralOrgMapper integralOrgMapper;
	
	@Autowired
	private OrganizationMapper organizationMapper;
	
	/**
	 * @Description:登记积分
	 * @date 2019年4月26日
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> registrationIntegral(Integral integral) {
		Map<String, Object> checkInfo = checkInfo(integral);
		
		if(MapUtils.isNotEmpty(checkInfo)){
			return ResultUtil.resultMessage(HttpResponseCode.NOT_ACCEPTABLE, "校验失败", checkInfo);
		}
		
		integral.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		integral.setIsDelete(0);
		int result = integralMapper.insertSelective(integral);
		//Long integralId = integral.getId();
		
		/**
		 * 关联的机构
		 */
		/*for(IntegralOrg integralOrg : integral.getIntegralOrgList()){
			integralOrg.setIntegralId(integralId);
			integralOrg.setCreatedAt(date);
			integralOrgMapper.insertSelective(integralOrg);
		}*/
		
		return ResultUtil.resultMessage(result, "");
	}
	
	/**
	 * @Description:信息校验
	 * @date 2019年4月26日
	 */
	private Map<String, Object> checkInfo(Integral integral){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(integral.getName())){
			Example example = new Example(Integral.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("isDelete", 0);
			createCriteria.andEqualTo("name", integral.getName());
			createCriteria.andNotEqualTo("id", integral.getId());
			List<Integral> select = integralMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(select)){
				map.put("error_name", "名称重复");
			}
		}else{
			map.put("error_name", "请填写积分名称");
		}
		if(StringUtils.isBlank(integral.getDescription())){
			map.put("error_description", "请填写积分描述");
		}
		/*if(integral.getIntegralOrgList() ==null || integral.getIntegralOrgList().size() == 0 ){
			map.put("error_description", "请选择积分登记单位");
		}*/
		if(integral.getOrgId() == null){
			map.put("error_orgId", "请选择积分登记单位");
		}
		if(integral.getTotal() == null){
			map.put("error_description", "请填写分配总额");
		}
		if(integral.getActivityId() == null){
			map.put("error_activityId", "请选择活动");
		}
		return map;
	}

	/**
	 * @Description:积分登记修改
	 * @date 2019年4月27日
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> updateIntegral(Integral integral) {
		Map<String, Object> checkInfo = checkInfo(integral);
		if(MapUtils.isNotEmpty(checkInfo)){
			return ResultUtil.resultMessage(HttpResponseCode.NOT_ACCEPTABLE, "校验失败", checkInfo);
		}
		integral.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = integralMapper.updateByPrimaryKeySelective(integral);
		
		/**
		 * 关联的机构
		 */
		/*if(result >0){
			IntegralOrg integralOrg = new IntegralOrg();
			integralOrg.setIntegralId(integral.getId());
			int delete = integralOrgMapper.delete(integralOrg);
			if(delete > 0){
				for(IntegralOrg org : integral.getIntegralOrgList()){
					org.setIntegralId(integral.getId());
					org.setCreatedAt(date);
					integralOrgMapper.insertSelective(org);
				}
			}
		}*/
		return ResultUtil.resultMessage(result, "");
	}

	/**
	 * @Description:积分登记列表
	 * @date 2019年4月30日
	 */
	@Override
	public PageInfo<Integral> selectIntegralList(PageBean page, Integral integral) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<Integral> selectList = integralMapper.selectList(integral);
		/*if(CollectionUtils.isNotEmpty(selectList)){
			for(Integral i : selectList){
				StringBuilder sb = new StringBuilder();
				List<IntegralOrg> integralOrgList = i.getIntegralOrgList();
				if(CollectionUtils.isNotEmpty(integralOrgList)){
					for(IntegralOrg integralOrg : integralOrgList){
						sb.append(integralOrg.getOrgName()+ "，");
					}
				}
				i.setOrgName(sb.substring(0, sb.length()-1));
			}
		}*/
		return new PageInfo<Integral>(selectList);
	}

	@Override
	public List<Organization> getOrgTree(Long integralId) {
		Example example=new Example(Organization.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("isDelete", 0);
		List<Organization> orgList = organizationMapper.selectByExample(example);
		
		/*if(integralId !=null){
			for(Organization org : orgList){
				if("false".equals(org.getIsParent())){
					Long orgId = org.getId();
					IntegralOrg integralOrg = new IntegralOrg();
					integralOrg.setIntegralId(integralId);
					integralOrg.setOrgId(orgId);
					List<IntegralOrg> activityOrgList = integralOrgMapper.select(integralOrg);
					if(activityOrgList !=null && activityOrgList.size() > 0){
						org.setChecked(true);
					}
				}
			}
		}*/
		return orgList;
	}

	@Override
	public List<Integral> selectByUsableActivity() {
		return integralMapper.selectByUsableActivity();
	}
}
