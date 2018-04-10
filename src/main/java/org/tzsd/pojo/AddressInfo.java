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
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "mail")
    private String mail;

    @Column(name = "user_id")
    private long user_id;

    @Column(name = "addr")
    private String addr;

    public AddressInfo(){

    }

    public AddressInfo(String id, String username, String mail, long user_id, String addr) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.user_id = user_id;
        this.addr = addr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "AddressInfo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", user_id=" + user_id +
                ", addr='" + addr + '\'' +
                '}';
    }
}
