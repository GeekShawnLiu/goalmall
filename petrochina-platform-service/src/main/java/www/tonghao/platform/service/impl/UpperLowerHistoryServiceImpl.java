package www.tonghao.platform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.platform.service.UpperLowerHistoryService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.UpperLowerHistory;

@Service("upperLowerHistoryService")
@Transactional
public class UpperLowerHistoryServiceImpl extends BaseServiceImpl<UpperLowerHistory> implements UpperLowerHistoryService {

	
}
