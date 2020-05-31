package com.bysj.fileshare.entity.po;

import lombok.Data;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.entity.po
 * @ClassName: DocumentInfo
 * @Description: java类作用描述
 * @Author: 孙燕
 * @CreateDate: 2020/5/21 16:40
 * @UpdateUser:
 * @UpdateDate: 2020/5/21 16:40
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: SunYan
 */

/**
 * 文档信息实体类
 */
@Data
public class DocumentInfo {
    private Long createTime;
    private Long updateTime;
    /**
     * 逻辑删除 0--未删除  1--删除
     */
    private Boolean isDeleted=false;
    /**
     * 主键
     */
    private Long id;
    /**
     * 文档名
     */
    private String documentName;
    /**
     * 关键词，可能多个，逗号分隔
     */
    private String keyWord;
    /**
     * 文档类型 1.. 2.. 3.. 以此类推
     */
    private Integer documentType;
    /**
     * 文件保存到服务器路径
     */
    private String documentUrl;
    /**
     * 上传人
     */
    private String userName;
}
