package com.bitprogress.rest.feign;

import com.bitprogress.model.login.LoginCmsDTO;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.TokenDTO;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuwuwupx
 */
@RestController
@RequestMapping("rest/gatewayCms/test")
public class TestFeignController {

    @GetMapping
    public ResultVO<String> test(@ModelAttribute TokenDTO tokenDTO) {
        String s = tokenDTO.toString();
        System.out.println(s);
        return ResultVO.successData(JsonUtils.serializeObject(tokenDTO));
    }

    @GetMapping("a")
    public ResultVO<String> testg(@ModelAttribute LoginCmsDTO loginDTO) {
        String s = loginDTO.toString();
        System.out.println(s);
        return ResultVO.successData(JsonUtils.serializeObject(loginDTO));
    }

    @DeleteMapping("a")
    public ResultVO<String> testd(@ModelAttribute LoginCmsDTO loginDTO) {
        String s = loginDTO.toString();
        System.out.println(s);
        return ResultVO.successData(JsonUtils.serializeObject(loginDTO));
    }

}
