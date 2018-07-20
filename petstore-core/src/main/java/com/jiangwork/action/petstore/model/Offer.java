package com.jiangwork.action.petstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="offer")
public class Offer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Column(name="user_id")
    private long userId;
    private String pet;
    private int total;
    private int rest;
    private double price;
    
    @Column(name="place_timestamp")
    private long placeTimestamp; // the time user puts this offer
    
    public Offer() {}
    
    
    public Offer(long id, long userId, String pet, int total, double price, long placeTimestamp) {
        super();
        this.id = id;
        this.userId = userId;
        this.pet = pet;
        this.total = total;
        this.price = price;
        this.placeTimestamp = placeTimestamp;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getPet() {
        return pet;
    }
    public void setPet(String pet) {
        this.pet = pet;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getRest() {
        return rest;
    }
    public void setRest(int rest) {
        this.rest = rest;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public long getPlaceTimestamp() {
        return placeTimestamp;
    }
    public void setPlaceTimestamp(long placeTimestamp) {
        this.placeTimestamp = placeTimestamp;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
