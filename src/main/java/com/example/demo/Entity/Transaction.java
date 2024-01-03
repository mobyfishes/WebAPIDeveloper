package com.example.demo.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String customerId;
    private LocalDate transactionDate;
    private double amount;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(java.lang.String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



}