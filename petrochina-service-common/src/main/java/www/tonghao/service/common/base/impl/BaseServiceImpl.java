
package www.tonghao.service.common.base.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.common.Mapper;
import www.tonghao.service.common.base.BaseEntity;
import www.tonghao.service.common.base.BaseService;

public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	/** "创建日期"属性名称 */
	private static final String CREATED_AT_PROPERTY_NAME = "createdAt";

	/** "修改日期"属性名称 */
	private static final String UPDATED_AT_PROPERTY_NAME = "updatedAt";
	
    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }
    
    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }
    
    public List<T> select(T entity){
    	return mapper.select(entity);
    }
    
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
    public T selectEntityOne(T entity){
    	return mapper.selectOne(entity);
    }
    public long selectCountByExample(Object example){
    	return mapper.selectCountByExample(example);
    }

    public int save(T entity) {
    	int result = mapper.insert(entity);
        return result;
    }
    
    public int saveSelective(T entity) {
    	int result = mapper.insertSelective(entity);
        return result;
    }

    public int delete(Object key) {
        int result = mapper.deleteByPrimaryKey(key);
        return result;
    }

    public int deleteByRecord(T entity) {
        int result = mapper.delete(entity);
        return result;
    }
    
    public int updateAll(T entity) {
        int result = mapper.updateByPrimaryKey(entity);
        return result;
    }

    public int updateNotNull(T entity) {
        int result = mapper.updateByPrimaryKeySelective(entity);
        return result;
    }

   
    
}
