package com.bysj.fileshare.controller;

import com.bysj.fileshare.entity.vo.DocumentInfoVo;
import com.bysj.fileshare.service.DocumentInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.controller
 * @ClassName: DocumentInfoController
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/21 10:46 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/21 10:46 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
@Api(tags = "【文档操作】")
@Controller
public class DocumentInfoController {
    @Autowired
    DocumentInfoService documentInfoService;

    /**
     * 查询文档列表
     *
     * @return
     */
    @PostMapping("/file/list/query")
    public ModelAndView queryFileList(Model model) {
        model.addAttribute("DocumentInfoVo", new DocumentInfoVo());
        List<DocumentInfoVo> ls=  documentInfoService.queryFileList();
        ModelAndView mav = new ModelAndView("demo");
        mav.addObject("list", ls);
        return mav;
    }

    @GetMapping("/index/query")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
