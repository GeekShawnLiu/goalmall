package www.tonghao.service.common.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.utils.ResultUtil;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.PlatformInfo;
import www.tonghao.service.common.entity.Protocol;
import www.tonghao.service.common.mapper.PlatformInfoMapper;
import www.tonghao.service.common.mapper.ProtocolMapper;
import www.tonghao.service.common.service.ProtocolService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("protocolService")
public class ProtocolServiceImpl extends BaseServiceImpl<Protocol> implements ProtocolService {

    @Autowired
    private ProtocolMapper protocolMapper;

    @Autowired
    private PlatformInfoMapper platformInfoMapper;

    @Override
    public List<Protocol> selectByMap(Map<String, Object> map) {
        List<Protocol> protocols = protocolMapper.selectByMap(map);
        if (CollectionUtils.isNotEmpty(protocols)) {
            for (Protocol protocol : protocols) {
                Date now = new Date();
                Date start = DateUtilEx.strToDate(protocol.getStartTime(), DateUtilEx.DATE_PATTERN);
                Date end = DateUtilEx.strToDate(protocol.getEndTime(), DateUtilEx.DATE_PATTERN);
                if(start != null && end != null){
                    if (now.before(start)) {
                        protocol.setStatus(1);
                    }
                    if (now.after(end)) {
                        protocol.setStatus(3);
                    }
                    if (now.after(start) && now.before(end)) {
                        protocol.setStatus(2);
                    }
                }
            }
        }
        return protocols;
    }

    @Override
    public Map<String, Object> saveEntity(Protocol protocol) {
        if (protocol.getPlatformInfoId() == null) {
            return ResultUtil.error("请选择平台信息");
        }
        PlatformInfo platformInfo = platformInfoMapper.selectByPrimaryKey(protocol.getPlatformInfoId());
        if (platformInfo == null) {
            return ResultUtil.error("平台信息不存在");
        }
        protocol.setIsDelete(0);
        protocol.setCreatedAt(DateUtilEx.timeFormat(new Date()));
        protocol.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
        protocol.setPlatformInfoCode(platformInfo.getCode());
        String startTime = protocol.getStartTime();
        String endTime = protocol.getEndTime();
        if (startTime == null || endTime == null) {
            return ResultUtil.error("起止时间不能为空");
        }
        Date start = DateUtilEx.strToDate(startTime, DateUtilEx.DATE_PATTERN);
        Date end = DateUtilEx.strToDate(endTime, DateUtilEx.DATE_PATTERN);
        if (start != null && end != null) {
            return ResultUtil.error("起止时间格式有误");
        }
        Date now = new Date();
        if (now.after(start) && now.before(end)) {
            protocol.setStatus(2);
        }
        if (now.before(start)) {
            protocol.setStatus(1);
        }
        int i = protocolMapper.insertSelective(protocol);
        return ResultUtil.resultMessage(i, "");
    }

    @Override
    public Map<String, Object> updateEntity(Protocol protocol) {
        protocol.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
        int i = protocolMapper.updateByPrimaryKeySelective(protocol);
        return ResultUtil.resultMessage(i, "");
    }

    @Override
    public Map<String, Object> deleteEntity(Long id) {
        int result = 0;
        Protocol protocol = protocolMapper.selectByPrimaryKey(id);
        if (protocol != null) {
            protocol.setIsDelete(1);
            protocol.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
            result = protocolMapper.updateByPrimaryKeySelective(protocol);
        }
        return ResultUtil.resultMessage(result, "");
    }

    @Override
    public Map<String, Object> updateStatus(Protocol protocol) {
        int result = 0;
        Protocol p = protocolMapper.selectByPrimaryKey(protocol.getId());
        if (p != null) {
            protocol.setStatus(protocol.getStatus());
            protocol.setUpdatedAt(DateUtilEx.timeFormat(new Date()));
            result = protocolMapper.updateByPrimaryKeySelective(protocol);
        }
        return ResultUtil.resultMessage(result, "");
    }
}
