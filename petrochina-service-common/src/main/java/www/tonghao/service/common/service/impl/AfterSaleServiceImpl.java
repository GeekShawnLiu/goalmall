package www.tonghao.service.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.AfterSale;
import www.tonghao.service.common.entity.AfterSaleRefundLog;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.AfterSaleMapper;
import www.tonghao.service.common.mapper.AfterSaleRefundLogMapper;
import www.tonghao.service.common.service.AfterSaleService;
import www.tonghao.service.common.service.SeqService;

/**
 * @Description:售后
 * @date 2019年7月15日
 */
@Service("afterSaleService")
public class AfterSaleServiceImpl extends BaseServiceImpl<AfterSale> implements AfterSaleService{

	@Autowired
	private AfterSaleMapper afterSaleMapper;
	
	@Autowired
	private AfterSaleRefundLogMapper afterSaleRefundLogMapper;
	
	@Autowired
	private SeqService seqService;
	
	/**
	 * @Description:提交售后申请
	 * @date 2019年7月11日
	 */
	@Override
	public Map<String, Object> submitApply(AfterSale afterSale) {
		//String afterSaleSn = createAfterSaleSn();
		afterSale.setStatus(0);//待审核
		//售后编号
		afterSale.setAfterSaleSn(seqService.getSeqSn(DateUtilEx.timeToDate(new Date(),"yyyyMMdd")));
		afterSale.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		int result = afterSaleMapper.insertSelective(afterSale);
		return ResultUtil.resultMessage(result, "");
	}
	
	/**
	 * @Description:创建售后编号
	 * @date 2019年7月12日
	 */
	private String createAfterSaleSn(){
		AfterSale afterSale = afterSaleMapper.selectAfterSaleSnByToDay();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String format = sdf.format(new Date());
		String sn = "";
		if(afterSale !=null){
			String toDayMaxSn = afterSale.getToDayMaxSn();
			Long parseInt = Long.parseLong(toDayMaxSn);
			parseInt ++;
			sn = String.format(format+ "%05d", parseInt);
		}else{
			sn = format + "00001";
		}
		return sn;
	}

	@Override
	public PageInfo<AfterSale> getAfterSaleList(PageBean page, Example example) {
		PageHelper.startPage(page.getPage(), page.getRows());
		List<AfterSale> list = afterSaleMapper.selectByExample(example);
		return new PageInfo<AfterSale>(list);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> resubmitSubmitApply(AfterSale afterSale) {
		if(afterSale.getIsResubmit() ==null || afterSale.getIsResubmit() != 1 || afterSale.getResubmitId() ==null){
			return ResultUtil.error("");
		}
		//String afterSaleSn = createAfterSaleSn();
		afterSale.setStatus(0);//待审核
		//售后编号
		afterSale.setAfterSaleSn(seqService.getSeqSn(DateUtilEx.timeToDate(new Date(),"yyyyMMdd")));
		afterSale.setCreatedAt(DateUtilEx.timeFormat(new Date()));
		int result = afterSaleMapper.insertSelective(afterSale);
		if(result > 0){
			AfterSale as = new AfterSale();
			as.setId(afterSale.getResubmitId());
			as.setIsResubmit(1);
			afterSaleMapper.updateByPrimaryKeySelective(as);
			return ResultUtil.resultMessage(result, "");
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
		return ResultUtil.error("");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> refund(Users user, Long afterSaleId) {
		AfterSale afterSale = new AfterSale();
		afterSale.setId(afterSaleId);
		afterSale.setIsRefund(1);//退款
		afterSale.setStatus(5);//售后完成
		afterSale.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int updateByPrimaryKeySelective = afterSaleMapper.updateByPrimaryKeySelective(afterSale);
		if(updateByPrimaryKeySelective >0){
			AfterSaleRefundLog afterSaleRefundLog = new AfterSaleRefundLog();
			afterSaleRefundLog.setUserId(user.getId());
			afterSaleRefundLog.setAfterSaleId(afterSaleId);
			afterSaleRefundLog.setOperator(user.getRealName());
			afterSaleRefundLog.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			int insert = afterSaleRefundLogMapper.insertSelective(afterSaleRefundLog);
			if(insert > 0){
				return ResultUtil.success("");
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
		return ResultUtil.error("");
	}
}
