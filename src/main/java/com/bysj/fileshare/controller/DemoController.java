package com.bysj.fileshare.controller;

import com.bysj.fileshare.entity.vo.DemoVo;
import com.bysj.fileshare.service.DemoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.controller
 * @ClassName: DemoController
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/21 10:02
 * @UpdateUser:
 * @UpdateDate: 2020/5/21 10:02
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
@Api(tags = "【demo】")
@RestController
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/hello")
    @ResponseBody
    public String  selectAll(){
      //  List<DemoVo> list = demoService.selectAll();
        return "hello word";
    }

}
