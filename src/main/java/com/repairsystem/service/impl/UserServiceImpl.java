package com.repairsystem.service.impl;
import com.repairsystem.constant.UserRoleEnum;
import com.repairsystem.entity.Result;
import com.repairsystem.entity.User;
import com.repairsystem.mapper.UserMapper;
import com.repairsystem.util.InputValidator;
import com.repairsystem.util.PasswordUtil;
import com.repairsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//用户服务实现类
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    @Override
    //1.实现用户登录功能
    public User login(String id, String password) {
        //根据ID查询用户信息
        User user = mapper.selectUserById(id);
        //用户信息存在且密码正确再返回对应用户对象
        if (user != null && PasswordUtil.check(password, user.getPassword())) {
            return user;
        }
        //用户信息不存在或密码错误返回null
        return null;
    }

    @Override
    //2.实现用户注册功能
    public boolean register(User user) {
        //获取学号
        String id = user.getId();
        //正则表达式验证学号格式
        if (!InputValidator.isValidStudentId(id) && !InputValidator.isValidAdminId(id)) {
            //格式错误，返回错误提示信息
            System.out.println("账号格式错误！学生号以3125/3225开头，管理员号以0025开头。");
            return false;
        }

        //检查该学号是否已存在
        if (mapper.selectUserById(id) != null) {
            //已存在输出错误提示信息
            System.out.println("该账号已存在！");
            return false;
        }
        //加密密码
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        //将用户数据插入数据库
        int result = mapper.insertUser(user);
        return result > 0;

    }

    @Override
    //3.更新用户宿舍信息111
    public boolean updateDormInfo(User user) {
        try {
            //获取对应用户对象
            User tempUser = mapper.selectUserById(user.getId());
            //用户不存在
            if (tempUser == null) {
                //输出错误提示信息
                log.info("用户不存在！");
                return false;
            }
            // 只允许学生绑定宿舍，若是管理员则返回错误提示
            if (tempUser.getRole() != UserRoleEnum.STUDENT) {
                log.info("只有学生可以绑定宿舍！");
                return false;
            }

            // 验证宿舍信息格式，只有空格或null则返回错误提示信息
            // trim():String类的一个方法，用于去除字符串两端的空白字符（包括空格、制表符、换行符等）
            if (user.getDormBuilding() == null || user.getDormBuilding().trim().isEmpty() || user.getDormRoom() == null || user.getDormRoom().trim().isEmpty()) {
                log.info("宿舍楼栋和房间号不能为空！");
                return false;
            }
            // 调用 Mapper 的更新方法
            // trim()去除首尾空格
            int result = mapper.updateUserDormInfo(user.getId(), user.getDormBuilding().trim(), user.getDormRoom().trim());

            if (result > 0) {
                log.info("宿舍信息更新成功！");
                return true;
            } else {
                log.info("宿舍信息更新失败！");
                return false;
            }
        } catch (Exception e) {
            // 输出错误信息
            log.info("更新宿舍信息失败：" + e.getMessage());
            return false;
        }
    }

    @Override
    //4.修改密码111
    public Result updatePassword(String userId, String oldPwd, String newPwd) {
        // 验证新密码长度
        if (newPwd == null || newPwd.trim().length() < 6) {
            log.info("新密码长度不能少于6位！");
            return Result.error("新密码长度不能少于6位！");
        }

        try {
            //获取当前用户对象
            User user = mapper.selectUserById(userId);
            //用户不存在
            if (user == null) {
                //输出错误信息
                log.info("用户不存在！");
                return Result.error("用户不存在！");
            }
            //检查原密码是否正确
            if (!PasswordUtil.check(oldPwd, user.getPassword())) {
                log.info("原密码错误！");
                return Result.error("原密码错误！");
            }
            //新密码不能与旧密码相同
            if (PasswordUtil.check(newPwd, user.getPassword())) {
                log.info("新密码不能与原密码相同！");
                return Result.error("新密码不能与原密码相同！");
            }

            //加密新密码
            String encryptedNewPwd = PasswordUtil.encrypt(newPwd.trim());
            int result = mapper.updatePassword(userId, encryptedNewPwd);

            if (result > 0) {
                log.info("密码修改成功！");
                return Result.success("密码修改成功！");
            } else {
                log.info("密码修改失败！");
                return Result.error("密码修改失败！");
            }
        } catch (Exception e) {
            //输出错误信息
            log.info("修改密码失败：" + e.getMessage());
            return Result.error("修改密码失败：" + e.getMessage());
        }
    }

    @Override
    //5.获取用户信息111
    public User getUserById(String id) {
        //创建数据库会话
        try {
            //根据用户id获取用户对象
            return mapper.selectUserById(id);
        } catch (Exception e) {
            //输出错误提示
            System.out.println("查询用户信息失败：" + e.getMessage());
            return null;
        }
    }
}
