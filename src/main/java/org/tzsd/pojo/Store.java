package org.tzsd.pojo;

import javax.persistence.*;

/**
 * @description: 商店
 */
@Entity
@Table(name = "store_info")
public class Store {
    @Id
    @Column(name = "id")
    private long id; //商店id

    @Column(name = "descs")
    private String descs; //商店描述

    @Column(name = "name")
    private String name; //商店名字

    @Column(name = "img")
    private String imgsrc; //商店图片路径

    @Column(name = "addr")
    private String addr; //商店地址

    @Column(name = "user_id")
    private String user_id; //所属用户id

    @Column(name = "visit_num")
    private String visit_num; //访问量

    @Column(name = "ischeck")
    @Enumerated(EnumType.STRING)
    private Check isCheck; //开店检查

    enum Check{
        NO,
        YES
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVisit_num() {
        return visit_num;
    }

    public void setVisit_num(String visit_num) {
        this.visit_num = visit_num;
    }

    public Check getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Check isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", descs='" + descs + '\'' +
                ", name='" + name + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", addr='" + addr + '\'' +
                ", user_id='" + user_id + '\'' +
                ", visit_num='" + visit_num + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
