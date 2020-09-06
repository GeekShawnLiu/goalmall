package www.tonghao.utils;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.jwt.TokenAuthenticationService;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.IpAddressUtil;
import www.tonghao.common.utils.SpringUtil;
import www.tonghao.common.warpper.TreeNode;
import www.tonghao.service.common.entity.Permissions;
import www.tonghao.service.common.entity.Roles;
import www.tonghao.service.common.entity.Suppliers;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.PermissionsService;
import www.tonghao.service.common.service.RolesService;
import www.tonghao.service.common.service.SuppliersService;
import www.tonghao.service.common.service.UsersService;




/**
 * 用户工具类
 *
 */
public class UserUtil {

	private UserUtil(){}
	
	
	public static boolean isLogin(HttpServletRequest request){
		boolean defaule=false;
		try {
			defaule=TokenAuthenticationService.getAuthentication(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaule;
	}
	
	public static Users getUser(HttpServletRequest request){
		Users users=null;
		if(isLogin(request)){
			RedisDao bean = SpringUtil.getBean(RedisDao.class);
			String key = request.getHeader("token");
			String ipAddress = IpAddressUtil.getIpAddress(request);
			users = (Users) bean.getValue(key + "@" + ipAddress);
		}
		return users;
	}
	public static Users getUser(String token){
		Users users=null;
		if(!StringUtils.isEmpty(token)){
			RedisDao bean = SpringUtil.getBean(RedisDao.class);
			users = (Users) bean.getValue(token);
		}
		return users;
	}
	
	public static Users currentLoginUser(Long userId){
		Users users=null;
		if(userId!=null){
			UsersService usersService = SpringUtil.getBean(UsersService.class);
			RolesService rolesService = SpringUtil.getBean(RolesService.class);
			PermissionsService permissionsService = SpringUtil.getBean(PermissionsService.class);
			SuppliersService suppliersService = SpringUtil.getBean(SuppliersService.class);
			users = usersService.selectByKey(userId);
			if(users!=null){
				List<Roles> roles = rolesService.getUserRoles(users.getId());
				users.setRoles(roles);
				if(!CollectionUtils.isEmpty(roles)){
					boolean isSuperAdmin = false;
					boolean isAdmin = false;
					for (Roles role : roles) {
						if(role.getIsSuperAdmin()){
							isSuperAdmin = true;
						}
						if(role.getIsSuperAdmin()||role.getIsAdmin()){
							isAdmin = true;
						}
					}
					users.setSuperAdmin(isSuperAdmin);
					users.setAdmin(isAdmin);
				}
				if(users.isAdmin()){
					List<Permissions> userPerms = null;
					if(users.isSuperAdmin()){
						Example example = new Example(Permissions.class);
						Criteria criteria = example.createCriteria();
						criteria.andEqualTo("isDelete", 0);
						criteria.andEqualTo("isFrozen", 0);
						example.setOrderByClause(" priority asc");
						userPerms = permissionsService.selectByExample(example);
					}else{
						if(!CollectionUtils.isEmpty(roles)){
							userPerms = permissionsService.findUsableRolesPerms(roles);
						}
					}
					List<TreeNode> perms = permissionsService.buildTree(userPerms);
					users.setList(perms);
				}
				
				//供应商
				if(users.getType() == 4){
					Suppliers selectByKey = suppliersService.selectByKey(users.getTypeId());
					Suppliers supplier = new Suppliers();
					supplier.setId(selectByKey.getId());
					supplier.setStatus(selectByKey.getStatus());
					supplier.setName(selectByKey.getName());
					supplier.setIsFillBank(selectByKey.getIsFillBank());
					users.setSupplier(supplier);
				}
			}
		}
		return users;
	}
	
	
	public static List<TreeNode> userTreePerms(Long permId,HttpServletRequest request){
    	List<TreeNode> userPerms = Lists.newArrayList();
    	Users users=getUser(request);
    	userPerms=users.getList();
		if(userPerms==null||permId==null){
			return userPerms;
		}
		//表示获取顶级权限
		if(permId<1){
			return userPerms;
		}
		//获取顶级权限下的子权限
		for (Iterator<TreeNode> iterator = userPerms.iterator(); iterator.hasNext();) {
			TreeNode permission = (TreeNode) iterator.next();
			if(permission.getId()!=null&&permission.getId().equals(permId)){
				userPerms = permission.getChildren();
				break;
			}
		}
    	return userPerms;
    }
	
}
