package org.tzsd.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 地址
 */
@Entity
@Table(name = "address_info")
public class AddressInfo {
    @Id
    @Column(name = "id")
    private String id; //地址信息id

    @Column(name = "name")
    private String name; //收件人姓名

    @Column(name = "mail")
    private String mail; //收件人邮编

    @Column(name = "user_id")
    private long user_id; //用户id

    @Column(name = "addr")
    private String addr; //收件人地址

    @Column(name = "phone")
    private String phone; //收件人电话

    public AddressInfo(){

    }

    public AddressInfo(String id, String name, String mail, long user_id, String addr, String phone) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.user_id = user_id;
        this.addr = addr;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", user_id=" + user_id +
                ", addr='" + addr + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
