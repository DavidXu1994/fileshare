package com.bysj.fileshare.service.impl;

import com.bysj.fileshare.entity.vo.DocumentInfoVo;
import com.bysj.fileshare.mybatis.mapper.DocumentInfoMapper;
import com.bysj.fileshare.service.DocumentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public List<DocumentInfoVo> queryFileList() {
        List<DocumentInfoVo> ls= documentInfoMapper.queryFileList();
        return ls;
    }

    @Override
    public void addFileUpload(DocumentInfoVo documentInfoVo) {
        documentInfoMapper.addFileUpload(documentInfoVo);
    }

    @Override
    public void editFileUpload(DocumentInfoVo documentInfoVo) {
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
}
