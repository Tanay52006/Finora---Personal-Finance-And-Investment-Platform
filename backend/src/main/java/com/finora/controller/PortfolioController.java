package com.finora.controller;

import com.finora.model.*;
import com.finora.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.finora.service.MarketDataService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:5173")
public class PortfolioController {
    private final HoldingRepository holdings;
    private final TransactionRepository transactions;
    private final StockRepository stocks;
    private final MarketDataService market;

    public PortfolioController(HoldingRepository holdings, TransactionRepository transactions, StockRepository stocks, MarketDataService market){
        this.holdings=holdings;this.transactions=transactions;this.stocks=stocks;this.market=market;
    }

    @GetMapping("/portfolio/{email}")
    public List<Holding> portfolio(@PathVariable String email){ return holdings.findByUserEmail(email); }

    @GetMapping("/transactions/{email}")
    public List<Transaction> transactions(@PathVariable String email){ return transactions.findByUserEmailOrderByCreatedAtDesc(email); }


    @GetMapping("/portfolio/{email}/history")
    public Map<String,Object> history(@PathVariable String email,
                                       @RequestParam(defaultValue="1M") String range) {
        List<Holding> hs = holdings.findByUserEmail(email);
        if (hs.isEmpty()) return Map.of("range", range, "points", List.of());

        Map<String,List<Map<String,Object>>> histories = new LinkedHashMap<>();
        for (Holding h : hs) histories.put(h.getStock().getSymbol(), market.history(h.getStock(), range));

        int max = histories.values().stream().mapToInt(List::size).max().orElse(0);
        List<Map<String,Object>> points = new ArrayList<>();
        double invested = hs.stream().mapToDouble(h -> h.getQuantity() * h.getAverageBuyPrice() * ("USD".equalsIgnoreCase(h.getStock().getCurrency()) ? market.usdToInr() : 1)).sum();

        for (int i=0; i<max; i++) {
            String label = null;
            long timestamp = i;
            double value = 0;
            double usdInr = market.usdToInr();
            for (Holding h : hs) {
                List<Map<String,Object>> series = histories.get(h.getStock().getSymbol());
                if (series.isEmpty()) continue;
                Map<String,Object> p = series.get(Math.min(i, series.size()-1));
                if (label == null) label = String.valueOf(p.get("date"));
                if (p.get("timestamp") instanceof Number) timestamp = ((Number)p.get("timestamp")).longValue();
                double unit = ((Number)p.get("price")).doubleValue();
                if ("USD".equalsIgnoreCase(h.getStock().getCurrency())) unit *= usdInr;
                value += unit * h.getQuantity();
            }
            double pnl = value - invested;
            points.add(Map.of(
                    "date", label == null ? ("Point " + (i+1)) : label,
                    "timestamp", timestamp,
                    "value", value,
                    "invested", invested,
                    "pnl", pnl
            ));
        }
        return Map.of("range", range, "points", points);
    }

    @PostMapping("/invest")
    public Holding invest(@RequestParam String email,@RequestParam String symbol,
                          @RequestParam double amount) {
        Stock stock=stocks.findBySymbol(symbol).orElseThrow();
        double qty=amount/stock.getCurrentPrice();
        Holding h=holdings.findByUserEmailAndStockId(email,stock.getId()).orElse(null);
        if(h==null){
            h=new Holding(email,stock,qty,stock.getCurrentPrice());
        } else {
            double totalOld=h.getQuantity()*h.getAverageBuyPrice();
            h.setQuantity(h.getQuantity()+qty);
            h.setAverageBuyPrice((totalOld+amount)/h.getQuantity());
        }
        transactions.save(new Transaction(email,"BUY",stock.getCompanyName(),amount,qty,stock.getCurrentPrice()));
        return holdings.save(h);
    }
}
