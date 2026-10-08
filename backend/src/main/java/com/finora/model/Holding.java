package com.finora.model;

import jakarta.persistence.*;

@Entity
@Table(name="holdings")
public class Holding {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    @ManyToOne(optional=false)
    private Stock stock;
    private double quantity;
    private double averageBuyPrice;

    public Holding(){}
    public Holding(String userEmail, Stock stock, double quantity, double averageBuyPrice){
        this.userEmail=userEmail; this.stock=stock; this.quantity=quantity; this.averageBuyPrice=averageBuyPrice;
    }
    public Long getId(){return id;}
    public String getUserEmail(){return userEmail;}
    public Stock getStock(){return stock;}
    public double getQuantity(){return quantity;}
    public double getAverageBuyPrice(){return averageBuyPrice;}
    public void setQuantity(double q){quantity=q;}
    public void setAverageBuyPrice(double p){averageBuyPrice=p;}
}
