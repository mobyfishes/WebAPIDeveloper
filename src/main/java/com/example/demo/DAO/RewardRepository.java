package com.example.demo.DAO;

import com.example.demo.Entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Integer> {
    @Query(value="SELECT * FROM Rewards WHERE customerId = :id", nativeQuery = true)
    public List<Reward> getRewardByCustomerId(Integer id);
}
