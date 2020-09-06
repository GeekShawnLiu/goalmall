package www.tonghao.service.common.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.FileUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.criteria.CriteriaLikeUtil;
import www.tonghao.common.warpper.HttpResponseCode;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Activity;
import www.tonghao.service.common.entity.Integral;
import www.tonghao.service.common.entity.IntegralAudit;
import www.tonghao.service.common.entity.IntegralGrant;
import www.tonghao.service.common.entity.IntegralOrg;
import www.tonghao.service.common.entity.IntegralUser;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.ActivityMapper;
import www.tonghao.service.common.mapper.IntegralAuditMapper;
import www.tonghao.service.common.mapper.IntegralGrantMapper;
import www.tonghao.service.common.mapper.IntegralMapper;
import www.tonghao.service.common.mapper.IntegralOrgMapper;
import www.tonghao.service.common.mapper.IntegralUserMapper;
import www.tonghao.service.common.mapper.UsersMapper;
import www.tonghao.service.common.service.IntegralGrantService;

/**
 * @Description:积分发放
 * @date 2019年5月2日
 */
@Service("integralGrantService")
public class IntegralGrantServiceImpl extends BaseServiceImpl<IntegralGrant> implements IntegralGrantService{
	
	@Autowired
	private IntegralGrantMapper integralGrantMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private IntegralOrgMapper integralOrgMapper;
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private IntegralMapper integralMapper;
	
	@Autowired
	private IntegralAuditMapper integralAuditMapper;
	
	@Autowired
	private IntegralUserMapper integralUserMapper;
	
