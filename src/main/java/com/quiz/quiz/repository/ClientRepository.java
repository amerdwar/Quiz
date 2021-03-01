package com.quiz.quiz.repository;

import com.quiz.quiz.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ClientRepository extends JpaRepository<Client,Integer> {




}
