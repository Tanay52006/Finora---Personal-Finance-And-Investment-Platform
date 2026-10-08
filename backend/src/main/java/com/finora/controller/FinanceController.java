package com.finora.controller;

import com.finora.model.FinanceTransaction;
import com.finora.repository.FinanceTransactionRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/finance/transactions")
@CrossOrigin(origins="http://localhost:5173")
public class FinanceController {
    private final FinanceTransactionRepository repo;
    public FinanceController(FinanceTransactionRepository repo){this.repo=repo;}

    @GetMapping("/{email}") public List<FinanceTransaction> list(@PathVariable String email){
        return repo.findByUserEmailOrderByDateDesc(email);
    }

    @PostMapping("/{email}") public FinanceTransaction create(@PathVariable String email,@RequestBody FinanceTransaction t){
        return repo.save(new FinanceTransaction(email,t.getType(),t.getCategory(),t.getDescription(),t.getAmount(),t.getDate()));
    }

    @PutMapping("/{email}/{id}") public FinanceTransaction update(@PathVariable String email,@PathVariable Long id,@RequestBody FinanceTransaction t){
        FinanceTransaction x=repo.findById(id).orElseThrow();
        if(!x.getUserEmail().equals(email)) throw new IllegalArgumentException("Unauthorized");
        x.setType(t.getType());x.setCategory(t.getCategory());x.setDescription(t.getDescription());
        x.setAmount(t.getAmount());x.setDate(t.getDate());
        return repo.save(x);
    }

    @DeleteMapping("/{email}/{id}") public void delete(@PathVariable String email,@PathVariable Long id){
        FinanceTransaction x=repo.findById(id).orElseThrow();
        if(!x.getUserEmail().equals(email)) throw new IllegalArgumentException("Unauthorized");
        repo.delete(x);
    }
}
