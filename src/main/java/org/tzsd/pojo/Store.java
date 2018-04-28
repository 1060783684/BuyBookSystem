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
    private long id; //商店id 用户id*10 + 1

    @Column(name = "descs")
    private String descs; //商店描述

    @Column(name = "name")
    private String name; //商店名字

    @Column(name = "imgsrc")
    private String imgsrc; //商店图片路径

    @Column(name = "addr")
    private String addr; //商店地址

    @Column(name = "user_id")
    private long user_id; //所属用户id

    @Column(name = "visit_num")
    private long visit_num; //访问量

    @Column(name = "isCheck")
    private int isCheck; //开店检查
//    public enum Check{
//        NO,
//        YES
//    }

    public static final int NO = 0;
    public static final int YES = 1;

    public Store(){

    }

    public Store(long id, String descs, String name, String imgsrc, String addr,
                 long user_id, long visit_num, int isCheck) {
        this.id = id;
        this.descs = descs;
        this.name = name;
        this.imgsrc = imgsrc;
        this.addr = addr;
        this.user_id = user_id;
        this.visit_num = visit_num;
        this.isCheck = isCheck;
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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getVisit_num() {
        return visit_num;
    }

    public void setVisit_num(long visit_num) {
        this.visit_num = visit_num;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
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
