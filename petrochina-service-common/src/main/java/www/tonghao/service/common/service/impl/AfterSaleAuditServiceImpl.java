package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.AfterSale;
import www.tonghao.service.common.entity.AfterSaleAudit;
import www.tonghao.service.common.mapper.AfterSaleAuditMapper;
import www.tonghao.service.common.mapper.AfterSaleMapper;
import www.tonghao.service.common.service.AfterSaleAuditService;

@Service("afterSaleAuditService")
public class AfterSaleAuditServiceImpl extends BaseServiceImpl<AfterSaleAudit> implements AfterSaleAuditService{

	@Autowired
	private AfterSaleAuditMapper afterSaleAuditMapper;
	
	@Autowired
	private AfterSaleMapper afterSaleMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> audit(AfterSaleAudit afterSaleAudit, Integer auditType, String supplierAddress) {
		AfterSale afterSale = new AfterSale();
		afterSale.setStatus(auditType);
		afterSale.setId(afterSaleAudit.getAfterSaleId());
		afterSale.setSupplierAddress(supplierAddress);
		afterSale.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
		int result = afterSaleMapper.updateByPrimaryKeySelective(afterSale);
		if(result >0){
			int insertSelective = afterSaleAuditMapper.insertSelective(afterSaleAudit);
			return ResultUtil.resultMessage(insertSelective, ""); 
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
		return ResultUtil.error("");
	}

	@Override
	public AfterSaleAudit selectByCreatedAtMax(Long afterSaleId) {
		return afterSaleAuditMapper.selectByCreatedAtMax(afterSaleId);
	}

}
