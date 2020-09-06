package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.UsersService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Api(value="testController",description="测试api")
@RestController
public class TestController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private RedisDao redisDao;
	
	@ApiOperation(value="测试根据id查询",notes="获取数据api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="主键id",required=true,dataType="int",paramType="query")
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public Users test(long id){
		Users selectByKey = usersService.selectByKey(id);
		redisDao.setKey("user", selectByKey);
		Users value = (Users) redisDao.getValue("user");
		System.out.println(value.getId());
		return selectByKey;
	}
	
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<Users> test(@ModelAttribute PageBean page){
		PageHelper.startPage(page.getPage(), page.getRows());
		Example example=new Example(Users.class);
		List<Users> list = usersService.selectByExample(example);
		return new PageInfo<Users>(list);
	}
	
	/*@ApiOperation(value="测试添加",notes="添加数据api")
	@RequestMapping(value="/save",method=RequestMethod.GET)
	public Map<String,Object> save(){
		Map<String,Object> map=new HashMap<String, Object>();
		Users users=new Users();
		users.setLoginName("zhangsan");
		users.setEncryptedPassword("123");
		users.setSignInCount(1);
		int save = usersService.save(users);
		if(save>0){
			map.put("message", "success");
		}else{
			map.put("message", "error");
		}
		return map;
	}*/
}
