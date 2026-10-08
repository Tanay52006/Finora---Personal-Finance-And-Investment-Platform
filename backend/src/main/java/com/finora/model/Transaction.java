package com.finora.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String type;
    private String description;
    private double amount;
    private Double quantity;
    private Double price;
    private LocalDateTime createdAt;

    public Transaction(){}
    public Transaction(String userEmail,String type,String description,double amount,Double quantity,Double price){
        this.userEmail=userEmail; this.type=type; this.description=description; this.amount=amount;
        this.quantity=quantity; this.price=price; this.createdAt=LocalDateTime.now();
    }
    public Long getId(){return id;}
    public String getUserEmail(){return userEmail;}
    public String getType(){return type;}
    public String getDescription(){return description;}
    public double getAmount(){return amount;}
    public Double getQuantity(){return quantity;}
    public Double getPrice(){return price;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
