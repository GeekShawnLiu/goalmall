package www.tonghao.platform.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.platform.entity.Attachment;
import www.tonghao.platform.enums.AttachType;
import www.tonghao.platform.mapper.AttachmentMapper;
import www.tonghao.platform.service.AttachmentService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

@Service("attachmentService")
@Transactional
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment> implements AttachmentService {

	@Autowired
	private AttachmentMapper attachmentMapper;

	@Override
	public Attachment findByType(AttachType contractSeal) {
		Attachment record = new Attachment();
		record.setIsDelete(0);
		record.setFileType(AttachType.contract_seal.toString());
		List<Attachment> list = attachmentMapper.select(record);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
}
