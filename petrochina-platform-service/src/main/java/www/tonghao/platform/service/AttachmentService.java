package www.tonghao.platform.service;

import www.tonghao.platform.entity.Attachment;
import www.tonghao.platform.enums.AttachType;
import www.tonghao.service.common.base.BaseService;


public interface AttachmentService extends BaseService<Attachment> {

	/**  
	 * <p>Title: findByType</p>
	 * <p>Description: 根据附件类型查询</p>  
	 * @author YML 
	 * @param contractSeal
	 * @return 
	 */
	Attachment findByType(AttachType contractSeal);

}
