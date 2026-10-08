package com.finora.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="goals")
public class Goal {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String name;
    private double targetAmount;
    private double currentAmount;
    private LocalDate targetDate;

    public Goal(){}
    public Goal(String userEmail,String name,double targetAmount,double currentAmount,LocalDate targetDate){
        this.userEmail=userEmail;this.name=name;this.targetAmount=targetAmount;this.currentAmount=currentAmount;this.targetDate=targetDate;
    }
    public Long getId(){return id;} public String getUserEmail(){return userEmail;} public String getName(){return name;}
    public double getTargetAmount(){return targetAmount;} public double getCurrentAmount(){return currentAmount;} public LocalDate getTargetDate(){return targetDate;}
    public void setName(String x){name=x;} public void setTargetAmount(double x){targetAmount=x;}
    public void setCurrentAmount(double x){currentAmount=x;} public void setTargetDate(LocalDate x){targetDate=x;}
}