	/**
	 * @Description:积分发放
	 * @date 2019年5月1日
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> saveInfo(IntegralGrant integralGrant) {
		Long integralId = integralGrant.getIntegralId();
		Integral integral = integralMapper.selectByPrimaryKey(integralId);
		//校验分配的数量是否超过积分池中剩余的数量
		if(integral !=null){
			//要发放的积分 > 剩余积分额度
			if((integralGrant.getIntegralNum()).compareTo(integral.getTotal().subtract(integral.getGrantQuota())) == 1){
				return ResultUtil.error("对不起，超出总额");
			}
		}else{
			return ResultUtil.error("操作失败");
		}
		
		integralGrant.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		integralGrant.setIsDelete(0);
		integralGrant.setState(0);
		int result = integralGrantMapper.insert(integralGrant);
		BigDecimal integralNum = integralGrant.getIntegralNum();
		if(result > 0){
			BigDecimal grantQuota = integral.getGrantQuota();
			BigDecimal addGrantQuota = grantQuota.add(integralNum);
			
			Integral addIntegral = new Integral();
			addIntegral.setId(integralId);
			addIntegral.setGrantQuota(addGrantQuota);
			addIntegral.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			int insertSelective = integralMapper.updateByPrimaryKeySelective(addIntegral);
			if(insertSelective > 0){
				return ResultUtil.resultMessage(insertSelective, "");
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
				return ResultUtil.error("");
			}
		}
		return ResultUtil.error("");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> updateInfo(IntegralGrant integralGrant) {
		//新的发放的积分
		BigDecimal newIntegralNum = integralGrant.getIntegralNum();
		Long integralId = integralGrant.getIntegralId();
		Integral integral = integralMapper.selectByPrimaryKey(integralId);
		//校验分配的数量是否超过积分池中剩余的数量
		if(integral !=null){
			if((newIntegralNum).compareTo(integral.getTotal().subtract(integral.getGrantQuota())) == 1){
				return ResultUtil.error("对不起，超出总额");
			}
		}else{
			return ResultUtil.error("操作失败");
		}
		
		integralGrant.setState(0);
		integralGrant.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = integralGrantMapper.updateByPrimaryKeySelective(integralGrant);
		if(result > 0){
			BigDecimal grantQuota = integral.getGrantQuota();
			BigDecimal addGrantQuota  = grantQuota.add(newIntegralNum);
			
			Integral addIntegral = new Integral();
			addIntegral.setId(integralId);
			addIntegral.setGrantQuota(addGrantQuota);
			addIntegral.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			int insertSelective = integralMapper.updateByPrimaryKeySelective(addIntegral);
			if(insertSelective > 0){
				return ResultUtil.resultMessage(insertSelective, "");
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
		return ResultUtil.error("");
	}

/*	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> updateInfo(IntegralGrant integralGrant) {
		IntegralGrant integralGrantInfo = integralGrantMapper.selectByPrimaryKey(integralGrant.getId());
		//之前发放的积分
		BigDecimal oldIntegralNum = integralGrantInfo.getIntegralNum();
		
		//修改的发放的积分
		BigDecimal newIntegralNum = integralGrant.getIntegralNum();
		Long integralId = integralGrant.getIntegralId();
		Integral integral = integralMapper.selectByPrimaryKey(integralId);
		//校验分配的数量是否超过积分池中剩余的数量
		if(integral !=null){
			//要发放的积分 > 剩余积分额度
			if((newIntegralNum.subtract(oldIntegralNum)).compareTo(integral.getTotal().subtract(integral.getGrantQuota())) == 1){
				return ResultUtil.error("对不起，超出总额");
			}
		}else{
			return ResultUtil.error("操作失败");
		}
		
		integralGrant.setState(0);
		integralGrant.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = integralGrantMapper.updateByPrimaryKeySelective(integralGrant);
		if(result > 0){
			BigDecimal grantQuota = integral.getGrantQuota();
			BigDecimal addGrantQuota  = BigDecimal.ZERO;
			
			//新积分 < 旧积分
			if(newIntegralNum.compareTo(oldIntegralNum) == -1){
				BigDecimal surplusNum =  oldIntegralNum.subtract(newIntegralNum);
				addGrantQuota = grantQuota.subtract(surplusNum);
			}
			//新积分 > 旧积分
			if(newIntegralNum.compareTo(oldIntegralNum) == 1){
				BigDecimal surplusNum =  newIntegralNum.subtract(oldIntegralNum);
				addGrantQuota = grantQuota.add(surplusNum);
			}
			
			//新积分 = 旧积分
			if(newIntegralNum.compareTo(oldIntegralNum) == 0){
				addGrantQuota = grantQuota;
			}
			
			Integral addIntegral = new Integral();
			addIntegral.setId(integralId);
			addIntegral.setGrantQuota(addGrantQuota);
			addIntegral.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			int insertSelective = integralMapper.updateByPrimaryKeySelective(addIntegral);
			if(insertSelective > 0){
				return ResultUtil.resultMessage(insertSelective, "");
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
				return ResultUtil.error("");
			}
		}else{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
			return ResultUtil.error("");
		}
	}*/
	
	
	/**
	 * @Description:获取机构下的用户
	 * @date 2019年4月30日
	 */
	@Override
	public List<Users> getUserList(Long orgId, String realName) {
		Example example = new Example(Users.class);
		Map<String, Object> maplike = new HashMap<String, Object>();
		Criteria createCriteria = example.createCriteria();
		maplike.put("realName", realName);
		createCriteria.andEqualTo("isDelete", 0);
		createCriteria.andEqualTo("depId", orgId);
		CriteriaLikeUtil.criteriaLike(createCriteria, maplike);
		List<Users> userList = usersMapper.selectByExample(example);
		return userList;
	}

	/**
	 * @Description:积分id获取机构
	 * @date 2019年5月1日
	 */
	@Override
	public List<IntegralOrg> selectIntegralOrg(Long integralId) {
		List<IntegralOrg> selectIntegralOrg = integralOrgMapper.selectIntegralOrg(integralId);
		return selectIntegralOrg;
	}

	/**
	 * @Description:积分id获取活动
	 * @date 2019年5月1日
	 */
	@Override
	public Activity selectActivityByIntegralId(Long integralId) {
		Activity selectActivityByIntegralId = activityMapper.selectActivityByIntegralId(integralId);
		return selectActivityByIntegralId;
	}

