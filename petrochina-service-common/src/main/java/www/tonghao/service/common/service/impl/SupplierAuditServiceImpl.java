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
import www.tonghao.service.common.entity.SupplierAudit;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.mapper.SupplierAuditMapper;
import www.tonghao.service.common.mapper.SuppliersMapper;
import www.tonghao.service.common.service.SupplierAuditService;

@Service("supplierAuditService")
public class SupplierAuditServiceImpl extends BaseServiceImpl<SupplierAudit> implements SupplierAuditService{

	@Autowired
	private SupplierAuditMapper supplierAuditMapper;
	
	@Autowired
	private SuppliersMapper suppliersMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> audit(String reason, Long supplierId, Integer status, String code) {
		Integer vaSupplierCode = suppliersMapper.vaSupplierCode(supplierId, code);
		if(vaSupplierCode > 0){
			return ResultUtil.error("供应商code已存在");
		}
		Suppliers suppliers = new Suppliers();
		suppliers.setId(supplierId);
		suppliers.setStatus(status);
		suppliers.setCode(code);
		suppliers.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		int updateNotNull = suppliersMapper.updateByPrimaryKeySelective(suppliers);
		if(updateNotNull >0){
			SupplierAudit sa = new SupplierAudit();
			sa.setSupplier_id(supplierId);
			sa.setReason(reason);
			sa.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
			int result = supplierAuditMapper.insertSelective(sa);
			return ResultUtil.resultMessage(result, "");
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
		return ResultUtil.error("");
	}

}
