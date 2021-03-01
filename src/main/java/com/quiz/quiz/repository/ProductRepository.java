package com.quiz.quiz.repository;

import com.quiz.quiz.entity.Client;
import com.quiz.quiz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Integer> {
}
