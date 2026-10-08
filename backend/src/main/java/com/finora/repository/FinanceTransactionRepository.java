package com.finora.repository;
import com.finora.model.FinanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface FinanceTransactionRepository extends JpaRepository<FinanceTransaction,Long>{
    List<FinanceTransaction> findByUserEmailOrderByDateDesc(String email);
}
