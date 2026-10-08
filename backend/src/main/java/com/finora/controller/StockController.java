package com.finora.controller;

import com.finora.model.Stock;
import com.finora.repository.StockRepository;
import com.finora.service.MarketDataService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins="http://localhost:5173")
public class StockController {
    private final StockRepository repo;
    private final MarketDataService market;

    public StockController(StockRepository repo, MarketDataService market){
        this.repo=repo; this.market=market;
    }

    @GetMapping
    public List<Stock> all(@RequestParam(required=false) String q,
                           @RequestParam(defaultValue="true") boolean live){
        List<Stock> result = (q==null || q.isBlank())
                ? repo.findAll()
                : repo.findByCompanyNameContainingIgnoreCaseOrSymbolContainingIgnoreCase(q,q);
        if(live){
            for(Stock stock: result){
                market.refreshStock(stock);
                repo.save(stock);
            }
        }
        return result;
    }

    @GetMapping("/{symbol}")
    public Stock one(@PathVariable String symbol){
        Stock stock=repo.findBySymbol(symbol).orElseThrow();
        market.refreshStock(stock);
        return repo.save(stock);
    }

    @GetMapping("/{symbol}/history")
    public Map<String,Object> history(@PathVariable String symbol,
                                      @RequestParam(defaultValue="1M") String range){
        Stock stock=repo.findBySymbol(symbol).orElseThrow();
        return Map.of("symbol",symbol,"range",range,"points",market.history(stock,range));
    }

    @GetMapping("/fx/usdinr")
    public Map<String,Object> fx(){ return Map.of("usdInr", market.usdToInr(), "timestamp", java.time.Instant.now().toString()); }

    @GetMapping("/{symbol}/quote")
    public Map<String,Object> quote(@PathVariable String symbol){
        Stock stock=repo.findBySymbol(symbol).orElseThrow();
        return market.quote(stock);
    }
}
