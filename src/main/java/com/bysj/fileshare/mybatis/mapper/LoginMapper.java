package com.bysj.fileshare.mybatis.mapper;

import com.bysj.fileshare.entity.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.mybatis.mapper
 * @ClassName: UserInfoMapper
 * @Description: java类作用描述
 * @Author: 孙燕
 * @CreateDate: 2020/5/23 11:55 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/23 11:55 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: SunYan
 */
@Mapper
public interface LoginMapper {
    void registerUser(UserInfoVo userInfoVo);

    String queryByUserName(String userName);

    String queryUserNameExit(String userName);
}
