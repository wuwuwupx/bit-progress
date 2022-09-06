package com.bitprogress.rest.feign;

import com.bitprogress.model.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuwuwupx
 */
@RestController
@RequestMapping("rest")
public class TestController {

    @GetMapping("test")
    public ResultVO<Long> get(@RequestParam("id") Long id) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResultVO.successData(id);
    }

}
