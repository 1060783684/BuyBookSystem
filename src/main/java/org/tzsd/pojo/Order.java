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

    @Column(name = "user_id")
    private long user_id; //用户id

    @Column(name = "goods_id")
    private String goods_id; //宝贝id

    @Column(name = "status")
    private Status status; //状态

    @Column(name = "goods_num")
    private long number; //库存

    @Column(name = "addr_start")
    private String addr_start; //起始地址

    @Column(name = "addr_start_ext")
    private String addr_start_ext; //起始地址详情

    @Column(name = "addr_end")
    private String addr_end; //结束地址

    @Column(name = "addr_end_ext")
    private String addr_end_ext; //结束地址详情

    enum Status{
        WAIT_PAY,
        WAIT_DELIVERY,
        WAIT_RECEIPT,
        WAIT_EVALUATION
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public String getAddr_start_ext() {
        return addr_start_ext;
    }

    public void setAddr_start_ext(String addr_start_ext) {
        this.addr_start_ext = addr_start_ext;
    }

    public String getAddr_end() {
        return addr_end;
    }

    public void setAddr_end(String addr_end) {
        this.addr_end = addr_end;
    }

    public String getAddr_end_ext() {
        return addr_end_ext;
    }

    public void setAddr_end_ext(String addr_end_ext) {
        this.addr_end_ext = addr_end_ext;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", user_id=" + user_id +
                ", goods_id='" + goods_id + '\'' +
                ", status=" + status +
                ", number=" + number +
                ", addr_start='" + addr_start + '\'' +
                ", addr_start_ext='" + addr_start_ext + '\'' +
                ", addr_end='" + addr_end + '\'' +
                ", addr_end_ext='" + addr_end_ext + '\'' +
                '}';
    }
}
