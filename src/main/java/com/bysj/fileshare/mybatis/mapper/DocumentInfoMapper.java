package com.bysj.fileshare.mybatis.mapper;

import com.bysj.fileshare.entity.vo.DocumentInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.mybatis.mapper
 * @ClassName: DocumentInfo
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/21 11:07 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/21 11:07 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
@Mapper //指定操作数据库mapper
public interface DocumentInfoMapper {

    /**
     * 查询数据库文档记录
     * @return
     */
   // @Select("select * from bysj_document_info" )
    List<DocumentInfoVo> queryFileList();

    void addFileUpload(DocumentInfoVo documentInfoVo);
    void editFileUpload(DocumentInfoVo documentInfoVo);
    DocumentInfoVo queryFileById(Long id);
    void deleteFileById(Long id);

}
