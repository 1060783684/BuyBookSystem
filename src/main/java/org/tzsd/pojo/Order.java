package org.tzsd.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 订单
 */

@Entity
@Table(name = "orders_info")
public class Order {
    @Id
    @Column(name = "id")
    private String id; //订单id

    @Column(name = "store_id")
    private long store_id; //商店id

    @Column(name = "user_id")
    private long user_id; //用户id

    @Column(name = "goods_id")
    private String goods_id; //宝贝id

    @Column(name = "status")
    private int status; //状态

    @Column(name = "goods_num")
    private long number; //购买的个数

    @Column(name = "addr_start")
    private String addr_start; //起始地址

    @Column(name = "addr_end")
    private String addr_end; //结束地址

    @Column(name = "express_id")
    private String express_id; //快递单号

    @Column(name = "addr_id")
    private String addr_id;

    @Column(name = "startTime")
    private long startTime;

    public static final int WAIT_PAY = 0; //待支付
    public static final int WAIT_PUB = 1; //待发货
    public static final int WAIT_INCOME = 2; //待收货
    public static final int WAIT_EVAL = 3; //待评价

    public Order(){

    }

    public Order(String id, long store_id, long user_id, String goods_id, int status, long number,
                 String addr_start, String addr_end, String express_id, String addr_id, long startTime) {
        this.id = id;
        this.store_id = store_id;
        this.user_id = user_id;
        this.goods_id = goods_id;
        this.status = status;
        this.number = number;
        this.addr_start = addr_start;
        this.addr_end = addr_end;
        this.express_id = express_id;
        this.addr_id = addr_id;
        this.startTime = startTime;
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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getAddr_start() {
        return addr_start;
    }

    public void setAddr_start(String addr_start) {
        this.addr_start = addr_start;
    }

    public String getAddr_end() {
        return addr_end;
    }

    public void setAddr_end(String addr_end) {
        this.addr_end = addr_end;
    }

    public String getExpress_id() {
        return express_id;
    }

    public void setExpress_id(String express_id) {
        this.express_id = express_id;
    }

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
