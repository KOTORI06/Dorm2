package com.repairsystem.util;
import org.apache.commons.codec.digest.DigestUtils;
//密码加密工具类
//不可逆加密：使用 MD5 哈希算法，只能从明文→密文，无法反向破解
//【MD5 算法原理】
// MD5（Message-Digest Algorithm 5）是一种广泛使用的哈希函数：
// 输入任意长度的数据 → 输出 128 位（16 字节）的哈希值
// 相同的输入永远产生相同的输出
// 不同的输入几乎不可能产生相同的输出
// 无法从哈希值反推出原始输入（单向性）
public class PasswordUtil {
    //使用 Apache Commons Codec 库提供的 MD5 实现
    //DigestUtils.md5Hex(): 计算 MD5 值并返回十六进制字符串
    //密码加密方法
    public static String encrypt(String password) {
        //使用 Apache Commons Codec 库的 MD5 工具类
        //md5Hex() 方法执行两步：
        //1. 计算字符串的 MD5 哈希值（字节数组）
        //2. 将字节数组转换为十六进制字符串
        return DigestUtils.md5Hex(password);
    }

    //密码验证方法
    //将用户输入的密码再次加密，然后比较两个密文
    //【验证原理】
    //利用 MD5 的确定性：相同的输入 → 相同的输出
    public static boolean check(String inputPwd, String storedPwd) {
        return encrypt(inputPwd).equals(storedPwd);
    }
}