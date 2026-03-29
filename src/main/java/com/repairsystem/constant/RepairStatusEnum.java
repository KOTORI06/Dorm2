package com.repairsystem.constant;
public enum RepairStatusEnum {
    PENDING("待处理"),
    PROCESSING("处理中"),
    COMPLETED("已完成"),
    CANCELLED("已取消");
    private final String description;
    RepairStatusEnum(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
