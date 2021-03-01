package com.quiz.quiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import com.quiz.quiz.entity.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sale {


    @Id
    @GeneratedValue
    private int id;
    private String seller;
    private double total;
    private String creationDate;


    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(foreignKey = @ForeignKey(name = "client_id"), name = "client_id", insertable = false, updatable = false)

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;




}
