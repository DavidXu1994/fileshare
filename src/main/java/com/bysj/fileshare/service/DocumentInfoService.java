package com.bysj.fileshare.service;

import com.bysj.fileshare.entity.vo.DocumentInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.service
 * @ClassName: DocumentInfoService
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/21 10:48 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/21 10:48 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
public interface DocumentInfoService {

    /**
     * 查询文档列表
     * @return
     */
    List<DocumentInfoVo> queryFileList(String userName ,String searchWord);

    /**
     * 新增文档
     * @param
     */
     void addFileUpload(MultipartFile file,String userName,String documentName,String keyWord, Integer documentType);

    /**
     * 编辑文档
     * @param documentInfoVo
     */
    void editFileUpload(DocumentInfoVo documentInfoVo);

    /**
     * 通过id查询文档
     * @param id
     * @return
     */
    DocumentInfoVo queryFileById(Long id);

    /**
     * 通过id删除文档
     * @param id
     */
    void deleteFileById(Long id);

    /**
     * 文档下载
     * @param response
     * @param id
     */
    void downloadFileById(HttpServletResponse response, Long id);

    /**
     * 文档下载
     * @param response
     */
    void queryFileOnline(HttpServletResponse response,Long id);

}
