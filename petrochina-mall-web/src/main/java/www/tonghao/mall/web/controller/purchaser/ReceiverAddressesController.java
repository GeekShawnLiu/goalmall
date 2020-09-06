package www.tonghao.mall.web.controller.purchaser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.common.utils.ValidatorUtil;
import www.tonghao.mall.entity.ReceiverAddresses;
import www.tonghao.mall.service.ReceiverAddressesService;
import www.tonghao.service.common.entity.Areas;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.service.AreasService;
import www.tonghao.utils.UserUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 收货地址
 * @author developer001
 *
 */
@Api(value="purchaserReceiverAddressesController",description="采购人收货地址")
@RestController
@RequestMapping("/purchaser/receiver_addresses")
public class ReceiverAddressesController {
	
	@Autowired
	private ReceiverAddressesService receiverAddressesService;
	
	@Autowired
	private AreasService areasService;
	
	@ApiOperation(value="分页查询",notes="分页查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="当前页",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="rows",value="条数",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="consigneeName",value="收货人",dataType="string",paramType="query"),
	})
	@RequestMapping(value="/getPage",method=RequestMethod.GET)
	public PageInfo<ReceiverAddresses> getPage(@ModelAttribute PageBean page
			,String consigneeName, HttpServletRequest request){
		PageHelper.startPage(page.getPage(), page.getRows());
		
		Users user = UserUtil.getUser(request);
		
		Example example=new Example(ReceiverAddresses.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("userId", user.getId());
		if(StringUtils.isNotEmpty(consigneeName)){
			createCriteria.andLike("consigneeName", consigneeName);
		}
		example.setOrderByClause(" is_default desc");
		List<ReceiverAddresses> list = receiverAddressesService.selectByExample(example);
		return new PageInfo<ReceiverAddresses>(list);
	}
	
	@ApiOperation(value="根据ID查询",notes="根据ID查询api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="ID",required=true,dataType="int",paramType="query"),
	})
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public ReceiverAddresses getById(Long id, HttpServletRequest request){
		ReceiverAddresses entity = receiverAddressesService.selectByKey(id);
		Users user = UserUtil.getUser(request);
		if(entity!=null&&entity.getUserId().equals(user.getId())){
			if(entity.getAreaId() != null){
				Areas area = areasService.selectByKey(entity.getAreaId());
				if(area != null){
					entity.setTreeIds(area.getTreeIds());
				}
			}
			return entity;
		}
		return null;
	}	
	
	@ApiOperation(value="保存(新增/修改)收货地址",notes="修改或添加api")
	@PostMapping(value="/save")
	public Map<String,Object> save(@RequestBody ReceiverAddresses entity, HttpServletRequest request){
		if(StringUtils.isBlank(entity.getConsigneeName())){
			return ResultUtil.error("收货人姓名不能为空");
		}
		if(entity.getAreaId()==null){
			return ResultUtil.error("请选择地区");
		}
		
		if(StringUtils.isBlank(entity.getAddr())){
			return ResultUtil.error("详细地址不能为空");
		}
		
		if(StringUtils.isBlank(entity.getMobile())){
			return ResultUtil.error("手机号码不能为空");
		}
		
		if(!ValidatorUtil.isMoblie(entity.getMobile())){
			return ResultUtil.error("手机号码格式错误");
		}
		
		/*if(StringUtils.isEmpty(entity.getEmail())){
			return ResultUtil.error("请填写邮箱");
		}*/
		
		/*if(!ValidatorUtil.isEmail(entity.getEmail())){
			return ResultUtil.error("邮箱格式错误");
		}*/
		
		Areas area = areasService.selectByKey(entity.getAreaId());
		if(area==null){
			return ResultUtil.error("地区不存在");
		}
		
		if(entity.getIsDefault()==null){
			entity.setIsDefault(false);
		}
		Users user = UserUtil.getUser(request);
		Example example=new Example(ReceiverAddresses.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId",user.getId());
		List<ReceiverAddresses> list = receiverAddressesService.selectByExample(example);
		if(list.size()>20){
			return ResultUtil.error("收货地址不能超过20个");
		}
		
		if(entity.getIsDefault()){
			for (ReceiverAddresses receiverAddresses : list) {
				if(receiverAddresses.getIsDefault()){
					receiverAddresses.setIsDefault(false);
					receiverAddressesService.updateNotNull(receiverAddresses);
				}
			}
		}
		entity.setUserId(user.getId());
		entity.setAreaName(area.getTreeNames());
		
		entity.setEmail("1@qq.com");
		entity.setZipCode("000000");
		if(entity.getId()!=null){
			entity.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			receiverAddressesService.updateNotNull(entity);
		}else{
			entity.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			receiverAddressesService.save(entity);
		}
		return ResultUtil.success("");
	}
	
	@ApiOperation(value="设置默认",notes="设置默认Api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="long"),
		
	})
	@RequestMapping(value="/setDefault",method=RequestMethod.PUT)
	public Map<String, Object> setDefault(Long id, HttpServletRequest request){
		ReceiverAddresses entity = receiverAddressesService.selectByKey(id);
		Users user = UserUtil.getUser(request);
		if(entity!=null&&entity.getUserId().equals(user.getId())){
			
			Example example=new Example(ReceiverAddresses.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("userId",user.getId());
			List<ReceiverAddresses> list = receiverAddressesService.selectByExample(example);
			for (ReceiverAddresses receiverAddresses : list) {
				if(receiverAddresses.getIsDefault()){
					receiverAddresses.setIsDefault(false);
					receiverAddressesService.updateNotNull(receiverAddresses);
				}
			}
			
			entity.setIsDefault(true);
			receiverAddressesService.updateNotNull(entity);
			return ResultUtil.success("");
		}
		return ResultUtil.error("操作失败");
	}
	
	@ApiOperation(value="删除",notes="删除api")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="id",required=true,dataType="long"),
		
	})
	@RequestMapping(value="/remove",method={RequestMethod.DELETE,RequestMethod.GET})
	public Map<String, Object> remove(Long id, HttpServletRequest request){
		if(id==null){
			return ResultUtil.error("ID不能为空");
		}
		ReceiverAddresses entity = receiverAddressesService.selectByKey(id);
		Users user = UserUtil.getUser(request);
		if(entity!=null&&entity.getUserId().equals(user.getId())){
			receiverAddressesService.delete(id);
			return ResultUtil.success("");
		}
		return ResultUtil.error("操作失败");
	}
	
	@ApiOperation(value="保存(新增/修改)收货地址",notes="修改或添加api")
	@PostMapping(value="/saveOrUpdate")
	public Map<String,Object> saveOrUpdate(ReceiverAddresses entity, HttpServletRequest request){
		if(StringUtils.isBlank(entity.getConsigneeName())){
			return ResultUtil.error("收货人姓名不能为空");
		}
		if(entity.getAreaId()==null){
			return ResultUtil.error("请选择地区");
		}
		
		if(StringUtils.isBlank(entity.getAddr())){
			return ResultUtil.error("详细地址不能为空");
		}
		
		if(StringUtils.isBlank(entity.getMobile())){
			return ResultUtil.error("手机号码不能为空");
		}
		
		if(!ValidatorUtil.isMoblie(entity.getMobile())){
			return ResultUtil.error("手机号码格式错误");
		}
		
		/*if(StringUtils.isEmpty(entity.getEmail())){
			return ResultUtil.error("请填写邮箱");
		}
		
		if(!ValidatorUtil.isEmail(entity.getEmail())){
			return ResultUtil.error("邮箱格式错误");
		}*/
		
		Areas area = areasService.selectByKey(entity.getAreaId());
		if(area==null){
			return ResultUtil.error("地区不存在");
		}
		
		if(entity.getIsDefault()==null){
			entity.setIsDefault(false);
		}
		Users user = UserUtil.getUser(request);
		Example example=new Example(ReceiverAddresses.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId",user.getId());
		List<ReceiverAddresses> list = receiverAddressesService.selectByExample(example);
		if(list.size()>20){
			return ResultUtil.error("收货地址不能超过20个");
		}
		
		if(entity.getIsDefault()){
			for (ReceiverAddresses receiverAddresses : list) {
				if(receiverAddresses.getIsDefault()){
					receiverAddresses.setIsDefault(false);
					receiverAddressesService.updateNotNull(receiverAddresses);
				}
			}
		}
		entity.setUserId(user.getId());
		entity.setAreaName(area.getTreeNames());
		entity.setEmail("1@qq.com");
		entity.setZipCode("000000");
		if(entity.getId()!=null){
			entity.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
			receiverAddressesService.updateNotNull(entity);
		}else{
			entity.setCreatedAt(DateUtilEx.timeFormat(new Date()));
			receiverAddressesService.save(entity);
		}
		return ResultUtil.success("");
	}
}
