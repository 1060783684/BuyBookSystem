package org.tzsd.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 公司法人
 */
@Entity
@Table(name = "store_user")
public class StoreUser {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "idnumber")
    private String idNumber;

    @Column(name = "store_name")
    private String store_name;

    @Column(name = "type")
    private String type;

    @Column(name = "business")
    private String business;

    @Column(name = "store_id")
    private long store_id;

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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public long getStore_id() {
        return store_id;
    }

    public void setStore_id(long store_id) {
        this.store_id = store_id;
    }

    public StoreUser() {

    }

    public StoreUser(long id, String name, String idNumber, String store_name, String type, String business, long store_id) {
        this.id = id;
        this.name = name;
        this.idNumber = idNumber;
        this.store_name = store_name;
        this.type = type;
        this.business = business;
        this.store_id = store_id;
    }
}
