package www.tonghao.platform.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.entity.*;
import www.tonghao.service.common.service.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/protocol")
@Api(value = "protocolController", description = "协议Api")
public class ProtocolController {

    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private PlatformInfoService platformInfoService;

    @ApiOperation(value = "分页查询", notes = "分页查询api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "协议名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "协议编号", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "失效日期开始", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "失效日期结束", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = false, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public PageInfo<Protocol> getPage(@ModelAttribute PageBean page, String name, String code, Integer status, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("code", code);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("status", status);
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Protocol> list = protocolService.selectByMap(map);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "获取所有的平台信息", notes = "获取所有的平台信息api")
    @RequestMapping(value = "/getAllPlatformInfo", method = RequestMethod.GET)
    public List<PlatformInfo> getAllPlatformInfo() {
        List<PlatformInfo> platformInfos = platformInfoService.selectAllPlatformInfo();
        return platformInfos;
    }

    @ApiOperation(value = "添加", notes = "添加api")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, Object> save(@RequestBody Protocol protocol) {
        return protocolService.saveEntity(protocol);
    }

    @ApiOperation(value = "删除协议", notes = "删除协议api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Map<String, Object> delete(Long id) {
        return protocolService.deleteEntity(id);
    }

    @ApiOperation(value = "根据id查询", notes = "查询单条api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query"),
    })
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Protocol getById(@RequestParam("id") Long id) {
        Protocol protocol = protocolService.selectByKey(id);
        if (protocol != null) {
            Date now = new Date();
            Date start = DateUtilEx.strToDate(protocol.getStartTime(), DateUtilEx.DATE_PATTERN);
            Date end = DateUtilEx.strToDate(protocol.getEndTime(), DateUtilEx.DATE_PATTERN);
            if(start != null && end != null && protocol.getStatus() != 4){
                if (now.after(start) && now.before(end)) {
                    protocol.setStatus(2);
                }
                if (now.before(start)) {
                    protocol.setStatus(1);
                }
                if (now.after(end)) {
                    protocol.setStatus(3);
                }
                protocolService.updateNotNull(protocol);
            }
        }

        return protocol;
    }

    @ApiOperation(value = "修改", notes = "修改api")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> update(@RequestBody Protocol protocol) {
        return protocolService.updateEntity(protocol);
    }

    @ApiOperation(value = "修改协议状态", notes = "修改协议状态api")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public Map<String, Object> updateStatus(@RequestBody Protocol protocol) {
        return protocolService.updateStatus(protocol);
    }
}
