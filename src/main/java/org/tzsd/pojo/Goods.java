package org.tzsd.pojo;

import javax.persistence.*;

/**
 * @description: 宝贝信息
 */
@Entity
@Table(name = "goods_info")
public class Goods {
    @Id
    @Column(name = "id")
    private String id; //宝贝id

    @Column(name = "store_id")
    private long store_id; //商店id

    @Column(name = "name")
    private String name; //宝贝名称

    @Column(name = "descs")
    private String descs; //宝贝描述

    @Column(name = "cost")
    private float cost; //宝贝价格

    @Column(name = "good_est")
    private long good_est; //好评数

    @Column(name = "bad_est")
    private long bad_est; //差评数

    @Column(name = "sell_num")
    private long sell_num; //访问量

    @Column(name = "num")
    private long num; //库存

    @Column(name = "img")
    private String imgSrc; //图片路径

    @Column(name = "ischeck")
    @Enumerated(EnumType.STRING)
    private Check isCheck;

    enum Check{
        YES,
        NO
    }

    public Goods(){

    }

    public Goods(String id, long store_id, String name, String descs, float cost,
                 long good_est, long bad_est, long sell_num, long num, String imgSrc, Check isCheck) {
        this.id = id;
        this.store_id = store_id;
        this.name = name;
        this.descs = descs;
        this.cost = cost;
        this.good_est = good_est;
        this.bad_est = bad_est;
        this.sell_num = sell_num;
        this.num = num;
        this.imgSrc = imgSrc;
        this.isCheck = isCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStore_id() {
        return store_id;
    }

    public void setStore_id(long store_id) {
        this.store_id = store_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public long getGood_est() {
        return good_est;
    }

    public void setGood_est(long good_est) {
        this.good_est = good_est;
    }

    public long getBad_est() {
        return bad_est;
    }

    public void setBad_est(long bad_est) {
        this.bad_est = bad_est;
    }

    public long getSell_num() {
        return sell_num;
    }

    public void setSell_num(long sell_num) {
        this.sell_num = sell_num;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Check getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Check isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id='" + id + '\'' +
                ", store_id=" + store_id +
                ", name='" + name + '\'' +
                ", descs='" + descs + '\'' +
                ", cost=" + cost +
                ", good_est=" + good_est +
                ", bad_est=" + bad_est +
                ", sell_num=" + sell_num +
                ", num=" + num +
                ", imgSrc='" + imgSrc + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
