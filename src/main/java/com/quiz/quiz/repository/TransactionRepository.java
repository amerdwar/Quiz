package com.quiz.quiz.repository;

import com.quiz.quiz.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    public void deleteBySaleId (int saleId);
    List<Transaction> findBySaleId(int saleId);
}
