package com.bysj.fileshare.controller;

import com.bysj.fileshare.config.ResponseResult;
import com.bysj.fileshare.entity.vo.DocumentInfoVo;
import com.bysj.fileshare.service.DocumentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
@RestController
public class DocumentInfoController {
    @Autowired
    DocumentInfoService documentInfoService;

    /**
     * 查询文档列表
     *
     * @return
     */
    @GetMapping("/file/list/query")
    @ApiOperation("查询文档列表")
    public ResponseResult queryFileList(@RequestParam(value = "searchWord")  String searchWord,
                                        @RequestParam(value = "userName")  String userName) {
        List<DocumentInfoVo> ls=  documentInfoService.queryFileList( userName ,searchWord);
        return ResponseResult.success(ls);
    }


    @PostMapping("/file/upload/add")
    @ApiOperation("上传新增文档")
    public ResponseResult addFileUpload(@RequestParam(value = "documentUrl")   MultipartFile file,
                                        @RequestParam(value = "userName")   String userName,
                                        @RequestParam(value = "documentName")   String documentName,
                                        @RequestParam(value = "keyWord")   String keyWord,
                                        @RequestParam(value = "documentType")   Integer documentType
    ) {
         documentInfoService.addFileUpload(file,userName,documentName,keyWord,documentType);
        return ResponseResult.success();
        //@RequestBody  DocumentInfoVo documentInfoVo
    }

    @PostMapping("/file/upload/edit")
    @ApiOperation("编辑文档")
    public ResponseResult editFileUpload(@RequestBody  DocumentInfoVo documentInfoVo) {
        documentInfoService.editFileUpload(documentInfoVo);
        return ResponseResult.success();
    }


    @GetMapping("/file/upload/byid/query")
    @ApiOperation("查询单个文档")
    public ResponseResult queryFileById(@RequestParam(value = "id") Long id) {
       DocumentInfoVo ls=  documentInfoService.queryFileById(id);
        return ResponseResult.success(ls);
    }


    @GetMapping("/file/upload/delete")
    @ApiOperation("删除单个文档")
    public ResponseResult deleteFileById(@RequestParam(value = "id") Long id) {
       documentInfoService.deleteFileById(id);
        return ResponseResult.success();
    }
    @GetMapping("/file/down/load")
    @ApiOperation("下载文档")
    public void downloadFileById(HttpServletResponse response, @RequestParam(value = "id") Long id) {
        documentInfoService.downloadFileById(response,id);
       // return ResponseResult.success();
    }


    @GetMapping("/index/query")
    @ApiOperation("首页")
    public ResponseResult index() {
        //return new ModelAndView("index");
        return ResponseResult.success();
    }
}
