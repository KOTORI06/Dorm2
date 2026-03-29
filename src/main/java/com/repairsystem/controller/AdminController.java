package com.repairsystem.controller;
import com.repairsystem.entity.Result;
import com.repairsystem.service.RepairOrderService;
import com.repairsystem.constant.RepairStatusEnum;
import com.repairsystem.entity.RepairOrder;
import com.repairsystem.entity.User;
import com.repairsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    private RepairOrderService repairOrderService;
    @Autowired
    private UserService userService;

    //查看所有报修单（带状态筛选功能）111
    @GetMapping
    private Result viewAllRepairOrders(@RequestParam(value = "status", required = false) String status) {
        List<RepairOrder> orders;
        if (status != null && !status.isEmpty()) {
            //筛选状态
            orders = repairOrderService.getAllRepairs(status);
        } else {
            //不筛选状态
            orders = repairOrderService.getAllRepairs(null);
        }
        //判断是否为空
        if (orders.isEmpty()) {
            log.info("暂无报修记录。");
            return Result.error("暂无报修记录。");
        }
        //返回报修单集合
        return Result.success(orders);
    }
    //查看报修单详细信息111
    @GetMapping("/{orderId}")
    private Result viewRepairOrderDetail(@PathVariable Integer orderId) {
        //获取目标报修单对象
        RepairOrder order = repairOrderService.getRepairOrderById(orderId);
        //判断目标报修单对象是否为空
        if (order == null) {
            log.info("未找到该报修单。");
            return Result.error("未找到该报修单。");
        }
        return Result.success(order);
    }
    //更新报修单状态111
    @PostMapping
    private Result updateRepairOrderStatus(@RequestBody RepairOrder order) {
        //获取目标报修单对象
        RepairOrder oldOrder = repairOrderService.getRepairOrderById(order.getOrderId());
        //判断目标报修单对象是否存在
        if (oldOrder == null) {
            log.info("报修单不存在。");
            return Result.error("报修单不存在。");
        }

        RepairStatusEnum newStatus = order.getStatus();
        Result result = repairOrderService.updateRepairStatus(order.getOrderId(), String.valueOf(newStatus));
        return result;
    }
    //删除报修单111
    @DeleteMapping
    private Result deleteRepairOrder(Integer orderId) {

        Result result = repairOrderService.deleteRepairOrder(orderId);
        return result;
    }
    //修改密码111
    @PutMapping
    private Result changePassword(@RequestBody User user) {
        //调用 Service 更新密码
        User oldUser = userService.getUserById(user.getId());
        Result result = userService.updatePassword(user.getId(), oldUser.getPassword(), user.getPassword());
        return result;
    }
}