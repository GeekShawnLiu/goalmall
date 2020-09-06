package www.tonghao.service.common.service;

import www.tonghao.service.common.base.BaseService;
import www.tonghao.service.common.entity.EnterpriseNature;


public interface EnterpriseNatureService extends BaseService<EnterpriseNature> {
  /**
   * 添加或修改企业性质
   * @param enterpriseNature
   * @return
   */
  public int saveOrUpdate(EnterpriseNature enterpriseNature);
}
