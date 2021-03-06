package com.bysj.fileshare.entity.po;

import lombok.Data;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.entity.po
 * @ClassName: UserInfo
 * @Description: java类作用描述
 * @Author: 孙燕
 * @CreateDate: 2020/5/23 11:45 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/23 11:45 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: SunYan
 */
@Data
public class UserInfo {

    private Long createTime;
    private Long updateTime;
    /**
     * 逻辑删除 0--未删除  1--删除
     */
    private Boolean isDeleted=false;
    private Long id;

    private String userName;

    private String password;

    private String email;
    /**
     * 用户状态 0--正常 1冻结
     */
    private Integer state=0;
    /**
     * 1--管理员  0--普通用户
     */
    private Integer type=0;
}
