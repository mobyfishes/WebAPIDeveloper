package com.example.demo.Service;

import com.example.demo.DAO.RewardRepository;
import com.example.demo.Entity.Reward;
import com.example.demo.Entity.Transaction;
import com.example.demo.Exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    RewardRepository rewardRepository;

    public Map<String, Map<String, Integer>> calculateRewards(@RequestBody List<Transaction> transactions) {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        for (Transaction transaction : transactions) {
            String customerId = transaction.getCustomerId();
            String date = transaction.getTransactionDate().getYear() + "-" +  transaction.getTransactionDate().getMonthValue();

            result.computeIfAbsent(customerId, k -> new HashMap<>());

            result.get(customerId).merge(date, calculateRewardPoints(transaction.getAmount()), Integer::sum);
            result.get(customerId).merge("Total", calculateRewardPoints(transaction.getAmount()), Integer::sum);
        }

        persistRewards(result);
        return result;
    }

    public void persistRewards(Map<String, Map<String, Integer>> result){
        for (Map.Entry<String, Map<String, Integer>> entry : result.entrySet()) {
            String customerId = entry.getKey();
            Map<String, Integer> records = entry.getValue();

            for (Map.Entry<String, Integer> record : records.entrySet()) {
                String date = record.getKey();
                if(date.equals("Total"))  continue;

                Integer points = record.getValue();

                Reward reward = new Reward();
                reward.setCustomerId(customerId);
                reward.setTransactionDate(date);
                reward.setPoints(points);

                rewardRepository.save(reward);
            }

        }
    }

    public Integer findTotalRewardById(Integer id) throws DataNotFoundException{
        List<Reward> rewardList = rewardRepository.getRewardByCustomerId(id);
        if(rewardList.size() == 0){
            throw new DataNotFoundException("No rewards record found for this customer id: " + id);
        }

        Integer totalPoints = 0;
        for(Reward reward : rewardList){
            totalPoints += reward.getPoints();
        }

        return totalPoints;
    }

    public Map<String, Integer > findMonthRewardById(Integer id) throws DataNotFoundException{
        Map<String, Integer > results = new HashMap<>();
        List<Reward> rewardList = rewardRepository.getRewardByCustomerId(id);

        if(rewardList.size() == 0){
            throw new DataNotFoundException("No rewards record found for this customer id: " + id);
        }

        for(Reward reward : rewardList){
            results.put(reward.getTransactionDate(), reward.getPoints());
        }

        return results;
    }

    public Map<String, Map<String, Integer>> findRewardsById(Integer id) {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        Integer totalPoints = 0;
        List<Reward> rewardList = rewardRepository.getRewardByCustomerId(id);
        Map<String, Integer> rewardRecord = new HashMap<>();
        for(Reward reward : rewardList){
            rewardRecord.put(reward.getTransactionDate(), reward.getPoints());
            totalPoints += reward.getPoints();
        }

        rewardRecord.put("Total", totalPoints);
        result.put(String.valueOf(id), rewardRecord);

        return result;
    }

    public int calculateRewardPoints(Double amount) {
        int points = 0;
        if(amount > 50 && amount <= 100){
            points = amount.intValue() - 50;
        }
        else if (amount > 100) {
            points += 2 * (amount - 100);
            points += 50;
        }

        return points;
    }


}