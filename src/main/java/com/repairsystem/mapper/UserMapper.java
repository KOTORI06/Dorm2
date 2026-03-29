package com.repairsystem.mapper;
import com.repairsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//用户数据访问接口（MyBatis Mapper）
//提供对用户表的数据库操作，包括查询、插入、更新等功能
@Mapper
public interface UserMapper {
    //根据学工号查询用户信息
    User selectUserById(String id);
    //插入新的用户记录到数据库
    int insertUser(User user);
    //更新用户的宿舍信息（楼栋和房间号）
    int updateUserDormInfo(@Param("userId") String userId, @Param("dormBuilding") String dormBuilding, @Param("dormRoom") String dormRoom);
    //更新用户的登录密码
    int updatePassword(@Param("userId") String userId, @Param("newPassword") String newPassword);
}
