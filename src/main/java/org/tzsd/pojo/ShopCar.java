package org.tzsd.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 购物车
 */

@Entity
@Table(name = "shop_car")
public class ShopCar {
    @Id
    @Column(name = "id")
    private String id; //购物车条目id

    @Column(name = "goods_id")
    private String goods_id; //宝贝id

    @Column(name = "user_id")
    private long user_id; //用户id

    @Column(name = "goods_num")
    private long number;  //购买宝贝个数

    public ShopCar(){

    }

    public ShopCar(String id, String goods_id, long user_id, long number) {
        this.id = id;
        this.goods_id = goods_id;
        this.user_id = user_id;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ShopCar{" +
                "id='" + id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", user_id=" + user_id +
                ", number=" + number +
                '}';
    }
}
