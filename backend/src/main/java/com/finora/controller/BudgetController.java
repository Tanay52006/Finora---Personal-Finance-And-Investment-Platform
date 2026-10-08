package com.finora.controller;

import com.finora.model.Budget;
import com.finora.repository.BudgetRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins="http://localhost:5173")
public class BudgetController {
    private final BudgetRepository repo;
    public BudgetController(BudgetRepository repo){this.repo=repo;}
    @GetMapping("/{email}") public List<Budget> get(@PathVariable String email){return repo.findByUserEmail(email);}
    @PostMapping("/{email}")
    public Budget add(@PathVariable String email,@RequestBody Budget budget){
        return repo.save(new Budget(email,budget.getCategory(),budget.getLimitAmount(),budget.getSpentAmount()));
    }
}
