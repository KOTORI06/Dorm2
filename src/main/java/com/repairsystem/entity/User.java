package com.repairsystem.entity;
import com.repairsystem.constant.UserRoleEnum;
import org.apache.ibatis.type.Alias;

//用户实体类
//用于存储系统用户的基本信息，包括学号/工号、密码、角色和宿舍信息
public class User {
    private String id; // 学号/工号
    private String password;// 密码
    private UserRoleEnum role;// 角色
    private String dormBuilding;// 宿舍楼
    private String dormRoom;// 宿舍号
    //空参构造
    public User() {
    }
    //带参构造
    public User(String id, String password, UserRoleEnum role) {
        this.id = id; this.password = password; this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public String getDormBuilding() {
        return dormBuilding;
    }

    public String getDormRoom() {
        return dormRoom;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public void setDormBuilding(String dormBuilding) {
        this.dormBuilding = dormBuilding;
    }

    public void setDormRoom(String dormRoom) {
        this.dormRoom = dormRoom;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", dormBuilding='" + dormBuilding + '\'' +
                ", dormRoom='" + dormRoom + '\'' +
                '}';
    }
}
