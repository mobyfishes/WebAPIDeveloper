package com.example.demo.Service;

import com.example.demo.DAO.RewardRepository;
import com.example.demo.Entity.Reward;
import com.example.demo.Entity.Transaction;
import com.example.demo.Exceptions.DataNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    RewardRepository rewardRepository;

    /**
     * Processing transaction record and store them into database as Rewards entity
     * @param transactions a record of every transaction during a three-month period
     */
    public void calculateRewards(List<Transaction> transactions) {
        Map<String, Map<String, Integer>> results = new HashMap<>();

        for (Transaction transaction : transactions) {
            String customerId = transaction.getCustomerId();
            String date = transaction.getTransactionDate().getYear() + "-" +  transaction.getTransactionDate().getMonthValue();

            results.computeIfAbsent(customerId, k -> new HashMap<>());

            results.get(customerId).merge(date, calculateRewardPoints(transaction.getAmount()), Integer::sum);
            results.get(customerId).merge("Total", calculateRewardPoints(transaction.getAmount()), Integer::sum);
        }
        persistRewards(results);
    }

    /**
     * Find all Rewards from database and calculate total points
     * @param id customerId
     * @return  JSON string of found total rewards points by given customerId
     * @throws DataNotFoundException No data found related to given customerId
     */
    public String findTotalRewardById(Integer id) throws DataNotFoundException{
        Map<Integer, Integer > results = new HashMap<>();
        List<Reward> rewardList = rewardRepository.getRewardByCustomerId(id);
        rewardList.sort(Comparator.comparing(Reward::getTransactionDate));

        if(rewardList.size() == 0){
            throw new DataNotFoundException("No rewards record found for this customer id: " + id);
        }

        Integer totalPoints = 0;
        for(Reward reward : rewardList){
            totalPoints += reward.getPoints();
        }
        results.put(id, totalPoints);

        return convertMapToJson(results);
    }


    /**
     * Find Rewards from database and calculate monthly points
     * @param id customerId
     * @return  JSON string of found monthly rewards points by given customerId
     * @throws DataNotFoundException No data found related to given customerId
     */
    public String findMonthRewardById(Integer id) throws DataNotFoundException{
        Map<String, Integer > results = new HashMap<>();
        List<Reward> rewardList = rewardRepository.getRewardByCustomerId(id);
        rewardList.sort(Comparator.comparing(Reward::getTransactionDate));

        if(rewardList.size() == 0){
            throw new DataNotFoundException("No rewards record found for this customer id: " + id);
        }

        for(Reward reward : rewardList){
            results.put(reward.getTransactionDate(), reward.getPoints());
        }

        return convertMapToJson(results);
    }

    /**
     * Find Rewards from database and calculate total and monthly points
     * @param id customerId
     * @return  JSON string of found otal and monthly rewards points by given customerId
     * @throws DataNotFoundException No data found related to given customerId
     */
    public String findRewardsById(Integer id) {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        Integer totalPoints = 0;
        List<Reward> rewardList = rewardRepository.getRewardByCustomerId(id);
        rewardList.sort(Comparator.comparing(Reward::getTransactionDate));

        if(rewardList.size() == 0){
            throw new DataNotFoundException("No rewards record found for this customer id: " + id);
        }

        Map<String, Integer> rewardRecord = new HashMap<>();
        for(Reward reward : rewardList){
            rewardRecord.put(reward.getTransactionDate(), reward.getPoints());
            totalPoints += reward.getPoints();
        }

        rewardRecord.put("Total", totalPoints);
        result.put(String.valueOf(id), rewardRecord);

        return convertMapToJson(result);

    }

    /**
     * Parse translation record into reward entity and save into database
     * @param result record of all translations
     */
    private void persistRewards(Map<String, Map<String, Integer>> result){
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

    /**
     * Convert map into JSON
     * @param results map stored data
     * @return JSON String
     */
    private <K, V> String convertMapToJson(Map<K, V> results){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(results);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculate reward points from transaction data
     * @param amount total cost amount in a transaction
     * @return  reward points
     */
    private int calculateRewardPoints(Double amount) {
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