package www.tonghao.service.common.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Seq;
import www.tonghao.service.common.mapper.SeqMapper;
import www.tonghao.service.common.service.SeqService;

@Service("seqService")
@Transactional
public class SeqServiceImpl extends BaseServiceImpl<Seq> implements SeqService {

	@Autowired
	private SeqMapper seqMapper;

	@Override
	public String getSeqSn(String code) {
		String name = code;
		int count = seqMapper.countByName(name);
		if(count<1){
			Seq seq = new Seq();
			seq.setName(name);
			seq.setMax(0L);
			seq.setLength(6);
			seq.setNext(1);
			save(seq);
		}
		return name+seqMapper.getSeqValue(name);
	}
	/*
	@Override
	public String getContractSn(String planCode) {
		int count = seqMapper.countByName(planCode);
		if(count<1){
			Seq seq = new Seq();
			seq.setName(planCode);
			seq.setMax(0L);
			seq.setLength(3);
			seq.setNext(1);
			save(seq);
		}
		return planCode+seqMapper.getSeqValue(planCode);
	}
	*/
	@Override
	public String getContractSn(String planCode) {
		int count = seqMapper.countByName(planCode);
		if(count<1){
			Seq seq = new Seq();
			seq.setName(planCode);
			seq.setMax(0L);
			seq.setLength(3);
			seq.setNext(1);
			save(seq);
		}
		return planCode+"_"+seqMapper.getSeqValue(planCode);
	}

	@Override
	public String getSaleAfterSn() {
		String name = "THH"+DateUtilEx.format(new Date(), "yyyyMMdd");
		int count = seqMapper.countByName(name);
		if(count<1){
			Seq seq = new Seq();
			seq.setName(name);
			seq.setMax(0L);
			seq.setLength(3);
			seq.setNext(1);
			save(seq);
		}
		return name+seqMapper.getSeqValue(name);
	}

}
