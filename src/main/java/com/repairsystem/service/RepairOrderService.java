package com.repairsystem.service;
import com.repairsystem.entity.RepairOrder;
import com.repairsystem.entity.Result;

import java.util.List;

//报修单服务接口
public interface RepairOrderService {
    // 学生：创建报修单111

    //1. 验证报修单信息完整性（设备类型、问题描述不能为空）
    //2. 检查学生是否已绑定宿舍（dormBuilding 和 dormRoom 必须有值）
    //3. 设置初始状态为 PENDING（待处理）
    //4. 设置创建时间为当前时间
    //5. 调用 Mapper 插入数据库
    //6. 返回成功/失败标志
    boolean createRepairOrder(RepairOrder order);
    //1. 学生：根据学号查询本人的所有报修单111

    //1. 验证学号格式是否合法
    //2. 调用 Mapper 查询该学生的所有报修记录
    //3. 按创建时间降序排列（最新的在前）
    //4. 返回报修单列表（可能为空）
    List<RepairOrder> getRepairsByStudentId(String studentId);
    //2. 学生：取消报修单（学生只能取消自己状态为“待处理”的报修单）111

    //1. 根据 orderId 查询报修单
    //2. 验证报修单是否存在
    //3. 验证报修单是否属于该学生（防止误操作他人订单）
    //4. 检查报修单状态是否为 PENDING（只有待处理才能取消）
    //5. 更新状态为 CANCELLED
    //6. 返回成功/失败标志
    boolean cancelRepairOrder(Integer orderId, String studentId);
    //3. 管理员：查询所有报修单（支持按状态筛选）111

    //1. 判断是否需要筛选（statusFilter 是否为 null 或空）
    //2. 如果不需要筛选：查询所有报修单
    //3. 如果需要筛选：只查询指定状态的报修单
    //4. 按创建时间降序排列
    //5. 返回报修单列表
    List<RepairOrder> getAllRepairs(String statusFilter);
    //4. 管理员：根据报修单ID查询单个报修单详情111

    //1. 验证 orderId 是否有效（不为 null 且大于 0）
    //2. 调用 Mapper 查询报修单详情
    //3. 可以关联查询学生信息（姓名、宿舍、联系方式）
    //4. 返回报修单对象或 null
    RepairOrder getRepairOrderById(Integer orderId);
    //5. 管理员：更新报修单状态111

    //1. 根据 orderId 查询报修单
    //2. 验证报修单是否存在
    //3. 验证新状态是否合法（必须是枚举中定义的状态）
    //4. 检查状态流转是否允许（如不能从已完成改为待处理）
    //5. 更新状态和更新时间
    //6. 返回成功/失败标志
    //允许的操作：
    //PENDING → PROCESSING  ✓ （管理员接收工单）
    //PENDING → CANCELLED   ✓ （学生取消或管理员拒绝）
    //PROCESSING → COMPLETED ✓ （维修完成）
    //PROCESSING → CANCELLED ✓ （无法维修，取消）
    //不允许的操作：
    //COMPLETED → 任何状态  ✗ （已完成不能回退）
    //CANCELLED → 任何状态  ✗ （已取消不能恢复）
    Result updateRepairStatus(Integer orderId, String newStatus);
    //6. 管理员：删除报修单

    //1. 根据 orderId 查询报修单
    //2. 验证报修单是否存在
    //3. 检查是否允许删除（通常只允许删除已完成或已取消的订单）
    //4. 调用 Mapper 从数据库删除记录
    //5. 返回成功/失败标志
    Result deleteRepairOrder(Integer orderId);
}
