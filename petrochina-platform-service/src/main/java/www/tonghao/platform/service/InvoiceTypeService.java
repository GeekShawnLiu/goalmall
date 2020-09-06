package www.tonghao.platform.service;

import www.tonghao.platform.entity.InvoiceType;
import www.tonghao.service.common.base.BaseService;

public interface InvoiceTypeService extends BaseService<InvoiceType> {

	/**  
	 * <p>Title: saveInvoiceType</p>  
	 * <p>Description: 保存发票类型</p>  
	 * @author Yml  
	 * @param invoiceType
	 * @return  
	 */  
	int saveInvoiceType(InvoiceType invoiceType);

}
