package org.tzsd.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 用户的详细信息
 */
@Entity
@Table(name = "user_ext")
public class UserDetailsInfo {
    @Id
    @Column(name = "id")
    private long id; //用户id

    @Column(name = "name")
    private String name; //用户名

    @Column(name = "id_num")
    private String id_number; //身份证号

    @Column(name = "phone")
    private String phone; //手机号

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserDetailsInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", id_number='" + id_number + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
