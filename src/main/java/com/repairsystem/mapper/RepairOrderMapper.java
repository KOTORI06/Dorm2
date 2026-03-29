package com.repairsystem.mapper;

import com.repairsystem.entity.RepairOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
//报修单数据访问接口（MyBatis Mapper）
//提供对报修单表的数据库操作，包括增删改查及条件查询等功能
@Mapper
public interface RepairOrderMapper {
    //插入新的报修单记录到数据库
    int insertRepairOrder(RepairOrder order);
    //根据报修单 ID 删除记录
    int deleteRepairOrder(Integer orderId);
    //更新报修单的状态
    int updateRepairStatus(@Param("orderId") Integer orderId, @Param("status") String status);
    //根据报修单 ID 查询单个报修单的详细信息
    RepairOrder selectRepairOrderById(Integer orderId);
    //根据学生学号查询该学生的所有报修记录
    List<RepairOrder> selectRepairsByStudentId(String studentId);
    //查询数据库中所有的报修单记录
    List<RepairOrder> selectAllRepairs();
    //根据状态筛选查询报修单
    List<RepairOrder> selectRepairsByCondition(@Param("status") String status);
}
