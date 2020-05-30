package com.bysj.fileshare.service.impl;

import com.bysj.fileshare.entity.vo.DocumentInfoVo;
import com.bysj.fileshare.mybatis.mapper.DocumentInfoMapper;
import com.bysj.fileshare.service.DocumentInfoService;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.service.impl
 * @ClassName: DocumentInfoServiceImpl
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/21 10:49 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/21 10:49 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
@Service
public class DocumentInfoServiceImpl implements DocumentInfoService {
    @Autowired
    private DocumentInfoMapper documentInfoMapper;
    @Autowired
    private DocumentConverter converter;

    @Value("${file.upload.url}")
    private String fileUploadUrl;

    @Value("${file.preview.url}")
    private String filePreviewUrl;

    /**
     * 查询文档列表
     *
     * @param userName
     * @param searchWord
     * @return
     */
    @Override
    public List<DocumentInfoVo> queryFileList(String userName, String searchWord) {
        List<DocumentInfoVo> ls = documentInfoMapper.queryFileList(searchWord);
        for (DocumentInfoVo documentInfoVo : ls) {
            if (userName.equals(documentInfoVo.getUserName())) {
                //判断是否本人
                documentInfoVo.setIsOneSelf(true);
            } else {
                documentInfoVo.setIsOneSelf(false);
            }
        }
        return ls;
    }

