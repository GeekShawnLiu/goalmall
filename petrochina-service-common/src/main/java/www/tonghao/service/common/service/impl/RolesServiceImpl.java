package www.tonghao.service.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.enums.RoleCode;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.mapper.RolesMapper;
import www.tonghao.service.common.service.RolesService;

@Service("roleService")
@Transactional
public class RolesServiceImpl extends BaseServiceImpl<Roles> implements RolesService {

	@Autowired
	private RolesMapper rolesMapper;

	@Override
	public int saveRole(Roles roles) {
		String nowDate = DateUtilEx.format(new Date(), DateUtilEx.TIME_PATTERN);
		roles.setCreatedAt(nowDate);
		roles.setUpdatedAt(nowDate);
		roles.setIsDelete(0);
		roles.setIsSuperAdmin(false);
		//int result = rolesMapper.insertSelective(roles);
		return saveSelective(roles);
	}

	@Override
	public List<Roles> getUserRoles(Long userId) {
		return rolesMapper.getUserRoles(userId);
	}

	@Override
	public List<Roles> find(Map<String, Object> map) {
		return rolesMapper.find(map);
	}

	@Override
	public Roles findByCode(RoleCode code) {
		Example example = new Example(Roles.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("code",code.getRoleCode());
		List<Roles> list = selectByExample(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
