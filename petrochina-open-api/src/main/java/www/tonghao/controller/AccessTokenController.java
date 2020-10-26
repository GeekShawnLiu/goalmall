package www.tonghao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import www.tonghao.dto.AccessTokenDto;
import www.tonghao.service.AccessTokenService;

@RestController
@RequestMapping("/api/auth2")
public class AccessTokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * 获取token
     * @param accessTokenDto
     * @return
     */
    @RequestMapping(value = "/access_token", method = RequestMethod.POST)
    public String accessToken(@ModelAttribute AccessTokenDto accessTokenDto){
        return accessTokenService.accessToken(accessTokenDto);
    }
}
