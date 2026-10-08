package com.finora.repository;
import com.finora.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TransactionRepository extends JpaRepository<Transaction,Long>{
    List<Transaction> findByUserEmailOrderByCreatedAtDesc(String email);
}
