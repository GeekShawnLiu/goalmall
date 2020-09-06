package www.tonghao.platform.service;

import www.tonghao.platform.entity.Qualification;
import www.tonghao.service.common.base.BaseService;

public interface QualificationService extends BaseService<Qualification> {

   public int saveOrUpdate(Qualification qualification);
	
	
}
