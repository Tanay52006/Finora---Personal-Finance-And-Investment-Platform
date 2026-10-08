package com.finora.controller;

import com.finora.model.*;
import com.finora.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/watchlist")
@CrossOrigin(origins="http://localhost:5173")
public class WatchlistController {
    private final WatchlistRepository watch;
    private final StockRepository stocks;
    public WatchlistController(WatchlistRepository watch,StockRepository stocks){this.watch=watch;this.stocks=stocks;}

    @GetMapping("/{email}")
    public List<Watchlist> get(@PathVariable String email){return watch.findByUserEmail(email);}

    @PostMapping("/{email}/{symbol}")
    public Watchlist add(@PathVariable String email,@PathVariable String symbol){
        Stock s=stocks.findBySymbol(symbol).orElseThrow();
        if(watch.existsByUserEmailAndStockId(email,s.getId())) return watch.findByUserEmail(email).stream()
                .filter(x->x.getStock().getId().equals(s.getId())).findFirst().orElseThrow();
        return watch.save(new Watchlist(email,s));
    }

    @DeleteMapping("/{email}/{symbol}")
    public void remove(@PathVariable String email,@PathVariable String symbol){
        Stock s=stocks.findBySymbol(symbol).orElseThrow();
        watch.deleteByUserEmailAndStockId(email,s.getId());
    }
}
