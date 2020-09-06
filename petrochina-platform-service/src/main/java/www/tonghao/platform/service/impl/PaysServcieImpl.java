package www.tonghao.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.platform.entity.Pays;
import www.tonghao.platform.mapper.PaysMapper;
import www.tonghao.platform.service.PaysServcie;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("paysServcie")
public class PaysServcieImpl extends BaseServiceImpl<Pays> implements PaysServcie {

	@Autowired
	private PaysMapper paysMapper;
	
}