	/**
	 * @Description:积分发放审核通过
	 * @date 2019年5月2日
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> audit(Long[] integralGrantIds, Users user) {
		int reuslt = 0;
		if(integralGrantIds !=null && integralGrantIds.length > 0){
			for(Long integralGrantId : integralGrantIds){
				IntegralGrant integralGrant = new IntegralGrant();
				integralGrant.setState(2);//通过审核
				integralGrant.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				integralGrant.setAuditAt(DateUtilEx.timeFormat(new Date()));
				
				Example example = new Example(IntegralGrant.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("state", 1);
				createCriteria.andEqualTo("id", integralGrantId);
				createCriteria.andEqualTo("isDelete", 0);
				int updateIntegralGrant = integralGrantMapper.updateByExampleSelective(integralGrant, example);
				//int updateIntegralGrant = integralGrantMapper.updateByPrimaryKeySelective(integralGrant);
				if(updateIntegralGrant > 0){
					/**
					 * 积分到账
					 */
					int updateOrAddIntegralUser = updateOrAddIntegralUser(integralGrantId);
					if(updateOrAddIntegralUser >0){
						reuslt ++;
					}
					
					/**
					 *审核记录
					 */
					IntegralAudit integralAudit = new IntegralAudit();
					integralAudit.setIntegralGrantId(integralGrantId);
					integralAudit.setAuditorId(user.getId());
					integralAudit.setAuditor(user.getRealName());
					integralAudit.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					integralAuditMapper.insertSelective(integralAudit);
				}
			}
		}
		return ResultUtil.resultMessage(HttpResponseCode.OK, "", reuslt);
	}
	
	/**
	 * @Description:更新或新增用户积分
	 * @date 2019年5月2日
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateOrAddIntegralUser(Long integralGrantId){
		IntegralGrant integralGrant = integralGrantMapper.selectByPrimaryKey(integralGrantId);
		int result = 0;
		if(integralGrant !=null){
			BigDecimal integralNum = integralGrant.getIntegralNum();//发放的积分
			Long activityId = integralGrant.getActivityId();//活动id
			Long integralId = integralGrant.getIntegralId();//登记积分id
			Long userId = integralGrant.getUserId();//用户id
			
			IntegralUser iu = new IntegralUser();
			iu.setUserId(userId);
			//iu.setIntegralId(integralId);
			iu.setActivityId(activityId);
			
			IntegralUser selectOne = integralUserMapper.selectOne(iu);
			if(selectOne !=null){
				BigDecimal balance = selectOne.getBalance();
				BigDecimal total = selectOne.getTotal();
				
				BigDecimal newTotal = total.add(integralNum);//增加到总积分
				BigDecimal newBalance = balance.add(integralNum); //增加到余额
				
				/**
				 * 更新用户的积分
				 */
				IntegralUser integralUser = new IntegralUser();
				integralUser.setId(selectOne.getId());
				integralUser.setTotal(newTotal);
				integralUser.setBalance(newBalance);
				integralUser.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				result = integralUserMapper.updateByPrimaryKeySelective(integralUser);
			}else{
				iu.setIntegralId(integralId);
				iu.setCreatedAt(DateUtilEx.timeFormat(new Date()));
				iu.setBalance(integralNum);
				iu.setTotal(integralNum);
				iu.setActivityId(activityId);
				result = integralUserMapper.insertSelective(iu);
			}
			
			/**
			 * 更新积分登记表中的确认额度
			 */
			if(result > 0){
				Integral IntegralInfo = integralMapper.selectByPrimaryKey(integralId);
				if(IntegralInfo !=null){
					BigDecimal confirmQuota = IntegralInfo.getConfirmQuota();
					BigDecimal newConfirmQuota = confirmQuota.add(integralNum);
					
					Integral integral = new Integral();
					integral.setId(integralId);
					integral.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					integral.setConfirmQuota(newConfirmQuota);
					integralMapper.updateByPrimaryKeySelective(integral);
				}
			}
		}
		return result;
	}

	/**
	 * @Description:积分发放审核不通过
	 * @date 2019年5月2日
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> auditNoPass(Long[] integralGrantIds, Users user) {
		int reuslt = 0;
		if(integralGrantIds !=null && integralGrantIds.length > 0){
			for(Long integralGrantId : integralGrantIds){
				IntegralGrant integralGrant = new IntegralGrant();
				integralGrant.setId(integralGrantId);
				integralGrant.setState(3);//审核不通过
				integralGrant.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
				integralGrant.setAuditAt(DateUtilEx.timeFormat(new Date()));
				int updateIntegralGrant = integralGrantMapper.updateByPrimaryKeySelective(integralGrant);
				if(updateIntegralGrant > 0){
					IntegralGrant integralGrantInfo = integralGrantMapper.selectByPrimaryKey(integralGrantId);
					BigDecimal integralNum = integralGrantInfo.getIntegralNum();
					Long integralId = integralGrantInfo.getIntegralId();
					
					Integral integralInfo = integralMapper.selectByPrimaryKey(integralId);
					BigDecimal grantQuota = integralInfo.getGrantQuota();
					BigDecimal newGrantQuota = grantQuota.subtract(integralNum);
					
					//更新发放的积分
					Integral integral = new Integral();
					integral.setId(integralId);
					integral.setGrantQuota(newGrantQuota);
					int updateByPrimaryKeySelective = integralMapper.updateByPrimaryKeySelective(integral);
					
					if(updateByPrimaryKeySelective >0){
						reuslt ++;
						/**
						 *审核记录
						 */
						IntegralAudit integralAudit = new IntegralAudit();
						integralAudit.setIntegralGrantId(integralGrantId);
						integralAudit.setAuditorId(user.getId());
						integralAudit.setAuditor(user.getRealName());
						integralAudit.setCreatedAt(DateUtilEx.timeFormat(new Date()));
						integralAuditMapper.insertSelective(integralAudit);
					}else{
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
						return ResultUtil.error("审核失败");
					}
				}
			}
		}
		return ResultUtil.resultMessage(HttpResponseCode.OK, "", reuslt);
	}

	/**
	 * @Description:删除积分发放
	 * @date 2019年5月3日
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> deleteIntegralGrant(Long integralGrantId) {
		IntegralGrant integralGrant = integralGrantMapper.selectByPrimaryKey(integralGrantId);
		if(integralGrant !=null && (integralGrant.getState() == 0 || integralGrant.getState() == 3)){
			BigDecimal integralNum = integralGrant.getIntegralNum();
			Long integralId = integralGrant.getIntegralId();
			
			IntegralGrant ig = new IntegralGrant();
			ig.setId(integralGrantId);
			ig.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			ig.setIsDelete(1);
			int update = integralGrantMapper.updateByPrimaryKeySelective(ig);
			
			/**
			 * 删除成功后 退还给积分池中
			 */
			if(update > 0){
				Integral integralInfo = integralMapper.selectByPrimaryKey(integralId);
				if(integralInfo !=null){
					BigDecimal grantQuota = integralInfo.getGrantQuota();
					BigDecimal addGrantQuota = grantQuota.subtract(integralNum);
					
					Integral integral = new Integral();
					integral.setGrantQuota(addGrantQuota);
					integral.setId(integralInfo.getId());
					integral.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					int result = integralMapper.updateByPrimaryKeySelective(integral);
					if(result > 0){
						return ResultUtil.success("删除成功");
					}else{
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
						return ResultUtil.error("删除失败");
					}
				}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
					return ResultUtil.error("删除失败");
				}
			}
		}
		return ResultUtil.error("删除失败");
	}

	/**
	 * @Description:Excel批量导入积分发放
	 * @date 2019年5月8日
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> importIntegralGrantExcle(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		MultipartFile multipartFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(FileUtilEx.multipartFileToTmpFile(multipartFile));
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.error("导入失败");
		}
		XSSFSheet sheet = workbook.getSheetAt(0);
		int i=1;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int last_num=sheet.getLastRowNum();
		while (i <= last_num) {
			XSSFRow row = sheet.getRow(i);
			int rowNum = row.getRowNum() + 1;
			if (row == null) {
				break;
			}
			String mobile =null;
			String email =null;
			String name = null;
			Double num = null;
			try {
				if(row.getCell(0) != null){
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				}
				if(row.getCell(1) != null){
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				}
				if(row.getCell(2) != null){
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				}
				if(row.getCell(3) != null){
					row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);
				}
				
				mobile = row.getCell(0)==null? null : row.getCell(0).getStringCellValue();
				email = row.getCell(1)==null? null : row.getCell(1).getStringCellValue();
				name = row.getCell(2)==null? null : row.getCell(2).getStringCellValue();
				num = row.getCell(3)==null? null : row.getCell(3).getNumericCellValue();
			} catch (Exception e) {
				e.printStackTrace();
				return ResultUtil.error("第" + rowNum +"行，格式有误");
			}
			if(mobile ==null || "".equals(mobile.trim())){
				return ResultUtil.error("第" + rowNum +"行，手机号有空值");
			}else if(email ==null || "".equals(email.trim())){
				return ResultUtil.error("第" + rowNum +"行，邮箱有空值");
			}else if(name ==null || "".equals(name.trim())){
				return ResultUtil.error("第" + rowNum +"行，积分名称有空值");
			}else if(num ==null || num <= 0){
				return ResultUtil.error("第" + rowNum +"行，积分数量格式有误");
			}
			
			Users users = new Users();
			users.setEmail(email.trim());
			users.setMobile(mobile.trim());
			users.setIsDelete(0);
			try {
				Users usersInfo = usersMapper.selectOne(users);
				if(usersInfo == null){
					return ResultUtil.error("第" + rowNum +"行，用户不存在");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultUtil.error("第" + rowNum +"行，用户有误或多条数据");
			}
			
			
			Integral integral = new Integral();
			integral.setName(name);
			integral.setIsDelete(0);
			try {
				Integral integralInfo = integralMapper.selectOne(integral);
				if(integralInfo ==null){
					return ResultUtil.error("第" + rowNum +"行，积分不存在");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultUtil.error("第" + rowNum +"行，积分有误或多条数据");
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mobile);
			map.put("email", email);
			map.put("name", name);
			map.put("num", num);
			list.add(map);
			i++;
		}
		
		int importNum=0;
		//导入批次
		String batch = getBatch();
		try {
			if(list.size() >0){
				for(Map<String, Object> map :  list){
					String mobile = (String) map.get("mobile");
					String email = (String) map.get("email");
					String name = (String) map.get("name");
					Double num = (Double) map.get("num");
					BigDecimal integralNum = new BigDecimal(num + "");
					
					/**
					 *获取用户
					 */
					Users users = new Users();
					users.setEmail(email.trim());
					users.setMobile(mobile.trim());
					users.setIsDelete(0);
					Users usersInfo = usersMapper.selectOne(users);
					if(usersInfo == null || usersInfo.getId() ==null || usersInfo.getRealName() ==null || usersInfo.getDepId() == null || usersInfo.getDepName() ==null){
						return ResultUtil.error("导入失败,请检查用户信息");
					}
					Long userId = usersInfo.getId(); //用户id
					String realName = usersInfo.getRealName(); //用户姓名
					Long orgId = usersInfo.getDepId(); //机构id
					String orgName = usersInfo.getDepName(); //机构名称
					
					/**
					 * 获取积分
					 */
					Integral integral = new Integral();
					integral.setIsDelete(0);
					integral.setName(name);
					Integral integralInfo = integralMapper.selectOne(integral);
					if(integralInfo == null || integralInfo.getId() ==null || integralInfo.getName() == null || integralInfo.getActivityId() ==null){
						return ResultUtil.error("导入失败,请检查积分信息");
					}
					Long integralId = integralInfo.getId(); //积分id
					String integralName = integralInfo.getName(); //积分名称
					Long activityId = integralInfo.getActivityId(); //活动id
					
					/**
					 * 获取活动
					 */
					Activity activityInfo = activityMapper.selectByPrimaryKey(activityId);
					if(activityInfo == null || activityInfo.getName() ==null){
						return ResultUtil.error("导入失败,请检查活动信息");
					}
					String activityName = activityInfo.getName(); //活动名称
					
					IntegralGrant integralGrant = new IntegralGrant();
					integralGrant.setBatch(batch);
					integralGrant.setIntegralId(integralId);
					integralGrant.setIntegralName(integralName);
					integralGrant.setActivityId(activityId);
					integralGrant.setActivityName(activityName);
					integralGrant.setUserId(userId);
					integralGrant.setUserRealname(realName);
					integralGrant.setOrgId(orgId);
					integralGrant.setOrgName(orgName);
					integralGrant.setState(0);
					integralGrant.setIntegralNum(integralNum);
					integralGrant.setIsDelete(0);
					integralGrant.setCreatedAt(DateUtilEx.timeFormat(new Date()));
					int insertIntegralGrant = integralGrantMapper.insertSelective(integralGrant);
					if(insertIntegralGrant > 0){
						BigDecimal grantQuota = integralInfo.getGrantQuota();
						BigDecimal addGrantQuota = grantQuota.add(integralNum);
						BigDecimal total = integralInfo.getTotal();
						if(total.compareTo(addGrantQuota) == 1 || total.compareTo(addGrantQuota) == 0){
							Integral addIntegral = new Integral();
							addIntegral.setId(integralId);
							addIntegral.setGrantQuota(addGrantQuota);
							addIntegral.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
							int insertSelective = integralMapper.updateByPrimaryKeySelective(addIntegral);
							if(insertSelective > 0){
								importNum ++;
							}else{
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
								return ResultUtil.error("导入失败");
							}
						}else{
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
							return ResultUtil.error(integralName + "，该积分超出总额");
						}
					}else{
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
						return ResultUtil.error("导入失败");
					}
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
			e.printStackTrace();
			return ResultUtil.error("导入失败"); 
		}
		return ResultUtil.success("成功导入" +importNum+ "条数据");
	}
	
	/**
	 * @Description:获取批次
	 * @date 2019年5月13日
	 */
	private String getBatch(){
		IntegralGrant selectBatchByToday = integralGrantMapper.selectBatchByToday();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String format = sdf.format(new Date());
		String batch = "";
		if(selectBatchByToday !=null){
			String batchNum = selectBatchByToday.getBatchNum();
			Long parseInt = Long.parseLong(batchNum);
			parseInt ++;
			batch = String.format(format+ "%04d", parseInt);
		}else{
			batch = format + "0001";
		}
		return batch;
	}

	@Override
	public PageInfo<IntegralGrant> selectList(PageBean page, Map<String, Object> map) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<IntegralGrant> selectList = integralGrantMapper.selectList(map);
		return new PageInfo<IntegralGrant>(selectList);
	}

	@Override
	public Map<String, Object> conditionSubmitAudit(Map<String, Object> map) {
		List<IntegralGrant> selectList = integralGrantMapper.selectList(map);
		int result = 0;
		if(CollectionUtils.isNotEmpty(selectList)){
			for(IntegralGrant integralGrant : selectList){
				if(integralGrant.getState() == 0){
					IntegralGrant ig = new IntegralGrant();
					ig.setId(integralGrant.getId());
					ig.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
					ig.setState(1);
					ig.setSubmitAudit(DateUtilEx.timeFormat(new Date()));
					int update = integralGrantMapper.updateByPrimaryKeySelective(ig);
					if(update > 0){
						result ++;
					}
				}
			}
		}
		return ResultUtil.success("成功提交审核：" + result + "个");
	}
}
