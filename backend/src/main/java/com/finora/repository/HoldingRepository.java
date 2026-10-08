package com.finora.repository;
import com.finora.model.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface HoldingRepository extends JpaRepository<Holding,Long>{
    List<Holding> findByUserEmail(String email);
    Optional<Holding> findByUserEmailAndStockId(String email,Long stockId);
}
