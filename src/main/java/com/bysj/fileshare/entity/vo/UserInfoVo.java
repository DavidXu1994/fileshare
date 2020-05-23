package com.bysj.fileshare.entity.vo;

import lombok.Data;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.entity.vo
 * @ClassName: UserInfoVo
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/23 11:51 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/23 11:51 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
@Data
public class UserInfoVo {
    private Long createTime;
    private Long updateTime;
    /**
     * 逻辑删除 0--未删除  1--删除
     */
    private Boolean isDeleted;
    private Long id;

    private String userName;

    private String password;

    private String passwordAgain;

    private String email;
    /**
     * 用户状态 0--正常 1冻结
     */
    private Integer state;
    /**
     * 1--管理员  0--普通用户
     */
    private Integer type;
}
