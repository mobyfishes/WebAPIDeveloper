package com.example.demo.Controller;

import com.example.demo.Entity.Transaction;
import com.example.demo.Exceptions.DataNotFoundException;
import com.example.demo.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class TranscationController {

    @Autowired
    TransactionService transcationService;


    @PostMapping("/Transaction")
    public ResponseEntity<HttpStatus> calculateAllRewards(@RequestBody List<Transaction> transactions) {
        Map<String, Map<String, Integer>> result  = transcationService.calculateRewards(transactions);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/Total/{id}")
    public ResponseEntity<Map<Integer, Integer>> getTotalRewardsById(@PathVariable Integer id) {
        Map<Integer, Integer> totalPoints = new HashMap<>();
        totalPoints.put(id, transcationService.findTotalRewardById(id));
        return  ResponseEntity.ok(totalPoints);
    }

    @GetMapping("/Month/{id}")
    public ResponseEntity<Map<Integer, Map<String, Integer >>> getMonthRewardsById(@PathVariable Integer id) {
        Map<Integer, Map<String, Integer >> totalPoints = new HashMap<>();
        totalPoints.put(id, transcationService.findMonthRewardById(id));
        return  ResponseEntity.ok(totalPoints);
    }

    @GetMapping("/Rewards/{id}")
    public ResponseEntity<Map<String, Map<String, Integer >>> getRewardsById(@PathVariable Integer id) {
        Map<String, Map<String, Integer >> results = transcationService.findRewardsById(id);
        return  ResponseEntity.ok(results);
    }
}
