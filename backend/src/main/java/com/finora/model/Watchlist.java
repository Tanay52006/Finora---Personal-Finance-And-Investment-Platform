package com.finora.model;

import jakarta.persistence.*;

@Entity
@Table(name="watchlists")
public class Watchlist {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    @ManyToOne(optional=false)
    private Stock stock;

    public Watchlist(){}
    public Watchlist(String userEmail, Stock stock){this.userEmail=userEmail;this.stock=stock;}
    public Long getId(){return id;}
    public String getUserEmail(){return userEmail;}
    public Stock getStock(){return stock;}
}
