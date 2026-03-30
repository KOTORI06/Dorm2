package com.repairsystem.controller;
import com.repairsystem.entity.Result;
import com.repairsystem.service.RepairOrderService;
import com.repairsystem.constant.RepairStatusEnum;
import com.repairsystem.entity.RepairOrder;
import com.repairsystem.entity.User;
import com.repairsystem.service.UserService;
import com.repairsystem.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {
    @Autowired
    private RepairOrderService repairOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    //绑定或修改宿舍信息111
    /**检查角色身份
     *验证学号
     */
    @PutMapping("/dorm")
    private Result bindOrUpdateDorm(@RequestBody User user,@RequestParam String token) {
        //检查Token角色身份
        if(!jwtUtil.getRoleFromToken(token).equals("STUDENT")){
            log.info("权限不足");
            return Result.error("权限不足");
        }
        //检查学号
        if(!user.getId().equals(jwtUtil.getUserIdFromToken(token))){
            log.info("学号错误！");
            return Result.error("学号错误！");
        }
        //调用服务层方法更新数据库
        boolean success = userService.updateDormInfo(user);
        if (success) {
            log.info("宿舍信息更新成功！");
            return Result.success("绑定或更新宿舍信息成功", user);
        } else {
            log.info("宿舍信息更新失败，请检查输入。");
            return Result.error("更新宿舍信息失败");
        }
    }
    //创建报修单111
    @PostMapping
    private Result createRepairOrder(@RequestBody RepairOrder order,@RequestParam String token) {
        //检查Token角色身份
        if(!jwtUtil.getRoleFromToken(token).equals("STUDENT")){
            log.info("权限不足");
            return Result.error("权限不足");
        }
        User User = userService.getUserById(order.getStudentId());
        //报修前检查是否已绑定宿舍
        if (User.getDormBuilding() == null || User.getDormRoom() == null) {
            log.info("请先绑定宿舍！");
            return Result.error("请先绑定宿舍信息");
        }
        //调用 Service 插入数据库
        boolean success = repairOrderService.createRepairOrder(order);
        if (success) {
            log.info("报修单创建成功！单号: " + order.getOrderId());
            return Result.success("报修单创建成功", order);
        } else {
            log.info("报修单创建失败，请稍后重试。");
            return Result.error("报修单创建失败");
        }
    }
    //查看我的报修记录111
    @GetMapping
    private Result viewMyRepairRecords(String id,String token) {
        //检查Token角色身份
        if(!jwtUtil.getRoleFromToken(token).equals("STUDENT")){
            log.info("权限不足");
            return Result.error("权限不足");
        }
        //检查学号
        if(!id.equals(jwtUtil.getUserIdFromToken(token))){
            log.info("学号错误！");
            return Result.error("学号错误！");
        }
        //获取当前用户的报修记录集合
        List<RepairOrder> orders = repairOrderService.getRepairsByStudentId(id);
        //判断集合是否为空
        if (orders.isEmpty()) {
            log.info("暂无报修记录。");
            return Result.error("暂无报修记录。");
        }
        //返回报修单集合
        return Result.success("查看我的报修记录成功", orders);
    }
    //取消报修单111
    @PutMapping("/cancel")
    private Result cancelRepairOrder(@RequestBody RepairOrder order,@RequestParam String token) {
        //检查Token角色身份
        if(!jwtUtil.getRoleFromToken(token).equals("STUDENT")){
            log.info("权限不足");
            return Result.error("权限不足");
        }
        //获取目标报修单对象
        RepairOrder oldOrder = repairOrderService.getRepairOrderById(order.getOrderId());
        //检查该订单是否属于当前学生
        if (oldOrder == null || !oldOrder.getStudentId().equals(jwtUtil.getUserIdFromToken(token))) {
            log.info("报修单不存在或您无权操作此单。");
            return Result.error("报修单不存在或您无权操作此单。");
        }
        //状态是否允许取消
        if (oldOrder.getStatus() != RepairStatusEnum.PENDING) {
            log.info("只有“待处理”状态的报修单可以取消。当前状态: " + oldOrder.getStatus().getDescription());
            return Result.error("只有“待处理”状态的报修单可以取消。当前状态: " + oldOrder.getStatus().getDescription());
        }
        //调用 Service 层更新订单状态
        Result result = repairOrderService.updateRepairStatus(order.getOrderId(), String.valueOf(RepairStatusEnum.CANCELLED));
        return result;
    }
    //修改密码111
    @PutMapping("/password")
    private Result changePassword(@RequestBody User user,@RequestParam String token) {
        //检查Token角色身份
        if(!jwtUtil.getRoleFromToken(token).equals("STUDENT")){
            log.info("权限不足");
            return Result.error("权限不足");
        }
        //检查学号
        if(!user.getId().equals(jwtUtil.getUserIdFromToken(token))){
            log.info("学号错误！");
            return Result.error("学号错误！");
        }
        //调用 Service 更新密码
        User oldUser = userService.getUserById(user.getId());
        Result result = userService.updatePassword(user.getId(), oldUser.getPassword(), user.getPassword());
        return result;
    }
}