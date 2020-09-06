package www.tonghao.service.common.mapper;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Seq;


public interface SeqMapper extends BaseMapper<Seq> {
	
	/**
	 * 根据名称获取Seq值
	 * @param name
	 * @return
	 */
	String getSeqValue(String name);
	
	/**
	 * 查询名称数量
	 * @param name
	 * @return
	 */
	int countByName(String name);
}