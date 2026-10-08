package com.finora.repository;
import com.finora.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface StockRepository extends JpaRepository<Stock,Long>{
    Optional<Stock> findBySymbol(String symbol);
    List<Stock> findByCompanyNameContainingIgnoreCaseOrSymbolContainingIgnoreCase(String name,String symbol);
}
