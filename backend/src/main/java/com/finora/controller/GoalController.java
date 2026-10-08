package com.finora.controller;

import com.finora.model.Goal;
import com.finora.repository.GoalRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins="http://localhost:5173")
public class GoalController {
    private final GoalRepository repo;
    public GoalController(GoalRepository repo){this.repo=repo;}

    @GetMapping("/{email}") public List<Goal> list(@PathVariable String email){
        return repo.findByUserEmailOrderByTargetDateAsc(email);
    }
    @PostMapping("/{email}") public Goal create(@PathVariable String email,@RequestBody Goal g){
        return repo.save(new Goal(email,g.getName(),g.getTargetAmount(),g.getCurrentAmount(),g.getTargetDate()));
    }
    @PutMapping("/{email}/{id}") public Goal update(@PathVariable String email,@PathVariable Long id,@RequestBody Goal g){
        Goal x=repo.findById(id).orElseThrow();
        if(!x.getUserEmail().equals(email)) throw new IllegalArgumentException("Unauthorized");
        x.setName(g.getName());x.setTargetAmount(g.getTargetAmount());x.setCurrentAmount(g.getCurrentAmount());x.setTargetDate(g.getTargetDate());
        return repo.save(x);
    }
    @DeleteMapping("/{email}/{id}") public void delete(@PathVariable String email,@PathVariable Long id){
        Goal x=repo.findById(id).orElseThrow();
        if(!x.getUserEmail().equals(email)) throw new IllegalArgumentException("Unauthorized");
        repo.delete(x);
    }
}
