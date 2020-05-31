package com.bysj.fileshare.mybatis.mapper;

import com.bysj.fileshare.entity.vo.DocumentInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.mybatis.mapper
 * @ClassName: DocumentInfo
 * @Description: java类作用描述
 * @Author: 孙燕
 * @CreateDate: 2020/5/21 11:07 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/21 11:07 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: SunYan
 */
@Mapper //指定操作数据库mapper
public interface DocumentInfoMapper {

    /**
     * 查询数据库文档记录
     *
     * @return
     */
    // @Select("select * from bysj_document_info" )
    List<DocumentInfoVo> queryFileList(String searchWord);

    void addFileUpload(DocumentInfoVo documentInfoVo);

    void editFileUpload(DocumentInfoVo documentInfoVo);

    DocumentInfoVo queryFileById(Long id);

    void deleteFileById(Map<String,Object> param);

}
