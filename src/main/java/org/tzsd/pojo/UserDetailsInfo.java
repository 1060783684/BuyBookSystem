package org.tzsd.pojo;

import javax.persistence.*;

/**
 * @description: 用户的详细信息
 */
@Entity
@Table(name = "user_ext")
public class UserDetailsInfo {
    @Id
    @Column(name = "id")
    private long id; //用户id

    @Column(name = "headsrc")
    private String headSrc; //头像地址

    @Column(name = "name")
    private String name; //姓名

    @Column(name = "phone")
    private String phone; //手机号

    @Column(name = "sex")
    private String sex; //性别

    public UserDetailsInfo(){

    }

    public UserDetailsInfo(long id, String headSrc, String name, String phone, String sex) {
        this.id = id;
        this.headSrc = headSrc;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeadSrc() {
        return headSrc;
    }

    public void setHeadSrc(String headSrc) {
        this.headSrc = headSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
