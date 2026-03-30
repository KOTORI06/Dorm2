package com.repairsystem.entity;
import com.repairsystem.constant.RepairStatusEnum;
import org.apache.ibatis.type.Alias;

import java.util.Date;
//报修单实体类
//用于存储宿舍报修单的完整信息，包括报修人、设备类型、问题描述、状态等
public class RepairOrder {
    private Integer orderId;// 报修单ID(主键)
    private String studentId;// 学生ID
    private String deviceType;// 设备类型
    private String description;// 报修描述
    private RepairStatusEnum status;// 报修状态（待处理、处理中、已完成、已取消）
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private String imageUrl; // 新增：图片 URL

    //空参构造
    public RepairOrder() {
    }
    //有参构造
    public RepairOrder(String studentId, String deviceType, String description) {
        this.studentId = studentId;// 学号/工号
        this.deviceType = deviceType;// 设备类型
        this.description = description;// 描述
        this.status = RepairStatusEnum.PENDING;// 状态
        this.createTime = new Date();// 创建时间为当前时间
        this.updateTime = new Date();// 更新时间为当前时间
    }
    //获取保修单的ID
    public Integer getOrderId() {
        return orderId;
    }
    //设置保修单的ID
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    //获取学生ID
    public String getStudentId() {
        return studentId;
    }
    //设置学生ID
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    //获取设备类型
    public String getDeviceType() {
        return deviceType;
    }
    //设置设备类型
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    //获取设备描述
    public String getDescription() {
        return description;
    }
    //设置设备描述
    public void setDescription(String description) {
        this.description = description;
    }
    //获取设备状态
    public RepairStatusEnum getStatus() {
        return status;
    }
    //设置设备状态
    public void setStatus(RepairStatusEnum status) {
        this.status = status;
    }
    //获取创建时间
    public Date getCreateTime() {
        return createTime;
    }
    //设置创建时间
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    //获取更新时间
    public Date getUpdateTime() {
        return updateTime;
    }
    //设置更新时间
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    //获取图片URL
    public String getImageUrl() {
        return imageUrl;
    }
    //设置图片URL
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    //获取报修单状态
    //返回报修单信息的字符串表示
    @Override
    public String toString() {
        return "RepairOrder{" +
                "orderId=" + orderId +
                ", studentId='" + studentId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
