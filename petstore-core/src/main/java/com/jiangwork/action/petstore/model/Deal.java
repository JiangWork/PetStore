package com.jiangwork.action.petstore.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="deal")
public class Deal {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Column(name="offer_id")
    private long offerId;
    private int quantity;
    @Column(name="buyer_id")
    private long buyer;
    private double cost;
    
    @Column(name="place_timestamp")
    private long placeTimestamp;
    
    @Convert(converter=DealStatusConverter.class)
    private DealStatus status;
    
    public Deal() {}
    
    public Deal(long id, long offerId, int quantity, long buyer, double cost, long placeTimestamp, DealStatus status) {
        super();
        this.id = id;
        this.offerId = offerId;
        this.quantity = quantity;
        this.buyer = buyer;
        this.cost = cost;
        this.placeTimestamp = placeTimestamp;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getOfferId() {
        return offerId;
    }


    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public long getBuyer() {
        return buyer;
    }


    public void setBuyer(long buyer) {
        this.buyer = buyer;
    }


    public double getCost() {
        return cost;
    }


    public void setCost(double cost) {
        this.cost = cost;
    }


    public long getPlaceTimestamp() {
        return placeTimestamp;
    }


    public void setPlaceTimestamp(long placeTimestamp) {
        this.placeTimestamp = placeTimestamp;
    }


    public DealStatus getStatus() {
        return status;
    }


    public void setStatus(DealStatus status) {
        this.status = status;
    }


    public static enum DealStatus {
        CLOSED(1, "closed"),
        CANCELLED(2, "cancelled by owner");
        
        private int value;

        private String display;
        
        DealStatus(int value, String display) {
            this.value = value;
            this.display = display;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
        
    }
}
