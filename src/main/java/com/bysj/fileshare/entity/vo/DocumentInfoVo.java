package com.bysj.fileshare.entity.vo;

import lombok.Data;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.entity.po
 * @ClassName: DocumentInfo
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/21 16:40
 * @UpdateUser:
 * @UpdateDate: 2020/5/21 16:40
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */

/**
 * 文档信息实体类
 */
@Data
public class DocumentInfoVo {
    private Long createTime;
    private Long updateTime;
    /**
     * 逻辑删除 0--未删除  1--删除
     */
    private Boolean isDeleted=false;
    private Long id;

    private String documentName;

    private String keyWord;

    private Integer documentType;

    private String documentUrl;
    /**
     * 上传人
     */
    private String userName;

    private Boolean isOneSelf;
}
