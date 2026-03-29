package com.repairsystem.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private boolean success;//成功或失败
    private String message;//错误信息
    private Object data;//数据

    public static Result success(Object data) {
        return new Result(true, "操作成功", data);
    }

    public static Result success(String message, Object data) {
        return new Result(true, message, data);
    }

    public static Result error(String message) {
        return new Result(false, message, null);
    }

    public static Result error(boolean success, String message) {
        return new Result(success, message, null);
    }
}
