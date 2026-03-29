package com.repairsystem.service;
import com.repairsystem.entity.Result;
import com.repairsystem.entity.User;

//用户服务接口
//UserService: 接口（定义规范）→ "做什么"
//UserServiceImpl: 实现类（具体实现）→ "怎么做"
public interface UserService {
    //1. 用户登录111

    //1. 根据学工号查询用户信息
    //2. 验证密码是否正确（加密后比对）
    //3. 验证用户角色是否匹配
    //4. 返回用户对象或 null
    //StudentController.login()
    //    ↓ 调用
    //UserService.login()
    //    ↓ 调用
    //UserMapper.selectUserById()
    User login(String id, String password);
    //2. 用户注册111

    //1. 验证学号/工号格式是否合法（InputValidator）
    //2. 检查该学号是否已存在（避免重复注册）
    //3. 加密密码（PasswordUtil.encrypt）
    //4. 设置默认角色（如 STUDENT）
    //5. 调用 Mapper 插入数据库
    //6. 返回成功/失败标志
    boolean register(User user);
    //3. 更新用户宿舍信息111

    //1. 验证楼栋号和房间号格式（非空、长度等）
    //2. 检查用户是否存在
    //3. 调用 Mapper 更新数据库
    //4. 返回成功/失败标志
    boolean updateDormInfo(User  user);
    //4. 更新用户密码111

    //1. 根据 userId 查询用户信息
    //2. 验证旧密码是否正确（加密后比对）
    //3. 验证新密码是否符合要求（长度、复杂度等）
    //4. 加密新密码
    //5. 调用 Mapper 更新数据库
    //6. 返回成功/失败标志
    Result updatePassword(String userId, String oldPwd, String newPwd);
    //5. 根据学工号查询用户信息11

    //1. 调用 Mapper 从数据库查询用户
    //2. 返回用户对象或 null
    User getUserById(String id);
}