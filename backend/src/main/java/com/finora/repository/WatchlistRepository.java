package com.finora.repository;
import com.finora.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface WatchlistRepository extends JpaRepository<Watchlist,Long>{
    List<Watchlist> findByUserEmail(String email);
    boolean existsByUserEmailAndStockId(String email,Long stockId);
    void deleteByUserEmailAndStockId(String email,Long stockId);
}
