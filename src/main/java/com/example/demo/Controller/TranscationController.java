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
        transcationService.calculateRewards(transactions);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/Total/{id}")
    public ResponseEntity<String> getTotalRewardsById(@PathVariable Integer id) {
        String resultsJson =  transcationService.findTotalRewardById(id);
        return ResponseEntity.ok(resultsJson);
    }

    @GetMapping("/Month/{id}")
    public ResponseEntity<String> getMonthRewardsById(@PathVariable Integer id) {
        String resultsJson = transcationService.findMonthRewardById(id);
        return ResponseEntity.ok(resultsJson);
    }

    @GetMapping("/Rewards/{id}")
    public ResponseEntity<String> getRewardsById(@PathVariable Integer id) {
        String resultsJson = transcationService.findRewardsById(id);
        return ResponseEntity.ok(resultsJson);
    }
}
