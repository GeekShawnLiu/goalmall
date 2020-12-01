package www.tonghao.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.tonghao.common.redis.RedisDao;
import www.tonghao.dto.AccessTokenDto;
import www.tonghao.service.AccessTokenService;
import www.tonghao.service.common.entity.Users;
import www.tonghao.service.common.mapper.UsersMapper;
import www.tonghao.util.ApiResultUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("accessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RedisDao redisDao;

    @Override
    public String accessToken(AccessTokenDto accessTokenDto) {
        String username = accessTokenDto.getUsername();
        String password = accessTokenDto.getPassword();
        String sign = accessTokenDto.getSign();
        String timestamp = accessTokenDto.getTimestamp();
        if (StringUtils.isBlank(username)) {
            return ApiResultUtil.error("username不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return ApiResultUtil.error("password不能为空！");
        }
        if (StringUtils.isBlank(sign)) {
            return ApiResultUtil.error("sign不能为空！");
        }
        if (StringUtils.isBlank(timestamp)) {
            return ApiResultUtil.error("timestamp不能为空！");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(timestamp);
            Date nowDate = new Date();
            long dif = nowDate.getTime() - date.getTime();
            if (dif < 0) {
                dif = 0 - dif;
            }
            if (dif > 600000) { // 10分钟
                return ApiResultUtil.error("timestamp时间错误");
            }
            String signMd5 = DigestUtils.md5Hex(username + password + timestamp + password);
            if (!signMd5.equals(sign)) {
                return ApiResultUtil.error("sign错误");
            }
            // 校验用户名密码
            Users users = usersMapper.findByLoginName(username, 10);
            if (users == null) {
                return ApiResultUtil.error("用户名或密码错误");
            }
            if (!password.equals(users.getEncryptedPassword())) {
                return ApiResultUtil.error("用户名或密码错误");
            }
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.HOUR, 12);
            String expires_at = dateFormat.format(calendar.getTime());
            redisDao.setKey(token, users.getId() + "", 43200000L);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("access_token", token);
            tokenMap.put("expires_at", expires_at);
            return ApiResultUtil.success("success", tokenMap);
        } catch (ParseException e) {
            e.printStackTrace();
            return ApiResultUtil.error("timestamp格式错误");
        }
    }


    public static void main(String[] args) {

        String signMd5 = DigestUtils.md5Hex("cg_sd" + "0a4a25478c06473685ae3ec3bfa2dad0" + "2020-11-30 16:00:00" + "0a4a25478c06473685ae3ec3bfa2dad0");

        System.out.println(signMd5);
    }
}
