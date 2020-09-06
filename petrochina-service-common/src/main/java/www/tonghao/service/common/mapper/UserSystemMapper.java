package www.tonghao.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.tonghao.service.common.base.BaseMapper;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.UserSystem;

public interface UserSystemMapper extends BaseMapper<UserSystem> {

	/**  
	 * <p>Title: getUserSystems</p>  
	 * <p>Description: </p>  
	 * @author Yml  
	 * @param userId
	 * @return  
	 */  
	List<Permissions> getUserSystems(@Param("userId") Long userId);
}