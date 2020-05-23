package com.bysj.fileshare.mybatis.mapper;

import com.bysj.fileshare.entity.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ProjectName: fileshare
 * @Package: com.bysj.fileshare.mybatis.mapper
 * @ClassName: UserInfoMapper
 * @Description: java类作用描述
 * @Author: 徐大伟
 * @CreateDate: 2020/5/23 11:55 下午
 * @UpdateUser:
 * @UpdateDate: 2020/5/23 11:55 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright: 上海昱泓教育科技有限公司
 */
@Mapper
public interface LoginMapper {
    void registerUser(UserInfoVo userInfoVo);
    String queryByUserName(String userName );
}
