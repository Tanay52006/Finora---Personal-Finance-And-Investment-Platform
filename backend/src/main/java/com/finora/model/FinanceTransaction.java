package com.finora.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="finance_transactions")
public class FinanceTransaction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String type; // INCOME, EXPENSE, BUY, SELL
    private String category;
    private String description;
    private double amount;
    private LocalDate date;

    public FinanceTransaction(){}
    public FinanceTransaction(String userEmail,String type,String category,String description,double amount,LocalDate date){
        this.userEmail=userEmail; this.type=type; this.category=category; this.description=description;
        this.amount=amount; this.date=date;
    }
    public Long getId(){return id;}
    public String getUserEmail(){return userEmail;}
    public String getType(){return type;}
    public String getCategory(){return category;}
    public String getDescription(){return description;}
    public double getAmount(){return amount;}
    public LocalDate getDate(){return date;}
    public void setType(String x){type=x;} public void setCategory(String x){category=x;}
    public void setDescription(String x){description=x;} public void setAmount(double x){amount=x;}
    public void setDate(LocalDate x){date=x;}
}
