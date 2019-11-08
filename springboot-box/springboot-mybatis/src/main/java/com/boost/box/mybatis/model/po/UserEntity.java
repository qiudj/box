package com.boost.box.mybatis.model.po;

/**
 * 用户类
 * @author: qdj
 * @date: 2019-11-08 14:54
 **/
public class UserEntity {

    private String account;
    private String name;
    private String gender;
    private String birthday;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
