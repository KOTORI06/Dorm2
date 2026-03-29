package com.repairsystem.constant;

public class RegexPattern {
    // 学生学号正则表达（限定十位数字）
    public static final String STUDENT_ID = "^(3125|3225)\\d{6}$";
    // 管理员工号正则表达式（限定十位数字）
    public static final String ADMIN_ID = "^0025\\d{6}$";
}
