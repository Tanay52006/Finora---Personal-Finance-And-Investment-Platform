package com.finora.config;

import com.finora.model.*;
import com.finora.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seed(StockRepository stocks, HoldingRepository holdings, TransactionRepository tx,
                           WatchlistRepository watch, BudgetRepository budgets) {
        return args -> {
            if (stocks.count() == 0) {
                Stock reliance = stocks.save(new Stock("RELIANCE","Reliance Industries","Energy",1425.60,1392.10,19300000000000.0,24.8,1612,1150));
                Stock tcs = stocks.save(new Stock("TCS","Tata Consultancy Services","Technology",3421.20,3378.10,12400000000000.0,27.2,4592,3050));
                Stock infy = stocks.save(new Stock("INFY","Infosys","Technology",1521.40,1554.70,6320000000000.0,24.1,1967,1350));
                Stock hdfc = stocks.save(new Stock("HDFCBANK","HDFC Bank","Banking",1785.20,1762.30,13600000000000.0,20.4,1880,1363));
                Stock tata = stocks.save(new Stock("TATAMOTORS","Tata Motors","Automobile",712.10,718.40,2620000000000.0,8.7,1179,608));
                Stock icici = stocks.save(new Stock("ICICIBANK","ICICI Bank","Banking",1328.80,1312.50,9350000000000.0,18.9,1362,895));
                Stock apple = stocks.save(new Stock("AAPL","Apple","Technology",182.42,176.80,28000000000000.0,29.3,237.49,164.08));
                Stock nvidia = stocks.save(new Stock("NVDA","NVIDIA","Technology",184.72,179.25,45000000000000.0,52.1,195.95,75.61));

                String user="tanay@finora.demo";
                holdings.save(new Holding(user,reliance,12,1320));
                holdings.save(new Holding(user,tcs,8,3250));
                holdings.save(new Holding(user,infy,15,1555));
                watch.save(new Watchlist(user,icici));
                watch.save(new Watchlist(user,nvidia));
                budgets.save(new Budget(user,"Food",10000,8200));
                budgets.save(new Budget(user,"Shopping",8000,5400));
                budgets.save(new Budget(user,"Transport",6000,3200));
                budgets.save(new Budget(user,"Entertainment",5000,2100));
                tx.save(new Transaction(user,"BUY","Reliance Industries",15840.0,12.0,1320.0));
                tx.save(new Transaction(user,"BUY","TCS",26000.0,8.0,3250.0));
                tx.save(new Transaction(user,"EXPENSE","Dining & food",820.0,null,null));
            }
        };
    }
}
