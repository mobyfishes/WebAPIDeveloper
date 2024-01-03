package com.example.demo.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Rewards")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "customerId")
    private String customerId;

    @Column(name = "transactionDate")
    private String transactionDate;

    @Column(name = "points")

    private Integer points;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(java.lang.String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", points=" + points +
                '}';
    }
}
