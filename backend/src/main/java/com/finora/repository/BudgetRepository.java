package com.finora.repository;
import com.finora.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface BudgetRepository extends JpaRepository<Budget,Long>{
    List<Budget> findByUserEmail(String email);
}