    /**
     * 文档上传
     *
     * @param file
     * @param userName
     * @param documentName
     * @param keyWord
     * @param documentType
     */
    @Override
    public void addFileUpload(MultipartFile file, String userName, String documentName, String keyWord, Integer documentType) {
        //文档上传，读取目标文件上传到服务器
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userName)) {
            throw new RuntimeException("表单信息不得为空！");
        }
        //上传后返回文档名
        String path = "";
            path = fileUpload(file);

        //数据库保存信息
        DocumentInfoVo documentInfoVo = new DocumentInfoVo();
        documentInfoVo.setUserName(userName);
        documentInfoVo.setDocumentName(documentName);
        documentInfoVo.setDocumentType(documentType);
        documentInfoVo.setDocumentUrl(path);
        documentInfoVo.setKeyWord(keyWord);
        documentInfoVo.setIsDeleted(false);
        documentInfoVo.setCreateTime(System.currentTimeMillis());
        documentInfoMapper.addFileUpload(documentInfoVo);

    }

    /**
     * 编辑文档
     *
     * @param documentInfoVo
     */
    @Override
    public void editFileUpload(DocumentInfoVo documentInfoVo) {
        documentInfoVo.setUpdateTime(System.currentTimeMillis());
        documentInfoMapper.editFileUpload(documentInfoVo);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Override
    public DocumentInfoVo queryFileById(Long id) {
        return documentInfoMapper.queryFileById(id);
    }

    /**
     * 通过id删除
     *
     * @param id
     */
    @Override
    public void deleteFileById(Long id) {
        Map<String, Object> param = new HashMap<>();
        Long updateTime = System.currentTimeMillis();
        param.put("id", id);
        param.put("updateTime", updateTime);
        documentInfoMapper.deleteFileById(param);
    }

    /**
     * 文档下载
     *
     * @param response
     * @param id
     */
    @Override
    public void downloadFileById(HttpServletResponse response, Long id) {
        DocumentInfoVo dto = documentInfoMapper.queryFileById(id);

        try {
            downloadFile(dto.getDocumentUrl(), response, dto.getDocumentName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在线预览
     *
     * @param response
     */
    @Override
    public void queryFileOnline(HttpServletResponse response, Long id) {
        //根据id查询文件路径
        DocumentInfoVo dto = documentInfoMapper.queryFileById(id);
        String fileUrl = dto.getDocumentUrl();
        String documentName = dto.getDocumentName()+ ".pdf";
        if (fileUrl.indexOf(".") >= 0) {
            String[] fileNameSplitArray = fileUrl.split("\\.");
            if (fileNameSplitArray[1].toLowerCase().equals("pdf")) {
                isPDF(response, fileUrl, documentName);
            } else {
                isNotPDF(response, fileUrl, documentName);
            }
        } else {
            isNotPDF(response, fileUrl, documentName);
        }
    }

    public void isNotPDF(HttpServletResponse response, String fileUrl, String documentName) {
        //需要转换的文件
        File file = new File(fileUrl);
        try {
            //转换之后文件生成的地址
            File newFile = new File(filePreviewUrl);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            //文件转化
            converter.convert(file).to(new File(filePreviewUrl + documentName)).execute();
            //使用response,将pdf文件以流的方式发送的前段
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(new File(filePreviewUrl + documentName));
            // copy文件
            int i = IOUtils.copy(in, outputStream);
            System.out.println(i);
            in.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 已经是pdf文件无需转码
     */
    public void isPDF(HttpServletResponse response, String fileUrl, String documentName) {
        File file = new File(fileUrl);
        BufferedInputStream br = null;
        try {
            br = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buf = new byte[1024];
        int len = 0;
        response.reset(); // 非常重要
        response.setContentType("application/pdf");
        try {
            response.setHeader("Content-Disposition", "inline; filename=" + java.net.URLEncoder.encode(documentName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        OutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!((len = br.read(buf)) != -1)) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.write(buf, 0, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     *
     * @param filePath 存储路径
     * @param response
     * @param fileName 文件名
     * @throws Exception
     */
    public void downloadFile(String filePath, HttpServletResponse response, String fileName) throws Exception {
        File file = new File(filePath);
        //此处fileName没有后缀，需要拼接
        if (filePath.indexOf(".") >= 0) {
            String[] fileNameSplitArray = filePath.split("\\.");
            fileName = fileName + "." + fileNameSplitArray[1];
        }
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            //文件名中文转码
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream outputStream = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 文件上传，返回服务器路径
     */
    public String fileUpload(MultipartFile file) {
        // 先设定一个放置上传文件的文件夹(该文件夹可以不存在，下面会判断创建)
        String deposeFilesDir = fileUploadUrl;
        // 判断文件是否有内容
        if (file.isEmpty()) {
            throw new RuntimeException("该文件无任何内容！");
        }
        // 获取附件原名(有的浏览器如chrome获取到的是最基本的含后缀的文件名,如myImage.png)
        // 获取附件原名(有的浏览器如ie获取到的是含整个路径的含后缀的文件名，如C:\\Users\\images\\myImage.png)
        String fileName = file.getOriginalFilename();
        // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
        int index = fileName.lastIndexOf("\\");
        if (index > 0) {
            fileName = fileName.substring(index + 1);
        }
        // 判断单个文件大于10M
        long fileSize = file.getSize();
        if (fileSize > 1024 * 1024*10 ) {
            System.out.println("文件大小为(单位字节):" + fileSize);
            throw new RuntimeException("该文件大于10M！:"+fileSize);

        }
        // 当文件有后缀名时
        if (fileName.indexOf(".") >= 0) {
            // split()中放正则表达式; 转义字符"\\."代表 "."
            String[] fileNameSplitArray = fileName.split("\\.");
            // 加上当前时间戳
            fileName = fileNameSplitArray[0] + DateTime.now().toString("yyyyMMddHHmmss") + "." + fileNameSplitArray[1];
        }
        // 当文件无后缀名时(如C盘下的hosts文件就没有后缀名)
        if (fileName.indexOf(".") < 0) {
            // 加上当前时间戳
            fileName = fileName + DateTime.now().toString("yyyyMMddHHmmss");
        }
        // 根据文件的全路径名字(含路径、后缀),new一个File对象dest
        File dest = new File(deposeFilesDir + fileName);
        // 如果该文件的上级文件夹不存在，则创建该文件的上级文件夹及其祖辈级文件夹;
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("文件的全路径名字(含路径、后缀)>>>>>>>" + deposeFilesDir + fileName);
        return deposeFilesDir + fileName;
    }
}
