package com.repairsystem.controller;
import com.repairsystem.entity.LoginInfo;
import com.repairsystem.entity.Result;
import com.repairsystem.entity.User;
import com.repairsystem.service.UserService;
import com.repairsystem.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ConsoleController {
    //防止意外被修改为其他对象
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {

        if (user.getId() == null || user.getId().trim().isEmpty() || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            log.info("账号和密码不能为空！");
            return Result.error("账号和密码不能为空");
        }

        User tempuser = userService.login(user.getId(), user.getPassword());

        if (tempuser != null) {
            log.info("登录成功！");
            String token = jwtUtil.generateToken(tempuser.getId(), tempuser.getRole().toString());
            LoginInfo loginInfo = new LoginInfo(tempuser.getId(), tempuser.getRole().toString(), token);
            return Result.success("登录成功", loginInfo);
        } else {
            log.info("登录失败，账号或密码错误！");
            return Result.error("登录失败，账号或密码错误");
        }
    }

    // 注册
    @PostMapping("/register")
    public Result register(@RequestBody User user) {

        if (user.getId() == null || user.getPassword() == null || user.getRole() == null) {
            log.info("注册信息不完整！");
            return Result.error("注册信息不完整");
        }

        try {

            boolean isSuccess = userService.register(user);
            if (isSuccess) {
                log.info("注册成功！");
                return Result.success("注册成功", null);
            } else {
                log.info("注册失败，该账号可能已存在！");
                return Result.error("注册失败，该账号可能已存在");
            }
        } catch (IllegalArgumentException e) {
            log.info("无效的角色类型！");
            return Result.error("无效的角色类型");
        }
    }

    // 登出
    @GetMapping("/logout")
    public Result logout() {
        log.info("已退出登录！");
        return Result.success("已退出登录", null);
    }
}