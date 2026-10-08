package com.finora.model;

import jakarta.persistence.*;

@Entity
@Table(name="budgets")
public class Budget {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String category;
    private double limitAmount;
    private double spentAmount;

    public Budget(){}
    public Budget(String userEmail,String category,double limitAmount,double spentAmount){
        this.userEmail=userEmail;this.category=category;this.limitAmount=limitAmount;this.spentAmount=spentAmount;
    }
    public Long getId(){return id;}
    public String getUserEmail(){return userEmail;}
    public String getCategory(){return category;}
    public double getLimitAmount(){return limitAmount;}
    public double getSpentAmount(){return spentAmount;}
}
