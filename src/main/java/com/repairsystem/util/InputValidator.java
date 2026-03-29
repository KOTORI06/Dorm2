package com.repairsystem.util;
import com.repairsystem.constant.RegexPattern;
//输入验证工具类
public class InputValidator {
    //验证学号格式是否合法
    public static boolean isValidStudentId(String id) {
        return id.matches(RegexPattern.STUDENT_ID);
    }
    //验证工号格式是否合法
    public static boolean isValidAdminId(String id) {
        return id.matches(RegexPattern.ADMIN_ID);
    }
}