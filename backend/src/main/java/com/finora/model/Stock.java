package com.finora.model;

import jakarta.persistence.*;

@Entity
@Table(name="stocks")
public class Stock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false)
    private String symbol;
    private String companyName;
    private String sector;
    private double currentPrice;
    private double previousPrice;
    private double marketCap;
    private double peRatio;
    private double high52;
    private double low52;
    private String currency = "INR";

    public Stock() {}
    public Stock(String symbol, String companyName, String sector, double currentPrice, double previousPrice,
                 double marketCap, double peRatio, double high52, double low52) {
        this.symbol=symbol; this.companyName=companyName; this.sector=sector;
        this.currentPrice=currentPrice; this.previousPrice=previousPrice;
        this.marketCap=marketCap; this.peRatio=peRatio; this.high52=high52; this.low52=low52;
    }
    public Long getId(){return id;}
    public String getSymbol(){return symbol;}
    public String getCompanyName(){return companyName;}
    public String getSector(){return sector;}
    public double getCurrentPrice(){return currentPrice;}
    public void setCurrentPrice(double p){currentPrice=p;}
    public double getPreviousPrice(){return previousPrice;}
    public void setPreviousPrice(double p){previousPrice=p;}
    public double getMarketCap(){return marketCap;}
    public double getPeRatio(){return peRatio;}
    public double getHigh52(){return high52;}
    public double getLow52(){return low52;}
    public String getCurrency(){return currency;}
    public void setCurrency(String c){currency=c;}
}
