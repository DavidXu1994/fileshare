package com.bysj.fileshare.service.impl;

import com.bysj.fileshare.entity.vo.DocumentInfoVo;
import com.bysj.fileshare.mybatis.mapper.DocumentInfoMapper;
import com.bysj.fileshare.service.DocumentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<DocumentInfoVo> queryFileList( String userName ,String searchWord) {
        List<DocumentInfoVo> ls= documentInfoMapper.queryFileList(searchWord);
        for(DocumentInfoVo documentInfoVo:ls){
            if(userName.equals(documentInfoVo.getUserName())){
                //判断是否本人
                documentInfoVo.setIsOneSelf(true);
            }else{
                documentInfoVo.setIsOneSelf(false);
            }
        }
        return ls;
    }

    @Override
    public void addFileUpload(MultipartFile file) {
        //文档上传，读取目标文件上传到服务器
        fileUpload( file);

        //documentInfoVo.setCreateTime(System.currentTimeMillis());
       // documentInfoVo.setIsDeleted(false);
       // documentInfoMapper.addFileUpload(documentInfoVo);
    }

    @Override
    public void editFileUpload(DocumentInfoVo documentInfoVo) {
        documentInfoVo.setUpdateTime(System.currentTimeMillis());
        documentInfoMapper.editFileUpload(documentInfoVo);
    }

    @Override
    public DocumentInfoVo queryFileById(Long id) {
        return  documentInfoMapper.queryFileById(id);
    }

    @Override
    public void deleteFileById(Long id) {
        documentInfoMapper.deleteFileById(id);
    }

    /**
     * 文件上传
     */
    public  void fileUpload (MultipartFile file) {
        // 先设定一个放置上传文件的文件夹(该文件夹可以不存在，下面会判断创建)
        String deposeFilesDir = "/Users/davidxu/Desktop/testfile/";
        // 判断文件手否有内容
        if (file.isEmpty()) {
            System.out.println("该文件无任何内容!!!");
        }
        // 获取附件原名(有的浏览器如chrome获取到的是最基本的含 后缀的文件名,如myImage.png)
        // 获取附件原名(有的浏览器如ie获取到的是含整个路径的含后缀的文件名，如C:\\Users\\images\\myImage.png)
        String fileName = file.getOriginalFilename();
        // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
        int index = fileName.lastIndexOf("\\");
        if (index > 0) {
            fileName = fileName.substring(index + 1);
        }
        // 判断单个文件大于1M
        long fileSize = file.getSize();
        if (fileSize > 1024 * 1024) {
            System.out.println("文件大小为(单位字节):" + fileSize);
            System.out.println("该文件大于1M");
        }
        // 当文件有后缀名时
        if (fileName.indexOf(".") >= 0) {
            // split()中放正则表达式; 转义字符"\\."代表 "."
            String[] fileNameSplitArray = fileName.split("\\.");
            // 加上random戳,防止附件重名覆盖原文件
            fileName = fileNameSplitArray[0] + (int) (Math.random() * 100000) + "." + fileNameSplitArray[1];
        }
        // 当文件无后缀名时(如C盘下的hosts文件就没有后缀名)
        if (fileName.indexOf(".") < 0) {
            // 加上random戳,防止附件重名覆盖原文件
            fileName = fileName + (int) (Math.random() * 100000);
        }
        System.out.println("fileName:" + fileName);

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("文件的全路径名字(含路径、后缀)>>>>>>>" + deposeFilesDir + fileName);
    }


}
