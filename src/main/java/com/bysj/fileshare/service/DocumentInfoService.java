package com.bysj.fileshare.service;

import com.bysj.fileshare.entity.vo.DocumentInfoVo;

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
    List<DocumentInfoVo> queryFileList();
}
