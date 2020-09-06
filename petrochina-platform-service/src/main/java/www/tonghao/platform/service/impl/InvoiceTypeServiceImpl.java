package www.tonghao.platform.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.platform.entity.InvoiceType;
import www.tonghao.platform.mapper.InvoiceTypeMapper;
import www.tonghao.platform.service.InvoiceTypeService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("invoiceTypeService")
@Transactional
public class InvoiceTypeServiceImpl extends BaseServiceImpl<InvoiceType> implements InvoiceTypeService {

	@Autowired
	private InvoiceTypeMapper invoiceTypeMapper;
	
	@Override
	public int saveInvoiceType(InvoiceType invoiceType) {
		invoiceType.setCreatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		invoiceType.setUpdatedAt(DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN));
		return invoiceTypeMapper.insertSelective(invoiceType);
	}

}
