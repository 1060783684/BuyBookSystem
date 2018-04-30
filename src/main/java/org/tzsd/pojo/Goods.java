package org.tzsd.pojo;

import javax.persistence.*;

/**
 * @description: 宝贝信息
 */
@Entity
@Table(name = "goods")
public class Goods {
    //宝贝状态
    public static final int UP = 2; //上架
    public static final int STAYUP = 1; //待审核
    public static final int DOWN = 0; //下架

    //类型
    public static final int EDUCATION = 0; //教育
    public static final int PHILOPHY = 1; //哲学
    public static final int ART = 2; //艺术
    public static final int CLASSICAL = 3; //古典
    public static final int LITERATURE = 4; //文学
    public static final int CHILDREN = 5; //少儿
    public static final int LAGISLATION = 6; //法律

    public static final int[] TYPES = new int[]{Goods.EDUCATION, Goods.PHILOPHY, Goods.ART,
            Goods.CLASSICAL, Goods.LITERATURE, Goods.CHILDREN, Goods.LAGISLATION};

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

    @Column(name = "sales_num")
    private long sales_num; //销量

    @Column(name = "num")
    private long num; //库存

    @Column(name = "img")
    private String imgSrc; //图片路径

    @Column(name = "status")
    private int status; //状态

    @Column(name = "type")
    private int type; //物品类型,通过后端配置常量配置

    public Goods(){

    }

    public Goods(String id, long store_id, String name, String descs, float cost, long good_est,
                 long bad_est, long sell_num, long sales_num, long num, String imgSrc, int status, int type) {
        this.id = id;
        this.store_id = store_id;
        this.name = name;
        this.descs = descs;
        this.cost = cost;
        this.good_est = good_est;
        this.bad_est = bad_est;
        this.sell_num = sell_num;
        this.sales_num = sales_num;
        this.num = num;
        this.imgSrc = imgSrc;
        this.status = status;
        this.type = type;
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

    public long getSales_num() {
        return sales_num;
    }

    public void setSales_num(long sales_num) {
        this.sales_num = sales_num;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
