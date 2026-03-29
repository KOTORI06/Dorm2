package com.repairsystem.service.impl;
import com.repairsystem.entity.Result;
import com.repairsystem.service.RepairOrderService;
import com.repairsystem.constant.RepairStatusEnum;
import com.repairsystem.entity.RepairOrder;
import com.repairsystem.mapper.RepairOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

//报修单服务实现类
@Slf4j
@Service
public class RepairOrderServiceImpl implements RepairOrderService {
    @Autowired
    private RepairOrderMapper mapper;

    @Override
    //学生创建报修单功能111
    public boolean createRepairOrder(RepairOrder order) {
        //检查报修信息是否完整
        //学号、设备类型、问题描述
        if (order.getStudentId() == null || order.getDeviceType() == null || order.getDescription() == null) {
            log.info("错误：报修信息不完整！");
            return false;
        }
        //设置初始状态为待处理和创建时间
        //设置创建时间为当前系统时间
        //new Date() 返回当前的日期时间对象
        order.setStatus(RepairStatusEnum.PENDING);
        order.setCreateTime(new Date());

        try {
            //插入报修单
            int result = mapper.insertRepairOrder(order);
            if (result > 0) {
                log.info("报修单创建成功！单号：" + order.getOrderId());
                return true;
            }
        } catch (Exception e) {
            log.info("创建报修单时发生错误：" + e.getMessage());
        }
        return false;
    }

    @Override
    //根据学号查询学生的所有报修记录111
    public List<RepairOrder> getRepairsByStudentId(String studentId) {
        //返回查询结果的报修表对象列表
        return mapper.selectRepairsByStudentId(studentId);
    }

    @Override
    // 取消报修单111
    public boolean cancelRepairOrder(Integer orderId, String studentId) {
        //获取当前报修单对象
        RepairOrder order = mapper.selectRepairOrderById(orderId);
        //当前报修单不存在
        if (order == null) {
            System.out.println("错误：报修单不存在！");
            return false;
        }
        //判断当前用户是否是报修单的创建者
        if (!order.getStudentId().equals(studentId)) {
            System.out.println("错误：您只能取消自己的报修单！");
            return false;
        }
        //判断报修单状态是否为待处理
        if (order.getStatus() != RepairStatusEnum.PENDING) {
            System.out.println("错误：只能取消状态为【待处理】的报修单！");
            return false;
        }
        //取消报修单
        int result = mapper.updateRepairStatus(orderId, RepairStatusEnum.CANCELLED.name());
        return result > 0;

    }

    @Override
    //获取所有报修单(可按状态筛选)111
    public List<RepairOrder> getAllRepairs(String statusFilter) {
        // 使用动态SQL查询，支持按状态筛选
        // 如果 statusFilter = null 或 ""，查询所有报修单
        // 如果 statusFilter = "PENDING" 等，只查询该状态的报修单
        return mapper.selectRepairsByCondition(statusFilter);
    }

    @Override
    // 根据报修单ID查询报修单111
    public RepairOrder getRepairOrderById(Integer orderId) {
        //根据报修单 ID 查询详细信息
        //返回单个 RepairOrder 对象或 null
        return mapper.selectRepairOrderById(orderId);
    }

    @Override
    //管理员更新报修单状态功能111
    public Result updateRepairStatus(Integer orderId, String newStatus) {
        //更新报修单状态
        int result = mapper.updateRepairStatus(orderId, newStatus);
        if (result > 0) {
            return Result.success("报修单状态更新成功！");
        } else {
            return Result.error("报修单状态更新失败！");
        }
    }

    @Override
    //删除报修单111
    public Result deleteRepairOrder(Integer orderId) {
        //从数据库删除报修单
        int result = mapper.deleteRepairOrder(orderId);
        if(result > 0){
            return Result.success("报修单删除成功！");
        }else{
            return Result.error("报修单删除失败！");
        }
    }
}
