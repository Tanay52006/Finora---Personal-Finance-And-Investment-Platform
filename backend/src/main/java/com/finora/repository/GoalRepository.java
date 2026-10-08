package com.finora.repository;
import com.finora.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface GoalRepository extends JpaRepository<Goal,Long>{
    List<Goal> findByUserEmailOrderByTargetDateAsc(String email);
}
